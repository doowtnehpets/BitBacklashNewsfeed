package com.example.android.bitbacklashnewsfeed;

import android.net.Uri;
import android.support.annotation.Nullable;

public final class GuardianUrlBuilder {

    // Sections
    public static final String SECTION_GAMES = "games",
            SECTION_SPORTS = "sport",
            SECTION_CULTURE = "culture",
            SECTION_POLITICS = "politics",
            SECTION_BOOKS = "books",
            SECTION_TECHNOLOGY = "technology";
    // Base of URL
    private static final String URL_BASE = "https://content.guardianapis.com/search";
    // API Key for Guardian
    private static final String URL_API_KEY = "85a473fc-b895-4de9-a6c5-16ae66dab850";
    // From date, so articles arent

    /**
     * Returns a Guardian API URL string
     *
     * @param section section in Guardian
     * @return URL string
     */
    public static String buildUrl(@Nullable String section, int noOfArticles) {

        // Build upon the base URL
        Uri baseUri = Uri.parse(URL_BASE);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Add show-fields section to get thumbnail and byline
        uriBuilder.appendQueryParameter("show-fields", "thumbnail,byline");

        // Add the section if included
        if (section != null) uriBuilder.appendQueryParameter("section", section);

        // Add the number of articles if in the correct range
        uriBuilder.appendQueryParameter("page-size", Integer.toString(noOfArticles));

        // Add the API key
        uriBuilder.appendQueryParameter("api-key", URL_API_KEY);

        return uriBuilder.toString();
    }

}
