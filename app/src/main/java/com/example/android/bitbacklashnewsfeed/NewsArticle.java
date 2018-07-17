package com.example.android.bitbacklashnewsfeed;

import android.graphics.Bitmap;

public class NewsArticle {

    // Publication date for the article
    private String webPublicationDate;

    // Title of the article
    private String webTitle;

    // Url of the article
    private String webUrl;

    // Author of the article
    private String byLine;

    // Bitmap of the thumbnail of the article
    private Bitmap thumbnail;

    /**
     * Constructs a new {@link NewsArticle} object
     *
     * @param webPublicationDate Publication date for the article
     * @param webTitle           Title of the article
     * @param webUrl             Url of the article
     * @param byLine             Author of the article
     * @param thumbnail          Url to the thumbnail of the article
     */
    public NewsArticle(String webPublicationDate, String webTitle, String webUrl, String byLine, Bitmap thumbnail) {
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.byLine = byLine;
        this.thumbnail = thumbnail;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getByLine() {
        return byLine;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }
}
