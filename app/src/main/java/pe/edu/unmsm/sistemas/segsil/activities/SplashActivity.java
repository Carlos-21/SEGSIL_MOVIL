package pe.edu.unmsm.sistemas.segsil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Timer;
import java.util.TimerTask;
import pe.edu.unmsm.sistemas.segsil.R;


public class SplashActivity extends AppCompatActivity {

    final int tiempoEspera = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
                Intent intent = new Intent(getApplicationContext(),BienvenidoActivity.class);
                startActivity(intent);
                finish();
        }else{
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, tiempoEspera);
        }
    }

}
