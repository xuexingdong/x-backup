package com.xuexingdong.x.wishwall.repository;

import com.xuexingdong.x.wishwall.model.Wish;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WishRepository extends ReactiveMongoRepository<Wish, String> {

    Flux<Wish> findAllBy(Pageable pageable);
}
