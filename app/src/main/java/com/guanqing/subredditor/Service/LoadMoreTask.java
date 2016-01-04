package com.guanqing.subredditor.Service;

import android.os.AsyncTask;
import android.util.Log;

import com.guanqing.subredditor.FrontPageAdapter;
import com.guanqing.subredditor.FrontPageModel;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.SubredditPaginator;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Guanqing on 2016/1/4.
 */
public class LoadMoreTask extends AsyncTask<Void, Void, Void> {
    protected List<FrontPageModel> data = new ArrayList<>();
    protected SubredditPaginator frontPage;
    protected FrontPageAdapter mAdapter;
    protected BGARefreshLayout mRefreshLayout;

    public LoadMoreTask(SubredditPaginator frontPage, FrontPageAdapter adapter, BGARefreshLayout mRefreshLayout){
        this.frontPage = frontPage;
        this.mAdapter = adapter;
        this.mRefreshLayout = mRefreshLayout;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            //load more data
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
        mAdapter.addMoreData(data);
        mRefreshLayout.endLoadingMore();
    }
}
