import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BasicFuctions {
    public double getTaxRate(JSONObject object) throws JSONException {
        double  taxrate;

        JSONObject obj_JSONArray = object.getJSONObject("totals");
        JSONArray test = obj_JSONArray.getJSONArray("items");

        JSONObject obj_JSONArray1 = new JSONObject(test.get(0).toString());
        String taxAmount = obj_JSONArray1.get("tax_percent").toString();
          taxrate = Double.parseDouble(taxAmount);

        return   taxrate;

    }

    public void prepareJsonObject(){


    }
}
