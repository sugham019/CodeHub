package com.example.backend.service;

import com.example.backend.dto.CodeSubmitDto;
import com.example.backend.dto.CodeResultDto;
import org.springframework.stereotype.Service;

// Todo
@Service("PYTHON")
public class PythonCodeServiceImpl extends CodeService{

    @Override
    public CodeResultDto compileAndRun(CodeSubmitDto codeInput) {
        return null;
    }

}
