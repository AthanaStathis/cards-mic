package com.athanasios.cards.service;

import com.athanasios.cards.dto.CardDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;


public interface CardService {
    void createCard(String mobileNumber);
    CardDto fetchCard(String mobileNumber);
    boolean updateCard(CardDto cardDto);
    boolean deleteCard(String mobileNumber);
}
