package com.spring.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authenticationService")
public interface AuthenticationServiceClient {
	
	@GetMapping("/auth/validate")
	public String validateToken(@RequestParam("token") String token);
}
