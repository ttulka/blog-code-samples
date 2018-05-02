<?php
namespace articles;

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
  
require_once __DIR__ . '/config/db.config.php';
require_once __DIR__ . '/application/ArticleController.php';
require_once __DIR__ . '/infrastructure/ArticleRepoPDO.php';
require_once __DIR__ . '/infrastructure/db/DatabaseFactory.php';

$db = DatabaseFactory::getDatabase(DB_TYPE, DB_HOST, DB_NAME, DB_USER, DB_PASS);

$repo = new ArticleRepoPDO($db->getConnection());

$controller = new ArticleController($repo);

$response = null;

switch ($_SERVER['REQUEST_METHOD']) {  
  case 'GET':
    if (isset($_GET['id'])) {
      $response = $controller->detailRequest($_GET['id']);
      
    } else {
      $response = $controller->listRequest($_GET);
    }
    if ($response === null) {
      http_response_code(404);
      
    } else {
      echo json_encode($response);
    }
    break;
    
  case 'POST':
    $_DATA = parseRequestData();
    $response = $controller->createRequest($_DATA);
    
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
    $response = $controller->updateRequest($_GET['id'], $_DATA);
    
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
    
    $response = $controller->deleteRequest($_GET['id']);
    
    if ($response === false) {
      http_response_code(404);
      echo json_encode(array('error' => 'Article not found.'));
    }
    break;
        
  default:
    throw new Exception('HTTP method not supported: ' . $_SERVER['REQUEST_METHOD']);
}

// ///////// HELPER FUNCTIONS //////////////////////////////////////////////////////////////////////// 

function parseRequestData() {
  $contentType = explode(';', $_SERVER['CONTENT_TYPE']);
  $rawBody = file_get_contents('php://input');
  $data = array();
  
  if (in_array('application/json', $contentType)) {
    $data = json_decode($rawBody, true);
    
  } else {
    parse_str($data, $data);
  }
  
  return $data;
}