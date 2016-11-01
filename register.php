
                                                                    File: register.php                                                                                                                                                                

<?php

$name = $_POST["username"];
$email = $_POST["email"];
$password = $_POST["password"];
//$db


$name = mysqli_real_escape_string($db, $name);
$email = mysqli_real_escape_string($db, $email);
$password = mysqli_real_escape_string($db, $password);
$hashAndSalt = password_hash($password, PASSWORD_BCRYPT);

//check if email is taken
$sql = "SELECT email FROM users WHERE email='$email'";
$result = mysqli_query($db,$sql);
$row = mysqli_fetch_array($result,MYSQLI_ASSOC);

if(mysqli_num_rows($result) == 1)
{
 echo "Sorry. This email already exists";
}
else
{
 //Code goes here.
 //Checks if username is taken
 $sql = "SELECT username FROM users WHERE username='$username'";
 $result = mysqli_query($db,$sql);
 $row = mysqli_fetch_array($result,MYSQLI_ASSOC);

 if(mysqli_now_rows($result) == 1)
 {
  echo "Sorry. This username is already taken";
 }
 else
 {
  $query = mysqli_query($db, "INSERT INTO users (name, email, password)VALUES ('$name', '$email', '$hashAndSalt')");
}
   if($query)
  {
   echo "Thank You! You are now registered.";                                                                                                                                                                                                                                  
 }                                                                                                                                                                                                                                                                             
}
?>



