package hieunnph32561.fpoly.du_an_1_hieu.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import hieunnph32561.fpoly.du_an_1_hieu.R;

public class Manhinhcho extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhcho);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Manhinhcho.this, Dangnhap.class);
                startActivity(intent);

            }
        },2000);
    }
}