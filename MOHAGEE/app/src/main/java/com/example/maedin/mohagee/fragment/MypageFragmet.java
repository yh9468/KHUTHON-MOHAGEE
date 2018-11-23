package com.example.maedin.mohagee.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maedin.mohagee.Layout.CircleImageView;
import com.example.maedin.mohagee.R;
import com.example.maedin.mohagee.activity.MainActivity;
import com.example.maedin.mohagee.activity.Select_Location_Activity;
import com.example.maedin.mohagee.activity.check_favorite_Activity;
import com.example.maedin.mohagee.application.App;
import com.example.maedin.mohagee.item.User;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


public class MypageFragmet extends Fragment  implements View.OnClickListener {

    View view;
    User curuser;
    private Uri photoURI, albumURI, imgUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    ImageView ivImage;
    ImageButton camerabutton, favoritebutton;
    TextView username, userid;
    private String mCurrentPhotoPath;
    private Spinner spinner_money_min, spinner_money_max;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mypage, container, false);
        curuser = ((App)(((MainActivity)getActivity()).getApplication())).getUser();

        camerabutton = view.findViewById(R.id.camera_button);
        camerabutton.setBackground(new ShapeDrawable(new OvalShape()));
        camerabutton.setClipToOutline(true);
        camerabutton.setImageResource(R.drawable.camera);
        camerabutton.setOnClickListener(this);

        favoritebutton = view.findViewById(R.id.My_Favorite_Button);
        favoritebutton.setBackground(new ShapeDrawable(new OvalShape()));
        favoritebutton.setClipToOutline(true);
        favoritebutton.setImageResource(R.drawable.pen);
        favoritebutton.setOnClickListener(this);

        username = view.findViewById(R.id.user_name);
        userid = view.findViewById(R.id.user_id);

        username.setText(curuser.getName());
        username.setTextSize(13);
        userid.setText(curuser.getId());
        userid.setTextSize(13);



        ImageView ivImage = view.findViewById(R.id.profile_image);
        ivImage.setBackground(new ShapeDrawable(new OvalShape()));
        ivImage.setClipToOutline(true);
        ivImage.setImageResource(R.drawable.mohagee_logo_);
        return view;

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.camera_button:
                if (ContextCompat.checkSelfPermission((MainActivity)getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale((MainActivity)getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    } else {

                        ActivityCompat.requestPermissions((MainActivity)getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }

                }

                if (ContextCompat.checkSelfPermission((MainActivity)getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale((MainActivity)getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    } else {

                        ActivityCompat.requestPermissions((MainActivity)getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    }

                }

                if (ContextCompat.checkSelfPermission((MainActivity)getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale((MainActivity)getActivity(),Manifest.permission.CAMERA)) {

                    } else {

                        ActivityCompat.requestPermissions((MainActivity)getActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                1);
                    }

                }

                makeDialog();
                break;

            case R.id.My_Favorite_Button:
                startActivityForResult(new Intent(getActivity(), check_favorite_Activity.class), 0);

                break;
        }

    }
    private void makeDialog(){

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(((MainActivity) getActivity()));

        alt_bld.setTitle("사진 업로드").setIcon(R.drawable.camera).setCancelable(

                false).setPositiveButton("사진촬영",

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Log.v("알림", "다이얼로그 > 사진촬영 선택");

                        // 사진 촬영 클릭

                        takePhoto();

                    }

                }).setNeutralButton("취소",

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int id) {

                        Log.v("알림", "다이얼로그 > 앨범선택 선택");

                        //앨범에서 선택
                        dialogInterface.cancel();


                    }

                }).setNegativeButton("앨범선택",

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Log.v("알림", "다이얼로그 > 취소 선택");

                        // 취소 클릭. dialog 닫기.

                        selectAlbum();

                    }

                });

        AlertDialog alert = alt_bld.create();

        alert.show();

    }

    //사진 찍기 클릭

    public void takePhoto(){

        // 촬영 후 이미지 가져옴

        String state = Environment.getExternalStorageState();

        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if(Environment.MEDIA_MOUNTED.equals(state)){

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(intent.resolveActivity(((MainActivity)getActivity()).getPackageManager())!=null){

                File photoFile = null;

                try{

                    photoFile = createImageFile();

                }catch (IOException e){

                    e.printStackTrace();

                }

                if(photoFile!=null){

                    Uri providerURI = FileProvider.getUriForFile(((MainActivity)getActivity()),((MainActivity)getActivity()).getPackageName(),photoFile);

                    imgUri = providerURI;

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);

                    startActivityForResult(intent, FROM_CAMERA);

                }

            }

        }else{

            Log.v("알림", "저장공간에 접근 불가능");

            return;

        }


    }




    public File createImageFile() throws IOException{

        String imgFileName = System.currentTimeMillis() + ".jpg";

        File imageFile= null;

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");



        if(!storageDir.exists()){

            Log.v("알림","storageDir 존재 x " + storageDir.toString());

            storageDir.mkdirs();

        }

        Log.v("알림","storageDir 존재함 " + storageDir.toString());

        imageFile = new File(storageDir,imgFileName);

        mCurrentPhotoPath = imageFile.getAbsolutePath();


        return imageFile;

    }


    //앨범 선택 클릭

    public void selectAlbum(){

        //앨범에서 이미지 가져옴


        //앨범 열기

        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);


        intent.setType("image/*");


        startActivityForResult(intent, FROM_ALBUM);

    }


    public void galleryAddPic(){

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File f = new File(mCurrentPhotoPath);

        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);

        ((MainActivity)getActivity()).sendBroadcast(mediaScanIntent);

        Toast.makeText( ((MainActivity)getActivity()),"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();

    }



    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode !=  ((MainActivity)getActivity()).RESULT_OK){

            return;

        }

        switch (requestCode){

            case FROM_ALBUM : {

                //앨범에서 가져오기

                if(data.getData()!=null){

                    try{

                        File albumFile = null;

                        albumFile = createImageFile();


                        photoURI = data.getData();

                        albumURI = Uri.fromFile(albumFile);


                        galleryAddPic();

                        Bitmap bitmap = getImageBitmap(photoURI.toString());

                        ivImage.setImageURI(photoURI);

                        ivImage.setImageBitmap(bitmap);
                        //cropImage();

                    }catch (Exception e){

                        e.printStackTrace();

                        Log.v("알림","앨범에서 가져오기 에러");

                    }

                }

                break;

            }

            case FROM_CAMERA : {

                //카메라 촬영

                try{

                    Log.v("알림", "FROM_CAMERA 처리");

                    galleryAddPic();

                    Bitmap bitmap = getImageBitmap(imgUri.toString());
                    ivImage.setImageURI(imgUri);
                    ivImage.setImageBitmap(bitmap);

                }catch (Exception e){

                    e.printStackTrace();

                }

                break;

            }

        }

    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }


}
