/*
 * @ (#) TaiKhoan.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package Entity;

/*
 * @description:
 * @author:
 * @date:  4/21/2025
 * @version:    1.0
 */
public class TaiKhoan {
    private String username;
    private String password;
    private String role;
    
    
    
    
    
	public TaiKhoan() {
		super();
		
	}
	public TaiKhoan(String username) {
		this(username,"123",null);
	}


	public TaiKhoan(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	@Override
	public String toString() {
		return "TaiKhoan [username=" + username + ", password=" + password + ", role=" + role + "]";
	}
    
	
	
    
    
}
