import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;

public class BasicFuctions  {
    public double getTaxRate(JSONObject object) throws JSONException {
        double  taxrate;

        JSONObject obj_JSONArray = object.getJSONObject("totals");
        JSONArray test = obj_JSONArray.getJSONArray("items");

        JSONObject obj_JSONArray1 = new JSONObject(test.get(0).toString());
        String taxAmount = obj_JSONArray1.get("tax_percent").toString();
          taxrate = Double.parseDouble(taxAmount);

        return   taxrate;

    }

    public JSONObject prepareJsonObject(String regionCode, String postalCode, String regionId) throws JSONException {


        JSONObject newObject = new JSONObject();
        newObject.put("countryId", "US");
        newObject.put("regionId", regionId);
        //newObject.put("regionCode", regionCode);
        newObject.put("postcode", postalCode);




        return newObject;
    }


    public JSONObject prepareJsonObject(JSONObject object) throws JSONException {

        JSONObject shippingObject = new JSONObject();
        shippingObject.put("shipping_address", object);
        shippingObject.put("billing_address", object);
        shippingObject.put("shipping_method_code", "boxycharm");
        shippingObject.put("shipping_carrier_code", "boxycharm");

        return shippingObject;
    }
}
