package com.example.android.bitbacklashnewsfeed;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods for querying data from the guardian API
 */
public final class NewsQueryUtils {

    // Log tag for easy logging
    private static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * Private constructor because a {@link NewsQueryUtils} object shouldn't be created
     */
    private NewsQueryUtils() {
    }

    /**
     * Convert a given string to a URL object
     *
     * @param stringUrl A URL to convert
     * @return URL object
     */
    private static URL createUrl(String stringUrl) {
        // Set the url to null to return that if there's any issue creating the URL
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the url", e);
        }

        return url;
    }

    /**
     * Convert the {@link InputStream} into a string which contains the JSON response
     *
     * @param inputStream input stream to convert to JSON response
     * @return string containing the JSON response
     * @throws IOException if there's a problem reading from the stream
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        // If the input stream is not null then read each line from the stream and build a string from it
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Make an HTTP request from the given URL and return a string for the JSON data
     *
     * @param url URL to query
     * @return JSON string data
     * @throws IOException if there's an IO Exception
     */
    private static String makeHttpRequest(URL url) throws IOException {
        // Set the response by default to be empty
        String jsonResponse = "";

        // If the url is null return early
        if (url == null) return jsonResponse;

        // Set up the HTTP URL connection and input stream
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            // Set up the type of connection and attempt connecting
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            // If the connection is successful, then parse the response
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            // Close the URL connection and input stream if they're not null
            if (httpURLConnection != null) httpURLConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }
        return jsonResponse;
    }

    /**
     * Return a list of {@link NewsArticle} objects from parsing the JSON response
     *
     * @param newsArticleJSON string containing the JSON response
     * @return list of {@link NewsArticle}
     */
    private static List<NewsArticle> extractFeatureFromJson(String newsArticleJSON) {
        // If JSON string is empty or null, return early
        if (TextUtils.isEmpty(newsArticleJSON)) return null;

        // Create an emtpy NewsArticle list to add NewsArticle objects to
        List<NewsArticle> newsArticles = new ArrayList<>();

        // Parse the JSON response and create new NewsArticles from it
        try {
            // Grab the JSON base object then grab the response object from that
            JSONObject jsonObjectRoot = new JSONObject(newsArticleJSON);
            JSONObject jsonObjectResponse = jsonObjectRoot.getJSONObject("response");

            // Grab the results array from the base object
            JSONArray jsonArrayResults = jsonObjectResponse.getJSONArray("results");

            // Loop through the array and create new NewsArticle objects from the results
            for (int i = 0; i < jsonArrayResults.length(); i++) {
                // Get the JSON result at the given position
                JSONObject jsonObjectResultPosition = jsonArrayResults.getJSONObject(i);

                // Grab each section for the JSON object to add to the array
                String webPublicationDate = jsonObjectResultPosition.getString("webPublicationDate");
                String webTitle = jsonObjectResultPosition.getString("webTitle");
                String webUrl = jsonObjectResultPosition.getString("webUrl");

                JSONObject jsonObjectFields = jsonObjectResultPosition.getJSONObject("fields");

                String byLine = jsonObjectFields.optString("byline");
                String thumbnail = jsonObjectFields.optString("thumbnail");

                // Add a new NewsArticle from the data
                newsArticles.add(new NewsArticle(webPublicationDate, webTitle, webUrl, byLine, downloadBitmap(thumbnail)));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }

        return newsArticles;
    }

    /**
     * Query guardian API with the given URL and return a list of {@link NewsArticle} objects
     *
     * @param requestUrl URL to request data from guardian
     * @return List of NewsArticle objects
     */
    public static List<NewsArticle> fetchNewsArticleData(String requestUrl) {
        // Create the URL from the given requestUrl
        URL url = createUrl(requestUrl);

        // Make the request for the JSON from the URL
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractFeatureFromJson(jsonResponse);
    }

    /**
     * Load an image from a URL and return a {@link Bitmap}
     *
     * @param url string of the URL link to the image
     * @return Bitmap of the image
     */
    private static Bitmap downloadBitmap(String url) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return bitmap;
    }

}
