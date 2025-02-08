package com.pankaj.UdhaarManagementSystem.Repo;

import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {




}
