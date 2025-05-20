package com.example.backend.controller;

import com.example.backend.model.CodeInput;
import com.example.backend.model.CodeSubmitResult;
import com.example.backend.model.Language;
import com.example.backend.service.CodeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/code")
public class CodeController {

    private final Map<String, CodeService> codeServiceMap;

    public CodeController(Map<String, CodeService> codeServiceMap){
        this.codeServiceMap = codeServiceMap;
    }

    @PostMapping("/compileAndRun")
    public CodeSubmitResult compileAndRun(@RequestParam Language language, @RequestBody CodeInput codeInput){
        CodeService codeService = codeServiceMap.get(language.toString());
        return codeService.submit(codeInput);
    }
}
