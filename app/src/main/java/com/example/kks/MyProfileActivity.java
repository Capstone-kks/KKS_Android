package com.example.kks;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.archive.ActForFragmentArchiveFolderActivity;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.controller.SharedPreference;
import com.example.kks.databinding.ActivityMyProfileBinding;
import com.example.kks.login.PostUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyProfileActivity extends AppCompatActivity {

    private ActivityMyProfileBinding binding;
    //NameViewModel NameViewModel;

    private File tempFile;

    String userId, nickname, newName, prefId;
    String userImg = null;

    private static final int SINGLE_PERMISSION = 1004; //권한 변수
    private static final int REQUEST_CODE = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    static final int PERMISSIONS_REQUEST_CODE = 1000;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    static private Context mcontext;
    public static Activity act;

    private ImageButton backButton;

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        //userId = intent.getStringExtra("user_id");
        //nickname = intent.getStringExtra("nickname");
        //userImg = intent.getStringExtra("userImage");
        //System.out.println("image url : " + userImg);

        mcontext = this;
        act = this;

        prefId = SharedPreference.getString(mcontext, "userId");
        Log.d("저장된", prefId);

        //서버에서 닉네임, 이미지 가져오기
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<PostUser> call = retrofitAPI.getUser(prefId);
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                if(response.isSuccessful()){
                    PostUser data = response.body();
                    nickname = data.getNickName();
                    userImg = data.getUserImg();
                    Log.d("이름", nickname);
                    Log.d("이미지", userImg);
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.d("실패", prefId);
                t.printStackTrace();
            }
        });

        //dummy data
        //nickname = "굥";
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        new Thread(new Runnable() {
            boolean isRun = false;
            int value = 0;

            @Override
            public void run() {
                isRun = true;
                while ((isRun)) {
                    value += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.editName.setText(nickname);
                            //get image using Glid lib
                            Activity activity = (Activity) mcontext;
                            if (activity.isFinishing())
                                return;
                            Glide.with(mcontext).load(userImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(binding.profileImg);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start(); //start()붙이면 바로실행시킨다.

        //binding.editName.setText(nickname);

        //get image using Glid lib
        //Glide.with(this).load(userImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(binding.profileImg);


    }

    private void CameraPermissionCheck() {

        String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MyProfileActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                //Toast.makeText(MyProfileActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);
                builder.setTitle("알림");
                builder.setMessage("권한을 허용하시겠습니까?");
                builder.setCancelable(false);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
                builder.create().show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("사진을 가져오기 위해선 카메라 접근 권한이 필요해요")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있어요.")
                //.setPermissions(Manifest.permission.READ_CONTACTS)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void AlbumPermissionCheck() {

        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS};

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MyProfileActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                //Toast.makeText(MyProfileActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);
                builder.setTitle("알림");
                builder.setMessage("권한을 허용하시겠습니까?");
                builder.setCancelable(false);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
                builder.create().show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("사진을 가져오기 위해선 앨범 접근 권한이 필요해요")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();

    }


    public void changeName(View view) {
        //NameViewModel = new NameViewModel();

        //Toast.makeText(getApplicationContext(), "newname : "+newName, Toast.LENGTH_LONG).show();
        String editName = binding.editName.getText().toString();
        nickname = editName;
        Toast.makeText(getApplicationContext(), "newname : " + nickname, Toast.LENGTH_LONG).show();

        //서버에도 닉네임 변경하여 저장하기


    }

    public void kakaoPhoto(View view){

        Toast.makeText(getApplicationContext(), "카카오 프로필 이미지로 세팅합니다.", Toast.LENGTH_LONG).show();

        String prefImg = SharedPreference.getString(mcontext, "userImg");
        Log.d("저장된", prefImg);

        Glide.with(this).load(prefImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(binding.profileImg);


        //디비에 저장

    }

    public void changePhoto(View view) {
        Context context;
        AlertDialog.Builder dlg = new AlertDialog.Builder(MyProfileActivity.this);
        dlg.setTitle("사진 가져오기");
        dlg.setIcon(R.drawable.calendarimage);

        dlg.setPositiveButton("카메라",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CameraPermissionCheck();

                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                        }
                        else {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
                        }
                    }
                });

        dlg.setNegativeButton("앨범",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlbumPermissionCheck();

                        Intent albumintent = new Intent(Intent.ACTION_PICK);
                        albumintent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        startActivityForResult(albumintent, PICK_FROM_ALBUM);
                    }
                });

        dlg.show();
    }

    private void setAlbumImage() {
        ImageView imageView = findViewById(R.id.profileImg);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);


        //set on the ui
        //imageView.setImageBitmap(originalBm);
        Glide.with(this).load(originalBm).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(imageView);

        //set on the db


    }

    private void setCameraImage(Bitmap originalBm){
        ImageView imageView = findViewById(R.id.profileImg);

        //set on the ui
        //imageView.setImageBitmap(originalBm);
        Glide.with(this).load(originalBm).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(imageView);

        //set on the db



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }
            return;
        }


        if (requestCode == PICK_FROM_ALBUM) {
            Uri photoUri = data.getData();
            Cursor cursor = null;

            try {
                //Uri 스키마를 content:/// 에서 file:/// 로  변경
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            setAlbumImage();

        } else if (requestCode == PICK_FROM_CAMERA) {
            if(resultCode == RESULT_OK && data.hasExtra("data"))    // OK 만이 아니라 카메라가 찍은 사진 데이터가 있는지도!
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                setCameraImage(photo);
            }

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void finish(View view){
        Intent intent = new Intent(MyProfileActivity.this, ActForFragmentArchiveFolderActivity.class);
        startActivity(intent);
        //Fragment fragment = new ArchiveFolderFragment();
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, fragment).commit();

    }
}
