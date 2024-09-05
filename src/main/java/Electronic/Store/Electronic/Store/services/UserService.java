package Electronic.Store.Electronic.Store.services;

import Electronic.Store.Electronic.Store.Dtos.UserDto;

import java.util.List;

public interface UserService {

    //Create

    UserDto createUser(UserDto user);

    //update
    UserDto updateUSer(UserDto userDto, String userId);

    //delete
    void deleteUser(String userId);


    //get all users

    List<UserDto> getAllUser();

    //get single user by id

    UserDto getUserById(String userId);

    //get single user email

    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

    //Other user specific details / features
}
