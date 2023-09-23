package com.example.Donation.controller;

import com.example.Donation.model.*;
import com.example.Donation.repository.DonateeRepository;
import com.example.Donation.repository.DonatorRepository;
import com.example.Donation.repository.LetterRepository;
import com.example.Donation.repository.BenefRepository;
import com.example.Donation.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@CrossOrigin(origins = "https://web-hackerton-iciy2almupbz2s.sel5.cloudtype.app")
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

    private List<Donatee>donatees = new ArrayList<>();;
    // 후원자(Donator) 관련 엔드포인트
    @PostConstruct
    void init() {
        Data data = new Data();

        Donator donator = new Donator();
        donator.setName("상수우");
        donator.setMoney(250000);

        Benef[] benefs = new Benef[11];
        for(int i=0;i<11;i++) {
            benefs[i] = new Benef();
            benefs[i].setName(data.getName(i));
            benefs[i].setInfo(data.getInfo(i));
            benefs[i].setTitle(data.getTitle(i));
            benefs[i].setAge(data.getAge(i));
            benefs[i].setGender(data.getGender(i));
            benefs[i].setCondition(data.getCondition(i));
            benefs[i].setGot_money(data.getGetMoney(i));
            benefs[i].setNeed_money(data.getNeedMoney(i));
            benefs[i].setHashtags(data.getHashtag1(i));
            benefs[i].setHashtags2(data.getHashtag2(i));
            benefs[i].setPer(data.getPer(i));
            benefRepository.save(benefs[i]);
        }

        Donatee donatee = new Donatee();
        donatee.setName("이재민");
        donatee.setDate("2023년 5월 12일");
        donatee.setMoney(100000);
        donatee.setImgOne(1);

        Donatee donatee2 = new Donatee();
        donatee2.setName("김수현");
        donatee2.setDate("2023년 6월 26일");
        donatee2.setMoney(150000);
        donatee2.setImgOne(2);

        donatees.add(donatee);
        donatees.add(donatee2);
        donateeRepository.save(donatee);
        donateeRepository.save(donatee2);

        donator.setDonatedTo(donatees);
        donatorRepository.save(donator);
    }
    @GetMapping("/donators") //기증자들 가져오기
    public List<Donator> getAllDonators() {
        return donatorRepository.findAll();
    }

    @GetMapping("/donators/{id}") //특정 기증자 가져오기(/donators/1)
    public Donator getDonatorById(@PathVariable Long id) {
        List<Donatee> donateeList = donateeRepository.findAll();
        Donator donator = donatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + id));
        int money = 0;
        for(Donatee donatee : donateeList) {
            money += donatee.getMoney();
        }
        donator.setMoney(money);
        return donatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + id));
    }

//    @PostMapping("/donators")
//    public Donator createDonator(@RequestBody Donator donator) {
//        return donatorRepository.save(donator);
//    }

//    @PutMapping("/donators/{id}")
//    public Donator updateDonator(@PathVariable Long id, @RequestBody Donator donatorDetails) {
//        Donator donator = donatorRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + id));
//
//        donator.setName(donatorDetails.getName());
//        //donator.setEmail(donatorDetails.getEmail());
//
//        return donatorRepository.save(donator);
//    }

//    @DeleteMapping("/donators/{id}")
//    public void deleteDonator(@PathVariable Long id) {
//        Donator donator = donatorRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + id));
//
//        donatorRepository.delete(donator);
//    }
    @GetMapping("/donators/{id}/donatees") //기증자가 기부하고 있는 수혜자들 가져오기
    public List<Donatee> getAllDonatees() {
        return donateeRepository.findAll();
    }

//    @PostMapping("/donators/{id}/donatees")
//    public Donatee createDonatee(@RequestBody Donatee donatee) {
//        return donateeRepository.save(donatee);
//    }

//    @GetMapping("/donators/{donator_id}/donatees/{donatee_id}")
//    public List<Letter> getAllLetters() {
//        return letterRepository.findAll();
//    }

//    @PostMapping("/donators/{donator_id}/donatees/{donatee_id}")
//    public Letter createLetter(@RequestBody Letter letter) {
//        return letterRepository.save(letter);
//    }

    @GetMapping("/benefs") //수혜자 정보들 가져오기
    public ResponseEntity<List<Benef>> getFilteredBeneficiaries(@RequestParam(value = "keyword") String keyword) {
        List<Benef> benefs = benefRepository.findByInfoContaining(keyword);

        if (!benefs.isEmpty()) {
            return ResponseEntity.ok(benefs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/donators/{donator_id}/benefs/{benef_id}/addDonatee") //수혜자 기부시 내가 기증하고 있는 수혜자 목록에 추가 및 per 업데이트
    public ResponseEntity<Benef> updateBeneficiaryPer(@PathVariable Long donator_id, @PathVariable Long benef_id, @RequestParam int money) {
        Benef beneficiary = benefRepository.findById(benef_id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + benef_id));

        // Update the 'per' field
        beneficiary.setGot_money(beneficiary.getGot_money() + money);
        int got = beneficiary.getGot_money();
        int need = beneficiary.getNeed_money();
        beneficiary.setPer(((float) got / need) * 100);

        Donatee don = new Donatee();
        don.setName(beneficiary.getName());

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일");
        String formattedDate = now.format(formatter);

        don.setDate(formattedDate);
        don.setImgOne(Math.toIntExact(benef_id));
        System.out.println(don.getImgOne());
        don.setMoney(money);
        donateeRepository.save(don);

        donatees.add(don);
        Donator donator = donatorRepository.findById(donator_id)
                .orElseThrow(() -> new ResourceNotFoundException("Donator not found with id " + donator_id));
        donator.setDonatedTo(donatees);
        donatorRepository.save(donator);

        Benef updatedBeneficiary = benefRepository.save(beneficiary);
        return ResponseEntity.ok(updatedBeneficiary);
    }

//    @PostMapping("/benefs")
//    public Benef createBeneficiary(@RequestBody Benef benef) {
//        return benefRepository.save(benef);
//    }

//    @PutMapping("/benefs/{id}")
//    public ResponseEntity<Benef> updateBeneficiary(@PathVariable Long id) {
//        Benef beneficiary = benefRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + id));
//
//
//
//        Benef updatedBeneficiary = benefRepository.save(beneficiary);
//        return ResponseEntity.ok(updatedBeneficiary);
//    }

//    @DeleteMapping("/benefs/{id}")
//    public void deleteBeneficiary(@PathVariable Long id) {
//        Benef beneficiary = benefRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id " + id));
//
//        benefRepository.delete(beneficiary);
//    }
}
