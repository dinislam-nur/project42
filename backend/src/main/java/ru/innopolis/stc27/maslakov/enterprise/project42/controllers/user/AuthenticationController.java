package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.user;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.CredentialsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.ErrorMessageDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.session.Session;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.session.SessionService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final SessionService sessionService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody CredentialsDTO credentials,
                                @RequestHeader(value = "TABLE_ID", required = false) UUID tableId) {
        val session = sessionService.loginWithCredentials(credentials, tableId);
        return session.isPresent() ?
                ResponseEntity.ok(session.get()) : ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessageDTO("Неправильный логин или пароль."));
    }

    @GetMapping(value = "/session")
    public ResponseEntity loginToken() {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        val details = authentication.getDetails();
        return details instanceof Session ? ResponseEntity.ok(details) :
                ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout() {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        val session = (Session) authentication.getDetails();
        return sessionService.logout(session.getToken()) ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
