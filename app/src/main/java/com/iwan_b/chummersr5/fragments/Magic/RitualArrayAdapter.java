package com.iwan_b.chummersr5.fragments.Magic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iwan_b.chummersr5.data.Ritual;

import java.util.ArrayList;

public class RitualArrayAdapter extends ArrayAdapter<Ritual> {
    private final Context context;
    private final ArrayList<Ritual> values;

    public RitualArrayAdapter(Context context, int textViewResourceId, ArrayList<Ritual> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.values = items;
    }

    static class ViewHolder {
        public TextView text;
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
        Ritual r = values.get(position);
        holder.text.setText(r.getName());

        return rowView;
    }

    @Override
    public Ritual getItem(int position) {
        return values.get(position);
    }
}