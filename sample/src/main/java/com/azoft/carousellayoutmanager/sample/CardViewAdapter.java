package com.azoft.carousellayoutmanager.sample;


//import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dingli on 2021-04-28.
 */

public class CardViewAdapter extends BaseQuickAdapter<HashMap<String, String>, BaseViewHolder> {


    public CardViewAdapter(@Nullable List<HashMap<String, String>> data) {
        super(R.layout.cardview_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HashMap<String, String> item) {
        helper.setText(R.id.item_name, item.get("name"));
        helper.setImageResource(R.id.item_img, R.mipmap.img1);
        helper.setText(R.id.item_content, item.get("name"));
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}


