package oaktrees.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {

	BOSS(Set.of(Permission.STATISTICS_P, Permission.USER_P)),
	USER(Set.of(Permission.USER_P));
	
	private final Set<Permission> permissions;
	
	Role(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public Set<Permission> getPermissions() {
		return this.permissions;
	}
	
	public Set<SimpleGrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		for(Permission permission : permissions) {
			authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
		}
		return authorities;
	}
	
}
