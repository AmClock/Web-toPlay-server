package dao;

import org.apache.ibatis.session.SqlSession;

import vo.User;

public class UserDAOImpl implements UserDAO {
	private SqlSession session;

	public void setSession(SqlSession session) {
		this.session = session;
	}

	@Override
	public int insetUser(User user) {
		// TODO Auto-generated method stub
		return session.insert("users.isertUser", user);
	}

	@Override
	public User selectUser(User user) {
		// TODO Auto-generated method stub
		return session.selectOne("users.selectUser",user);
	}

	@Override
	public int selectId(String id) {
		// TODO Auto-generated method stub
		return session.selectOne("users.selectId",id);
	}

	@Override
	public int selectName(String name) {
		// TODO Auto-generated method stub
		return session.selectOne("users.selectName",name);
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return session.update("users.updateUser",user);
	}
	
	

}// UserDAOImpl end
