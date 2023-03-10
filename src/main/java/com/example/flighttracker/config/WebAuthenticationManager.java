package com.example.flighttracker.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebAuthenticationManager implements AuthenticationManager {

    final List<AuthenticationProvider> authenticationProviders;

    public WebAuthenticationManager(List<AuthenticationProvider> authenticationProviders) {
        this.authenticationProviders = authenticationProviders;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication webAuthentication;
        for(AuthenticationProvider authenticationProvider : authenticationProviders){
            webAuthentication = authenticationProvider.authenticate(authentication);
            if(webAuthentication != null){
                webAuthentication.setAuthenticated(true);
                return webAuthentication;
            }
        }
        throw new BadCredentialsException("Bad user Credentials!");
    }
}
