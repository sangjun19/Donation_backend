package com.example.Donation.service;

import com.example.Donation.exception.ResourceNotFoundException;
import com.example.Donation.model.Benef;
import com.example.Donation.model.Donator;
import com.example.Donation.repository.BenefRepository;
import com.example.Donation.repository.DonatorRepository;
import com.example.Donation.repository.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private DonatorRepository donatorRepository;

    @Autowired
    private BenefRepository benefRepository;

    @Autowired
    private LetterRepository letterRepository;

    public List<Donator> getAllDonators() {
        return donatorRepository.findAll();
    }

    public Donator getDonatorById(Long id) {
        Optional<Donator> donator = donatorRepository.findById(id);
        return donator.orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + id));
    }

    public Donator createDonator(Donator donator) {
        return donatorRepository.save(donator);
    }

    public Donator updateDonator(Long id, Donator donatorDetails) {
        Donator donator = getDonatorById(id);
        donator.setName(donatorDetails.getName());
        //donator.setEmail(donatorDetails.getEmail());
        return donatorRepository.save(donator);
    }

    public void deleteDonator(Long id) {
        Donator donator = getDonatorById(id);
        donatorRepository.delete(donator);
    }

    public List<Benef> getAllBeneficiaries() {
        return benefRepository.findAll();
    }

    public Benef getBeneficiaryById(Long id) {
        return benefRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + id));
    }

    public Benef createBeneficiary(Benef benef) {
        return benefRepository.save(benef);
    }

    public Benef updateBeneficiary(Long id, Benef benefDetails) {
        Benef beneficiary = benefRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + id));

        // Update beneficiary details here
        // beneficiary.setName(benefDetails.getName());
        // ...

        return benefRepository.save(beneficiary);
    }

    public void deleteBeneficiary(Long id) {
        Benef beneficiary = benefRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + id));

        benefRepository.delete(beneficiary);
    }

    // 다른 비즈니스 로직을 추가할 수 있음
}
