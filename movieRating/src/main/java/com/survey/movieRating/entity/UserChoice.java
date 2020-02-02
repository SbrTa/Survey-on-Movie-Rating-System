package com.survey.movieRating.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class UserChoice implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Integer talkativeLevel;
    private Integer findFaultLevel;
    private Integer thoroughJobLevel;
    private Integer depressionLevel;
}
