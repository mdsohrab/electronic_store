package Electronic.Store.Electronic.Store.Controller;


import Electronic.Store.Electronic.Store.Dtos.ApiResponseMessage;
import Electronic.Store.Electronic.Store.Dtos.UserDto;
import Electronic.Store.Electronic.Store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //Update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId,
            @RequestBody UserDto userDto
    ) {
        UserDto userDto1 = userService.updateUSer(userDto, userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/userId")
    public ResponseEntity<ApiResponseMessage> deleteUser(
            @PathVariable("userId") String userId
    ) {
        userService.deleteUser(userId);
        ApiResponseMessage message=ApiResponseMessage.builder().message("User is deleted successfully")
                .success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // getall
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    //get single

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    //get by email


    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    //search user

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(String keyword){
        return new ResponseEntity<>(userService.searchUser(keyword),HttpStatus.OK);
    }

}
