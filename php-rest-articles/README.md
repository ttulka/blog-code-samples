# Blog Back-end Services

## Installation

Change credentials in `config/db.config.php`

## Usage

### List articles
```
curl -X GET http://localhost/articles.php
curl -X GET http://localhost/articles.php?categoryId=123
curl -X GET http://localhost/articles.php?authorId=123
curl -X GET http://localhost/articles.php?categoryId=123&authorId=123
```

### Show an article
```
curl -X GET http://localhost/articles.php?id=123
```

### Create a new article
`post.dat`
```
{ "title": "New article", 
  "summary": "Lorem ipsum", 
  "body": "Lorem ipsum dolor sit amet", 
  "createdAt": "2018-05-03", 
  "categoryId": 1, 
  "authorId": 1 }
```
```
curl -v http://localhost/articles.php -X POST -H "Content-Type: application/json" -d @post.dat
{"id":4}
```

### Update an article
`put.dat`
```
{ "title": "Updated article", 
  "summary": "Lorem ipsum", 
  "body": "Lorem ipsum dolor sit amet", 
  "createdAt": "2018-05-03", 
  "categoryId": 1, 
  "authorId": 1 }
```
```
curl http://localhost/articles.php?id=4 -X PUT -H "Content-Type: application/json" -d @put.dat
```

### Delete an article
```
curl http://localhost/articles.php?id=4 -X DELETE
```