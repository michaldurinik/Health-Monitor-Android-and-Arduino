package ie.dcu.healthmonitorv12;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

class EditActivity extends Activity {

    private EditText heightText;
    private EditText weightText;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_element);

        Button enterBtn = (Button) findViewById(R.id.enter_btn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                heightText = (EditText) findViewById(R.id.height_text);
                weightText = (EditText) findViewById(R.id.weight_text);
                resultView = (TextView) findViewById(R.id.bmi);

                int height = Integer.parseInt(heightText.getText().toString());
                int weight = Integer.parseInt(weightText.getText().toString());
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
        });
    }
}
