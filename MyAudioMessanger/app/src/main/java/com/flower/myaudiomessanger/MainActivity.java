package com.flower.myaudiomessanger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Message;
import com.amplifyframework.datastore.generated.model.Priority;
import com.amplifyframework.datastore.generated.model.Todo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }

        Todo item = Todo.builder()
                .name("Amardeep application")
                .description("Android developer using Amplify")
                .time("100")
                .build();

        Message message = Message.builder()
                .message("hi Android")
                .time("100")
                .build();

        Amplify.DataStore.save(message,
                success -> Log.i("Tutorial", "Saved item: " ),
                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
        );

        Amplify.DataStore.query(Message.class,
                todos -> {
                    while (todos.hasNext()) {
                        Message todo = todos.next();

                        Log.i("Tutorial", "==== Todo ====");
                        Log.i("Tutorial", "Name: " + todo.getMessage());

                        if (todo.getMessage() != null) {
                            Log.i("Tutorial", "Description: " + todo.getMessage());
                        }
                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );


        Amplify.DataStore.query(Todo.class,
                Where.matches(Todo.PRIORITY.eq(Priority.HIGH)),
                todos -> {
                    while (todos.hasNext()) {
                        Todo todo = todos.next();

                        Log.i("Tutorial", "==== Todo ====");
                        Log.i("Tutorial", "Name: " + todo.getName());

                        if (todo.getPriority() != null) {
                            Log.i("Tutorial", "Priority: " + todo.getPriority().toString());
                        }

                        if (todo.getDescription() != null) {
                            Log.i("Tutorial", "Description: " + todo.getDescription());
                        }
                    }
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );



        Amplify.DataStore.observe(Todo.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );

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
}