package ar.lucasmunoz.trainingregister.Models;


import javax.persistence.*;

@Entity
public class TrainingOfUsers {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    User user;
    @ManyToOne
    Training training;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    boolean isActive;




}
