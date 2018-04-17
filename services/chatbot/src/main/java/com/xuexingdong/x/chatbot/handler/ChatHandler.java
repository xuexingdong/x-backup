package com.xuexingdong.x.chatbot.handler;


import com.xuexingdong.x.chatbot.model.Chat;
import com.xuexingdong.x.chatbot.repository.ChatRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

@Component
public class ChatHandler {

    private final ChatRepository repository;

    public ChatHandler(ChatRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getChat(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Chat> mono = repository.findById(id);
        System.out.println(mono.block());
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(mono, Chat.class).switchIfEmpty(ServerResponse.badRequest().build())
                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    public Mono<ServerResponse> listChat(ServerRequest request) {
        Optional<String> nickname = request.queryParam("nickname");
        if (!nickname.isPresent()) {
            return ServerResponse.badRequest().build();
        }
        Integer page = request.queryParam("page").map(Integer::valueOf).orElse(1);
        Integer size = request.queryParam("size").map(Integer::valueOf).orElse(10);
        Pageable pageable = PageRequest.of(page, size);
        Flux<Chat> chats = repository.findAllByNickname(nickname.get(), pageable);
        return ServerResponse.ok().contentType(APPLICATION_STREAM_JSON).body(chats, Chat.class);
    }

}