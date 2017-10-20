package mx.edu.upc.proyectoujat2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button2;
    Button button1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=(Button) findViewById(R.id.Button1);
        button2=(Button) findViewById(R.id.Button2);
        button2.setOnClickListener(this);
        button1.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Button2:
                Intent intent = new Intent(MainActivity.this,Registro_activity.class);
                intent.putExtra("cont","Segunda actividad");
                startActivity(intent);
                break;
            case R.id.Button1:
                Intent intent1 = new Intent(MainActivity.this,Manager_Tabs.class);
                intent1.putExtra("cont","Segunda actividad");
                startActivity(intent1);
                break;

        }

    }
}
