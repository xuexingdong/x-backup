package com.xxd.x.todolist.domain.factory;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class IdGenerator {
    public <T> Long generate(Class<T> clazz) {
        return new Random().nextLong();
    }
}
