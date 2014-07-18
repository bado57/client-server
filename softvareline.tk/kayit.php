<?php 
 header("content-type:application/json; charset=UTF-8");
 
      require_once "ayar.php";
/*
 * burada tüm kayıt işlemleri gerçekleşmektedir
 *  HTTP sorgularını alır ve yönlendirir.
 */

//array dizisine atıyoruz cevaplarımızı
$response = array();

if (isset($_POST['name']) && isset($_POST['soyad']) && isset($_POST['kadi'])&& isset($_POST['sifre'])&& isset($_POST['eposta'])) {
    
    $name = $_POST['name'];
    $soyad = $_POST['soyad'];
    $kadi = $_POST['kadi'];
	$sifre = $_POST['sifre'];
	$eposta = $_POST['eposta'];

    // insert işlemi
    $result = mysql_query("INSERT INTO uyeler(uye_adi, uye_soyad, uye_kadi,uye_sifre,uye_eposta) VALUES('$name', '$soyad', '$kadi', '$sifre', '$eposta')");

    //başarılı ise
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Basariyla kayit oldunuz";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Hop! Bi hata var";
        
        // json sonucunu döndürür
        echo json_encode($response);
    }
} else {
    // işlem kaybolursa
    $response["success"] = 0;
    $response["message"] = "Islem kayip";

    // ve java ya geri döndürüyoruz işlemi
    echo json_encode($response);
}

?>
  