package com.sprinboot.blog.controller;

import com.sprinboot.blog.payload.PostDto;
import com.sprinboot.blog.payload.PostResponse;
import com.sprinboot.blog.service.PostService;
import com.sprinboot.blog.utils.AppConstant;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog api
//url  http://localhost:8080/api/posts
    // @Vaild anno enales java bean annotation eg @NotNull @NotEmpty @Size in dto
    @PreAuthorize("hasRole('ADMIN')") //it will only accessable bt admin
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

//    /get all post api
//url   http://localhost:8080/api/posts
    //url for pagination  http://localhost:8080/api/posts?pageNo=0&pageSize=5 // http://localhost:8080/api/posts?pageNo=0&pageSize=5
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value="pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
            ){
        return postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
    }

//    url   http://localhost:8080/api/posts/1
    @GetMapping ("{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //url  http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')") //role based authentication
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //url http://localhost:8080/api/posts/4
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted succesfully.", HttpStatus.OK);
    }

    //Build get post by category Rest api
    // url : http://localhost:8080/api/posts/category/3
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }

}

// Before posting data we need to do login using follwind step
//1. access url http://localhost:8080/api/auth/login
//2. {
//        "usernameOrEmail":"admin@gmail.com",
//        "password":"admin"
//        }
//3. then copy : accesstoken
//4. Then go to api page -> Authorization -> Type: Bearer Token -> copy the token -> then data will send