package com.guanqing.subredditor.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Guanqing on 2015/12/10.
 */
public class FrontPageDBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "FRONT_PAGE_DATABASE";
    private static String[] SUBREDDIT_NAMES;
    private static final int DATABASE_VERSION = 1;
    private static final String FRONT_PAGE_TABLE_NAME = "front_page_table";
    private static final String _ID = "_id";
    private static final String SUBMISSION_ID = "submission_id";
    private static final String THUMBNAIL_URL = "thumbnail_url";
    private static final String TITLE = "title";
    private static final String COMMENTS_NUM = "comments_num";
    private static final String UPVOTES_NUM = "upvotes_num";


    public FrontPageDBHelper(Context context, String[] subreddits){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SUBREDDIT_NAMES = subreddits;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSQLTable(FRONT_PAGE_TABLE_NAME));

        if(SUBREDDIT_NAMES ==null){return;}
        //create a table to save contents for each subreddit
        for (String subreddit: SUBREDDIT_NAMES){
            db.execSQL(createSQLTable(subreddit));
        }
    }

    private static String createSQLTable(String tableName){
        String SQL_CREATE_SUBREDDIT_TABLE = "CREATE IF NOT EXISTS TABLE " + tableName + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SUBMISSION_ID + " TEXT NOT NULL, " +
                THUMBNAIL_URL + " TEXT, " +
                TITLE + " TEXT, " +
                COMMENTS_NUM + " INTEGER NOT NULL, " +
                UPVOTES_NUM + " INTEGER NOT NULL " +
                "UNIQUE (" + SUBMISSION_ID + ") ON CONFLICT IGNORE)";
        return SQL_CREATE_SUBREDDIT_TABLE;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF EXISTS "+ FRONT_PAGE_TABLE_NAME);
        onCreate(db);
    }
}
