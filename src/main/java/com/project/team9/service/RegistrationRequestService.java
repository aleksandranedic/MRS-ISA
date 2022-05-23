package com.project.team9.service;

import com.project.team9.model.request.RegistrationRequest;
import com.project.team9.model.request.Request;
import com.project.team9.repo.RegistrationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationRequestService {
    private final RegistrationRequestRepository repository;

    @Autowired
    public RegistrationRequestService(RegistrationRequestRepository repository) {
        this.repository = repository;
    }

    public RegistrationRequest addRegistrationRequest(RegistrationRequest registrationRequest) {
        return repository.save(registrationRequest);
    }

    public List<RegistrationRequest> getAllUndeletedRegistrationRequests() {
        //TODO moras da izmenis da proveris da ti proveri i da ti registracija response ne bude prazno
//        ArrayList<RegistrationRequest> registrationRequests = new ArrayList<>();
//        for (RegistrationRequest registrationRequest:
//                repository.findAll()) {
//            if (!registrationRequest.getDeleted() && registrationRequest.getResponse().isEmpty())
//                registrationRequests.add(registrationRequest);
//        }
//        return registrationRequests;
        return repository.findAll().stream().filter(registrationRequest -> !registrationRequest.getDeleted()).collect(Collectors.toCollection(ArrayList::new));
    }

    public String deleteRegistrationRequest(String id) {
        RegistrationRequest registrationRequest = repository.findById(Long.parseLong(id)).orElse(null);
        if (registrationRequest == null)
            return "Zahtev nije uspešno odbijen";
        else {
            registrationRequest.setDeleted(true);
            repository.save(registrationRequest);
            return "Uspešno ste odbili registraciju";
        }
    }

    public RegistrationRequest getRegistrationRequest(String id) {
        return repository.findById(Long.parseLong(id)).orElse(null);
    }
}
