package com.instabug.backend.challenge.application.controller;

import com.instabug.backend.challenge.application.dto.ApplicationResponse;
import com.instabug.backend.challenge.application.dto.CreateApplicationRequest;
import com.instabug.backend.challenge.application.dto.UpdateApplicationRequest;
import com.instabug.backend.challenge.application.entity.Application;
import com.instabug.backend.challenge.application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody CreateApplicationRequest request) {
        ApplicationResponse application = applicationService.createApplication(request);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<ApplicationResponse> getApplicationByToken(@PathVariable String token) {
        ApplicationResponse application = applicationService.getApplicationByToken(token);
        return application != null ?
                new ResponseEntity<>(application, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{token}")
    public ResponseEntity<ApplicationResponse> updateApplication(
            @PathVariable String token,
            @RequestBody UpdateApplicationRequest updateApplicationRequest) {
        ApplicationResponse application = applicationService.updateApplication(token, updateApplicationRequest);
        return ResponseEntity.ok(application);
    }

}
