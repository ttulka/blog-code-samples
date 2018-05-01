<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
  
include_once 'config/db.config.php';
include_once 'infrastructure/db/DatabaseFactory.php';
include_once 'infrastructure/ArticleRepoImpl.php';

$db = DatabaseFactory::getDatabase(DB_TYPE, DB_HOST, DB_NAME, DB_USER, DB_PASS);

$repo = new ArticleRepoImpl($db->getConnection());

$response = null;

switch ($_SERVER['REQUEST_METHOD']) {  
  case 'GET':
    if (isset($_GET['id'])) {
      $response = detailRequest($_GET, $repo);
      
    } else {
      $response = listRequest($_GET, $repo);
    }
    if ($response === null) {
      http_response_code(404);
      
    } else {
      echo json_encode($response);
    }
    break;
    
  case 'POST':
    $_DATA = parseRequestData();
    $response = createRequest($_DATA, $repo);
    
    if ($response === false) {
      http_response_code(400);       
    
    } else {
      http_response_code(201);
      echo json_encode($response);
    }
    break;                           
  
  case 'PUT':
    if (!isset($_GET['id'])) {
      http_response_code(400);
      echo json_encode(array('error' => 'Missing "id" parameter.'));
    }
  
    $_DATA = parseRequestData();
    $response = updateRequest($_GET['id'], $_DATA, $repo);
    
    if ($response === false) {
      http_response_code(404);
      echo json_encode(array('error' => 'Article not found.'));
    }
    break;
    
  case 'DELETE':
    if (!isset($_GET['id'])) {
      http_response_code(400);
      echo json_encode(array('error' => 'Missing "id" parameter.'));
    }
    
    $response = deleteRequest($_GET['id'], $repo);
    
    if ($response === false) {
      http_response_code(404);
      echo json_encode(array('error' => 'Article not found.'));
    }
    break;
        
  default:
    throw new Exception('HTTP method not supported: ' . $_SERVER['REQUEST_METHOD']);
}

// ///////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Get Article Detail.
 */ 
function detailRequest($params, $repo) {
  $article = $repo->fetchOne($params['id']);
  
  return $article;
}

/**
 * List Articles.
 */ 
function listRequest($params, $repo) {
  $limit = 10;
  
  $categoryId = getIfSet($params, 'categoryId');   
  $authorId = getIfSet($params, 'authorId');   
  $page = getIfSet($params, 'page', 0);
  
  $articles = $repo->fetchAll($categoryId, $authorId, $page * $limit, $limit);
  
  return $articles;
}

/**
 * Create a New Article.
 */ 
function createRequest($params, $repo) {
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
  
  $article = $repo->create($article);
  
  return array('id' => (int)$article->id);
}

/**
 * Update an Article.
 */ 
function updateRequest($id, $params, $repo) {
  $article = new Article();
  $article->title = $params['title'];
  $article->summary = $params['summary'];
  $article->body = $params['body'];
  $article->createdAt = $params['createdAt'];
  $article->categoryId = (int)$params['categoryId'];
  $article->authorId = (int)$params['categoryId'];
  
  return $repo->update($id, $article);
}

/**
 * Delete an Article.
 */ 
function deleteRequest($id, $repo) {  
  return $repo->delete($id);
}

// ///////////////////////////////////////////////////////////////////////////////////////////////////

function getIfSet($params, $var, $def = null) {
  return isset($params[$var]) ? $params[$var] : $def;
} 

function parseRequestData() {
  $contentType = explode(';', $_SERVER['CONTENT_TYPE']); // Check all available Content-Type
  $rawBody = file_get_contents('php://input'); // Read body
  $data = array(); // Initialize default data array
  
  if (in_array('application/json', $contentType)) { // Check if Content-Type is JSON
    $data = json_decode($rawBody, true); // Then decode it
    
  } else {
    parse_str($data, $data); // If not JSON, just do same as PHP default method
  }
  
  return $data;
}