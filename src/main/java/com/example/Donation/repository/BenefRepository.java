package com.example.Donation.repository;

import com.example.Donation.model.Benef;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BenefRepository extends JpaRepository<Benef, Long> {
    List<Benef> findByInfoContaining(String keyword);
}

