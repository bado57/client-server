<?php
header("content-type:application/json; charset=UTF-8");
 
require_once "ayar.php";

/*
 * Tüm mesajları çekemye yarar
 */

// Json sonuc dizisi
$response = array();

// mesaj tablosundan tüm emsajları getireck
$result = mysql_query("SELECT * FROM mesajlar") or die(mysql_error());

// etkilenen satır varsa
if (mysql_num_rows($result) > 0) {
    $response["adminmesaj"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // kullanıcı mesajları
        $message = array();
        $message["pid"] = $row["mesaj_id"];
		$message["mesaj_konu"] = $row["mesaj_konu"];
        $message["mesaj_gonderen"] = $row["mesaj_gonderen"];
        $message["mesaj_icerik"] = $row["mesaj_icerik"];
        //$message["created_at"] = $row["created_at"];
        //$message["updated_at"] = $row["updated_at"];



        // en son olarak tüm sonuçları push ederiz diziye ve döndürürüz
        array_push($response["adminmesaj"], $message);
    }
    // success
    $response["success"] = 1;

    // json encode u echo yaparız
    echo json_encode($response);
} else {
    // hiç mesaj yoksa da
    $response["success"] = 0;
    $response["message"] = "No products found";

    // mesaj yoksa
    echo json_encode($response);
}
?>
