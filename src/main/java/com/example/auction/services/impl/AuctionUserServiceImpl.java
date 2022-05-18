package com.example.auction.services.impl;

import com.example.auction.controllers.exceptions.AuctionUserAlreadyExistException;
import com.example.auction.controllers.exceptions.AuctionUserNotExisted;
import com.example.auction.controllers.models.BetDto;
import com.example.auction.controllers.models.LotDto;
import com.example.auction.controllers.models.UserDto;
import com.example.auction.controllers.models.UserRequest;
import com.example.auction.database.entities.AuctionUser;
import com.example.auction.database.repositories.AuctionUserRepository;
import com.example.auction.security.models.OurAuthToken;
import com.example.auction.services.AuctionUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
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
    public UserDto getUser(OurAuthToken ourAuthToken){
        if (ourAuthToken == null)
            return null;

        Optional<AuctionUser> existedUser = userRepository.findById(ourAuthToken.getUserId());
        AuctionUser user = existedUser.get();
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUser(String userId) throws AuctionUserNotExisted{
        Optional<AuctionUser> existedUser = userRepository.findById(userId);

        AuctionUser user = existedUser.orElseThrow(AuctionUserNotExisted::new);
        var mappedDto = mapper.map(user, UserDto.class);
        mappedDto.setBalance(null);
        return mappedDto;
    }

    @Override
    public UserDto money(UserRequest user, OurAuthToken ourAuthToken) {
        if (ourAuthToken == null)
            return null ;

        Optional<AuctionUser> existedUser = userRepository.findOptionalByEmail(user.getEmail());
        AuctionUser updatedUser = existedUser.get();
        updatedUser.setBalance(user.getBalance());
        userRepository.save(updatedUser);

        return mapper.map(existedUser, UserDto.class);
    }

    @Override
    public List<LotDto> getUserLots(String userId) throws AuctionUserNotExisted{
        Optional<AuctionUser> existedUser = userRepository.findById(userId);
        AuctionUser user = existedUser.orElseThrow(AuctionUserNotExisted::new);

        return user.getOwnLots().stream().map(lot -> {
            var mappedDto = mapper.map(lot, LotDto.class);
            if(lot.getImage() != null)
                mappedDto.setImage(Base64.getEncoder().encodeToString(lot.getImage()));
            return mappedDto;
        }).toList();
    }

    @Override
    public List<LotDto> getCurrentUserLots(OurAuthToken ourAuthToken){
        Optional<AuctionUser> existedUser = userRepository.findById(ourAuthToken.getUserId());
        AuctionUser user = existedUser.get();

        return user.getOwnLots().stream().map(lot -> {
            var mappedDto = mapper.map(lot, LotDto.class);
            if(lot.getImage() != null)
                mappedDto.setImage(Base64.getEncoder().encodeToString(lot.getImage()));
            return mappedDto;
        }).toList();
    }

    @Override
    public List<BetDto> getCurrentUserBets(OurAuthToken ourAuthToken){
        Optional<AuctionUser> existedUser = userRepository.findById(ourAuthToken.getUserId());
        AuctionUser user = existedUser.get();

        return user.getOwnBets().stream().map(bet -> mapper.map(bet, BetDto.class)).toList();
    }
}
