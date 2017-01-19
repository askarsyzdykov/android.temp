package kz.base.app.mvp.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Date: 14.06.2016
 * Time: 14:11
 *
 * @author Askar Syzdykov
 */
public class MyError extends Throwable {
    public MyError(ResponseBody responseBody) {
        super(getMessage(responseBody));
    }

    private static String getMessage(ResponseBody responseBody) {
        try {
            JSONObject rootObject = new JSONObject(responseBody.string());
            JSONArray errors = rootObject.optJSONArray("errors");
            if (errors != null && errors.length() > 0){
                return errors.getString(0);
            } else if (errors == null) {
                return rootObject.optString("error");
            }
            return errors.toString();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return "Unknown exception";
    }
}
