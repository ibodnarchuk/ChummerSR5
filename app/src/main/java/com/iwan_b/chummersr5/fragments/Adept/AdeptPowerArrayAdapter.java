package com.iwan_b.chummersr5.fragments.Adept;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iwan_b.chummersr5.data.AdeptPower;

import java.util.ArrayList;

public class AdeptPowerArrayAdapter extends ArrayAdapter<AdeptPower> {
    private final Context context;
    private final ArrayList<AdeptPower> values;

    public AdeptPowerArrayAdapter(Context context, int textViewResourceId, ArrayList<AdeptPower> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(android.R.id.text1);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        AdeptPower s = values.get(position);
        holder.text.setText(s.getName());

        return rowView;
    }

    @Override
    public AdeptPower getItem(int position) {
        return values.get(position);
    }

    static class ViewHolder {
        public TextView text;
    }
}