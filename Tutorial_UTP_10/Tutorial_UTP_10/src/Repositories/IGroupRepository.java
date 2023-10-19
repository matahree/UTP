package Repositories;

import java.util.List;
import DTOS.GroupDTO;
import DTOS.UserDTO;

public interface IGroupRepository extends IRepository<GroupDTO> {
	List<GroupDTO> findByName(String name);
	
	int getID(GroupDTO dto);

	List<UserDTO> usersGroups(GroupDTO dto);
}