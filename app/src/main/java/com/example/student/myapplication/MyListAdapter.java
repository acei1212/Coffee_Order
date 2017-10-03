package com.example.student.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {
    //若要解除藕合，需要設計介面(像Fragment 與 Activity 通訊)
    private MainActivity mainactivity;

    public MyListAdapter(MainActivity mainactivity){
        this.mainactivity = mainactivity;
    }
    @Override
   public int getCount(){
        return mainactivity.getmCoffeeList().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View covnertView, ViewGroup parent) {
        View v = mainactivity.getLayoutInflater().inflate(R.layout.listview_layout,null);

        TextView tvItemId = (TextView)v.findViewById(R.id.itemId);
        TextView tvItemTitle = (TextView)v.findViewById(R.id.itemTitle);
        TextView tvItemPrice = (TextView)v.findViewById(R.id.itemPrice);
        ImageView iv_ItemImage = (ImageView)v.findViewById(R.id.itemImage);

        Coffee coffee = mainactivity.getmCoffeeList().get(position);

        tvItemId.setText("id");
        tvItemTitle.setText(coffee.getTitle());
        tvItemPrice.setText(String.valueOf(coffee.getPrice()));
        iv_ItemImage.setImageResource(coffee.getImg_resource_id());

        return v;
    }

}
