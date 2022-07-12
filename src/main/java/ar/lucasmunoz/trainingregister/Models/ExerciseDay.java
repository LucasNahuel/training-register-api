package ar.lucasmunoz.trainingregister.Models;


import javax.persistence.*;

@Entity
public class ExerciseDay {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
     Integer id;

    @ManyToOne
    Exercise exerciseFk;

    @ManyToOne
    TrainingDay trainingDayFk;

    Integer executionOrder;

    public TrainingDay getTrainingDayFk() {
        return trainingDayFk;
    }

    public void setTrainingDayFk(TrainingDay trainingDayFk) {
        this.trainingDayFk = trainingDayFk;
    }

    public Exercise getExerciseFk() {
        return exerciseFk;
    }

    public void setExerciseFk(Exercise exerciseFk) {
        this.exerciseFk = exerciseFk;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }
}
