package toy.project.apiserver.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// WebMvcConfigurer  --> method 오버라이드 할 것들이 많아짐
@Configuration
public class CustomServletConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든경로
				.maxAge(500) // pre-fly 얼마나 기다릴지
				.allowedMethods("GET", "POST", "DELETE", "PUT", "HEAD", "OPTIONS")
				.allowedOriginPatterns("*"); // 모든경로
	}
}
