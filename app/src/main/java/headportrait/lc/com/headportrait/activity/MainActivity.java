package headportrait.lc.com.headportrait.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import headportrait.lc.com.headportrait.R;
import headportrait.lc.com.headportrait.view.CircleImageView;

public class MainActivity extends Activity {
	
	private static final int REQUESTCODE_PIC = 0;
	private static final int REQUESTCODE_CAM = 1;
	private static final int REQUESTCODE_CUT = 2;
	private CircleImageView imageView1;
	private File mFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageView1 = (CircleImageView) findViewById(R.id.imageView1);
		imageView1.setOutCircleWidth(5);
		imageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.menu_layout, null);
				Button Bt_Album = (Button) view.findViewById(R.id.Btt_album);
				Button Bt_Photo = (Button) view.findViewById(R.id.Btt_photo);
				Button Bt_Cancel = (Button) view.findViewById(R.id.Btt_cancel);
				final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						false);
				popupWindow.setFocusable(true);
				popupWindow.setOutsideTouchable(true);
				popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000)); 
				backgroundAlpha(0.7f);
				popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
				popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
				popupWindow.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						backgroundAlpha(1.0f);
					}
				});
				
				Bt_Album.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this, PhotoAbumActivity.class);
						startActivity(intent);
//						openPic();
//						popupWindow.dismiss();
					}
				});
				Bt_Photo.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						openCamera();
						popupWindow.dismiss();
					}
				});
				Bt_Cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
					}
				});
			}
		});
	}
	
	 /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }
    
    /**
     * 打开相册
     */
    public void openPic(){
//    	Intent picIntent = new Intent(Intent.ACTION_GET_CONTENT);
//    	picIntent.setType("image/*"); 
    	Intent picIntent = new Intent(Intent.ACTION_PICK,null);
    	picIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
    	startActivityForResult(picIntent, REQUESTCODE_PIC);
    }
    
    /**
     * 调用相机
     */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!file.exists()){
                file.mkdirs();
            }
            mFile = new File(file, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
            startActivityForResult(intent,REQUESTCODE_CAM);
        } else {
            Toast.makeText(this, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 打开系统图片裁剪功能
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop",true);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        intent.putExtra("scale",true); //黑边
        intent.putExtra("scaleUpIfNeeded",true); //黑边
        intent.putExtra("return-data",true);
        intent.putExtra("noFaceDetection",true);
        startActivityForResult(intent,REQUESTCODE_CUT);

    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(resultCode ==  RESULT_OK){
    		 switch (requestCode) {
			case REQUESTCODE_PIC:
				if(data == null || data.getData() == null){
					return;
				}
				startPhotoZoom(data.getData());
				break;
			case REQUESTCODE_CAM:
				startPhotoZoom(Uri.fromFile(mFile));
				break;
			case REQUESTCODE_CUT:
				if (data!= null){
                    setPicToView(data);
                }
				break;
			}
    	}
    }
    
    private void setPicToView(Intent data) {
        Bundle bundle =  data.getExtras();
         if (bundle != null){
             //这里也可以做文件上传
             Bitmap mBitmap = bundle.getParcelable("data");
             imageView1.setImageBitmap(mBitmap);
         }
     }

}
