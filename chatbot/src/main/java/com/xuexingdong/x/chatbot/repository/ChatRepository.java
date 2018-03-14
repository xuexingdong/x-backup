package com.xuexingdong.x.chatbot.repository;

import com.xuexingdong.x.chatbot.model.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    Flux<Chat> findAllBy(Pageable pageable);

    Flux<Chat> findAllByNickname(String nickname, Pageable pageable);
}
