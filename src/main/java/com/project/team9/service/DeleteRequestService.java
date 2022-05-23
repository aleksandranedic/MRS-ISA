package com.project.team9.service;

import com.project.team9.model.request.DeleteRequest;
import com.project.team9.repo.DeleteRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<DeleteRequest> getAllDeletionRequests() {
        return deleteRequestRepository.findAll().stream().filter(deleteRequest -> !deleteRequest.getDeleted()).collect(Collectors.toCollection(ArrayList::new));
//        ArrayList<DeleteRequest> deleteRequests=new ArrayList<>();
//        for (DeleteRequest deleteRequest :
//                deleteRequestRepository.findAll()) {
//            if(!deleteRequest.getDeleted() && deleteRequest.getResponse().isEmpty())
//                deleteRequests.add(deleteRequest);
//        }
//        return deleteRequests;
    }

    public DeleteRequest getById(String requestId) {
        return deleteRequestRepository.getById(Long.parseLong(requestId));
    }
}
