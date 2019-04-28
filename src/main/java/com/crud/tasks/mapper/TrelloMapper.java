package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloMapper {

    public List<TrelloBoard> mapToTrelloBoardList(final List<TrelloBoardDto> trelloBoardDtoList) {
        return trelloBoardDtoList.stream()
                .map(trelloBoardDto -> new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToTrelloList(trelloBoardDto.getTrelloLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloBoardDto> mapToTrelloBoardDtoList(final List<TrelloBoard> trelloBoardList) {
        return trelloBoardList.stream()
                .map(trelloBoard -> new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(), mapToTrelloListDto(trelloBoard.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloList> mapToTrelloList(final List<TrelloListDto> trelloListDto) {
        return trelloListDto.stream()
                .map(trelloList -> new TrelloList(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(Collectors.toList());
    }

    public List<TrelloListDto> mapToTrelloListDto(final List<TrelloList> trelloList) {
        return trelloList.stream()
                .map(trelloListObject -> new TrelloListDto(trelloListObject.getId(), trelloListObject.getName(), trelloListObject.isClosed()))
                .collect(Collectors.toList());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPos(), trelloCard.getListId());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(), trelloCardDto.getPos(), trelloCardDto.getListId());
    }
}
