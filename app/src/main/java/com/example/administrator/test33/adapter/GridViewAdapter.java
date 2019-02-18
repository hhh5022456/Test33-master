package com.example.administrator.test33.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.test33.CategoriesBean;
import com.example.administrator.test33.R;
import java.util.List;


/**
 * Created by Administrator on 2018/5/21.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<CategoriesBean.HobbiesBean> hobbiesList;

    public GridViewAdapter(Context context, List<CategoriesBean.HobbiesBean> hobbiesList) {
        this.context = context;
        this.hobbiesList = hobbiesList;
    }

    @Override
    public int getCount() {
        return hobbiesList.size();
    }
    @Override
    public Object getItem(int position) {
        return hobbiesList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item_small_sort, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_small);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(hobbiesList.get(position).getLname());
        return convertView;
    }
    public static class ViewHolder {
        TextView textView;
    }
}