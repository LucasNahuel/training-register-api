package ar.lucasmunoz.trainingregister.Models;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
public class Training {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    String name;

    @ManyToOne
    User creatorFK;

    @JsonInclude()
    @Transient
    Float rating;

    @JsonInclude()
    @Transient
    Integer noOfRatings;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatorFK() {
        return creatorFK;
    }

    public void setCreatorFK(User creatorFK) {
        this.creatorFK = creatorFK;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getNoOfRatings() {
        return noOfRatings;
    }

    public void setNoOfRatings(Integer noOfRatings) {
        this.noOfRatings = noOfRatings;
    }
}
