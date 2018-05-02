<?php
namespace articles;

class Article {
 
    public $id;
    public $title;
    public $summary;
    public $body;
    public $createdAt;
    
    public $categoryId;
    
    public $author;         
}

class ArticleAuthor {
  
    public $id;
    public $name;
    public $email;
}