package come.zhu.com.hushiguang.zhudome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import come.zhu.com.hushiguang.zhudome.DB.Themes;
import come.zhu.com.hushiguang.zhudome.MainActivity;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.entity.ThemesEntity;

/**
 * Created by office on 2015/12/5.
 */
public class LeftRecycleAdapter extends RecyclerView.Adapter<LeftRecycleAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ThemesEntity.OthersEntity> themesList;
    private List<Themes> userthemesList;
    onClickListner onClickListner;
    MainActivity mainActivity;

    public LeftRecycleAdapter(MainActivity mainActivity, Context mContext, ArrayList<ThemesEntity.OthersEntity> themesList, List<Themes> userthemesList) {
        this.mainActivity = mainActivity;
        this.mContext = mContext;
        this.themesList = themesList;
    }


    public void setUserthemesList(List<Themes> userthemesList) {
        this.userthemesList = userthemesList;
        notifyDataSetChanged();
    }

    public void setThemesList(ArrayList<ThemesEntity.OthersEntity> themesList) {
        this.themesList = themesList;
        notifyDataSetChanged();
    }

    public void setOnClickListner(LeftRecycleAdapter.onClickListner onClickListner) {
        this.onClickListner = onClickListner;
    }

    public interface onClickListner {

        void onItemClick(View v, int postion);

        void onImageViewClick(View v, int postion);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_left_recycle, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (userthemesList != null && userthemesList.size() != 0) {
            for (int i = 0; i < themesList.size(); i++) {
                for (int j = 0; j < userthemesList.size(); j++) {
                    if (userthemesList.get(j).getThemesId().equals(themesList.get(i).getId() + "")) {
                        ThemesEntity.OthersEntity entity = themesList.get(i);
                        themesList.remove(i);
                        entity.setType(1);
                        themesList.add(0, entity);
                    }
                }
            }
        }


        holder.mName.setText(themesList.get(position).getName());
        if (themesList.get(position).getType() == 1){
            holder.mImageView.setImageResource(R.mipmap.ic_keyboard_arrow_right_black_24dp);
        }else {
            holder.mImageView.setImageResource(R.mipmap.ic_add_black_24dp);
        }


        if (onClickListner != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListner.onItemClick(v, holder.getAdapterPosition());
                }
            });

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListner.onImageViewClick(v, holder.getAdapterPosition());
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return themesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.left_name)
        TextView mName;
        @InjectView(R.id.left_image)
        ImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
