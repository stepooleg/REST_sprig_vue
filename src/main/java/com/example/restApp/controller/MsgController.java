package com.example.restApp.controller;

import com.example.restApp.models.MsgModel;
import com.example.restApp.models.Views;
import com.example.restApp.repository.MsgRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("message")
public class MsgController {
    private final MsgRepo msgRepo;

    @Autowired
    public MsgController(MsgRepo msgRepo) {
        this.msgRepo = msgRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<MsgModel> list(){
        return msgRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public MsgModel getOne(@PathVariable("id") MsgModel msgModel){
        return msgModel;
    }

    @PostMapping
    public MsgModel create(@RequestBody MsgModel msgModel){
        msgModel.setCreationDate(LocalDateTime.now());
        return msgRepo.save(msgModel);
    }

    @PutMapping("{id}")
    public MsgModel update(
            @PathVariable("id") MsgModel msgFromDb,
            @RequestBody MsgModel msgModel
    ){
        BeanUtils.copyProperties(msgModel, msgFromDb, "id");
        return msgRepo.save(msgFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") MsgModel msgModel){
      msgRepo.delete(msgModel);

    }
}
