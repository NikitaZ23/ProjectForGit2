package com.example.projectforgit2.repository;

import com.example.projectforgit2.domain.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepostory extends CrudRepository<Profile, Integer> {
    Optional<Profile> findByUuid(UUID uuid);
}
