package com.example.projectforgit2.service;

import com.example.projectforgit2.domain.Profile;
import com.example.projectforgit2.dto.requests.CreateProfileRequests;

import java.util.Optional;
import java.util.UUID;

public interface ProfileServiceImp {
    Iterable<Profile> findAll();

    Profile createProfile(CreateProfileRequests request);

    Optional<Profile> findProfile(UUID uuid);

    void deleteProfile(UUID uuid);

    Optional<Profile> updateProfile(CreateProfileRequests requests, UUID uuid);
}
