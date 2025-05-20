package com.example.backend.service;

import com.example.backend.model.DataType;
import com.example.backend.model.Language;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TemplateService {

    private final HashMap<DataType, String> javaTypeMap;

    private void initJavaTypeMap(){
        javaTypeMap.put(DataType.INT, "int");
        javaTypeMap.put(DataType.INT_ARRAY, "int[]");
        javaTypeMap.put(DataType.STRING, "String");
        javaTypeMap.put(DataType.STRING_ARRAY, "String[]");
    }

    public TemplateService(){
        javaTypeMap = new HashMap<>();
        initJavaTypeMap();
    }

    public String generateJavaTemplate(DataType inputType, DataType outputType){
        return """
               public class Code{
               \n
               \n
               \tpublic\s""" +javaTypeMap.get(outputType)+" output("+javaTypeMap.get(inputType)+" input){"+
                """
               \n
               \t}
               \n
               }""";
    }

}
