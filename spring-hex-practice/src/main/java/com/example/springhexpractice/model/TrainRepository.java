package com.example.springhexpractice.model;

import com.example.springhexpractice.model.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train,String> {
    @Query(value = "select * from train where train_no =?1",nativeQuery = true)
    Train findByTrainNo(Integer trainNO);

    Train findByUuid(String uuid);
}
