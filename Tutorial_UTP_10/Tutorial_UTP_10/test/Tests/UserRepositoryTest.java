package Tests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import Exception.HandleException;
import DTOS.GroupDTO;
import DTOS.UserDTO;
import Repositories.IUserRepository;
import Database.Group;
import Database.User;


public final class UserRepositoryTest extends RepositoryTestBase<UserDTO, IUserRepository> {

	String url = "jdbc:postgresql://localhost:5432/postgres";
	String pgUser = "postgres";
	String pgPassword = "psqlpass";

	String userName ="name";
	String userPass ="password";

	User _user;

	@Test
	public void add() throws SQLException {
		UserDTO dto = new UserDTO( userName, userPass);
		_user.add(dto);
		Statement statement = _user.getConnection().createStatement();
		ResultSet userResult = statement.executeQuery("SELECT user_login FROM \"User\"");
		Assert.assertEquals(_user.getCount(), 1);
		userResult.next();
		Assert.assertEquals(userResult.getString(1), "name");

	}

	@Test
	public void update() throws SQLException{
		UserDTO dto = new UserDTO( "name", "description");
		_user.add(dto);
		Statement statement = _user.getConnection().createStatement();
		UserDTO dtoUpdate = new UserDTO( "name", "password_1");
		_user.update(dtoUpdate);
		ResultSet userResult = statement.executeQuery("SELECT password FROM \"User\";");
		userResult.next();
		Assert.assertEquals(userResult.getString(1), "password_1");
		Assert.assertEquals(_user.getCount(), 1);
	}
	
	@Test
	public void addOrUpdate() throws SQLException{
		UserDTO dto = new UserDTO( "name", "password");
		Statement statement = _user.getConnection().createStatement();
		_user.addOrUpdate(dto);
		ResultSet userResult = statement.executeQuery("SELECT user_login FROM \"User\";");
		userResult.next();
		Assert.assertEquals(userResult.getString(1), "name");
		Assert.assertEquals(_user.getCount(), 1);
	}

	@Test
	public void delete() throws SQLException{
		UserDTO dto = new UserDTO( "name", "password");
		_user.add(dto);
		_user.delete(dto);
		Assert.assertFalse(_user.exists(dto));
		Assert.assertEquals(_user.getCount(), 0);
	}

	@Test
	public void findById() {
	}
	
	@Test
	public void findByName() {
		UserDTO dto = new UserDTO( "name1", "password1");
		_user.add(dto);
		dto = new UserDTO( "name2", "password2");
		_user.add(dto);
		dto = new UserDTO( "so_name", "password3");
		_user.add(dto);
		List<UserDTO> l = _user.findByName("%name%");
		Assert.assertEquals(l.size(), 3);
		Assert.assertEquals(l.get(0).getLogin(), "name1");
		Assert.assertEquals(l.get(1).getPassword(), "password2");
		Assert.assertEquals(l.get(2).getPassword(), "password3");
	}
	
	@Test
	public void addUserToGroup () throws SQLException{
		UserDTO udto = new UserDTO( "name", "password");
		_user.add(udto);
		GroupDTO gdto = new GroupDTO( "name", "description");
		Group _group = new Group(_user.getConnection());
		_group.add(gdto);
		_user.addGroup(udto, gdto);
		List<GroupDTO>l = _user.usersGroups(udto);
		Assert.assertEquals(l.size(), 1);
		Assert.assertEquals(l.get(0).getName(), "name");
	}
	
	@Override
	protected IUserRepository Create() {
		try {
			_user = new User(url, pgUser, pgPassword);
			return _user;
		} catch (SQLException e) {
			throw new HandleException (e) ;
		}
	}
}