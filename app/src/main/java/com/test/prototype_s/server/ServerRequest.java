package com.test.prototype_s.server;

import android.content.Context;
import android.util.Log;

import com.test.prototype_s.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class for performing a request to the REST API and returns an object with the JSON content included
 */
public class ServerRequest {

    public enum REQ_TYPE {
        PRODUCTS,
        POSTS
    }

    private static final String TAG = "ServerRequest";

    public ServerRequest() {
    }

    public Result getJSONContent(Context ctx, REQ_TYPE type, String category) {
        HttpURLConnection connection = null;

        try {
            String urlBase = ctx.getString(R.string.url_base);
            String path;
            int itemsCount;
            if (type == REQ_TYPE.PRODUCTS) {
                path = ctx.getString(R.string.path_products);
                itemsCount = ctx.getResources().getInteger(R.integer.prod_items);
            } else {
                path = ctx.getString(R.string.path_posts);
                itemsCount = ctx.getResources().getInteger(R.integer.posts_items);
            }
            String query = "?category=" + category + "&pageItems=" + itemsCount;

            URL url = new URL(urlBase + path + query);

            Log.d(TAG, "[ ServerRequest ] URL: " + url.getHost() + url.getPath() + "?" + url.getQuery());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Locale", "de_DE");
            connection.setRequestProperty("X-apiKey", "C6490912AB3211E680F576304DEC7EB7");

            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "[ ServerRequest ] HTTP_OK!");
            } else {
                Log.d(TAG, "[ ServerRequest ] Got error response: " + connection.getResponseCode() + " : " + connection.getResponseMessage());
                return new Result(Result.ResultCode.RES_HTTP_ERR, connection.getResponseMessage());
            }

            StringBuffer resultBuffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                resultBuffer.append(line + "\n");
            }

            reader.close();

            return new Result(Result.ResultCode.RES_OK, resultBuffer.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new Result(Result.ResultCode.RES_NOK, null);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(Result.ResultCode.RES_NOK, null);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
