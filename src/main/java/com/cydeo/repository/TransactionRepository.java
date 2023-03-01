package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

//    public static List<TransactionDTO> transactionList = new ArrayList<>();
//
//    public TransactionDTO save(TransactionDTO transaction){
//        transactionList.add(transaction);
//        return transaction;
//    }
//
//    public List<TransactionDTO> findAll() {
//        return transactionList;
//    }
//
//    public List<TransactionDTO> lastTransactions() {
//
//        return transactionList.stream().sorted(Comparator.comparing(TransactionDTO::getCreationDate).reversed())
//                .limit(10).collect(Collectors.toList());
//    }
//
//    public List<TransactionDTO> findTransactionListById(Long id) {
//      return  transactionList.stream().filter(transaction -> transaction.getSender().equals(id) || transaction.getReceiver().equals(id))
//                .collect(Collectors.toList());
//    }

    List<Transaction> findBySenderIdOrReceiverId(Long id);

    @Query(value="SELECT * FROM transactions ORDER BY creation_date DESC LIMIT 10",nativeQuery = true)
    List<Transaction> findLastTransaction();

//    @Query("SELECT t FROM Transaction t WHERE t.sender.id = ?1 OR t.receiver.id = ?1")
//    List<Transaction> findByReceiverorSenderId(@PathVariable("id")Long id);
}
