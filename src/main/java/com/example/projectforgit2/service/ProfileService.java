package com.example.projectforgit2.service;

import com.example.projectforgit2.domain.Profile;
import com.example.projectforgit2.dto.requests.CreateProfileRequests;
import com.example.projectforgit2.exceptions.ProfileNotFoundExceptions;
import com.example.projectforgit2.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileService implements ProfileServiceImp {
    private final ProfileRepository profileRepository;

    private static final String PROFILE_NOT_FOUND = "Profile Not found";

    @Override
    public Profile createProfile(@NotNull CreateProfileRequests requests) {
        return profileRepository.save(new Profile(requests.getName(), requests.getSurname(), requests.getSex(), requests.getBirthday()));
    }

    @Override
    public Iterable<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Optional<Profile> findProfile(UUID uuid) {
        return profileRepository.findByUuid(uuid);
    }

    @Transactional
    @Override
    public void deleteProfile(UUID uuid) {
        Profile profile = profileRepository.findByUuid(uuid).orElseThrow(() -> new ProfileNotFoundExceptions(PROFILE_NOT_FOUND));
        profileRepository.delete(profile);
    }

    @Transactional
    @Override
    public Optional<Profile> updateProfile(CreateProfileRequests requests, UUID uuid) {
        Optional<Profile> profileOptional = profileRepository.findByUuid(uuid);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setName(requests.getName());
            profile.setSurname(requests.getSurname());
            profile.setSex(requests.getSex());
            profile.setBirthday(requests.getBirthday());
            return Optional.of(profileRepository.save(profile));
        } else
            return Optional.empty();
    }
}
