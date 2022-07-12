package ar.lucasmunoz.trainingregister.Models;
import javax.persistence.*;

@Entity
public class SetLog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    TrainingLog trainingLogFK;


    @ManyToOne
    ExerciseSet setFK;


    @Override
    public boolean equals(Object o){

        if(o == null || o.getClass() != this.getClass()){
            return false;
        }else{
            if (((SetLog) o).getId() == this.id){
                return true;
            }
        }

        return false;
    }

    int repetitionsDone;

    float weightUsed;

    public TrainingLog getTrainingLogFK() {
        return trainingLogFK;
    }

    public void setTrainingLogFK(TrainingLog trainingLogFK) {
        this.trainingLogFK = trainingLogFK;
    }

    public ExerciseSet getSetFK() {
        return setFK;
    }

    public void setSetFK(ExerciseSet setFK) {
        this.setFK = setFK;
    }

    public int getRepetitionsDone() {
        return repetitionsDone;
    }

    public void setRepetitionsDone(int repetitionsDone) {
        this.repetitionsDone = repetitionsDone;
    }

    public float getWeightUsed() {
        return weightUsed;
    }

    public void setWeightUsed(float weightUsed) {
        this.weightUsed = weightUsed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
