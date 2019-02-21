package ie.dcu.healthmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEntryListener();
    }

    protected void setEntryListener(){
        Button entryBtn = (Button) findViewById(R.id.welcomeBtn);
        entryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view){
                TextView listen = (TextView) findViewById(R.id.textView);
                listen.setText("Welcome");

                Intent choiceIntent = new Intent(getApplicationContext(), ChoiceActivity.class);
                startActivity(choiceIntent);
            }
        });
    }
}
