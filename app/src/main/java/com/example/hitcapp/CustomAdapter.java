package com.example.hitcapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<AppItem> list;

    public CustomAdapter(Context context, ArrayList<AppItem> list) {
        this.context = context;
        this.list = list;
    }

    public static class AppItem {
        String name;
        String color;

        public AppItem(String name, String color) {
            this.name = name;
            this.color = color;
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // ViewHolder
    static class ViewHolder {
        TextView txtName;
        TextView viewColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_listview, parent, false);

            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.TextView);
            holder.viewColor = convertView.findViewById(R.id.Color);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppItem item = list.get(position);

        // set tên
        holder.txtName.setText(item.name);

        // chữ cái đầu
        holder.viewColor.setText(item.name.substring(0, 1));

        // màu nền
        holder.viewColor.setBackgroundColor(Color.parseColor(item.color));

        return convertView;
    }
}
