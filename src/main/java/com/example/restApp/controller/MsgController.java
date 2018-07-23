package com.example.restApp.controller;

import com.example.restApp.models.MsgModel;
import com.example.restApp.repository.MsgRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<MsgModel> list(){
        return msgRepo.findAll();
    }
    @GetMapping("{id}")
    public MsgModel getOne(@PathVariable("id") MsgModel msgModel){
        return msgModel;
    }

    @PostMapping
    public MsgModel create(@RequestBody MsgModel msgModel){
        return msgRepo.save(msgModel);
    }

    @PutMapping("{id}")
    public MsgModel update(
            @PathVariable("id") MsgModel msgFromDb,
            @RequestBody MsgModel msgModel
    ){
        BeanUtils.copyProperties(msgModel, msgFromDb, "id");
        return msgRepo.save(msgModel);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") MsgModel msgModel){
      msgRepo.delete(msgModel);

    }
}
