package com.pankaj.UdhaarManagementSystem.Repo;

import com.pankaj.UdhaarManagementSystem.Entity.Payments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payments,Long> {


    List<Payments> findByAmountGreaterThan(Double amount);

    List<Payments> findByCustomerId(Long customerId);



    Page<Payments> findByCustomer_NameContainingIgnoreCase(String name, Pageable pageable);

}
