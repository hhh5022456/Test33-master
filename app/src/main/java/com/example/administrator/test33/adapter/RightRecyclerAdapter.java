package com.example.administrator.test33.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test33.CategoriesBean;
import com.example.administrator.test33.MyGridView;
import com.example.administrator.test33.R;

import java.util.List;

/**
 * Created by Administrator on 2018/5/18.
 */

public class RightRecyclerAdapter extends RecyclerView.Adapter<RightRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<CategoriesBean> fatDatas;
    private RecyclerView recyclerView;
    private RightListener listener;
    /**
     * 左侧点击时是否进行第二次滚动
     */
    private boolean secondMove;

    public RightRecyclerAdapter(Context context,List<CategoriesBean> fatDatas, RecyclerView recyclerView) {
        this.context = context;
        this.fatDatas = fatDatas;
        this.recyclerView = recyclerView;
    }
    /**
     * 获取被选中的位置，将选中项移动到顶部，并刷新
     * @param selectedPosition
     */
    public void getSelectedPosition(int selectedPosition) {
        //调用移动位置的方法
        moveToPosition(selectedPosition);
        //刷新
        notifyDataSetChanged();
    }

    /**
     * RecyclerView的点击方法
     * @param listener
     */
    public void setItemClickListener(RightListener listener) {
        this.listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //填充Item中的布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_search_sort_right, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(view,listener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 绑定数据
        holder.tvTitle.setText(fatDatas.get(position).getLname());
        // 获取position位置大分类中小分类的集合
        List<CategoriesBean.HobbiesBean> hobbiesList = fatDatas.get(position).getHobbies();
        // 小分类的数据用GridView展示
        GridViewAdapter adapter = new GridViewAdapter(context, hobbiesList);
        holder.gridView.setAdapter(adapter);
        // gridView中的Item的点击事件
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context,fatDatas.get(position).getHobbies().get(i).getLname(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 点击左侧，右侧移动到顶部的方法，注意区分有两个移动的方法，scrollToPosition和scrollBY
     * scrollToPosition只能将上面的item和屏幕中可见范围的item移动到顶部，还需要计算高度使用scrollBy移动到顶部
     * @param position
     */
    private void moveToPosition(int position) {
        //先从RecyclerView的LayoutManager中获取当前第一项和最后一项的Position
        int firstItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int lastItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        //刚开始不需要进行第二次滚动
        secondMove = false;
        //监听第二次的滚动事件
        recyclerView.addOnScrollListener(new RecyclerViewSecondListener(position));
        if (position <= firstItem) {
            //当要置顶的项在当前显示的第一个项的上面时,屏幕外面，直接调用可移动到顶部
            recyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            //当要置顶的项在当前显示的第一个项后面，最后一个项前面，使用scrollBy,计算距离，进行滚动
            int top = recyclerView.getChildAt(position - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时,调用scrollToPosition只会将该项滑动到屏幕最下面，并不会滑到顶部
            // 需要再次滑动到顶部，也就是需要二次滑动，sendMove置为true;
            // 先滑动一次，置为true后会二次滑动
            recyclerView.scrollToPosition(position);
            secondMove = true;
        }
    }
    @Override
    public int getItemCount() {
        return fatDatas.size();
    }
    /**
     * 第二次的滚动监听滚动监听
     */
    public class RecyclerViewSecondListener extends RecyclerView.OnScrollListener{
        private int position = 0;
        RecyclerViewSecondListener(int position) {
            this.position = position;
        }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动
            if (secondMove){
                secondMove = false;
                moveToPosition(position);
            }
        }
    }


    public  static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTitleHole;
        /**
         * 上面大标签的容器，要监听他的点击事件
         */
        RelativeLayout rlWhole;
        MyGridView gridView;

        public ViewHolder(View itemView,final RightListener listener) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTitleHole = (TextView) itemView.findViewById(R.id.tv_title_whole);
            rlWhole = (RelativeLayout) itemView.findViewById(R.id.rl_whole);
            gridView = (MyGridView) itemView.findViewById(R.id.gridView);
            rlWhole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
    /**
     * RecyclerView没有内置监听器，自定义item点击事件
     */
    public interface RightListener {

        void onItemClick(int position);
    }

}