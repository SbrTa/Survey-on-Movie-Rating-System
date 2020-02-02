package com.survey.movieRating.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Movie{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @Type(type="text")
    private String logo;
    private String type;
}
