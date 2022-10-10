package com.example.projectforgit2.controller;

import com.example.projectforgit2.configuration.ProfileConfigurationTests;
import com.example.projectforgit2.domain.Profile;
import com.example.projectforgit2.service.ProfileService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = ProfileConfigurationTests.class)
public class ProfileControllerTests {

    @MockBean
    ProfileService service;

    @Autowired
    ObjectMapper mapper;

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
}
