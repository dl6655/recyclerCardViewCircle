package com.azoft.carousellayoutmanager.sample;

//import android.support.annotation.NonNull;

import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
    //    private String[] names = new String[]{};
    private String[] names = {"德玛西亚", "盖伦", "今日讲解", "今日说法", "宋朝那些事", "王者荣耀", "优酷牛人", "天下第一哦", "红楼梦"};

    RecyclerView recyclerView;
    CardViewAdapter adapter;
    List<HashMap<String, String>> adapterList = new ArrayList<>();
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recycler_cardview);
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
//        for (int i = 0; i < 5; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", i + "");
//            map.put("name", names[i]);
            adapterList.add(map);
        }
        recyclerView = findViewById(R.id.recycle);
        adapter = new CardViewAdapter(adapterList);
        //使用方法和普通的LayoutManager一样
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        //这个是设置除了中间最大的子view外，两边最多能显示的子view的个数
        layoutManager.setMaxVisibleItems(2);
        //设置item的滑动监听，返回当前滑动到的某一项（最大的那一项）
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
        //设置这个主要是为了好看，使数据默认居中（可以在第一次显示数据时使用）
//        recyclerView.scrollToPosition(adapterList.size() / 2);
        //设置item的点击
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == currentPosition) {
                    Log.d("Main2Activity", "onItemClick: --------相同");

                } else {
                    Log.d("Main2Activity", "onItemClick: --------不是相同的不能操作" + position + "/n" + currentPosition);
                }
            }
        });

        /**
         * 1.设置滚动到中间项的点击事件（可见的条目中，任意点击该条目，把该条目的位置设置为中间项）
         * 2.响应中间项的点击事件
         * 3.会与setOnItemClickListener产生冲突，造成OnItemClickListener无法无法响应
         */
        DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClicked(@NonNull RecyclerView recyclerView, @NonNull CarouselLayoutManager carouselLayoutManager, @NonNull View v) {

                final int intposition = recyclerView.getChildLayoutPosition(v);
                Toast.makeText(CardViewActivity.this, "我是中间项" + intposition + "\n" + names[2], Toast.LENGTH_SHORT).show();
//                Toast.makeText(Main2Activity.this, "我是中间项" + intposition + "\n" + names[intposition], Toast.LENGTH_SHORT).show();

            }
        }, recyclerView, layoutManager);
    }


}


