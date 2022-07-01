package com.nextgendevs.hanchor.service.impl;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.io.entity.QuoteEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import com.nextgendevs.hanchor.io.repository.QuoteRepository;
import com.nextgendevs.hanchor.io.repository.UserRepository;
import com.nextgendevs.hanchor.service.QuoteService;
import com.nextgendevs.hanchor.shared.dto.QuoteDto;
import com.nextgendevs.hanchor.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuoteRepository quoteRepository;

    @Override
    public QuoteDto createQuote(String userId, QuoteDto quoteDto) {
        checkUserEntity(userId);
        QuoteEntity quoteEntity = new ModelMapper().map(quoteDto, QuoteEntity.class);

        QuoteEntity quote = quoteRepository.save(quoteEntity);

        return new ModelMapper().map(quote, QuoteDto.class);
    }

    @Override
    public QuoteDto getQuote(String userId, long quoteId) {
        checkUserEntity(userId);
        QuoteEntity quoteEntity = checkQuoteEntity(quoteId);

        return new ModelMapper().map(quoteEntity, QuoteDto.class);
    }

    @Override
    public QuoteDto updateQuote(String userId, long quoteId, QuoteDto quoteDto) {
        checkUserEntity(userId);
        QuoteEntity quoteEntity = checkQuoteEntity(quoteId);

        QuoteEntity newQuote = new ModelMapper().map(quoteDto, QuoteEntity.class);
        newQuote.setId(quoteEntity.getId());

        QuoteEntity quote = quoteRepository.save(newQuote);

        return new ModelMapper().map(quote, QuoteDto.class);
    }

    @Override
    public List<QuoteDto> getQuotes(String userId, int page, int limit) {
        List<QuoteDto> returnValue = new ArrayList<>();
        checkUserEntity(userId);

        if (page > 0) page = page - 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<QuoteEntity> pagedEntities = quoteRepository.findAll(pageableRequest);

        List<QuoteEntity> entityList = pagedEntities.getContent();
        for (QuoteEntity entity : entityList) {
            returnValue.add(new ModelMapper().map(entity, QuoteDto.class));
        }

        return returnValue;
    }

    @Override
    public void deleteQuote(String userId, long quoteId) {
        checkUserEntity(userId);
        QuoteEntity quoteEntity = checkQuoteEntity(quoteId);

        quoteRepository.delete(quoteEntity);
    }

    private QuoteEntity checkQuoteEntity(long quoteId) {
        QuoteEntity quoteEntity = quoteRepository.findById(quoteId);
        if (quoteEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return quoteEntity;
    }

    private void checkUserEntity(String query) {
        UserEntity userEntity = new UserEntity();
        if (query.contains("@")) {
            userEntity = userRepository.findUserEntitiesByEmail(query);
        } else {
            userEntity = userRepository.findUserEntitiesByUserId(query);
        }

        if (userEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        System.out.println(userEntity);
    }
}
