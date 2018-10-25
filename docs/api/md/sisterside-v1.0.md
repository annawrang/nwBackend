# Api Docs 2 API documentation version 1.0
http://localhost:8080/1.0

### RESTful API documentation for Api Docs 2
Api Docs 2 documentation

---

## /posts

### /posts

* **get**: get posts with with their comments, likes, comment-likes and comment-replies
* **post**: creates a new post from a user

### /posts/user/{userNumber}

* **get**: get posts written by a user (plus the posts' comments, likes, replies, commentlikes)

### /posts/{postNumber}

* **get**: gets a post with its comments, likes, comment-likes and comment-replies
* **delete**: deletes a post with its comments, likes, comment-likes and comment-replies
* **put**: updates a post

### /posts/{postNumber}/likes

* **post**: create a post-like from a user

### /posts/{postNumber}/likes/{likeNumber}

* **delete**: delete a post-like from a user (unlike)

### /posts/{postNumber}/comments

* **post**: create a post-comment

### /posts/{postNumber}/comments/{commentNumber}

* **delete**: delete a post-comment
* **put**: update a post-comment

### /posts/{postNumber}/comments/{commentNumber}/likes

* **post**: create a post-comment like

### /posts/{postNumber}/comments/{commentNumber}/likes/{likeNumber}

* **delete**: delete a post-comment like (unlike)

### /posts/{postNumber}/comments/{commentNumber}/replies

* **post**: create a post-comment-reply

### /posts/{postNumber}/comments/{commentNumber}/replies/{commentReplyNumber}

* **put**: updates a post-comment-reply
* **delete**: deletes a post-comment-reply

### /posts/{postNumber}/comments/{commentNumber}/replies/{commentReplyNumber}/likes

* **post**: creates a post-comment-reply-like
* **delete**: deletes a post-comment-reply-like

