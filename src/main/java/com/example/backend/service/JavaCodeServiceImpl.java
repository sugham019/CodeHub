package com.example.backend.service;

import com.example.backend.dto.CodeResultDto;
import com.example.backend.exception.CodeSubmissionException;
import com.example.backend.model.DataType;
import com.example.backend.model.Language;
import com.example.backend.util.MemoryUsageMonitor;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

@Service("JAVA")
public class JavaCodeServiceImpl extends CodeService{

    private static final int CODE_EXEC_TIMEOUT_SEC = 3;


    public JavaCodeServiceImpl(UserService userService, ProblemService problemService){
        super(Language.JAVA, userService, problemService);
    }

    public CodeResultDto compileAndRun(String code, DataType inputType, String[] inputs, DataType outputType, String[] expectedOutputs) {
        try{
            Path tempDir = Files.createTempDirectory("temp_java_run");
            String mainFile = getAppropriateMainFile(inputType, outputType);
            compile(tempDir, mainFile, code);
            int totalTestPass = 0;
            long startTime = System.currentTimeMillis();
            double peakMemoryUsageKB = 0.0;
            for(int i=0; i<inputs.length; i++){
                Process run = new ProcessBuilder("java", "-cp", tempDir.toString(), mainFile,
                        inputs[i], expectedOutputs[i]).start();

                // Start the memory usage monitor which will keep track of peek memory usage of the process
                MemoryUsageMonitor memoryUsageMonitor = new MemoryUsageMonitor((int) run.pid());
                Thread mThread = new Thread(memoryUsageMonitor);
                mThread.start();

                boolean finished = run.waitFor(CODE_EXEC_TIMEOUT_SEC, TimeUnit.SECONDS);
                if(!finished){
                    run.destroyForcibly();
                    throw new CodeSubmissionException("The code took too long to execute");
                }
                mThread.interrupt(); // the mThread should exit naturally after the process exits, just a failsafe
                double currentMemoryUsageKB = (double) memoryUsageMonitor.getPeakMemoryUsage() / 1024;
                // only record max peak memory usage among all test cases
                peakMemoryUsageKB = Math.max(peakMemoryUsageKB, currentMemoryUsageKB);

                int exitCode = run.exitValue();
                totalTestPass = (exitCode == 0)? totalTestPass + 1: totalTestPass;
            }
            long endTime = System.currentTimeMillis();
            long executionTimeInMs = endTime - startTime;
            return new CodeResultDto(totalTestPass == inputs.length, "Total Test Case Passed: "+
                    totalTestPass+"/"+inputs.length, executionTimeInMs, peakMemoryUsageKB);
        } catch (Exception e) {
            return new CodeResultDto(false, e.getMessage(), 0, 0);
        }
    }

    @Override
    protected boolean isUsingLibrary(String code, String library){
        CompilationUnit compilationUnit;
        try{
            compilationUnit = StaticJavaParser.parse(code);
        }catch (ParseProblemException e){
            throw new CodeSubmissionException(e.getProblems().getFirst().getMessage());
        }
        for(ImportDeclaration importDecl: compilationUnit.getImports()){
            String importName = importDecl.getNameAsString();
            if(importName.contains(library)){
                return true;
            }
        }
        return false;
    }

    private String getAppropriateMainFile(DataType inputType, DataType outputType){
        if(inputType == DataType.INT && outputType == DataType.INT){
            return "MainIntInt";
        }else if(inputType == DataType.INT_ARRAY && outputType == DataType.INT_ARRAY){
            return "MainIntArrayIntArray";
        }else if(inputType == DataType.STRING && outputType == DataType.STRING){
            return "MainStringString";
        }
        throw new RuntimeException("Invalid Return Type/Param Type");
    }

    private void compile(Path tempDir, String mainFile, String userInputCode) throws IOException, InterruptedException{
        InputStream startupCode = JavaCodeServiceImpl.class.getClassLoader().getResourceAsStream("startup/java/"+mainFile+".java");
        if(startupCode == null){
            throw new IOException("Server Error: Could not find Java Main File");
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
        if(compile.waitFor() != 0){
            throw new RuntimeException("Compilation failed");
        }
    }

}
