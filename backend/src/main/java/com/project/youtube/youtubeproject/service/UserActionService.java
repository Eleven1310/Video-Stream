package com.project.youtube.youtubeproject.service;

import com.project.youtube.youtubeproject.dto.VideoDto;
import com.project.youtube.youtubeproject.model.User;
import com.project.youtube.youtubeproject.model.Video;
import com.project.youtube.youtubeproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserActionService {
    private final UserRepository userRepository;

    private final VideoService videoService;

    private final UserService userService;

    public List<VideoDto> userHistory(String userId) {
        User user = userService.getUserById(userId);
        Set<String> videoHistory = user.getVideoHistory();
        List<Video> videoList = new ArrayList<>();
        for(String videoId: videoHistory){
            videoList.add(videoService.getVideoById(videoId));
        }
        return videoList.stream().map(video -> videoService.mapToVideoDto(video)).collect(Collectors.toList());

       // return videoHistory;
    }

    public List<VideoDto> likedVideos(String userId) {
        User user = userService.getUserById(userId);
        Set<String> likedVideos = user.getLikedVideos();
        List<Video> likedVideosList = new ArrayList<>();
        for(String videoId: likedVideos){
            likedVideosList.add(videoService.getVideoById(videoId));
        }
        return likedVideosList.stream().map(video -> videoService.mapToVideoDto(video)).collect(Collectors.toList());
    }

    public List<VideoDto> userSubscriptions(String userId) {
        User user = userService.getUserById(userId);
        Set<String> subscribedUsers = user.getSubscribedToUsers();
        List<VideoDto> subscribedVideoList = new ArrayList<>();
        List<VideoDto> videos = videoService.getAllVideos();
        for(String userid: subscribedUsers) {
            for (VideoDto videoDto : videos)
                if (videoDto.getUserId().equals(userid)) {
                    subscribedVideoList.add(videoDto);
                }
        }
        return subscribedVideoList;
    }

    public boolean findIfSubscribed(String userId, String videoId) {
        User user = userService.getUserById(userId);
        Video video = videoService.getVideoById(videoId);

        for(String userid: user.getSubscribedToUsers()){
            if(userid.equals(video.getUserId())){
                return true;
            }
        }
        return false;
    }
}
