package com.azoft.carousellayoutmanager.sample;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener;
import com.azoft.carousellayoutmanager.sample.databinding.ActivityRecyclerCardviewBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CardViewAdapter adapter;
    List<HashMap<String, String>> adapterList = new ArrayList<>();
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityRecyclerCardviewBinding binding = ActivityRecyclerCardviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        binding.btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPosition-1 > 0){
                    currentPosition = currentPosition-1;
                }
                binding.recycle.smoothScrollToPosition(currentPosition);
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = currentPosition+1;
                binding.recycle.smoothScrollToPosition(currentPosition);
            }
        });
    }


    void initViews() {
        for (int i = 0; i < 100; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", i + "");
            adapterList.add(map);
        }
        recyclerView = findViewById(R.id.recycle);
        adapter = new CardViewAdapter(adapterList);
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        layoutManager.setMaxVisibleItems(4);
        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
            @Override
            public void onCenterItemChanged(int adapterPosition) {
                currentPosition = adapterPosition;
                Log.d("Main2Activity", "onCenterItemChanged: -----" + adapterPosition + adapterList.get(adapterPosition).get("name"));
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.scrollToPosition(adapterList.size() / 2);
        //??????item?????????
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == currentPosition) {
                    Log.d("Main2Activity", "onItemClick: --------??????");

                } else {
                    Log.d("Main2Activity", "onItemClick: --------???????????????????????????" + position + "/n" + currentPosition);
                }
            }
        });

        /**
         * 1.?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * 2.??????????????????????????????
         * 3.??????setOnItemClickListener?????????????????????OnItemClickListener??????????????????
         */
        DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {

                final int intposition = recyclerView.getChildLayoutPosition(v);
//                Toast.makeText(CardViewActivity.this, "???????????????" + intposition + "\n" + "ding ding", Toast.LENGTH_SHORT).show();
//                Toast.makeText(Main2Activity.this, "???????????????" + intposition + "\n" + names[intposition], Toast.LENGTH_SHORT).show();

            }
        }, recyclerView, layoutManager);
    }


}


