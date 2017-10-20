package mx.edu.upc.proyectoujat2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Manager_Tabs extends AppCompatActivity {
    private ViewPager_Adapter pager_adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__tabs);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayoutPrincipal);

        // Añadimos las 2 tabs de las secciones.
        // Le damos modo "fixed" para que todas las tabs tengan el mismo tamaño. Tambien le asignamos una gravedad centrada.

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        viewPager = (ViewPager) findViewById(R.id.ViewPagerPrincipal);
        pager_adapter = new ViewPager_Adapter(getSupportFragmentManager(), tabLayout.getTabCount(),this);
        viewPager.setAdapter(pager_adapter);
        // Y por último, vinculamos el viewpager con el control de tabs para sincronizar ambos.
        tabLayout.setupWithViewPager(viewPager);
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.mmmn, menu);
        //return true;
    //}

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(Manager_tabs.this,"Cerrando sesión",Toast.LENGTH_LONG).show();
            sharedPreference.clearall();
            Intent i = new Intent(Manager_tabs.this,inicio.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


}
