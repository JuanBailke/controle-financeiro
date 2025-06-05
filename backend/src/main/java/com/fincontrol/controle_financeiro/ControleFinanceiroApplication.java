package com.fincontrol.controle_financeiro;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ControleFinanceiroApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		String dbHost = dotenv.get("DEV_DB_HOST");
		String dbUser = dotenv.get("DEV_DB_USER");
		String dbPassword = dotenv.get("DEV_DB_PASSWORD");
		String dbName = dotenv.get("DEV_DB_NAME");
		String SECRET_KEY = dotenv.get("SECRET_KEY");


		if (dbHost == null || dbHost.isEmpty()) dbHost = "postgres_db";
		if (dbUser == null || dbUser.isEmpty()) dbUser = "fallback_user";
		if (dbPassword == null || dbPassword.isEmpty()) dbPassword = "fallback_password";
		if (dbName == null || dbName.isEmpty()) dbName = "fallback_db";
		if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
			throw new RuntimeException("🔑 A chave secreta JWT não está configurada. Defina a variável de ambiente 'SECRET_KEY'.");
		}

		System.setProperty("DEV_DB_HOST", dbHost);
		System.setProperty("DEV_DB_USER", dbUser);
		System.setProperty("DEV_DB_PASSWORD", dbPassword);
		System.setProperty("DEV_DB_NAME", dbName);
		System.setProperty("api.security.token.secret", SECRET_KEY);

		SpringApplication.run(ControleFinanceiroApplication.class, args);
	}

}
