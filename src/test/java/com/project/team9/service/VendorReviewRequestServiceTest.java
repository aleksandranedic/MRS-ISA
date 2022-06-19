package com.project.team9.service;

import com.project.team9.model.request.VendorReviewRequest;
import com.project.team9.repo.VendorReviewRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
class VendorReviewRequestServiceTest {

    @Mock
    private VendorReviewRequestRepository repository;

    @InjectMocks
    private VendorReviewRequestService vendorReviewRequestService;

    @Test
    void delete() { //Student 3

        VendorReviewRequest vendorReviewRequest = new VendorReviewRequest(
                "",
                "",
                2L,
                3L,
                5,
                4L,
                false,
                false,
                1L
        );

        vendorReviewRequest.setId(1L);
        vendorReviewRequest.setDeleted(false);

        when(repository.getById(1L)).thenReturn(vendorReviewRequest);

        VendorReviewRequest test = vendorReviewRequestService.delete(1L);
        assertTrue(test.getDeleted());
    }
}