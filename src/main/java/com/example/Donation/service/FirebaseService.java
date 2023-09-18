package com.example.Donation.service;

import com.example.Donation.model.Benef;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

//import static sun.jvm.hotspot.oops.CellTypeState.ref;


@Service
public class FirebaseService {

    //private final DatabaseReference database;
    final FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference newBenefRef;

    public FirebaseService() {
        //database = ref.child("posts");
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("donation");
    }

    public void uploadData(Benef benef) {
        // Firebase Realtime Database에 데이터 업로드
        DatabaseReference benefRef = ref.child("benefs");
        newBenefRef = benefRef.push();
        newBenefRef.setValueAsync(benef);
        //newBenefRef.getDatabase();
    }

//    public Benef getData(String id) {
//        // Firebase Realtime Database에서 데이터 검색
//        DatabaseReference benefRef = database.getReference("donation/benefs");
//        //return benefRef.;
//        return database.child("beneficiaries").child(id).getValue(Benef.class);
//    }

    //    public void getData() {
//        ref = database.getReference("donation/benefs");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Benef benef = dataSnapshot.getValue(Benef.class);
//                System.out.println(benef);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
//    }
    public CompletableFuture<Benef> getDataAsync() {
        CompletableFuture<Benef> future = new CompletableFuture<>();
        DatabaseReference benefRef = database.getReference("donation/benefs");
        benefRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Benef benef = dataSnapshot.getValue(Benef.class);
                if (benef != null) {
                    future.complete(benef); // 데이터를 가져와서 CompletableFuture를 완료합니다.
                } else {
                    future.completeExceptionally(new RuntimeException("Data not found"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }
}
