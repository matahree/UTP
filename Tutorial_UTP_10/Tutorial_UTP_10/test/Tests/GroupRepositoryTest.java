package Tests;

import DTOS.GroupDTO;
import DTOS.UserDTO;
import Database.Group;
import Database.User;
import Exception.HandleException;
import Repositories.IGroupRepository;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class GroupRepositoryTest extends RepositoryTestBase<GroupDTO, IGroupRepository>  {

	String url = "jdbc:postgresql://localhost:5432/postgres";
	String user = "postgres";
	String password = "root";

	@Test
	public void add() throws SQLException {
		GroupDTO dto = new GroupDTO( "name", "description");
		_repository.add(dto);
		Statement statement = _repository.getConnection().createStatement();
		ResultSet groupResult = statement.executeQuery("SELECT group_name FROM \"Group\"");
		Assert.assertEquals(_repository.getCount(), 1);
		groupResult.next();
		Assert.assertEquals(groupResult.getString(1), "name");
	}

	@Test
	public void update() throws SQLException{
		GroupDTO dto = new GroupDTO( "name", "description");
		_repository.add(dto);
		Statement statement = _repository.getConnection().createStatement();
		GroupDTO dtoUpdate = new GroupDTO(_repository.getID(dto), "name_1", "description_1");
		_repository.update(dtoUpdate);
		ResultSet groupResult = statement.executeQuery("SELECT group_name FROM \"Group\";");
		groupResult.next();
		Assert.assertEquals(groupResult.getString(1), "name_1");
		Assert.assertEquals(_repository.getCount(), 1);
	}
	
	@Test
	public void addOrUpdate() throws SQLException{
		GroupDTO dto = new GroupDTO( "name", "description");
		Statement statement = _repository.getConnection().createStatement();
		_repository.addOrUpdate(dto);
		ResultSet groupResult = statement.executeQuery("SELECT group_name FROM \"Group\";");
		groupResult.next();
		Assert.assertEquals(groupResult.getString(1), "name");
		Assert.assertEquals(_repository.getCount(), 1);
	}

	@Test
	 public void delete() throws SQLException {
		GroupDTO dto = new GroupDTO( "name", "description");
		_repository.add(dto);
		_repository.delete(dto);
		Assert.assertFalse(_repository.exists(dto));
		Assert.assertEquals(_repository.getCount(), 0);
	}

	@Test
	public void findById() throws SQLException {
		GroupDTO dto = new GroupDTO( "name", "description");
		_repository.add(dto);
		Statement statement = _repository.getConnection().createStatement();
		ResultSet groupID = statement.executeQuery("SELECT group_id FROM \"Group\" WHERE group_name LIKE \'name\';");
		groupID.next();
		_repository.findById(groupID.getInt(1));
		Assert.assertEquals(_repository.getCount(), 1);
		Assert.assertEquals(_repository.findById(groupID.getInt(1)).getName(), "name");
	}
	
	@Test
	public void findByName() throws SQLException {
		GroupDTO dto = new GroupDTO( "name", "description");
		_repository.add(dto);
		List <GroupDTO> l = _repository.findByName("name");
		Assert.assertEquals(l.size(), 1);
		Assert.assertEquals(l.get(0).getDescription(), "description");
	}

	@Override
	protected IGroupRepository Create() {
		try {
			_repository = new Group(url, user, password);
			return _repository;
		} catch (Exception e) {
			throw new HandleException( e );
		}
	}

	@Test
	public void addGroupToUser () throws SQLException{
		User _user = new User(_repository.getConnection());
		UserDTO udto = new UserDTO( "name", "password");
		_user.add(udto);
		GroupDTO gdto = new GroupDTO( "name", "description");
		_repository.add(gdto);
		_user.addGroup(udto, gdto);
		List<UserDTO>l = _repository.usersGroups(gdto);
		Assert.assertEquals(l.size(), 1);
		Assert.assertEquals(l.get(0).getLogin(), "name");

	}
}
