package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void testMapToBoards() {
        //GIVEN
        TrelloList trelloList = new TrelloList("1", "test list", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);
        TrelloBoard trelloBoard = new TrelloBoard("1","test", trelloLists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        List<TrelloBoardDto> trelloBoardDtoList= trelloMapper.mapToBoardsDto(trelloBoards);
        //WHEN
        String testedId = trelloBoardDtoList.get(0).getId();
        String testedName = trelloBoardDtoList.get(0).getName();
        List<TrelloListDto> testedTrelloList = trelloBoardDtoList.get(0).getLists();
        //THEN
        Assertions.assertEquals("1", testedId);
        Assertions.assertEquals("test", testedName);
        Assertions.assertEquals(1, testedTrelloList.size());

    }

    @Test
    void testMapToBoardsDto() {
        //GIVEN
        TrelloListDto trelloList = new TrelloListDto("1", "test list", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloList);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1","test", trelloListDtoList);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtoList);
        //WHEN
        String testedId = trelloBoards.get(0).getId();
        String testedName = trelloBoards.get(0).getName();
        List<TrelloList> testedTrelloList = trelloBoards.get(0).getLists();
        //THEN
        Assertions.assertEquals("1", testedId);
        Assertions.assertEquals("test", testedName);
        Assertions.assertEquals(1, testedTrelloList.size());

    }

    @Test
    void testMapToList() {
        //GIVEN
        TrelloListDto trelloList = new TrelloListDto("1", "test list", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloList);

        List<TrelloList> testedTrelloList = trelloMapper.mapToList(trelloListDtoList);
        //WHEN
        String testedId = testedTrelloList.get(0).getId();
        String testedName = testedTrelloList.get(0).getName();
        boolean testedIsClosed = testedTrelloList.get(0).isClosed();
        //THEN
        Assertions.assertEquals("1", testedId);
        Assertions.assertEquals("test list", testedName);
        Assertions.assertEquals(false, testedIsClosed);
    }

    @Test
    void testMapToListDto() {
        //GIVEN
        TrelloList trelloList = new TrelloList("1", "test list", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);

        List<TrelloListDto> testedTrelloListDto = trelloMapper.mapToListDto(trelloLists);
        //WHEN
        String testedId = testedTrelloListDto.get(0).getId();
        String testedName = testedTrelloListDto.get(0).getName();
        boolean testedIsClosed = testedTrelloListDto.get(0).isClosed();
        //THEN
        Assertions.assertEquals("1", testedId);
        Assertions.assertEquals("test list", testedName);
        Assertions.assertEquals(false, testedIsClosed);
    }

    @Test
    void testMapToCardDto() {
        //GIVEN
        TrelloCard trelloCard = new TrelloCard(
                "TestName", "TestDesc", "TestPos", "TestListId");

        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //WHEN
        String name = trelloCardDto.getName();
        String description = trelloCardDto.getDescription();
        String pos = trelloCardDto.getPos();
        String listId = trelloCardDto.getListId();
        //THEN
        Assertions.assertEquals("TestName", name);
        Assertions.assertEquals("TestDesc", description);
        Assertions.assertEquals("TestPos", pos);
        Assertions.assertEquals("TestListId", listId);

    }

    @Test
    void testMapToCard() {
        //GIVEN
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "TestName", "TestDesc", "TestPos", "TestListId");

        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //WHEN
        String name = trelloCard.getName();
        String description = trelloCard.getDescription();
        String pos = trelloCard.getPos();
        String listId = trelloCard.getListId();
        //THEN
        Assertions.assertEquals("TestName", name);
        Assertions.assertEquals("TestDesc", description);
        Assertions.assertEquals("TestPos", pos);
        Assertions.assertEquals("TestListId", listId);

    }
}
