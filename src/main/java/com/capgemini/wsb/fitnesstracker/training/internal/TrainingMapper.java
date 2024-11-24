package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserController;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class TrainingMapper {
    /**
     * Wyświetlanie treningów
     * @param training
     * @return
     */
    TrainingDto toTrainingDto(Training training){
        return new TrainingDto(training.getId(),
                training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());
    }

    /**
     * Dodawanie nowego treningu
     * @param newTrainingDto
     * @param user
     * @return
     */
    Training toNewEntity(NewTrainingDto newTrainingDto, User user) {
        return new Training(
                user,
                newTrainingDto.startTime(),
                newTrainingDto.endTime(),
                newTrainingDto.activityType(),
                newTrainingDto.distance(),
                newTrainingDto.averageSpeed()
        );
    }

    /**
     * Aktualizacja/edycja wybranego treningu
     * @param newTrainingDto
     * @param training
     * @return
     */
    Training toUpdateEntity(NewTrainingDto newTrainingDto, Training training) {
        Optional.ofNullable(newTrainingDto.startTime()).ifPresent(training::setStartTime);
        Optional.ofNullable(newTrainingDto.endTime()).ifPresent(training::setEndTime);
        Optional.ofNullable(newTrainingDto.activityType()).ifPresent(training::setActivityType);
        Optional.of(newTrainingDto.distance()).ifPresent(training::setDistance);
        Optional.of(newTrainingDto.averageSpeed()).ifPresent(training::setAverageSpeed);
        return training;
    }
}
