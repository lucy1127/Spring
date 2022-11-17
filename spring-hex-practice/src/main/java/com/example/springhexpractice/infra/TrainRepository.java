package com.example.springhexpractice.infra;

import com.example.springhexpractice.domain.aggreate.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TrainRepository extends JpaRepository<Train,String> {
    @Query(value = "select * from train where train_no =?1",nativeQuery = true)
    Train findByTrainNo(Integer trainNO);

    Train findByUuid(String uuid);

    @Query(value = "select train_no,train_kind,name,time from train inner join train_stop on train.uuid = train_stop.train_uuid and  train.train_no =?1",nativeQuery = true)
    List<Map<String,Object>> findByTrainNoAndStopAndTime(Integer trainNO);
}
