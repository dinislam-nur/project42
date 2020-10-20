package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.session.SessionService;

@RestController
@AllArgsConstructor
public class LogoutController {

    private final SessionService sessionService;

    @RequestMapping(value = "/logout_token", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestHeader(value = "JSESSIONID") String token){
        return sessionService.logout(token)? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
