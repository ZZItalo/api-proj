package com.italo.task_api.advice;

import com.italo.task_api.controller.TaskController;
import com.italo.task_api.dto.TaskDto;
import com.italo.task_api.service.TaskManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class ExceptionHandlingTest {

    @Autowired
    private MockMvc mmvc;

    @Autowired
    private ObjectMapper om;

    @MockitoBean
    private TaskManagementService tms;

    @Test
    void shouldReturnExceptionMessage() throws Exception {
        TaskDto t = new TaskDto();

        String taskJson = om.writeValueAsString(t);

        mmvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
