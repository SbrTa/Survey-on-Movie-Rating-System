package com.survey.movieRating.service;

import com.survey.movieRating.entity.UserChoice;
import com.survey.movieRating.repository.UserChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserChoiceService extends BaseService{

    @Autowired
    private UserChoiceRepository userChoiceRepository;

    public void saveUserChoice(int talkativeLevel, int findFaultLevel, int thoroughJobLevel, int depressionLevel) {
        UserChoice userChoice = new UserChoice();
        userChoice.setUserId(getCurrentUser().getId());
        userChoice.setTalkativeLevel(talkativeLevel);
        userChoice.setFindFaultLevel(findFaultLevel);
        userChoice.setThoroughJobLevel(thoroughJobLevel);
        userChoice.setDepressionLevel(depressionLevel);
        UserChoice choice = userChoiceRepository.save(userChoice);
        System.out.println(choice);
    }
}
