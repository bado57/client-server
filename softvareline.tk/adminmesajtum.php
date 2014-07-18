<?php

header("content-type:application/json; charset=UTF-8");
 
require_once "ayar.php";
$response = array();

// check for post data
if (isset($_GET["pid"])) {
    $pid = $_GET['pid'];

    // get a product from products table
    $result = mysql_query("SELECT *FROM mesajlar WHERE mesaj_id = $pid");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);
            $isim=$result["mesaj_gonderen"];    
            $product = array();
            $product["pid"] = $result["mesaj_id"];
           // $product["name"] = $result["mesaj_gonderen"];
            $product["mesaj_konu"] = $result["mesaj_konu"];
            $product["mesaj_icerik"] = $result["mesaj_icerik"];
			
			//mesaj göndereni bulan yerdir 
			$result2=mysql_query("SELECT *FROM uyeler WHERE uye_id=$isim");
			if(mysql_num_rows($result2)>0){
			$result2 = mysql_fetch_array($result2);
			$product["name"] = $result2["uye_adi"];
			}
           

            // user node
            $response["product"] = array();
           
            array_push($response["product"], $product);
             $response["success"] = 1;
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";

        // echo no users JSON
        echo json_encode($response);
		}
   }else{
   // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>