package ie.dcu.healthmonitor;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends ListActivity {
	
    private ArrayAdapter<CharSequence> adapter;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        adapter = ArrayAdapter.createFromResource(this, R.array.settings_entries, R.layout.list_element_label);
        setListAdapter(adapter);
    }
    @Override
    public void onListItemClick(ListView listView, View clickedView, int position, long id){
        super.onListItemClick(listView, clickedView, position, id);
        TextView tv = (TextView) clickedView;
        String txt = (String) tv.getText();
        String clickText = "List Item " + txt + " was clicked!";
        toast = Toast.makeText(getBaseContext(), clickText, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        if(txt.equals("Height m")|txt.equals("Weight kg")|txt.equals("BMI")){
            toast.show();
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
        }else if(txt.equals("Emergency Contact No.")){
            toast.show();
        }else if(txt.equals("Avg. Heart Rate")){
            toast.show();
        }else if(txt.equals("Your Location")){
            toast.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
