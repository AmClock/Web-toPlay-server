package service;

import vo.User;

public interface UserService {
	public int signUp(User user);

	public User login(User user);

	public Boolean checkId(String id);

	public Boolean checkName(String name);
	
	public Boolean userUpdate(User user);
}
