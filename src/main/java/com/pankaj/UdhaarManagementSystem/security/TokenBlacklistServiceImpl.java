package com.pankaj.UdhaarManagementSystem.security;

import com.pankaj.UdhaarManagementSystem.Entity.BlacklistToken;
import com.pankaj.UdhaarManagementSystem.security.Interface.BlacklistTokenRepo;
import com.pankaj.UdhaarManagementSystem.security.Interface.TokenBlacklistServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistServiceInterface {
    private static final String BLACKLIST_PREFIX = "blacklist:";
    private final BlacklistTokenRepo blacklistTokenRepo;



    @Override
    public void addToBlacklist(String token) {
        blacklistTokenRepo.save(new BlacklistToken(BLACKLIST_PREFIX + token));
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklistTokenRepo.existsById(BLACKLIST_PREFIX + token);
    }
}
