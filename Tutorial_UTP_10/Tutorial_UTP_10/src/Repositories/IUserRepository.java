package Repositories;

import java.util.List;

import DTOS.UserDTO;

public interface IUserRepository extends IRepository<UserDTO> {

	List<UserDTO> findByName(String username);
}