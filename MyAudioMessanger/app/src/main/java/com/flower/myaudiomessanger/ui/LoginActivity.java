package com.flower.myaudiomessanger.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelPagination;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Todo;
import com.amplifyframework.datastore.generated.model.Users;
import com.flower.myaudiomessanger.R;
import com.flower.myaudiomessanger.utility.Alerts;
import com.flower.myaudiomessanger.utility.AppPreference;
import com.flower.myaudiomessanger.utility.Constant;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btn_login;
    ProgressDialog dialog;
    TextView forgot_password, login_tv, msg_tv;
    Context context;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login_tv = findViewById(R.id.login_tv);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);
        msg_tv = findViewById(R.id.msg_tv);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getTodo(AppPreference.getStringPreference(LoginActivity.this, Constant.Email));
                checkUser(email.getText().toString());
            }
        });

        /*Amplify.API.query(
                ModelQuery.list(Users.class),
                response -> {
                    for (Users todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getName());
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
*/
        Amplify.API.query(
                ModelQuery.list(Todo.class),
                response -> {
                    for (Todo todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getName());
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
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
                            gotochat(todo);
                        }
                    }else {
                        Alerts.showRun(LoginActivity.this,"no user");
                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
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
                intent = new Intent(LoginActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    public void queryFirstPage() {
        query(ModelQuery.list(Users.class, ModelPagination.limit(1000)));
    }

    private static void query(GraphQLRequest<PaginatedResult<Users>> request) {
        Amplify.API.query(
                request,
                response -> {
                    if (response.hasData()) {
                        for (Users todo : response.getData()) {
                            Log.d("MyAmplifyApp", todo.getName());
                        }
                        if (response.getData().hasNextResult()) {
                            query(response.getData().getRequestForNextResult());
                        }
                    }
                },
                failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
        );
    }

    private void getTodo(String email) {
        Amplify.API.query(
                ModelQuery.get(Users.class, email),
                response -> Log.i("MyAmplifyApp", response.getData().toString()),
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );

    }
}