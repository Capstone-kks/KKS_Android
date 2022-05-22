package com.example.kks.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.MainActivity;
import com.example.kks.R;
import com.example.kks.controller.SharedPreference;
import com.example.kks.databinding.ActivityLoginPageBinding;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.Profile;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.example.kks.SharedPreferenceManagerKt;
public class LoginPageActivity extends AppCompatActivity {

    private ActivityLoginPageBinding binding;
    static private boolean checking;
    static private Context context;

    String user_id;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView( R.layout.activity_login_page);

        //myDBAdapter = new myDBAdapter(this);

        context = this;

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //자동로그인버튼 체크했는지 확인
        Switch btn = (Switch) findViewById(R.id.maintainlogBtn);
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    checking = true;
                    System.out.println("true");
                } else {
                    //Toast.makeText(getApplicationContext(), "자동로그인 안함", Toast.LENGTH_LONG).show();
                    checking = false;
                }
            }
        });
    }


    public void kakaoLog(View view) {
        login();
    }


    //카카오계정 로그인
    private void login(){
        //myDBAdapter = new myDBAdapter(this);

        UserApiClient.getInstance().loginWithKakaoAccount(this, new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(throwable != null){
                    Log.e("LoginActivity","throwable="+throwable.getMessage());
                }

                if(!TextUtils.isEmpty(oAuthToken.getAccessToken())){
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            long userId = user.getId();
                            //System.out.println(userId);
                            user_id = String.valueOf(userId);
                            Account account = user.getKakaoAccount();
                            if(account != null){
                                Profile userProfile = account.getProfile();
                                if(userProfile != null) {
                                    String nickname = userProfile.getNickname();
                                    String userImage = userProfile.getProfileImageUrl();
                                    //Toast.makeText(getApplicationContext(), "ID : "+user_id, 0).show();

                                    //새로운 회원이면 server에 추가
                                    PostUser postUser = new PostUser();
                                    
                                    postUser.setUserId(user_id);
                                    postUser.setNickName(nickname);
                                    postUser.setUserImg(userImage);

                                    //sharedpreference 초기화
                                    SharedPreference.deleteAll(context, "userId");
                                    SharedPreference.deleteAll(context, "userImg");

                                    //sharedpreference 저장
                                    SharedPreference.saveString(context, "userId", user_id);
                                    SharedPreference.saveString(context, "userImg", userImage);
                                    //String prefId = SharedPreference.getString(context, "userId");
                                    //String prefImg = SharedPreference.getString(context, "userImg");
                                    //Log.d("저장된", prefId);
                                    //Log.d("저장된", prefImg);

                                    System.out.println("POST USER"+postUser.getUserId());

                                    // eunkyung
                                    SharedPreferenceManagerKt.saveNickname(context,nickname);
                                    SharedPreferenceManagerKt.saveUserIdx(context,user_id);



                                    //retrofit
                                    RetrofitClient client = new RetrofitClient();
                                    Retrofit retrofit = client.setRetrofit();
                                    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

                                    retrofitAPI.postData(postUser).enqueue(new Callback<PostUser>() {
                                        @Override
                                        public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                                            if(response.isSuccessful()){
                                                PostUser data = response.body();
                                                Log.d("내정보", user_id);
                                                Log.d("sever 보내기 성공", data.getNickName());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<PostUser> call, Throwable t) {
                                            Log.d("실패", user_id);
                                            t.printStackTrace();
                                        }
                                    });
                                    
                                    //db에 없는 회원이면 회원 table에 추가
                                    //있는 회원이라면 닉네임, 프로필 사진 값 가져와서 넘기기

                                    //다음 페이지로 넘기기
                                    //Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("user_id", user_id);
                                    //intent.putExtra("nickname", nickname);
                                    //intent.putExtra("userImage", userImage);
                                    intent.putExtra("checking", checking);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            return null;
                        }
                    });
                }
                Log.e("LoginActivity","login token = "+oAuthToken);

                return null;
            }
        });
    }
}

