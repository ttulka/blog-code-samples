<?php
interface ArticleRepo {
 
    public function fetchAll($categoryId, $authorId, $start, $limit);
    
    public function fetchOne($id);
    
    public function create($article);
    
    public function update($iId, $article);
    
    public function delete($id);
}