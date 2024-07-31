package ecommerce_project.post_service.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class PostDto {
    private String title;
    private String content;
    private String state;
    private String imageUrl;
    private BigInteger businessId;
    private BigInteger blog;
    private BigInteger author;
    LocalDateTime creationDate;
    LocalDateTime updatedDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigInteger getBusinessId() {
        return businessId;
    }

    public void setBusinessId(BigInteger businessId) {
        this.businessId = businessId;
    }

    public BigInteger getBlog() {
        return blog;
    }

    public void setBlog(BigInteger blog) {
        this.blog = blog;
    }

    public BigInteger getAuthor() {
        return author;
    }

    public void setAuthor(BigInteger author) {
        this.author = author;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
