package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrelloServiceTest {
    @InjectMocks
    TrelloService trelloService;

    @Mock
    AdminConfig adminConfig;

    @Mock
    TrelloClient trelloClient;

    @Mock
    SimpleEmailService emailService;

    @Test
    void testFetchTrelloBoards(){
        //Given
        TrelloBoardDto dto = new TrelloBoardDto("1", "testBoard", new ArrayList<>());
        List<TrelloBoardDto> list = new ArrayList<>();
        list.add(dto);
        when(trelloClient.getTrelloBoards()).thenReturn(list);

        //When
        List<TrelloBoardDto> testList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, testList.size());
    }
}