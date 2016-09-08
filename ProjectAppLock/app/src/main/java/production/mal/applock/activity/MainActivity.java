package production.mal.applock.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.wx.goodview.GoodView;

import production.mal.applock.R;

public class MainActivity extends AppCompatActivity {
    private Button btAppLock;
    private Button btScreenLock;
    private Button btAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GoodView goodView = new GoodView(this);
        final Animation animAlpha = AnimationUtils.loadAnimation(this,
                R.anim.anim_alpha);

        btAppLock = (Button) findViewById(R.id.btLockApp);
        btAppLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodView.setTextInfo("The function help you to protect your app", Color.parseColor("#ffffff"), 10);
                view.startAnimation(animAlpha);
                goodView.setDuration(2000);
                goodView.show(view);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            startActivity(new Intent(getApplication(),LockAppActivity.class));
                            finish();
                        } catch (InterruptedException e) {
                        }

                    }
                };
                thread.start();

            }
        });

        btScreenLock = (Button) findViewById(R.id.btLockScreen);
        btScreenLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodView.setTextInfo("The function help you have perfect screen", Color.parseColor("#ffffff"), 10);
                view.startAnimation(animAlpha);
                goodView.setDuration(2000);
                goodView.show(view);
            }
        });

        btAbout = (Button) findViewById(R.id.btAbout);
        btAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodView.setTextInfo("Something about me", Color.parseColor("#ffffff"), 10);
                view.startAnimation(animAlpha);
                goodView.setDuration(2000);
                goodView.show(view);
            }
        });
    }
}
