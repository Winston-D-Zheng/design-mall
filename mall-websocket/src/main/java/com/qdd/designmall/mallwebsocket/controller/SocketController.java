package com.qdd.designmall.mallwebsocket.controller;

import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.security.config.ZUserDetails;
import com.qdd.designmall.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.qdd.designmall.mallwebsocket.util.SocketUserUtils.getSocketUsername;

@RestController
@RequiredArgsConstructor
public class SocketController {
    private final SecurityUserService securityUserService;

    @CrossOrigin
    @GetMapping("/socketUsername")
    ResponseEntity<String> username(){
        ZUserDetails zUserDetails = securityUserService.currentUserDetails();
        String socketUsername = getSocketUsername(UserDto.of(zUserDetails.getUserType(), zUserDetails.getUserId()));
        return ResponseEntity.ok(socketUsername);
    }
}
