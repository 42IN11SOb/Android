package com.avans.in11sob.pep_android.Utilities;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by Mark on 24-5-2016.
 */
public class GsonPostRequest<T> extends JsonRequest<T> {

    private final Gson gson;
    private final Type type;
    private final Response.Listener<T> listener;


    public GsonPostRequest(String url, String body, Type type, Gson gson, Response.Listener<T> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, url, body, listener, errorListener);

        this.gson = gson;
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(T response)
    {
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
     * An example call to demonstrate how to do a Volley POST call          *
     * and parse the response with Gson.                                    *
     *                                                                      *
     * @param listener is the listener for the success response             *
     * @param errorListener is the listener for the error response          *
     ************************************************************************
     public static GsonPostRequest getDummyObjectArrayWithPost
     (
            Response.Listener<DummyObject> listener,
            Response.ErrorListener errorListener
     )
     {
        final String url = "http://PostApiEndpoint";
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(DummyObject.class, new DummyObjectDeserializer())
                .create();

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Ficus");
        jsonObject.addProperty("surname", "Kirkpatrick");

        final JsonArray squareGuys = new JsonArray();
        final JsonObject dev1 = new JsonObject();
        final JsonObject dev2 = new JsonObject();
        dev1.addProperty("name", "Jake Wharton");
        dev2.addProperty("name", "Jesse Wilson");
        squareGuys.add(dev1);
        squareGuys.add(dev2);

        jsonObject.add("squareGuys", squareGuys);

        return new GsonPostRequest<>
                (
                        url,
                        jsonObject.toString(),
                        new TypeToken<DummyObject>()
                        {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );
     }
     ************************************************************************/
}
