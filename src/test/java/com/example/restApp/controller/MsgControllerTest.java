package com.example.restApp.controller;

import com.example.restApp.exeptions.NotFoundExeption;
import com.example.restApp.models.MsgModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MsgControllerTest {
    MsgController msgController;
    final String id ="1";

    @Before
    public void tearDown() throws Exception {
        msgController = new MsgController();
    }

    @Test
    public void list() {
        final List<MsgModel> mapList= msgController.list();
        assertNotEquals(new ArrayList<Map<String, String>>(),mapList);
        assertNotNull(mapList);
    }

    @Test(expected = NotFoundExeption.class)
    public void getOneNotFoundExept() {
        msgController.getOne("nan");
    }

    @Test
    public void getOne() {
        final Map<String,String> getOne = msgController.getOne(id);
        assertNotEquals(new HashMap<String, String>(),getOne);
        assertNotNull(getOne);
    }

    @Test
    public void create() {
        final Map<String,String> testMap = new HashMap<String, String>();
        testMap.put("text","test");
        final Map<String,String> createMap = msgController.create(testMap);
        assertNotNull(createMap);
        assertFalse(Integer.parseInt(createMap.get("id"))<=0);
        assertNotEquals(new HashMap<String, String>(),createMap);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}