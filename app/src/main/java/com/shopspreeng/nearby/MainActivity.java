package com.shopspreeng.nearby;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.shopspreeng.nearby.utils.NearbyUtils;
import com.shopspreeng.nearby.utils.PermissionUtils;

import java.util.Calendar;

/**
 * Created by Thadeus-APMIS on 8/12/2018.
 */

public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    MessageListener mMessageListener;
    RecyclerView messageRecycler;
    MessageAdapter messageAdapter;
    NearbyUtils nearbyUtils;
    TextInputEditText inputMessage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.checkPermissions(this);
        messageRecycler = findViewById(R.id.message_recycler);
        inputMessage = findViewById(R.id.message_edittext);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messageAdapter = new MessageAdapter(this);
        messageRecycler.setAdapter(messageAdapter);
        nearbyUtils = new NearbyUtils(this).getInstance();

        mMessageListener = onMessage();



    }


    @Override
    protected void onStart() {
        super.onStart();
        Nearby.getMessagesClient(this).subscribe(mMessageListener);
        Nearby.getMessagesClient(this).publish(new Message("hello there".getBytes()));
    }

    @Override
    protected void onStop() {
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener);
        nearbyUtils.unPublishMessage();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.LOCATION_REQUEST_CODE) {
           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

               //TODO start discovery && advertising
           } else {
                /*Do nothing if permission not granted*/
           }
        }
    }

    MessageListener onMessage () {
        return new MessageListener() {
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                Log.d(TAG, "Found message: " + new String(message.getContent()));
                MessageItem messageItem = new MessageItem(new String(message.getContent()), Calendar.getInstance().getTimeInMillis());
                messageAdapter.addNewMessage(messageItem);
            }

            @Override
            public void onLost(Message message) {
                super.onLost(message);
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };
    }

    public void publish(View view) {
        String newMessage = inputMessage.getText().toString();
        nearbyUtils.publishMessage(newMessage);
        inputMessage.setText("");
    }

    public void startDiscovery(View view) {
        nearbyUtils.startDiscovery();
    }

    public void startAdvertising(View view) {
        nearbyUtils.startAdvertising();
    }
}

