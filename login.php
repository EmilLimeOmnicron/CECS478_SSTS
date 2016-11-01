<?php
use \Firebase\JWT\JWT;
define('SECRET_KEY','fill_secret'); 
define('ALGORITHM','HS512');

    $username = !empty($_POST['username']);
    $pass = !empty($_POST['password']);

                // if there is no error below code run
                $statement = $config->prepare("select * from users where username = :username" );
                $statement->execute(array(':username' => $username));
                $row = $statement->fetchAll(PDO::FETCH_ASSOC);
                $hashAndSalt = password_hash($password, PASSWORD_BCRYPT);
                
                if(count($row)>0 && password_verify($row[0]['password'],$hashAndSalt)) {
                    $tokenId    = base64_encode(mcrypt_create_iv(32));
                    $issuedAt   = time();
                    $notBefore  = $issuedAt + 10;  //Adding 10 seconds
                    $expire     = $notBefore + 60; // Adding 60 seconds
                    $serverName = 'localhost'; /// set your domain name


                    /*
                     * Create the token as an array
                     */
                    $data = [
                        'iat'  => $issuedAt,         // Issued at: time when the token was generated
                        'jti'  => $tokenId,          // Json Token Id: an unique identifier for the token
                        'iss'  => $serverName,       // Issuer
                        'nbf'  => $notBefore,        // Not before
                        'exp'  => $expire,           // Expire
                        'data' => [                  // Data related to the logged user you can set your required data
                                    'id'   => $row[0]['id'], // id from the users table
                                     'name' => $row[0]['name'], //  name
                             
                                  ]
                    ];

           
                  $secretKey = base64_decode(SECRET_KEY);
                  /// Here we will transform this array into JWT:
                  $jwt = JWT::encode(
                            $data, //Data to be encoded in the JWT
                            $secretKey // The signing key

                           );
                 $unencodedArray = ['jwt' => $jwt];
                  echo json_encode($unencodedArray);
 } else {
                  echo  "{'status' : 'error','msg':'Invalid email or password'}";
                  }
   
?>
