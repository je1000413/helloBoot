package com.mice.backoffice.c2v_mice_backoffice_api.filter;

import com.com2verse.platform.object.C2VResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.ResponseUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SecretLogic;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.PrincipalDetails;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.admin.AdminService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.login.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

/**
 * Spring Security 에서 사용할 Authorization Filter 설정
 * @author	JiEun Chun
 * @email	je1000@com2us.com
 * @since	2023.02.16
 */
@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

	private AdminRepository adminRepository;

	private LoginService loginService;

	private AdminService adminService;

	public AuthorizationFilter(AuthenticationManager authenticationManager,
							   AdminRepository adminRepository,
							   LoginService loginService,
							   AdminService adminService) {
		super(authenticationManager);
		this.adminRepository = adminRepository;
		this.loginService = loginService;
		this.adminService = adminService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		ResponseUtils.setResponseDefaultAttribute(response);

		// Request header 에 있는 JWT Token 을 통해 인증된 사용자인지 확인하고, 권한을 ww부여함.
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String requestPath = request.getServletPath();

//		if (!requestPath.contains("swagger") && !requestPath.contains("api-docs") && !requestPath.contains("favicon") && !requestPath.equals("OPTIONS") && !requestPath.equals("/api")) {
//			System.out.println("Request Path : " + requestPath);
//			System.out.println("Authorization : " + authorizationHeader);
//		}

		if (	requestPath.equals("/api")
				|| requestPath.equals("/api/join")
				|| requestPath.equals("/api/login")
				|| requestPath.equals("/api/admin/operator/login")
				|| requestPath.equals("/api/admin/operator/partner/login")
				|| requestPath.equals("/api/admin/operator/partner/auth")
				|| requestPath.equals("/api/refreshToken")
				|| requestPath.equals("/test")
				|| requestPath.contains("/api/mybatis")
				|| requestPath.contains("/api/sample")
				|| requestPath.contains("swagger")
				|| requestPath.contains("api-docs")) {
			chain.doFilter(request, response);
			return;
		}

		Gson gson = new Gson();

		if (authorizationHeader == null || !authorizationHeader.startsWith(Constants.Auth.API_TOKEN_PREFIX)) {
			JsonObject resObj = gson.toJsonTree(C2VResponse.builder().code(Constants.ResponseCode.FAIL_ACCESS_DENIED.value()).msg("접근 권한이 없습니다.").build()).getAsJsonObject();
			response.getWriter().write(resObj.toString());
			return;
		}

		String accessToken = authorizationHeader.replace(Constants.Auth.API_TOKEN_PREFIX, "");
		String adminId = null;
		String permission = null;

		try {

			Claims claims = Jwts.parserBuilder()
								.requireIssuer(Constants.Auth.API_TOKEN_ISSUER)
								.setSigningKey(SecretLogic.convertStringToPublicKey(Constants.Auth.API_TOKEN_PUBLIC_KEY))
								.build()
								.parseClaimsJws(accessToken)
								.getBody();

			byte[] decodedBytes = Base64.getDecoder().decode(claims.get(Constants.Auth.API_TOKEN_CLAIMS).toString());
			Map<String, Object> tmpMap = gson.fromJson(SecretLogic.AesDecrypt(decodedBytes), Map.class);
			//adminId가 Long형이라 인증 태우면서 Double형으로 tmpMap에 쌓임
			adminId = String.format("%1$,.0f",tmpMap.get("adminId"));
			permission = (String) tmpMap.get("permission");



		} catch (ExpiredJwtException eje) {
			log.error(eje.getMessage());
			JsonObject resObj = gson.toJsonTree(C2VResponse.builder().code(Constants.ResponseCode.FAIL_TOKEN_EXPIRED.value()).msg("토큰이 만료되었습니다.").build()).getAsJsonObject();
			response.getWriter().write(resObj.toString());
		} catch (SignatureException se) {
			log.error(se.getMessage());
			JsonObject resObj = gson.toJsonTree(C2VResponse.builder().code(Constants.ResponseCode.FAIL_INVALID_TOKEN_SIGNATURE.value()).msg("토큰 서명이 잘못되었습니다.").build()).getAsJsonObject();
			response.getWriter().write(resObj.toString());
		} catch (JwtException je) {
			log.error(je.getMessage());
			JsonObject resObj = gson.toJsonTree(C2VResponse.builder().code(Constants.ResponseCode.FAIL_TOKEN_INVALID.value()).msg("잘못된 토큰입니다.").build()).getAsJsonObject();
			response.getWriter().write(resObj.toString());
		} catch (UncategorizedSQLException use) {
			log.error(use.getMessage());
			JsonObject resObj = gson.toJsonTree(C2VResponse.builder().code(Constants.ResponseCode.FAIL_ACCESS_DENIED.value()).msg("권한이 없는 메뉴입니다.").build()).getAsJsonObject();
			response.getWriter().write(resObj.toString());
			return;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			JsonObject resObj = gson.toJsonTree(C2VResponse.builder().code(Constants.ResponseCode.FAIL_TOKEN_EXCEPTION.value()).msg("토큰 인증 과정에서 예외가 발생하였습니다.").build()).getAsJsonObject();
			response.getWriter().write(resObj.toString());
		}

		if (StringUtils.isNotBlank(adminId) && StringUtils.isNotBlank(permission)) {
			request.setAttribute("adminId", adminId);
			request.setAttribute("permission", permission);


			PrincipalDetails principalDetails = new PrincipalDetails(loginService.findByAdminId(adminId));
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		}
	}
}
