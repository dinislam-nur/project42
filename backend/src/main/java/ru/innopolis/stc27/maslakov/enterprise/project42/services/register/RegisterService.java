package ru.innopolis.stc27.maslakov.enterprise.project42.services.register;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SignupGuestDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SignupStaffDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.Optional;

public interface RegisterService {

    Optional<User> signup(SignupGuestDTO data);

    Optional<User> signup(SignupStaffDTO data);

}
