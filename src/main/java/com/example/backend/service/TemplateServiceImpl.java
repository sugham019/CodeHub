package com.example.backend.service;

import com.example.backend.exception.LanguageNotSupportedException;
import com.example.backend.model.DataType;
import com.example.backend.model.Language;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TemplateServiceImpl implements TemplateService{

    private final HashMap<DataType, String> javaTypeMap = new HashMap<>();

    private void initJavaTypeMap(){
        javaTypeMap.put(DataType.INT, "int");
        javaTypeMap.put(DataType.INT_ARRAY, "int[]");
        javaTypeMap.put(DataType.STRING, "String");
        javaTypeMap.put(DataType.STRING_ARRAY, "String[]");
    }

    public TemplateServiceImpl(){
        initJavaTypeMap();
    }

    @Override
    public String generateTemplateCode(DataType inputType, DataType outputType, Language language) {
        if(language == Language.JAVA){
            return generateJavaTemplate(inputType, outputType);
        }
        throw new LanguageNotSupportedException(language);
    }

    public String generateJavaTemplate(DataType inputType, DataType outputType){
        return """
               \n
               public class Code{
               \n
               \tpublic\s""" +javaTypeMap.get(outputType)+" output("+javaTypeMap.get(inputType)+" input){"+
                """
               \n
               \t}
               \n
               }
               """;
    }

}
