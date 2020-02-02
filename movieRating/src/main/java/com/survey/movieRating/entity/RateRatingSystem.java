package com.survey.movieRating.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class RateRatingSystem {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long colorCircle;
    private Long colorStar;
    private Long colorEmo;
    private Long gradient;
    private Long bwCircle;
    private Long bwEmo;
    private Long bwStar;
}
