package com.wekids.backend.mission.dto.request;

import com.wekids.backend.mission.domain.enums.MissionCategory;
import com.wekids.backend.mission.domain.enums.MissionState;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissionListGetRequestParams {
   private MissionState state;
   private MissionCategory category;
   private Long child;
}
