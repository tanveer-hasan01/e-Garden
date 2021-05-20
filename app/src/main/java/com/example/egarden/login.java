package com.example.egarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.egarden.databinding.ActivityLoginBinding;
import com.example.egarden.databinding.ActivityMainBinding;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class login extends AppCompatActivity {

    ActivityLoginBinding binding;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 101;

    SharedPreference sharedPreference;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreference = SharedPreference.getPreferences(login.this);

        callbackManager=CallbackManager.Factory.create();



        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        binding.loginButton.setPermissions(Arrays.asList("email","user_birthday"));

        binding.loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {



                final String accessToken = loginResult.getAccessToken().getUserId();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {

                                // Getting FB User Data and checking for null
                                Bundle facebookData = getFacebookData(jsonObject);
                                String email = "";
                                String first_name = "";
                                String last_name = "";
                                String profile_pic = "";

                                if (facebookData.getString("email") != null && !TextUtils.isEmpty(facebookData.getString("email")))
                                    email = facebookData.getString("email");
                                else
                                    email = "none";

                                if (facebookData.getString("first_name") != null && !TextUtils.isEmpty(facebookData.getString("first_name")))
                                    first_name = facebookData.getString("first_name");
                                else
                                    first_name = "none";

                                if (facebookData.getString("last_name") != null && !TextUtils.isEmpty(facebookData.getString("last_name")))
                                    last_name = facebookData.getString("last_name");
                                else
                                    last_name = "none";

                                if (facebookData.getString("profile_pic") != null && !TextUtils.isEmpty(facebookData.getString("profile_pic")))
                                    profile_pic = facebookData.getString("profile_pic");
                                else
                                    profile_pic = "none";

                                sharedPreference.setName(first_name+" "+last_name);
                                sharedPreference.setEmail(email);
                                sharedPreference.setImage(""+profile_pic);
                                Intent mainIntent = new Intent(login.this, MainActivity.class);
                                login.this.startActivity(mainIntent);
                                login.this.finish();


                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

      /*  AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();*/


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

/*    AccessTokenTracker tokenTracker= new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {


            if (currentAccessToken==null)
            {

            }else {
                loaduserProfile(currentAccessToken);
            }

        }
    };*/


    private void loaduserProfile(AccessToken accessToken)
    {
        GraphRequest request=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                if(object!=null)
                {
                    try {
                        String email=object.getString("email");
                        String name=object.getString("name");
                        String id=object.getString("id");
                        Log.d(TAG, "onCompleted: "+id);
                        Toast.makeText(login.this, ""+id, Toast.LENGTH_LONG).show();
                    }catch (JSONException e)
                    {

                        Toast.makeText(login.this, "catch", Toast.LENGTH_LONG).show();
                       e.printStackTrace();
                    }
                }
            }
        });

        Bundle parameters=new Bundle();
        parameters.putString("fields", "id,name,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));


        } catch (Exception e) {
            Log.d("TAG", "BUNDLE Exception : "+e.toString());
        }

        return bundle;
    }



    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            sharedPreference.setName(account.getDisplayName());
            sharedPreference.setEmail(account.getEmail());
            sharedPreference.setImage(""+account.getPhotoUrl());
            Intent mainIntent = new Intent(login.this, MainActivity.class);
            login.this.startActivity(mainIntent);
            login.this.finish();


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }



}