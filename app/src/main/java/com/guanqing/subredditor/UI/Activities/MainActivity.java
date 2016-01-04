package com.guanqing.subredditor.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guanqing.subredditor.events.FinishLoginActivityEvent;
import com.guanqing.subredditor.ui.fragments.LeftDrawerMenuFragment;
import com.guanqing.subredditor.ui.fragments.MainFragment;
import com.guanqing.subredditor.ui.fragments.WelcomeDialog;
import com.guanqing.subredditor.R;
import com.mxn.soul.flowingdrawer_core.FlowingView;
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    @Bind(R.id.leftDrawer) LeftDrawerLayout mLeftDrawerLayout;
    @Bind(R.id.flowing_view) FlowingView mFlowingView;

    protected FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();

        //show the welcome screen when app runs for the first time
        WelcomeDialog fragment = new WelcomeDialog();
        fragment.show(fm.beginTransaction(), "welcome_dialog");

        setContentView(R.layout.activity_main);
        //bind views
        ButterKnife.bind(this);

        LeftDrawerMenuFragment mMenuFragment = (LeftDrawerMenuFragment) fm.findFragmentById(R.id.id_container_menu);

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
