Feature: API testleri


    #zafer
    #[API_US08] Ziyaret Amaci Listesi delete işlemleri test edilir
  Scenario: [TC_01->API_US_08] Bir yönetici olarak API baglantisi üzerinden sistemdeki visitor purpose kaydini silebilmeliyim.
  /api/visitorsPurposeDelete endpoint'ine gecerli authorization bilgileri ve dogru data (id) iceren bir DELETE body
  gönderildiginde dönen status code'in 200 oldugu ve response body'deki message bilgisinin "Success" oldugu dogrulanmali

    Given "api/visitorsPurposeAdd" ile yeni bir ziyaret nedeni eklenir, id alinir
    Then Yeni eklenen visitorsPurpose id'nin sistemde bulundugu dogrulanir
    Then Yeni alinan id'nin DELETE istemiyle silindigi, status code'un 200 oldugu ve mesajin "success" oldugu dogrulanir


@02
    #zafer
    #[API_US08] Ziyaretçi Listesi Api işlemleri test edilir
  Scenario: [TC_02->API_US_08] Bir yönetici olarak ziyaret amaci delete islemini dogrulamaliyim
  /api/visitorsPurposeDelete endpoint'ine gecersiz authorization bilgileri
  veya yanlis data (id) iceren bir DELETE body gönderildiginde dönen
  status code'in 403 oldugu ve response body'deki
  message bilgisinin "failed" oldugu dogrulanmali

    Given "api/VisitorsPurposeDelete" icin hatali authorization bilgileriyle DELETE sorgusu gonderilir
    Then hatali authorizationla yapilan isteğin mesajının failed, status code'unun 403 oldugu dogrulanir


     #zafer
    #[API_US08] Ziyaret Amaci Listesi delete işlemleri test edilir
  Scenario: [TC_03->API_US_08] Ziyaret amaci bilgisi silme islemlerinin kontrolü saglanir
  Response body icindeki DeletedId bilgisinin /api/visitorsPurposeDelete endpoint'ine gönderilen
  delete request body icindeki id bilgisi ile  ayni oldugu dogrulanmali.

    Given "api/visitorsPurposeDelete" ile ziyaret amaci listeden silinir
    Then Silinen ziyaret amaci ID'si ile response olarak gelenin ayni oldugu dogrulanir


     #zafer
    #[API_US08] Ziyaret Amaci Listesi delete işlemleri test edilir
  Scenario: [TC_04->API_US_08] Silinen ziyaretci amacinin listede olmadiginin kontrolü yapilir
  API uzerinden silinmek istenen visitor purpose kaydinin silindigi, API uzerinden dogrulanmali.

    Given "api/visitorsPurposeList" ile ziyaret amaclari listelenir
    Then Silinen ziyaret amacinin ana listede yer almadigi dogrulanir


     #zafer
    #[API_US09] Ziyaretci Listesi işlemleri test edilir
  Scenario: [TC_01->API_US_09] Silinen ziyaretci amacinin listede olmadiginin kontrolü yapilir
  /api/visitorsList endpoint'ine gecerli authorization bilgileri ile bir GET request
  gönderildiginde dönen status code'un 200 oldugu ve response message
  bilgisinin "Success" oldugu dogrulanmali.

    Given "api/visitorsList" ile ziyaretci listesi goruntulenir
    Then ziyaretci listesi sorgusunun status code'unun 200 oldugu dogrulanir
    And Donen sorgunun message'inin "Success" oldugu dogrulanir


     #zafer
    #[API_US09] Ziyaretci Listesi işlemleri test edilir
  Scenario: [TC_02->API_US_09] Silinen ziyaretci amacinin listede olmadiginin kontrolü yapilir
  /api/visitorsList endpoint'ine gecersiz authorization bilgileri ile bir GET Request
  gönderildiginde dönen status code'un 403 oldugu ve response message
  bilgisinin "failed" oldugu dogrulanmali

    Given hatali authorization koduyla "api/visitorsList" ile ziyaretci listesi cagrilir
    Then ziyaretci listesi sorgusunun status code'unun 403 oldugu dogrulanir
    And Donen sorgunun message'inin "failed" oldugu dogrulanir


     #zafer
    #[API_US09] Ziyaretci Listesi işlemleri test edilir
  Scenario: [TC_03->API_US_09] Ziyaretci listesinden bir id secilir ve detaylar kontrol edilir
  Response body icindeki list icerigi (id:"38" olan icerigin source: "Online",
  purpose:"Improve local visibility for heallifehospital.com",
  name: "Mike Holmes", email: "no-replyAnnora@gmail.com" ,
  date:"2023-05-16", created_at: "2023-05-15 21:24:47" oldugu) dogrulanmali.

    Given Ziyaretci listesinden id numarasi 38 olan ziyaretcinin detay bilgileri dogrulanir
