package ie.dcu.healthmonitorv12;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    private ArrayAdapter<CharSequence> adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        adapter = ArrayAdapter.createFromResource(this, R.array.menu_entries, R.layout.list_element_label);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View clickedView, int position, long id){
        super.onListItemClick(listView, clickedView, position, id);
        TextView tv = (TextView) clickedView;
        String clickText = "List Item " + tv.getText() + " was clicked!";
        Toast.makeText(getBaseContext(), clickText, Toast.LENGTH_SHORT).show();
        if(tv.getText().equals("Personalise Settings")){
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }else{
            intent = new Intent(this, MonitorActivity.class);
            startActivity(intent);
        }
    }
}
