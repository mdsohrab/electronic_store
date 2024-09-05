package Electronic.Store.Electronic.Store.services.impl;

import Electronic.Store.Electronic.Store.Dtos.UserDto;
import Electronic.Store.Electronic.Store.Entities.UserEntity;
import Electronic.Store.Electronic.Store.Repositories.UserRepository;
import Electronic.Store.Electronic.Store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto-entity
        UserEntity userEntity = dtoToEntity(userDto);
        UserEntity savedUser = userRepository.save(userEntity);
        //entity-dto
        UserDto userDtos = entityToDto(savedUser);
        return userDtos;
    }


    @Override
    public UserDto updateUSer(UserDto userDto, String userId) {

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found this given id"));

        userEntity.setName(userDto.getName());
        //
        userEntity.setEmail(userDto.getEmail());
        userEntity.setAbout(userDto.getAbout());
        userEntity.setGender(userDto.getGender());
        userEntity.setImageName(userDto.getImageName());
        userEntity.setPassword(userDto.getPassword());

        UserEntity updatedUser = userRepository.save(userEntity);
        UserDto updatedUserDto = entityToDto(updatedUser);

        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User id not found !!"));

        userRepository.delete(userEntity);

    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> dtoList = userEntities.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public UserDto getUserById(String userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User id not found"));
        UserDto userDto = entityToDto(userEntity);
        return userDto;

    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("email not found"));
        UserDto userDto = entityToDto(userEntity);
        return userDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<UserEntity> userEntities = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = userEntities.stream().map(users -> entityToDto(users)).collect(Collectors.toList());
        return dtoList;
    }

    //manually setup
    private UserDto entityToDto(UserEntity savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();
        // return userDto

        // By model mapper
        return modelMapper.map(savedUser, UserDto.class);

    }

    //manually setup
    private UserEntity dtoToEntity(UserDto userDto) {
//        UserEntity userEntity = UserEntity.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imageName(userDto.getImageName())
//                .build();
//
//        return userEntity;


        // By model mapper
        return  modelMapper.map(userDto, UserEntity.class);
    }
}
