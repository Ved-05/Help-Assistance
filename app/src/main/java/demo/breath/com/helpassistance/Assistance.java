package demo.breath.com.helpassistance;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Assistance extends AppCompatActivity {

    //Image
    private ImageView imgContainer;
    private Button cancel, outOfEmg, yesButton, noButton;
    private TextView statusBar, response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance);

        int id = getIntent().getIntExtra("choosed", 0);

        imgContainer = (ImageView) findViewById(R.id.img_container);
        outOfEmg = (Button) findViewById(R.id.outOfEmergency);
        switch(id){
            case 0: imgContainer.setImageResource(getResources().getIdentifier("img_sos", "drawable",  getPackageName()));
                outOfEmg.setTextColor(getResources().getColor(R.color.fontColorPrimary));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_sos", "drawable",  getPackageName()));
                break;
            case 1: imgContainer.setImageResource(getResources().getIdentifier("img_accdnt", "drawable",  getPackageName()));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_accdnt", "drawable",  getPackageName()));
                break;
            case 2: imgContainer.setImageResource(getResources().getIdentifier("img_theft", "drawable",  getPackageName()));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_theft", "drawable",  getPackageName()));
                break;
            case 3: imgContainer.setImageResource(getResources().getIdentifier("img_medical", "drawable",  getPackageName()));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_medical", "drawable",  getPackageName()));
                break;
            case 4: imgContainer.setImageResource(getResources().getIdentifier("img_terrorism", "drawable",  getPackageName()));
                outOfEmg.setBackgroundResource(getResources().getIdentifier("btn_asst_terrorism", "drawable",  getPackageName()));
                break;
        }

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        statusBar = (TextView) findViewById(R.id.status);
        response = (TextView) findViewById(R.id.response);

        outOfEmg = (Button) findViewById(R.id.outOfEmergency);
        yesButton = (Button) findViewById(R.id.yesBtn);
        noButton = (Button) findViewById(R.id.noBtn);

        outOfEmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusBar.setCompoundDrawables(null,null,null,null);
                statusBar.setText("If you exit, below details won't be shared.");
                cancel.setVisibility(View.GONE);
                outOfEmg.setVisibility(View.GONE);
                response.setVisibility(View.VISIBLE);
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusBar.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_if_9_3898370,0);
                statusBar.setText("Hold tight, dispatch is on the way.");
                cancel.setVisibility(View.VISIBLE);
                outOfEmg.setVisibility(View.VISIBLE);
                response.setVisibility(View.GONE);
                yesButton.setVisibility(View.GONE);
                noButton.setVisibility(View.GONE);
            }
        });
    }
}
