package ru.wtw.moreliatalkclient;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.util.Consumer;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.protocols.IProtocol;

import com.google.gson.Gson;


public class Network {
    private URI socketURI;
    private WebSocketClient socket;

    private Activity activity;

    private String username;
    private String password;
    private String servername;
    private boolean isConnected;

    public Network(Activity activity){
        this.activity=activity;
        isConnected=false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void connect() {
        try {
            socketURI = new URI(servername);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        socket = new WebSocketClient(socketURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                isConnected=true;
                sendAuth();
                Log.i("SERVER","Connected");
            }

            @Override
            public void onMessage(final String message) {
                final Protocol protocol = new Gson().fromJson(message,Protocol.class);
                activity.runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        final TextView chat = ((TextView) activity.findViewById(R.id.chat));
                        final ScrollView chatScroller = activity.findViewById(R.id.chatScroller);
                        if (protocol.getMode().equals("message")) {
                            chat.setText(chat.getText().toString() + "\n" + protocol.getUsername() + ": " + protocol.getText() + "\n");
                        }
                        if (protocol.getMode().equals("reg")) {
                            String status=protocol.getStatus();
                            String reply=activity.getResources().getString(R.string.auth_status_unknown);
                            if (status.equals("true")) {reply=activity.getResources().getString(R.string.auth_status_true);}
                            if (status.equals("false")) {reply=activity.getResources().getString(R.string.auth_status_false);}
                            if (status.equals("newreg")) {reply=activity.getResources().getString(R.string.auth_status_newreg);}
                            chat.setText(chat.getText().toString() + "\n" + reply + "\n");
                        }
                        chatScroller.post(new Runnable()
                            {
                                public void run()
                                {
                                    chatScroller.smoothScrollTo(0, chat.getBottom());
                                }
                            });
                    }
                });
                Log.i("SERVER","Message");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("SERVER","Disconnected");
            }

            @Override
            public void onError(Exception ex) {
                Log.e("SERVER","Connected", ex);
            }
        };
        Log.i("SERVER","Connect");
        socket.connect();

    }

    public void sendAuth () {
        Gson gson = new Gson();
        Protocol protocol = new Protocol();
        protocol.setMode("reg");
        protocol.setUsername(username);
        protocol.setPassword(password);
        String json = gson.toJson(protocol);
        if (socket != null && socket.isOpen()) {
            socket.send(json);
        }
    }

    public void sendMessage (String text) {
        Gson gson = new Gson();
        Protocol protocol = new Protocol();
        protocol.setMode("message");
        protocol.setUsername(username);
        protocol.setText(text);
        String json = gson.toJson(protocol);
        if (socket != null && socket.isOpen()) {
            socket.send(json);
        }
    }

}
