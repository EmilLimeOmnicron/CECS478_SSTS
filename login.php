<?php
require_once 'dbconnect.php';
require_once('vendor/autoload.php');
use \Firebase\JWT\JWT;
define('ALGORITHM','HS512');   
define('SECRET_KEY','Your-Secret-key'); //placeholder key

//Login uses username and password
    $username = $_POST['username'];
    $pass = $_POST['password'];

echo "username '$username', password '$pass'\n";

$stmt = mysqli_stmt_init($conn);

//SQL statements for the correct login of the user.
if(mysqli_stmt_prepare($stmt, "SELECT COUNT(username) AS num FROM users WHERE username = ?")){
}
else{
}

if(mysqli_stmt_bind_param($stmt, "s", $username)){
}
else {
}

if(mysqli_stmt_execute($stmt)){
}
else{
}

if(mysqli_stmt_store_result($stmt)){
}
else{
}

if(mysqli_stmt_fetch ($stmt)){
	}
else{
}

if(mysqli_stmt_num_rows($stmt) > 0){

  mysqli_stmt_free_result($stmt);
  $getPass = mysqli_stmt_init($conn);

 if( mysqli_stmt_prepare($getPass, "SELECT password FROM users WHERE username = ?")){
 }
 if(mysqli_stmt_bind_param($getPass, "s", $username)){
 }
  if(mysqli_stmt_execute($getPass)){
  }
  if( $result = mysqli_stmt_get_result($getPass)){
  }
  
  if($hashedPass = mysqli_fetch_assoc($result)) {
   }
	//Verify if password is correct
        if (password_verify($pass, $hashedPass['password'])) {

                $tokenId    = base64_encode(mcrypt_create_iv(32));

                    $issuedAt   = time();
                    $notBefore  = $issuedAt + 10;  //Adding 10 seconds
                    $expire     = $notBefore + 60; // Adding 60 seconds
                    $serverName = 'localhost'; /// set your domain name
                   //Bundle for token
                   $data = [
                        'iat'  => $issuedAt,         // Issued at: time when the token was generated
                        'jti'  => $tokenId,          // Json Token Id: an unique identifier for the token
                        'iss'  => $serverName,       // Issuer
                        'nbf'  => $notBefore,        // Not before
                        'exp'  => $expire,           // Expire
                        'data' => [                  // Data related to the logged user you can set your required data                                                                                                                                                         
                                //    'id'   => ['id'], // id from the users table
                                     'name' => $username, //  name 
                                  ]
                    ];

                  $secretKey = base64_decode(SECRET_KEY);
              
                  //encode token
                  $jwt = JWT::encode(
                            $data, //Data to be encoded in the JWT
                            SECRET_KEY, // The signing key
                          ALGORITHM
                                 );
                 echo $jwt; 
     
                 } else {
                  echo  "{'status' : 'error','msg':'Invalid username or password'}";
                  }
}
?>

