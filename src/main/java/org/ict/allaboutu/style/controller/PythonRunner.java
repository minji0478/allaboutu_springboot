package org.ict.allaboutu.style.controller;

import lombok.extern.java.Log;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Slf4j
public class PythonRunner {

    public void runPythonFile(String dir_path, String imagePath) throws IOException {
        String pythonFilePath = "/poketAi_workspace/allaboutu_ai/main.py";
        ProcessBuilder processBuilder = new ProcessBuilder("python", pythonFilePath, dir_path, imagePath);
        processBuilder.inheritIO();
        processBuilder.directory(new File("/poketAi_workspace/allaboutu_ai"));
        Process process = processBuilder.start();

        try {
            int exitCode = process.waitFor();
            System.out.println("Python process exited with code: " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void callPythonApi() {
        String apiUrl = "http://localhost:5000/";

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 바디 설정
        String requestBody = "{\"data\": \"example data\"}";
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);

        // REST 클라이언트 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, String.class);

        // API 응답 처리
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            // 응답 처리 로직 작성
            log.info("responseBody : " + responseBody);
        }
    }
}
