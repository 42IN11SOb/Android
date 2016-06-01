package com.avans.in11sob.pep_android.Utilities;


import android.support.annotation.NonNull;

import com.android.volley.BuildConfig;
import com.android.volley.Response;
import com.avans.in11sob.pep_android.Api.Models.IsLoggedIn;
import com.avans.in11sob.pep_android.Api.Models.Login;
import com.avans.in11sob.pep_android.Api.Models.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
/**
 * Created by Mark on 24-5-2016.
 */
public class ApiRequests {

    private static final Gson gson = new GsonBuilder()
            //.registerTypeAdapter(DummyObject.class, new DummyObjectDeserializer())
            .create();
    /**
     * Returns a dummy object
     *
     * @param listener is the listener for the correct answer
     * @param errorListener is the listener for the error response
     *
     * @return {@link GsonGetRequest}
     */
    public static GsonGetRequest<IsLoggedIn> checkLogin
    (
            @NonNull final Response.Listener<IsLoggedIn> listener,
            @NonNull final Response.ErrorListener errorListener,
            @NonNull final String token
    )
    {
        final String url = "http://projectpep.herokuapp.com/users/loggedin?token=" + token;

        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<IsLoggedIn>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

    public static GsonGetRequest<Profile> profile
    (
            @NonNull final Response.Listener<Profile> listener,
            @NonNull final Response.ErrorListener errorListener,
            @NonNull final String token
    )
    {
        final String url = "http://projectpep.herokuapp.com/users/profile?token=" + token;

        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<Profile>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

    public static GsonPostRequest<Login> login
    (
        @NonNull final Response.Listener<Login> listener,
        @NonNull final Response.ErrorListener errorListener,
        @NonNull final String username,
        @NonNull final String password
    )
    {
        final String url = "http://projectpep.herokuapp.com/users/login";

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);

        return new GsonPostRequest<>
                (
                        url,
                        jsonObject.toString(),
                        new TypeToken<Login>()
                        {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

}
