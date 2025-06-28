package com.example.backend.controller.api;

import com.example.backend.model.DataType;
import com.example.backend.model.Language;
import com.example.backend.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/template")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService codeTemplateService){
        this.templateService = codeTemplateService;
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generate(@RequestParam DataType inputType, @RequestParam DataType outputType, @RequestParam Language language){
        String generatedCode = templateService.generateTemplateCode(inputType, outputType, language);
        return new ResponseEntity<>(generatedCode, HttpStatus.OK);
    }

}
