package com.giiso.submmited.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.giiso.submmited.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/17.
 */

public class SpinnerAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater li;
    private ArrayList<SpinnerBean> dataList;

    public SpinnerAdapter(Context ctx, ArrayList<SpinnerBean> dataList) {
        this.ctx = ctx;
        this.li = LayoutInflater.from(ctx);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public SpinnerBean getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_spinner, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();// get convertView's holder
        holder.car_brand.setText(getItem(position).getValue());
        return convertView;
    }

    class ViewHolder {
        TextView car_brand;
        public ViewHolder(View convertView){
            car_brand = (TextView) convertView.findViewById(R.id.tv_spinner);
            convertView.setTag(this);//set a viewholder
        }
    }

}
