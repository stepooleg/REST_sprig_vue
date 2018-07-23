package com.example.restApp.repository;

import com.example.restApp.models.MsgModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsgRepo extends JpaRepository<MsgModel, Long> {

}
