# Articles - PHP Restful API Microservice

## Installation

Change credentials in `config/db.config.php`

### Database Schema
```sql
CREATE SCHEMA `blog` DEFAULT CHARACTER SET utf8;

CREATE TABLE `blog`.`categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `blog`.`authors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `blog`.`articles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `summary` TEXT(1000) NOT NULL,
  `body` TEXT NOT NULL,
  `createdAt` DATE  NOT NULL,
  `categoryId` INT NOT NULL,
  `authorId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `categoryId_idx` (`categoryId` ASC),
  INDEX `authorId_idx` (`authorId` ASC),
  CONSTRAINT `category_fk`
    FOREIGN KEY (`categoryId`)
    REFERENCES `blog`.`categories` (`id`),
  CONSTRAINT `author_fk`
    FOREIGN KEY (`authorId`)
    REFERENCES `blog`.`authors` (`id`));
```

### Test Data
```sql
INSERT INTO `blog`.`authors` (`id`, `name`, `email`)
	VALUES (0, 'Tomas Tulka', 'tomas.tulka@gmail.com');
  
INSERT INTO `blog`.`categories` (`id`, `name`)
	VALUES (0, 'PHP'), (0, 'Java');

INSERT INTO `blog`.`articles` (`id`, `title`, `summary`, `body`, `createdAt`, `categoryId`, `authorId`)
  VALUES (0, 'Sample PHP blog post', 
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed hendrerit a ipsum in faucibus. Nullam volutpat nunc quis orci sodales fermentum. Fusce a mi at neque maximus condimentum eu ac nulla. Nunc non tellus vehicula, scelerisque quam eu, convallis metus. Maecenas accumsan libero vitae ipsum elementum pulvinar.',
	'Sed vitae tincidunt magna. Sed pretium neque commodo mauris lobortis, quis finibus dolor malesuada. Duis molestie tellus quis orci venenatis, ac pretium quam malesuada. Vivamus congue justo nulla, sit amet pharetra purus condimentum at. Donec semper a nunc nec dapibus. Sed tristique vel ipsum vitae euismod. Aenean vel nibh ac diam ullamcorper porta id at purus. Etiam venenatis pulvinar purus id imperdiet. Mauris euismod tempus lacinia. Proin gravida nisi a tortor vestibulum imperdiet.<br>Proin fermentum iaculis tortor, sit amet tempor dolor cursus pretium. Integer libero justo, bibendum et dolor eget, hendrerit ornare elit. Mauris sed congue mi. Nam ornare venenatis enim eget ultrices. Sed convallis dignissim mollis. Vivamus ullamcorper augue et libero imperdiet convallis. Etiam porta nunc eros, vestibulum bibendum nunc pretium nec. Sed vitae nisi eros. Vestibulum sed augue eu mi efficitur finibus nec tempor magna. Nulla est odio, cursus in consectetur eget, vulputate nec ex. Sed porta lorem ac turpis convallis, sit amet fermentum orci dictum. Maecenas dui augue, hendrerit quis urna id, condimentum iaculis enim. In mollis, magna faucibus interdum hendrerit, diam mi tempus sem, nec feugiat ipsum velit et ipsum. Nullam in est sem. Donec suscipit mi non purus ornare vestibulum.<br>Praesent porta sagittis diam non interdum. Vivamus quis ante ut libero auctor tempus. Donec vel tellus pharetra, consectetur orci eget, semper metus. Vestibulum pulvinar sapien quis turpis sodales dignissim. Quisque sem diam, vulputate eget congue placerat, mattis at mi. Duis congue dolor scelerisque eros lacinia laoreet. Praesent ullamcorper lectus quis urna interdum bibendum. Vivamus semper vulputate laoreet. Proin lorem orci, luctus quis blandit in, facilisis ac dolor. Maecenas tristique hendrerit dolor, eget cursus elit mattis vel. Pellentesque ultrices erat nec tellus viverra convallis. Aenean ut accumsan quam. Nullam euismod nec nisl ac suscipit.',
	'2018-05-01', 1, 1), 
    (0,  'Another PHP blog post', 
    'Donec id pellentesque elit, sit amet accumsan mi. Nunc pharetra nisl non arcu varius, vitae blandit augue sagittis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque et purus mollis nunc lobortis faucibus. Maecenas non mi sed purus molestie commodo. Mauris pellentesque lacus rhoncus gravida consectetur. Nulla in dui id risus molestie auctor. Integer volutpat varius metus eu auctor.',
	'Sed vitae tincidunt magna. Sed pretium neque commodo mauris lobortis, quis finibus dolor malesuada. Duis molestie tellus quis orci venenatis, ac pretium quam malesuada. Vivamus congue justo nulla, sit amet pharetra purus condimentum at. Donec semper a nunc nec dapibus. Sed tristique vel ipsum vitae euismod. Aenean vel nibh ac diam ullamcorper porta id at purus. Etiam venenatis pulvinar purus id imperdiet. Mauris euismod tempus lacinia. Proin gravida nisi a tortor vestibulum imperdiet.<br>Proin fermentum iaculis tortor, sit amet tempor dolor cursus pretium. Integer libero justo, bibendum et dolor eget, hendrerit ornare elit. Mauris sed congue mi. Nam ornare venenatis enim eget ultrices. Sed convallis dignissim mollis. Vivamus ullamcorper augue et libero imperdiet convallis. Etiam porta nunc eros, vestibulum bibendum nunc pretium nec. Sed vitae nisi eros. Vestibulum sed augue eu mi efficitur finibus nec tempor magna. Nulla est odio, cursus in consectetur eget, vulputate nec ex. Sed porta lorem ac turpis convallis, sit amet fermentum orci dictum. Maecenas dui augue, hendrerit quis urna id, condimentum iaculis enim. In mollis, magna faucibus interdum hendrerit, diam mi tempus sem, nec feugiat ipsum velit et ipsum. Nullam in est sem. Donec suscipit mi non purus ornare vestibulum.<br>Praesent porta sagittis diam non interdum. Vivamus quis ante ut libero auctor tempus. Donec vel tellus pharetra, consectetur orci eget, semper metus. Vestibulum pulvinar sapien quis turpis sodales dignissim. Quisque sem diam, vulputate eget congue placerat, mattis at mi. Duis congue dolor scelerisque eros lacinia laoreet. Praesent ullamcorper lectus quis urna interdum bibendum. Vivamus semper vulputate laoreet. Proin lorem orci, luctus quis blandit in, facilisis ac dolor. Maecenas tristique hendrerit dolor, eget cursus elit mattis vel. Pellentesque ultrices erat nec tellus viverra convallis. Aenean ut accumsan quam. Nullam euismod nec nisl ac suscipit.',
	'2018-05-02', 1, 1), 
    (0,  'Java blog post', 
    'Praesent porta sagittis diam non interdum. Vivamus quis ante ut libero auctor tempus. Donec vel tellus pharetra, consectetur orci eget, semper metus. Vestibulum pulvinar sapien quis turpis sodales dignissim. Quisque sem diam, vulputate eget congue placerat, mattis at mi. Duis congue dolor scelerisque eros lacinia laoreet.',
	'Sed vitae tincidunt magna. Sed pretium neque commodo mauris lobortis, quis finibus dolor malesuada. Duis molestie tellus quis orci venenatis, ac pretium quam malesuada. Vivamus congue justo nulla, sit amet pharetra purus condimentum at. Donec semper a nunc nec dapibus. Sed tristique vel ipsum vitae euismod. Aenean vel nibh ac diam ullamcorper porta id at purus. Etiam venenatis pulvinar purus id imperdiet. Mauris euismod tempus lacinia. Proin gravida nisi a tortor vestibulum imperdiet.<br>Proin fermentum iaculis tortor, sit amet tempor dolor cursus pretium. Integer libero justo, bibendum et dolor eget, hendrerit ornare elit. Mauris sed congue mi. Nam ornare venenatis enim eget ultrices. Sed convallis dignissim mollis. Vivamus ullamcorper augue et libero imperdiet convallis. Etiam porta nunc eros, vestibulum bibendum nunc pretium nec. Sed vitae nisi eros. Vestibulum sed augue eu mi efficitur finibus nec tempor magna. Nulla est odio, cursus in consectetur eget, vulputate nec ex. Sed porta lorem ac turpis convallis, sit amet fermentum orci dictum. Maecenas dui augue, hendrerit quis urna id, condimentum iaculis enim. In mollis, magna faucibus interdum hendrerit, diam mi tempus sem, nec feugiat ipsum velit et ipsum. Nullam in est sem. Donec suscipit mi non purus ornare vestibulum.<br>Praesent porta sagittis diam non interdum. Vivamus quis ante ut libero auctor tempus. Donec vel tellus pharetra, consectetur orci eget, semper metus. Vestibulum pulvinar sapien quis turpis sodales dignissim. Quisque sem diam, vulputate eget congue placerat, mattis at mi. Duis congue dolor scelerisque eros lacinia laoreet. Praesent ullamcorper lectus quis urna interdum bibendum. Vivamus semper vulputate laoreet. Proin lorem orci, luctus quis blandit in, facilisis ac dolor. Maecenas tristique hendrerit dolor, eget cursus elit mattis vel. Pellentesque ultrices erat nec tellus viverra convallis. Aenean ut accumsan quam. Nullam euismod nec nisl ac suscipit.',
	'2018-05-02', 2, 1);

```

## Usage

### List articles
```
curl http://localhost/articles
curl http://localhost/articles?categoryId=1
curl http://localhost/articles?authorId=1
curl http://localhost/articles?categoryId=1&authorId=1
```

### Show an article
```
curl http://localhost/articles/1
```

### Create a new article
`post.json`
```
{ "title": "New article", 
  "summary": "Lorem ipsum", 
  "body": "Lorem ipsum dolor sit amet", 
  "createdAt": "2018-05-03", 
  "categoryId": 1, 
  "authorId": 1 }
```
```
curl http://localhost/articles -X POST -H "Content-Type: application/json" -d @post.json
{"id":4}
```

### Update an article
`put.json`
```
{ "title": "Updated article", 
  "summary": "Lorem ipsum", 
  "body": "Lorem ipsum dolor sit amet", 
  "createdAt": "2018-05-03", 
  "categoryId": 1, 
  "authorId": 1 }
```
```
curl http://localhost/articles/4 -X PUT -H "Content-Type: application/json" -d @put.json
```

### Delete an article
```
curl http://localhost/articles/4 -X DELETE
```