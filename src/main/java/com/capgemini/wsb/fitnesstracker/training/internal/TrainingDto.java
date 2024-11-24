package com.capgemini.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micrometer.common.lang.Nullable;

import java.util.Date;

public record TrainingDto(
        @Nullable Long id,
        com.capgemini.wsb.fitnesstracker.user.api.User user,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00", timezone = "UTC") Date startTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+00:00", timezone = "UTC") Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {}

