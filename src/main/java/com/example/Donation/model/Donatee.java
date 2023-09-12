package com.example.Donation.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Donatee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int money;
    private String date;

    @ManyToOne
    @JoinColumn(name = "donator_id")
    private Donator donator; // 수혜자가 어떤 기부자에게 받은 것인지를 나타내는 연관관계

//    @OneToMany(mappedBy = "donatee")
//    private Benef benef;

    @OneToMany(mappedBy = "donatee", fetch = FetchType.EAGER)
    private List<Letter> sendLetters;

    // Getter and Setter methods
}
