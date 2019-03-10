package service;

import dao.UserDAO;
import vo.User;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public int signUp(User user) {
		// TODO Auto-generated method stub
		return userDAO.insetUser(user);
	}

	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return userDAO.selectUser(user);
	}

	@Override
	public Boolean checkId(String id) {
		// TODO Auto-generated method stub
		return 0==userDAO.selectId(id);
	}

	@Override
	public Boolean checkName(String name) {
		// TODO Auto-generated method stub
		return 0 == userDAO.selectName(name);
	}

	@Override
	public Boolean userUpdate(User user) {
		// TODO Auto-generated method stub
		return 1== userDAO.updateUser(user);
	}
	
}
