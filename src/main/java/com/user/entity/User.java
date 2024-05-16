package com.user.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User implements UserDetails {
	
	private int id;
	private int sidoCode;
	private int gugunCode;
	private String name;
	private String email;
	private String password;
	private String comment;
	private String userCode;
	private String profileImagePath;
	private String role;
	
	public void modifySidoCode(int sidoCode) {
		if(sidoCode < 1 || (sidoCode > 8 && sidoCode < 31) || sidoCode > 39) {
			throw new IllegalArgumentException("잘못된 시 도 코드입니다.");
		}
		
		this.sidoCode = sidoCode;
	}
	
	public void modifyGugunCode(int gugunCode) {
		if(gugunCode < 1 || gugunCode > 31) {
			throw new IllegalArgumentException("잘못된 시 군 구 코드입니다.");
		}
		
		this.gugunCode = gugunCode;
	}
	
	public void modifyName(String name) {
		this.name = name;
	}
	
	public void modifyComment(String comment) {
		this.comment = comment;
	}
	
	public void modifyPassword(String password) {
		this.password = password;
	}
	
	public void modifyUserProfileImage(String newImgPath) {
		this.profileImagePath = newImgPath;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<String> roles = new ArrayList<>();
		roles.add(role);
		
		return roles.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
