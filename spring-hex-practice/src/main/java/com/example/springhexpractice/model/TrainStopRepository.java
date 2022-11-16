package com.example.springhexpractice.model;

import com.example.springhexpractice.model.entity.TrainStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainStopRepository extends JpaRepository<TrainStop,String> {

    @Query(value = "SELECT * FROM train_stop WHERE train_uuid=?1 ORDER BY seq",nativeQuery = true)
    List<TrainStop> findByUUID(String uuid);

    TrainStop findByUuid(String uuid);

    @Query(value = "SELECT * FROM train_stop WHERE train_uuid=?1 and name=?2",nativeQuery = true)
    TrainStop findByUuidAndStop(String uuid, String name);

    @Query(value = "SELECT seq FROM train_stop WHERE train_uuid=?1 and name=?2",nativeQuery = true)
    int findByUuidAndName(String uuid,String name);


    @Query(value = "select train_uuid FROM train_stop WHERE name=?1 ORDER BY time",nativeQuery = true)
    List<String> findByStop(String via);

}

