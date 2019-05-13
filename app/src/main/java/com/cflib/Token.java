package com.cflib;

import android.widget.TextView;

import java.net.HttpURLConnection;

import static java.net.URLConnection.getDefaultAllowUserInteraction;

/**
 * Created by nando on 19/03/2018.
 */


public class Token extends android.os.AsyncTask <String, Void, String>
{

    String _url;
    String _recebido;
    Cabecalho[] _headers;
    boolean _metodoPOST;
    org.json.JSONObject _jsonObject=null;

    HttpURLConnection httpURLConnection = null;

    TextView _saida;
    java.io.BufferedOutputStream outputStream ;//  =new java.io.BufferedInputStream( con.getInputStream());
    java.io.BufferedInputStream inputStream;//  =new java.io.BufferedOutputStream( con.getOutputStream());

    public Token()
    {

    }

    public void setMetodoPOST(boolean valor)
    {
        _metodoPOST = valor;
    }
    public void setHeaders(Cabecalho[] valor)
    {
        _headers = valor;
    }

    public Cabecalho[] getHeaders()
    {
        return _headers;
    }

    public void setSaida(TextView textViewSaida)
    {
        _saida = textViewSaida;
    }

    @Override
    protected String  doInBackground(String... params)
    {
        String retorno="";

        //AbrirConexao(true, true  );
        if(Enviar())
        {
            if(Receber())
            {
                retorno = "R2: " + _recebido;
            }

        }

        return retorno;
    }

    @Override
    protected void onPostExecute(String result)
    {

        String resultado = result;
        _saida.setText("R: " + result);
    }

    public String  getRecebido()
    {
        return _recebido;
    }

    public void setJson(org.json.JSONObject valor)
    {
        _jsonObject =valor;
    }

    public org.json.JSONObject getJson()
    {
        return _jsonObject;
    }

    public String getUrl()
    {
        return _url;
    }

    public void setUrl(String valor)
    {
        _url = valor;
    }

    HttpURLConnection AbrirConexao(boolean json)
    {
        try
        {
            java.net.URL url = new java.net.URL(_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            if(_metodoPOST)
            {
                httpURLConnection.setRequestMethod("POST");
            }
            if(json)
            {
                httpURLConnection.setRequestProperty("Content-Type", "application/json;");
            }

            for(int i=0; i <= _headers.length-1; i++ )
            {
                httpURLConnection.setRequestProperty(_headers[i].nome, _headers[i].valor);
            }

            if(_metodoPOST)
            {
                httpURLConnection.setDoOutput(true);
                outputStream  =new java.io.BufferedOutputStream( httpURLConnection.getOutputStream());
            }
            httpURLConnection.setDoInput(true);

            //inputStream  =new java.io.BufferedInputStream( httpURLConnection.getInputStream());

            return httpURLConnection;
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
        }
    }

    public boolean Enviar()
    {
        try
        {
            this.AbrirConexao(true);
            if(_metodoPOST)
            {
                outputStream.write(_jsonObject.toString().getBytes("utf-8"));
                outputStream.flush();

            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
        finally
        {
        }
    }

    public boolean Receber()
    {
        String s="";
        try
        {
            char c;
            int b;
            inputStream  =new java.io.BufferedInputStream( httpURLConnection.getInputStream());
            while((b=inputStream.read()) != -1)
            {
                c = (char)b;
                s += c;

            }
            _recebido=s;
            return  true;

        }
        catch(Exception e)
        {
            return false;
        }

        finally
        {
        }


    }

}


