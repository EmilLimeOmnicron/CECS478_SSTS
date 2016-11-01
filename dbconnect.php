<?php
$servername = "localhost";
$username = "username";
$password = "filler";

try {
$conn = new PDO("mysql:host=$servername;dbname=webdata", $username, $password);
$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
echo "Connected successfully";
}
catch(PDOException $e)
{
echo "Connection failed: " . $e->getMessage();
}
   
?>
