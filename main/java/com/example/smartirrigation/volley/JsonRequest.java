package com.example.smartirrigation.volley;

import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;


/**
 * A request for retrieving a {@link JSONObject} response body at a given URL, allowing for an
 * optional {@link JSONObject} to be passed in as part of the request body.
 * Usable for authorized and unauthorized requests.
 */
public class JsonRequest extends JsonObjectRequest {

    /**
     * User token for authorized requests.
     */
    private final Long accessToken;
    private int requestStatusCode;
    private FragmentManager fragmentManager;
    private String requestUrl;

    /**
     * Create a new authorized request.
     *
     * @param method          The HTTP method to use
     * @param requestUrl      URL to fetch the JSON from
     * @param jsonRequest     A {@link JSONObject} to post with the request. Null is allowed and
     *                        indicates no parameters will be posted along with request.
     * @param successListener Listener to retrieve successful response
     * @param errorListener   Error listener, or null to ignore errors
     * @param fragmentManager Manager to create re-login dialog on HTTP status 403. Null is allowed.
     * @param accessToken     Token identifying user used for user specific requests.
     */
    public JsonRequest(int method, String requestUrl, JSONObject jsonRequest, Response.Listener<JSONObject> successListener,
                       Response.ErrorListener errorListener, FragmentManager fragmentManager, Long accessToken) {
        super(method, requestUrl, jsonRequest, successListener, errorListener);
        this.requestUrl = requestUrl;
        this.fragmentManager = fragmentManager;
        this.accessToken = accessToken;
    }


    /**
     * Create a new unauthorized request.
     *
     * @param method          The HTTP method to use
     * @param requestUrl      URL to fetch the JSON from
     * @param jsonRequest     A {@link JSONObject} to post with the request. Null is allowed and
     *                        indicates no parameters will be posted along with request.
     * @param successListener Listener to retrieve successful response
     * @param errorListener   Error listener, or null to ignore errors
     */
    public JsonRequest(int method, String requestUrl, JSONObject jsonRequest, Response.Listener<JSONObject> successListener,
                       Response.ErrorListener errorListener) {
        this(method, requestUrl, jsonRequest, successListener, errorListener, null, null);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
//        headers.put("Client-Version", WebdeskApp.APP_VERSION);
//        headers.put("Device-Token", WebdeskApp.ANDROID_ID);
//        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("Content-Type", "text/plain");

        // Determine if request should be authorized.
//        if (accessToken != null && accessToken != 0) {
//            String credentials = accessToken + ":";
//            String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//            headers.put("Authorization", "Basic " + encodedCredentials);
//        }
        return headers;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            requestStatusCode = response.statusCode;
//            if (BuildConfig.DEBUG)
//                Timber.d("%s URL: %s. ResponseCode: %d", this.getClass().getSimpleName(), requestUrl, response.statusCode);
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
        return super.parseNetworkResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null) {
            // Save request status code
            requestStatusCode = volleyError.networkResponse.statusCode;
//            if (BuildConfig.DEBUG)
//                Timber.e("%s URL: %s. ERROR: %s", this.getClass().getSimpleName(), requestUrl, new String(volleyError.networkResponse.data));

            // If AccessToken expired. Logout user and redirect to home page.
            if (getStatusCode() == HttpURLConnection.HTTP_FORBIDDEN && fragmentManager != null) {
//                SettingsMy.setActiveUser(null); uncomment latter

            }
        } else {
            requestStatusCode = 9999;
        }
        return super.parseNetworkError(volleyError);
    }

    /**
     * Method returns result statusCode of invoked request.
     *
     * @return HTTP status code.
     */
    public int getStatusCode() {
        return requestStatusCode;
    }
}
