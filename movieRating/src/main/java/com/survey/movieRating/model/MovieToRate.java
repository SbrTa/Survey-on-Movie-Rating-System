package com.survey.movieRating.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MovieToRate implements Serializable{
    private Long id;
    private String name;
    private String logo;
    private Long ratingId;
    private Long ratingSystem;
    private Long serial;
}
