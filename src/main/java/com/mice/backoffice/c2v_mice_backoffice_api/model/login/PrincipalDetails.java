package com.mice.backoffice.c2v_mice_backoffice_api.model.login;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 인증 요청 사용자 객체
 * @author	JiEun Chun
 * @email	je1000@com2us.com
 * @since	2023.02.16
 */
@Data
public class PrincipalDetails implements UserDetails {
	private static final long serialVersionUID = 482274885553322754L;
	private AdminEntity adminEntity;

	public PrincipalDetails(AdminEntity adminEntity) {
		this.adminEntity = adminEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new SimpleGrantedAuthority("ADMIN"));
		return collect;
	}

	@Override
	public String getPassword() {
		return adminEntity.getAccountPassword();
	}

	@Override
	public String getUsername() {
		return adminEntity.getAdminId().toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
