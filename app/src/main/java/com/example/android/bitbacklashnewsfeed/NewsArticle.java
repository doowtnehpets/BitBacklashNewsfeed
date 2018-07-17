package com.example.android.bitbacklashnewsfeed;

import android.graphics.Bitmap;

public class NewsArticle {

    // Section ID of the article
    String sectionId;

    // Publication date for the article
    String webPublicationDate;

    // Title of the article
    String webTitle;

    // Url of the article
    String webUrl;

    // Author of the article
    String byLine;

    // Bitmap of the thumbnail of the article
    Bitmap thumbnail;

    /**
     * Constructs a new {@link NewsArticle} object
     *
     * @param sectionId          Section ID of the article
     * @param webPublicationDate Publication date for the article
     * @param webTitle           Title of the article
     * @param webUrl             Url of the article
     * @param byLine             Author of the article
     * @param thumbnail          Url to the thumbnail of the article
     */
    public NewsArticle(String sectionId, String webPublicationDate, String webTitle, String webUrl, String byLine, Bitmap thumbnail) {
        this.sectionId = sectionId;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.byLine = byLine;
        this.thumbnail = thumbnail;
    }

    public String getSectionId() {
        return sectionId;
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
