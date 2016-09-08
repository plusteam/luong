package production.mal.applock.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import production.mal.applock.R;
import production.mal.applock.adapter.AppAdapter;

/**
 * Created by Admin on 9/7/2016.
 */
public class LockAppActivity extends ListActivity {
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applicationInfos = null;
    private AppAdapter appAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockapp);

        packageManager = getPackageManager();

        new LoadApplications().execute();
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applicationInfos = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            appAdapter = new AppAdapter(LockAppActivity.this,
                    R.layout.app_properties, applicationInfos);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(appAdapter);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(LockAppActivity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void backToMain(View view){
        Intent intent = new Intent(LockAppActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
