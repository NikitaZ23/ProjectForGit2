package com.example.projectforgit2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "\"profiles\"")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", sequenceName = "hibernate_sequence_pr", allocationSize = 1)
    private int id;

    @Column(name = "\"uuid\"", nullable = false)
    private UUID uuid = UUID.randomUUID();

    @Column(name = "\"name\"")
    String name;

    @Column(name = "\"surname\"")
    String surname;

    @Column(name = "\"sex\"")
    String sex;

    @Column(name = "\"birthday\"")
    LocalDate birthday;

    @CreatedDate
    @Column(name = "\"sign_up_date\"")
    protected LocalDateTime signUpDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "\"last_visit_date\"")
    protected LocalDateTime lastVisitDate = LocalDateTime.now();

    public Profile(String name, String surname, String sex, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.birthday = birthday;
    }
}
