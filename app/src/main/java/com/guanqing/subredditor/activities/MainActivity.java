package com.guanqing.subredditor.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guanqing.subredditor.Events.FinishLoginActivityEvent;
import com.guanqing.subredditor.Fragments.LeftDrawerMenuFragment;
import com.guanqing.subredditor.Fragments.MainFragment;
import com.guanqing.subredditor.Fragments.WelcomeFragment;
import com.guanqing.subredditor.R;
import com.mxn.soul.flowingdrawer_core.FlowingView;
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;


public class MainActivity extends BaseActivity {

    private LeftDrawerLayout mLeftDrawerLayout;
    private FlowingView mFlowingView;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();

        //show the welcome screen when app runs
        WelcomeFragment fragment = new WelcomeFragment();
        fragment.show(fm.beginTransaction(), "welcome_dialog");

        setContentView(R.layout.activity_main);

        LeftDrawerMenuFragment mMenuFragment = (LeftDrawerMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.leftDrawer);
        mFlowingView = (FlowingView) findViewById(R.id.flowing_view);
        if (mMenuFragment == null) {
            mMenuFragment = new LeftDrawerMenuFragment();
            fm.beginTransaction()
                    .add(R.id.id_container_menu, mMenuFragment).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);
        setupToolbar();

        fm.beginTransaction()
                .replace(R.id.container, new MainFragment())
                .addToBackStack((String) getTitle())
                .commit();
    }

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftDrawerLayout.toggle();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mLeftDrawerLayout.isShownMenu()) {
            mLeftDrawerLayout.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void onEvent(FinishLoginActivityEvent event){
        mLeftDrawerLayout.closeDrawer();
    }
}
