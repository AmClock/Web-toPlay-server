package dao;

import vo.User;

public interface UserDAO {
	public int insetUser(User user);

	public User selectUser(User user);
	
	public int selectId(String id);
	
	public int selectName(String name);
	
	public int updateUser(User user);
}
