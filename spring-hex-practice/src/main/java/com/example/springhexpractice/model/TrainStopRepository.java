package com.example.springhexpractice.model;

import com.example.springhexpractice.controller.dto.request.TrainStop;

import com.example.springhexpractice.controller.dto.response.SelectStopResponse;
import com.example.springhexpractice.model.entity.Train_Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Repository
public interface TrainStopRepository extends JpaRepository<Train_Stop,String> {

    @Query(value = "SELECT * FROM train_stop WHERE train_uuid=?1 ORDER BY seq",nativeQuery = true)
    List<Train_Stop> findByUUID(String uuid);

    Train_Stop findByUuid(String uuid);

    @Query(value = "SELECT * FROM train_stop WHERE train_uuid=?1 and name=?2",nativeQuery = true)
    Train_Stop findByUuidAndStop(String uuid,String name);

    @Query(value = "SELECT seq FROM train_stop WHERE train_uuid=?1 and name=?2",nativeQuery = true)
    int findByUuidAndName(String uuid,String name);


    @Query(value = "select train_uuid FROM train_stop WHERE name=?1 ORDER BY time",nativeQuery = true)
    List<String> findByStop(String via);

}

