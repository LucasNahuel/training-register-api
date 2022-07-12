package ar.lucasmunoz.trainingregister.Models;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    String name;

    @ManyToOne
    Training trainingFk;

    public Training getTrainingFk() {
        return trainingFk;
    }

    public void setTrainingFk(Training trainingFk) {
        this.trainingFk = trainingFk;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
