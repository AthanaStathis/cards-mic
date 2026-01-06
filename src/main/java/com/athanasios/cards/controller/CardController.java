package com.athanasios.cards.controller;

import com.athanasios.cards.constants.CardConstants;
import com.athanasios.cards.dto.CardDto;
import com.athanasios.cards.dto.ResponseDto;
import com.athanasios.cards.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/cards/",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
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
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam @Pattern(regexp="(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits") String mobileNumber) {
        cardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardConstants.STATUS_201, CardConstants.MESSAGE_201)); }

    @GetMapping()
    public ResponseEntity<CardDto> fetchCardDetails(@Valid @RequestParam String mobileNumber) {
        CardDto CardDto = cardService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(CardDto);
    }

    @PutMapping()
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CardDto CardDto) {
        boolean isCardUpdated = cardService.updateCard(CardDto);
        if(isCardUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200,
                            CardConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.STATUS_417,
                            CardConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<ResponseDto> deleteCard(@Valid @RequestParam("mobileNumber") String mobileNumber) {
        boolean isCardDeleted = cardService.deleteCard(mobileNumber);
        if (isCardDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.MESSAGE_417_DELETE, CardConstants.MESSAGE_417_DELETE));
        }

    }
}
