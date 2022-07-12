package ar.lucasmunoz.trainingregister.Models;


import javax.persistence.*;

@Entity
public class TrainingRatings {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    User userFK;

    @ManyToOne
    Training trainingFK;

    Integer rating;

    String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserFK() {
        return userFK;
    }

    public void setUserFK(User userFK) {
        this.userFK = userFK;
    }

    public Training getTrainingFK() {
        return trainingFK;
    }

    public void setTrainingFK(Training trainingFK) {
        this.trainingFK = trainingFK;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
