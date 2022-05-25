package com.example.kks.info;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.MainActivity;
import com.example.kks.R;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.controller.SharedPreference;
import com.example.kks.databinding.FragmentInfoBinding;
import com.example.kks.info.follow.FollowActivity;
import com.example.kks.info.pattern.SpendpatternActivity;
import com.example.kks.login.LoginPageActivity;
import com.example.kks.login.PostUser;
import com.example.kks.login.myDBAdapter;
import com.example.kks.login.myDBHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoFragment extends Fragment {

    //프래그먼트 화면 설정 binding
    private FragmentInfoBinding binding;
    private View root;

    //SharedPreference 사용자 Id
    String prefId;

    /*
     * 유경 - 프로필 사진 설정
     * */
    private ImageView profile_imv;
    private EditText editName_edt;
    private Button niknamechange_btn, kakao_img_btn;
    private ImageButton profile_imb;

    private File tempFile;

    String userId, nickname, newName;
    String userImg = null;

    private static final int SINGLE_PERMISSION = 1004; //권한 변수
    private static final int REQUEST_CODE = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    static final int PERMISSIONS_REQUEST_CODE = 1000;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    Handler handler = new Handler();

    /*
     * 예슬
     * */
    private ImageButton following_btn, follower_btn;
    private TextView following_txt, follower_txt;
    private Button liked_btn, analysis_btn, withdrawal_btn, logout_btn;

    private LinearLayout layout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        prefId = SharedPreference.getString(root.getContext(), "userId");

        profile_imv = root.findViewById(R.id.imgbtn_profilepicture);
        editName_edt = root.findViewById(R.id.edt_nikname);
        niknamechange_btn = root.findViewById(R.id.btn_niknamechange);
        kakao_img_btn = root.findViewById(R.id.kakao_img_btn);
        profile_imb = root.findViewById(R.id.photochangebtn);

        analysis_btn = root.findViewById(R.id.btn_analysis);

        following_btn = root.findViewById(R.id.imgbtn_following);
        follower_btn = root.findViewById(R.id.imgbtn_follower);
        following_txt = root.findViewById(R.id.tv_followingcount);
        follower_txt = root.findViewById(R.id.tv_followercount);
        liked_btn = root.findViewById(R.id.btn_likedlist);
        withdrawal_btn = root.findViewById(R.id.btn_withdrawal);
        logout_btn = root.findViewById(R.id.btn_logout);
        layout = root.findViewById(R.id.info_fragment);

        //서버에서 닉네임, 이미지 가져오기
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        /*
         * 유경 - 프로필 이미지 닉네임 설정
         * */
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
                    editName_edt.setText(nickname);
                    if (((Activity)root.getContext()).isFinishing())
                        return;
                    Glide.with(root.getContext()).load(userImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(profile_imv);
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.d("실패", prefId);
                t.printStackTrace();
            }
        });

        //닉네임 변경 버튼 클릭시
        niknamechange_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editName = editName_edt.getText().toString();
                nickname = editName;
                Toast.makeText(root.getContext(), "New Name : " + nickname, Toast.LENGTH_LONG).show();

                //서버에도 닉네임 변경하여 저장하기
            }
        });

        //카카오 프로필 이미지 설정 버튼 클릭 시
        kakao_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(root.getContext(), "카카오 프로필 이미지로 세팅합니다.", Toast.LENGTH_LONG).show();

                String prefImg = SharedPreference.getString(root.getContext(), "userImg");
                Log.d("저장된", prefImg);

                Glide.with(root.getContext()).load(prefImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(profile_imv);
            }
        });

        // 프로필 사진이나 아이콘 클릭 시
        profile_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhoto();
            }
        });
        profile_imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhoto();
            }
        });

        //유경 - 스레드 (사용안해도 될 것 같아 주석 처리. 혹시 사용하게 되신다면 주석 푸시고 사용하시면 됩니다.
        /*
        new Thread(new Runnable() {
            boolean isRun = false;
            int value = 0;

            @Override
            public void run() {
                isRun = true;;
                binding = FragmentInfoBinding.inflate(inflater, container, false);
                root = binding.getRoot();
                editName_edt = root.findViewById(R.id.edt_nikname);
                    while ((isRun)) {
                        value += 1;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                editName_edt.setText(nickname);
                                //get image using Glid lib
                                if (((Activity)root.getContext()).isFinishing())
                                    return;
                                Glide.with(root.getContext()).load(userImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(profile_imv);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                    }
                }
            }
        }).start();
        */

        //유경 - 사용자 문화 패턴 분석 버튼 클릭 시
        analysis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), SpendpatternActivity.class);
                startActivity(intent);
            }
        });



        /*
         * 예슬 - 팔로워 팔로잉, 공감글 모아보기, 회원 탈퇴
         * */
        //팔로워 팔로이 숫자 셋팅(미완 - 서버연결)
        int followeing = 3;
        int follower = 3;
        following_txt.setText(Integer.toString(followeing));
        follower_txt.setText(Integer.toString(follower));

        //팔로잉 버튼 클릭 시
        following_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowActivity.follow_num = 0;//사용자를 팔로우 하는 List 띄워야 함
                Intent intent = new Intent(root.getContext(), FollowActivity.class);
                startActivity(intent);
            }
        });

        //팔로워 버튼 클릭 시
        follower_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowActivity.follow_num = 1;//사용자를 팔로우 하는 List 띄워야 함
                Intent intent = new Intent(root.getContext(), FollowActivity.class);
                startActivity(intent);
            }
        });

        //유경
        //로그아웃 버튼 클릭시
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setTitle("로그아웃");
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setCancelable(false);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDBAdapter adapter = new myDBAdapter(root.getContext());
                        //adapter.clear();
                        adapter.open();
                        adapter.delete("userId");
                        adapter.close();

                        Intent intent = new Intent(root.getContext(), LoginPageActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //((Activity)root.getContext()).finish();
                    }
                });
                builder.create().show();
            }
        });

        return root;
    }
    //이미지 변경 클릭 시 실행 함수
    public void changePhoto(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(root.getContext());
        dlg.setTitle("사진 가져오기");
        dlg.setIcon(R.drawable.calendarimage);

        dlg.setPositiveButton("카메라",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CameraPermissionCheck();

                        if (root.getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        //set on the ui
        //imageView.setImageBitmap(originalBm);
        Glide.with(this).load(originalBm).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(profile_imv);

        //set on the db
    }

    private void CameraPermissionCheck() {

        String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(root.getContext(), "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                //Toast.makeText(MyProfileActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
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
                        ((Activity)root.getContext()).finish();
                    }
                });
                builder.create().show();
            }
        };

        TedPermission.with(root.getContext())
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
                Toast.makeText(root.getContext(), "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                //Toast.makeText(MyProfileActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
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
                        ((Activity)root.getContext()).finish();
                    }
                });
                builder.create().show();
            }
        };

        TedPermission.with(root.getContext())
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("사진을 가져오기 위해선 앨범 접근 권한이 필요해요")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();

    }

    private void setCameraImage(Bitmap originalBm){
        //set on the ui
        //imageView.setImageBitmap(originalBm);
        Glide.with(this).load(originalBm).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(profile_imv);

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
                Toast.makeText(root.getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
            }
            else
            {
                Toast.makeText(root.getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != RESULT_OK) {

            Toast.makeText(root.getContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show();

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
                cursor = root.getContext().getContentResolver().query(photoUri, proj, null, null, null);

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
            Toast.makeText(root.getContext(), "사진 선택 취소", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}