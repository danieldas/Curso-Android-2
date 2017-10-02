<?php 

include ('conexion.php');

$Nombre=(!isset($_POST['Nombre']))?'':mysql_escape_string($_POST['Nombre']);
$Apellido=(!isset($_POST['Apellido']))?'':mysql_escape_string($_POST['Apellido']);
$Ci=(!isset($_POST['Ci']))?'':mysql_escape_string($_POST['Ci']);
$FkViaje=(!isset($_POST['FkViaje']))?'':mysql_escape_string($_POST['FkViaje']);


$sql="INSERT INTO  `reserva` (
								`IdReserva`,
								`Nombre`,
								`Apellido`,
								`Ci`,
								`FkViaje`

							  )
							  VALUES (
								'$IdReserva' ,
								'$Nombre',
								'$Apellido',								
								'$Ci',
								'$FkViaje'
								)";

mysqli_set_charset($conexion, "utf8"); //formato de datos utf8

mysqli_query($conexion, $sql);



	        
	        


?>