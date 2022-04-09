package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.AuctionUserAlreadyExistException;
import com.example.auction.controllers.models.RegistrationParamsRequest;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.database.entities.AuctionUser;
import com.example.auction.database.repositories.AuctionUserRepository;
import com.example.auction.services.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final AuctionUserRepository auctionUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public RegistrationServiceImpl(ModelMapper mapper, AuctionUserRepository auctionUserRepository, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.auctionUserRepository = auctionUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto signup(RegistrationParamsRequest registrationParamsRequest) throws AuctionUserAlreadyExistException {
        Optional<AuctionUser> existedUser = auctionUserRepository.findOptionalByEmail(registrationParamsRequest.getEmail());
        if (existedUser.isPresent()) {
            throw new AuctionUserAlreadyExistException();
        }

        AuctionUser user = mapper.map(registrationParamsRequest, AuctionUser.class);
        String password = passwordEncoder.encode(user.getPassword() + "sada");

        user.setPassword(password);
        auctionUserRepository.save(user);
        return mapper.map(user, UserDto.class);
    }
}
