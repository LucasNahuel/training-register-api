package ar.lucasmunoz.trainingregister.Models;

import java.util.ArrayList;
import java.util.List;

public class ExerciseLogResponse {


    int id;
    String exerciseName;

    List<SetResponse> sets = new ArrayList<>();

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public List<SetResponse> getSets() {
        return sets;
    }

    public void setSets(List<SetResponse> sets) {
        this.sets = sets;
    }

    public void addSet(SetResponse sr){
        sets.add(sr);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
