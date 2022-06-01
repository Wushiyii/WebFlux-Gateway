package com.wushiyii.controller;

import com.wushiyii.model.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping(value = "getUserById", method = RequestMethod.GET)
    public Mono<UserDTO> getUserById(Long userId) {

        return Mono.just(new UserDTO("tom", userId));
    }

    @RequestMapping(value = "getAllUser", method = RequestMethod.GET)
    public Flux<UserDTO> getAllUser() {

        List<UserDTO> userList = Arrays.asList(new UserDTO("tom", 15L), new UserDTO("lisa", 17L));
        return Flux.fromIterable(userList);
    }
}
