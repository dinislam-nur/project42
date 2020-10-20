package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.user;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.CredentialsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.ErrorMessageDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.session.SessionService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final SessionService sessionService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody CredentialsDTO credentials,
                                @RequestHeader("TABLE_ID") UUID tableId) {
        val session = sessionService.loginWithCredentials(credentials, tableId);
        return session.isPresent() ?
                ResponseEntity.ok(session.get()) : ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessageDTO("Неправильный логин или пароль."));
    }

    @PostMapping(value = "/login_token")
    public ResponseEntity loginToken(@RequestHeader(value = "JSESSIONID") String token) {
        val session = sessionService.loginWithToken(token);
        return session.isPresent() ? ResponseEntity.ok(session.get()) : ResponseEntity.badRequest().build();
    }
}
