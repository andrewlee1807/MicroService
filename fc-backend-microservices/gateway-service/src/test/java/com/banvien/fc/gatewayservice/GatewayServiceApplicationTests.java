package com.banvien.fc.gatewayservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayServiceApplicationTests {

	@Value("${zuul.routes.auth-service.path}")
	String cc;

	@Test
	public void contextLoads() {
		int k = 0;
	}

}
