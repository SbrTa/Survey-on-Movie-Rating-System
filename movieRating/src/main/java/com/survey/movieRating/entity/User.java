package com.survey.movieRating.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private Long favRating;
    private Boolean active = true;
}
