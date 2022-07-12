package ar.lucasmunoz.trainingregister.Models;
import javax.persistence.*;

@Entity
public class Progression {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    float weight;

    int reps;

    int step;

    String occurrence;

    @ManyToOne
    Exercise exerciseFK;

    int incrementEvery;

    Integer nextStep;
}
