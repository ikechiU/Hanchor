package com.nextgendevs.hanchor.service;

import com.nextgendevs.hanchor.shared.dto.QuoteDto;

import java.util.List;

public interface QuoteService {

    QuoteDto createQuote(String userId, QuoteDto quoteDto);

    QuoteDto getQuote(String userId, long quoteId);

    QuoteDto updateQuote(String userId, long quoteId, QuoteDto quoteDto);

    List<QuoteDto> getQuotes(String userId, int page, int limit);

    void deleteQuote(String userId, long quoteId);
}
