package com.shenrui.wukongrebate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.entities.ZhiJxItem;

import java.util.List;

/**
 * Created by heikki on 2017/3/18.
 */

public class ZhiAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LOAD_MORE = 4;
    public static final int LOADING = 5;
    public static final int NO_MORE_DATA = 6;
    public final int HEADER = 0;
    public final int NORMAL = 1;
    public final int MULTIIMAGE = 2;
    public final int FOOTER = 3;
    private int load_status = LOAD_MORE;
    private Context mContext;
    // 将RollPagerView作为HeaderView添加给RecyclerView
    private List<ZhiJxItem> mList;
    private View headerView;
    private OnItemClickListener listener;

    public ZhiAdapterNew(Context context, List<ZhiJxItem> list) {
        mContext = context;
        mList = list;
    }

    // headerView的setter
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView == null) {
            if (position + 1 == getItemCount()) return FOOTER;
            ZhiJxItem currNM = mList.get(position);
//            if (currNM.imgextra == null || currNM.imgextra.size() == 0)
//                return NORMAL;
            return MULTIIMAGE;
        }
        if (position == 0) return HEADER;
        if (position + 1 == getItemCount()) return FOOTER;
        ZhiJxItem currNM = mList.get(position - 1);
//        if (currNM.imgextra == null || currNM.imgextra.size() == 0)
//            return NORMAL;
        return MULTIIMAGE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView != null && viewType == HEADER)
            return new ViewHolderNormal(headerView);
//        if (viewType == NORMAL) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_detail_rv, parent, false);
//            return new ViewHolderNormal(view);
//        }

        if (viewType == FOOTER) {
            TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.recy_layout_footer, parent, false);
            return new ViewHolderFooter(view);
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.zhi_jx_item, parent, false);
        return new ViewHolderMultiImage(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == HEADER)
            return;

        if (getItemViewType(position) == FOOTER) {
            ViewHolderFooter footerHolder = (ViewHolderFooter) holder;
            switch (load_status) {
                case LOAD_MORE:
                    footerHolder.text.setText("上拉加载更多...");
                    break;
                case LOADING:
                    footerHolder.text.setText("正在加载更多数据...");
                    break;
                case NO_MORE_DATA:
                    footerHolder.text.setText("没有更多数据");
                    break;
            }
            return;
        }


        final int pos = getRealPosition(holder);
        ZhiJxItem currNM = mList.get(pos);
        switch (getItemViewType(position)) {
            case NORMAL:
//                ViewHolderNormal normalHolder = (ViewHolderNormal) holder;
//                normalHolder.tv_title.setText(currNM.title);
//                normalHolder.tv_author.setText(currNM.source);
//                normalHolder.tv_zan.setText(currNM.replyCount + "跟帖");
//                Glide.with(mContext).load(currNM.imgsrc).into(normalHolder.iv_item_news_detail_rv);

                break;
            case MULTIIMAGE:
                ViewHolderMultiImage imageHolder = (ViewHolderMultiImage) holder;
                imageHolder.iv_center.setImageResource(currNM.getImg());
                imageHolder.tv_title.setText(currNM.getTitle());
                imageHolder.tv_info.setText(currNM.getInfo());
                //Glide.with(mContext).load(currNM.imgsrc).into(imageHolder.iv_item_news_detail_rv);
                break;
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onItemClick(pos, mList.get(pos));
                }
            }
        });
    }

    // 用新数据替换mList内容
    public void changeList(List<ZhiJxItem> newData) {
        mList.clear();
        mList.addAll(newData);
        notifyDataSetChanged();
    }

    public List<ZhiJxItem> getmList() {
        return mList;
    }

    // 上拉加载更多数据时调用
    public void addAll(List<ZhiJxItem> newData) {
        mList.addAll(newData);
        notifyDataSetChanged();
    }

    // 改变脚布局的文字
    public void changeLoadStatus(int status) {
        load_status = status;
        notifyDataSetChanged();
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return headerView == null ? mList.size() + 1 : mList.size() + 2;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    public class ViewHolderNormal extends RecyclerView.ViewHolder {
         ImageView iv_item_news_detail_rv;
         TextView tv_title;
         TextView tv_info;

        public ViewHolderNormal(View itemView) {
            super(itemView);
            if (itemView == headerView) return;
            iv_item_news_detail_rv = (ImageView) itemView.findViewById(R.id.zhi_jx_item_good_img);
            tv_title = (TextView) itemView.findViewById(R.id.zhi_jx_item_good_title);
            tv_info = (TextView) itemView.findViewById(R.id.zhi_jx_item_good_info);
        }
    }

    public class ViewHolderMultiImage extends RecyclerView.ViewHolder {

        private ImageView iv_center;
        private TextView tv_title;
        private TextView tv_info;

        public ViewHolderMultiImage(View itemView) {
            super(itemView);

            iv_center = (ImageView) itemView.findViewById(R.id.zhi_jx_item_good_img);
            tv_title = (TextView) itemView.findViewById(R.id.zhi_jx_item_good_title);
            tv_info = (TextView) itemView.findViewById(R.id.zhi_jx_item_good_info);
        }
    }

    public class ViewHolderFooter extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolderFooter(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }
}
