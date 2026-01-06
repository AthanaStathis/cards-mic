package com.athanasios.cards.service;

import com.athanasios.cards.constants.CardConstants;
import com.athanasios.cards.dto.CardDto;
import com.athanasios.cards.entity.Card;
import com.athanasios.cards.exception.CardAlreadyExistsException;
import com.athanasios.cards.exception.ResourceNotFoundException;
import com.athanasios.cards.mapper.CardMapper;
import com.athanasios.cards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void createCard(String mobileNumber) {
        Optional<Card> optionalCards = cardRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        newCard.setCreatedAt(LocalDateTime.now());
        newCard.setCreatedBy("Anonymous");
        return newCard;
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Card",
                                "mobileNumber",
                                mobileNumber));
        return CardMapper.mapToCardDto(card, new CardDto());
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card = cardRepository
                .findByCardNumber(cardDto.getCardNumber())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Card",
                                "CardNumber",
                                cardDto.getCardNumber()));

        CardMapper.mapToCard(cardDto, card);
        cardRepository.save(card);
        return  true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card cards = cardRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(
                    () -> new ResourceNotFoundException(
                            "Card",
                            "mobileNumber",
                            mobileNumber));

        cardRepository.deleteById(cards.getCardId());
        return true;
    }
}
