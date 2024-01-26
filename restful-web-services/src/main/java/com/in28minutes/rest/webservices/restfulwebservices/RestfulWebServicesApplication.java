package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}

	// 이제 eclipse에서 설정
	// 그럼 Ctrl+Shift+T 또는 Cmd+Shift+T를 누르고 모두 대문자로 WMC라고 검색
	// 그리고 여기 CORS 매핑을 추가하는 메서드가 보일 겁니다 즉 addCorsMappings()
	// 그럼 이걸 이용하면 크로스 오리진 리퀘스트 처리를 '전역적으로' 설정할 수 있음
	// 그걸 하기 위해 우린 WebMvcConfigurer에 관한 Bean을 정의할 거고요, 거기서 우리는 커스텀 설정을 통해 이 특정한 메서드를
	// 오버라이드함

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// 모든것에 대해 활성화하려먼
				registry.addMapping("/**").allowedMethods("*") // 이러면 get put post 등 모든게 될 수 있음
						.allowedOrigins("http://localhost:3001");
			}
		};
	}
}
