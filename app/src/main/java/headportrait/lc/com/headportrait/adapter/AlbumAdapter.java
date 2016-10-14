package headportrait.lc.com.headportrait.adapter;

/**
 *
 * @author Aizaz AZ
 *
 */
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import headportrait.lc.com.headportrait.R;
import headportrait.lc.com.headportrait.model.AlbumModel;

public class AlbumAdapter extends BaseAdapter {

	List<AlbumModel> list;
	Context context;

	public AlbumAdapter(List<AlbumModel> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.photo_abum_list_item, null);
			holder.textView = (TextView) convertView.findViewById(R.id.textview);
			holder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(list.get(position).getName()+"("+list.get(position).getCount()+")");
		Glide.with(context).load(list.get(position).getRecent()).into(holder.imageview);
		return convertView;
	}

	class ViewHolder{
		TextView textView;
		ImageView imageview;
	}

}
