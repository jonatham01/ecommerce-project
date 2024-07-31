package ecommerce_project.post_service.repository;

import ecommerce_project.post_service.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

 @Repository
public interface PostRepository extends ReactiveCrudRepository<Post, BigInteger> {
    Flux<Post> findBy(Pageable pageable);
    Flux<Post> findByBlog(BigInteger blog);
    Flux<Post> findByAuthor(BigInteger author);
    Flux<Post> findByAuthorAndBlog(BigInteger author, BigInteger blog);
}
