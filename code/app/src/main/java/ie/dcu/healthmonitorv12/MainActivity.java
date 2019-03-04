package ie.dcu.healthmonitorv12;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends ListActivity implements  EasyPermissions.PermissionCallbacks {

    private ArrayAdapter<CharSequence> adapter;
    private Intent intent;
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.BLUETOOTH,
                                    Manifest.permission.SEND_SMS,
                                    Manifest.permission.RECEIVE_BOOT_COMPLETED};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        getPermissions();

    }

    @AfterPermissionGranted(123)
    private void getPermissions(){
        if(EasyPermissions.hasPermissions(this, permissions)){
            Toast.makeText(this, R.string.welcome, Toast.LENGTH_SHORT);
            adapter = ArrayAdapter.createFromResource(this, R.array.menu_entries, R.layout.list_element_label);
            setListAdapter(adapter);
        }else{
            EasyPermissions.requestPermissions(this, "These permissions are needed for you to get the full benefit of this application", 123, permissions);
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }
}
