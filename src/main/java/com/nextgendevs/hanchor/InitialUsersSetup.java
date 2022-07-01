package com.nextgendevs.hanchor;

import com.nextgendevs.hanchor.io.entity.*;
import com.nextgendevs.hanchor.io.repository.*;
import com.nextgendevs.hanchor.service.utils.PreLoaded;
import com.nextgendevs.hanchor.shared.Role;
import com.nextgendevs.hanchor.shared.Utils;
import com.nextgendevs.hanchor.shared.dto.LifeHackDto;
import com.nextgendevs.hanchor.shared.dto.QuoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Component
public class InitialUsersSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Utils utils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuoteRepository quoteRepository;

    @Autowired
    LifeHackRepository lifeHackRepository;


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event...");

        Iterable<QuoteEntity> quoteEntities = quoteRepository.findAll();
        Iterable<LifeHackEntity> lifeHackEntities = lifeHackRepository.findAll();

//        long quoteSize = quoteEntities.spliterator().getExactSizeIfKnown();
//        long lifeHackSize = lifeHackEntities.spliterator().getExactSizeIfKnown();

//        long quoteSize = StreamSupport.stream(quoteEntities.spliterator(), false).count();
//        long lifeHackSize = StreamSupport.stream(lifeHackEntities.spliterator(), false).count();

        if(!quoteEntities.iterator().hasNext() && !lifeHackEntities.iterator().hasNext()) {
            List<QuoteDto> quoteDtos = new PreLoaded().quoteDtos();
            for (QuoteDto dto : quoteDtos) {
                QuoteEntity quoteEntity = new QuoteEntity();
                quoteEntity.setQuote(dto.getQuote());

                quoteRepository.save(quoteEntity);
            }

            List<LifeHackDto> lifeHackDtos = new PreLoaded().lifeHackDtos();
            for (LifeHackDto dto : lifeHackDtos) {
                LifeHackEntity lifeHackEntity = new LifeHackEntity();
                lifeHackEntity.setLifeHack(dto.getLifeHack());

                lifeHackRepository.save(lifeHackEntity);
            }
        }

        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity superWriteAuthority = createAuthority("SUPER_WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        createRole(Role.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
        RolesEntity roleAdmin = createRole(Role.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, superWriteAuthority));
        RolesEntity roleSuper = createRole(Role.ROLE_SUPER.name(), Arrays.asList(readAuthority, writeAuthority, superWriteAuthority,  deleteAuthority));


        if(roleAdmin == null) return;

        UserEntity adminUser = new UserEntity();
        adminUser.setUsername("Admin");
        adminUser.setFirstname("Admin");
        adminUser.setLastname("Admin");
        adminUser.setEmail("admin@hanchor.com");
        adminUser.setUserId("123456789");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setAffirmationEntities(new ArrayList<>());
        adminUser.setGratitudeEntities(new ArrayList<>());
        adminUser.setTodoEntities(new ArrayList<>());
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123456"));
        adminUser.setEmailVerificationToken(null);
        adminUser.setTimestamp(new Timestamp(System.currentTimeMillis()));
        adminUser.setRoles(Arrays.asList(roleAdmin));


        UserEntity userEntity = userRepository.findUserEntitiesByEmail("admin@hanchor.com");
        if (userEntity == null) {
            userRepository.save(adminUser);
        }

        if(roleSuper == null) return;

        UserEntity superUser = new UserEntity();
        superUser.setUsername("Super");
        superUser.setFirstname("Super");
        superUser.setLastname("Super");
        superUser.setEmail("super@hanchor.com");
        superUser.setUserId("A123456789");
        superUser.setEmailVerificationStatus(true);
        superUser.setAffirmationEntities(new ArrayList<>());
        superUser.setGratitudeEntities(new ArrayList<>());
        superUser.setTodoEntities(new ArrayList<>());
        superUser.setEncryptedPassword(bCryptPasswordEncoder.encode("A123456"));
        superUser.setEmailVerificationToken(null);
        superUser.setTimestamp(new Timestamp(System.currentTimeMillis()));
        adminUser.setRoles(Arrays.asList(roleSuper));


        UserEntity userEntity2 = userRepository.findUserEntitiesByEmail("super@hanchor.com");
        if (userEntity2 == null) {
            userRepository.save(superUser);
        }

    }

    @Transactional
    protected AuthorityEntity createAuthority(String name) {

        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    protected RolesEntity createRole(
            String name, Collection<AuthorityEntity> authorities) {

        RolesEntity role = rolesRepository.findByName(name);
        if (role == null) {
            role = new RolesEntity(name);
            role.setAuthorities(authorities);
            rolesRepository.save(role);
        }
        return role;
    }

}
