package com.wekids.backend.log.service;

import com.wekids.backend.log.dto.reqeust.LogRequestParams;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.util.Map;

public interface LogService {
    public Map<String, String> showLogs(@ModelAttribute LogRequestParams requestParams);
    public void backUp(LocalDate date);
}
