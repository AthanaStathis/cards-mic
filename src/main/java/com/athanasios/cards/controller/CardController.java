package com.athanasios.cards.controller;

import com.athanasios.cards.constants.CardsConstants;
import com.athanasios.cards.dto.CardDto;
import com.athanasios.cards.dto.ResponseDto;
import com.athanasios.cards.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/cards/",
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class CardController {

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("say-hello")
    String sayHello() {
        return "Hello World - Card Controller";
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestBody CardDto cardDto) {
        cardService.createCard(cardDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping()
    public ResponseEntity<CardDto> fetchCardDetails(@Valid @RequestParam String mobileNumber) {
        CardDto CardDto = cardService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(CardDto);
    }

    @PutMapping()
    public ResponseEntity<ResponseDto> updateAccountDetails(
            @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @RequestBody CardDto CardDto) {

        boolean isCardUpdated = cardService.updateCard(CardDto);
        if(isCardUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200,
                            CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417,
                            CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<ResponseDto> deleteCard(@Valid @RequestParam("mobileNumber") String mobileNumber) {
        boolean isCardDeleted = cardService.deleteCard(mobileNumber);
        if (isCardDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.MESSAGE_417_DELETE, CardsConstants.MESSAGE_417_DELETE));
        }

    }
}
