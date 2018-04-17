package com.xuexingdong.x.ucenter.repository;


import com.xuexingdong.x.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
