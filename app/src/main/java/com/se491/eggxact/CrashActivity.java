package com.se491.eggxact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class CrashActivity extends AppCompatActivity {
    private static final String TAG = "CrashActivity";
    FirebaseCrashlytics firebaseCrashlytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        firebaseCrashlytics = FirebaseCrashlytics.getInstance();
    }

    /*
     *
     *  Crash A and B are forcing the app to crash (Unhandled Exceptions) in this case
     *  they will be reported automatically... helpful for unpredictable crashes (Automatic Reports).
     *
     */
    public void crashA(View v){
        throw new RuntimeException("Crash A");
    }

    public void crashB(View v){
        throw new NullPointerException("Crash B");
    }

    /*
     *
     *  Crash C and D is a way to log exceptions, errors or anything...
     *  in this case of a handled exception the app won't crash but we will get it reported to our console.
     *
     */
    public void crashC(View v){
        try {
            throw new ClassCastException("Crash C");
        }
        catch (Exception e){
            Log.d(TAG, "crashC: ");
            firebaseCrashlytics.log("Forced Crash");
            firebaseCrashlytics.recordException(e);
        }
    }

    public void crashD(View v){
        try {
            throw new ArrayIndexOutOfBoundsException("Crash D");
        }
        catch (Exception e){
            Log.d(TAG, "crashD: ");
            firebaseCrashlytics.recordException(e);
        }
    }

}