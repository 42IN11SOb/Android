package com.avans.in11sob.pep_android.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.avans.in11sob.pep_android.Adapter.Authenticator;

/**
 * Created by Mark on 4-5-2016.
 */
public class PepAuthenticatorService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        Authenticator authenticator = new Authenticator(this);
        return authenticator.getIBinder();
    }
}
