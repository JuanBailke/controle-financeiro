package com.fincontrol.controle_financeiro.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY;

    public JwtUtil(@Value("${api.security.token.secret: Erro encontrado!}") String secretKey) {
        if (secretKey == null) {
            throw new IllegalStateException("Chave JWT inválida ou não definida! Verifique a variável de ambiente 'SECRET_KEY'.");
        }
        this.SECRET_KEY = secretKey;
    }

    @PostConstruct
    public void onStartup() {
        if (SECRET_KEY.length() < 32) {
            throw new IllegalArgumentException("A chave secreta JWT deve ter pelo menos 32 caracteres.");
        }
    }

    public String generateAccessToken(String email){
        try{
            return Jwts.builder()
                    .subject(email)
                    .claim("type", "access")
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutos de validade
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();
        }catch (Exception e){
            throw new RuntimeException("Erro ao gerar token JWT de acesso" + e.getMessage());
        }
    }

    public String generateRefreshToken(String email){
        try{
            return Jwts.builder()
                    .subject(email)
                    .claim("type", "refresh")
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas de validade
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();
        }catch (Exception e){
            throw new RuntimeException("Erro ao gerar token JWT refresh" + e.getMessage());
        }
    }

    public boolean validateToken(String token) {
        System.out.println("🔍 Validando token...");
        try{
            return !isTokenExpired(token);
        }catch (Exception e){
            System.out.println("❌ Erro ao validar token: " + e.getMessage());
            return false;
        }
    }

    public boolean isAccessToken(String token){
        System.out.println("🔍 Verificando tipo de token...");
        return "access".equals(extractClaims(token).get("type"));
    }

    public String extractUsername(String token){
        System.out.println("🔍 Extraindo token...");
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        System.out.println("🔍 Validando expiração do token...");
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token){
        System.out.println("🔍 Extraindo claim...");
        JwtParser parser = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build();
        try {
            return parser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "❌ Token expirado: " + e.getMessage(), e);
            //throw new ResponseStatusException(HttpServletResponse.SC_UNAUTHORIZED, "❌ Token expirado: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Erro ao extrair claims do token: " + e.getMessage(), e);
        }
    }
}
