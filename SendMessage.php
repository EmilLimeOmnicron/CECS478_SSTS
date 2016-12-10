
<?php

require_once('vendor/autoload.php');
require_once 'dbconnect.php';
use \Firebase\JWT\JWT;

define('ALGORITHM', 'HS512');
define('SECRET_KEY', SECRET KEY);

if(isset($_POST['message']) &&  isset($_POST['receiver'])) {

  $headers = apache_request_headers();
  $message = $_POST['message'];
  $receiver = $_POST['receiver'];
  $token = $headers['token'];

try {
     $jwt = JWT::decode($token, SECRET_KEY, array(ALGORITHM));
} catch (Exception $e) {
    echo 'Caught exception: ',  $e->getMessage(), "\n";
}
if(isset($jwt)) {}//echo "JWT DECODE\n";}

     $jwtArr = (array) $jwt;
if(isset($jwtArr)) {}//echo "JWT ARRAY\n";}

     $sender = $jwtArr['data']->name;
if(isset($sender)) {}//echo "JWT DONE, SENDER SET FROM TOKEN\n";}

     $stmt = mysqli_prepare($conn, "INSERT into messages(sender, receiver, message) values (?,?,?)");
     mysqli_stmt_bind_param($stmt, "sss", $sender, $receiver, $message);
//echo "STATEMENT PREPARED AND BINDED\n";

     mysqli_stmt_execute($stmt);
     mysqli_stmt_close($stmt);
//echo "STATEMENT EXECUTED AND CLOSED\n";

        echo 'Message Sent!';
}
else {
  echo 'sender, receiver, or message doesnt exist';
}
?>




