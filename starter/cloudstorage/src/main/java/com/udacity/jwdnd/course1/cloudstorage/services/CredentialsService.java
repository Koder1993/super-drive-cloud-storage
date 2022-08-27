package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialsService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential) {
        credential.setKey(encryptionService.generateRandomKey());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credentialMapper.insertCredential(credential);
    }

    public int deleteCredential(Integer currentUserId, Integer noteId) {
        return credentialMapper.deleteCredential(currentUserId, noteId);
    }

    public int updateCredential(Credential credential) {
        Credential cred = credentialMapper.getCredential(credential.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), cred.getKey());
        return credentialMapper.updateCredential(new Credential(credential.getCredentialId(),
                credential.getUrl(), credential.getUsername(), credential.getKey(),
                encryptedPassword, credential.getUserId()));
    }

    public List<Credential> getCredentialsListForUser(Integer userId) {
        return credentialMapper.getCredentialsListForUser(userId);
    }

    public Credential getCredential(Integer currentUserId, Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }
}
