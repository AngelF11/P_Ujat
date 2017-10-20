package mx.edu.upc.proyectoujat2.helpers;

/**
 * Created by Bricaire on 26/09/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class Mysharedpreferences {

    private SharedPreferences prefs;

    private Context context;

    public Mysharedpreferences(Context context){
        this.context = context;
        prefs = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
    }


    public void cant(String total){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString("t_imagenes", total);
        edits.apply();
    }
    public String retriveCant(){return prefs.getString("t_imagenes","");}


    public void clearall(){
        SharedPreferences.Editor edits = prefs.edit();
        edits.clear();
        edits.apply();
    }
}

