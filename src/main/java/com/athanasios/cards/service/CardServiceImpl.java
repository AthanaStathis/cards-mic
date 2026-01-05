package com.athanasios.cards.service;

import com.athanasios.cards.repository.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl {

    private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


}
