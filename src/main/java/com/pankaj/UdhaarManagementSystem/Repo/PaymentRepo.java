package com.pankaj.UdhaarManagementSystem.Repo;

import com.pankaj.UdhaarManagementSystem.Entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payments,Long> {

}
