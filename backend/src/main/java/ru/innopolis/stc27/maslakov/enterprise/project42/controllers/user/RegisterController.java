package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.user;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.ErrorMessageDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.SignupDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.register.RegisterService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RegisterController {

    private final RegisterService registerService;

    @ResponseBody
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody SignupDTO data) {
        Optional<User> user = registerService.signup(data);
        return user.isPresent() ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest()
                .body(new ErrorMessageDTO("Пользователь с таким именем уже существует."));
    }

    //TODO Доступ по роли ROLE_ADMIN
    @PostMapping(value = "/staff/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity staffRegister(@Valid @RequestBody SignupDTO data) {
        Optional<User> user = registerService.signup(data);
        return user.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        val message = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            message.append(error.getDefaultMessage())
                    .append(System.lineSeparator());
        });
        return message.toString();
    }

}
