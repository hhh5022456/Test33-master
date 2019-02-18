package com.example.administrator.test33;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.example.administrator.test33.adapter.LeftRecyclerAdapter;
import com.example.administrator.test33.adapter.RightRecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SearchSortActivity extends BaseActivity  {

    @BindView(R.id.rv_sort_left)
    RecyclerView leftRecyclerView;
    @BindView(R.id.rv_sort_right)
    RecyclerView rightRecyclerView;

    private RecyclerView.LayoutManager leftLayoutManager;
    private RecyclerView.LayoutManager rightLayoutManager;
    private LeftRecyclerAdapter leftAdapter;
    private RightRecyclerAdapter rightAdapter;
    private List<CategoriesBean> fatDatas;

    @Override
    protected void onViewBindFinish() {
        super.onViewBindFinish();
        initData();
        initView();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search_org_assortment;
    }

    @Override
    protected String getUmengPageTitle() {
        return "机构全部分类";
    }

    private void initData() {
        fatDatas = new Gson().fromJson(getAssetsData("categories.txt"), new TypeToken<List<CategoriesBean>>() {
        }.getType());

        leftAdapter = new LeftRecyclerAdapter(this, fatDatas, leftRecyclerView);
        //左侧列表的点击事件
        leftAdapter.setItemClickListener(new LeftRecyclerAdapter.LeftListener() {
            @Override
            public void onItemClick(int position) {
                //向适配器中返回点击的位置，在适配器中进行操作
                leftAdapter.getSelectedPosition(position);
                rightAdapter.getSelectedPosition(position);
            }
        });
        leftRecyclerView.setAdapter(leftAdapter);
        rightAdapter = new RightRecyclerAdapter(getApplicationContext(), fatDatas, rightRecyclerView);
        //右侧列表的点击事件
        rightAdapter.setItemClickListener(new RightRecyclerAdapter.RightListener() {
           @Override
           public void onItemClick(int position) {
               Toast.makeText(SearchSortActivity.this,fatDatas.get(position).getLname(),Toast.LENGTH_SHORT).show();
           }
        });
        //右侧列表的滚动事件
        rightRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取右侧列表的第一个可见Item的position
                int TopPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                //左侧得到这个position
                leftAdapter.getSelectedPosition(TopPosition);
            }
        });
        rightRecyclerView.setAdapter(rightAdapter);
    }

    private void initView() {
        // 设置布局管理器
        leftLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        leftRecyclerView.setLayoutManager(leftLayoutManager);
        rightLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rightRecyclerView.setLayoutManager(rightLayoutManager);
    }

    /**
     * 读取文件数据的方法
     * @param fileName
     * @return
     */
    private String getAssetsData(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line;
            }
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

