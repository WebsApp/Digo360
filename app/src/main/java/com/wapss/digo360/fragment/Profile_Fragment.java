package com.wapss.digo360.fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wapss.digo360.R;
import com.wapss.digo360.activity.AboutDigo;
import com.wapss.digo360.activity.ChooseLanguageActivity;
import com.wapss.digo360.activity.HelpPage;
import com.wapss.digo360.activity.LoginActivity;
import com.wapss.digo360.activity.MyProfile;
import com.wapss.digo360.activity.ReferPage;
import com.wapss.digo360.activity.RegistrationActivity;
import com.wapss.digo360.activity.SearchPage;
import com.wapss.digo360.apiServices.ApiService;
import com.wapss.digo360.authentication.CustomProgressDialog;
import com.wapss.digo360.response.Profile_Response;
import com.wapss.digo360.utility.Internet_Check;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Fragment extends Fragment {

    LinearLayout profile_layout,about_layout,version_layout,logout_layout,btn_language,btn_refer,permission_layout,
            btn_manage_case,btn_storage,btn_Search,btn_Ai_question,btn_alart,btn_case_file;
    LinearLayout ll_logOut;
    ImageView btn_faq;
    TextView txt_profile,txt_degree;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String name,Token,deviceToken;
    CustomProgressDialog progressDialog;
    private Dialog noInternetDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = requireActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireActivity().getWindow().getContext(), R.color.purple));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View profile =  inflater.inflate(R.layout.fragment_profile_, container, false);

        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        deviceToken = loginPref.getString("deviceToken", null);
        progressDialog = new CustomProgressDialog(getContext());
        //name = loginPref.getString("DR_NAME", null);
        txt_degree = profile.findViewById(R.id.txt_degree);
        txt_profile = profile.findViewById(R.id.txt_profile);
        btn_refer = profile.findViewById(R.id.btn_refer);
        btn_language = profile.findViewById(R.id.btn_language);
        btn_faq = profile.findViewById(R.id.btn_faq);
        logout_layout = profile.findViewById(R.id.logout_layout);
        version_layout = profile.findViewById(R.id.version_layout);
        profile_layout = profile.findViewById(R.id.profile_layout);
        about_layout = profile.findViewById(R.id.about_layout);
        ll_logOut = profile.findViewById(R.id.ll_logOut);
        permission_layout = profile.findViewById(R.id.permission_layout);
        btn_manage_case = profile.findViewById(R.id.btn_manage_case);
        btn_storage = profile.findViewById(R.id.btn_storage);
        btn_Search = profile.findViewById(R.id.btn_Search);
        btn_Ai_question = profile.findViewById(R.id.btn_Ai_question);
        btn_alart = profile.findViewById(R.id.btn_alart);
        btn_case_file = profile.findViewById(R.id.btn_case_file);
        /*network Connection Check*/
        if(!Internet_Check.isInternetAvailable(getContext())) {
            noInternetDialog = new Dialog(getContext());
            noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            noInternetDialog.setContentView(R.layout.no_internet_layout);
            noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            noInternetDialog.setCancelable(false);

            TextView retryButton = noInternetDialog.findViewById(R.id.retry_button);
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Internet_Check.isInternetAvailable(getContext())) {
                        noInternetDialog.dismiss();
                    }
                }
            });
            noInternetDialog.show();
            noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
        else {

        }
        get_profile();
        //txt_profile.setText("Dr." +" "+ name);

        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyProfile.class));
            }
        });
        about_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AboutDigo.class));
            }
        });
        btn_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ReferPage.class));
            }
        });
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_NAME", "PROFILE");
                Intent i = new Intent(getContext(), HelpPage.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        ll_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                LayoutInflater inflater1 = getLayoutInflater();
                View dialogView1 = inflater1.inflate(R.layout.logout_layout, null);
                dialogView1.setBackgroundResource(R.drawable.rounded_dialog_background);
                builder1.setCancelable(false);
                builder1.setView(dialogView1);
                final androidx.appcompat.app.AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();
                TextView et_yes = (TextView)alertDialog1.findViewById(R.id.et_yes);
                TextView et_cancel = (TextView)alertDialog1.findViewById(R.id.et_cancel);
                alertDialog1.setCanceledOnTouchOutside(false);
                et_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences preferences = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                et_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });*/

                noInternetDialog = new Dialog(getContext());
                noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                noInternetDialog.setContentView(R.layout.logout_layout);
                noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noInternetDialog.show();
                TextView et_yes = (TextView)noInternetDialog.findViewById(R.id.et_yes);
                TextView et_cancel = (TextView)noInternetDialog.findViewById(R.id.et_cancel);
                et_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences preferences = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                et_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        noInternetDialog.dismiss();
                    }
                });
                noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
        btn_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternetDialog = new Dialog(getContext());
                noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                noInternetDialog.setContentView(R.layout.language_layout);
                noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noInternetDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noInternetDialog.dismiss();
                    }
                }, 2000);
                noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
        permission_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternetDialog = new Dialog(getContext());
                noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                noInternetDialog.setContentView(R.layout.language_layout);
                noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noInternetDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noInternetDialog.dismiss();
                    }
                }, 2000);
                noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
        btn_manage_case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternetDialog = new Dialog(getContext());
                noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                noInternetDialog.setContentView(R.layout.language_layout);
                noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noInternetDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noInternetDialog.dismiss();
                    }
                }, 2000);
                noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
        btn_storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternetDialog = new Dialog(getContext());
                noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                noInternetDialog.setContentView(R.layout.language_layout);
                noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noInternetDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noInternetDialog.dismiss();
                    }
                }, 2000);
                noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchPage.class));
            }
        });
        btn_Ai_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternetDialog = new Dialog(getContext());
                noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                noInternetDialog.setContentView(R.layout.language_layout);
                noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noInternetDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noInternetDialog.dismiss();
                    }
                }, 2000);
                noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
        btn_alart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternetDialog = new Dialog(getContext());
                noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                noInternetDialog.setContentView(R.layout.language_layout);
                noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noInternetDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noInternetDialog.dismiss();
                    }
                }, 2000);
                noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
        btn_case_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noInternetDialog = new Dialog(getContext());
                noInternetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                noInternetDialog.setContentView(R.layout.language_layout);
                noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                noInternetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                noInternetDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        noInternetDialog.dismiss();
                    }
                }, 2000);
                noInternetDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                noInternetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });

        return profile;
    }

    private void get_profile() {
        progressDialog.showProgressDialog();
        Token = "Bearer " + deviceToken;
        Call<Profile_Response> profile_apiCall = ApiService.apiHolders().get_profile(Token);
        profile_apiCall.enqueue(new Callback<Profile_Response>() {
            @Override
            public void onResponse(Call<Profile_Response> call, Response<Profile_Response> response) {
                if (response.isSuccessful()){
                    progressDialog.hideProgressDialog();
                    String Degree = response.body().getDoctorDetailDegree().get(0).getDegree().getName();
                    txt_profile.setText(response.body().getTitle() +"." + " "+ response.body().getName());
                    txt_degree.setText(Degree);
                }
            }
            @Override
            public void onFailure(Call<Profile_Response> call, Throwable t) {
                progressDialog.hideProgressDialog();
            }
        });
    }
}