package com.pankaj.UdhaarManagementSystem.Repo;

import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Customer> findByUserId(Long userId);

    List<Customer> findByName(String name);


}
