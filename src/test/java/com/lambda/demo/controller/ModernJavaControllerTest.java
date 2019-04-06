package com.lambda.demo.controller;

import com.lambda.demo.service.ModernJavaUserService;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModernJavaControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    ModernJavaUserService modernJavaUserService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders
                        .webAppContextSetup(webApplicationContext)
                        .alwaysDo(print())
                        .build();
    }

    @Test
    public void modernJavaControllerWithCommaTest() throws Exception {
        String name = "TEST1";

        when(modernJavaUserService.findUserEmailByName(name))
                .thenReturn(Arrays.asList("test1@test1.com", "test1@test2.com"));

        this.mockMvc.perform(get("/email/comma/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().string(Is.is("test1@test1.com,test1@test2.com")));

    }

    @Test
    public void modernJavaControllerWithColonTest() throws Exception {
        String name = "TEST1";

        when(modernJavaUserService.findUserEmailByName(name))
                .thenReturn(Arrays.asList("test1@test1.com", "test1@test2.com"));

        this.mockMvc.perform(get("/email/colon/{name}", "TEST1"))
                .andExpect(status().isOk())
                .andExpect(content().string(Is.is("test1@test1.com:test1@test2.com")));
    }

}
