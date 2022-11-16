package com.example.springhexpractice.model;

import com.example.springhexpractice.model.entity.TrainTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainTicketRepository extends JpaRepository<TrainTicket,String> {
    @Query(value = "select * FROM train_ticket WHERE train_uuid=?1",nativeQuery = true)
    TrainTicket findByTicketNo(String uid);
}
