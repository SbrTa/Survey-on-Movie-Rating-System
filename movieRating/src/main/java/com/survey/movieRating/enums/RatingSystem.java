package com.survey.movieRating.enums;

import lombok.Getter;

@Getter
public enum RatingSystem {
    COLOR_CIRCLE(1,"Color Circle"),
    COLOR_STAR(2,"Color Star"),
    COLOR_EMO(3,"Color Emoticon"),
//    GRADIENT(4,"Gradient"),
    BW_CIRCLE(4,"BW Circle"),
    BW_EMO(5,"BW Emoticon"),
    BW_STAR(6,"BW Star");
    int ratingSystem;
    String ratingSystemName;

    RatingSystem() {
    }

    RatingSystem(int ratingSystem, String ratingSystemName) {
        this.ratingSystem = ratingSystem;
        this.ratingSystemName = ratingSystemName;
    }
}
