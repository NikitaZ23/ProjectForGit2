package com.example.projectforgit2.mapper;

import com.example.projectforgit2.domain.Profile;
import com.example.projectforgit2.dto.ProfileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto map(Profile profile);

    Iterable<ProfileDto> map(Iterable<Profile> profiles);
}
