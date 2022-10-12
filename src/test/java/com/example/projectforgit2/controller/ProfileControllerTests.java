package com.example.projectforgit2.controller;

import com.example.projectforgit2.configuration.ProfileConfigurationTests;
import com.example.projectforgit2.domain.Profile;
import com.example.projectforgit2.dto.ProfileDto;
import com.example.projectforgit2.dto.requests.CreateProfileRequests;
import com.example.projectforgit2.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = ProfileConfigurationTests.class)
public class ProfileControllerTests {

    @MockBean
    ProfileService service;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DisplayName("Получение профиля")
    public void getProfileTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());

        Mockito.when(service.findProfile(Mockito.any())).thenReturn(Optional.of(profile));

        mockMvc.perform(
                        get("/profiles/7207d531-0e01-4cd0-ba0a-02f7c0c8fb2d")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.surname").value("Zar"));
    }

    @SneakyThrows
    @Test
    @DisplayName("Получение профилей")
    public void getProfilesTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());
        Profile profile2 = new Profile("Alex2", "Zar", "Male", LocalDate.now());

        Mockito.when(service.findAll()).thenReturn(Arrays.asList(profile, profile2));

        mockMvc.perform(
                        get("/profiles/")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(
                        new ProfileDto(profile.getId(), profile.getUuid(), profile.getName(), profile.getSurname(), profile.getSex(), profile.getBirthday(), profile.getSignUpDate(), profile.getLastVisitDate()),
                        new ProfileDto(profile2.getId(), profile2.getUuid(), profile2.getName(), profile2.getSurname(), profile2.getSex(), profile2.getBirthday(), profile2.getSignUpDate(), profile2.getLastVisitDate())))));
    }

    @Test
    @SneakyThrows
    @DisplayName("Проверка создания профиля")
    public void createThemeTest() {
        CreateProfileRequests request = new CreateProfileRequests("Alex", "Zar", "Male", LocalDate.now());

        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());
        Mockito.when(service.createProfile(Mockito.any())).thenReturn(profile);

        mockMvc.perform(
                        post("/profiles")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.surname").value("Zar"));

    }

    @Test
    @SneakyThrows
    @DisplayName("Проверка обновления профиля")
    public void updateThemeTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());
        Profile profile2 = new Profile("Alex2", "Zar2", "Male", LocalDate.now());

        Mockito.when(service.findProfile(Mockito.any())).thenReturn(Optional.of(profile));
        Mockito.when(service.updateProfile(Mockito.any(), Mockito.any())).thenReturn(Optional.of(profile2));

        mockMvc.perform(
                        put("/profiles/7207d531-0e01-4cd0-ba0a-02f7c0c8fb2d")
                                .content(objectMapper.writeValueAsString(new CreateProfileRequests("Alex", "Zar", "Male", LocalDate.now())))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex2"))
                .andExpect(jsonPath("$.surname").value("Zar2"));
    }

    @Test
    @SneakyThrows
    @DisplayName("Проверка удаления профиля")
    public void deleteThemeTest() {
        Profile profile = new Profile("Alex", "Zar", "Male", LocalDate.now());

        Mockito.when(service.findProfile(Mockito.any())).thenReturn(Optional.of(profile));

        mockMvc.perform(
                        delete("/profiles/7207d531-0e01-4cd0-ba0a-02f7c0c8fb2d"))
                .andExpect(status().isNoContent());
    }

    @SneakyThrows
    @Test
    @DisplayName("Получение профиля с ошибкой")
    public void getThemeTestExceptions() {
        CreateProfileRequests request = new CreateProfileRequests("Alex", "Zar", "Male", LocalDate.now());

        Mockito.when(service.findProfile(Mockito.any())).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> mockMvc.perform(
                                get("/profiles/7207d531-0e01-4cd0-ba0a-02f7c0c8fb2d")
                                        .content(objectMapper.writeValueAsString(request))
                                        .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
        );

        assertThat(thrown).isInstanceOf(AssertionError.class);
    }

    @Test
    @SneakyThrows
    @DisplayName("Проверка обновления профиля с ошибкой")
    public void updateThemeTestExceptions() {
        Mockito.when(service.findProfile(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(service.updateProfile(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> mockMvc.perform(
                        put("/profiles/7207d531-0e01-4cd0-ba0a-02f7c0c8fb2d")
                                .content(objectMapper.writeValueAsString(new CreateProfileRequests("Alex", "Zar", "Male", LocalDate.now())))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        );

        assertThat(thrown).isInstanceOf(AssertionError.class);
    }

    @Test
    @SneakyThrows
    @DisplayName("Проверка удаления профиля с ошибкой")
    public void deleteThemeTestExceptions() {
        Mockito.when(service.findProfile(Mockito.any())).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> mockMvc.perform(
                        delete("/profiles/7207d531-0e01-4cd0-ba0a-02f7c0c8fb2d"))
                .andExpect(status().isNotFound())
        );

        assertThat(thrown).isInstanceOf(AssertionError.class);
    }
}
