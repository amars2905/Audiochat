package com.flower.myaudiomessanger.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.datastore.generated.model.Todo;
import com.amplifyframework.datastore.generated.model.Users;
import com.flower.myaudiomessanger.R;
import com.flower.myaudiomessanger.adapter.CustomAdapter;
import com.flower.myaudiomessanger.model.languagecodemodel.Code;
import com.flower.myaudiomessanger.utility.Alerts;
import com.flower.myaudiomessanger.utility.AppPreference;
import com.flower.myaudiomessanger.utility.Constant;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText username, email, password;
    Button btn_register;
    Spinner spin;
    List<Code> languageCodeModels;
    String languageName = "";
    String languageCode = "";
    String languageVoice = "";
    String languageEngine = "";
    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        languageCodeModels = new ArrayList<>();

        languageCodeModels.add(new Code("Arabic","ar","Zeina", "standard"));
        languageCodeModels.add(new Code("Indian English", "en", "Aditi", "standard"));
        languageCodeModels.add(new Code("Hindi", "hi", "Aditi", "standard"));
        languageCodeModels.add(new Code("Maltese", "mt", "Joanna", "neural"));

        spin = (Spinner) findViewById(R.id.simpleSpinner);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),languageCodeModels);
        spin.setAdapter(customAdapter);
        spin.setOnItemSelectedListener(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUser(email.getText().toString());

                /*Amplify.DataStore.query(Users.class,
                        todos -> {
                            while (todos.hasNext()) {
                                Users todo = todos.next();
                                if (todo.getEmail().equals(email.getText().toString())) {
                                    Log.i("Tutorial", "==== Todo ====");
                                    Log.i("Tutorial", "Name: " + todo.getName());
                                    Log.i("Tutorial", "Id: " + todo.getId());
                                    gotochat();
                                }
                            }
                        },
                        failure -> Log.e("Tutorial", "Could not query DataStore", failure)
                );*/

                Amplify.DataStore.observe(Users.class,
                        started -> Log.i("Tutorial", "Observation began."),
                        change -> Log.i("Tutorial : ", change.item().toString()),
                        failure -> Log.e("Tutorial", "Observation failed.", failure),
                        () -> Log.i("Tutorial", "Observation complete.")
                );

            }
        });

    }

    private void checkUser(String strEmail){
        Amplify.DataStore.query(Users.class,
                Where.matches(Users.EMAIL.eq(strEmail)),
                todos -> {
                    Log.i("Tutorial","..."+todos.hasNext());
                    if (todos.hasNext()) {
                        while (todos.hasNext()) {
                            Users todo = todos.next();
                            Log.i("Tutorial", "==== Todo ====");
                            Log.i("Tutorial", "Name: " + todo.getName());
                            Log.i("Tutorial", "Id: " + todo.getId());
                            Alerts.showRun(RegisterActivity.this,"already user");
                        }
                    }else {
                        saveData();
                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );
    }

    private void saveData(){
        Users users = Users.builder()
                .name(username.getText().toString())
                .email(email.getText().toString().trim())
                .language(languageName)
                .languageCode(languageCode)
                .engine(languageEngine)
                .voice(languageVoice)
                .password(password.getText().toString().trim())
                .build();
        Amplify.DataStore.save(users,
                success ->  gotochat(users),
                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
        );
    }

    private void gotochat(Users users){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppPreference.setStringPreference(context, Constant.userEmail, users.getEmail());
                AppPreference.setStringPreference(context, Constant.userId, users.getId());
                AppPreference.setStringPreference(context, Constant.userName, users.getName());
                AppPreference.setStringPreference(context, Constant.userLanguage, users.getLanguage());
                AppPreference.setStringPreference(context, Constant.userLanguageCode, users.getLanguageCode());
                AppPreference.setStringPreference(context, Constant.userLanguageEngine, users.getEngine());
                AppPreference.setStringPreference(context, Constant.userLanguageVoice, users.getVoice());
                intent = new Intent(RegisterActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveUser(){
        //Toast.makeText(mContext,"Save", Toast.LENGTH_SHORT).show();
        Log.i("Ragistration","Save Date");
        AppPreference.setStringPreference(RegisterActivity.this, Constant.userEmail,email.getText().toString());
    }

    /*private void languageDataApi() {
        if (cd.isNetWorkAvailable()) {
            RetrofitService.getLanguageData(new Dialog(mContext), retrofitApiClient.languageData(), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    LanguageCodeModel translateModel = (LanguageCodeModel) result.body();
                    // tv_chat_message.setText(translateModel.getTarget().getTranslatedText());
                    //outputString = translateModel.getTarget().getTranslatedText();
                    Log.d("translat", ".." + ((LanguageCodeModel) result.body()).toString());
                    languageCodeModels = translateModel.getLanguagecode().getCode();
                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), translateModel.getLanguagecode().getCode());
                    spin.setAdapter(customAdapter);
                }

                @Override
                public void onResponseFailed(String error) {
                    Alerts.show(mContext, error);
                }
            });
        } else {
            cd.show(mContext);
        }
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), languageCodeModels.get(position).getLanguage(), Toast.LENGTH_LONG).show();
        languageName = languageCodeModels.get(position).getLanguage();
        languageCode = languageCodeModels.get(position).getLanguageCode();
        languageEngine = languageCodeModels.get(position).getEngine();
        languageVoice = languageCodeModels.get(position).getVoice();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}