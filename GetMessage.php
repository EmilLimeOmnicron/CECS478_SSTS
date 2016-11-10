<?php
require_once 'dbconnect.php';

$username = $_GET['receiver'];

	
	 $stmt1 = mysqli_prepare($conn, "SELECT * FROM messages WHERE receiver = ?");
	 mysqli_stmt_bind_param($stmt1,"s", $username);
	 mysqli_stmt_execute($stmt1);
	 $result1 = mysqli_query($conn, $stmt1);
	 $row1 = mysqli_fetch_assoc($result1);
	//make sure theres a row of users
if(isset($username) &&  $row1 > 0) {
	
	$stmt = mysqli_prepare("SELECT * FROM messages WHERE receiver = ? OR sender = ? ORDER BY timeSent");
 mysqli_stmt_bind_param($stmt, "ss", $username, $username);
     mysqli_stmt_execute($stmt);
	 $result = mysqli_query($conn, $stmt);
	 $row = mysqli_fetch_assoc($result);
	 
	 if($row > 0) {
		 while($row = $result->fetch_assoc()) {
			 $sender = $row['sender'];
			 $receiver = $row['receiver'];
			 $message = $row['message'];
			 $timeSent = $row['timeSent'];
			 $data = [
			 'sender' => $sender,
			 'receiver' => $receiver,
			 'message' => $message,
			 'timeSent' => $timeSent
			 ];
		 }
	 }
	//TODO: output messages 
     mysqli_stmt_close($stmt);
	
	
}
else {
	echo 'user doesnt exist';
}
 mysqli_stmt_close($stmt1); 


?>
