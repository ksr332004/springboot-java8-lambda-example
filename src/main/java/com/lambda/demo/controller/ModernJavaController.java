package com.lambda.demo.controller;

import com.lambda.demo.interfaces.ListToStringFunction;
import com.lambda.demo.service.ModernJavaUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/email")
public class ModernJavaController {
    private final ModernJavaUserService modernJavaUserService;

    public ModernJavaController(ModernJavaUserService modernJavaUserService) {
        this.modernJavaUserService = modernJavaUserService;
    }

    @GetMapping("/comma/{name}")
    public String getUserEmailWithComma(@PathVariable("name") String name) {
        List<String> emails = modernJavaUserService.findUserEmailByName(name);
        return transformList(emails, (l) -> String.join(",", l));
    }

    @GetMapping("/colon/{name}")
    public String getUserEmailWithColon(@PathVariable("name") String name) {
        List<String> emails = modernJavaUserService.findUserEmailByName(name);
        return transformList(emails, (l) -> String.join(":", l));
    }

    private static String transformList(List<String> list, ListToStringFunction function) {
        return function.execute(list);
    }

}
