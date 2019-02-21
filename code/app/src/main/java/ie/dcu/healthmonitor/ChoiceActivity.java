package ie.dcu.healthmonitor;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ChoiceActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        ListView menu = (ListView) findViewById(R.id.menuList);
        ArrayAdapter<String> adapter = ArrayAdapter.createFromResource(this, R.layout.list_element, R.id.menu.);
        setListAdapter(adapter);

        //if(getIntent().hasExtra()){

        //}
        //setSettingListener();
        //setMonitorListener();
    }
/**
 *
 <Button
 android:id="@+id/settingsBtn"
 android:layout_width="182dp"
 android:layout_height="142dp"
 android:layout_marginStart="8dp"
 android:layout_marginTop="8dp"
 android:layout_marginEnd="8dp"
 android:layout_marginBottom="8dp"
 android:background="@android:color/holo_purple"
 android:elevation="5dp"
 android:text="@string/settingsBtn"
 android:textSize="24sp"
 app:layout_constraintBottom_toBottomOf="parent"
 app:layout_constraintEnd_toStartOf="@+id/monitorBtn"
 app:layout_constraintHorizontal_bias="0.5"
 app:layout_constraintStart_toStartOf="parent"
 app:layout_constraintTop_toTopOf="parent"
 tools:text="@string/settingsBtn" />

 <Button
 android:id="@+id/monitorBtn"
 android:layout_width="182dp"
 android:layout_height="142dp"
 android:layout_marginStart="8dp"
 android:layout_marginTop="8dp"
 android:layout_marginEnd="8dp"
 android:layout_marginBottom="8dp"
 android:backgroundTint="@android:color/holo_blue_light"
 android:elevation="5dp"
 android:text="@string/monitorBtn"
 android:textSize="24sp"
 app:layout_constraintBottom_toBottomOf="parent"
 app:layout_constraintEnd_toEndOf="parent"
 app:layout_constraintHorizontal_bias="0.96"
 app:layout_constraintStart_toEndOf="@+id/settingsBtn"
 app:layout_constraintTop_toTopOf="parent"
 tools:text="@string/monitorBtn" />

 </LinearLayout>
    protected void setSettingListener(){
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent settingsIntent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(settingsIntent);
            }
        });
    }

    protected void setMonitorListener(){
        Button monitorBtn = (Button) findViewById(R.id.monitorBtn);
        monitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monitorIntent = new Intent(getApplicationContext(), MonitorActivity.class);
                startActivity(monitorIntent);
            }
        });
    }
 */
}
