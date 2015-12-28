package come.zhu.com.hushiguang.zhudome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.InjectView;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.common.AutoViewPager;
import come.zhu.com.hushiguang.zhudome.entity.HomeMsgEntity;

/**
 * Created by office on 2015/12/5.
 */
public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.MyViewHolder> {


    private ArrayList<HomeMsgEntity.StoriesEntity> newsList = new ArrayList<>();
    Context mContext;
    onItemClickListener onItemClickListener;
    private ArrayList<HomeMsgEntity.TopStoriesEntity> listAd = new ArrayList<>();

    private int IS_HEAD = 0;
    private int IS_BODY = 1;

    public void setNewsList(ArrayList<HomeMsgEntity.StoriesEntity> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void setListAd(ArrayList<HomeMsgEntity.TopStoriesEntity> listAd) {
        this.listAd = listAd;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(MainViewAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onViewPagerOnclick(View v, int postion);

        void onItemClick(View v, int postion);
    }

    public MainViewAdapter(ArrayList<HomeMsgEntity.StoriesEntity> newsList, ArrayList<HomeMsgEntity.TopStoriesEntity> listAd, Context mContext) {
        this.newsList = newsList;
        this.listAd = listAd;
        this.mContext = mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == IS_HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_header, parent, false);
            holder = new MyViewHolder(view, IS_HEAD);
            return holder;
        } else if (viewType == IS_BODY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main, parent, false);
            holder = new MyViewHolder(view, IS_BODY);
            return holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (position == 0 && holder.viewType == IS_HEAD) {

            holder.mAutoViewPager.isHavaPoint(true);
            holder.mAutoViewPager.setSize(listAd.size());
            holder.mAutoViewPager.autoViewPagerCallBack = new AutoViewPager.AutoViewPagerCallBack() {
                @Override
                public void setView(View view, ImageView imageView, TextView textView) {
                    final int postion = (int) view.getTag();
                    final HomeMsgEntity.TopStoriesEntity topStoriesEntity = listAd.get(postion);
                    Glide.with(mContext).load(topStoriesEntity.getImage()).into(imageView);
                    textView.setText(topStoriesEntity.getTitle());

                    if (onItemClickListener != null) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.onViewPagerOnclick(v, postion);
                            }
                        });
                    }
                }
            };


        } else {
            holder.mTextView.setText(newsList.get(position - 1).getTitle().trim());
            Glide.with(mContext).load(newsList.get(position - 1).getImages().get(0)).into(holder.mImageView);
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, holder.getAdapterPosition() - 1);
                    }
                });
            }
        }


    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return IS_HEAD;
        else
            return IS_BODY;
    }


    @Override
    public int getItemCount() {
        return newsList.size() + 1;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.adapter_dsc)
        TextView mTextView;
        @InjectView(R.id.adapter_imageview)
        ImageView mImageView;
        @InjectView(R.id.adapter_viewpager)
        AutoViewPager mAutoViewPager;

        public int viewType;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType == IS_HEAD) {
                mAutoViewPager = (AutoViewPager) itemView.findViewById(R.id.adapter_viewpager);
            } else {
                mTextView = (TextView) itemView.findViewById(R.id.adapter_dsc);
                mImageView = (ImageView) itemView.findViewById(R.id.adapter_imageview);
            }


        }
    }
}
