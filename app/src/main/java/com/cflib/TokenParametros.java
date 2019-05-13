package com.cflib;

/**
 * Created by nando on 19/03/2018.
 */

public class TokenParametros
{
    String _url;
    org.json.JSONObject _jsonObject;

    public void setUrl(String valor)
    {
        _url = valor;
    }

    public String getUrl()
    {
        return _url;
    }

    public void setJsonObject(org.json.JSONObject valor)
    {
        _jsonObject = valor;
    }

    public org.json.JSONObject getJsonObject()
    {
        return _jsonObject;
    }

}
