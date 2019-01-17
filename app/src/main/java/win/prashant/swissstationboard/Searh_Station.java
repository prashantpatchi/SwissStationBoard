package win.prashant.swissstationboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Searh_Station extends AppCompatActivity {
    EditText editText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searh__station);

        editText = findViewById(R.id.editTextstationname);
        btn = findViewById(R.id.buttonsubmit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //String station = editText.getText().toString();

                //String train=String.valueOf(editText.getText().toString());
                Intent in = new Intent(Searh_Station.this,MainActivity.class);
                //send data one activity to different activity

                //in.putExtra("position",""+position);
                startActivity(in);

            }


        });
    }


}
