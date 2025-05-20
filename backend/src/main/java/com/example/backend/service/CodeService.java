package com.example.backend.service;

import com.example.backend.model.CodeInput;
import com.example.backend.model.CodeSubmitResult;

public interface CodeService {

    CodeSubmitResult submit(CodeInput codeInput);
}
