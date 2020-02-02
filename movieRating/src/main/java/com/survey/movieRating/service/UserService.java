package com.survey.movieRating.service;

import com.survey.movieRating.entity.User;
import com.survey.movieRating.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService{
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(){
        User user = new User();
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        for (User user1: users){
            System.out.println(user1);
        }
    }

    public void saveFavRatingSystem(Long favSystem) {
        User user = getCurrentUser();
        user.setFavRating(favSystem);
        User savedUser = userRepository.save(user);
        System.out.println("\n\n\nUsers favourite rating system saved : ");
        System.out.println(savedUser);
    }

    public void saveUserEmail(String email) {
        User user = getCurrentUser();
        user.setEmail(email);
        User savedUser = userRepository.save(user);
        System.out.println("\n\n\nUsers email saved : ");
        System.out.println(savedUser);
    }
}
