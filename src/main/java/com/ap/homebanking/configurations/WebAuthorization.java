package com.ap.homebanking.configurations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class WebAuthorization {
    @Bean
    protected SecurityFilterChain filterChain( HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/**").permitAll()
                .antMatchers( HttpMethod.POST, "/api/login", "/api/logout", "/api/clients").permitAll()
                .antMatchers(  "/api/transaction/**", "/api/transaction",
                        "/api/loan/**", "/api/loans",
                        "/api/accounts","/api/clients/current/accounts").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers( HttpMethod.POST, "/api/clients/current/**",
                                            "/api/transactions/**").hasAnyAuthority("CLIENT", "ADMIN")

                .antMatchers("/manager.html", "/rest/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAuthority("ADMIN")

                .antMatchers("/**").hasAuthority("CLIENT");

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");
       http.logout().logoutUrl("/api/logout");

        http.csrf().disable();// deshabilita chequeo por tokens CSRF
        http.headers().frameOptions().disable(); //deshabilita frameOptions para poder acceder a h2-console
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));// Si el usr no está autenticado
        http.formLogin().successHandler( (req, res, auth) -> clearAuthenticationAttributes(req) ); // Si se leggea, limpiar las banderas para que no vuelva a pedir por la autenticacion al navegar
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED)); //Si login falla o no esta autorizado para navegar a cierto lugar
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()); // al desloggearse responder con http estado de que salio bién
    return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
