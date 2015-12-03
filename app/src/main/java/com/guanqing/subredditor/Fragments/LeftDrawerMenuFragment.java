package com.guanqing.subredditor.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanqing.subredditor.R;
import com.guanqing.subredditor.UI.CircleTransformation;
import com.guanqing.subredditor.Activities.LoginActivity;
import com.mxn.soul.flowingdrawer_core.MenuFragment;
import com.squareup.picasso.Picasso;



public class LeftDrawerMenuFragment extends MenuFragment {

    private ImageView ivMenuUserProfilePhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ivMenuUserProfilePhoto = (ImageView) view.findViewById(R.id.ivMenuUserProfilePhoto);
        setupHeader();

        ImageView userProfileImage = (ImageView)view.findViewById(R.id.ivMenuUserProfilePhoto);
        TextView userProfileName = (TextView)view.findViewById(R.id.ivUserProfileName);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        };
        userProfileImage.setOnClickListener(listener);
        userProfileName.setOnClickListener(listener);
        return  setupReveal(view) ;
    }

    private void setupHeader() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);

        Picasso.with(getActivity())
                .load(R.drawable.profile_icon)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);
    }

}
