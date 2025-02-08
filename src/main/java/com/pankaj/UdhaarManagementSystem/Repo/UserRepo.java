package com.pankaj.UdhaarManagementSystem.Repo;

import com.pankaj.UdhaarManagementSystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {




}
