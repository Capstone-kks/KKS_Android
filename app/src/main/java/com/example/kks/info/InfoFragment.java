package com.example.kks.info;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.R;
import com.example.kks.SharedPreferenceManagerKt;
import com.example.kks.controller.Name;
import com.example.kks.controller.ProfImg;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.controller.SharedPreference;
import com.example.kks.databinding.FragmentInfoBinding;
import com.example.kks.info.alarm.AlarmActivity;
import com.example.kks.info.liked.LikedActivity;
import com.example.kks.info.myrecord.MyRecordActivity;
import com.example.kks.info.pattern.SpendpatternActivity;
import com.example.kks.login.LoginPageActivity;
import com.example.kks.login.PostUser;
import com.example.kks.login.myDBAdapter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    private Button niknamechange_btn;
    private ImageButton profile_imb, kakao_img_btn;

    private File tempFile;

    String userId, nickname, newName;
    String userImg = null;

    private static final int SINGLE_PERMISSION = 1004; //권한 변수
    private static final int REQUEST_CODE = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    static final int PERMISSIONS_REQUEST_CODE = 1000;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    static private Context context;
    private Activity activity;

    private MultipartBody.Part multibody = null;

    //예슬
    private ConstraintLayout myrecord_btn, liked_btn, analysis_btn, alarm_btn, withdrawal_btn, logout_btn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        context = container.getContext();
        activity = getActivity();

        prefId = SharedPreference.getString(root.getContext(), "userId");

        profile_imv = root.findViewById(R.id.imgbtn_profilepicture);
        editName_edt = root.findViewById(R.id.edt_nikname);
        niknamechange_btn = root.findViewById(R.id.btn_niknamechange);
        kakao_img_btn = root.findViewById(R.id.photokakaobtn);
        profile_imb = root.findViewById(R.id.photochangebtn);

        myrecord_btn = root.findViewById(R.id.btn_myrecord);
        analysis_btn = root.findViewById(R.id.btn_analysis);
        liked_btn = root.findViewById(R.id.btn_likedlist);
        alarm_btn = root.findViewById(R.id.btn_alarm);
        withdrawal_btn = root.findViewById(R.id.btn_withdrawal);
        logout_btn = root.findViewById(R.id.btn_logout);

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
                    if (userImg != null)
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

                //서버에도 닉네임 변경하여 저장하기
                RetrofitClient client = new RetrofitClient();
                Retrofit retrofit = client.setRetrofit();
                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

                Name name = new Name();
                name.setNickName(nickname);

                Call<Name> call = retrofitAPI.editName(prefId, name);
                call.enqueue(new Callback<Name>() {
                    @Override
                    public void onResponse(Call<Name> call, Response<Name> response) {
                        if (response.isSuccessful()) {
                            //Toast.makeText(root.getContext(), "'" + nickname + "' 으로 변경되었습니다.", Toast.LENGTH_LONG).show();
                        }

                        Name nameResponse = response.body();
                        Log.d("닉네임 변경 성공", nickname);
                        //Toast.makeText(root.getContext(), "'" + nameResponse.getNickName() + "' 으로 변경되었습니다.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Name> call, Throwable t) {
                        //Log.d("닉네임 변경 실패", nickname);
                        t.printStackTrace();
                    }
                });

                //은경님 sharedpref에 닉네임 저장
                SharedPreferenceManagerKt.saveNickname(context,nickname);
                Toast.makeText(root.getContext(), "'" + nickname + "' 으로 변경되었습니다.", Toast.LENGTH_LONG).show();
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

                //db api 적용
                RetrofitClient client = new RetrofitClient();
                Retrofit retrofit = client.setRetrofit();
                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

                ProfImg pimg = new ProfImg();
                pimg.setUserImg(prefImg);

                Call<ProfImg> call = retrofitAPI.editImage(prefId, pimg);
                call.enqueue(new Callback<ProfImg>() {
                    @Override
                    public void onResponse(Call<ProfImg> call, Response<ProfImg> response) {
                        if (response.isSuccessful()) {
                            //Toast.makeText(root.getContext(), "'" + nickname + "' 으로 변경되었습니다.", Toast.LENGTH_LONG).show();
                        }

                        ProfImg imgResponse = response.body();
                        Log.d("이미지 변경 성공", imgResponse.getUserImg());
                    }

                    @Override
                    public void onFailure(Call<ProfImg> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
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

        //유경 - 사용자 문화 패턴 분석 버튼 클릭 시
        analysis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), SpendpatternActivity.class);
                startActivity(intent);
            }
        });



        /**
         * 예슬 - 내 정보, 공감글 모아보기, 알림 설정, 회원 탈퇴
         * */
        //내 정보 버튼 클릭 시 페이지 이동
        myrecord_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), MyRecordActivity.class);
                intent.putExtra("userId",prefId);
                startActivity(intent);
            }
        });

        //공감글 모아보기 버튼 클릭 시 페이지 이동
        liked_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), LikedActivity.class);
                startActivity(intent);
            }
        });

        //알람 설정 버튼 클릭 시 페이지 이동
        alarm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        //회원탈퇴 버튼 클릭 시 로그인 화면으로 돌아감
        withdrawal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(root.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_delete);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                TextView title = dialog.findViewById(R.id.dialog_title_tv);
                TextView subtitle = dialog.findViewById(R.id.dialog_content_tv);
                title.setText("탈퇴하시겠습니까?");
                subtitle.setText("회원 탈퇴는 돌이킬 수 없습니다.");
                dialog.findViewById(R.id.dialog_approve_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retrofitAPI.WithdrawalUser(userId).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                SharedPreferenceManagerKt.removeSpfAll(context);
                                SharedPreferences preferences  = root.getContext().getSharedPreferences("AlarmInfo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("AlarmStatus",false);
                                editor.commit();
                                Intent intent = new Intent(getContext(), LoginPageActivity.class);
                                startActivity(intent);
                                activity.finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                });
                dialog.findViewById(R.id.dialog_cancel_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        //유경
        //로그아웃 버튼 클릭시
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(root.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_delete);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                TextView title = dialog.findViewById(R.id.dialog_title_tv);
                TextView subtitle = dialog.findViewById(R.id.dialog_content_tv);

                title.setText("로그아웃");
                subtitle.setText("로그아웃 하시겠습니까?");

                dialog.findViewById(R.id.dialog_approve_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDBAdapter adapter = new myDBAdapter(root.getContext());
                        //adapter.clear();
                        adapter.open();
                        adapter.delete("userId");
                        adapter.close();
                        SharedPreferenceManagerKt.removeSpfAll(context);
                        Intent intent = new Intent(root.getContext(), LoginPageActivity.class);
                        startActivity(intent);
                        activity.finish();
                    }
                });
                dialog.findViewById(R.id.dialog_cancel_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
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
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
        // File file = new File(filepath);
        // InputStream inputStream = null;
        // try {
        // inputStream = getContext().getContentResolver().openInputStream(photoUri);
        // }catch(IOException e) {
        // e.printStackTrace();
        // }
        // Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        originalBm.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), byteArrayOutputStream.toByteArray());
        multibody = MultipartBody.Part.createFormData("images","image.jpeg",requestBody);

        Log.d("멀티", multibody.toString());

        //db api 적용
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        ProfImg pimg = new ProfImg();
        //pimg.setUserImg(prefImg);

        Call<ProfImg> call = retrofitAPI.editImagefile(prefId, multibody, pimg);
        call.enqueue(new Callback<ProfImg>() {
            @Override
            public void onResponse(Call<ProfImg> call, Response<ProfImg> response) {
                if (response.isSuccessful()) {
                    Log.d("이미지 변경 성공", multibody.toString());
                    //Toast.makeText(root.getContext(), "'" + nickname + "' 으로 변경되었습니다.", Toast.LENGTH_LONG).show();
                }
                //ProfImg imgResponse = response.body();
            }

            @Override
            public void onFailure(Call<ProfImg> call, Throwable t) {
                Log.d("이미지 변경 실패", multibody.toString());
                t.printStackTrace();
            }
        });
        Toast.makeText(root.getContext(), "프로필 사진이 변경되었습니다.", Toast.LENGTH_LONG).show();
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
                //Toast.makeText(root.getContext(), "권한 허가", Toast.LENGTH_SHORT).show();
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

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        originalBm.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), byteArrayOutputStream.toByteArray());
        multibody = MultipartBody.Part.createFormData("images","image.jpeg",requestBody);

        //set on the db
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        ProfImg pimg = new ProfImg();
        //pimg.setUserImg(prefImg);

        Call<ProfImg> call = retrofitAPI.editImagefile(prefId, multibody, pimg);
        call.enqueue(new Callback<ProfImg>() {
            @Override
            public void onResponse(Call<ProfImg> call, Response<ProfImg> response) {
                if (response.isSuccessful()) {
                    Log.d("이미지 변경 성공", multibody.toString());
                    //Toast.makeText(root.getContext(), "'" + nickname + "' 으로 변경되었습니다.", Toast.LENGTH_LONG).show();
                }
                //ProfImg imgResponse = response.body();
            }

            @Override
            public void onFailure(Call<ProfImg> call, Throwable t) {
                Log.d("이미지 변경 실패", multibody.toString());
                t.printStackTrace();
            }
        });
        Toast.makeText(root.getContext(), "프로필 사진이 변경되었습니다.", Toast.LENGTH_LONG).show();
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
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
