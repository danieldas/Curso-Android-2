<?php

$server = "localhost";
$user = "root";
$pass = "AQUI VA EL PASSWORD";
$bd = "viajedb";

//Creamos la conexión
$conexion = mysqli_connect($server, $user, $pass,$bd) 
or die("Ha sucedido un error inesperado en la conexion de la base de datos");


?>