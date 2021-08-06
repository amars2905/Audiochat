package com.flower.myaudiomessanger.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.datastore.generated.model.Users;
import com.amplifyframework.predictions.aws.AWSPredictionsPlugin;
import com.flower.myaudiomessanger.R;
import com.flower.myaudiomessanger.adapter.OnItemClick;
import com.flower.myaudiomessanger.adapter.UserAdapter;
import com.flower.myaudiomessanger.utility.AppPreference;
import com.flower.myaudiomessanger.utility.Constant;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity  implements OnItemClick{
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<Users> mUsers;
    Context context;
    static OnItemClick onItemClick;
    ImageView btn_logout;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        context = this;
        mUsers = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        setUserList();

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreference.clearAllPreferences(context);
                Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        Amplify.DataStore.observe(Users.class,
                cancelable -> Log.i("MyAmplifyApp", "Observation began."),
                this::onNewMessageReceived,
                failure -> Log.e("MyAmplifyApp", "Observation failed.", failure),
                () -> Log.i("MyAmplifyApp", "Observation complete.")
        );

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUserList();
            }
        });

    }

    private void setUserList(){
        mUsers.clear();
        Amplify.DataStore.query(Users.class,
                todos -> {
                    while (todos.hasNext()) {
                        Users todo = todos.next();
                        Log.i("Tutorial", "==== Users ====");
                        Log.i("Tutorial", "Name: " + todo.getName());
                        Log.i("Tutorial", "Id: " + todo.getId());
                        if (todo.getId().equals(AppPreference.getStringPreference(context, Constant.userId)))
                        {}
                        else {mUsers.add(todo);}
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userAdapter = new UserAdapter(ChatActivity.this,mUsers);
                            recyclerView.setAdapter(userAdapter);
                        }
                    });

                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );
        swipeRefreshLayout.setEnabled(false);
    }

    private void onNewMessageReceived(DataStoreItemChange<Users> messageChanged) {
        if (messageChanged.type().equals(DataStoreItemChange.Type.CREATE)) {
            Users message = messageChanged.item();
            Log.i("Tutorial", "==== Message ====");
            Log.i("Tutorial", "Name: " + message.getName());
            Log.i("Tutorial", "Id: " + message.getId());
            mUsers.add(message);
            runOnUiThread(() -> userAdapter.notifyDataSetChanged());
        }
    }



    @Override
    public void onItemCLick(String uid, View view) {

    }
}