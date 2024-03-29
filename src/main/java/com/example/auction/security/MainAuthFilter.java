package com.example.auction.security;

import com.example.auction.database.entities.AuctionUser;
import com.example.auction.database.repositories.AuctionUserRepository;
import com.example.auction.security.models.OurAuthToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class MainAuthFilter implements Filter {

    private static final String LOGIN_HEADER = "x-access-login";
    private static final String PASSWORD_HEADER = "x-access-password";

    protected final AuthenticationFailureHandler failureHandler;

    private final AuctionUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private List<RequestMatcher> requireAuthMatcher;

    public MainAuthFilter setRequireAuthMatcher(List<RequestMatcher> requireAuthMatcher) {
        this.requireAuthMatcher = requireAuthMatcher;
        return this;
    }

    public MainAuthFilter(AuthenticationFailureHandler failureHandler,
                          AuctionUserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.failureHandler = failureHandler;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        if (requireAuth((HttpServletRequest) req)) {
            OurAuthToken token = tryAuth((HttpServletRequest) req, (HttpServletResponse) res);
            if (token == null) {
//                failureHandler.onAuthenticationFailure(
//                        (HttpServletRequest) req,
//                        (HttpServletResponse) res,
//                        new AuthenticationServiceException("Invalid user name or password")
//                );
                token = new OurAuthToken(null, null, Collections.singleton(
                        new SimpleGrantedAuthority("USER")
                ));

            }
            SecurityContextHolder.getContext().setAuthentication(token);
            chain.doFilter(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Nullable
    protected OurAuthToken tryAuth(HttpServletRequest req, HttpServletResponse res) {
        String login = req.getHeader(LOGIN_HEADER);
        String password = req.getHeader(PASSWORD_HEADER);

        Optional<AuctionUser> optionalUser = userRepository.findOptionalByEmail(login);
        if (optionalUser.isEmpty()) {
            return null;
        }
        AuctionUser user = optionalUser.get();

        if (!passwordEncoder.matches(password + "sada", user.getPassword())) {
            return null;
        }

        Collection<? extends GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("BASE_USER")
        );

        return new OurAuthToken(user.getId(), user, authorities);
    }

    ///////////////////////////////////////////////////////////////////////////
    //                          private
    ///////////////////////////////////////////////////////////////////////////

    private boolean requireAuth(HttpServletRequest req) {
        boolean find = requireAuthMatcher.stream().anyMatch(m -> m.matches(req));
        return requireAuthMatcher == null || find;
    }
}
