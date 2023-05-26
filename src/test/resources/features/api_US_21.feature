Feature: API test islemleri

    #zafer
    #[API_US21] Bir yönetici olarak API bağlantısı üzerinden yeni bir harcama olusturabilmek istiyorum
  Scenario: [TC_01->API_US_21] Bir yönetici olarak API bağlantısı üzerinden yeni bir harcama olusturabilmek istiyorum
  /api/addExpenseHead endpoint'ine gecerli authorization bilgileri ve dogru datalar
  (exp_category, description, is_active, is_deleted) iceren bir POST body gönderildiginde
  dönen status code'in 200 oldugu ve response body'deki message bilgisinin "Success" oldugu dogrulanmali

    Given "api/addExpenseHead" baglantisi uzerinden gecerli authorization bilgileri ve dogru datalarla post body gonderilir
    Then Gonderilen post isleminin donusunde status code'un 200 oldugu dogrulanir
    Then Gonderilen post isleminin donusunda mesajin "success" oldugu dogrulanir

  @us2102
    #zafer
    #[API_US21] Bir yönetici olarak API bağlantısı üzerinden yeni bir harcama olusturabilmek istiyorum
  Scenario: [TC_02->API_US_21] API bağlantısı üzerinden gecersiz authorization koduyla yeni bir harcama olusturabilmek istiyorum
  /api/addExpenseHead endpoint'ine gecersiz authorization bilgileri veya yanlis datalar
  (exp_category, description, is_active, is_deleted) iceren bir POST body gönderildiginde
  dönen status code'in 403 oldugu ve response body'deki message bilgisinin "failed" oldugu dogrulanmali

    Given "api/addExpenseHead" baglantisi uzerinden gecersiz authorization bilgileri ve dogru datalarla post body gonderilir
    Then Gonderilen post isleminin neticesinde status code'un 403 oldugu dogrulanir
    Then Gonderilen post isleminin neticesinde mesajin "failed" oldugu dogrulanir

  @us2103
    #zafer
    #[API_US21] Bir yönetici olarak API bağlantısı üzerinden yeni bir harcama olusturabilmek istiyorum
  Scenario: [TC_03->API_US_21] API uzerinden olusturulmak istenen yeni harcama kaydinin olustugu, API uzerinden dogrulanmali.


    Given Yeni olusturulan harcama kaydinin API'de bulundugu dogrulanmali