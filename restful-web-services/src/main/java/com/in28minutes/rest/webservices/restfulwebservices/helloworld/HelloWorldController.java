package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	// 기본인증 url
	// 누군가가 토큰이 올바른지 확인하려 한다면 여기로 요청을 전송하고 헤더에서 인증 토큰을 전송할 수 있음
	@GetMapping(path = "/basicauth")
	public String basicAuthChech() {
		// 응답은 의미없고 200이란 응답을 받는걸로 충분함
		// 사용자 이름과 패스워드를 받고 토큰을 생성할 수 있고, 이 인증 url로 요청을 전송할 수 있음
		// ->200을 받으면 인증
		return "Success";
	}

	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World v2";
	}

	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}

	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
}
