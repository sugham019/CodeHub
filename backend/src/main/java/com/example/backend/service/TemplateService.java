package com.example.backend.service;

import com.example.backend.model.DataType;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TemplateService {

    private final HashMap<DataType, String> types;

    public TemplateService(){
         types = new HashMap<>();
         types.put(DataType.INT_ARRAY, "int[]");
         types.put(DataType.STRING, "String");
         types.put(DataType.STRING_ARRAY, "String[]");
    }

    public String generate(DataType inputType, DataType outputType){
        return """
               public class Code{
               \n
               \n
               \tpublic\s""" +types.get(outputType)+" output("+types.get(inputType)+" input){"+
                """
               \n
               \t}
               \n
               }""";
    }

}
