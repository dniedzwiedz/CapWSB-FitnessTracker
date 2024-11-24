package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    List<Training> findAllTrainings();
    List<Training> getTrainingsByUserId(Long userId);
    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);

    /**
     * Zwraca trening po wybranym przez użytkownika czasie
     * @param afterTime
     * @return
     */
    List<Training> findFinishedTrainingsAfter(String afterTime);

    /**
     * Zwraca trening po wybranym przez użytkownika typie aktywności
     * @param activityType
     * @return
     */
    List<Training> findTrainingsByActivityType(ActivityType activityType);

    /**
     * Tworzy trening
     * @param training
     * @return
     */
    Training createTraining(Training training);

    /**
     * Edytuje wybrany trening
     * @param training
     * @return
     */
    Training updateTraining(Training training);
}
