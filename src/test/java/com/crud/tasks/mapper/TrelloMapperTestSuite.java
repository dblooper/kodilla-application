package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void shouldReturnTrelloBoardList() {
        //Given
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "Test trello board1", new ArrayList<>());
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "Test trello board2", new ArrayList<>());
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto1);
        trelloBoardDtoList.add(trelloBoardDto2);
        //When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToTrelloBoardList(trelloBoardDtoList);
        //Then
        assertEquals(2, trelloBoardList.size());
        assertEquals("Test trello board2", trelloBoardList.get(1).getName());

    }

    @Test
    public void shouldReturnTrelloBoardDtioList() {
        //Given
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "Test trello board1", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "Test trello board2", new ArrayList<>());
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard1);
        trelloBoardList.add(trelloBoard2);
        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToTrelloBoardDtoList(trelloBoardList);
        //Then
        assertEquals(2, trelloBoardList.size());
        assertEquals("Test trello board2", trelloBoardList.get(1).getName());
    }

    @Test
    public void shouldReturnTrelloList() {
        //Given
        TrelloListDto trelloDtoList1 = new TrelloListDto("1", "Test list1", false);
        TrelloListDto trelloDtoList2 = new TrelloListDto("2", "Test list2", false);
        List<TrelloListDto> listOfTrelloListDto = new ArrayList<>();
        listOfTrelloListDto.add(trelloDtoList1);
        listOfTrelloListDto.add(trelloDtoList2);
        //When
        List<TrelloList> listOfTrelloList = trelloMapper.mapToTrelloList(listOfTrelloListDto);
        //Then
        assertEquals(2, listOfTrelloList.size());
        assertEquals("2", listOfTrelloList.get(1).getId());
    }

    @Test
    public void shouldReturnTrelloListDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "Test list1", false);
        TrelloList trelloList2 = new TrelloList("2", "Test list2", false);
        List<TrelloList> listOfTrelloList = new ArrayList<>();
        listOfTrelloList.add(trelloList1);
        listOfTrelloList.add(trelloList2);
        //When
        List<TrelloListDto> listOfTrelloListDto = trelloMapper.mapToTrelloListDto(listOfTrelloList);
        //Then
        assertEquals(2, listOfTrelloListDto.size());
        assertEquals("2", listOfTrelloListDto.get(1).getId());
    }

    @Test
    public void shouldReturnTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test name1", "Test description1", "testPos1", "125");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("Test description1", trelloCard.getDescription());
        assertEquals("125", trelloCard.getListId());
    }

    @Test
    public void shouldReturnTrelloCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Test name1", "Test description1", "testPos1", "125");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("Test description1", trelloCardDto.getDescription());
        assertEquals("125", trelloCardDto.getListId());
    }
}