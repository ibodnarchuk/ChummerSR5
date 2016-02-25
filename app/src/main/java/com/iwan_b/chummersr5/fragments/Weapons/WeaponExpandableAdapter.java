package com.iwan_b.chummersr5.fragments.Weapons;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.ParentItem;
import com.iwan_b.chummersr5.data.Weapon;

import java.util.List;

public class WeaponExpandableAdapter extends BaseExpandableListAdapter {
    private final List<ParentItem> itemList;
    private final LayoutInflater inflater;
    private final Context context;

    public WeaponExpandableAdapter(Context context, List<ParentItem> itemList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @Override
    public Weapon getChild(int groupPosition, int childPosition) {
        return itemList.get(groupPosition).getChildItemList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return itemList.get(groupPosition).getChildItemList().size();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             final ViewGroup parent) {
        View resultView = convertView;
        ViewHolder holder;


        if (resultView == null) {

            resultView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.textLabel = (TextView) resultView.findViewById(R.id.lblListItem);
            resultView.setTag(holder);
        } else {
            holder = (ViewHolder) resultView.getTag();
        }

        final Weapon item = getChild(groupPosition, childPosition);

        holder.textLabel.setText(item.getName().toString());

        return resultView;
    }

    @Override
    public ParentItem getGroup(int groupPosition) {
        return itemList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return itemList.size();
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View theConvertView, ViewGroup parent) {
        View resultView = theConvertView;
        ViewHolder holder;

        if (resultView == null) {
            resultView = inflater.inflate(R.layout.list_group, null);
            holder = new ViewHolder();
            holder.textLabel = (TextView) resultView.findViewById(R.id.lblListHeader);
            resultView.setTag(holder);
        } else {
            holder = (ViewHolder) resultView.getTag();
        }

        final ParentItem item = getGroup(groupPosition);


        if (isExpanded) {
            holder.textLabel.setText("[-]  " + item.name.toString());
        } else {
            holder.textLabel.setText("[+]  " + item.name.toString());
        }

        return resultView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static final class ViewHolder {
        TextView textLabel;
    }
}