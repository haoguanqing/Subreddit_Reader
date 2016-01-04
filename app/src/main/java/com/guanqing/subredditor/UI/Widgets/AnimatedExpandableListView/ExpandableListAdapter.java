package com.guanqing.subredditor.UI.Widgets.AnimatedExpandableListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guanqing.subredditor.R;

import java.util.List;

/**
 * Created by Guanqing on 2015/12/14.
 */
public class ExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;

    private List<GroupItem> items;

    public ExpandableListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<GroupItem> items) {
        this.items = items;
    }

    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildItem.ChildHolder holder;
        ChildItem item = getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new ChildItem.ChildHolder();
            convertView = inflater.inflate(R.layout.subreddit_child_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.tvChildSubreddit);
            convertView.setTag(holder);
        } else {
            holder = (ChildItem.ChildHolder) convertView.getTag();
        }

        holder.title.setText(item.title);

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).items.size();
    }

    @Override
    public GroupItem getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupItem.GroupHolder holder;
        GroupItem item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new GroupItem.GroupHolder();
            convertView = inflater.inflate(R.layout.subreddit_group_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.tvGroupSubreddits);
            convertView.setTag(holder);
        } else {
            holder = (GroupItem.GroupHolder) convertView.getTag();
        }

        holder.title.setText(item.title);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }











}
