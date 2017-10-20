package mx.edu.upc.proyectoujat2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Bricaire on 18/09/2017.
 */

public class ViewPager_Adapter extends FragmentPagerAdapter {
    private int numeroDeSecciones;  //nº de secciones
    private Context contexto;       //contexto

    //recibimos en el constructor el gestor de fragmentos, el nº de secciones después de haber creado
    //las tabs y el contexto de aplicación
    public ViewPager_Adapter(FragmentManager fm, int numeroDeSecciones, Context contexto){
        super(fm);
        this.numeroDeSecciones=numeroDeSecciones;
        this.contexto=contexto;

    }

    @Override
    public Fragment getItem(int position) {

        // recibimos la posición por parametro y en función de ella devolvemos el Fragment correspondiente a esa sección.
        switch (position) {

            case 0: // siempre empieza desde 0

                return new Informe();
            case 1:

                return new Perfil();

            // si la posición recibida no se corresponde a ninguna sección
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numeroDeSecciones;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        // recibimos la posición por parametro y en función de ella devolvemos el titulo correspondiente.
        switch (position) {

            case 0: // siempre empieza desde 0

                return "REPORTES ";
            case 1:
                return "CONFIGURACIÓN";

            // si la posición recibida no se corresponde a ninguna sección
            default:
                return null;
        }

    }

}
