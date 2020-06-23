package com.example.crudrest.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {

        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();

        boolean isAdmin = false;
        boolean isUser = false;

        for (GrantedAuthority grantedAutority : grantedAuthorities) {
            if (grantedAutority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
            }
            if (grantedAutority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        if (isAdmin) {
            httpServletResponse.sendRedirect("/admin");
            return;
        }
        if (isUser) {
            httpServletResponse.sendRedirect("/user");
        }
    }

}
