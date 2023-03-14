package com.project.youtube.youtubeproject.service;

import com.project.youtube.youtubeproject.dto.CommentDto;
import com.project.youtube.youtubeproject.dto.UploadVideoResponse;
import com.project.youtube.youtubeproject.dto.VideoDto;
import com.project.youtube.youtubeproject.model.Comment;
import com.project.youtube.youtubeproject.model.Video;
import com.project.youtube.youtubeproject.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;

    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        Video savedVideo = videoRepository.save(video);

        return new UploadVideoResponse(savedVideo.getId(),savedVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        //Find the video by ID
        Video savedVideo = getVideoById(videoDto.getId());
        //Map the video dto fields to video
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setUserId(videoDto.getUserId());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());

        //save the video to the database
        videoRepository.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        Video savedVideo = getVideoById(videoId);

        String thumbnailUrl = s3Service.uploadFile(file);
        savedVideo.setThumbnailUrl(thumbnailUrl);

        videoRepository.save(savedVideo);
        return thumbnailUrl;
    }

    Video getVideoById(String videoId){
       return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id - " +videoId));
    }

    public VideoDto getVideoDetails(String videoId) {
        Video savedVideo = getVideoById(videoId);

        increaseVideoCount(savedVideo);
        userService.addVideoToHistory(videoId);
        return mapToVideoDto(savedVideo);
    }

    private void increaseVideoCount(Video savedVideo) {
        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDto likeVideo(String videoId) {
        //Get video by id
        Video videoById = getVideoById(videoId);

        //Increment Like Count
        //If user already liked the video the decrement like count
        //If user already disliked the video, then increment like count and decrement dislike count

        if(userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDisLikedVideo(videoId)) {
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }else {
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        videoRepository.save(videoById);

        return mapToVideoDto(videoById);

    }
    public VideoDto disLikeVideo(String videoId) {
        //Get video by id
        Video videoById = getVideoById(videoId);

        //Increment Like Count
        //If user already liked the video the decrement like count
        //If user already disliked the video, then increment like count and decrement dislike count

        if(userService.ifDisLikedVideo(videoId)){
            videoById.decrementDisLikes();
            userService.removeFromDisLikedVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        }else {
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        }
        videoRepository.save(videoById);

        return mapToVideoDto(videoById);
    }

    public VideoDto mapToVideoDto(Video videoById) {
        VideoDto videoDto = new VideoDto();
        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setId(videoById.getId());
        videoDto.setUserId(videoById.getUserId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDislikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());
        return videoDto;
    }

    public void addComment(String videoId, CommentDto commentDto) {
        Video video = getVideoById(videoId);
        Comment comment = new Comment();
        comment.setText(commentDto.getCommentText());
        comment.setAuthorId(commentDto.getAuthorId());

        video.addComment(comment);
        videoRepository.save(video);

    }

    public List<CommentDto> getAllComments(String videoId) {
        Video video = getVideoById(videoId);
        List<Comment> commentList = video.getCommentList();
        return commentList.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
    }

    private CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getText());
        commentDto.setAuthorId(comment.getAuthorId());
        return commentDto;
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll().stream().map(videoById -> mapToVideoDto(videoById)).collect(Collectors.toList());
    }
}
