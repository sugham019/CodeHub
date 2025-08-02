package com.example.backend.controller.api;

import com.example.backend.dto.CodeResultDto;
import com.example.backend.model.Language;
import com.example.backend.service.CodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    private final Map<String, CodeService> codeServiceMap;

    public CodeController(Map<String, CodeService> codeServiceMap){
        this.codeServiceMap = codeServiceMap;
    }

    @PostMapping("/submit")
    public ResponseEntity<CodeResultDto> submit(@RequestParam String problemId, @RequestParam Language language, @RequestBody String code){
        CodeService codeService = codeServiceMap.get(language.toString());
        CodeResultDto result = codeService.submit(problemId, code);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
