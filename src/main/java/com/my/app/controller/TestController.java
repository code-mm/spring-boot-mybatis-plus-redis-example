package com.my.app.controller;


import com.my.app.mysql.ms_a.entity.MTestA;
import com.my.app.mysql.ms_a.service.IMTestAService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Resource
    private IMTestAService imTestAService;

    @GetMapping(value = "/test")
    public List<MTestA> test() {
        List<MTestA> list = imTestAService.list();
        return list;
    }

    @GetMapping(value = "/test_1")
    public MTestA test_1() {
        return imTestAService.getMTestA(1);
    }
}
