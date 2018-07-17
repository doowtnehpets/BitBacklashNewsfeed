package com.example.android.bitbacklashnewsfeed;

import android.support.annotation.Nullable;

public final class GuardianUrlBuilder {

    // Sections
    public static final String SECTION_GAMES = "&section=games",
            SECTION_SPORTS = "&section=sport",
            SECTION_CULTURE = "&section=culture",
            SECTION_POLITICS = "&section=politics",
            SECTION_BOOKS = "&section=books",
            SECTION_TECHNOLOGY = "&section=technology";
    // Base of URL
    private static final String URL_BASE = "https://content.guardianapis.com/search?show-fields=thumbnail%2Cbyline";
    // API Key for Guardian
    private static final String URL_API_KEY = "&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850";

    /**
     * Returns a Guardian API URL string
     *
     * @param section section in Guardian
     * @return URL string
     */
    public static String buildUrl(@Nullable String section) {
        StringBuilder stringBuilder = new StringBuilder();

        // Add the URL Base
        stringBuilder.append(URL_BASE);

        // If the section isn't null then add that
        if (section != null) stringBuilder.append(section);

        // Add the API Key
        stringBuilder.append(URL_API_KEY);

        return stringBuilder.toString();
    }

}
