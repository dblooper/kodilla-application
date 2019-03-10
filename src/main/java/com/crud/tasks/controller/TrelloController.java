package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClinet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloClinet trelloClinet;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() throws BoardNotFoundException{
        List<TrelloBoardDto> trelloBoards = trelloClinet.getTrelloBoards();
        trelloBoards.stream()
                .filter(board -> board.getId()!=null && board.getName() != null)
                .filter(board -> board.getName().contains("Kodilla"))
                .collect(Collectors.toList())
                .forEach(trelloBoardDto -> System.out.println("ID: " + trelloBoardDto.getId()
                                                                    + "\nBOARD NAME: " + trelloBoardDto.getName()
                                                                    + "\nURL: " + trelloBoardDto.getUrl()));
    }
}
