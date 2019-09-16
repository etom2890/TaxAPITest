import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jdk.nashorn.internal.ir.annotations.Ignore;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AppTest extends BasicFuctions {


    @Test
    @Parameters({"uri"})
    public void setURI(String uri){
        RestAssured.baseURI = "https://www.boxytest.com/rest/default/V1/guest-carts/4c3dff414546fb4be2dacc3e9895416f/shipping-information";//uri;
    }

    @Test(dataProvider = "Taxrate")

    public void verifyTaxRate(String regionCode, String postalCode, String regionName, String StateRate, String taxAmount, String countryRate,
                              String cityRate, String specialRate, String riskLevel, String regionId) throws JSONException {
       /* try {
            DataClass.getCSVData("C:\\Users\\enlil.tom\\Documents\\sd-2677-run\\TAXRATES_ZIP5_NM201906.csv");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } */
        double taxAmountDouble = Double.parseDouble(taxAmount);
        // Specify the base URL to the RESTful web service
        //setURI(uri);
        String fileName = System.getProperty("fileName");
        // System.out.println(fileName);
        RestAssured.baseURI =fileName; //"https://www.boxycharm.com/rest/default/V1/guest-carts/170ec3410ada340636eacaabe7fc47e8/shipping-information";
        //"https://www.boxypreprod.com/rest/default/V1/guest-carts/2c59ebf17f877a03e3f7c6255d63c73b/shipping-information";
        //"https://www.boxytest.com/rest/default/V1/guest-carts/4c3dff414546fb4be2dacc3e9895416f/shipping-information";
        //"";
        RequestSpecification request = given().contentType("application/json");
        JSONObject newObject = new JSONObject();
        newObject.put("countryId", "US");
        newObject.put("regionId", regionId);
        newObject.put("regionCode", regionCode);
        //newObject.put("region",region);
        newObject.put("postcode", postalCode);


        JSONObject shippingObject = new JSONObject();
        shippingObject.put("shipping_address", newObject);
        shippingObject.put("billing_address", newObject);
        shippingObject.put("shipping_method_code", "boxycharm");
        shippingObject.put("shipping_carrier_code", "boxycharm");

        JSONObject address = new JSONObject();

        address.put("addressInformation", shippingObject);
        JSONArray newArray = new JSONArray();
        request.body(address.toString());
        //.out.println(address.toString());
        Response response= request.post();
        //  response.prettyPrint();
        int statusCode = response.getStatusCode();
        //System.out.println("The status code recieved: " + statusCode);
        String test = response.asString();
        ///System.out.println(response.asString());
        JSONObject obj_JSONObject = new JSONObject(test);
        Double taxRate = getTaxRate(obj_JSONObject);


        //System.out.println(taxAmountDouble);

        double taxExpected = taxAmountDouble * (float) 100;
        DecimalFormat df = new DecimalFormat("##.0###");
        //df.setRoundingMode(RoundingMode.DOWN);
        String taxExpectedDouble = df.format(taxExpected);
        //System.out.println(taxRate);
        //System.out.println(taxExpectedDouble);
        //System.out.println(taxAmountDouble);
        //System.out.println(taxExpected);
        Assert.assertEquals(taxRate.toString(),taxExpectedDouble,"Expected Rate is :"+ taxExpectedDouble +" , " + "Tax Actual is :" + taxRate.toString() + " for zip code:" + postalCode + " for state :" + regionCode);

       // System.out.println("Expected Rate is :"+ taxExpectedDouble +" , " + "Tax Actual is :  " + taxRate.toString() + " for zip code " + postalCode + " for state " + regionCode );
        //given().log().uri();


    }

     /* @Test(dataProvider = "Taxrate")

    public void VerifyTaxRate(String RegionCode,String postalCode,String regionName,String taxAmount,String regionId) throws JSONException  {
        double taxAmountDouble = Double.parseDouble(taxAmount);
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "https://www.boxytest.com/rest/default/V1/guest-carts/4c3dff414546fb4be2dacc3e9895416f/shipping-information";
        RequestSpecification request = RestAssured.given().contentType("application/json");
        JSONObject newObject = new JSONObject();
        newObject.put("countryId","US");
        newObject.put("regionId",regionId);
        newObject.put("regionCode",RegionCode);
        //newObject.put("region",region);
        newObject.put("postcode",postalCode);
        JSONObject shippingObject = new JSONObject();
        shippingObject.put("shipping_address", newObject);
        shippingObject.put("billing_address",newObject);
        shippingObject.put("shipping_method_code","boxycharm");
        shippingObject.put("shipping_carrier_code","boxycharm");

        JSONObject address = new JSONObject();

        address.put("addressInformation",shippingObject);
        JSONArray newArray = new JSONArray();
        request.body(address.toString());
        System.out.println(address.toString());
        Response response = request.post ();

        int statusCode = response.getStatusCode();
        System.out.println("The status code recieved: " + statusCode);
        JSONObject obj_JSONObject = new JSONObject(response.prettyPrint());
        Double taxRate = getTaxRate(obj_JSONObject);


        //System.out.println(taxAmountDouble);

        double taxExpected = taxAmountDouble*(float)100;
        DecimalFormat df = new DecimalFormat("##.0##");
        df.setRoundingMode(RoundingMode.DOWN);
        String taxExpectedDouble = df.format(taxExpected);
        System.out.println(taxRate);
        System.out.println(taxExpectedDouble);
        //System.out.println(taxAmountDouble);
        //System.out.println(taxExpected);
        Assert.assertEquals(taxExpectedDouble,taxRate.toString());



    } */

  /*  @Test
    public void testFiles() {
        ListFileClass.listFiles();
    } */

    @DataProvider(name = "Taxrate")
    public Object[][] taxData() {
        Object[][] newobj = null;
        String[] dataSet = ListFileClass.listFiles();
        List<Object[]>  dataList = Lists.newArrayList();
        for (String fileName : dataSet) {

            try {
                newobj = DataClass.getCSVData(fileName);
                //newobj = DataClass.getCSVData("C:\\Users\\enlil.tom\\Documents\\sd-2677-run\\TAXRATES_ZIP5_NM201906.csv");

            } catch (Exception e) {
                e.printStackTrace();
            }
            dataList.addAll(Arrays.asList(newobj));
        }

        return dataList.toArray(new Object[dataList.size()][]);
    }
}






