package com.redditclone.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String userName;
    private String url;
    private String description;
    private String subredditName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
}
