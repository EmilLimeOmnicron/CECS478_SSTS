<?php
session_start();
 require 'dbconnect.php';

 $username =strip_tags($_POST['username']);
 $email =strip_tags($_POST['email']);
 $pass = strip_tags($_POST['password']);
 $hashAndSalt = password_hash($pass, PASSWORD_BCRYPT);

 $stmt1 = mysqli_prepare($conn, "insert into users(username, email, password) values (?,?,?)");
 $stmt2 = mysqli_prepare($conn, "SELECT * from users where email = ?");
 $stmt3 = mysqli_prepare($conn, "SELECT * from users where username = ?");
 mysqli_stmt_bind_param($stmt1, "sss", $username, $email, $hashAndSalt);
 mysqli_stmt_bind_param($stmt2, "s", $email);
 mysqli_stmt_bind_param($stmt3, "s", $username);

 mysqli_stmt_execute($stmt2);
 mysqli_stmt_store_result($stmt2);
 $row2 = mysqli_stmt_num_rows($stmt2);

 mysqli_stmt_execute($stmt3);
 mysqli_stmt_store_result($stmt3);
 $row3 = mysqli_stmt_num_rows($stmt3);

 ///stop if username OR email are taken
 if($row2 != 0 or $row3  != 0) {
        echo 'username/email is not available';
 }
 else{
    echo "Thanks for registering";
    mysqli_stmt_execute($stmt1);
//    printf("rows inserted: %d\n", mysqli_stmt_affected_rows($stmt1));
    mysqli_stmt_close($stmt1);
 }
 mysqli_stmt_close($stmt2);
 mysqli_stmt_close($stmt3);

?>

