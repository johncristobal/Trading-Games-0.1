package nowoscmexico.com.tradinggames;

/*
* Splash activity....
* */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nowoscmexico.com.tradinggames.DataBase.modelBase;
import nowoscmexico.com.tradinggames.tuto.TutorialActivity;
import nowoscmexico.com.tradinggames.DataBase.DBaseMethods;
import nowoscmexico.com.tradinggames.R;

public class MainActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    public modelBase base;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        base = new modelBase(getApplicationContext(),Integer.parseInt(getResources().getString(R.string.dbversion)));
        DBaseMethods.base = base;

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String TAG = "Temp";
                if (user != null) {
                    // User is signed in
                    if (user.getUid().equals("WII3oNBHEgcLJNcrB7GBy2GhmNF3")){
                        Log.d(TAG, "onAuthStateChanged:signed_out:" + user.getUid());
                        //Intent i = new Intent(getApplicationContext(), TutorialActivity.class);
                        //startActivity(i);
                    }
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        /*try {
            //Thread.sleep(3000);
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
            String sesion = sharedPreferences.getString("sesion","null");
            //String tuto = sharedPreferences.getString("tutohecho","null");

            if(sesion.equals("1")){
                Intent i = new Intent(this, TrendsGames.class);
                startActivity(i);
            }else{
                Intent i = new Intent(this, TutorialActivity.class);
                startActivity(i);
            }
        }catch (Exception e){}*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
                String sesion = sharedPreferences.getString("sesion","null");
                //String tuto = sharedPreferences.getString("tutohecho","null");

                if(sesion.equals("1")){
                    //ya existe usuario, entonces lanzamos app...
                    Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
                    startActivity(i);
                } else {
                    //firebase to authenticate user
                    mAuth.signInWithEmailAndPassword("johnalexiscristobaljimenez6@gmail.com", "vera123")
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("Completo", "signInWithEmail:onComplete:" + task.isSuccessful());
                                Intent i = new Intent(getApplicationContext(), TutorialActivity.class);
                                startActivity(i);

                                //sharedPreferences.edit().putString("sesion","1").apply();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("Error", "signInWithEmail:failed", task.getException());
                                    Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                    });
                }
                // close this activity
            }
        }, SPLASH_TIME_OUT);

        /*try{

            WSTask ws = new WSTask();
            String res = ws.execute("hola").get();

            Log.w("Res",res);

        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

    public void showTuto(View v){

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);
        String sesion = sharedPreferences.getString("sesion","null");
        //String tuto = sharedPreferences.getString("tutohecho","null");
        sharedPreferences.edit().putString("sesion","1").apply();
        if(sesion.equals("1")){
            Intent i = new Intent(this, TrendsGames.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this, TutorialActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
