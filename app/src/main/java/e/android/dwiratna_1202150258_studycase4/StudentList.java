package e.android.dwiratna_1202150258_studycase4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;


import java.util.ArrayList;

public class StudentList extends AppCompatActivity {

    private ListView gListView;
    private ProgressBar gProgressBar;
    private String [] gUsers= {
            "Acil",
            "Sisi",
            "Gendut",
            "Unyil",
            "Keling",
            "Cancan",
            "Masiqbal",
            "Masoren",

    };

    private ItemListView itemListView;
    private Button h;

    FragmentName fragment;

    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        gProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        gListView = (ListView) findViewById(R.id.listView);
        h = (Button) findViewById(R.id.list);

        gListView.setVisibility(View.GONE);


        gListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));


        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListView = new ItemListView(activity);
                itemListView.execute();
                // fragment.beginTask();
            }
        });

        if (savedInstanceState == null){
            fragment = new FragmentName();
            getSupportFragmentManager().beginTransaction().add(fragment, "task").commit();
        }else{ //activity created for subscquent time
            fragment = (FragmentName) getSupportFragmentManager().findFragmentByTag("task");
        }

        if (fragment != null){
            if (fragment.itemListView != null && fragment.itemListView.getStatus() == AsyncTask.Status.RUNNING){
                // progressBar.setVisibility(View.VISIBLE);
            }
        }

    }


    class ItemListView  extends AsyncTask<Void, String, Void> {

        private Activity activity;

        public ItemListView(Activity activity) {
            this.activity = activity;
        }

        private ArrayAdapter<String> mAdapter;
        private int counter = 1;
        ProgressDialog mProgressDialog = new ProgressDialog(StudentList.this);

        public void onAttach(Activity activity) {
            this.activity = activity;
        }

        public void onDetach() {
            activity = null;
        }

        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) gListView.getAdapter(); //casting suggestion

            //for progress dialog
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setMessage("Please wait....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgress(0);

            //this will handle cacle asynctack when click cancle button
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    itemListView.cancel(true);
                    gProgressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            for (String item : gUsers){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    itemListView.cancel(true);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);

            Integer current_status = (int) ((counter/(float)gUsers.length)*100);
            gProgressBar.setProgress(current_status);

            //set progress only working for horizontal loading
            mProgressDialog.setProgress(current_status);

            //set message will not working when using horizontal loading
            mProgressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //hide progreebar
            gProgressBar.setVisibility(View.GONE);

            //remove progress dialog
            mProgressDialog.dismiss();
            gListView.setVisibility(View.VISIBLE);

            StudentList.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }
}
