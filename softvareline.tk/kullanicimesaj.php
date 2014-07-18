<?php
    
    header("content-type:application/json; charset=UTF-8");
 
    require_once "ayar.php";
	$response = array();
        if(isset($_POST['kullaniciadi']) && isset($_POST['kullanicisifre'])&& isset($_POST['konu'])&& isset($_POST['icerik']))	{
	     //kullancııdan veri alırken mutlaka true kullanmak gerekki olası html kod girdilerinde
		//bizim için sıkıntı olmasın.
	    $kadi  =$_POST['kullaniciadi'];
	    $sifre =$_POST['kullanicisifre'];
		$konu =$_POST['konu'];
		$icerik =$_POST['icerik'];
		
		
	    
		if(!$konu || !$icerik){
		    $response["success"] = 2;
            $response["message"] = "Lütfen Boş geçmeyiniz.";
			 echo json_encode($response);
		  }else {	
			$query = mysql_query ("SELECT * FROM uyeler WHERE uye_kadi='$kadi' && uye_sifre='$sifre'");
		    if(mysql_affected_rows()){
			
			
					while($row=mysql_fetch_array($query)){
					 
						  $uye_id   =$row["uye_id"];
						}
						
							
					$result = mysql_query("INSERT INTO mesajlar (mesaj_gonderen,mesaj_icerik,mesaj_konu) VALUES('$uye_id','$icerik','$konu')");
							
					$response["success"] = 1;
					$response["message"] = "basarili";	
				
			}else {
			        $response["success"] = 2;
					$response["message"] = "tekrar deneyiniz";
			//$hata ="Böyle bir üye, sistemde kayıtlı gözükmüyor....";
			}
			  echo json_encode($response);
			} 
		// echo json_encode($response);
		}else {
		$response["success"] = 2;
        $response["message"] = "Hop! Bi hata var";
		 echo json_encode($response);
		}
		
		

?>