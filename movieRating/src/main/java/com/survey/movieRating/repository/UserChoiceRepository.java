package com.survey.movieRating.repository;

import com.survey.movieRating.entity.UserChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChoiceRepository extends JpaRepository<UserChoice,Long> {
}
