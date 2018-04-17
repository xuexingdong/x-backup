// package com.xuexingdong.x.ucenter.handler;
//
//
// import org.springframework.stereotype.Component;
// import org.springframework.web.reactive.function.server.ServerRequest;
// import org.springframework.web.reactive.function.server.ServerResponse;
// import reactor.core.publisher.Mono;
//
// import java.net.URI;
// import java.util.Optional;
//
// @Component
// public class TokenHandler {
//
//     public Mono<ServerResponse> authorize(ServerRequest request) {
//         Optional<String> response_type = request.queryParam("response_type");
//         Optional<String> client_id = request.queryParam("client_id");
//         Optional<String> redirect_uri = request.queryParam("redirect_uri");
//         Optional<String> scope = request.queryParam("scope");
//         Optional<String> state = request.queryParam("state");
//         if (redirect_uri.isPresent()) {
//             URI uri = URI.create(redirect_uri.get());
//             return ServerResponse.temporaryRedirect(uri).build();
//         }
//         return ServerResponse.badRequest().build();
//     }
//
// }