package com.example.backend.service;

import com.example.backend.model.DataType;
import com.example.backend.model.Language;

public interface TemplateService {

    String generateTemplateCode(DataType inputType, DataType outputType, Language language);

}
