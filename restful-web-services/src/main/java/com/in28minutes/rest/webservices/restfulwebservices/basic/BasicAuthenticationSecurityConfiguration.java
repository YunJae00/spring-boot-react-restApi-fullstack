package com.in28minutes.rest.webservices.restfulwebservices.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//security 설치 이후 get은 (property에서 id password설정) and 가능한데 post 하려면 안됨 -> 
//403err 이유는 CSRF(크로스 사이트 요청 위조)때문 그래서 이걸 비활성화
//@Configuration
public class BasicAuthenticationSecurityConfiguration {
	// 여기서 설정하려는건 Spring Security 필터체인
	// 요청이 들어오면 Spring Security는 일련의 필터를 이용해서 그걸 확인하려 함

	// 요청이 들어오면 Spring Security는 일련의 필터를 이용해서 그걸 확인하려고 시도하죠
	// 우린 지금 그 필터들을 설정하려고 합니다 그럼 우린 @Bean이라고 해주고요, 이건 public SecurityFilterChain이 될
	// 겁니다
	// 그리고 메서드 이름은 filterChain()이라고 하죠 그리고 연결할 Bean은 HttpSecurity가 될 겁니다
	// 이걸 http라고 부르고요
	// 여기서 우리는 http.build()라고 해줄 겁니다
	// 이렇게 SpringFilterChain은 HttpServletRequest에 매칭될 수 있는 필터 체인을 정의합니다
	// 그럼 요청이 들어오면 Spring Security는 이 필터 체인을 사용하게 되죠
	// HttpSecurity는 실제로 우리가 특정한 HTTP 요청에 대해 웹 기반 보안을 설정할 수 있게 도와줍니다
	// 그럼 우린 필터 체인을 맞춤화하려고 하고요, 그렇게 하기 위해 우리는 HttpSecurity를 사용하죠
	// 그리고 우리는 지금 CSRF를 비활성화한 상태로 시작하려고 합니다 하지만 Spring Security에는 중요한 점이 있는데요, 여러분이
	// 체인을 정의하기 시작하면
	// 그 체인 전체를 정의해야 한다는 점입니다
	// 기본값으로서 Spring Security는 모든 요청을 인증하게 되죠 그럼 모든 요청이 인증되고요 우리는 모든 요청에 대해 기본적인 인증을
	// 사용하려고 합니다
	// 그리고 우리가 CSRF를 비활성화하는 이유는 세션이 전혀 없도록 하려는 것이죠
	// 그래서 여러분에게 세션이 있으면 반드시 CSRF를 활성화해야 합니다
	// 여기서 우리는 REST API를 만들고 있고요, 그래서 우리는 세션이 전혀 필요하지 않습니다, 그럼 우리는 상태가 없는 REST API를
	// 만들려고 하는 것이죠
	// 그럼 우린 지금 이것들을 모두 설정하려고 합니다

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		return http.build();
//	}
	// 이러면 모든 요청을 실행할 수 있음 이건 우리가 Spring Security를 비활성화 했음을 의미함

	// 이제 이제 우린 이것들을 하나씩 설정함
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		// 모든 요청에 대한 인증 설정
//		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
//		// 이러면 모든 http요청이 인증되어야 한다고 해주는거임 -> 요청하면 아무것도 안나옴
//		http.httpBasic(Customizer.withDefaults());
//		// 이러면 기본 인증을 해주는거
//
//		// 이제 상태가 없는 REST API를 만들고 CSRF를 비활성화
//		// 이렇게 CSRF를 비활성화한다면 세션에 상태가 없어야함
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.csrf().disable();
//
//		return http.build();
//	}
	// 이제 이렇게 하면 프론트엔드에서 기본 인증 헤더를 줘야 그걸 전송할 수 있음

//	위에 전체를 
//	return http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//			.httpBasic(Customizer.withDefaults())
//			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//			.csrf().disable()
//			.build();
//
//	도 가능

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(auth -> auth
				// 프리플라이트에 대한 응답이 액세스 제어 체크를 통과하게 하려함
				// 특정한거에 대해 인증을 비활성화하려함
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated()) // "/**"에 유입되면
																									// .permintAll()
				// permitall까지는 프리플라이트 요청에 대한 액세스를 허용, 뒤에는 기본 인증을 위한 테스트 url생성
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).csrf()
				.disable().build();
	}
}
