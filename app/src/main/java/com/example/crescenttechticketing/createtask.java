package com.example.crescenttechticketing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class createtask extends Activity {


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    // url to create new product
    private static String url_create_task = "http://10.0.2.2/crescenttech/tasks/create_task.php";
    JSONParser jsonParser = new JSONParser();
    EditText tasksubject, taskdescription, taskitem,
            taskmake, taskserialno, taskmodel, taskunderwarranty, taskwarrantytype,
            taskresolution, taskquantity, taskpriority, taskitemtype, taskclientname;
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createtask);

        // Edit Text
        tasksubject = (EditText) findViewById(R.id.tasksubject);
        taskclientname = (EditText) findViewById(R.id.taskclient);
        taskdescription = (EditText) findViewById(R.id.taskdescription);
        taskitem = (EditText) findViewById(R.id.taskitem);
        taskmake = (EditText) findViewById(R.id.taskmake);
        taskserialno = (EditText) findViewById(R.id.taskserialno);
        taskmodel = (EditText) findViewById(R.id.taskmodel);
        taskunderwarranty = (EditText) findViewById(R.id.taskunderwarranty);
        taskwarrantytype = (EditText) findViewById(R.id.taskwarrantytype);
        taskresolution = (EditText) findViewById(R.id.taskresolution);
        taskquantity = (EditText) findViewById(R.id.taskquantity);
        taskpriority = (EditText) findViewById(R.id.taskpriority);
        taskitemtype = (EditText) findViewById(R.id.taskitemtype);

        // Create button
        Button btnCreateTask = (Button) findViewById(R.id.buttonCreate);

        // button click event
        btnCreateTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });
    }

    /**
     * Background Async Task to Create new task
     */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(createtask.this);
            pDialog.setMessage("Creating Job card. please wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating task
         */
        protected String doInBackground(String... args) {
            String client_name = taskclientname.getText().toString();
            String subject = tasksubject.getText().toString();
            String description = taskdescription.getText().toString();
            String item = taskitem.getText().toString();
            String make = taskmake.getText().toString();
            String serial_no = taskserialno.getText().toString();
            String model = taskmodel.getText().toString();
            String under_warranty = taskunderwarranty.getText().toString();
            String warranty_type = taskwarrantytype.getText().toString();
            String resolution = taskresolution.getText().toString();
            String quantity = taskquantity.getText().toString();
            String priority = taskpriority.getText().toString();
            String itemtype = taskitemtype.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_name", client_name));
            params.add(new BasicNameValuePair("subject", subject));
            params.add(new BasicNameValuePair("description", description));
            params.add(new BasicNameValuePair("item", item));
            params.add(new BasicNameValuePair("make", make));
            params.add(new BasicNameValuePair("serial_no", serial_no));
            params.add(new BasicNameValuePair("model", model));
            params.add(new BasicNameValuePair("warranty_type", warranty_type));
            params.add(new BasicNameValuePair("under_warranty", under_warranty));
            //params.add(new BasicNameValuePair("Cname", client));
            //params.add(new BasicNameValuePair("category", category));
            params.add(new BasicNameValuePair("resolution", resolution));
            params.add(new BasicNameValuePair("quantity", quantity));
            params.add(new BasicNameValuePair("priority", priority));
            params.add(new BasicNameValuePair("type", itemtype));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_task,
                    "POST", params);

            // check log cat from response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), Alltasks.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}
