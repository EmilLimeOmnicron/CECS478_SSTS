<?php
require_once 'dbconnect.php';
require_once('vendor/autoload.php');
use \Firebase\JWT\JWT;
define('ALGORITHM','HS512');   // Algorithm used to signthe token, see
define('SECRET_KEY','-');
header('Content-Type: application/json');

    $username = $_POST['username'];
    $pass = $_POST['password'];

//echo "username '$username', password '$pass'\n";

$stmt = mysqli_stmt_init($conn);

if(mysqli_stmt_prepare($stmt, "SELECT COUNT(username) AS num FROM users WHERE username = ?"))
{}
else
{}

if(mysqli_stmt_bind_param($stmt, "s", $username))
{}
else
{}


if(mysqli_stmt_execute($stmt))
{}
else
{}

if(mysqli_stmt_store_result($stmt))
{}
else
{}

if(mysqli_stmt_fetch ($stmt))
{}
else
{}


if(mysqli_stmt_num_rows($stmt) > 0)
{

  mysqli_stmt_free_result($stmt);
  $getPass = mysqli_stmt_init($conn);
//  echo "INIT\n";
 if( mysqli_stmt_prepare($getPass, "SELECT password FROM users WHERE username = ?")){
//      echo "PREP\n";
 }
 if(mysqli_stmt_bind_param($getPass, "s", $username)) {
//      echo "BIND\n";
 }
  if(mysqli_stmt_execute($getPass)) {
//      echo "EXECUTE\n";
 }
  if( $result = mysqli_stmt_get_result($getPass)) {
//      echo "getResult\n";
}
//  printf("Number of rows: %d\n", $hashedPass);
  if($hashedPass = mysqli_fetch_assoc($result)) {

//      echo "FETCH\n";
  }


//  echo "HASHED PASS ".$hashedPass['password']."\n";



        if (password_verify($pass, $hashedPass['password'])) {
//              echo "password valid\n";
                $tokenId    = base64_encode(mcrypt_create_iv(32));

                    $issuedAt   = time();
                    $notBefore  = $issuedAt + 10;  //Adding 10 seconds
                    $expire     = $notBefore + 7200; // Adding 60 seconds
                    $serverName = 'localhost'; /// set your domain name
//echo "this is the tken ID";
//echo $tokenID;

                   $data = [
                        'iat'  => $issuedAt,         // Issued at: time when the token was generated
                        'jti'  => $tokenId,          // Json Token Id: an unique identifier for the token
                        'iss'  => $serverName,       // Issuer
                        'nbf'  => $notBefore,        // Not before
                        'exp'  => $expire,           // Expire
                        'data' => [                  // Data related to the logged user you can set your required data
                                //    'id'   => ['id'], // id from the users table
                                     'name' => $username //,  name
                                  ]
                    ];
                  $secretKey = base64_decode(SECRET_KEY);

                  $jwt = JWT::encode(
                            $data, //Data to be encoded in the JWT
                            SECRET_KEY, // The signing key
                            ALGORITHM
                         );
//echo $jwt;
                $response["jwt"] = $jwt;
                echo json_encode($response);
//              $unencodedArray = ['jwt' => $jwt];

         } else {
                echo  "{'status' : 'error','msg':'Invalid username or password'}";
         }
}
?>
