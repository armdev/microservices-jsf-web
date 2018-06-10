package io.project.mongo.resources;

import io.project.mongo.domain.User;
import io.project.mongo.dto.LoginDTO;
import io.project.mongo.repositories.UserRepository;
import io.project.mongo.signer.TimeProvider;
import io.project.mongo.signer.TokenHelper;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/users")
public class LoginController {

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private TimeProvider timeProvider;


    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @CrossOrigin
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO user
          
    ) {

        try {          
            Optional<User> loggedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

            if (loggedUser.isPresent()) {
                HttpHeaders headers = new HttpHeaders();
               // headers.add("AUTH-TOKEN", loggedUser.get().getToken());
                LOGGER.info("User is exist, do login " + loggedUser.get().toString());
                return ResponseEntity.ok().header("AUTH-TOKEN", "TOKEN").body(loggedUser.get());
            }
        } catch (RestClientException e) {
            LOGGER.info("LocalizedMessage " + e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Mono.just("Some error occured"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Mono.just("No user account was found with email:"));

    }

//    @GetMapping(path = "/verify/{token}", produces = "application/json;charset=UTF-8")
//    @CrossOrigin
//    public ResponseEntity<?> verifyToken(@PathVariable String token
//    ) {
//
//        try {
//
//            LOGGER.info("!!!!!!verifyToken called!!!!!!");
//
//            if (token == null) {
//                LOGGER.error("Did not find token in the request");
//                return ResponseEntity.badRequest().body(Mono.error(new BadRequestException("Please put token in the request")));
//            }
//
//            boolean validateToken = tokenHelper.validateToken(token);
//            if (!validateToken) {
//                LOGGER.info("!!!!!!Token is not valid!!!!!!");
//                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Token is not valid");
//            }
//
//        } catch (RestClientException e) {
//            LOGGER.info("LocalizedMessage " + e.getLocalizedMessage());
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Mono.just("Some error occured"));
//        }
//        LOGGER.info("!!!!!!Token is accepted!!!!!!");
//        return ResponseEntity.accepted().body(tokenHelper.getAllClaimsFromToken(token));
//
//    }
//
//    @GetMapping(path = "/verify/user/{token}", produces = "application/json;charset=UTF-8")
//    @CrossOrigin
//    public ResponseEntity<?> findByToken(@PathVariable String token
//    ) {
//        try {
//
//            LOGGER.info("!!!!!!findByToken called!!!!!!");
//
//            if (token == null) {
//                LOGGER.error("Did not find token in the request");
//                return ResponseEntity.badRequest().body(Mono.error(new BadRequestException("Please put token in the request")));
//            }
//
//            final Optional<User> user = userRepository.findByToken(token);
//
//            if (user.isPresent()) {
//                LOGGER.info("!!!!!!User is present!!!!!!");
//                return ResponseEntity.status(HttpStatus.OK).body(user.get());
//            }
//        } catch (RestClientException e) {
//            LOGGER.info("LocalizedMessage " + e.getLocalizedMessage());
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(Mono.just("Some error occured"));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Did not find any user with given token");
//
//    }

}
