package com.instabug.backend.challenge.application.repository;

import com.instabug.backend.challenge.application.entity.Application;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
ApplicationRepository extends JpaRepository<Application, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Application a SET a.chatsCount = :chatCount WHERE a.token = :token")
    void updateChatsCount(@Param("token") String token, @Param("chatCount") Long chatCount);
    Application findByToken(String token);

    @Query("SELECT a.token FROM Application a")
    List<String> findAllTokens();
}
