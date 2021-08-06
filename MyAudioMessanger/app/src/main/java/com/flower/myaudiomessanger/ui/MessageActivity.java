package com.flower.myaudiomessanger.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.DataStoreItemChange;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.predictions.models.LanguageType;
import com.flower.myaudiomessanger.R;
import com.flower.myaudiomessanger.adapter.MessageAdapter;
import com.flower.myaudiomessanger.utility.Alerts;
import com.flower.myaudiomessanger.utility.AppPreference;
import com.flower.myaudiomessanger.utility.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MessageActivity extends AppCompatActivity {

    String chatUserName;
    String chatUserEmail;
    String chatUserId;
    String chatUserLanguage;
    String chatUserLanguageCode;
    String chatUserLanguageEngine;
    String chatUserLanguageVoice;

    TextView username;

    ImageButton btn_send;
    ImageButton btn_voice;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Message> mchat = new ArrayList<>();

    RecyclerView recyclerView;

    Intent intent;
    Context context;

    private final MediaPlayer mp = new MediaPlayer();
    TextToSpeech t1;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        intent = getIntent();
        context = this;
        chatUserId = intent.getStringExtra(Constant.chatUserId);
        chatUserEmail = intent.getStringExtra(Constant.chatUserEmail);
        chatUserName = intent.getStringExtra(Constant.chatUserName);
        chatUserLanguage = intent.getStringExtra(Constant.chatUserLanguage);
        chatUserLanguageCode = intent.getStringExtra(Constant.chatUserLanguageCode);
        chatUserLanguageEngine = intent.getStringExtra(Constant.chatUserLanguageEngine);
        chatUserLanguageVoice = intent.getStringExtra(Constant.chatUserLanguageVoice);

        Log.i("Chat Username",""+chatUserName);
        Log.i("Chat Useremail",""+chatUserEmail);
        Log.i("Chat UserId",""+chatUserId);
        Log.i("Chat UserLanguage",""+chatUserLanguage);
        Log.i("Chat UserLanguageCode",""+chatUserLanguageCode);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // and this
                startActivity(new Intent(MessageActivity.this, ChatActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        t1.setLanguage(Locale.forLanguageTag(AppPreference.getStringPreference(context,Constant.userLanguageCode)));
                    }
                }
            }
        });
        checkPermission();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(MessageActivity.this, mchat);
        recyclerView.setAdapter(messageAdapter);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        btn_voice = findViewById(R.id.btn_voice);
        text_send = findViewById(R.id.text_send);

        username.setText(chatUserName);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t1 != null) {
                    t1.stop();
                    t1.shutdown();
                }
                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                t1.setLanguage(Locale.forLanguageTag(AppPreference.getStringPreference(context,Constant.userLanguageCode)));
                            }
                        }
                    }
                });
                translateText(text_send.getText().toString());
            }
        });

        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, AppPreference.getStringPreference(context,Constant.userLanguageCode));
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to send message");
                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast.makeText(MessageActivity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkUser();

        Amplify.DataStore.observe(Message.class,
                cancelable -> Log.i("MyAmplifyApp", "Observation began."),
                this::onNewMessageReceived,
                failure -> Log.e("MyAmplifyApp", "Observation failed.", failure),
                () -> Log.i("MyAmplifyApp", "Observation complete.")
        );
    }

    private void onNewMessageReceived(DataStoreItemChange<Message> messageChanged) {
        if (messageChanged.type().equals(DataStoreItemChange.Type.CREATE)) {
            Message message = messageChanged.item();
            Log.i("Tutorial", "==== Message ====");
            Log.i("Tutorial", "Name: " + message.getMessage());
            Log.i("Tutorial", "Id: " + message.getId());
            Log.i("Tutorial", "Sender: " + message.getSender());
            if (message.getUserUrl().equals(AppPreference.getStringPreference(context, Constant.userId)+ "___" +chatUserId)) {
                //t1.speak(message.getMessage(), TextToSpeech.QUEUE_FLUSH, null);
                Amplify.Predictions.convertTextToSpeech(
                        message.getMessage(),
                        result -> playAudio(result.getAudioData()),
                        error -> Log.e("MyAmplifyApp", "Conversion failed", error)
                );
                mchat.add(message);
            }
            runOnUiThread(() -> messageAdapter.notifyDataSetChanged());
        }
    }

    private void translateText(String message){
        Log.i("User Code",AppPreference.getStringPreference(context, Constant.userLanguageCode));
        Log.i("Chat Code",chatUserLanguageCode);
        Amplify.Predictions.translateText(
                message,
                LanguageType.from(AppPreference.getStringPreference(context, Constant.userLanguageCode)),
                LanguageType.from(chatUserLanguageCode),
                result -> saveData(result.getTranslatedText()),
                error -> Log.e("MyAmplifyApp", "Translation failed", error)
        );
    }

    private void saveData(String message) {
        Log.i("MyAmplifyApp", message);
        Message users = Message.builder()
                .message(message)
                .time(String.valueOf(System.currentTimeMillis()))
                .sender(AppPreference.getStringPreference(context, Constant.userId))
                .receiver(chatUserId)
                .userUrl(chatUserId + "___" + AppPreference.getStringPreference(context, Constant.userId))
                .audioUrl("")
                .build();
        Amplify.DataStore.save(users,
                success -> Log.i("Tutorial", "Send Msg"),
                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
        );

        Message users1 = Message.builder()
                .message(text_send.getText().toString())
                .time(String.valueOf(System.currentTimeMillis()))
                .sender(AppPreference.getStringPreference(context, Constant.userId))
                .receiver(chatUserId)
                .userUrl(AppPreference.getStringPreference(context, Constant.userId)+ "___" +chatUserId)
                .audioUrl("")
                .build();
        Amplify.DataStore.save(users1,
                success -> sendMsg(),
                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
        );

        text_send.setText("");
    }

    private void sendMsg() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Alerts.show(context, "Message Send");
            }
        });
    }

    private void checkUser() {
        Amplify.DataStore.query(Message.class,
                Where.matches(Message.USER_URL.eq(AppPreference.getStringPreference(context, Constant.userId)+ "___" +chatUserId)),
                todos -> {
                    Log.i("Tutorial", "..." + todos.hasNext());
                    while (todos.hasNext()) {
                        Message todo = todos.next();
                        Log.i("Tutorial", "==== Todo ====");
                        Log.i("Tutorial", "Name: " + todo.getMessage());
                        Log.i("Tutorial", "Id: " + todo.getId());
                        Log.i("Tutorial", "Sender: " + todo.getUserUrl());

                        /*Amplify.API.mutate(ModelMutation.create(todo),
                                response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
                                error -> Log.e("MyAmplifyApp", "Create failed", error)
                        );*/

                        if (todo.getSender() != null)
                            mchat.add(todo);

                    }
                    messageAdapter.notifyDataSetChanged();
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );
    }

    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }
    }

    @Override
    protected void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        t1.setLanguage(Locale.forLanguageTag(AppPreference.getStringPreference(context,Constant.userLanguageCode)));
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                text_send.setText(Objects.requireNonNull(result).get(0));
                translateText(text_send.getText().toString());
                if (t1 != null) {
                    t1.stop();
                    t1.shutdown();
                }
                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                t1.setLanguage(Locale.forLanguageTag(AppPreference.getStringPreference(context,Constant.userLanguageCode)));
                            }
                        }
                    }
                });
            }else {
                Log.e("Voice ","..."+data);
                if (t1 != null) {
                    t1.stop();
                    t1.shutdown();
                }
                t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                t1.setLanguage(Locale.forLanguageTag(AppPreference.getStringPreference(context,Constant.userLanguageCode)));
                            }
                        }
                    }
                });
            }
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onStop() {
        if (t1 != null) {
            t1.stop();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (t1 != null) {
            t1.shutdown();
        }
        super.onDestroy();
    }

}