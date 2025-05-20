package com.example.backend.service;

import com.example.backend.model.CodeInput;
import com.example.backend.model.CodeSubmitResult;
import com.example.backend.model.DataType;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

// Todo: Support for memory monitor support

@Service("JAVA")
public class JavaCodeService implements CodeService{

    public CodeSubmitResult submit(CodeInput codeInput) {
        try{
            Path tempDir = Files.createTempDirectory("temp_java_run");
            String mainFile = getAppropriateMainFile(codeInput.getInputType(), codeInput.getOutputType());
            if(!compile(tempDir, mainFile, codeInput.getCode())){
                return new CodeSubmitResult(false, "Compilation failed", 0);
            }
            String[] inputs = codeInput.getInputs();
            String[] expectedOutputs = codeInput.getExpectedOutputs();
            int totalTestPass = 0;
            long startTime = System.currentTimeMillis();
            for(int i=0; i<codeInput.getTotalTests(); i++){
                Process run = new ProcessBuilder("java", "-cp", tempDir.toString(), mainFile,
                        inputs[i], expectedOutputs[i]).start();
                int exitCode = run.waitFor();
                totalTestPass = (exitCode == 0)? totalTestPass + 1: totalTestPass;
            }
            long endTime = System.currentTimeMillis();
            long executionTimeInMs = endTime - startTime;
            return new CodeSubmitResult(totalTestPass == codeInput.getTotalTests(), "Total Test Case Passed: "+
                    totalTestPass+"/"+codeInput.getTotalTests(), executionTimeInMs);
        } catch (Exception e) {
            return new CodeSubmitResult(false, e.getMessage(), 0);
        }
    }

    private String getAppropriateMainFile(DataType inputType, DataType outputType){
        if(inputType == DataType.INT && outputType == DataType.INT){
            return "MainIntInt";
        }else if(inputType == DataType.INT_ARRAY && outputType == DataType.INT_ARRAY){
            return "MainIntArrayIntArray";
        }else if(inputType == DataType.STRING && outputType == DataType.STRING){
            return "MainStringString";
        }
        return null;
    }

    private boolean compile(Path tempDir, String mainFile, String userInputCode) throws IOException, InterruptedException {
        InputStream startupCode = JavaCodeService.class.getClassLoader().getResourceAsStream("startup/"+mainFile+".java");
        if(startupCode == null){
            return false;
        }
        Path startupFile = tempDir.resolve(mainFile+".java");
        Files.copy(startupCode, startupFile, StandardCopyOption.REPLACE_EXISTING);
        Path userCodeFile = tempDir.resolve("Code.java");
        Files.writeString(userCodeFile, userInputCode);
        Process compile = new ProcessBuilder(
                "javac",
                startupFile.toString(),
                userCodeFile.toString()
        ).inheritIO().start();
        return compile.waitFor() == 0;
    }
}
