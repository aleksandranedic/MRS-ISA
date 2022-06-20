package com.project.team9.controller;

import com.project.team9.model.review.ClientReview;
import com.project.team9.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReviewControllerTest {

    private static final String URL_PREFIX = "/review";
    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void getResourcesReviews() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get(URL_PREFIX +  "/getReviews/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(2)))
        ;
    }

    @Test
    public void getVendorReviews() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get(URL_PREFIX + "/getVendorReviews/7"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(2)))
        ;
    }

    @Test
    @Transactional
    @Rollback(true)
    void addReview() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ClientReview clientReview = new ClientReview(
                1L,
                null,
                5,
                "Super!",
                3,
                ""
        );

        String json = TestUtil.json(clientReview);
        this.mockMvc.perform(post(URL_PREFIX + "/add").contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Test
    void getUserStat() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get(URL_PREFIX + "/getStat/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(0));
        ;
    }
}