<?php 
 header("content-type:application/json; charset=UTF-8");
 
      require_once "ayar.php";
/*
 * burada tüm kayıt işlemleri gerçekleşmektedir
 *  HTTP sorgularını alır ve yönlendirir.
 */

//array dizisine atıyoruz cevaplarımızı
$response = array();

if (isset($_POST['enlem']) && isset($_POST['boylam'])) {
    
    $enlem = $_POST['enlem'];
    $boylam = $_POST['boylam'];

    // insert işlemi
    $result = mysql_query("INSERT INTO location(location_enlem, location_boylam) VALUES('$enlem', '$boylam')");

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
  