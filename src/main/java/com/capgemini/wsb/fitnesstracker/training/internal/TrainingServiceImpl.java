package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// TODO: Provide Impl
@Service
@RequiredArgsConstructor
@Slf4j

public class TrainingServiceImpl implements TrainingProvider{
    private final TrainingRepository trainingRepository;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    @Override
    public List<Training> findFinishedTrainingsAfter(String afterTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(afterTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + afterTime);
        }
        return trainingRepository.findByEndTimeAfter(date);
    }

    @Override
    public List<Training> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    @Override
    public Training createTraining(Training training) {
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID");
        }
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Training training) {
        if (training.getId() == null) {
            throw new IllegalArgumentException("Missing training with this ID");
        }
        return trainingRepository.save(training);
    }

}
