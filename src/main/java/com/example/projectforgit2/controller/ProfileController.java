package com.example.projectforgit2.controller;

import com.example.projectforgit2.domain.Profile;
import com.example.projectforgit2.repository.ProfileRepostory;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {
    ProfileRepostory profileRepostory;

    @PostMapping
    public Profile createProfile(){
        return profileRepostory.save(new Profile("a", "b", "c", LocalDate.now()));
    }
}
