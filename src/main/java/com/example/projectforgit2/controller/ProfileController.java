package com.example.projectforgit2.controller;

import com.example.projectforgit2.dto.ProfileDto;
import com.example.projectforgit2.dto.requests.CreateProfileRequests;
import com.example.projectforgit2.exceptions.ProfileNotFoundExceptions;
import com.example.projectforgit2.exceptions.ProfileNotFoundRestExceptions;
import com.example.projectforgit2.mapper.ProfileMapper;
import com.example.projectforgit2.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {
    public static final String PROFILE_NOT_FOUND = "Profile Not found";
    private final ProfileService profileService;

    private final ProfileMapper mapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<ProfileDto> getAll() {
        return mapper.map(profileService.findAll());
    }

    @GetMapping("/{profileId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProfileDto findProfile(@PathVariable("profileId") final UUID uuid) {
        final var profile = profileService.findProfile(uuid)
                .orElseThrow(() -> new ProfileNotFoundRestExceptions(PROFILE_NOT_FOUND));
        return mapper.map(profile);
    }

    @DeleteMapping("/{profileId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable("profileId") final UUID uuid) {
        try {
            profileService.deleteProfile(uuid);
        } catch (ProfileNotFoundExceptions exceptions) {
            throw new ProfileNotFoundRestExceptions(exceptions.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProfileDto createProfile(@Valid @RequestBody final CreateProfileRequests request) {
        return mapper.map(profileService.createProfile(request));
    }

    @PutMapping("/{profileId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProfileDto updateProfile(@Valid @RequestBody final CreateProfileRequests request,
                                    @PathVariable("profileId") final UUID uuid) {
        final var profile = profileService.updateProfile(request, uuid)
                .orElseThrow(() -> new ProfileNotFoundRestExceptions(PROFILE_NOT_FOUND));

        return mapper.map(profile);
    }


}
