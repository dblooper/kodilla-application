package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.CreatedTrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> TrelloBoards = new ArrayList<>();
        TrelloBoards.add(new TrelloBoardDto("1", "my_task1", trelloLists));
        TrelloBoards.add(new TrelloBoardDto("2", "my_task2", trelloLists));
        when(trelloClient.getTrelloBoards()).thenReturn(TrelloBoards);

        //When
        List<TrelloBoardDto> fetchedBoardList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(2, fetchedBoardList.size());
        assertEquals("my_task2", fetchedBoardList.get(1).getName());
    }

    @Test
    public void shouldCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("My_card", "Testing_card", "Empty", "25");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1","My_card", "test_URL");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");

        //When
        CreatedTrelloCardDto createdTrelloCardDto = trelloService.createdTrelloCard(trelloCardDto);

        //Then
        assertNotNull(createdTrelloCardDto);
        assertEquals("1", createdTrelloCardDto.getId());
        assertEquals("My_card", createdTrelloCardDto.getName());
        verify(emailService, times(1)).send(any());
    }
}