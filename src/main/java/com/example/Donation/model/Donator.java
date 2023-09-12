package com.example.Donation.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Donator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "donator")
    private List<Donatee> donatedTo; // 기부자가 기부한 수혜자 목록

    // Getter and Setter methods
}



