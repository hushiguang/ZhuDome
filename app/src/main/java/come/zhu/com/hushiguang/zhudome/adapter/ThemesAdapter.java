package come.zhu.com.hushiguang.zhudome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.entity.ThemesNewsMsgEntity;

/**
 * Created by office on 2015/12/5.
 */
public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.MyViewHolder> {


    private ArrayList<ThemesNewsMsgEntity.StoriesEntity> newsList = new ArrayList<>();
    private ArrayList<ThemesNewsMsgEntity.EditorsEntity> editorList = new ArrayList<>();
    private ThemesNewsMsgEntity eneity;
    Context mContext;
    onItemClickListener onItemClickListener;

    private int IS_HEAD = 0;
    private int IS_BODY = 1;


    public void setNewsList(ArrayList<ThemesNewsMsgEntity.StoriesEntity> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }
    public void seteditorList(ArrayList<ThemesNewsMsgEntity.EditorsEntity> editorList) {
        this.editorList = editorList;
        notifyDataSetChanged();
    }
    public void setEntity(ThemesNewsMsgEntity eneity) {
        this.eneity = eneity;
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(ThemesAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public interface onItemClickListener {

        void onItemEditorClick(View v);

        void onImageViewClick(View v);

        void onItemClick(View v, int postion);
    }

    public ThemesAdapter(ArrayList<ThemesNewsMsgEntity.StoriesEntity> newsList,
                         ArrayList<ThemesNewsMsgEntity.EditorsEntity> editorList,
                         ThemesNewsMsgEntity eneity, Context mContext) {
        this.newsList = newsList;
        this.editorList = editorList;
        this.mContext = mContext;
        this.eneity = eneity;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == IS_HEAD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_themes_header, parent, false);
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

            Glide.with(mContext).load(eneity.getImage()).into(holder.headImg);
            holder.headTitle.setText(eneity.getDescription());

            if (editorList != null && editorList.size() != 0) {
                holder.mLinearLayout.removeAllViews();
                for (ThemesNewsMsgEntity.EditorsEntity entity : editorList) {
                    final ImageView imageView = new ImageView(mContext);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.height = 80;
                    layoutParams.width = 80;
                    layoutParams.leftMargin = 20;

                    Glide.with(mContext).load(entity.getAvatar()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });

                    holder.mLinearLayout.addView(imageView,layoutParams);
                }

                if (onItemClickListener != null) {
                    holder.headImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onImageViewClick(v);
                        }
                    });

                    holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemEditorClick(v);
                        }
                    });
                }

            }

        } else {

            holder.mTextView.setText(newsList.get(position - 1).getTitle().trim());
            if (newsList.get(position - 1).getImages() == null || newsList.get(position - 1).getImages().size() == 0){
                holder.mImageView.setVisibility(View.GONE);
            }else
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


        TextView mTextView, headTitle;
        ImageView mImageView, headImg;
        LinearLayout mLinearLayout;
        public int viewType;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType == IS_HEAD) {
                mLinearLayout = (LinearLayout) itemView.findViewById(R.id.adapter_themes_linear);
                headTitle = (TextView) itemView.findViewById(R.id.adapter_themes_title);
                headImg = (ImageView) itemView.findViewById(R.id.adapter_themes_img);
            } else {
                mTextView = (TextView) itemView.findViewById(R.id.adapter_dsc);
                mImageView = (ImageView) itemView.findViewById(R.id.adapter_imageview);
            }


        }
    }
}
