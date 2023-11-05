
# Social Media API

Backend service for a social media platform with the ability to create a profile, upload publications, send messages and subscribe to other users




## Tech Stack

- Java 17
- Spring Framework (Boot, Data, Security)
- PostgreSQL
- Swagger

## Features

- Registration and JWT Authentication
- Upload images and posts
- View activity feed
- Send messages to other users


## Environment Variables


`DB_URL` url for db

`DB_USERNAME` username for db

`DB_PASSWORD` password for db


## Run Locally

Clone the project

```bash
  git clone https://github.com/greenblat17/social-media.git
```

Go to the project directory

```bash
  cd social-media
```

Set environment variables

```bash
  export \
  DB_URL=postgresql://host:port/database \
  DB_USERNAME=username \
  DB_PASSWORD=password 
```

Start the server

```bash
  mvn spring-boot:run
```


## API Reference

### Auth

#### Register user

```http
  POST /api/v1/auth/register
```

| Parameter  | Type     | Description                   |
|:-----------|:---------|:------------------------------|
| `username` | `string` | **Required**. Unique username |
| `email`    | `string` | **Required**. User email      |
| `password` | `string` | **Required**. User password   |

```json
{
  "username": "",
  "email": "",
  "password": ""
}
```

#### Login user

```http
  POST /api/v1/auth/login
```

| Parameter  | Type     | Description                   |
|:-----------|:---------|:------------------------------|
| `username` | `string` | **Required**. Unique username |
| `password` | `string` | **Required**. User password   |

```json
{
  "username": "",
  "password": ""
}
```

### Activity Feed

#### Get all posts

```http
  GET /api/v1/feeds/posts?page_id=?
```

| Parameter | Type  | Description                                  |
|:----------|:------|:---------------------------------------------|
| `page_id` | `int` | **Not Required**. page number for pagination |

### Post

#### Add post

```http
  POST /api/v1/post/
```

| Parameter | Type     | Description                                  |
|:----------|:---------|:---------------------------------------------|
| `images`  | `list`   | **Not Required**. Multipart list with images |
| `title`   | `string` | **Required**. Post title                     |
| `content` | `string` | **Required** Post content                    |


```json
{
  "title": "",
  "content": ""
}
```

#### Update post

```http
  PUT /api/v1/post/${id}
```

| Parameter |   Type   | Description                                  |
|:----------|:--------:|:---------------------------------------------|
| `id`      |  `int`   | **Required**. Post ID                        |
| `images`  |  `list`  | **Not Required**. Multipart list with images |
| `title`   | `string` | **Required**. Post title                     |
| `content` | `string` | **Required** Post content                    |


```json
{
  "title": "",
  "content": ""
}
```

#### Delete post

```http
  DELETE /api/v1/post/${id}
```

| Parameter |   Type   | Description                                  |
|:----------|:--------:|:---------------------------------------------|
| `id`      |  `int`   | **Required**. Post ID                        |

#### Get users post

```http
  DELETE /api/v1/post/by-user/${id}
```

| Parameter |   Type   | Description           |
|:----------|:--------:|:----------------------|
| `id`      |  `int`   | **Required**. User ID |

### User

#### Follow to another user

```http
  POST /api/v1/users/follow/${id}
```

| Parameter |   Type   | Description                     |
|:----------|:--------:|:--------------------------------|
| `id`      |  `int`   | **Required**. Following user ID |


#### Follow to another user

```http
  POST /api/v1/users/friend/${id}?is_friend=?
```

| Parameter  |   Type    | Description                                |
|:-----------|:---------:|:-------------------------------------------|
| `id`       |   `int`   | **Required**. Friend user ID               |
| `is_friend`| `boolean` | **Required**. Accept or not friend requist |

#### Unfollow to another user

```http
  POST /api/v1/users/friend/${id}
```

| Parameter  |   Type    | Description                       |
|:-----------|:---------:|:----------------------------------|
| `id`       |   `int`   | **Required**. Unfollowing user ID |

#### Send message

```http
  POST /api/v1/users/send-message
```

| Parameter  |   Type   | Description                         |
|:-----------|:--------:|:------------------------------------|
| `username` | `string` | **Required**. Username of recipient |
| `text`     | `string` | **Required**. content of messages   |

```json
{
  "username": "",
  "text": ""
}
```

## Roadmap

- Add tests

- Add S3 storage for images

- Add OAuth2 authentication

- Add docker-compose file
