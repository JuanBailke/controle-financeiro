package com.fincontrol.controle_financeiro.security;

import com.fincontrol.controle_financeiro.models.user.User;
import com.fincontrol.controle_financeiro.repositories.UserRepository;
import com.fincontrol.controle_financeiro.services.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository repository;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("✅ Iniciado o filtro JWT");
        String header = request.getHeader("Authorization");

        try{
            if (header != null && header.startsWith("Bearer ")){
                String token = header.substring(7);

                if (request.getRequestURI().equals("/auth/refresh")) {
                    if(!jwtUtil.isAccessToken(token)) {
                        validate(token);  // Valida o token de refresh
                        filterChain.doFilter(request,response);
                    }else{
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido: somente Refresh Tokens são permitidos.");
                    }
                    return;  // Permite o fluxo normal para o endpoint de refresh
                }

                if (!jwtUtil.isAccessToken(token)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido: somente Access Tokens são permitidos.");
                }
                validate(token);
            }
            filterChain.doFilter(request, response);
        } catch (ResponseStatusException ex){
            CustomAuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();
            entryPoint.commence(request, response, new AuthenticationException(ex.getReason()) {
            });
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Erro.");
            response.getWriter().flush();
        }

    }

    private void validate(String token) {
        try{
            String email = jwtUtil.extractUsername(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(email);
                //User user = repository.findByEmail(email).orElse(null);
                if (userDetails != null && jwtUtil.validateToken(token)){
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtException e) {
            System.out.println("❌ Erro ao processar o token JWT: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
