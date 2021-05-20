package oaktrees.security;

public enum Permission {
	
	STATISTICS_P("statistics_p"),
	USER_P("user_p");
	
	private final String permission;

	Permission(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() {
		return this.permission;
	}

}
