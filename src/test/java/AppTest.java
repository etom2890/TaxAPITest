import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AppTest extends BasicFuctions {


    @Test(dataProvider = "Taxrate")

    public void verifyTaxRate(String regionCode, String postalCode, String regionName, String StateRate, String taxAmount, String countryRate,
                              String cityRate, String specialRate, String riskLevel, String regionId) throws JSONException {

        double taxAmountDouble = Double.parseDouble(taxAmount);
        String uri = System.getProperty("fileName");

        RestAssured.baseURI =uri;
       // "https://www.boxycharm.com/rest/default/V1/guest-carts/170ec3410ada340636eacaabe7fc47e8/shipping-information";
        //"https://www.boxypreprod.com/rest/default/V1/guest-carts/2c59ebf17f877a03e3f7c6255d63c73b/shipping-information";
        //"https://www.boxytest.com/rest/default/V1/guest-carts/4c3dff414546fb4be2dacc3e9895416f/shipping-information";
               // "https://test1.boxytest.com/rest/default/V1/guest-carts/4fc300225193eba48828a8a8014d88fc/shipping-information";
        //"https://test1.boxytest.com/rest/default/V1/guest-carts/4fc300225193eba48828a8a8014d88fc/shipping-information";
        //"";
        RequestSpecification request = given().contentType("application/json");
        JSONObject newObject = prepareJsonObject(regionCode,postalCode,regionId);
        JSONObject shippingObject = prepareJsonObject(newObject);
        JSONObject address = new JSONObject();
        address.put("addressInformation", shippingObject);

        request.body(address.toString());
        Response response= request.post();
        int statusCode = response.getStatusCode();
        //System.out.println("The status code recieved: " + statusCode);
        String test = response.asString();
        ///System.out.println(response.asString());
        JSONObject obj_JSONObject = new JSONObject(test);
        Double taxRate = getTaxRate(obj_JSONObject);
        System.out.println(taxAmountDouble*100);
        System.out.println(taxRate);

        double taxExpected = taxAmountDouble * (float) 100;
        DecimalFormat df = new DecimalFormat("##.0###");
        String taxExpectedDouble = df.format(taxExpected);
        Assert.assertEquals(taxRate.toString(),taxExpectedDouble, "zip code:" + postalCode + " state: " + regionCode);
        //Assert.
    }



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






