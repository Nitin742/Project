package com.Project.project.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class confirmationTokenService {


@Autowired
    private final confirmationTokenRepo confirmationTokenRepository;

    public void saveConfirmationToken(confirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<confirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }



}
