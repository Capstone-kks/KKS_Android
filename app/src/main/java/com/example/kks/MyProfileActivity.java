package com.example.kks;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.databinding.ActivityMyProfileBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyProfileActivity extends AppCompatActivity {

    private ActivityMyProfileBinding binding;
    NameViewModel NameViewModel;

    private File tempFile;

    String userId, nickname, newName;
    String userImg = "no";

    private static final int SINGLE_PERMISSION = 1004; //권한 변수
    private static final int REQUEST_CODE = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    static final int PERMISSIONS_REQUEST_CODE = 1000;

    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        nickname = intent.getStringExtra("nickname");
        userImg = intent.getStringExtra("userImage");
        //System.out.println("image url : " + userImg);

        //dummy data
        //nickname = "굥";
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.editName.setText(nickname);

        //get image using Glid lib
        Glide.with(this).load(userImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(binding.profileImg);


        //get image using url connection... but failed. don't know why
        /*
        if (!userImg.equals("no")) {
            {
                try {
                    URL url = new URL(userImg);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    //BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap originalBm = BitmapFactory.decodeStream(conn.getInputStream());
                    binding.profileImg.setImageBitmap(originalBm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        */
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


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {
                            tempFile = createImageFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            Uri photoUri = Uri.fromFile(tempFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        }
                    }
                });

        dlg.setNegativeButton("앨범",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlbumPermissionCheck();

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        startActivityForResult(intent, PICK_FROM_ALBUM);
                    }
                });

        dlg.show();
    }

    private void setImage() {
        ImageView imageView = findViewById(R.id.profileImg);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);


        //set on the ui
        imageView.setImageBitmap(originalBm);

        //set on the db


    }

    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
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
                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
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
            setImage();
        } else if (requestCode == PICK_FROM_CAMERA) {
            //Bundle extras = data.getExtras();
            setImage();

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            return;
        }

    }



}
