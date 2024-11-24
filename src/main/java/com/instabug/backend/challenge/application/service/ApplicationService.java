package com.instabug.backend.challenge.application.service;

import com.instabug.backend.challenge.application.dto.ApplicationResponse;
import com.instabug.backend.challenge.application.dto.CreateApplicationRequest;
import com.instabug.backend.challenge.application.dto.UpdateApplicationRequest;
import com.instabug.backend.challenge.application.entity.Application;
import com.instabug.backend.challenge.application.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Transactional
    public ApplicationResponse createApplication(CreateApplicationRequest request) {
        Application application = new Application();
        application.setName(request.getName());
        application.setToken(generateApplicationToken());
        Application savedApplication = applicationRepository.save(application);
        return ApplicationResponse.builder().name(savedApplication.getName()).token(savedApplication.getToken()).chatsCount(savedApplication.getChatsCount()).build();
    }

    public ApplicationResponse getApplicationByToken(String token) {


        Application savedApplication = applicationRepository.findByToken(token);
        ;
        return ApplicationResponse.builder().name(savedApplication.getName()).token(savedApplication.getToken()).chatsCount(savedApplication.getChatsCount()).build();
    }

    private String generateApplicationToken() {
        return java.util.UUID.randomUUID().toString();
    }

    public ApplicationResponse updateApplication(String token, UpdateApplicationRequest request) {
        Application application = applicationRepository.findByToken(token);

        application.setName(request.getName());
        Application savedApplication = applicationRepository.save(application);
        return ApplicationResponse.builder().name(savedApplication.getName()).token(savedApplication.getToken()).chatsCount(savedApplication.getChatsCount()).build();
    }

}
