package com.wekids.backend.log.dto.reqeust;

import com.wekids.backend.log.enums.LogLevel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogRequestParams {
    @Builder.Default
    private LogLevel level = LogLevel.ALL;

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start = LocalDate.now().minusDays(3);

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end = LocalDate.now();
}
