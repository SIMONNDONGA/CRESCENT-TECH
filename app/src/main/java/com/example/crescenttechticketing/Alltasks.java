package com.example.crescenttechticketing;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Alltasks extends ListActivity {
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TASK = "task";
    private static final String TAG_TASKID = "task_id";
    private static final String TAG_CLIENT_NAME = "client_name";
    //private static final String TAG_SUBJECT = "subject";
    //private static final String TAG_ITEM = "item";
    //private static final String TAG_MAKE = "make";
    //private static final String TAG_SERIAL_NO = "serial_no";
    //private static final String TAG_TYPE = "type";
    //private static final String TAG_DESCRIPTION ="description";
    //private static final String TAG_RESOLUTION ="resolution";
    //private static final String TAG_DATE_OF_START = "date_of_start";
    //private static final String TAG_DATE_OF_COMPLETION ="date_of_completion";
    private static final String TAG_PRIORITY = "priority";
    // url to get all products list
    private static String url_all_tasks = "http://10.0.2.2/crescenttech/tasks/get_all_tasks.php";
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> tasksList;
    // products JSONArray
    JSONArray tasks = null;
    //private static final String TAG_QUANTITY ="quantity";
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tasks);


        // Hashmap for ListView
        tasksList = new ArrayList<HashMap<String, String>>();

        // Loading tasks in Background Thread
        new LoadAlltasks().execute();

        // Get listview
        ListView lv = getListView();

        // on seleting single task
        // launching Edit task Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String task_id = ((TextView) view.findViewById(R.id.task_id)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        updatetask.class);

                // sending pid to next activity
                in.putExtra(TAG_TASKID, task_id);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }

    // Response from update task Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all tasks by making HTTP Request
     */
    class LoadAlltasks extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Alltasks.this);
            pDialog.setMessage("Loading tasks. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All tasks from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_tasks, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Tasks: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Tasks
                    tasks = json.getJSONArray(TAG_TASK);

                    // looping through All Tasks
                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject c = tasks.getJSONObject(i);

                        // Storing each json item in variable
                        String task_id = c.getString(TAG_TASKID);
                        String name = c.getString(TAG_CLIENT_NAME);
                        //String subject = c.getString(TAG_SUBJECT);
                        //String description = c.getString(TAG_DESCRIPTION);
                        //String resolution = c.getString(TAG_RESOLUTION);
                        //String date_of_start =c.getString(TAG_DATE_OF_START);
                        //String date_of_completion = c.getString(TAG_DATE_OF_COMPLETION);
                        String priority = c.getString(TAG_PRIORITY);
                        //String quantity = c.getString(TAG_QUANTITY);
                        //String make = c.getString(TAG_MAKE);
                        //String type = c.getString(TAG_TYPE);
                        //String item = c.getString(TAG_ITEM);
                        //String serial_no = c.getString(TAG_SERIAL_NO);


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_TASKID, task_id);
                        map.put(TAG_CLIENT_NAME, name);
                        //map.put(TAG_SUBJECT, subject);
                        //map.put(TAG_DESCRIPTION, description);
                        //map.put(TAG_RESOLUTION, resolution);
                        //map.put(TAG_DATE_OF_START, date_of_start);
                        //map.put(TAG_DATE_OF_COMPLETION, date_of_completion);
                        map.put(TAG_PRIORITY, priority);
                        //map.put(TAG_QUANTITY, quantity);
                        //map.put(TAG_SERIAL_NO, serial_no);
                        //map.put(TAG_ITEM, item);
                        //map.put(TAG_TYPE, type);
                        //map.put(TAG_MAKE, make);


                        // adding HashList to ArrayList
                        tasksList.add(map);
                    }
                } else {
                    // no products found
                    // Launch create task Activity
                    Intent i = new Intent(getApplicationContext(),
                            createtask.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
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
            // dismiss the dialog after getting all tasks
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            Alltasks.this, tasksList,
                            R.layout.list_tasks, new String[]{TAG_TASKID, TAG_CLIENT_NAME, TAG_PRIORITY},
                            new int[]{R.id.task_id, R.id.task_name, R.id.task_priority});
                    // updating listview


                    setListAdapter(adapter);
                }
            });

        }

    }


}
