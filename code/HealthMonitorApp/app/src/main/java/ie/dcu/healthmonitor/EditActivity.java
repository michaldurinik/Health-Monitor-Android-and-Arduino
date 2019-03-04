package ie.dcu.healthmonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class EditActivity extends AppCompatActivity {

    private TextView heightLabel;
    private EditText heightText;
    private TextView weightLabel;
    private EditText weightText;
    private TextView resultView;
	private int height;
	private int weight;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button enterBtn = (Button) findViewById(R.id.enter_btn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                calcBmi();
            }
        });

    }

    private void calcBmi(){
        heightLabel = (TextView) findViewById(R.id.height_text_label);
        heightText = (EditText) findViewById(R.id.height_text);
        weightLabel = (TextView) findViewById(R.id.weight_text_label);
        weightText = (EditText) findViewById(R.id.weight_text);
        resultView = (TextView) findViewById(R.id.bmi);

        heightLabel.setText(heightText.getText().toString() + " cms");
        weightLabel.setText(weightText.getText().toString() + " kgs");
        height = Integer.parseInt(heightText.getText().toString());
        weight = Integer.parseInt(weightText.getText().toString());

        DecimalFormat value = new DecimalFormat("#.#");
        String result = value.format((weight / (Math.pow(height, 2)))*10000);
        String finalStr = "Body Mass Index\n" + result + "\n";
        if(((weight / (Math.pow(height, 2)))*10000) < 18.5)
            finalStr = finalStr + "Underweight";
        else if(((weight / (Math.pow(height, 2)))*10000) > 24.9)
            finalStr = finalStr + "Overweight";
        else
            finalStr = finalStr + "Good Ratio";
        resultView.setText(finalStr);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
