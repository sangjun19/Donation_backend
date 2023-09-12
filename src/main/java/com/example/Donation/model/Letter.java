package com.example.Donation.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.example.Donation.model.Donatee;

@Entity
@Getter
@Setter
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String date;

    @ManyToOne
    @JoinColumn(name = "donatee_id")
    private Donatee donatee; // Letter가 어떤 수혜자에게 속하는지 나타내는 연관관계

    // Getter and Setter methods
}

