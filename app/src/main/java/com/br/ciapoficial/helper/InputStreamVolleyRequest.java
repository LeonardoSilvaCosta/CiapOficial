package com.br.ciapoficial.helper;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

public class InputStreamVolleyRequest extends Request<byte[]> {

    private final Response.Listener<byte[]> myListener;
    private Map<String, String> myParams;

    public Map<String, String> responseHeaders;

    public InputStreamVolleyRequest(int method, String myUrl, Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener,
                                    HashMap<String, String> params) {
        super(method, myUrl, errorListener);
        setShouldCache(false);
        myListener = listener;
        myParams = params;
    }

        @Override
        protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
            return myParams;
        };

        @Override
        protected void deliverResponse(byte[] response) {
            myListener.onResponse(response);
        }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success( response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
