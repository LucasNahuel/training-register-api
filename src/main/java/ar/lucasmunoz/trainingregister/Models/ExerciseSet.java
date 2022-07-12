package ar.lucasmunoz.trainingregister.Models;
import javax.persistence.*;

@Entity
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    ExerciseDay exerciseDayFk;

    int repetitions;

    float weight;

    @Override
    public boolean equals(Object o){

        if(o == null || o.getClass() != this.getClass()){
            return false;
        }else{
            if (((ExerciseSet) o).getId() == this.id){
                return true;
            }
        }

        return false;
    }

    public ExerciseDay getExerciseDayFk() {
        return exerciseDayFk;
    }

    public void setExerciseDayFk(ExerciseDay exerciseDayFk) {
        this.exerciseDayFk = exerciseDayFk;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
