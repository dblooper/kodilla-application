package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class BadgesDtoTestSuite {

    @Test
    public void gettingAttachmentsTest() {
        //Given
        Trello trello = new Trello(2, 10);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);

        //When
        BadgesDto badgesDto = new BadgesDto(156987, attachmentsByType);

        //Then
        assertEquals(156987, badgesDto.getVotes());
        assertEquals(2, badgesDto.getAttachments().getTrello().getBoard());
        assertEquals(10, badgesDto.getAttachments().getTrello().getCard());
    }

}