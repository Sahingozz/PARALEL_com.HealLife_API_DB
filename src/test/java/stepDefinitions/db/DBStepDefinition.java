package stepDefinitions.db;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import utilities.DB_Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static utilities.DB_Utils.*;



public class DBStepDefinition {
    List<Object> UserEmailList = new ArrayList<>();
    private static final String DB_URL = "jdbc:mysql://194.140.198.209/heallife_hospitaltraining";
    private static final String DB_USERNAME = "heallife_hospitaltraininguser";
    private static final String DB_PASSWORD = "PI2ZJx@9m^)3";
    int sabahRandevulari;
    int ogledenSonraRandevulari;

    String language;

    @Given("Database connection established")
    public void database_connection_established() {
        createConnection();
    }

    @Given("From the Users table,{string} data of the user whose {string} and {string} information is entered are retrieved")
    public void from_the_users_table_data_of_the_user_whose_and_information_is_entered_are_retrieved(String email, String firstName, String lastName) {
        String query = "SELECT email FROM u480337000_tlb_training.users where first_name='" + firstName + "' and last_name= '" + lastName + "';";
        UserEmailList = getColumnData(query, email);
    }

    @Then("User's {string} data is verified")
    public void user_s_data_is_verified(String email) {
        assertTrue(UserEmailList.get(0).equals(email));

    }

    @Then("Database connection is closed")
    public void database_connection_is_closed() {
        closeConnection();

    }

    //---------------------------ZAFER -----------------------------------
    public static Connection DatabaseBaglantisi() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    @Given("Database baglantisi kurulur")
    public void database_baglantisi_kurulur() throws SQLException {
       DatabaseBaglantisi();

    }
    @Then("DB uzerinde ogleden onceki randevu sayisinin ogleden sonrakilerden az oldugu dogrulanir")
    public void db_uzerinde_ogleden_onceki_randevu_sayisinin_ogleden_sonrakilerden_az_oldugu_dogrulanir() throws SQLException {
        String query = "SELECT COUNT(*) FROM appointment WHERE HOUR(time) < 12";
        Statement statement = DatabaseBaglantisi().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        sabahRandevulari = resultSet.getInt(1);
        System.out.println("sabahRandevulari = " + sabahRandevulari);

        query = "SELECT COUNT(*) FROM appointment WHERE HOUR(time) > 12";
        statement = DatabaseBaglantisi().createStatement();
        resultSet = statement.executeQuery(query);
        resultSet.next();
        ogledenSonraRandevulari = resultSet.getInt(1);
        System.out.println("ogledenSonraRandevulari = " + ogledenSonraRandevulari);

        Assert.assertTrue(ogledenSonraRandevulari>sabahRandevulari);



    }
    @Then("Database baglantisi kapatilir")
    public void database_baglantisi_kapatilir() {
        DB_Utils.closeConnection();
        }
    @Then("Charge_id'si {string} ile baslayan icerikler bulunur,tekrarlar cikarilip, listelendigi dogrulanir")
    public void charge_id_si_ile_baslayan_icerikler_bulunur_tekrarlar_cikarilip_listelendigi_dogrulanir(String string) throws SQLException {
        String query = "SELECT DISTINCT charge_type_id FROM charge_categories WHERE name LIKE 'P%'";
        Statement statement = DatabaseBaglantisi().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<Integer>chargeTypeIds = new ArrayList<>();
        while (resultSet.next()) {
            int chargeTypeId = resultSet.getInt("charge_type_id");
            chargeTypeIds.add(chargeTypeId);
        }

        List<Integer> beklenenChargeTypeIds = new ArrayList<>(Arrays.asList(6, 7));
        assertEquals(beklenenChargeTypeIds, chargeTypeIds);
    }
    @Then("Languages bolumunde short_code'u {string} olan verilerin {string} oldugu dogrulanir")
    public void languages_bolumunde_short_cod_u_olan_verilerin_oldugu_dogrulanir(String shortCode, String beklenenLanguage) throws SQLException {
        String query = "SELECT language FROM languages WHERE short_code = '" + shortCode +"'";
        Statement statement = DatabaseBaglantisi().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();
        language = resultSet.getString("language");
        System.out.println("language = " + language);
        assertEquals(beklenenLanguage, language);
    }
    }








