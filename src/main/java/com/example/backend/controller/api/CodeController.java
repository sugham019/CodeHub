package com.example.backend.controller.api;

import com.example.backend.dto.CodeResultDto;
import com.example.backend.model.Language;
import com.example.backend.model.Problem;
import com.example.backend.model.UserPrincipal;
import com.example.backend.service.CodeService;
import com.example.backend.service.ProblemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    private final Map<String, CodeService> codeServiceMap;

    private final ProblemService problemService;

    public CodeController(Map<String, CodeService> codeServiceMap, ProblemService problemService){
        this.codeServiceMap = codeServiceMap;
        this.problemService = problemService;
    }

    @PostMapping("/submit")
    public ResponseEntity<CodeResultDto> submit(@RequestParam String problemId, @RequestParam Language language, @RequestBody String code,
                                                @AuthenticationPrincipal UserPrincipal userDetails){
        Problem problem = problemService.getProblemById(problemId);
        CodeService codeService = codeServiceMap.get(language.toString());
        CodeResultDto result = codeService.submit(userDetails.getUsername(), problem, code);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
