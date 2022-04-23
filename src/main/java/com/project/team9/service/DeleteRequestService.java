package com.project.team9.service;

import com.project.team9.model.request.DeleteRequest;
import com.project.team9.repo.DeleteRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteRequestService {
    private final DeleteRequestRepository deleteRequestRepository;

    @Autowired
    public DeleteRequestService(DeleteRequestRepository deleteRequestRepository) {
        this.deleteRequestRepository = deleteRequestRepository;
    }

    public void addDeleteRequest(DeleteRequest deleteRequest) {
        deleteRequestRepository.save(deleteRequest);
    }
}
