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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class updatetask extends Activity {
    // single task url
    private static final String url_task_details = "http://10.0.2.2/crescenttech/tasks/get_task_details.php";
    //url to create document
    private static final String url_create_pdf = "http://10.0.2.2/crescenttech/PDF/pdf.php";
    // url to update task
    private static final String url_update_task = "http://10.0.2.2/crescenttech/tasks/update_tasks.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TASK = "task";
    private static final String TAG_TASKID = "task_id";
    private static final String TAG_SUBJECT = "subject";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_RESOLUTION = "resolution";
    private static final String TAG_ITEM = "item";
    private static final String TAG_MAKE = "make";
    private static final String TAG_SERIALNO = "serial_no";
    private static final String TAG_QUANTITY = "quantity";
    private static final String TAG_MODEL = "model";
    private static final String TAG_WARRANTY_TYPE = "warranty_type";
    private static final String TAG_ITEM_TYPE = "type";
    private static final String TAG_UNDER_WARRANTY = "under_warranty";
    private static final String TAG_CLIENT_NAME = "client_name";
    private static final String TAG_PRIORITY = "priority";
    EditText tasksubject, taskdescription, taskitem,
            taskmake, taskserialno, taskmodel, taskunderwarranty, taskwarrantytype,
            taskresolution, taskquantity, taskpriority, taskitemtype, taskclientname;
    Button btnUpdate;
    Button btnResolve;
    String task_id;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatetask);

        // save button
        btnUpdate = (Button) findViewById(R.id.buttonupdatetask);
        //create pdf
        btnResolve = (Button) findViewById(R.id.buttonpdf);

        // getting task details from intent
        Intent i = getIntent();

        // getting task id (task_id) from intent
        task_id = i.getStringExtra(TAG_TASKID);

        // Getting complete task details in background thread
        new GetTaskDetails().execute();

        // save button click event
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update task
                new SaveTaskDetails().execute();
            }
        });
        btnResolve.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new CreatePdf().execute();
            }
        });


    }

    /**
     * Background Async Task to Get complete task details
     */
    class GetTaskDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(updatetask.this);
            pDialog.setMessage("Loading task details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting task details in background thread
         */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("task_id", task_id));

                        // getting task details by making HTTP request
                        // Note that task details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_task_details, "GET", params);

                        // check your log for json response
                        Log.d("Single task Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_TASK); // JSON Array

                            // get first product object from JSON Array
                            JSONObject task = productObj.getJSONObject(0);

                            // product with this task_id found
                            // Edit Text
                            tasksubject = (EditText) findViewById(R.id.tasksubjectupdate);
                            taskdescription = (EditText) findViewById(R.id.taskdescriptionupdate);
                            taskitem = (EditText) findViewById(R.id.taskitemupdate);
                            taskmake = (EditText) findViewById(R.id.taskmakeupdate);
                            taskserialno = (EditText) findViewById(R.id.taskserialnoupdate);
                            taskmodel = (EditText) findViewById(R.id.taskmodelupdate);
                            taskwarrantytype = (EditText) findViewById(R.id.taskwarrantytypeupdate);
                            taskitemtype = (EditText) findViewById(R.id.taskitemtypeupdate);
                            taskunderwarranty = (EditText) findViewById(R.id.taskunderwarrantyupdate);
                            taskclientname = (EditText) findViewById(R.id.taskclientupdate);
                            taskresolution = (EditText) findViewById(R.id.taskresolutionupdate);
                            taskquantity = (EditText) findViewById(R.id.taskquantityupdate);
                            taskpriority = (EditText) findViewById(R.id.taskpriorityupdate);
                            // display product data in EditText
                            tasksubject.setText(task.getString(TAG_SUBJECT));
                            taskdescription.setText(task.getString(TAG_DESCRIPTION));
                            taskitem.setText(task.getString(TAG_ITEM));
                            taskmake.setText(task.getString(TAG_MAKE));
                            taskserialno.setText(task.getString(TAG_SERIALNO));
                            taskmodel.setText(task.getString(TAG_MODEL));
                            taskwarrantytype.setText(task.getString(TAG_WARRANTY_TYPE));
                            taskitemtype.setText(task.getString(TAG_ITEM_TYPE));
                            taskunderwarranty.setText(task.getString(TAG_UNDER_WARRANTY));
                            taskclientname.setText(task.getString(TAG_CLIENT_NAME));
                            taskresolution.setText(task.getString(TAG_RESOLUTION));
                            taskquantity.setText(task.getString(TAG_QUANTITY));
                            taskpriority.setText(task.getString(TAG_PRIORITY));


                        } else {
                            // product with task_id not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }

    /**
     * Background Async Task to  Save product Details
     */
    class SaveTaskDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(updatetask.this);
            pDialog.setMessage("Saving task ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving task
         */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
            String subject = tasksubject.getText().toString();
            String description = taskdescription.getText().toString();
            String item = taskitem.getText().toString();
            String make = taskmake.getText().toString();
            String serialno = taskserialno.getText().toString();
            String model = taskmodel.getText().toString();
            String warrantytype = taskwarrantytype.getText().toString();
            String itemtype = taskitemtype.getText().toString();
            String underwarranty = taskunderwarranty.getText().toString();
            String clientname = taskclientname.getText().toString();
            String resolution = taskresolution.getText().toString();
            String quantity = taskquantity.getText().toString();
            String priority = taskpriority.getText().toString();


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_TASKID, task_id));
            params.add(new BasicNameValuePair(TAG_SUBJECT, subject));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));
            params.add(new BasicNameValuePair(TAG_ITEM, item));
            params.add(new BasicNameValuePair(TAG_MAKE, make));
            params.add(new BasicNameValuePair(TAG_SERIALNO, serialno));
            params.add(new BasicNameValuePair(TAG_MODEL, model));
            params.add(new BasicNameValuePair(TAG_WARRANTY_TYPE, warrantytype));
            params.add(new BasicNameValuePair(TAG_ITEM_TYPE, itemtype));
            params.add(new BasicNameValuePair(TAG_UNDER_WARRANTY, underwarranty));
            params.add(new BasicNameValuePair(TAG_CLIENT_NAME, clientname));
            params.add(new BasicNameValuePair(TAG_RESOLUTION, resolution));
            params.add(new BasicNameValuePair(TAG_QUANTITY, quantity));
            params.add(new BasicNameValuePair(TAG_PRIORITY, priority));

            // sending modified data through http request
            // Notice that update task url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_task,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about task update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update task
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
            // dismiss the dialog once task updated
            pDialog.dismiss();
        }
    }

    class CreatePdf extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(updatetask.this);
            pDialog.setMessage("Creating The Document. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Deleting product
         */
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("task_id", task_id));

                // getting client details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_create_pdf, "POST", params);

                // check your log for json response
                Log.d("Create PDF", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    Intent i = getIntent();

                    setResult(100, i);
                    finish();
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
            // dismiss the dialog once product deleted
            pDialog.dismiss();

        }

    }

}

