<?php
  // パラメータの説明
  // country:国（jaとかusとかukとか）

  require_once $_POST['country'].'/url.php';

  $returnJson['ad'] = AD; // 0=off, 1=on
  $returnJson['url'] = URL;

  // 出力
  header("Content-Type: text/javascript; charset=utf-8");
  header("Access-Control-Allow-Origin: *");
  echo json_encode($returnJson);
  die();
