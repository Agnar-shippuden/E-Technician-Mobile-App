//package com.example.serviceprovider.services;
//
//
//import android.app.IntentService;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//import android.util.Log;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.net.URISyntaxException;
//
//import io.socket.client.IO;
//import io.socket.client.Socket;
//import io.socket.emitter.Emitter;
//
//public class SocketService extends IntentService {
//
//    private static final String TAG = "SocketService";
//    private Socket mSocket;
//    private Context mContext;
//    private String mSender;
//
//    public SocketService() {
//        super(TAG);
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d(TAG, "onCreate");
//        mContext = getApplicationContext();
//        mSender = Prefs.getUserGuid(mContext);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(TAG, "onStartCommand");
//        try {
//            IO.Options opts = new IO.Options();
//            opts.reconnection = true;
//            mSocket = IO.socket("http://10.0.2.2:3000/", opts);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        initSocket();
//
//        mBus.getInstance().register(this);
//        return START_REDELIVER_INTENT;
//    }
//
//    private void initSocket() {
//        mSocket.connect();
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.on(Socket.EVENT_CONNECT, onConnect);
//        mSocket.on(Socket.EVENT_RECONNECT, onConnect);
//        mSocket.on(Socket.EVENT_DISCONNECT, onConnectError);
//
//        //I send the an auth event to the socket.io server
//
//        auth();
//        mSocket.on("receive", onReceived);
//    }
//
//    public void auth() {
//        String mSender = Prefs.getUserGuid(mContext);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("user_guid", mSender);
//            jsonObject.put("device_id", "android");
//            this.mSocket.emit("auth", jsonObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Emitter.Listener onConnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            Log.d(TAG, "connected " + mSocket.connected());
//        }
//    };
//
//    private Emitter.Listener onConnectError = args -> {
//        Log.d(TAG, "error " + args[0].toString());
//    };
//
//    private Emitter.Listener onReceived = args -> {
//        Log.d(TAG, "received rtm");
//        // I parse the object...
//    };
//
//
//    @Subscribe
//    public void sendMessage(SendMessageEvent event) {
//        mSocket.emit("send", event.getMessage());
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy");
//        mSocket.disconnect();
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT, onConnect);
//        mSocket.off("receive", onReceived);
//
//        EventBus mBus = EventBus.getDefault().unregister(this);
//        mBus.getInstance().unregister(this);
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Log.d(TAG, "onHandleIntent");
//    }
//}