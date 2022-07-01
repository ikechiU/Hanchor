package com.nextgendevs.hanchor.service.impl;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.io.entity.*;
import com.nextgendevs.hanchor.io.repository.*;
import com.nextgendevs.hanchor.security.UserPrinciple;
import com.nextgendevs.hanchor.service.UserService;
import com.nextgendevs.hanchor.service.utils.*;
import com.nextgendevs.hanchor.shared.AmazonSES;
import com.nextgendevs.hanchor.shared.Role;
import com.nextgendevs.hanchor.shared.Utils;
import com.nextgendevs.hanchor.shared.dto.*;
import com.nextgendevs.hanchor.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AmazonSES amazonSES;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    GratitudeRepository gratitudeRepository;

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    LifeHackRepository lifeHackRepository;

    @Autowired
    AffirmationRepository affirmationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;


    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findUserEntitiesByEmail(userDto.getEmail()) != null) {
            throw new HanchorServiceException(ErrorMessages.EMAIL_ALREADY_EXISTS.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        String userId = utils.generateUserId(20);

        userEntity.setUserId(userId);
        userEntity.setFirstname(utils.getCapitalizeName(userDto.getFirstname()));
        userEntity.setLastname(utils.getCapitalizeName(userDto.getLastname()));
        userEntity.setUsername(userDto.getUsername());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(userId));
        userEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        // Set roles
        Collection<RolesEntity> roleEntities = new HashSet<>();
        RolesEntity roleEntity = rolesRepository.findByName(Role.ROLE_USER.name());
        if(roleEntity != null) {
            roleEntities.add(roleEntity);
        }
        roleEntities.add(roleEntity);
        userEntity.setRoles(roleEntities);

        UserEntity createdUser = userRepository.save(userEntity);

        List<GratitudeDto> gratitudeDtos = new PreLoaded().gratitudeDtos();
        for (GratitudeDto dto : gratitudeDtos) {
            GratitudeEntity gratitudeEntity = new GratitudeEntity();
            gratitudeEntity.setTitle(dto.getTitle());
            gratitudeEntity.setMessage(dto.getMessage());
            gratitudeEntity.setImageId(dto.getImageId());
            gratitudeEntity.setImageUrl(dto.getImageUrl());
            gratitudeEntity.setUserEntityGratitude(createdUser);

            gratitudeRepository.save(gratitudeEntity);
        }

        List<AffirmationDto> affirmationDtos = new PreLoaded().affirmationDtos();

        for (AffirmationDto dto : affirmationDtos) {
            AffirmationEntity affirmationEntity = new AffirmationEntity();
            affirmationEntity.setTitle(dto.getTitle());
            affirmationEntity.setAffirmation(dto.getAffirmation());
            affirmationEntity.setUserEntityAffirmation(createdUser);

            affirmationRepository.save(affirmationEntity);
        }

        Iterable<QuoteEntity> quoteEntities = quoteRepository.findAll();
        Iterable<LifeHackEntity> lifeHackEntities = lifeHackRepository.findAll();

        UserDto returnValue = modelMapper.map(createdUser, UserDto.class);
        returnValue.setGratitudeDtos(gratitudeDtos);
        returnValue.setAffirmationDtos(affirmationDtos);
        returnValue.setTodoDtos(new ArrayList<>());

//        amazonSES.verifyEmail(returnValue); // Send an email message to user to verify their email address

        return new Extension().updateUserDtoRest(returnValue, quoteEntities, lifeHackEntities);

    }

    @Override
    public UserDto getUserLoginDetails(String userId) { //Used by AuthenticationFilter to get response headers
        UserEntity userEntity = getUserEntity(userId);
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUser(String userId) {
        UserEntity userEntity = getUserEntity(userId);

        Iterable<QuoteEntity> quoteEntities = quoteRepository.findAll();
        Iterable<LifeHackEntity> lifeHackEntities = lifeHackRepository.findAll();

        return new Extension().getUserDto(userEntity, quoteEntities, lifeHackEntities);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserEntity userEntity = getUserEntity(userId);

        String newFirstName;
        String newLastName;
        String newUsername;

        if (userDto.getFirstname().isEmpty()) newFirstName = userEntity.getFirstname();
        else newFirstName = userDto.getFirstname();
        if (userDto.getLastname().isEmpty()) newLastName = userEntity.getLastname();
        else newLastName = userDto.getLastname();
        if (userDto.getUsername().isEmpty()) newUsername = userEntity.getUsername();
        else newUsername = userDto.getUsername();

        userEntity.setFirstname(newFirstName);
        userEntity.setLastname(newLastName);
        userEntity.setUsername(newUsername);

        Iterable<QuoteEntity> quoteEntities = quoteRepository.findAll();
        Iterable<LifeHackEntity> lifeHackEntities = lifeHackRepository.findAll();

        UserEntity updatedUserEntity = userRepository.save(userEntity);

        return new Extension().getUserDto(updatedUserEntity, quoteEntities, lifeHackEntities);
    }


    @Override
    public List<UserDto> getUsers(int page, int limit, String queryParam) {
        List<UserDto> returnValue;

        List<UserEntity> entityList;

        if (page > 0) page = page - 1;
        Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("timestamp").descending());
        Page<UserEntity> studentEntities;

        if (queryParam.isEmpty()) {
            studentEntities = userRepository.findAll(pageableRequest);
        } else {
            studentEntities = userRepository.findAllByFirstnameLikeOrLastnameLikeOrUsernameLikeOrEmailLike(
                    queryParam, queryParam, queryParam, queryParam, pageableRequest);
        }

        Iterable<QuoteEntity> quoteEntities = quoteRepository.findAll();
        Iterable<LifeHackEntity> lifeHackEntities = lifeHackRepository.findAll();

        entityList = studentEntities.getContent();
        returnValue = getStudentDtos(entityList);

        for (int i = 0; i < entityList.size(); i++) {
            returnValue.get(i).setGratitudeRests(getGratitudeRest(entityList.get(i).getGratitudeEntities()));
            returnValue.set(i, returnValue.get(i));
        }

        for (int i = 0; i < entityList.size(); i++) {
            returnValue.get(i).setQuoteRests(new Extension().quoteRests(quoteEntities));
            returnValue.set(i, returnValue.get(i));
        }

        for (int i = 0; i < entityList.size(); i++) {
            returnValue.get(i).setLifeHackRests(new Extension().lifeHackRests(lifeHackEntities));
            returnValue.set(i, returnValue.get(i));
        }

        for (int i = 0; i < entityList.size(); i++) {
            returnValue.get(i).setAffirmationRests(getAffirmationRest(entityList.get(i).getAffirmationEntities()));
            returnValue.set(i, returnValue.get(i));
        }

        for (int i = 0; i < entityList.size(); i++) {
            returnValue.get(i).setGratitudeRests(getGratitudeRest(entityList.get(i).getGratitudeEntities()));
            returnValue.set(i, returnValue.get(i));
        }

        for (int i = 0; i < entityList.size(); i++) {
            returnValue.get(i).setTodoRests(getTodoRest(entityList.get(i).getTodoEntities()));
            returnValue.set(i, returnValue.get(i));
        }


        return returnValue;
    }

    private List<UserDto> getStudentDtos(List<UserEntity> entityList) {
        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();
        return new ModelMapper().map(entityList, listType);
    }


    @Override
    public void deleteUser(String userId) {
        UserEntity studentEntity = getUserEntity(userId);
        userRepository.delete(studentEntity);
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        UserEntity userEntity = userRepository.findUserEntityByEmailVerificationToken(token);

        if (userEntity != null) {
            boolean hastokenExpired = Utils.hasTokenExpired(token);
            if (!hastokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(userEntity);
                returnValue = true;
            }
        }

        return returnValue;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        boolean returnValue = false;

        UserEntity userEntity = userRepository.findUserEntitiesByEmail(email);

        if (userEntity == null) {
            return returnValue;
        }

        String token = new Utils().generatePasswordResetToken(userEntity.getUserId());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserEntity(userEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

//        returnValue = new AmazonSES().sendPasswordResetRequest(
//                userEntity.getFirstname(),
//                userEntity.getEmail(),
//                token);

        return returnValue;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;

        if (Utils.hasTokenExpired(token)) {
            return returnValue;
        }

        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenEntity == null) {
            return returnValue;
        }

//         Prepare new password
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        // Update User password in database
        UserEntity userEntity = passwordResetTokenEntity.getUserEntity();
        userEntity.setEncryptedPassword(encodedPassword);
        UserEntity savedUserEntity = userRepository.save(userEntity);

        // Verify if password was saved successfully
        if (savedUserEntity != null && savedUserEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
            returnValue = true;
        }

        // Remove Password Reset token from database
        passwordResetTokenRepository.delete(passwordResetTokenEntity);

        return returnValue;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = new UserEntity();
        String userName = "";

        if (username.contains("@")) {
            userEntity = userRepository.findUserEntitiesByEmail(username);
            userName = userEntity.getEmail();
        } else {
            userEntity = userRepository.findUserEntitiesByUserId(username);
            userName = userEntity.getUserId();
        }

//        return new User(userName, userEntity.getEncryptedPassword(), new ArrayList<>());
//        return new User(userName, userEntity.getEncryptedPassword(), studentEntity.getEmailVerificationStatus(),
//                true, true, true, new ArrayList<>());
        return new UserPrinciple(userEntity, userName);
    }

    public UserEntity getUserEntity(String query) {
        UserEntity userEntity = new UserEntity();
        if (query.contains("@")) {
            userEntity = userRepository.findUserEntitiesByEmail(query);
        } else {
            userEntity = userRepository.findUserEntitiesByUserId(query);
        }

        if (userEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        return userEntity;
    }

    @Transactional
    public List<GratitudeRest> getGratitudeRest(List<GratitudeEntity> gratitudeEntities) {
        List<GratitudeRest> gratitudeRests = new ArrayList<>();

        for (GratitudeEntity entity : gratitudeEntities) {
            GratitudeRest gratitudeRest = new GratitudeRest();
            gratitudeRest.setId(entity.getId());
            gratitudeRest.setTitle(entity.getTitle());
            gratitudeRest.setMessage(entity.getMessage());
            gratitudeRest.setImageUrl(entity.getImageUrl());
            gratitudeRest.setImageId(entity.getImageId());

            gratitudeRests.add(gratitudeRest);
        }

        return gratitudeRests;
    }

    @Transactional
    public List<AffirmationRest> getAffirmationRest(List<AffirmationEntity> affirmationEntities) {
        List<AffirmationRest> affirmationRests = new ArrayList<>();

        for (AffirmationEntity entity : affirmationEntities) {
            AffirmationRest affirmationRest = new AffirmationRest();
            affirmationRest.setId(entity.getId());
            affirmationRest.setTitle(entity.getTitle());
            affirmationRest.setAffirmation(entity.getAffirmation());

            affirmationRests.add(affirmationRest);
        }

        return affirmationRests;
    }


    @Transactional
    public List<TodoRest> getTodoRest(List<TodoEntity> todoEntities) {
        List<TodoRest> todoRests = new ArrayList<>();

        for (TodoEntity entity : todoEntities) {
            TodoRest todoRest = new TodoRest();
            todoRest.setId(entity.getId());
            todoRest.setTitle(entity.getTitle());
            todoRest.setTask(entity.getTask());
            todoRest.setCompleted(entity.getCompleted());
            todoRest.setDate(entity.getDate());

            todoRests.add(todoRest);
        }

        return todoRests;
    }

}
