package com.example.Donation.repository;

import com.example.Donation.model.Benef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefRepository extends JpaRepository<Benef, Long> {
    // 여기에 추가적인 메서드를 정의할 수 있음
}
