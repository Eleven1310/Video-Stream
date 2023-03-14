package com.project.youtube.youtubeproject.repository;

import com.project.youtube.youtubeproject.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
