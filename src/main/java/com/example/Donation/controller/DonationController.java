package com.example.Donation.controller;

import com.example.Donation.model.Benef;
import com.example.Donation.model.Donatee;
import com.example.Donation.model.Donator;
import com.example.Donation.model.Letter;
import com.example.Donation.repository.DonateeRepository;
import com.example.Donation.repository.DonatorRepository;
import com.example.Donation.repository.LetterRepository;
import com.example.Donation.repository.BenefRepository;
import com.example.Donation.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DonationController {

    @Autowired
    private DonatorRepository donatorRepository;

    @Autowired
    private BenefRepository benefRepository;

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private DonateeRepository donateeRepository;

    // 후원자(Donator) 관련 엔드포인트

    @GetMapping("/donators")
    public List<Donator> getAllDonators() {
        return donatorRepository.findAll();
    }

    @GetMapping("/donators/{id}")
    public Donator getDonatorById(@PathVariable Long id) {
        return donatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + id));
    }

    @PostMapping("/donators")
    public Donator createDonator(@RequestBody Donator donator) {
        return donatorRepository.save(donator);
    }

    @PutMapping("/donators/{id}")
    public Donator updateDonator(@PathVariable Long id, @RequestBody Donator donatorDetails) {
        Donator donator = donatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + id));

        donator.setName(donatorDetails.getName());
        //donator.setEmail(donatorDetails.getEmail());

        return donatorRepository.save(donator);
    }

    @DeleteMapping("/donators/{id}")
    public void deleteDonator(@PathVariable Long id) {
        Donator donator = donatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + id));

        donatorRepository.delete(donator);
    }

    @PostConstruct
    void init() {
        Letter letter = new Letter();
        letter.setTitle("9월");
        letter.setContent("편지");
        List<Letter> letters = new ArrayList<>();
        letters.add(letter);
        letterRepository.save(letter);
        Letter letter2 = new Letter();
        letter2.setTitle("10월");
        letter2.setContent("안녕");
        letters.add(letter2);
        letterRepository.save(letter2);

        Donatee donatee = new Donatee();
        donatee.setName("gildong");
        donatee.setMoney(1000);

        donatee.setSendLetters(letters);
        donateeRepository.save(donatee);

        Donator donator = new Donator();
        donator.setName("Hong");
        List<Donatee> donatees = new ArrayList<>();
        donatees.add(donatee);
        donator.setDonatedTo(donatees);
        donatorRepository.save(donator);
    }

    @GetMapping("/donators/{id}/donatees")
    public List<Donatee> getAllDonatees() {
        return donateeRepository.findAll();
    }

    @PostMapping("/donators/{id}/donatees")
    public Donatee createDonatee(@RequestBody Donatee donatee) {
        return donateeRepository.save(donatee);
    }

    @GetMapping("/donators/{donator_id}/donatees/{donatee_id}")
    public List<Letter> getAllLetters() {
        return letterRepository.findAll();
    }

    @PostMapping("/donators/{donator_id}/donatees/{donatee_id}")
    public Letter createLetter(@RequestBody Letter letter) {
        return letterRepository.save(letter);
    }

    @GetMapping("/benefs")
    public List<Benef> getAllBeneficiaries() {
        return benefRepository.findAll();
    }

    @GetMapping("/benefs/{id}")
    public Benef getBeneficiaryById(@PathVariable Long id) {
        return benefRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + id));
    }

    @PostMapping("/benefs")
    public Benef createBeneficiary(@RequestBody Benef benef) {
        return benefRepository.save(benef);
    }

    @PutMapping("/benefs/{id}")
    public Benef updateBeneficiary(@PathVariable Long id, @RequestBody Benef benefDetails) {
        Benef beneficiary = benefRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + id));

        // Update beneficiary details here
        // beneficiary.setName(benefDetails.getName());
        // ...

        return benefRepository.save(beneficiary);
    }

    @DeleteMapping("/benefs/{id}")
    public void deleteBeneficiary(@PathVariable Long id) {
        Benef beneficiary = benefRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + id));

        benefRepository.delete(beneficiary);
    }


    // 수혜자(Benef) 관련 엔드포인트

    // Letter 엔터티 관련 엔드포인트

    // 예외 처리를 위한 핸들러 등을 추가할 수 있습니다.
}
