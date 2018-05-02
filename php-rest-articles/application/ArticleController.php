<?php
namespace articles;

require_once __DIR__ . '/../domain/ArticleRepo.php';

class ArticleController {

  private $repo;

  public function __construct(ArticleRepo $repo){                                           
    $this->repo = $repo;
  }

  public function detailRequest($id) {
    $article = $this->repo->fetchOne((int)$id);
    
    return $article;
  }
  
  public function listRequest($params) {
    $limit = 10;
    
    $categoryId = $this->getIfSet($params, 'categoryId');   
    $authorId = $this->getIfSet($params, 'authorId');   
    $page = $this->getIfSet($params, 'page', 0);
    
    $articles = $this->repo->fetchAll((int)$categoryId, (int)$authorId, $page * $limit, $limit);
    
    return $articles;
  }
  
  public function createRequest($params) {
    $article = new Article();
    $article->title = $params['title'];
    $article->summary = $params['summary'];
    $article->body = $params['body'];
    $article->createdAt = $params['createdAt'];
    $article->categoryId = (int)$params['categoryId'];
    $article->authorId = (int)$params['authorId'];
    
    if (!$article->title || !$article->summary || !$article->body || !$article->createdAt || !$article->categoryId || !$article->authorId) {
      return array('error' => 'Incorrect payload.');
    }
    
    $article = $this->repo->create($article);
    
    return array('id' => (int)$article->id);
  }
  
  public function updateRequest($id, $params) {
    $article = new Article();
    $article->title = $params['title'];
    $article->summary = $params['summary'];
    $article->body = $params['body'];
    $article->createdAt = $params['createdAt'];
    $article->categoryId = (int)$params['categoryId'];
    $article->authorId = (int)$params['categoryId'];
    
    return $this->repo->update($id, $article);
  }
  
  public function deleteRequest($id) {  
    return $this->repo->delete($id);
  }
  
  // ///////// HELPER FUNCTIONS ////////////////////////////////////////////////////////////////////////
  
  private function getIfSet($params, $var, $def = null) {
    return isset($params[$var]) ? $params[$var] : $def;
  }
}