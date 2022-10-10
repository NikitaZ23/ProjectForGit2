package com.example.projectforgit2.service;

import com.example.projectforgit2.domain.Profile;
import com.example.projectforgit2.dto.requests.CreateProfileRequests;
import com.example.projectforgit2.exceptions.ProfileNotFoundExceptions;
import com.example.projectforgit2.repository.ProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTests {
    @Mock
    ProfileRepository repository;

    @InjectMocks
    ProfileService service;

    @Test
    @DisplayName("Проверка создания профиля")
    public void createProfileTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());

        Mockito.when(repository.save(Mockito.any())).thenReturn(profile);

        Profile serviceProfile = service.createProfile(new CreateProfileRequests("Alex", "Zar", "Male", LocalDate.now()));

        assertThat(serviceProfile).isEqualTo(profile);
    }

    @Test
    @DisplayName("Проверка обновления репозитория")
    public void updateProfileTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());
        Profile profile2 = new Profile("Oleg", "Zar", "Male", LocalDate.now());

        Mockito.when(repository.findByUuid(Mockito.any())).thenReturn(Optional.of(profile));
        Mockito.when(repository.save(Mockito.any())).thenReturn(profile2);

        Optional<Profile> updateProfile = service.updateProfile(new CreateProfileRequests("Oleg", "Zar", "Male", LocalDate.now()), UUID.randomUUID());

        assertThat(updateProfile).isEqualTo(Optional.of(profile2));
    }

    @Test
    @DisplayName("Проверка получения профиля")
    public void findProfileTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());

        Mockito.when(repository.findByUuid(Mockito.any())).thenReturn(Optional.of(profile));

        Optional<Profile> serviceProfile = service.findProfile(UUID.randomUUID());

        assertThat(Optional.of(profile)).isEqualTo(serviceProfile);
    }

    @Test
    @DisplayName("Проверка получения всех профилей")
    public void findAllTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());
        Profile profile2 = new Profile("Oleg", "Pal", "Male", LocalDate.now());

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(profile, profile2));

        Iterable<Profile> profiles = service.findAll();

        assertThat(Arrays.asList(profile, profile2)).isEqualTo(profiles);
    }

    @Test
    @DisplayName("Проверка получения пустого репозитория")
    public void findAllEmptyTest() {
        Iterable<Profile> profiles = service.findAll();

        assertThat(profiles).isEmpty();
    }

    @Test
    @DisplayName("Проверка получения удаления профиля")
    public void deleteProfileTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());

        Mockito.when(repository.findByUuid(Mockito.any())).thenReturn(Optional.of(profile));

        service.deleteProfile(UUID.randomUUID());

        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("Проверка вызова ошибки при поиске по uuid профиля")
    public void findByUuidTestWithException() {
        Mockito.when(repository.findByUuid(Mockito.any())).thenThrow(ProfileNotFoundExceptions.class);

        Throwable throwable = catchThrowable(() -> service.findProfile(UUID.randomUUID()));

        assertThat(throwable).isInstanceOf(ProfileNotFoundExceptions.class);
    }
}
