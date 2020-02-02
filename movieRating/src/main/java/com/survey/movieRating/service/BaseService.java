package com.survey.movieRating.service;

import com.survey.movieRating.entity.User;
import com.survey.movieRating.repository.UserRepository;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class BaseService {
    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(){
        return userRepository.findFirstByActiveOrderByIdDesc(true);
    }


    public String convertMultipartToBase64Img(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        return "data:image/*;base64,"+Base64.getEncoder().encodeToString(fileContent);
    }

    public String convertInputStreamToBase64Img(InputStream input) throws IOException {
        byte[] fileContent = IOUtils.toByteArray(input);
        return "data:image/*;base64,"+Base64.getEncoder().encodeToString(fileContent);
    }
}
