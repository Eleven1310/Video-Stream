package com.project.youtube.youtubeproject.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.youtube.youtubeproject.dto.UserinfoDTO;
import com.project.youtube.youtubeproject.model.User;
import com.project.youtube.youtubeproject.repository.UserRepository;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userinfoEndpoint}")
    private String userInfoEndpoint;
    private final UserRepository userRepository;

    public String registerUser(String tokenValue) {
        //Make a call to the userInfo Endpoint
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try{
            HttpResponse<String> responseString = httpClient.send(httpRequest, HttpResponse.BodyHandler.asString());
            String body = responseString.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            UserinfoDTO userInfoDTO = objectMapper.readValue(body, UserinfoDTO.class);

            Optional<User> userBySubject =  userRepository.findBySub(userInfoDTO.getSub());
            if(userBySubject.isPresent()){
                return userBySubject.get().getId();
            } else {
                User user = new User();
                user.setFirstName(userInfoDTO.getGivenName());
                user.setLastName(userInfoDTO.getFamilyName());
                user.setFullName(userInfoDTO.getName());
                user.setEmailAddress(userInfoDTO.getEmail());
                user.setSub(userInfoDTO.getSub());

                return userRepository.save(user).getId();
            }
        } catch(Exception exception) {
            throw new RuntimeException("Exception occurred while registering user", exception);
        }
    }
}
