package com.crud.tasks.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.CreatedTrelloCardDto;
import com.crud.tasks.trello.validator.TrelloVaildator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);

    @Autowired
    private TrelloService trelloService;
    @Autowired
    private TrelloMapper trelloMapper;
    @Autowired
    private TrelloVaildator trelloVaildator;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToTrelloBoardList(trelloService.fetchTrelloBoards());
        List<TrelloBoard> filteredBoards = trelloVaildator.valiDateTrelloBoards(trelloBoards);
        return trelloMapper.mapToTrelloBoardDtoList(filteredBoards);
    }

    public CreatedTrelloCardDto createCard(final TrelloCardDto trelloCardDto) {
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        trelloVaildator.validateCard(trelloCard);
        return trelloService.createdTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }
}
