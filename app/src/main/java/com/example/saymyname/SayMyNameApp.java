package com.example.saymyname;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider;

public class SayMyNameApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            AmplifyModelProvider provider = AmplifyModelProvider.getInstance();
            Amplify.addPlugin(new AWSDataStorePlugin(provider));
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());

            Amplify.configure(getApplicationContext());
            Log.i("amlify", "configured !");
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
    }
}
