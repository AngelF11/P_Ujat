package mx.edu.upc.proyectoujat2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import mx.edu.upc.proyectoujat2.helpers.Mysharedpreferences;

public class agrereporte extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private Button btn_hacerfoto, send, location;
    private ImageView img, img2, c1,c2;
    Spinner spiner;
    TextView textView, tdivision;
    private Uri output;
    EditText report;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

    int fotito = 0;
    private Mysharedpreferences sharedPreference;
    private static final int PICK_IMAGE = 100;
    Bitmap bm, bm2;
    Uri imageUri;
    String foto;
    File file;
    String cookiess, picturePath;
    WebView mWebView;
    Spinner divs;
    public class MyWebViewClient extends WebViewClient {}
    String url = "http://soporteujat.byethost7.com";
    @SuppressLint("SetJavaScriptEnabled")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrereporte);

        spiner = (Spinner) findViewById(R.id.spinner);
        textView=(TextView) findViewById(R.id.edit);
        send = (Button) findViewById(R.id.send);
        location = (Button) findViewById(R.id.buttonn);
        spiner.setOnItemSelectedListener(this);
        tdivision = (TextView) findViewById(R.id.tdivision);

        send.setOnClickListener(this);

        img = (ImageView) this.findViewById(R.id.imgMostrar);
        img2 = (ImageView) this.findViewById(R.id.imgMostrarr);
        c1 = (ImageView) this.findViewById(R.id.c_1);
        c2 = (ImageView) this.findViewById(R.id.c_2);
        c1.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.INVISIBLE);
        sharedPreference = new Mysharedpreferences(this);
        report = (EditText) this.findViewById(R.id.reporte);


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

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(R.mipmap.blanco);
                if(fotito==2){
                    fotito=3;
                }else{
                    fotito=0;
                }
                c1.setVisibility(View.INVISIBLE);
                bm = null;
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img2.setImageResource(R.mipmap.blanco);
                if(fotito==3){
                    fotito=0;
                }else{
                    fotito=1;
                }
                c2.setVisibility(View.INVISIBLE);
                bm2=null;
            }
        });

        btn_hacerfoto = (Button) this.findViewById(R.id.btn_camara);
        //Añadimos el Listener Boton
        btn_hacerfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(agrereporte.this);
                final View vista = getLayoutInflater().inflate(R.layout.selecamara,null);

                Button cam = (Button) vista.findViewById(R.id.checkBox);
                Button galeria = (Button) vista.findViewById(R.id.checkBox2);

                cam.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        getCamara();
                    }
                });

                galeria.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });
                mBuilder.setView(vista);
                AlertDialog dialogo = mBuilder.create();
                dialogo.show();
                    }
                });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(agrereporte.this);
                final View vista = getLayoutInflater().inflate(R.layout.ubicacion,null);

                divs = (Spinner) vista.findViewById(R.id.divs);

                divs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(!parent.getItemAtPosition(position).toString().equals("Seleccione la division")){
                            String[] midiv = parent.getItemAtPosition(position).toString().split("D");
                            String mindiv = midiv[1];
                            midiv = mindiv.split("");
                            mindiv = "D";
                            for(int i=0;i<midiv.length-2;i++){
                                mindiv += midiv[i];
                            }
                            tdivision.setText(mindiv.toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                mBuilder.setView(vista);
                AlertDialog dialogo = mBuilder.create();
                dialogo.show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!report.getText().toString().trim().equals("")){
                    serverUpdate();
                }else{
                    Toast.makeText(getApplication(),"La descripción está vacía",Toast.LENGTH_SHORT);
                }



            }
        });

    }

    private void getCamara(){

        if(sharedPreference.retriveCant().equals("")) {
            sharedPreference.cant("1");
            Log.e("Shared is: --->", sharedPreference.retriveCant());
            foto = Environment.getExternalStorageDirectory() + "/DCIM/Camera/foto1.jpg";
            file = new File(foto);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            output = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
            startActivityForResult(intent, 1);
        }else{
            String num = sharedPreference.retriveCant();
            int num_int = Integer.parseInt(num);
            num_int++;
            String nnum = String.valueOf(num_int);
            sharedPreference.cant(nnum);
            Log.e("Shared is: --->", sharedPreference.retriveCant());
            foto = Environment.getExternalStorageDirectory() + "/DCIM/Camera/foto"+nnum+".jpg";
            file = new File(foto);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            output = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ContentResolver cr=this.getContentResolver();
            Bitmap bit = null;
            try {
                bit = android.provider.MediaStore.Images.Media.getBitmap(cr, output);
                //orientation
                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(
                            file.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bit = Bitmap.createBitmap(bit , 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //imagen.setBackgroundResource(0);
            switch (fotito){
                case 0:
                    img.setImageBitmap(bit);
                    c1.setVisibility(View.VISIBLE);
                    fotito=1;
                    bm = bit;
                    break;
                case 1:
                    img2.setImageBitmap(bit);
                    c2.setVisibility(View.VISIBLE);
                    fotito=2;
                    bm2 = bit;
                    break;
                case 2:
                    img2.setImageBitmap(bit);
                    c2.setVisibility(View.VISIBLE);
                    fotito=2;
                    bm2 = bit;
                    break;
                case 3:
                    img.setImageBitmap(bit);
                    c1.setVisibility(View.VISIBLE);
                    fotito=2;
                    bm = bit;
                    break;
            }

        }
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imageUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Bitmap mbit = BitmapFactory.decodeFile(picturePath);
            cursor.close();

            switch (fotito){
                case 0:
                    img.setImageURI(imageUri);
                    c1.setVisibility(View.VISIBLE);
                    fotito=1;
                    bm = mbit;
                    break;
                case 1:
                    img2.setImageURI(imageUri);
                    c2.setVisibility(View.VISIBLE);
                    fotito=2;
                    bm2 = mbit;
                    break;
                case 2:
                    img2.setImageURI(imageUri);
                    c2.setVisibility(View.VISIBLE);
                    fotito=2;
                    bm2 = mbit;
                    break;
                case 3:
                    img.setImageURI(imageUri);
                    c1.setVisibility(View.VISIBLE);
                    fotito=2;
                    bm = mbit;
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
        }
    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public String postImage(){

        if(bm!=null){
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 15, bao);
            byte[] ba = bao.toByteArray();
            String ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
            Log.e("base64", "-----" + ba1);
        }

        if(bm2!=null){
            ByteArrayOutputStream bao2 = new ByteArrayOutputStream();
            bm2.compress(Bitmap.CompressFormat.JPEG, 15, bao2);
            byte[] baa = bao2.toByteArray();
            String ba2 = Base64.encodeToString(baa, Base64.NO_WRAP);
            nameValuePairs.add(new BasicNameValuePair("b2",ba2));
            nameValuePairs.add(new BasicNameValuePair("ImageName2", System.currentTimeMillis() + ".jpg"));
            Log.e("base64n", "-----" + ba2);

        }

        HttpClient httpclient;
        httpclient=new DefaultHttpClient();
        String resquest="";

        nameValuePairs.add(new BasicNameValuePair("text",report.getText().toString().trim()));
        try {
            HttpPost httppost;
            httppost= new HttpPost("http://soporteujat.byethost7.com/upload.php"); // Url del Servidor

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Añadimos nuestros datos
            httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 ");
            httppost.addHeader("Cookie", cookiess+"; expires=Thu, 31-Dec-37 23:55:55 GMT; path=/");
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            resquest = httpclient.execute(httppost, responseHandler);

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
        Log.e("Images","Estado: "+resquest);
        return resquest;
    }



    private void serverUpdate(){
        new ServerUpdate().execute();
    }

    class ServerUpdate extends AsyncTask<String,String,String> {

        ProgressDialog pDialog;
        @Override
        protected String doInBackground(String... arg0) {

            if(postImage().equals("Success"))
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(agrereporte.this, "Reporte enviado",
                                Toast.LENGTH_LONG).show();
                    }
                });
            else
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(agrereporte.this, "¡Error al enviar reporte!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(agrereporte.this);
            pDialog.setMessage("Envíando reporte, espere..." );
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(parent.getItemAtPosition(position).toString());



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}