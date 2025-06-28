package com.example.backend.service;

import com.example.backend.dto.CodeSubmitDto;
import com.example.backend.dto.CodeResultDto;

public interface CodeService {

    CodeResultDto submit(CodeSubmitDto codeInput);

}
