package com.pankaj.UdhaarManagementSystem.security.Interface;

public interface TokenBlacklistServiceInterface {
    void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}
