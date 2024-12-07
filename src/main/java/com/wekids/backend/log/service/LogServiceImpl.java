package com.wekids.backend.log.service;

import com.wekids.backend.aws.s3.AmazonS3Manager;
import com.wekids.backend.aws.s3.Filepath;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.log.dto.reqeust.LogRequestParams;
import com.wekids.backend.log.enums.LogLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
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
    private static final String LOG_DIR = "./logs";
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public Map<String, String> showLogs(LogRequestParams requestParams) {
        Map<String, String> logMap = new HashMap<>();
        LocalDate currentDate = requestParams.getStart();

        while (!currentDate.isAfter(requestParams.getEnd())) {
            String logFilePath = buildLogFilePath(requestParams.getLevel(), currentDate);

            try {
                logMap.put(
                        currentDate.toString(),
                        Files.readAllLines(Paths.get(logFilePath), StandardCharsets.UTF_8).toString()
                );
            }
            catch (NoSuchFileException e) {}
            catch (IOException e) { throw new WekidsException(ErrorCode.FAILED_READ_LOG, e.getMessage());}

            currentDate = currentDate.plusDays(1);
        }
        return logMap;
    }

    @Override
    public void backUp(LocalDate date) {
        backupLogsByLevel(LogLevel.ALL, date);
        backupLogsByLevel(LogLevel.INFO, date);
        backupLogsByLevel(LogLevel.WARM, date);
        backupLogsByLevel(LogLevel.ERROR, date);
    }

    private void backupLogsByLevel(LogLevel level, LocalDate date) {
        Map<String, File> logFiles = findLogsByLogLevelAndDate(level, date);

        for (Map.Entry<String, File> entry : logFiles.entrySet()) {
            String logKey = level + "/" + entry.getKey();
            File logFile = entry.getValue();

            amazonS3Manager.uploadLogFile(Filepath.LOG + "/" +logKey, logFile);
            removeLog(logFile.getAbsolutePath());
        }
    }

    private Map<String, File> findLogsByLogLevelAndDate(LogLevel level, LocalDate date) {
        Map<String, File> logFiles = new HashMap<>();
        String logFilePath = buildLogFilePath(level, date);

        Path path = Paths.get(logFilePath);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            logFiles.put(path.getFileName().toString(), path.toFile());
        }

        return logFiles;
    }

    private void removeLog(String logFilePath) {
        try {
            Files.deleteIfExists(Paths.get(logFilePath));
        } catch (IOException e) {
            throw new WekidsException(ErrorCode.FAILED_DELETE_LOG, e.getMessage());
        }
    }

    private String buildLogFilePath(LogLevel level, LocalDate date) {
        return LOG_DIR + "/" + level + "/" + level + "-" + date + ".log";
    }
}
