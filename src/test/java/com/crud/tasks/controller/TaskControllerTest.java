package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.trello.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DbService dbService;

    @MockBean
    TaskMapper taskMapper;

    @Test
    void shouldFetchTasks() throws Exception{
        //Given
        List<TaskDto> dtos = List.of(new TaskDto(1L, "Test", "something"));
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(dtos);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("something")));
    }

    @Test
    void shouldGetTask() throws Exception{
        //Given
        Task task = new Task(1L, "Test", "content");
        TaskDto dto = new TaskDto(1L, "mapped test", "mapped content");
        when(taskMapper.mapToTaskDto(task)).thenReturn(dto);
        when(dbService.getTask(anyLong())).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/task/getTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("mapped test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("mapped content")));
    }

    @Test
    void shouldGetTasks() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();

        when(dbService.getAllTasks()).thenReturn(taskList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldUpdateTask() throws Exception{
        //Given
        Task task = new Task(1L, "Test", "content");
        TaskDto dto = new TaskDto(1L, "mapped test", "mapped content");
        when(taskMapper.mapToTaskDto(task)).thenReturn(dto);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-*")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("content")));
    }

    @Test
    void shouldCreateTask() throws Exception{
        //Given
        Task task = new Task(1L, "Test", "something");
        TaskDto dto = new TaskDto(1L, "mapped test", "mapped content");
        when(taskMapper.mapToTask(dto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test", "content");
        TaskDto taskDto = new TaskDto(1L, "Mapped test", "mapped content");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.getTask(task.getId())).thenReturn(Optional.ofNullable(task));
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .delete("/v1/task/deleteTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}