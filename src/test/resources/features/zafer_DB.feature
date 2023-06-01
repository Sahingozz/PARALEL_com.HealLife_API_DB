#4,13,22 zafer
@db01
Feature: DB test işlemleri gerçekleştirilir

  @us_04
    #zafer
    #[DB_US04] Randevu sayilari karsilastirilir
  Scenario: heallife_hospitaltraining.appointment bolumunde randevu sayilari karsilastirilir
    Given Database baglantisi kurulur
    Then DB uzerinde ogleden onceki randevu sayisinin ogleden sonrakilerden az oldugu dogrulanir
    And Database baglantisi kapatilir

  @us_13
    #zafer
    #[DB_US13] Categories bölümü karsilastirilir
  Scenario: heallife_hospitaltraining.charge categories bolumunde icerikler karsilastirilir
  Database uzerinden charge_categories tablosundaki name bilgisi "P" ile baslayan
  iceriklerin charge_type_id numaralarini tekrardan arindirilmis olarak listeleyiniz
  ve dogrulayiniz. (6 , 7)

    Given Database baglantisi kurulur
    Then Charge_id'si "P" ile baslayan icerikler bulunur,tekrarlar cikarilip, listelendigi dogrulanir
    And Database baglantisi kapatilir


  @us_22
    #zafer
    #[DB_US22] Languages tablosunda data cagirma ve dogrulama islemleri yapilir
  Scenario: heallife_hospitaltraining.languages bolumunde karsilastirma yapilir
  Database üzerinden languages tablosundaki short_code "yi" olan verinin language bilgisinin
  "Yiddish" oldugunu dogrulayin.

    Given Database baglantisi kurulur
    Then Languages bolumunde short_code'u "yi" olan verilerin "Yiddish" oldugu dogrulanir
    And Database baglantisi kapatilir

      #----------------------------------------------------------------zafer feature end ----------------


    #[DB_US17] Departman tablosunda bilgi dogrulamalari




