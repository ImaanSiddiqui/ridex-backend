package com.ridex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class RidexApplication {
	
	
public static void main(String[] args) {
		
		Dotenv dotenv = Dotenv.configure()
                .filename(".env") 
                .load();

        // Fetch environment variables
        String dbUrl = dotenv.get("DB_URL");
        String dbUsername = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");

        // Check if any variable is missing
        if (dbUrl == null || dbUsername == null || dbPassword == null) {
            System.err.println("⚠️ Error: Missing environment variables! Check your .env file.");
            System.exit(1); 
        }

        // Set environment variables as system properties
        System.setProperty("DB_URL", dbUrl);
        System.setProperty("DB_USERNAME", dbUsername);
        System.setProperty("DB_PASSWORD", dbPassword);
        
		SpringApplication.run(RidexApplication.class, args);


    
	}

}
