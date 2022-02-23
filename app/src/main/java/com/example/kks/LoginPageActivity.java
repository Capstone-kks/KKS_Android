package com.example.kks;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kks.databinding.ActivityLoginPageBinding;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.Profile;
import com.kakao.sdk.user.model.User;

import java.net.MalformedURLException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginPageActivity extends AppCompatActivity {

    private ActivityLoginPageBinding binding;

    String user_id, nickname, userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_login_page);
    }


    public void autoLog(View view) {
        login();
    }


    //카카오계정 로그인
    private void login(){
        //NetworkUtil.setNetworkPolicy();

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
                                    //String images[] = userImage.split("/");

                                    Toast.makeText(getApplicationContext(), "ID : "+user_id, 0).show();

                                    //다음 페이지로 넘기기
                                    /*
                                    Intent intent = new Intent(getApplicationContext(), "홈화면".class);
                                    intent.putExtra("user_id", user_id);
                                    intent.putExtra("nickname", nickname);
                                    intent.putExtra("userImage", userImage);
                                    startActivity(intent);
                                    finish();
                                     */
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

