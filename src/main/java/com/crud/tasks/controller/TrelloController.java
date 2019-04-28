package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.facade.TrelloFacade;
import com.crud.tasks.trello.client.CreatedTrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin(origins = "*")
public class TrelloController {

    @Autowired
    private TrelloFacade trelloFacade;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        List<TrelloBoardDto> trelloBoards = trelloFacade.fetchTrelloBoards();
        trelloBoards.stream()
                .filter(board -> board.getId()!=null && board.getName() != null)
                .filter(board -> board.getName().contains("Kodilla"))
                .collect(Collectors.toList())
                .forEach(trelloBoardDto -> {
                    System.out.println("ID: " + trelloBoardDto.getId()
                            + "\nBOARD NAME: " + trelloBoardDto.getName()
                            + "\nURL: " + trelloBoardDto.getUrl());
                    System.out.println("The board contains lists: ");
                    trelloBoardDto.getTrelloLists().forEach(trelloList ->
                            System.out.println("   ->NAME: " + trelloList.getName() +
                                               "\n     ID: " + trelloList.getId()));
                });
        return trelloBoards;
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }
}
