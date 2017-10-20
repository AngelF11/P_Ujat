package mx.edu.upc.proyectoujat2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bricaire on 18/09/2017.
 */

public class Registro_activity extends Activity implements View.OnClickListener {
    Button button;
    EditText e_email, nombre, paterno, materno;
    ImageView logo;
    String cookiess;
    WebView mWebView;
    public class MyWebViewClient extends WebViewClient {}
    String url = "http://soporteujat.byethost7.com";
    @SuppressLint("SetJavaScriptEnabled")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        e_email= (EditText) findViewById(R.id.correo_edittext);
        button=(Button) findViewById(R.id.Button3);
        button.setOnClickListener(this);
        logo =(ImageView) findViewById(R.id.imageView3);
        nombre=(EditText)findViewById(R.id.nombre_edittext);
        paterno=(EditText)findViewById(R.id.ap_paterno_edittext);
        materno=(EditText)findViewById(R.id.ap_materno_edittext);
        logo.setImageResource(R.mipmap.escudo_ujat);

        mWebView = new WebView(this);

        mWebView.setWebViewClient(new MyWebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                CookieSyncManager.getInstance().sync();
                URL url1 = null;
                try {
                    url1 = new URL(url);
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("C sharp corner user Ref " + url1.getRef());
                System.out.println(" C sharp corner user host " + url1.getHost());
                System.out.println("C sharp corner user authority " + url1.getAuthority());
                String cookies = CookieManager.getInstance().getCookie(url);
                cookiess=cookies;
                System.out.println("All COOKIES " + cookies);
                Log.e("Cookies -----------> ", cookies);
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Button3:
                if(!nombre.getText().toString().trim().equalsIgnoreCase("")||
                        !paterno.getText().toString().trim().equalsIgnoreCase("")||
                        !materno.getText().toString().trim().equalsIgnoreCase("")||
                        !e_email.getText().toString().trim().equalsIgnoreCase("")){
                    new Insertar(Registro_activity.this).execute();
                    break;
                }else{
                    Toast.makeText(Registro_activity.this, "Hay información por rellenar", Toast.LENGTH_LONG).show();}
                break;
        }
    }

    private String insertar(){

        HttpClient httpclient;
        String resquest="";
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://soporteujat.byethost7.com/send.php"); // Url del Servidor
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(5);
        nameValuePairs.add(new BasicNameValuePair("tag","register"));
        nameValuePairs.add(new BasicNameValuePair("destino",e_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("nombre",nombre.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("paterno",paterno.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("materno",materno.getText().toString()));

        try {
            //httpclient.execute(httppost);
            //return true;
            httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 ");
            httppost.addHeader("Cookie", cookiess+"; expires=Thu, 31-Dec-37 23:55:55 GMT; path=/");
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            resquest = httpclient.execute(httppost, responseHandler);
            //test
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("Email","Estado: "+resquest);
        return resquest;
    }

    //AsyncTask para insertar Personas
    class Insertar extends AsyncTask<String,String,String> {
        private Activity context;


        Insertar(Activity context) {
            this.context = context;
        }
        @Override
        protected String doInBackground (String...params){
            // TODO Auto-generated method stub
                if (!insertar().equals(""))
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(Registro_activity.this,MainActivity.class);
                            Toast.makeText(Registro_activity.this,"Revisa tu correo para confirmar la cuenta",Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            Toast.makeText(context, "No se pudo insertar los datos con éxito", Toast.LENGTH_LONG).show();
                        }
                    });
                return null;

        }
    }


}
