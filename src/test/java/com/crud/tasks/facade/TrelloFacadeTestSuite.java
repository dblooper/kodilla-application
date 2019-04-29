package com.crud.tasks.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.CreatedTrelloCardDto;
import com.crud.tasks.trello.validator.TrelloVaildator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTestSuite {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloVaildator trelloVaildator;

    @Mock
    private TrelloMapper trelloMapper;

    @Autowired
    private TrelloVaildator cardValidator;

    @Test
    public void shouldFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToTrelloBoardList(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToTrelloBoardDtoList(anyList())).thenReturn(new ArrayList<>());
        when(trelloVaildator.valiDateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task","testUrl", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "my_task", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToTrelloBoardList(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToTrelloBoardDtoList(anyList())).thenReturn(trelloBoards);
        when(trelloVaildator.valiDateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos= trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1,trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());
            assertEquals("testUrl", trelloBoardDto.getUrl());
            assertEquals(1, trelloBoardDto.getTrelloLists().size());

            trelloBoardDto.getTrelloLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldFetchTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("My_card", "Testing_card", "Empty", "25");
        TrelloCard trelloCard = new TrelloCard("My_card", "Testing_card", "Empty", "25");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1","My_card", "test_URL");

        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createdTrelloCard(trelloCardDto)).thenReturn(createdCard);

        //When
        CreatedTrelloCardDto testedCreatingTrelloCard = trelloFacade.createCard(trelloCardDto);

        //Then
        assertNotNull(testedCreatingTrelloCard);
        assertEquals("1", testedCreatingTrelloCard.getId());
        assertEquals("My_card", testedCreatingTrelloCard.getName());
    }

}