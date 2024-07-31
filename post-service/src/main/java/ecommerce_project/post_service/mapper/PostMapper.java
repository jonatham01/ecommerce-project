package ecommerce_project.post_service.mapper;

import ecommerce_project.post_service.dto.PostDto;
import ecommerce_project.post_service.entity.Post;

public class PostMapper {

    public static PostDto toDto(Post post) {
        PostDto dto =new PostDto();
        dto.setAuthor(post.getAuthor());
        dto.setBlog(post.getBlog());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setBusinessId(post.getBusinessId());
        dto.setState(post.getState());
        dto.setImageUrl(post.getImageUrl());
        dto.setCreationDate(post.getCreationDate());
        dto.setUpdatedDate(post.getUpdatedDate());
        return dto;
    }

    public static Post toEntity(PostDto dto) {
        Post post = new Post();
        post.setAuthor(dto.getAuthor());
        post.setBlog(dto.getBlog());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setBusinessId(dto.getBusinessId());
        post.setState(dto.getState());
        post.setImageUrl(dto.getImageUrl());
        post.setCreationDate(dto.getCreationDate());
        post.setUpdatedDate(dto.getUpdatedDate());
        return post;
    }
}
