package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.UserDto;
import cm.acentice.ideale.entities.User;
import cm.acentice.ideale.repositories.UserRepos;
import org.modelmapper.ModelMapper;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {

    private final UserRepos userRepos;
    private final ModelMapper modelMapper;

    public UserService(UserRepos userRepos, ModelMapper modelMapper) {
        this.userRepos = userRepos;
        this.modelMapper = modelMapper;
    }

    public UserDto create(UserDto userDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd");
        User user = modelMapper.map(userDto, User.class);
        /*LocalDateTime dd = userDto.getDateDeNaissance();
        user.setDateDeNaissance(formatter.format(dd));*/
        user = userRepos.save(user);
        userDto = modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
