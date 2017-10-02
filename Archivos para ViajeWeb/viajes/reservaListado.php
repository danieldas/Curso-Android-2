<?php
include ('conexion.php');

$sql = "SELECT * FROM viaje";
mysqli_set_charset($conexion, "utf8"); //formato de datos utf8


if(!$result = mysqli_query($conexion, $sql)) die();

$viajes = array(); //creamos un array

while($row = mysqli_fetch_array($result)) 
{ 
    $Codigo=$row['Codigo'];
    $Destino=$row['Destino'];
    $Horario=$row['Horario'];
    $Precio=$row['Precio'];
    $Flota=$row['Flota'];
    $Imagen=$row['Imagen'];
    $viajes[] = array('Codigo'=> $Codigo, 'Destino'=> $Destino, 'Horario'=> $Horario, 'Precio'=> $Precio, 'Flota'=> $Flota, 'Imagen'=> $Imagen);

}



$close = mysqli_close($conexion) 
or die("Ha sucedido un error inesperado en la desconexion de la base de datos");
  

//Creamos el JSON
$json_string = json_encode($viajes);
echo $json_string;
?>