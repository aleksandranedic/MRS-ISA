package com.project.team9.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FishingInstructorControllerTest {

    private static final String URL_PREFIX = "/fishinginstructor";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getFishingInstructors() throws Exception {
        mockMvc.perform(get(URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(15)))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem("Mirko")))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem("Grujin")));
    }

    @Test
    public void getFishingInstructorNames() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/names"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$.[*]").value(hasItem("Mirko Grujin")));
    }

    @Test
    public void getById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mirko"))
                .andExpect(jsonPath("$.lastName").value("Grujin"));
    }

    @Test
    public void getUserStat() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/getStat/15"))
                .andExpect(status().isOk());
    }
}