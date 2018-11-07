package cn.netkiller.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ThreadModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_mode);

        EventBus.getDefault().register(this);

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EventBus Thread : ", Thread.currentThread().getName());
                EventBus.getDefault().post("http://www.netkiller.cn");
            }
        });

        findViewById(R.id.buttonThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("EventBus Thread : ", Thread.currentThread().getName());
                        EventBus.getDefault().post("http://www.netkiller.cn");

                    }
                }).start();

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostThread(String event) {
        Log.d("EventBus PostThread", "Message： " + event + "  thread: " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(String event) {
        Log.d("EventBus MainThread", "Message： " + event + "  thread: " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onEventMainOrdered(String event) {
        Log.d("EventBus MainOrdered", "Message: " + event + " thread:" + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackgroundThread(String event) {
        Log.d("EventBus BackgroundThread", "Message： " + event + "  thread: " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventAsync(String event) {
        Log.d("EventBus Async", "Message： " + event + "  thread: " + Thread.currentThread().getName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
