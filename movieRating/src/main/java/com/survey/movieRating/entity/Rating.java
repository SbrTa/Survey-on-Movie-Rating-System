package com.survey.movieRating.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Rating {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long movieId;
    private Long ratingSystem;
    private String ratingSystemName;
    private Long rating;
    private Boolean isRated=false;
    private Long serial;
}
