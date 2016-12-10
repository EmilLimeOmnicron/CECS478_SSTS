
<?php

require_once('vendor/autoload.php');
require_once 'dbconnect.php';
use \Firebase\JWT\JWT;

define('ALGORITHM', 'HS512');
define('SECRET_KEY', SECRET KEY);

  $headers = apache_request_headers();
  $token = $headers['token'];

try {
     $jwt = JWT::decode($token, SECRET_KEY, array(ALGORITHM));
} catch (Exception $e) {
    echo "Caught exception: ", $e->getMessage(), "\n";
}
if(isset($jwt)) {
//      echo "JWT DECODE\n";
}
$jwtArr = (array) $jwt;

if(isset($jwtArr)) {
//      echo "JWT ARRAY\n";
}
$username = $jwtArr['data']->name;

if(isset($username)) {
//      echo "JWT DONE, user SET FROM TOKEN\n";
}
if(isset($username)) {echo "$username 's messages\n";
        $stmt = mysqli_stmt_init($conn);
//      echo "INIT\n";
        if( mysqli_stmt_prepare($stmt, "SELECT * FROM messages WHERE receiver = ? OR sender = ?")){
//              echo "PREP\n";
        }

        if(mysqli_stmt_bind_param($stmt, "ss", $username, $username)){
//              echo "BIND\n";
        }
        if(mysqli_stmt_execute($stmt)){
//              echo "EXECUTE\n";
        }
        if( $result = mysqli_stmt_get_result($stmt)) {
//              echo "getResult\n";
        }
         if($row = mysqli_fetch_assoc($result)) {
//              echo "FETCH\n";
        }

        $numMessages = mysqli_num_rows ($result);
//      printf("Result set has %d rows.\n", $numMessages);

        if($numMessages > 0) {
                echo "".$row['addedOn']." FROM: ".$row['sender']." TO: ".$row['receiver']."\n".$row['message']."\n";
                while($row = mysqli_fetch_assoc($result)) {
                        echo "".$row['addedOn']." FROM: ".$row['sender']." TO: ".$row['receiver']."\n".$row['message']."\n";

                }
        }
        else {
                echo "There are no messages for you";
        }

     mysqli_stmt_close($stmt);
}
else {
  echo "User doesn't exist";
}

?>

