package com.cflib;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;

import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * Created by nando on 22/03/2018.
 */

public class SegCore
{
    byte[] BuscaChaveHash()
    {
        return new byte[] {(byte)0,(byte)0,(byte)0,(byte)1};
    }

    byte[] BuscaChaveCifraKey()
    {
        return new byte[] { (byte)1, (byte)3, (byte)5, (byte)7, (byte)11, (byte)13, (byte)17, (byte)19 };
    }

    byte[] BuscaChaveCifraIV()
    {
        return new byte[] {  (byte)21, (byte)23, (byte)27, (byte)29, (byte)31, (byte)35, (byte)37, (byte)41 };

        //return new byte[] {  (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0 };
    }

    public String fecharSenha(String senhaAberta)
    {
        String retorno = "";

        try
        {
            String algoritmo = "HmacMD5";

            javax.crypto.spec.SecretKeySpec chave = new javax.crypto.spec.SecretKeySpec( BuscaChaveHash(), algoritmo);

            javax.crypto.Mac mac = javax.crypto.Mac.getInstance(algoritmo);
            mac.init(chave);

            byte[] senhaFechadaBytes = mac.doFinal(senhaAberta.getBytes("ASCII"));

            retorno = android.util.Base64.encodeToString(senhaFechadaBytes, Base64.DEFAULT);

            return retorno;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String Cifrar(String frase)
    {
        String retorno = "";

        try
        {
            String algoritmo = "DES";

            javax.crypto.spec.SecretKeySpec chave = new javax.crypto.spec.SecretKeySpec( BuscaChaveCifraKey(), algoritmo);

            byte[] chaveIV = BuscaChaveCifraIV();

            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/CBC/NoPadding");
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE,chave, new IvParameterSpec(chaveIV)); //


            byte[] fraseByte = frase.getBytes("ASCII");
            if(fraseByte.length % 8 != 0)
            {
                byte[] fraseBitePadding = new byte[fraseByte.length + 8 - (fraseByte.length % 8)];
                System.arraycopy(fraseByte,0,fraseBitePadding,0,fraseByte.length);
                fraseByte = fraseBitePadding;
            }

            byte[] fraseSaidaBytes =  cipher.doFinal(fraseByte);

            retorno =  android.util.Base64.encodeToString(fraseSaidaBytes, Base64.NO_WRAP);

            return retorno;
        }
        catch (Exception e)
        {
            return null;
        }

    }
}
