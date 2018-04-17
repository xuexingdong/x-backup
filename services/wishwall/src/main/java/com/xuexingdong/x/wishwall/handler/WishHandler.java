package com.xuexingdong.x.wishwall.handler;


import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuexingdong.x.wishwall.mapper.WishMapper;
import com.xuexingdong.x.wishwall.model.Wish;
import com.xuexingdong.x.wishwall.repository.WishRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
public class WishHandler {

    private final WishRepository wishRepository;

    @Autowired
    private GridFsOperations operations;

    @Autowired
    private WishMapper wishMapper;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public WishHandler(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<GridFsResource> resourceMono = mongoTemplate.findById(id, Wish.class)
                .map(wish -> operations.getResource(wish.getPic()));
        return ok().body(BodyInserters.fromDataBuffers(resourceMono.map(resource -> {
            return DataBufferUtils.read(resource, null, StreamUtils.BUFFER_SIZE);
        })));
    }

    @PostMapping
    public Mono<ServerResponse> post(ServerRequest request) {
        return request
                .body(BodyExtractors.toMultipartData())
                .flatMap(map -> {
                    try {
                        Map<String, Part> parts = map.toSingleValueMap();
                        String title = parts.get("title").toString();
                        Part pic = parts.get("pic");
                        Optional<ObjectId> objectId = DataBufferUtils.join(pic.content())
                                .map(dataBuffer -> operations.store(dataBuffer.asInputStream(true), "a.jpg")).blockOptional();
                        return ServerResponse.ok().body(BodyInserters.from(objectId.get()));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}