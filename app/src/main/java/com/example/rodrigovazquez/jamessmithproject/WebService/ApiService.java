/**
 * @author
 * @version
 * @see
 */

package com.example.rodrigovazquez.jamessmithproject.WebService;
import com.loopj.android.http.*;


public class ApiService {

    //Cliente asincrono
    private static AsyncHttpClient client  = new AsyncHttpClient();
    //Url base para peticiones GET y POST
    private static final String baseUrl = "http://homeapiportal.azurewebsites.net//";

    /**
     * GET.
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * POST.
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * PUT.
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * DELETE.
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        client.delete(getAbsoluteUrl(url), params, responseHandler);
    }


    /**
     * Url base de todas las peticiones.
     * @param relativeUrl
     * @return
     */
    private static String getAbsoluteUrl(String relativeUrl) {

        return baseUrl + relativeUrl;
    }
}
