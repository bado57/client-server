<?php

header("content-type:application/json; charset=UTF-8");
 
      require_once "ayar.php";
/*
 * burada tüm kayıt işlemleri gerçekleşmektedir
 *  HTTP sorgularını alır ve yönlendirir.
 */
$response = array();

if (isset($_POST['pid'])) {
  

    // get a product from products table
    $result = mysql_query("SELECT * FROM location ORDER BY location_id DESC LIMIT 1");
     
        if (mysql_num_rows($result) > 0) {
            while($row=mysql_fetch_array($result)){
            $enlem=$row["location_enlem"];
            $boylam=$row["location_boylam"];
            }
            

            $product = array();
            $product["enlem"] = $enlem;
            $product["boylam"] = $boylam;
            // success
            $response["success"] = 1;

            // user node
            $response["product"] = array();

            array_push($response["product"], $product);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "LOCATİON ŞU ANDA GİRİLMEMİŞ MESAJ ATIN LÜTFEN";

            // echo no users JSON
            echo json_encode($response);
        }

} else {

    // required field is missing
    $response["success"] = 0;
    $response["message"] = "burasi";

    // echoing JSON response
    echo json_encode($response);
}
?>