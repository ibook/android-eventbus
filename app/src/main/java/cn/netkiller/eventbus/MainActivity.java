package cn.netkiller.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.AsyncExecutor;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

import cn.netkiller.eventbus.pojo.MessageEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncExecutor.create().execute(
                        new AsyncExecutor.RunnableEx() {
                            @Override
                            public void run() throws Exception {
                                init();
                                EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
                            }
                        }
                );
            }
        });

        findViewById(R.id.buttonSticky).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent("Hello everyone!"));
                startActivity(new Intent(MainActivity.this, StickyActivity.class));
            }
        });
        findViewById(R.id.buttonThreadMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThreadModeActivity.class));
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }

    public void init() throws Exception {
        // ...
        throw new Exception("实际发送异常");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hrowableFailureEvent(ThrowableFailureEvent event) {
        Log.d("EventBus", "hrowableFailureEvent: " + event.getThrowable().getMessage());
        Toast.makeText(this, event.getThrowable().getMessage(), Toast.LENGTH_SHORT).show();
    }

}
