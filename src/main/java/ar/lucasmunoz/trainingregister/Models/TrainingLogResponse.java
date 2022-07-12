package ar.lucasmunoz.trainingregister.Models;

import java.util.ArrayList;
import java.util.List;

public class TrainingLogResponse {

    int id;

    String trainingName;

    String creatorName;

    List<ExerciseLogResponse> exercises = new ArrayList<>();

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public List<ExerciseLogResponse> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseLogResponse> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(ExerciseLogResponse elr){
        this.exercises.add(elr);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
