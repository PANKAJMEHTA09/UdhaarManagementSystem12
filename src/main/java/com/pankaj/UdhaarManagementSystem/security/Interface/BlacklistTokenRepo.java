package com.pankaj.UdhaarManagementSystem.security.Interface;

import com.pankaj.UdhaarManagementSystem.Entity.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistTokenRepo extends JpaRepository<BlacklistToken, String> {
}
