<?php

require_once('vendor/autoload.php');
require_once 'dbconnect.php';
use \Firebase\JWT\JWT;


define('ALGORITHM', 'HS512');
define('SECRET_KEY','SecretKey');

//Requires message, receiver, and token
if(isset($_POST['message']) &&  isset($_POST['receiver'])) {                                                                                                                                                                                                                                                       
                                                                                                                                                                                                                                                                               
  $headers = apache_request_headers();                                                                                                                                                                                                                                         
  $message = $_POST['message'];
  $receiver = $_POST['receiver'];
  $token = $headers['token'];

echo "TO: $receiver, MESSAGE: $message\nTOKEN: $token\n";

//
try {
     $jwt = JWT::decode($token, SECRET_KEY, array(ALGORITHM));
} catch (Exception $e) {
    echo 'Caught exception: ',  $e->getMessage(), "\n";
}
if(isset($jwt)) {
echo "JWT DECODE\n";
}

     $jwtArr = (array) $jwt;
if(isset($jwtArr)) {
echo "JWT ARRAY\n";
}


     $sender = $jwtArr['data']->name;
     
if(isset($sender)) {
echo "JWT DONE, SENDER SET FROM TOKEN\n";
}

//statement to store into the messages table
     $stmt = mysqli_prepare($conn, "INSERT into messages(sender, receiver, message) values (?,?,?)");
     mysqli_stmt_bind_param($stmt, "sss", $sender, $receiver, $message);


     mysqli_stmt_execute($stmt);
     mysqli_stmt_close($stmt);

}

else {
  echo 'sender, receiver, or message doesnt exist';
}
?>



