package come.zhu.com.hushiguang.zhudome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.common.Util;
import come.zhu.com.hushiguang.zhudome.entity.ComnentEntity;

/**
 * Created by office on 2015/12/8.
 */
public class LongCommentAdapter extends RecyclerView.Adapter<LongCommentAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<ComnentEntity.CommentsEntity> longCommentList;
    onClickListner onClickListner;

    public LongCommentAdapter(Context mContext, ArrayList<ComnentEntity.CommentsEntity> longCommentList) {
        this.mContext = mContext;
        this.longCommentList = longCommentList;
    }

    public void setLongCommentList(ArrayList<ComnentEntity.CommentsEntity> longCommentList) {
        this.longCommentList = longCommentList;
        notifyDataSetChanged();
    }


    public void setOnClickListner(LongCommentAdapter.onClickListner onClickListner) {
        this.onClickListner = onClickListner;
    }

    public interface onClickListner {
        void onItemClick(View v, int postion);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Glide.with(mContext).load(longCommentList.get(position).getAvatar()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.mCommentImg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.mCommentImg.setImageDrawable(circularBitmapDrawable);
            }
        });
        holder.mCommentName.setText(longCommentList.get(position).getAuthor());
        holder.mCommentDate.setText(Util.timestampToString(longCommentList.get(position).getTime()));
        holder.mCommentPrise.setText("点赞数 : " + longCommentList.get(position).getLikes());
        holder.mCommentContent.setText(longCommentList.get(position).getContent());
        if (longCommentList.get(position).getReply_to() != null && !longCommentList.get(position).getReply_to().equals("")) {
            holder.mContentReply.setVisibility(View.VISIBLE);
            if (longCommentList.get(position).getReply_to().getStatus() == 0) {

                holder.mContentReply.setText(Html.fromHtml("<b><font color=\"black\">" + "//" +
                        longCommentList.get(position).getReply_to().getAuthor() + ":</font></b>") + longCommentList.get(position).getReply_to().getContent());
            } else {
                holder.mContentReply.setBackgroundColor(Color.parseColor("#F7F5F7"));
                holder.mContentReply.setText(longCommentList.get(position).getReply_to().getError_msg());
            }

        } else {
            holder.mContentReply.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return longCommentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.comment_name)
        TextView mCommentName;
        @InjectView(R.id.comment_content)
        TextView mCommentContent;
        @InjectView(R.id.comment_reply_content)
        TextView mContentReply;
        @InjectView(R.id.comment_date)
        TextView mCommentDate;
        @InjectView(R.id.comment_img)
        ImageView mCommentImg;
        @InjectView(R.id.comment_prise)
        TextView mCommentPrise;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
