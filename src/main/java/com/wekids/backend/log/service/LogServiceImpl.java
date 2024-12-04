package com.wekids.backend.log.service;

import com.wekids.backend.log.dto.reqeust.LogRequestParams;
import com.wekids.backend.log.enums.LogLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final String LOG_DIR = "./logs";
    public Map<String, String> getLogs(LogRequestParams requestParams) {
        Map<String, String> logMap = new HashMap<>();
        LocalDate currentDate = requestParams.getStart();

        while (!currentDate.isAfter(requestParams.getEnd())) {
            try {
                currentDate = currentDate.plusDays(1);
                String logFilePath = LOG_DIR + "/" + requestParams.getLevel() + "/" + requestParams.getLevel() + "-" + currentDate + ".log";
                Path path = Paths.get(logFilePath);
                logMap.put(currentDate.toString(), Files.readAllLines(path, StandardCharsets.UTF_8).toString());
            } catch (NoSuchFileException e) {
            } catch (IOException e) {
            }
        }
        return logMap;
    }

    public void removeLog(LogLevel logLevel, LocalDate date) {
        try {
            String logFilePath = LOG_DIR + "/" + logLevel + "/" + logLevel + "-" + date + ".log";
            Path path = Paths.get(logFilePath);
            Files.delete(path);
        }
        catch (NoSuchFileException e) {}
        catch (IOException e) {}
    }
}
