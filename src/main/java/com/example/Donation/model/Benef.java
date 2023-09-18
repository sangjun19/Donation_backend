package com.example.Donation.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Benef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String info;
    private String title;
    private int per;

    @ElementCollection
    @CollectionTable(name = "benef_hashtags", joinColumns = @JoinColumn(name = "benef_id"))
    @Column(name = "hashtag")
    private List<String> hashtags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "benef_hashtags2", joinColumns = @JoinColumn(name = "benef_id"))
    @Column(name = "hashtag2")
    private List<String> hashtags2 = new ArrayList<>();

    public Benef() {

    }

    public Benef(String name, String info, String title, int per) {
        this.name = name;
        this.info = info;
        this.title = title;
        this.per = per;
    }

    public void addHashtag(String hashtag) {
        this.hashtags.add(hashtag);
    }

    public List<String> getHashtags() {
        return this.hashtags;
    }

    public void addHashtag2(String hashtag) {
        this.hashtags2.add(hashtag);
    }

    public List<String> getHashtags2() {
        return this.hashtags2;
    }

    // Getter and Setter methods
}
