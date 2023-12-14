package PassBoard.Assessment.Mappers;

import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Models.User;
import lombok.extern.apachecommons.CommonsLog;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component

public class UserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public UserDTO mapToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User mapToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
