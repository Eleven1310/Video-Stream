package com.project.youtube.youtubeproject.dto;

import com.project.youtube.youtubeproject.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private String userId;
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private String thumbnailUrl;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer viewCount;
}
