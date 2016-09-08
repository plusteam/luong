package production.mal.applock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.wangyuwei.particleview.ParticleView;
import production.mal.applock.R;


public class SplashActivity extends AppCompatActivity {

    ParticleView mPvGithub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        mPvGithub = (ParticleView) findViewById(R.id.pv_github);

        mPvGithub.startAnim();

        mPvGithub.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}
