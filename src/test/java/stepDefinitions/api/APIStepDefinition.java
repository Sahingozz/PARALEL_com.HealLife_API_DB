package stepDefinitions.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.protobuf.StringValue;
import hooks.api.HooksAPI;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.core.gherkin.messages.internal.gherkin.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import utilities.API_Utils;
import utilities.ConfigReader;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class APIStepDefinition {

    public static String fullPath;
    JSONObject reqBodyJson;
    Response response;
    int basariliStatusCode = 200;
    String basariliExpenseAddMessage ;
    int basariliExpenseAddStatusCode;
    String basarisizExpenseAddMessage ;
    int basarisizExpenseAddStatusCode;
    int yeniHarcamaKaydiId;
    boolean eklenenYeniHarcamaIdListedeMi;

    //------zafer

    private int denemeVisitorPurposeAddid;
    boolean eklenenVisitorsPurposeIdListedeMi;

    int visitorsPurposeDeleteStatusCode;
    String visitorsPurposeDeleteMessage;
    int silinecekVisitorPurposeId;
    int visitorsPurposedeletedId;
    boolean silinenListedeMi;
    String visitorsListMessage;
    int visitorsListStatusCode;

    @Given("Api kullanicisi {string} path parametreleri set eder")
    public void api_kullanicisi_path_parametreleri_set_eder(String rawPaths) {

        // https://trendlifebuy.com/api/register

        // HooksAPI.spec.pathParams("pp1","api","pp2","register");

        //    api/register

        String[] paths = rawPaths.split("/"); // ["api","register"]

        StringBuilder tempPath = new StringBuilder("/{");

        for (int i = 0; i < paths.length; i++) {

            String key = "pp" + i; // pp0 pp1 pp2
            String value = paths[i].trim();

            System.out.println("value = " + value);

            HooksAPI.spec.pathParam(key, value);

            tempPath.append(key + "}/{");
        }
        // System.out.println("tempPath = " + tempPath);

        tempPath.deleteCharAt(tempPath.lastIndexOf("{"));
        tempPath.deleteCharAt(tempPath.lastIndexOf("/"));

        System.out.println("tempPath = " + tempPath);

        fullPath = tempPath.toString(); // /{pp0}/{pp1}/{pp2}

    }

    @Then("Api kullanici OPD List icin gonderdigi Get Request sonucunda donen status kodunun ikiyuz oldugunu dogrular")
    public void apiKullaniciOPDListIcinGonderdigiGetRequestSonucundaDonenStatusKodununIkiyuzOldugunuDogrular() {

        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .get(fullPath);

        //response.prettyPrint();

        assertEquals(basariliStatusCode, response.getStatusCode());


    }

    @Then("Api kullanicisi {string} visitors_purpose ,{string} description bilgileriyle yeni bir visitor purpose kaydi olusturur")
    public void apiKullanicisiVisitors_purposeDescriptionBilgileriyleYeniBirVisitorPurposeKaydiOlusturur(String visitors_purpose, String description) {

        /*
        {
             "visitors_purpose":"deneme purpose",
              "description":"deneme description"
    `   }
         */

        reqBodyJson = new JSONObject();

        reqBodyJson.put("visitors_purpose", visitors_purpose);
        reqBodyJson.put("description", description);

        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .body(reqBodyJson.toString())
                .post(fullPath);
        response.prettyPrint();


    }

    @Then("Api kullanicisi donen status kodunun {int} oldugunu dogrular")
    public void apiKullanicisiDonenStatusKodununOldugunuDogrular(int statusCode) {

        assertEquals(statusCode, response.getStatusCode());

    }

    @Then("Api kullanicisi donen response bodysindeki message degerinin {string} oldugunu dogrular")
    public void apiKullanicisiDonenResponseBodysindekiMessageDegerininOldugunuDogrular(String message) {

        JsonPath resJP = response.jsonPath();
        assertEquals(message, resJP.getString("message"));
    }

    @Then("Api kullanicisi id'si {int} olan kaydin visitors_purpose {string},description {string}, created_at {string} expected datasi hazirlanir")
    public void api_kullanicisi_id_si_olan_kaydin_visitors_purpose_description_created_at_expected_datasi_hazirlanir(Integer id, String visitors_purpose, String description, String created_at) {

        /*
     {
    "status": 200,
    "message": "Success",
    "Token_remaining_time": 1313,
    "lists": [
        {
            "id": "4",
            "visitors_purpose": "Visit",
            "description": "Visitor centers used to provide fairly basic information about the place, corporation or event they are celebrating, acting more as the entry way to a place. The role of the visitor center has been rapidly evolving over the past 10 years to become more of an experience and to tell the story of the place or brand it represents. Many have become destinations and experiences in their own right.",
            "created_at": "2021-10-29 01:25:09"
        },
        {
            "id": "19",
            "visitors_purpose": "feridun bey",
            "description": "bayram 123 111",
            "created_at": "2023-04-12 08:34:56"
        }
                ]
    }
         */

    }

    @Then("Api kullanicisi visitors Purpose List gormek icin Get request gonderir")
    public void api_kullanicisi_visitors_purpose_list_gormek_icin_get_request_gonderir() {

        response = given()
                .spec(HooksAPI.spec)
                .headers("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .when()
                .get(fullPath);
        response.prettyPrint();
    }

    @Then("Api kullanicisi visitorsPurposeList donen response body icindeki id'si {string} olan kaydin visitors_purpose {string},description {string}, created_at {string} oldugunu dogrular")
    public void apiKullanicisiVisitorsPurposeListDonenResponseBodyIcindekiIdSiOlanKaydinVisitors_purposeDescriptionCreated_atOldugunuDogrular(String arg0, String arg1, String arg2, String arg3) {
        JsonPath resJp = response.jsonPath();
        assertEquals(arg0, resJp.get("lists[6].id"));
        assertEquals(arg1, resJp.get("lists[6].visitors_purpose"));
        assertEquals(arg2, resJp.get("lists[6].description"));
        // assertEquals(arg3, resJp.get("lists[6].created_at") );
    }

    //---------------------EXTRA_USER STORY

    @Given("{string} icin GET sorgusu gonderilir")
    public void icin_get_sorgusu_gonderilir(String url) {
        url = "https://www.heallifehospital.com/api/visitorsPurposeList";


        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .when()
                .get(url);


    }


    @Then("Donen sorgunun status code'unun {int} oldugu dogrulanir")
    public void donen_sorgunun_status_code_unun_oldugu_dogrulanir(Integer int1) {
        String url = "https://www.heallifehospital.com/api/visitorsPurposeList";


        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .when()
                .get(url);

        response.then()
                .assertThat()
                .statusCode(int1);


    }

    @Then("VisitorsPurposeList donen response'in message bilgisinin {string} oldugu dogrulanir")
    public void visitors_purpose_list_donen_response_in_message_bilgisinin_oldugu_dogrulanir(String string) {
        String url = "https://www.heallifehospital.com/api/visitorsPurposeList";


        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .when()
                .get(url);

        response.then()
                .assertThat()
                .body("message", equalTo("Success"));
        response.prettyPrint();
    }

    @Given("{string} icin hatali authorization bilgileriyle GET sorgusu gonderilir")
    public void icin_hatali_authorization_bilgileriyle_get_sorgusu_gonderilir(String url) {
        url = "https://www.heallifehospital.com/api/visitorsPurposeList";
        String hataliToken = "ABC11111";

        Response response = given()
                .header("Authorization", "Bearer " + hataliToken)
                .when()
                .get(url);
    }

    @Then("hatali authorization koduyla yapilan istek sonrasi donen status code'unun {int} oldugu dogrulanir")
    public void hatali_authorization_koduyla_yapilan_istek_sonrasi_donen_status_code_unun_oldugu_dogrulanir(Integer int1) {
        String url = "https://www.heallifehospital.com/api/visitorsPurposeList";
        String hataliToken = "ABC11111";

        Response response = given()
                .header("Authorization", "Bearer " + hataliToken)
                .when()
                .get(url);

        response.then()
                .assertThat()
                .statusCode(int1);
        response.prettyPrint();
    }
    //------------US_021_-----------------------------------

    @Given("{string} baglantisi uzerinden gecerli authorization bilgileri ve dogru datalarla post body gonderilir")
    public void baglantisi_uzerinden_gecerli_authorization_bilgileri_ve_dogru_datalarla_post_body_gonderilir(String string) {
        String endpoint = "https://www.heallifehospital.com/api/addExpenseHead";
/*
"exp_category": "stationary",
            "description": "stationary expense",
            "is_active": "yes",
            "is_deleted": "no"
 */
        // JSON body oluşturma
        String requestBody = "{\"exp_category\":\"stationary\"" +
                ",\"description\":\"stationary expense\"" +
                ",\"is_active\":\"yes\"" +
                ",\"is_deleted\":\"no\"}";

        // POST isteği gönderme
        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(endpoint);

        // İsteğe verilen yanıtı alma
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Sonuçları yazdırma
        System.out.println("Status code: " + statusCode);

        // eklenen kaydin Id no'sunu atama
        yeniHarcamaKaydiId =response.jsonPath().getInt("addId");
        System.out.println("yeni kayıt id : " + yeniHarcamaKaydiId);

        JSONObject responseJson = new JSONObject(responseBody);
        basariliExpenseAddStatusCode = response.getStatusCode();
        basariliExpenseAddMessage = response.jsonPath().getString("message");
        System.out.println("basariliExpenseAddStatusCode = " + basariliExpenseAddStatusCode);


    }

    @Then("Gonderilen post isleminin donusunde status code'un {int} oldugu dogrulanir")
    public void gonderilen_post_isleminin_donusunde_status_code_un_oldugu_dogrulanir(Integer int1) {

        Assert.assertEquals(200,basariliExpenseAddStatusCode);
    }
    @Then("Gonderilen post isleminin donusunda mesajin {string} oldugu dogrulanir")
    public void gonderilen_post_isleminin_donusunda_mesajin_oldugu_dogrulanir(String string) {
        Assert.assertEquals("Success",basariliExpenseAddMessage);

    }

    @Given("{string} baglantisi uzerinden gecersiz authorization bilgileri ve dogru datalarla post body gonderilir")
    public void baglantisi_uzerinden_gecersiz_authorization_bilgileri_ve_dogru_datalarla_post_body_gonderilir(String string) {

        String endpoint = "https://www.heallifehospital.com/api/addExpenseHead";
        String hataliAuthCode ="ABC12345";
/*
"exp_category": "stationary",
            "description": "stationary expense",
            "is_active": "yes",
            "is_deleted": "no"
 */
        // JSON body oluşturma
        String requestBody = "{\"exp_category\":\"stationary\"" +
                ",\"description\":\"stationary expense\"" +
                ",\"is_active\":\"yes\"" +
                ",\"is_deleted\":\"no\"}";

        // POST isteği gönderme
        Response response01 = given()
                .header("Authorization", "Bearer " + hataliAuthCode)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(endpoint);

        // İsteğe verilen yanıtı alma
        int statusCode = response01.getStatusCode();
        String responseBody = response01.getBody().asString();

        // Sonuçları yazdırma
        System.out.println("Status code: " + statusCode);


        JSONObject responseJson = new JSONObject(responseBody);
        basarisizExpenseAddStatusCode = response01.getStatusCode();
        basarisizExpenseAddMessage = response01.jsonPath().getString("message");
        System.out.println("basarisizExpenseAddStatusCode = " + basarisizExpenseAddStatusCode);


    }
    @Then("Gonderilen post isleminin neticesinde status code'un {int} oldugu dogrulanir")
    public void gonderilen_post_isleminin_neticesinde_status_code_un_oldugu_dogrulanir(Integer int1) {
        Assert.assertEquals(403,basarisizExpenseAddStatusCode);
        System.out.println("sonraki ilk class'tan  :"+yeniHarcamaKaydiId);
    }
    @Then("Gonderilen post isleminin neticesinde mesajin {string} oldugu dogrulanir")
    public void gonderilen_post_isleminin_neticesinde_mesajin_oldugu_dogrulanir(String string) {
        Assert.assertEquals("failed",basarisizExpenseAddMessage);
    }
    @Given("Yeni olusturulan harcama kaydinin API'de bulundugu dogrulanmali")
    public void yeni_olusturulan_harcama_kaydinin_api_de_bulundugu_dogrulanmali() {
        String endpoint = "https://www.heallifehospital.com/api/addExpenseHead";
        // JSON body oluşturma
        String requestBody = "{\"exp_category\":\"stationary\"" +
                ",\"description\":\"stationary expense\"" +
                ",\"is_active\":\"yes\"" +
                ",\"is_deleted\":\"no\"}";

        // POST isteği gönderme
        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(endpoint);

        // İsteğe verilen yanıtı alma
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Sonuçları yazdırma
        System.out.println("Status code: " + statusCode);

        // eklenen kaydin Id no'sunu atama
        yeniHarcamaKaydiId =response.jsonPath().getInt("addId");
        System.out.println("yeni kayıt id : " + yeniHarcamaKaydiId);
            endpoint = "https://www.heallifehospital.com/api/getExpenseHead";

        // GET isteği gönderme
                response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .get(endpoint);

        // İsteğe verilen yanıtı alma
        int statusCode1 = response.getStatusCode();
        String responseBody1 = response.getBody().asString();

        // Sonuçları yazdırma
        System.out.println("Status code: " + statusCode1);
        System.out.println("Response body: " + responseBody1);

        // Yeni kaydın ID'sinin harcama listeleri içinde yer alıp almadığını kontrol etme
        eklenenYeniHarcamaIdListedeMi = response.jsonPath().getString("lists.id").contains(String.valueOf(yeniHarcamaKaydiId));
        System.out.println("Yeni kayıt listede mi?: " + eklenenYeniHarcamaIdListedeMi);
        Assert.assertTrue(eklenenYeniHarcamaIdListedeMi);
    }




    //-------------------ZAFER----------------------------------------------------------------

    @Given("{string} ile yeni bir ziyaret nedeni eklenir, id alinir")
    public void ile_yeni_bir_ziyaret_nedeni_eklenir_id_alinir(String string) {
        String endpoint = "https://www.heallifehospital.com/api/visitorsPurposeAdd";

        // JSON body oluşturma
        String requestBody = "{\"visitors_purpose\":\"deneme purpose\",\"description\":\"deneme description\"}";

        // POST isteği gönderme
        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(endpoint);

        // İsteğe verilen yanıtı alma
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Sonuçları yazdırma
        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: " + responseBody);

        responseBody = response.getBody().asString();
        JSONObject responseJson = new JSONObject(responseBody);
        denemeVisitorPurposeAddid = responseJson.getInt("addId");

        System.out.println("addId: " + denemeVisitorPurposeAddid);
    }

    @Then("Yeni eklenen visitorsPurpose id'nin sistemde bulundugu dogrulanir")
    public void yeni_eklenen_visitors_purpose_id_nin_sistemde_bulundugu_dogrulanir() {
        String url = "https://www.heallifehospital.com/api/visitorsPurposeList";
        // VisitorsPurposeList API sorgusu yapalim
        Response listResponse = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .get("https://www.heallifehospital.com/api/visitorsPurposeList");

// Yanıtı bir değişkene atayalim ve JSON olarak analiz edelim
        String listResponseBody = listResponse.getBody().asString();
        JSONObject listResponseJson = new JSONObject(listResponseBody);

// "lists" bölümündeki nesnelerin kontrolü için for loop oluşturalım
        JSONArray listsArray = listResponseJson.getJSONArray("lists");
        silinenListedeMi = false;
        for (int i = 0; i < listsArray.length(); i++) {
            JSONObject listItem = listsArray.getJSONObject(i);
            String listItemID = listItem.getString("id");

            // silinen "id" değerini kontrol edelim
            if (listItemID.equals(String.valueOf(denemeVisitorPurposeAddid))) {
                eklenenVisitorsPurposeIdListedeMi = true;
                break;
            } else {
                assertFalse("ekleme gerçeklememiş",eklenenVisitorsPurposeIdListedeMi);
            }
        }
        Assert.assertTrue(eklenenVisitorsPurposeIdListedeMi);

    }





    @Then("Yeni alinan id'nin DELETE istemiyle silindigi, status code'un {int} oldugu ve mesajin {string} oldugu dogrulanir")
    public void yeni_alinan_id_nin_delete_istemiyle_silindigi_status_code_un_oldugu_ve_mesajin_oldugu_dogrulanir(Integer int1, String string) {
        String url = "https://www.heallifehospital.com/api/visitorsPurposeDelete";

        Response response = given()
                .header("Authorization", HooksAPI.token)
                .contentType(ContentType.JSON)
                .body("{\"id\": \"" + denemeVisitorPurposeAddid + "\"}")
                .when()
                .delete(url);

        response.then().assertThat()
                .statusCode(200)
                .body("message", equalTo("Success"));

        /*
        String urlList = "https://www.heallifehospital.com/api/visitorsPurposeList";
        response = given().header("Authorization", HooksAPI.token)
                .when().get(urlList);
        response.prettyPrint(); */
    }

    @Given("{string} icin hatali authorization bilgileriyle DELETE sorgusu gonderilir")
    public void icin_hatali_authorization_bilgileriyle_delete_sorgusu_gonderilir(String string) {
        String url = "https://www.heallifehospital.com/api/visitorsPurposeDelete";
        String hataliToken = "A123456";
        int testId = 107;
        int expStatusCode = 403;
        String expMessage = "failed";
        Response response = RestAssured.given()
                .header("Authorization", hataliToken)
                .contentType(ContentType.JSON)
                .body("{\"id\": \"" + testId + "\"}")
                .when()
                .delete(url);

        response.then().assertThat()
                .statusCode(expStatusCode)
                .body("message", equalTo(expMessage));
int sonuc = response.getStatusCode();
        visitorsPurposeDeleteStatusCode = response.getStatusCode();
        visitorsPurposeDeleteMessage= response.jsonPath().getString("message");

    }

    @Then("hatali authorizationla yapilan isteğin mesajının failed, status code'unun {int} oldugu dogrulanir")
    public void hatali_authorizationla_yapilan_isteğin_mesajının_failed_status_code_unun_oldugu_dogrulanir(Integer int1) {

        Assert.assertEquals("failed",visitorsPurposeDeleteMessage);
        Assert.assertEquals(403,visitorsPurposeDeleteStatusCode);
    }

    @Given("{string} ile ziyaret amaci listeden silinir")
    public void ile_ziyaret_amaci_listeden_silinir(String string) {
        String endpoint = "https://www.heallifehospital.com/api/visitorsPurposeAdd";

        // JSON body oluşturma
        String requestBody = "{\"visitors_purpose\":\"deneme purpose\",\"description\":\"deneme description\"}";

        // POST isteği gönderme
        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(endpoint);

        // İsteğe verilen yanıtı alma
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        // Sonuçları yazdırma
        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: " + responseBody);

        responseBody = response.getBody().asString();
        JSONObject responseJson = new JSONObject(responseBody);
        silinecekVisitorPurposeId = responseJson.getInt("addId");

        System.out.println("addId: " + silinecekVisitorPurposeId);

        String url = "https://www.heallifehospital.com/api/visitorsPurposeDelete";

        response = RestAssured.given()
                .header("Authorization", HooksAPI.token)
                .contentType(ContentType.JSON)
                .body("{\"id\": \"" + silinecekVisitorPurposeId + "\"}")
                .when()
                .delete(url);

        response.then().assertThat()
                .statusCode(200)
                .body("message", equalTo("Success"))
                .body("DeletedId", equalTo(String.valueOf(silinecekVisitorPurposeId)));
        response.prettyPrint();
        visitorsPurposedeletedId = response.jsonPath().getInt("DeletedId");
        System.out.println("deletedId = " + visitorsPurposedeletedId);

    }

    @Then("Silinen ziyaret amaci ID'si ile response olarak gelenin ayni oldugu dogrulanir")
    public void silinen_ziyaret_amaci_id_si_ile_response_olaak_gelenin_ayni_oldugu_dogrulanir() {
        Assert.assertEquals(silinecekVisitorPurposeId, visitorsPurposedeletedId);

    }

    @Given("{string} ile ziyaret amaclari listelenir")
    public void ile_ziyaret_amaclari_listelenir(String string) {
// VisitorsPurposeList API sorgusu yapalim
        Response listResponse = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .get("https://www.heallifehospital.com/api/visitorsPurposeList");

// Yanıtı bir değişkene atayalim ve JSON olarak analiz edelim
        String listResponseBody = listResponse.getBody().asString();
        JSONObject listResponseJson = new JSONObject(listResponseBody);

// "lists" bölümündeki nesnelerin kontrolü için for loop oluşturalım
        JSONArray listsArray = listResponseJson.getJSONArray("lists");
        silinenListedeMi = false;
        for (int i = 0; i < listsArray.length(); i++) {
            JSONObject listItem = listsArray.getJSONObject(i);
            String listItemID = listItem.getString("id");

            // silinen "id" değerini kontrol edelim
            if (listItemID.equals(String.valueOf(visitorsPurposedeletedId))) {
                silinenListedeMi = true;
                break;
            } else {
                assertFalse(silinenListedeMi);
            }
        }
        System.out.println("silinenListedeMi = " + silinenListedeMi);
    }

    @Then("Silinen ziyaret amacinin ana listede yer almadigi dogrulanir")
    public void silinen_ziyaret_amacinin_ana_listede_yer_almadigi_dogrulanir() {
        Assert.assertFalse(silinenListedeMi);
    }

    @Given("{string} ile ziyaretci listesi goruntulenir")
    public void ile_ziyaretci_listesi_goruntulenir(String url) {
        url = "https://www.heallifehospital.com/api/visitorsList";


        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .when()
                .get(url);

        visitorsListStatusCode = response.getStatusCode();
        visitorsListMessage = response.jsonPath().getString("message");


    }

    @Then("ziyaretci listesi sorgusunun status code'unun {int} oldugu dogrulanir")
    public void ziyaretci_listesi_sorgusunun_status_code_unun_oldugu_dogrulanir(Integer int1) {
        Assert.assertEquals(String.valueOf(int1), String.valueOf(visitorsListStatusCode));
        System.out.println("visitorsListStatusCode = " + visitorsListStatusCode);
    }

    @Then("Donen sorgunun message'inin {string} oldugu dogrulanir")
    public void donen_sorgunun_message_inin_oldugu_dogrulanir(String message) {
        Assert.assertEquals(message, visitorsListMessage);
        System.out.println("visitorsListMessage = " + visitorsListMessage);
    }

    @Given("hatali authorization koduyla {string} ile ziyaretci listesi cagrilir")
    public void hatali_authorization_koduyla_ile_ziyaretci_listesi_cagrilir(String url) {
        String hataliAuthorizationCode = "A123456";
        url = "https://www.heallifehospital.com/api/visitorsList";


        Response response = given()
                .header("Authorization", "Bearer " + hataliAuthorizationCode)
                .when()
                .get(url);

        visitorsListStatusCode = response.getStatusCode();
        visitorsListMessage = response.jsonPath().getString("message");

    }

    @Given("Ziyaretci listesinden id numarasi {int} olan ziyaretcinin detay bilgileri dogrulanir")
    public void ziyaretci_listesinden_id_numarasi_olan_ziyaretcinin_detay_bilgileri_dogrulanir(Integer int1) {
        String url = "https://www.heallifehospital.com/api/visitorsId";
        Response response = given()
                .header("Authorization", "Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .body("{\"id\": " + int1 + "}")
                .when()
                .get(url);
        System.out.println("response.getStatusCode() = " + response.getStatusCode());
        response.then().assertThat()
                .body("lists.id", equalTo("38"))
                .body("lists.source", equalTo("Online"))
                .body("lists.purpose", equalTo("Improve local visibility for heallifehospital.com"))
                .body("lists.email", equalTo("no-replyAnnora@gmail.com"))
                .body("lists.date", equalTo("2023-05-16"))
                .body("lists.created_at", equalTo("2023-05-15 21:24:47"));


    }
}



