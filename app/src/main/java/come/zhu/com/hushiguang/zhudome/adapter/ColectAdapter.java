package come.zhu.com.hushiguang.zhudome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import come.zhu.com.hushiguang.zhudome.DB.Colect;
import come.zhu.com.hushiguang.zhudome.R;

/**
 * Created by office on 2015/12/9.
 */
public class ColectAdapter extends RecyclerView.Adapter<ColectAdapter.MyViewHolder> {


    private Context mContext;
    private List<Colect> colectList;
    onClickListner onClickListner;

    public ColectAdapter(Context mContext, List<Colect> colectList) {
        this.mContext = mContext;
        this.colectList = colectList;
    }


    public void setColectList(List<Colect> colectList) {
        this.colectList = colectList;
    }

    public void setOnClickListner(ColectAdapter.onClickListner onClickListner) {
        this.onClickListner = onClickListner;
    }

    public interface onClickListner {
        void onItemClick(View v, int postion);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mTextView.setText(colectList.get(position).getTitle().trim());
        if (colectList.get(position).getImage() == null || colectList.get(position).getImage().equals("")){
            holder.mImageView.setVisibility(View.GONE);
        }else
             Glide.with(mContext).load(colectList.get(position).getImage()).into(holder.mImageView);

        if (onClickListner != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListner.onItemClick(v, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return colectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.adapter_dsc)
        TextView mTextView;
        @InjectView(R.id.adapter_imageview)
        ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
