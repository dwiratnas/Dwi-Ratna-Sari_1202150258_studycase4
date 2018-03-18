package e.android.dwiratna_1202150258_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainHome extends AppCompatActivity {

    Button image_a,list_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        image_a = (Button) findViewById(R.id.image);
        list_b = (Button) findViewById(R.id.list);


        image_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHome.this,SearchImage.class);
                startActivity(intent);
            }
        });

        list_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainHome.this,StudentList.class);
                startActivity(intent);
            }
        });

    }
}
