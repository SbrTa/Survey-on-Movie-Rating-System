package com.survey.movieRating.repository;

import com.survey.movieRating.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findFirstByActiveOrderByIdDesc(boolean active);
}
