package com.example.marcosportatil.horarioandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.marcosportatil.horarioandroid.Prefencias.PreferencesActivity;

import java.util.Calendar;
import java.util.HashMap;

import com.example.marcosportatil.horarioandroid.Bd.AsignaturaColumnas;
import com.example.marcosportatil.horarioandroid.Bd.HorarioDbHelper;

public class MainActivity extends AppCompatActivity {


    private TextView txtResultado;

    private HashMap<String, String> miMapa;


    Calendar c = Calendar.getInstance();
    int dia = c.get(Calendar.DAY_OF_WEEK);
    String hora = "'" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + "'";

    private SQLiteDatabase db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                iniciarPreferences();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Obtenemos las referencias a los controles

        txtResultado = (TextView) findViewById(R.id.txtResultado);

        miMapa = new HashMap<>();
        crearMapa();


        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        HorarioDbHelper usdbh =
                new HorarioDbHelper(this);

        db = usdbh.getWritableDatabase();

        //Alternativa 1: m�todo rawQuery()
        Cursor c = db.rawQuery("SELECT * FROM " + AsignaturaColumnas.AsignaturaColumna.TABLE_NAME
                + " WHERE " + dia + " LIKE diaSemana AND " + hora + " BETWEEN hora_Inicio AND hora_Final AND (grupo = "
                +recuperarDatos() +" OR grupo = 0 )", null);


        //Recorremos los resultados para mostrarlos en pantalla
        txtResultado.setText("");
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                String cod = c.getString(0);
                String nom = c.getString(1);
                String codigo = c.getString(2);

                txtResultado.append("Ahora toca: "+miMapa.get(codigo) + "\n");
            } while (c.moveToNext());
        }


        //Cerramos la base de datos


    }

    private void iniciarPreferences() {
        startActivity(new Intent(this, PreferencesActivity.class));
    }


    public void crearMapa() {
        miMapa.put("7", "M7- Desenvolupament d'interficies (A201) José Antonio Leo");
        miMapa.put("11", "Tutoria  (A201) Josefa González");
        miMapa.put("3", "M3- Programació básica (A201)/(A208) Josefa Gónzalez");
        miMapa.put("10", "M10- Sistemes de gestió empresarial (A201) Marta Planas");
        miMapa.put("9", "M9- Programació de serveis i procesos A201)/(A208) Jorge Rubio");
        miMapa.put("8", "M8- Programació multimèdia i dispositius mòbils A201)/(A208) Lluis Perpinyà");
        miMapa.put("5", "M5- Entorns de desenvolupament / Bases de dades / Accés a dades A201)/(A208) Jorge Rubio");

    }

    public int recuperarDatos() {
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(this);
        String grupo = s.getString("lista_preferences", "A1");
        //return grupo.equals("A1") ? 1 : grupo.equals("A2") ? 2 : 0;
        if(grupo == "A1"){
            return 1;
        }
        else if (grupo == "A2"){
            return 2;
        }
        else
            return 0;
    }

}

