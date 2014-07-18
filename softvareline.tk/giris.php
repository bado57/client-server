<?php
    //Türkçe karekter sorunu olmaması için.
    header("content-type:application/json; charset=UTF-8");
     
	//veritabanı bağlantısını yapıyoruz, ayar.php yi bu sayfaya çağırarak burada
	//kullanabileceğimizi söylüyoruz programa.
	
    require_once "ayar.php";
	
	//response dizisini tanımladık.
	$response = array();
	
	     /*javadan veriyi post ile alıyoruz.
		 javadan da veriyi gönderirken name ve sifre gibi isimlere dikkat etmeliyiz
		 yoksa burda sıkıntı çıkacaktır.  */
		 //isset() de name isminde post ile gelen veri varmı kontrolü.
        if(isset($_POST['name']) && isset($_POST['sifre']))	{
		
	     //burada ,java classlarımızdan name ve sifre isminde gönderdiğimiz verileri
		 //Post ile alıyoruz.
	    $kadi  =$_POST['name'];
	    $sifre =$_POST['sifre'];
		
	    //boşluk kontrolü.Yani verilerin içerisi dolu mu değil mi.
		if(!$kadi || !$sifre){
		    
			//dizimize atamaları yapıyoruz.Burda bi farklılık success ve message şeklinde veriyi dizide 
			//tutmamız.Java classlarımızdan tekrar alırken de bu kelimelere göre alacağız.
		    $response["success"] = 0;
            $response["message"] = "Lütfen Boş geçmeyiniz.";
			
			//java sınıflarımıza sonucu döndürüyoruz.return gibi düşünebiliriz buradaki echo kullanımını.
			 echo json_encode($response);
		}else {	
		    // bu aşamaya kadar bir yanlışlık yapmadıysak veritabanından bilgi isteyebiliriz. 
            //tek yapmamız gereken. Query dilinde doğru cümleyi kurmak.
			$query = mysql_query ("SELECT * FROM uyeler WHERE uye_kadi='$kadi' && uye_sifre='$sifre'");
			
			//etkilenen satır varsa
		    if(mysql_affected_rows()){
			
			 
			  //mysql_fetch_array fonksiyonu bize istediğimiz sorguyu parçlamaya yarar.Ve istediğimizi
			  // veritabanından çekebilirmiş oluruz.
			    while($row=mysql_fetch_array($query)){
		          
				  /*veritabanına uye_id diye kayıt yaptırdık ve auto_increment olarak artmasını istedik
				  yani her kayıtta 1 artırarak.Adminin uye_id sini 1 olarak kaydettik.Yani 1 ise  admin diğerlerinde ise 
				  kullanıcı olmuş oalcaktır.*/
			      $uye_id   =$row["uye_id"];
		        }
				
				if($uye_id==1){
				//adminse
				   $response["success"] = 1;
					$response["message"] = "admin";
				}else{
				//kullanıcı ise
				    $response["success"] = 2;
					$response["message"] = "kullanici";
				}
				
			}else {
			//ne admin ne de kayıtlı bir kullanıcı ise, hata mesajımızı verebiliriz.
			        $response["success"] = 3;
					$response["message"] = "böyle bir kullanici sistemde kayitli degildir tekrar deneyiniz";
			//$hata ="Böyle bir üye, sistemde kayıtlı gözükmüyor....";
			}
			  echo json_encode($response);
			} 
	
		}else {
		//Java classlarımızdan post ile birşey gelmediği için if-else bloğumuzdan çıkabiliriz.
		//Hatayı da ekrana gösterebiliriz.
		$response["success"] = 0;
        $response["message"] = "Hop! Bi hata var";
		 echo json_encode($response);
		}
		
		

?>