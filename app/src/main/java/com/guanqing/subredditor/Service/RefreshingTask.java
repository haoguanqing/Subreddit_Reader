package com.guanqing.subredditor.Service;

import android.os.AsyncTask;
import android.util.Log;

import com.guanqing.subredditor.FrontPageAdapter;
import com.guanqing.subredditor.FrontPageModel;

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.paginators.TimePeriod;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Guanqing on 2016/1/4.
 */
public class RefreshingTask extends AsyncTask<Void, Void, Void> {
    protected RedditClient redditClient;
    protected List<FrontPageModel> data = new ArrayList<>();
    protected SubredditPaginator frontPage;
    protected FrontPageAdapter mAdapter;
    protected BGARefreshLayout mRefreshLayout;

    public RefreshingTask(RedditClient client, SubredditPaginator frontPage, FrontPageAdapter adapter, BGARefreshLayout mRefreshLayout){
        this.redditClient = client;
        this.frontPage = frontPage;
        this.mAdapter = adapter;
        this.mRefreshLayout = mRefreshLayout;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            //refresh data
            // Adjust the request parameters
            frontPage.setLimit(25);                    // Default is 25 (Paginator.DEFAULT_LIMIT)
            frontPage.setTimePeriod(TimePeriod.MONTH); // Default is DAY (Paginator.DEFAULT_TIME_PERIOD)
            frontPage.setSorting(Sorting.HOT);         // Default is HOT (Paginator.DEFAULT_SORTING)
            // This Paginator is now set up to retrieve the highest-scoring links submitted within the past
            // month, 25 at a time

            Listing<Submission> submissions = frontPage.next();
            for (Submission s : submissions) {
                FrontPageModel model = new FrontPageModel(
                        s.getId(),
                        s.getTitle(),
                        s.getThumbnail(),
                        s.getUrl(),
                        s.getCommentCount(),
                        s.getScore(),
                        s.getId(),
                        s.getSubredditName()
                );
                data.add(model);
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("HGQ", "retrieve task failed ");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        mAdapter.addNewData(data);
        mRefreshLayout.endRefreshing();
    }
}
