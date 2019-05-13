package nandolrs.com.br.consomeapi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.cflib.Cabecalho;
import com.cflib.SegCore;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inicio();
        EnviarJSON();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void inicio()
    {
        try
        {
            // enviar

            String _url =  "http://173.193.111.189:30875/api/consultar";

            java.net.URL url = new java.net.URL(_url);
            HttpURLConnection httpURLConnection = null;
            httpURLConnection = (HttpURLConnection) url.openConnection();


            httpURLConnection.setRequestProperty("Content-Type", "application/json;");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // receber

            String s="";

            char c;
            int b;
            java.io.BufferedInputStream inputStream;//  =new java.io.BufferedOutputStream( con.getOutputStream());
            String _recebido;

            inputStream  =new java.io.BufferedInputStream( httpURLConnection.getInputStream());
            while((b=inputStream.read()) != -1)
            {
                c = (char)b;
                s += c;

            }
            _recebido=s;


        }
        catch (Exception e)
        {
            String erro = e.getMessage();
        }
        finally
        {
        }
    }

    void EnviarJSON()
    {
        try
        {



            String  inputUsuario  = "nandolrs@hotmail.com";
            String  inputSenha  = "321";
            String  inputMensagem = "mensagem ok";

            //inputMensagem.setText("Aguarde ....");

            //

            SegCore segCore = new SegCore();

            String usuarioAberto = inputUsuario;
            String usuarioFechado = segCore.Cifrar(usuarioAberto);

            String senhaAberta = inputSenha;
            String senhaFechada = segCore.Cifrar(senhaAberta);;

            org.json.JSONObject credenciais = new org.json.JSONObject();
            credenciais.put("nome",usuarioFechado);
            credenciais.put("senha",senhaFechada );

            String url = "http://173.193.111.189:30875/api/consultar";

            com.cflib.Token token = new com.cflib.Token();
            token.setUrl(url);

            Cabecalho cabecalho = new Cabecalho();
            cabecalho.nome="w3seguranca.credenciais";
            cabecalho.valor = usuarioFechado + "." + senhaFechada + "." + usuarioFechado + "." + senhaFechada;

            Cabecalho[] cabecalhos = new Cabecalho[1];
            cabecalhos[0] = cabecalho;

            token.setHeaders(cabecalhos);

            token.setJson(credenciais);
           // token.setSaida(inputMensagem);
            token.execute("", null, "");
        }
        catch(Exception e)
        {

        }
        finally
        {

        }
    }

}
