<?php
namespace articles;

require_once __DIR__ . '/Article.php';

interface ArticleRepo {
 
    public function fetchAll($categoryId, $authorId, $start, $limit);
    
    public function fetchOne($id);
    
    public function create(Article $article);
    
    public function update($iId, Article $article);
    
    public function delete($id);
}