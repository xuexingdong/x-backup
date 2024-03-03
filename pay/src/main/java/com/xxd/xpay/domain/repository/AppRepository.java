package com.xxd.xpay.domain.repository;

import com.xxd.xpay.domain.aggregate.App;

import java.util.Optional;

public interface AppRepository {

    Optional<App> findByAppId(String appId);
}
