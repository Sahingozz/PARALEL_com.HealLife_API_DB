Feature: API test islemleri
@us_07
    #zafer
    #[API_US07] Bir yönetici olarak API bağlantısı üzerinden yeni bir harcama olusturabilmek istiyorum
  Scenario: [TC_01->API_US_07] sisteme kayitli visitor purpose bilgilerini guncelleyebilmeliyim.
  /api/visitorsPurposeUpdate endpoint'ine gecerli authorization bilgileri ve dogru datalar
  (id, visitors_purpose, description) iceren bir PATCH body gönderildiginde
  dönen status code'in 200 oldugu ve response body'deki message bilgisinin "Success" oldugu dogrulanmali

    Given Api kullanicisi "api/visitorsPurposeUpdate" path parametreleri set eder
    Then "api/visitorsPurposeUpdate" baglantisi uzerinden gecerli authorization bilgileri ve dogru datalarla PATCH body gonderilir
    Then Gonderilen patch isleminin donusunde status code'un 200 oldugu dogrulanir
    Then Gonderilen patch isleminin donusunda mesajin "success" oldugu dogrulanir
