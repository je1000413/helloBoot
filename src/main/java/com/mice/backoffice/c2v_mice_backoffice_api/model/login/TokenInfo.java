package com.mice.backoffice.c2v_mice_backoffice_api.model.login;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 로그인 Request Object
 * @author	Jieun Chun
 * @email	je1000@com2us.com
 * @since	2023.02.16
 */
@Builder
@Data
@AllArgsConstructor
public class TokenInfo {
	private String grantType;
	private String accessToken;
	private String refreshToken;
}

