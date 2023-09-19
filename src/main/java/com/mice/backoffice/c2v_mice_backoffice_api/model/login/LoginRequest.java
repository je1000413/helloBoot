package com.mice.backoffice.c2v_mice_backoffice_api.model.login;


import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 로그인 Request Object
 * @author	JiEun Chun
 * @email	je1000@com2us.com
 * @since	2023.02.16
 */
@Data
public class LoginRequest {
	private String accountName;
	private String accountPassword;

	private String getEncodePassword(String accountPassword) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return new StringBuilder().append("{bcrypt}").append(passwordEncoder.encode(accountPassword)).toString();
	}
}

