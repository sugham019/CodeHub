package com.example.backend.controller;

import com.example.backend.model.CompileResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeController {

    @PostMapping("/compile")
    public CompileResult compile(RequestBody code){
        // Todo
        return null;
    }
}
