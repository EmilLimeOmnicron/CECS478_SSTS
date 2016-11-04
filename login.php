<?php
require_once 'dbconnect.php';
require_once('vendor/autoload.php');
use \Firebase\JWT\JWT;
define('ALGORITHM','HS512');   // Algorithm used to signthe token, see
define('SECRET_KEY','Your-Secret-key');

    $username = $_POST['username'];
    $pass = $_POST['password'];

echo "username '$username', password '$pass'\n";

$stmt = mysqli_stmt_init($conn);

if(mysqli_stmt_prepare($stmt, "SELECT COUNT(username) AS num FROM users WHERE username = ?"))
{
  echo "PREPARE WORKS\n";
}
else
{
  echo "PREPARE DIDNT WORK\n";
}

if(mysqli_stmt_bind_param($stmt, "s", $username))
{
   echo "BIND WORKS\n";
}
else
{
   echo "BIND BROKE\n";
}


if(mysqli_stmt_execute($stmt))
{
  echo "EXECUTED\n";
}
else
{
  echo "NOT EXECUTED\n";
}

if(mysqli_stmt_store_result($stmt))
{
  echo "STORED\n";
}
else
{
  echo "NOT STORED\n";
}

if(mysqli_stmt_fetch ($stmt))
{
	  echo "RESULT FETCHED\n";

	
	}
else
{
  echo "RESULT NOT FETCHED\n";
}

printf("Number of rows: %d.\n", mysqli_stmt_num_rows($stmt));

echo "im right before the row num check";

if(mysqli_stmt_num_rows($stmt) > 0)
{
  echo "GET PASS\n";

  mysqli_stmt_free_result($stmt);
  $getPass = mysqli_stmt_init($conn);
echo "INIT\n";
 if( mysqli_stmt_prepare($getPass, "SELECT password FROM users WHERE username = ?"))
{echo "PREP\n";}
 if(mysqli_stmt_bind_param($getPass, "s", $username))
{echo "BIND\n";}
  if(mysqli_stmt_execute($getPass))
{echo "EXECUTE\n";}
  if( $result = mysqli_stmt_get_result($getPass))
{echo "getResult\n";}
  printf("Number of rows: %d\n", $hashedPass);
  if($hashedPass = mysqli_fetch_assoc($result)) {

	    echo "FETCH\n";
   }


  echo "HASHED PASS ".$hashedPass['password']."\n";



        if (password_verify($pass, $hashedPass['password']))
                {
echo "password valid"; 
                $tokenId    = base64_encode(mcrypt_create_iv(32));

                    $issuedAt   = time();
                    $notBefore  = $issuedAt + 10;  //Adding 10 seconds
                    $expire     = $notBefore + 60; // Adding 60 seconds
                    $serverName = 'localhost'; /// set your domain name
echo "this is the tken ID";
echo $tokenID;

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
echo "this is the data";
echo $username;         
        echo $data;
                  $secretKey = base64_decode(SECRET_KEY);
                  /// Here we will transform this array into JWT:

echo "this is the secret key";
echo $secretKey;

                  $jwt = JWT::encode(
                            $data, //Data to be encoded in the JWT
                            SECRET_KEY, // The signing key
                          ALGORITHM
                                 );
echo $jwt; 
              // $unencodedArray = ['jwt' => $jwt];
               //   echo json_encode($unencodedArray);
                 } else {
                  echo  "{'status' : 'error','msg':'Invalid username or password'}";
                  }
}
?>

