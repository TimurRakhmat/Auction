package com.example.auction.security.models;

import com.example.auction.database.entities.AuctionUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class OurAuthToken extends AbstractAuthenticationToken {

    private AuctionUser principal;
    private String userId;

    public OurAuthToken(String userId, AuctionUser principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.userId = userId;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
