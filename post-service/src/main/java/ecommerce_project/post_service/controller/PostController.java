package ecommerce_project.post_service.controller;

import ecommerce_project.post_service.dto.PostDto;
import ecommerce_project.post_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping
    public Flux<PostDto> allPosts(){
        return this.postService.getAllPosts();
    }

    @GetMapping("paginated")
    public Mono<List<PostDto>> allPosts(
            @RequestParam (defaultValue = "1") int page,
            @RequestParam (defaultValue = "5") int size){
        return this.postService.getAllPosts(page,size)
                .collectList();
    }

    @GetMapping("{id}")
    public Mono<PostDto> getPost(@PathVariable BigInteger id){
        return this.postService.getPostById(id);
    }

    @PostMapping
    public Mono<PostDto> createPost(@RequestBody Mono<PostDto> mono){
        return  this.postService.createPost(mono);
    }

    @PutMapping("{id}")
    public Mono<PostDto> updatePost(@PathVariable BigInteger id, @RequestBody  Mono<PostDto> mono){
       return  this.postService.updatePost(mono,id);
    }
    @DeleteMapping("{id}")
    public Mono<Void> deletePost(@PathVariable BigInteger id){
        return  this.postService.deletePost(id);
    }
}
