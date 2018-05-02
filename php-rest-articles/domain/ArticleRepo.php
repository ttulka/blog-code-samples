<?php
include_once 'Article.php';

interface ArticleRepo {
 
    public function fetchAll($categoryId, $authorId, $start, $limit);
    
    public function fetchOne($id);
    
    public function create(Article $article);
    
    public function update($iId, Article $article);
    
    public function delete($id);
}