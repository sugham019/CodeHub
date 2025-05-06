package com.example.backend.controller;

import com.example.backend.model.DataType;
import com.example.backend.service.CodeTemplateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
public class CodeTemplateController {

    private final CodeTemplateService codeTemplateService;

    public CodeTemplateController(CodeTemplateService codeTemplateService){
        this.codeTemplateService = codeTemplateService;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam DataType inputType, @RequestParam DataType outputType){
        System.out.println("Hit");
        return codeTemplateService.generate(inputType, outputType);
    }
}
