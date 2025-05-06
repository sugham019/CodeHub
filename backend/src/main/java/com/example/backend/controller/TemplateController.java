package com.example.backend.controller;

import com.example.backend.model.DataType;
import com.example.backend.service.TemplateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
public class TemplateController {

    private final TemplateService codeTemplateService;

    public TemplateController(TemplateService codeTemplateService){
        this.codeTemplateService = codeTemplateService;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam DataType inputType, @RequestParam DataType outputType){
        return codeTemplateService.generate(inputType, outputType);
    }
}
