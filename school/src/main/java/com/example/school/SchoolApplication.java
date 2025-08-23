package com.example.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.school.repository")
@EnableMongoAuditing
public class SchoolApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(SchoolApplication.class, args);
//	}

	SchoolApplication() {

		System.out.println("School Application Started...");
	}

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);

		System.out.println("\n🌟 ================================");
		System.out.println("🚀 School Application Started Successfully!");
		System.out.println("📊 MongoDB connection configured");
		System.out.println("🔗 API available at: http://localhost:8080");
//		System.out.println("📋 Swagger UI: http://localhost:8080/swagger-ui.html");
		System.out.println("📋 Swagger UI: http://localhost:8080/swagger-ui/index.html");
		System.out.println("📚 Documentation: http://localhost:8080/v3/api-docs");
		System.out.println("================================");

		System.out.println("\n📍 Available Endpoints:");
		System.out.println("┌─────────────────────────────────────────────────────────┐");
		System.out.println("│                    USER OPERATIONS                     │");
		System.out.println("├─────────────────────────────────────────────────────────┤");
		System.out.println("│ GET    /api/users                - Get all users       │");
		System.out.println("│ POST   /api/users                - Create new user     │");
		System.out.println("│ GET    /api/users/{id}           - Get user by ID      │");
		System.out.println("│ PUT    /api/users/{id}           - Update user         │");
		System.out.println("│ DELETE /api/users/{id}           - Delete user         │");
		System.out.println("├─────────────────────────────────────────────────────────┤");
		System.out.println("│                    SEARCH OPERATIONS                   │");
		System.out.println("├─────────────────────────────────────────────────────────┤");
		System.out.println("│ GET    /api/users/search?name=John  - Search by name   │");
		System.out.println("│ GET    /api/users/age-range?min=18&max=65 - Age range  │");
		System.out.println("│ GET    /api/users/hobby?hobby=reading    - By hobby    │");
		System.out.println("│ GET    /api/users/city?city=Colombo     - By city      │");
		System.out.println("├─────────────────────────────────────────────────────────┤");
		System.out.println("│                    STATISTICS                          │");
		System.out.println("├─────────────────────────────────────────────────────────┤");
		System.out.println("│ GET    /api/users/count          - Get total count     │");
		System.out.println("│ GET    /api/users/stats          - Get user statistics │");
		System.out.println("│ GET    /api/users/analytics/hobbies - Popular hobbies  │");
		System.out.println("└─────────────────────────────────────────────────────────┘");

		System.out.println("\n🔧 Development Tips:");
		System.out.println("• Use MongoDB Compass to view your data visually");
		System.out.println("• Check application-dev.properties for local development");
		System.out.println("• Use Postman or cURL to test API endpoints");
		System.out.println("• Monitor logs for MongoDB connection status");
		System.out.println("\n✅ Application is ready for development!\n");
	}

}



