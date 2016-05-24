package com.avans.in11sob.pep_android.Utilities;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by Mark on 24-5-2016.
 */
public class GsonGetRequest<T> extends Request<T> {

    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;


    public GsonGetRequest(String url, Type type, Gson gson, Response.Listener<T> listener, Response.ErrorListener errorListener)
    {
        super(Method.GET, url, errorListener);

        this.gson = gson;
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try
        {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return (Response<T>) Response.success(gson.fromJson(json, type), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        }
    }

    /************************ EXAMPLE EXECUTION CODE ************************
     * returns a dummy object                                               *
     *                                                                      *
     * @param listener is the listener for the correct answer               *
     * @param  errorListener is the listener for the error response         *
     ************************************************************************
     public static GsonGetRequest<DummyObject> getDummyObject (
        Response.Listener<DummyObject> listener,
        Response.ErrorListener errorListener
     ){
        final String url = "http://www.mocky.io/v2/55973508b0e9e4a71a02f05f";

        final Gson gson = new GsonBuilder()
        .registerTypeAdapter(DummyObject.class, new DummyObjectDeserializer())
        .create();

        return new GsonGetRequest<> (url, new TypeToken<DummyObject>() {}.getType(), gson, listener, errorListener);
     }
     ************************************************************************/

    /************************ EXAMPLE EXECUTION CODE ************************
     * returns a dummy object's array                                       *
     *                                                                      *
     * @param listener is the listener for the correct answer               *
     * @param errorListener is the listener for the error response          *
     ************************************************************************
     public static GsonGetRequest<ArrayList<DummyObject>> getDummyObjectArray (
            Response.Listener<ArrayList<DummyObject>> listener,
            Response.ErrorListener errorListener
     )
     {
        final String url = "http://www.mocky.io/v2/5597d86a6344715505576725";

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(DummyObject.class, new DummyObjectDeserializer())
                .create();

        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<ArrayList<DummyObject>>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
     }
     ************************************************************************/
}
