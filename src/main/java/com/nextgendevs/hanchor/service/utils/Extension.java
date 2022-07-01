package com.nextgendevs.hanchor.service.utils;

import com.nextgendevs.hanchor.io.entity.*;
import com.nextgendevs.hanchor.shared.dto.*;
import com.nextgendevs.hanchor.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Extension {

    public UserDto getUserDto(UserEntity updatedUserEntity, Iterable<QuoteEntity> quoteEntities, Iterable<LifeHackEntity> lifeHackEntities) {
        UserDto returnValue = new ModelMapper().map(updatedUserEntity, UserDto.class);

        List<GratitudeEntity> gratitudeEntities = updatedUserEntity.getGratitudeEntities();
        Type gratitudeListType = new TypeToken<List<GratitudeRest>>() {}.getType();
        List<GratitudeRest> gratitudeRests = new ModelMapper().map(gratitudeEntities, gratitudeListType);

        List<AffirmationEntity> affirmationEntities = updatedUserEntity.getAffirmationEntities();
        Type affirmationListType = new TypeToken<List<AffirmationRest>>() {}.getType();
        List<AffirmationRest> affirmationRests = new ModelMapper().map(affirmationEntities, affirmationListType);

        List<TodoEntity> todoEntities = updatedUserEntity.getTodoEntities();
        Type todoListType = new TypeToken<List<TodoRest>>() {}.getType();
        List<TodoRest> todoRests = new ModelMapper().map(todoEntities, todoListType);

        returnValue.setGratitudeRests(gratitudeRests);
        returnValue.setAffirmationRests(affirmationRests);
        returnValue.setTodoRests(todoRests);
        returnValue.setQuoteRests(quoteRests(quoteEntities));
        returnValue.setLifeHackRests(lifeHackRests(lifeHackEntities));

        return returnValue;
    }

    public UserDto updateUserDtoRest(UserDto userDto, Iterable<QuoteEntity> quoteEntities, Iterable<LifeHackEntity> lifeHackEntities) {

        List<GratitudeDto> gratitudeDtos = userDto.getGratitudeDtos();
        Type gratitudeListType = new TypeToken<List<GratitudeRest>>() {}.getType();
        List<GratitudeRest> gratitudeRests = new ModelMapper().map(gratitudeDtos, gratitudeListType);

        List<AffirmationDto> affirmationDtos = userDto.getAffirmationDtos();
        Type affirmationListType = new TypeToken<List<AffirmationRest>>() {}.getType();
        List<AffirmationRest> affirmationRests = new ModelMapper().map(affirmationDtos, affirmationListType);

        List<TodoDto> todoDtos = userDto.getTodoDtos();
        Type todoListType = new TypeToken<List<TodoRest>>() {}.getType();
        List<TodoRest> todoRests = new ModelMapper().map(todoDtos, todoListType);

        userDto.setGratitudeRests(gratitudeRests);
        userDto.setAffirmationRests(affirmationRests);
        userDto.setTodoRests(todoRests);
        userDto.setQuoteRests(quoteRests(quoteEntities));
        userDto.setLifeHackRests(lifeHackRests(lifeHackEntities));

        return userDto;
    }

    public List<QuoteRest> quoteRests(Iterable<QuoteEntity> quoteEntities) {
        List<QuoteEntity> quoteEntityList = StreamSupport
                .stream(quoteEntities.spliterator(), false)
                .collect(Collectors.toList());

        Type quoteListType = new TypeToken<List<QuoteEntity>>() {}.getType();
        return new ModelMapper().map(quoteEntityList, quoteListType);
    }

    public List<LifeHackRest> lifeHackRests(Iterable<LifeHackEntity> lifeHackEntities) {
        List<LifeHackEntity> lifeHackEntityList = StreamSupport
                .stream(lifeHackEntities.spliterator(), false)
                .collect(Collectors.toList());

        Type lifeHackListType = new TypeToken<List<LifeHackRest>>() {}.getType();
        return new ModelMapper().map(lifeHackEntityList, lifeHackListType);
    }



}
