package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.AuctionUserAlreadyExistException;
import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.database.entities.AuctionUser;
import com.example.auction.database.repositories.AuctionUserRepository;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.AuctionUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.management.OperatingSystemMXBean;
import java.util.HashSet;
import java.util.Optional;

@Service
public class AuctionUserServiceImpl implements AuctionUserService {

    private final ModelMapper mapper;
    private final PasswordEncoder encoder;
    private final AuctionUserRepository userRepository;

    public AuctionUserServiceImpl(ModelMapper mapper, PasswordEncoder encoder, AuctionUserRepository userRepository) {
        this.mapper = mapper;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUser(String userId) throws AuctionUserNotExisted{
        Optional<AuctionUser> existedUser = userRepository.findById(userId);

        AuctionUser user = existedUser.orElseThrow(AuctionUserNotExisted::new);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto login(UserRequest user, OurAuthToken authToken) throws AuctionUserNotExisted{
        if (authToken != null)
            return getUser(authToken.getUserId());

        Optional<AuctionUser> existedUser = userRepository.findOptionalByEmail(user.getEmail());

        AuctionUser auctionUser;

        if (existedUser.isPresent())
            auctionUser = existedUser.get();
        else
            throw new AuctionUserNotExisted();

        if (encoder.matches(user.getPassword() + "sada", auctionUser.getPassword()))
            return mapper.map(auctionUser, UserDto.class);
        else
            throw new AuctionUserNotExisted();
    }

    @Override
    public UserDto money(UserRequest user) {
        Optional<AuctionUser> existedUser = userRepository.findOptionalByEmail(user.getEmail());
        //AuctionUser existedUser = userRepository.findOptionalByEmail(user.getEmail());
        AuctionUser updatedUser = mapper.map(existedUser, AuctionUser.class);
        updatedUser.setBalance(user.getBalance());
        userRepository.save(updatedUser);

        return mapper.map(existedUser, UserDto.class);
    }
}
