package com.sprinboot.blog.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private long id;

    //title should not be null or empty
    // title should have at least 2 character
    @NotEmpty(message = "Title may not be empty")
    @Size(min = 2, message = "Post title should have at least 2 character")
    private String title;

    //post description should not be notnull or empty
    // post description should have at least 2 character
    @NotEmpty (message = "Description may not be empty")
    @Size(min = 10, message = "Post description should have at least 2 character")
    private String description;

    //post description should not be notnull or empty
    @NotEmpty(message = "Content may not be empty")
    private String content;

    private Set<CommentDto> comments;

    private Long categoryId;

}
