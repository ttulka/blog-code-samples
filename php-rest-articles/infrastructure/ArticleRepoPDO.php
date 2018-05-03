<?php
namespace articles;

use \PDO;

require_once __DIR__ . '/../domain/Article.php';
require_once __DIR__ . '/../domain/ArticleRepo.php';

class ArticleRepoPDO implements ArticleRepo {
 
    private $conn;
    
    private $articles_table = 'articles';
    private $articles_categories_table = 'categories';
    private $authors_table = 'authors';
  
    public function __construct(PDO $conn){                                           
        $this->conn = $conn;
    }
    
    function fetchAll($categoryId = null, $authorId = null, $start = 0, $limit = 10) {   
        $q = "SELECT a.id, a.title, a.summary, a.createdAt, a.categoryId, a.authorId, au.name authorName, au.email authorEmail
                FROM {$this->articles_table} a
                    LEFT JOIN {$this->authors_table} au ON a.authorId = au.id
                WHERE 1=1 ";
                
        $params = array('start' => (int)$start, 'limit' => (int)$limit);

        if ($categoryId) {
            $q .= " AND a.categoryid = :categoryId";
            $params['categoryId'] = (int)$categoryId;
        }
        if ($authorId) {
            $q .= " AND a.authorId = :authorId";
            $params['authorId'] = (int)$authorId;
        }                    
                    
        $q .="  ORDER BY a.createdAt DESC, a.id DESC
                LIMIT :start,:limit";
        
        $stmt = $this->conn->prepare($q);
        
        foreach ($params as $param => $value) {
            $stmt->bindValue($param, $value, PDO::PARAM_INT);
        }
        
        $stmt->execute();
        
        $articles = array();  
               
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
          $article = new Article();
          
          $article->id = (int)$row['id'];
          $article->title = $row['title'];
          $article->summary = $row['summary'];
          $article->createdAt = $row['createdAt'];
          $article->categoryId = (int)$row['categoryId'];
          
          $article->author = new ArticleAuthor();
          $article->author->id = (int)$row['authorId'];
          $article->author->name = $row['authorName'];
          $article->author->email = $row['authorEmail'];
          
          array_push($articles, $article);
        }
             
        return $articles;
    }
    
    public function fetchOne($id) {
        $q = "SELECT a.id, a.title, a.summary, a.body, a.createdAt, a.categoryId, a.authorId, au.name authorName, au.email authorEmail
                FROM {$this->articles_table} a
                    LEFT JOIN {$this->authors_table} au ON a.authorId = au.id
                WHERE a.id = :id ";
                        
        $stmt = $this->conn->prepare($q);        
        $stmt->bindValue('id', (int)$id, PDO::PARAM_INT);        
        $stmt->execute();
        
        $article = null;  
               
        if ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
          $article = new Article();
          
          $article->id = (int)$row['id'];
          $article->title = $row['title'];
          $article->summary = $row['summary'];
          $article->body = $row['body'];
          $article->createdAt = $row['createdAt'];
          $article->categoryId = (int)$row['categoryId'];
          
          $article->author = new ArticleAuthor();
          $article->author->id = (int)$row['authorId'];
          $article->author->name = $row['authorName'];
          $article->author->email = $row['authorEmail'];
        }
             
        return $article;
    }
    
    public function create(Article $article) {
        $q = "INSERT INTO {$this->articles_table} (id, title, summary, body, createdAt, categoryId, authorId)
                VALUES (0, 
                  '{$article->title}', 
                  '{$article->summary}', 
                  '{$article->body}', 
                  '{$article->createdAt}', 
                  {$article->categoryId}, 
                  {$article->authorId}
                )";
                        
        $stmt = $this->conn->prepare($q);        
        $stmt->execute(); 
        
        $article->id = $this->conn->lastInsertId();
        
        return $article;   
    }
    
    public function update($id, Article $article) {
        $q = "UPDATE {$this->articles_table}
                SET title = '{$article->title}', 
                    summary = '{$article->summary}', 
                    body = '{$article->body}', 
                    createdAt = '{$article->createdAt}', 
                    categoryId = {$article->categoryId}, 
                    authorId = {$article->authorId}
                WHERE id = :id";
                        
        $stmt = $this->conn->prepare($q);        
        $stmt->bindValue('id', (int)$id, PDO::PARAM_INT);        
        $stmt->execute();
                
        return true;    
    }
    
    public function delete($id) {
        $article = $this->fetchOne($id);
        if ($article === null) {
            return false;
        }
        
        $q = "DELETE FROM {$this->articles_table} WHERE id = :id ";
        
        $stmt = $this->conn->prepare($q);                                  
        $stmt->bindValue('id', (int)$id, PDO::PARAM_INT);        
        $stmt->execute();
        
        return true;
    }
}