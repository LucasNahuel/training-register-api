package ar.lucasmunoz.trainingregister.Models;
import javax.persistence.*;

@Entity
public class TrainingDay {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    String day;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
