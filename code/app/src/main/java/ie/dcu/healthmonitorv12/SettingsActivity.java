package ie.dcu.healthmonitorv12;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

class SettingsActivity extends ListActivity {

    private ArrayAdapter<CharSequence> adapter;
    private Intent intent;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
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
        toast.show();
        if(txt.equals("Height m")|txt.equals("Weight kg")|txt.equals("BMI")){
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            intent = new Intent(this, EditActivity.class);
            startActivity(intent);
        }else if(txt.equals("Emergency Contact No.")){
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }else if(txt.equals("Avg. Heart Rate")){
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }else if(txt.equals("Your Location")){
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
}
