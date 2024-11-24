package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor

public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;

    /**
     * Wszukanie wszystkich treningów
     * @return wszystkie treningi
     */
     @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings().stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }
    /**
     * Wszukanie treningu po ID użytkownika
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUser(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId).stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    /**
     * Wyszukanie po czasu zakończenia treningu
     * @param afterTime
     * @return
     */

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainingsAfter(@PathVariable String afterTime) {
        return trainingService.findFinishedTrainingsAfter(afterTime)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    /**
     * Wyszukanie treningu za pomocą typu treningu
     * @param activityType
     * @return
     */

    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.findTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    /**
     * Dodanie treningu
     * @param newTrainingDto
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training createTraining(@RequestBody NewTrainingDto newTrainingDto) {
        User user = userProvider.getUser(newTrainingDto.userId()).orElseThrow(() -> new IllegalArgumentException("User with ID: " + newTrainingDto.userId() + " not found"));
        return trainingService.createTraining(trainingMapper.toNewEntity(newTrainingDto, user));
    }

    /**
     * Edycja wybranego treningu
     * @param trainingId
     * @param newTrainingDto
     * @return
     */
    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable Long trainingId, @RequestBody NewTrainingDto newTrainingDto) {
        Training training = trainingService.getTraining(trainingId).orElseThrow(() -> new IllegalArgumentException("Training with ID: " + trainingId + " not found"));
        Training updatedTraining = trainingMapper.toUpdateEntity(newTrainingDto, training);
        return trainingService.updateTraining(updatedTraining);
    }

}
