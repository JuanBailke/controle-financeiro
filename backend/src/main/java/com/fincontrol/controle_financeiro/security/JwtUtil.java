package com.fincontrol.controle_financeiro.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
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

    public String generateToken(String email){
        try{
            return Jwts.builder()
                    .subject(email)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();
        }catch (Exception e){
            throw new RuntimeException("Erro ao gerar token JWT" + e.getMessage());
        }
    }

    public boolean validateToken(String token, String email) {
        System.out.println("🔍 Validando token...");
        try{
            String tokenUsername = extractUsername(token);
            return (tokenUsername.equals(email) && !isTokenExpired(token));
        }catch (Exception e){
            System.out.println("❌ Erro ao validar token: " + e.getMessage());
            return false;
        }
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
        return parser.parseSignedClaims(token).getPayload();
    }
}
