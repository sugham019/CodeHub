package com.example.backend.controller;

import com.example.backend.dto.CodeSubmitDTO;
import com.example.backend.dto.CodeResultDTO;
import com.example.backend.model.Language;
import com.example.backend.service.CodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/code")
public class CodeController {

    private final Map<String, CodeService> codeServiceMap;

    public CodeController(Map<String, CodeService> codeServiceMap){
        this.codeServiceMap = codeServiceMap;
    }

    @PostMapping("/submit")
    public ResponseEntity<CodeResultDTO> submit(@RequestParam Language language, @RequestBody CodeSubmitDTO codeInput){
        CodeService codeService = codeServiceMap.get(language.toString());
        return new ResponseEntity<>(codeService.submit(codeInput), HttpStatus.OK);
    }

}
