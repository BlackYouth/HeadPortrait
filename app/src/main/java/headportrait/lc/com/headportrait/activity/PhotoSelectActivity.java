package headportrait.lc.com.headportrait.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import headportrait.lc.com.headportrait.R;
import headportrait.lc.com.headportrait.adapter.PhotoSelectAdapter;
import headportrait.lc.com.headportrait.controller.AlbumController;
import headportrait.lc.com.headportrait.model.PhotoModel;

/**
 * Created by Administrator on 2016/10/13.
 */
public class PhotoSelectActivity extends Activity {

    private RecyclerView recyclerView;
    private GridLayoutManager GridLayoutManager;
    private  List<PhotoModel> datalist;
    //列数
    private int spanCount = 3 ;
    private int itemWidth;
    private int intervalSize = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_select_layout);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        datalist = new AlbumController(this).getAlbum(name);

        int width = this.getWindowManager().getDefaultDisplay().getWidth();
        itemWidth = width/spanCount ;//- intervalSize * (spanCount + 1) * 2;
        initview();
    }

    private void initview() {
        recyclerView  = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager = new GridLayoutManager(this,spanCount, android.support.v7.widget.GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(GridLayoutManager);
        recyclerView.addItemDecoration(new SpceItemDecoration(intervalSize));
        LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT );
        LayoutParams.setMargins(intervalSize,intervalSize,intervalSize,intervalSize);
        recyclerView.setLayoutParams(LayoutParams);
        PhotoSelectAdapter adapter = new PhotoSelectAdapter(this,datalist,itemWidth);
        recyclerView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new PhotoSelectAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String data) {
                Intent intent = new Intent(PhotoSelectActivity.this,PhotoTailorActivity.class);
                intent.putExtra("uri",data);
                startActivity(intent);
            }
        });
    }

    public class SpceItemDecoration extends RecyclerView.ItemDecoration{
        private int space;
        public SpceItemDecoration(int space) {
            super();
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = space;
                outRect.right = space;
            outRect.top = space;
            outRect.bottom = space;
        }
    }


}
