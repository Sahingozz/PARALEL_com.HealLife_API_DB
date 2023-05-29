Feature: API testleri


    #zafer
    #[API_US01] Ziyaretçi Listesi Api işlemleri test edilir
  Scenario: [TC_01->API_US_04] Ziyaretci listesi Api islemleri test edilir
  /api/visitorsPurposeList endpoint'ine gecerli authorization bilgileri
  ile bir GET request gönderildiginde dönen status code'un 200 oldugu ve
  response message bilgisinin "Success" oldugu dogrulanmali.

    Given "api/VisitorsPurposeList" icin GET sorgusu gonderilir
    Then Donen sorgunun status code'unun 200 oldugu dogrulanir
    Then VisitorsPurposeList donen response'in message bilgisinin "Success" oldugu dogrulanir

  @US04_TC02
    #zafer
    #[API_US01] Ziyaretçi Listesi Api işlemleri test edilir
  Scenario: [TC_02->API_US_04] Ziyaretci listesi Api islemleri test edilir
  /api/visitorsPurposeList endpoint'ine gecersiz authorization bilgileri ile bir
  GET Request gönderildiginde dönen status code'un 403 oldugu ve
  response message bilgisinin "failed" oldugu dogrulanmali

    Given "api/VisitorsPurposeList" icin hatali authorization bilgileriyle GET sorgusu gonderilir
    Then hatali authorization koduyla yapilan istek sonrasi donen status code'unun 403 oldugu dogrulanir

  @US01_TC03
    #zafer
    #[API_US01] OPD List işlemleri test edilir
  Scenario: [TC_02->API_US_04] Bir yönetici olarak API baglantisi üzerinden OPD List'e erisebilmeliyim.
  Response body icindeki lists icerigi (id:"1" olan icerigin patient_name: "John Smith"
  ve id:"23" olan icerigin patient_id: "16" oldugu)  dogrulanmali.

    Given "api/opdList" icin 1 nolu id GET sorgusu gonderilir
    Then gelen bilgilerle beklenen bilgiler uyumlu oldugu dogrulanir