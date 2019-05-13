package com.cflib;

import android.widget.TextView;

import java.net.HttpURLConnection;

import static java.net.URLConnection.getDefaultAllowUserInteraction;

/**
 * Created by nando on 13/04/2018.
 */

public class Rede extends android.os.AsyncTask <String, Void, String>
{

    String _url;
    String _recebido;
    Cabecalho[] _headers;
    String _mensagem;

    org.json.JSONObject _jsonObject=null;

    HttpURLConnection httpURLConnection = null;

    TextView _saida;
    java.io.BufferedOutputStream outputStream ;//  =new java.io.BufferedInputStream( con.getInputStream());
    java.io.BufferedInputStream inputStream;//  =new java.io.BufferedOutputStream( con.getOutputStream());

    public Rede()
    {

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


    public void setMensagem(String valor)
    {
        _mensagem = valor;
    }

    public String getMensagem()
    {
        return _mensagem;
    }
    @Override
    protected String  doInBackground(String... params)
    {
        String retorno="";

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

    HttpURLConnection AbrirConexao(boolean metodoPOST, boolean json)
    {
        try
        {
            java.net.URL url = new java.net.URL(_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            if(metodoPOST)
            {
                httpURLConnection.setRequestMethod("POST");
            }
            else
            {
                httpURLConnection.setRequestMethod("GET"); //
            }
            if(json)
            {
                httpURLConnection.setRequestProperty("Content-Type", "application/json;");
            }

            for(int i=0; i <= _headers.length-1; i++ )
            {
                httpURLConnection.setRequestProperty(_headers[i].nome, _headers[i].valor);
            }

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            outputStream  =new java.io.BufferedOutputStream( httpURLConnection.getOutputStream());

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

            this.AbrirConexao(false, false);
            //outputStream.write(_jsonObject.toString().getBytes("utf-8"));

            outputStream.write(_mensagem.toString().getBytes("utf-8"));

            outputStream.flush();
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
            if(inputStream != null)
            {
                while((b=inputStream.read()) != -1)
                {
                    c = (char)b;
                    s += c;

                }
                _recebido=s;

            }
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


