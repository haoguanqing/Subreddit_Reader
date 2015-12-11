package com.guanqing.subredditor.Data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Guanqing on 2015/12/11.
 */
public class FeedContract {
    public static final String CONTENT_AUTHORITY = "com.guanqing.subredditor";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FEEDS = "feeds";
    public static final String PATH_AUTHORS = "authors";
    public static final String PATH_CATEGORIES = "categories";


    public static final class BookEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FEEDS).build();


        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FEEDS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_FEEDS;

        public static final String TABLE_NAME = "books";

        public static final String TITLE = "title";

        public static final String IMAGE_URL = "imgurl";

        public static final String SUBTITLE = "subtitle";

        public static final String DESC = "description";

        public static Uri buildFeedUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
