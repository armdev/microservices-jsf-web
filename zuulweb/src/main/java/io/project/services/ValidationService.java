package io.project.services;


import io.project.model.Claim;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author armena
 */
@Service
public class ValidationService {
    
    @Autowired
    private RestTemplate restTemplate;

    public Try<Claim> findUser(String token) {
        return Try.of(() -> restTemplate.getForObject("http://auth/api/v2/users/verify/user/{token}", Claim.class, token));

    }

    
}
