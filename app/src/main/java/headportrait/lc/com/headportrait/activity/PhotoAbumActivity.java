package headportrait.lc.com.headportrait.activity;

import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import headportrait.lc.com.headportrait.R;
import headportrait.lc.com.headportrait.adapter.AlbumAdapter;
import headportrait.lc.com.headportrait.controller.AlbumController;
import headportrait.lc.com.headportrait.model.AlbumModel;

public class PhotoAbumActivity extends Activity{

	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_abum);
		
		listView = (ListView) findViewById(R.id.listview);
		AlbumController controller = new AlbumController(getApplicationContext());
		final List<AlbumModel> list = controller.getAlbums();
		final AlbumAdapter adapter = new AlbumAdapter(list, getApplicationContext());
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(PhotoAbumActivity.this,PhotoSelectActivity.class);
				intent.putExtra("name",list.get(i).getName());
				startActivity(intent);
			}
		});
	}
}
