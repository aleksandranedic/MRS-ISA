package com.project.team9.service;

import com.project.team9.model.request.VendorReviewRequest;
import com.project.team9.repo.VendorReviewRequestRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class VendorReviewRequestServiceTest {

    @Mock
    private VendorReviewRequestRepository repository;

    @InjectMocks
    private VendorReviewRequestService vendorReviewRequestService;

    private static VendorReviewRequest vendorReviewRequest;

    @BeforeAll
    public static void setUp() {
        vendorReviewRequest = new VendorReviewRequest(
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
    }


    @Test
    @Transactional
    @Rollback(true)
    void delete() { //Student 3
        when(repository.getById(1L)).thenReturn(vendorReviewRequest);
        VendorReviewRequest test = vendorReviewRequestService.delete(1L);
        assertTrue(test.getDeleted());
    }

    @Test
    void getAllVendorReviews() {
        vendorReviewRequest.setDeleted(false);
        when(repository.findAll()).thenReturn(Arrays.asList(vendorReviewRequest, vendorReviewRequest));
        List<VendorReviewRequest> test = vendorReviewRequestService.getAllVendorReviews();
        assertEquals(2, test.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getById() {
        when(repository.getById(1L)).thenReturn(vendorReviewRequest);
        VendorReviewRequest test = vendorReviewRequestService.getById(1L);
        assertEquals(test.getResourceId(), vendorReviewRequest.getResourceId());
        assertEquals(test.getClientId(), vendorReviewRequest.getClientId());
        verify(repository, times(1)).getById(1L);


    }
}