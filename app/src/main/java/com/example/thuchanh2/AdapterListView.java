package com.example.thuchanh2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListView extends BaseAdapter {
    ArrayList<City> arrayList;

    public AdapterListView(ArrayList<City> arrayList) {
        this.arrayList = arrayList;
    }
    public class ListViewItem{
        TextView name,city,date,amount;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewItem listViewItem = null;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(),R.layout.customelistview,null);
            listViewItem = new ListViewItem();
            listViewItem.name = convertView.findViewById(R.id.Nametxt);
            listViewItem.city = convertView.findViewById(R.id.citytxt);
            listViewItem.date = convertView.findViewById(R.id.datetxt);
            listViewItem.amount = convertView.findViewById(R.id.amounttxt);
            convertView.setTag(listViewItem);
        }
        else {
        listViewItem =(ListViewItem) convertView.getTag();
        }
        City city = arrayList.get(position);
        listViewItem.name.setText(city.getName());
        listViewItem.date.setText(city.getDates());
        listViewItem.city.setText(city.getCitys());
        listViewItem.amount.setText(city.getAmount()+" VND");
        return convertView;
    }
}
