package ecommerce_project.post_service.service;

import ecommerce_project.post_service.dto.PostDto;
import ecommerce_project.post_service.mapper.PostMapper;
import ecommerce_project.post_service.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Flux<PostDto> getAllPosts() {
        return this.postRepository.findAll()
                .map(PostMapper::toDto);
    }

    public Flux<PostDto> getAllPosts(Integer page, Integer size) {
        return this.postRepository.findBy(PageRequest.of(page,size))
                .map(PostMapper::toDto);
    }

    public Mono<PostDto> getPostById(BigInteger id) {
        return this.postRepository.findById(id)
                .map(PostMapper::toDto);
    }

    public Flux<PostDto> getAllByBlog(BigInteger blogId) {
        return this.postRepository.findByBlog(blogId)
                .map(PostMapper::toDto);
    }

    public Flux<PostDto> getAllByAuthor(BigInteger author) {
        return this.postRepository.findByAuthor(author)
                .map(PostMapper::toDto);
    }

    public Flux<PostDto> getAllByAuthorAndBlog(BigInteger author, BigInteger blogId) {
        return this.postRepository.findByAuthorAndBlog(author,blogId)
                .map(PostMapper::toDto);
    }

    public Mono<PostDto> createPost(Mono<PostDto> postDto) {
        return postDto.map(PostMapper::toEntity)
                .flatMap(this.postRepository::save)
                .map(PostMapper::toDto);
    }

    public Mono<PostDto> updatePost(Mono<PostDto> postDto, BigInteger id) {
        return this.postRepository.findById(id)
                .flatMap(entity -> postDto)
                .map(PostMapper::toEntity)
                .doOnNext(c -> c.setId(id))
                .flatMap(this.postRepository::save)
                .map(PostMapper::toDto);
    }

    public Mono<Void> deletePost(BigInteger id) {
        return this.postRepository.deleteById(id);
    }

}
