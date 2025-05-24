package com.example.backend.service;

import com.example.backend.dto.CodeSubmitDTO;
import com.example.backend.dto.CodeResultDTO;

public interface CodeService {

    CodeResultDTO submit(CodeSubmitDTO codeInput);

}
