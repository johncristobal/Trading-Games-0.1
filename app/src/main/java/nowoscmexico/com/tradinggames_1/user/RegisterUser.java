package nowoscmexico.com.tradinggames_1.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import nowoscmexico.com.tradinggames_1.GalleryActivity;
import nowoscmexico.com.tradinggames_1.R;
import nowoscmexico.com.tradinggames_1.game.MatchActivity;

public class RegisterUser extends AppCompatActivity {

    public EditText nombre;
    public EditText correo;
    public EditText pass;
    public EditText confirma;
    public TextView pathAvatar;
    public TextView chooseLocation;
    public AppCompatSpinner horarios;

    public String lastActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent i = getIntent();
        lastActivity = i.getStringExtra("activity");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Vatiables
        nombre =(EditText)findViewById(R.id.editTextName);
        correo =(EditText)findViewById(R.id.editTextcorreo);
        pass =(EditText)findViewById(R.id.editTextpassword);
        confirma =(EditText)findViewById(R.id.editTextconfirma);

        //Textoview to show another antivities...
        //chooseLocation = (TextView)findViewById(R.id.textViewlocation);

        //Inicializas horariros
        //horarios = (AppCompatSpinner)findViewById(R.id.viewSpinnerHorarios);
        /*ArrayList<String> horas = new ArrayList<>();
        horas.add("Hora de comida");
        horas.add("Antes de las 12 pm");
        horas.add("Entre 12 y 1 pm");
        horas.add("Entre 1 y 2 pm");
        horas.add("Entre 2 y 3 pm");
        horas.add("Entre 3 y 4 pm");
        horas.add("Después de las 4 pm");

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,horas);
        horarios.setAdapter(adapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_view_g, menu);
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
        if (id == 16908332){
            //Toast.makeText(this,"Bavk to the last page. This upon tha activity",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Opencondciones
    public void openCondicionesPage(View v){

    }

    //validate uinfo and show map
    public void validateInfoandInit(View v){

        //uSE Sharedpereferences to save user sesion
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sesion","1");
        editor.commit();

        //When session opened, you have to show a differentes menu
        if(lastActivity.equals("trends")){
            //jUST SHOW TRENDS
            Intent i = new Intent(this, GalleryActivity.class);
            startActivity(i);
        }else if(lastActivity.equals("match")){
            //TLaunch activty to save wish list
            Intent i = new Intent(this, MatchActivity.class);
            startActivity(i);
        }else if(lastActivity.equals("contacto")){
            //launch activity to contact dueno
            Intent i = new Intent(this, GalleryActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this, GalleryActivity.class);
            startActivity(i);
        }

        //get info from layout and validate
        /*
        //Validate info and launch activity
        //Save it in WS :>
        String name = nombre.getText().toString();
        String mail = correo.getText().toString();
        String pass1 = pass.getText().toString();
        String pass2 = confirma.getText().toString();

        String modelo = "modelo";
        String ubicacion = "0,0";
        String hora = "horario";

        if(pass1.equals("")){

        }

        if(!pass1.equals(pass2)){
            Toast.makeText(this,"Coloque una constraseña.",Toast.LENGTH_LONG).show();
            return;
        }

        if(name.equals("")){
            Toast.makeText(this,"Coloque nombre del Godin",Toast.LENGTH_LONG).show();
            return;
        }

        if(mail.equals("")){
            correo.setFocusable(true);
            Toast.makeText(this,"Coloque correo",Toast.LENGTH_LONG).show();
            return;
        }
        if(hora.equals("Hora de comida")){
            Toast.makeText(this,"Seleccione un horario de comida",Toast.LENGTH_LONG).show();
            return;
        }

        //openDB to save Data
        try{
            //DBaseMethods.ThreadDBInsert insert = new DBaseMethods.ThreadDBInsert();
            //String res = insert.execute(modelBase.FeedEntryGodin.TABLE_NAME, name, mail,ubicacion, hora, pass1,path).get();
            String res = "1";
            if(res.equals("-1")){
                Toast.makeText(this,"Error al guardar informaciòn. \n Intente màs tarde.", Toast.LENGTH_SHORT).show();
            }else{
                //Si se guardo localmente,,,,intentammos guardar WS
                //Create task to send Data to DB --- WS
                //AsyncTaskWS task = new AsyncTaskWS();
                //String ws = task.execute(modelBase.FeedEntryGodin.TABLE_NAME, name, mail,ubicacion, hora, pass1,path,modelo).get();
                //Validar si ws ejecutado correctamente...
                String ws = "1";
                if(ws.equals("error")){
                    Toast.makeText(this,"Error al guardar informaciòn. \n Intente màs tarde.", Toast.LENGTH_SHORT).show();
                }else{
                    //Guardo id de Fonda en un shared preferenes par aposterior uso
                    //Aqui no es taaaan nvecesario ya que solo serà un unico godin registraod
                    //SharedPreferences shared = getSharedPreferences("FKFonda",MODE_PRIVATE);
                    //shared.edit().putString("idFonda",res).apply();

                    //Vmaos al menu godin y veamos fondas....woioooooo"
                    Intent intent = new Intent(this,GalleryActivity.class);
                    startActivity(intent);
                }
            }
        }catch(Exception e){}*/
        //Create JSon with Data
        //Send json to WS
        //WS get json and save data to DB server
    }
}
