package jpabook.jpashop;

// import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule; // Hibernate 7 호환 안 됨
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	// Hibernate 7 호환 안 됨 - DTO 사용으로 대체 필요
	// @Bean
	// Hibernate5JakartaModule hibernate5Module() {
	// 	return new Hibernate5JakartaModule();
	// }

}
