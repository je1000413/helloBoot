package com.example.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class HellobootApplicationTests {

	@Test
	void contextLoads() {
		SimpleHelloService helloService = new SimpleHelloService();

		String ret = helloService.sayHello("Test");

		Assertions.assertThat(ret).isEqualTo("Hello Test");
	}

	@Test
	void helloDecorator(){
		HelloDecorator decorator = new HelloDecorator(name -> name);

		String ret = decorator.sayHello("Test");
		Assertions.assertThat(ret).isEqualTo("*Test*");
	}

}
