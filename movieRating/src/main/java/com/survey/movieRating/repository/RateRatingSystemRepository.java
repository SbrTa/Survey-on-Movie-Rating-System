package com.survey.movieRating.repository;

import com.survey.movieRating.entity.RateRatingSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRatingSystemRepository extends JpaRepository<RateRatingSystem,Long> {
}
