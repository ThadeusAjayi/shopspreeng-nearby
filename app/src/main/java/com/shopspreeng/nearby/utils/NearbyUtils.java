package com.shopspreeng.nearby.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.UnsupportedEncodingException;

/**
 * Created by Thadeus-APMIS on 8/12/2018.
 */

/**
 * Helper class for {Nearby functions and actions}
 */
public class NearbyUtils {

    private static final String TAG = NearbyUtils.class.getSimpleName();
    private static Message mMessage;

    private NearbyUtils sInstance;
    private static final Object LOCK = new Object();
    private static Activity mContext;


    /**
     * Class must be initialized with contructor to ensure context is passed into utils
     *
     * @return Class instance with context passed in
     */
    public NearbyUtils(Activity context) {
        mContext = context;
    }

    /**
     * Get singleton instance of class
     *
     * @return Class reference
     */
    public NearbyUtils getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NearbyUtils(mContext);
                mMessage = new Message("Welcome".getBytes());
            }
        }
        return sInstance;
    }


    /**
     * Publish message to other subscribers
     * @param   message String message into method and publish to all.
     *
     * @return  void
     */
    public void publishMessage(String message) {
        mMessage = new Message(message.getBytes(), "UTF-8");
        Nearby.getMessagesClient(mContext).publish(mMessage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG + " successfule publish", "Works well");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG + " failure publish", e.getMessage());
                    }
                });
    }

    public void unPublishMessage() {
        if (mMessage != null) {
            Nearby.getMessagesClient(mContext).unpublish(mMessage);
        }
    }

    //From discovering device
    public void startDiscovery() {
        Nearby.getConnectionsClient(mContext)
                .startDiscovery(
                        "SHOPSPREENG", //Service ID
                        endpointDiscoveryCallback(),
                        new DiscoveryOptions(Strategy.P2P_CLUSTER))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "Nearby discovered", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to discover nearby", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    EndpointDiscoveryCallback endpointDiscoveryCallback() {
        return new EndpointDiscoveryCallback() {
            @Override
            public void onEndpointFound(@NonNull String endpointId, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                Log.d("Endpoint Discovered ID", endpointId);
                Nearby.getConnectionsClient(mContext)
                        .requestConnection(
                                "DI", //User nickname
                                endpointId,
                                connectionLifecycleCallback())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Endpoint discovered", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }

            @Override
            public void onEndpointLost(@NonNull String s) {

            }
        };
    }

    //From advertising device
    public void startAdvertising() {
        Nearby.getConnectionsClient(mContext)
                .startAdvertising(
                        "DI",//User nickname
                        "SHOPSPREENG",//Service ID
                        connectionLifecycleCallback(),
                        new AdvertisingOptions(Strategy.P2P_CLUSTER))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "Advertisement started", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Advertisement failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    ConnectionLifecycleCallback connectionLifecycleCallback() {
        return new ConnectionLifecycleCallback() {
            @Override
            public void onConnectionInitiated(@NonNull final String endpointId, @NonNull ConnectionInfo connectionInfo) {
                Log.d(TAG, "Connection Initiated Both devices seen " + connectionInfo.toString());
                new AlertDialog.Builder(mContext)
                        .setTitle("Accept connection to " + connectionInfo.getEndpointName())
                        .setMessage("Confirm the code " + connectionInfo.getAuthenticationToken() + " is also displayed on the other device")
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The user confirmed, so we can accept the connection.
                                Nearby.getConnectionsClient(mContext).acceptConnection(endpointId, payloadCallback());
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The user canceled, so we should reject the connection.
                                Nearby.getConnectionsClient(mContext).rejectConnection(endpointId);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

            @Override
            public void onConnectionResult(@NonNull String s, @NonNull ConnectionResolution result) {
                switch (result.getStatus().getStatusCode()) {
                    case ConnectionsStatusCodes.STATUS_OK:
                        // We're connected! Can now start sending and receiving data.
                        Log.d(TAG, "Connection successful");
                        break;
                    case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                        // The connection was rejected by one or both sides.
                        Log.d(TAG, "Connection rejected");
                        break;
                    case ConnectionsStatusCodes.STATUS_ERROR:
                        // The connection broke before it was able to be accepted.
                        Log.d(TAG, "Connection broke");
                        break;
                }
            }

            @Override
            public void onDisconnected(@NonNull String s) {

            }
        };
    }

    private static PayloadCallback payloadCallback() {
        return new PayloadCallback() {
            @Override
            public void onPayloadReceived(@NonNull String endpointId, @NonNull Payload payload) {
                Log.d(TAG + " payload", String.valueOf(payload));
                String receivedMessage = "";
                try {
                    receivedMessage = new String(payload.asBytes(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, String.valueOf(receivedMessage), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {

            }
        };
    }

}
