<?php
//error_reporting(0);
    $host="localhost";
	$veriisim  ="software_bado";
	$verisifre ="V@1gO)Ki4S9C";
	$veritabani="software_servisci";

    $baglanti = mysql_connect($host,$veriisim,$verisifre) or die(mysql_error("ne yazık"));
	$baglanti2=mysql_select_db($veritabani,$baglanti) or die(mysql_error("ne yazık"));

	 ##Karekter Sorunu
	  mysql_query("SET NAMES 'utf8'");
      mysql_query("SET CHARACTER SET utf8");
      mysql_query("SET COLLATION_CONNECTION = 'utf8'");
?>