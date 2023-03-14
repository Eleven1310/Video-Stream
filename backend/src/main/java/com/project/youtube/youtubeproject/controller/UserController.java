package com.project.youtube.youtubeproject.controller;

import com.project.youtube.youtubeproject.dto.UserinfoDTO;
import com.project.youtube.youtubeproject.dto.VideoDto;
import com.project.youtube.youtubeproject.model.User;
import com.project.youtube.youtubeproject.service.UserActionService;
import com.project.youtube.youtubeproject.service.UserRegistrationService;
import com.project.youtube.youtubeproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    private final UserActionService userActionService;

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt)authentication.getPrincipal();

        return userRegistrationService.registerUser(jwt.getTokenValue());
    }

    @PostMapping("/{userId}/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public boolean subscribeUser(@PathVariable String userId) {
        userService.subscribeUser(userId);
        return true;
    }

    @GetMapping("/{userId}/{videoId}/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public boolean findIfSubscribed(@PathVariable String userId, @PathVariable String videoId) {
        return userActionService.findIfSubscribed(userId,videoId);
    }

    @PostMapping("/{userId}/unSubscribe")
    @ResponseStatus(HttpStatus.OK)
    public boolean unSubscribeUser(@PathVariable String userId) {
        userService.unSubscribeUser(userId);
        return true;
    }

    @GetMapping("/{userId}/history")
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> userHistory(@PathVariable String userId) {
        return userActionService.userHistory(userId);
    }

    @GetMapping("/{userId}/subscriptions")
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> userSubscriptions(@PathVariable String userId) {
        return userActionService.userSubscriptions(userId);
    }

    @GetMapping("/{userId}/likedVideos")
    @ResponseStatus(HttpStatus.OK)
    public List<VideoDto> likedVideos(@PathVariable String userId) {
        return userActionService.likedVideos(userId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserinfoDTO findUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return userService.maptoUserinfoDto(user);
    }
}
