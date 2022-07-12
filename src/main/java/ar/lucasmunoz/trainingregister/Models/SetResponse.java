package ar.lucasmunoz.trainingregister.Models;

public class SetResponse {

    String dayName;

    int reps;
    float weight;

    int repsLogged;


    float weightLogged;

    public int getRepsLogged() {
        return repsLogged;
    }

    public void setRepsLogged(int repsLogged) {
        this.repsLogged = repsLogged;
    }

    public float getWeightLogged() {
        return weightLogged;
    }

    public void setWeightLogged(float weightLogged) {
        this.weightLogged = weightLogged;
    }

    int setId;

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String setName) {
        this.dayName = setName;
    }
}
