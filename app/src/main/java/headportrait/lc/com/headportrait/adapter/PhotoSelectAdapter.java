package headportrait.lc.com.headportrait.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import headportrait.lc.com.headportrait.R;
import headportrait.lc.com.headportrait.model.PhotoModel;

/**
 * Created by Administrator on 2016/10/13.
 */
public class PhotoSelectAdapter extends RecyclerView.Adapter<PhotoSelectAdapter.MViewHolder> implements View.OnClickListener {
    private List<PhotoModel> dataList;
    private Context context;
    private int itemWidth;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public PhotoSelectAdapter(Context context, List<PhotoModel> dataList, int itemWidth) {
        super();
        this.context = context;
        this.dataList = dataList;
        this.itemWidth = itemWidth;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viwe = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_select_item_layout,null);
        MViewHolder viewHolder = new MViewHolder(viwe);
        viewHolder.imageView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getOriginalPath()).into(holder.imageView);
        holder.imageView.setTag(dataList.get(position).getOriginalPath());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view,dataList.indexOf((String)view.getTag()),(String)view.getTag());
        }
    }

    class MViewHolder extends RecyclerView.ViewHolder {

        public   ImageView imageView;
        public MViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.getLayoutParams().width = itemWidth;
            imageView.getLayoutParams().height = itemWidth;
        }
    }

    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view,int position,String data);
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}
