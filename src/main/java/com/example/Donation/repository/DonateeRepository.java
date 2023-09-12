package com.example.Donation.repository;

import com.example.Donation.model.Donatee;
import com.example.Donation.model.Donator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonateeRepository extends JpaRepository<Donatee, Long> {
    List<Donatee> findAllByDonator(Donator donator);
}
