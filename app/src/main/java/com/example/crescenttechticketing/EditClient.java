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

public class EditClient extends Activity {
    // single client url
    private static final String url_clients_detials = "http://10.0.2.2/crescenttech/clients/get_clients_details.php";
    // url to update client
    private static final String url_update_clients = "http://10.0.2.2/crescenttech/clients/update_client.php";
    // url to delete client
    private static final String url_delete_clients = "http://10.0.2.2/crescenttech/clients/delete_client.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CLIENT = "client";
    private static final String TAG_CLIENT_ID = "client_id";
    private static final String TAG_NAME = "client_name";
    private static final String TAG_PERSON = "contact_person";
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_COUNTY = "county";
    private static final String TAG_TOWN = "town";
    private static final String TAG_BUILDING = "building";
    private static final String TAG_EMAIL = "email";
    EditText txtName;
    EditText txtperson;
    EditText txtcontacts;
    EditText txtlocation;
    EditText txtemail;
    EditText txtcounty;
    EditText txttown;
    EditText txtbuilding;
    EditText txtCreatedAt;
    Button btnSave;
    Button btnDelete;
    String client_id;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_client);

        // save button
        btnSave = (Button) findViewById(R.id.buttonEdit);
        btnDelete = (Button) findViewById(R.id.buttonDel);

        // getting client details from intent
        Intent i = getIntent();

        // getting client id (client_id) from intent
        client_id = i.getStringExtra(TAG_CLIENT_ID);

        // Getting complete client details in background thread
        new GetClientDetails().execute();

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update client
                new SaveClientDetails().execute();
            }
        });

        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting client in background thread
                new DeleteClient().execute();
            }
        });

    }

    /**
     * Background Async Task to Get complete client details
     */
    class GetClientDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditClient.this);
            pDialog.setMessage("Loading clients details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting client details in background thread
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
                        params.add(new BasicNameValuePair("client_id", client_id));

                        // getting client details by making HTTP request
                        // Note that client details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_clients_detials, "GET", params);

                        // check your log for json response
                        Log.d("Single client Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received client details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_CLIENT); // JSON Array

                            // get first client object from JSON Array
                            JSONObject client = productObj.getJSONObject(0);

                            // client with this client_id found
                            // Edit Text
                            txtName = (EditText) findViewById(R.id.customernameE);
                            txtperson = (EditText) findViewById(R.id.contactpersonE);
                            txtcontacts = (EditText) findViewById(R.id.phonenoE);
                            txtlocation = (EditText) findViewById(R.id.locationE);
                            txtcounty = (EditText) findViewById(R.id.countyE);
                            txttown = (EditText) findViewById(R.id.townE);
                            txtbuilding = (EditText) findViewById(R.id.buildingE);
                            txtemail = (EditText) findViewById(R.id.emailE);

                            // display client details in EditText
                            txtName.setText(client.getString(TAG_NAME));
                            txtperson.setText(client.getString(TAG_PERSON));
                            txtcontacts.setText(client.getString(TAG_CONTACTS));
                            txtlocation.setText(client.getString(TAG_LOCATION));
                            txtcounty.setText(client.getString(TAG_COUNTY));
                            txttown.setText(client.getString(TAG_TOWN));
                            txtbuilding.setText(client.getString(TAG_BUILDING));
                            txtemail.setText(client.getString(TAG_EMAIL));

                        } else {
                            // client with client_id not found
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
     * Background Async Task to  Save client Details
     */
    class SaveClientDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditClient.this);
            pDialog.setMessage("Saving client's details ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving client
         */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
            String name = txtName.getText().toString();
            String person = txtperson.getText().toString();
            String contacts = txtcontacts.getText().toString();
            String location = txtlocation.getText().toString();
            String county = txtcounty.getText().toString();
            String town = txttown.getText().toString();
            String building = txtbuilding.getText().toString();
            String email = txtemail.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_CLIENT_ID, client_id));
            params.add(new BasicNameValuePair(TAG_NAME, name));
            params.add(new BasicNameValuePair(TAG_PERSON, person));
            params.add(new BasicNameValuePair(TAG_CONTACTS, contacts));
            params.add(new BasicNameValuePair(TAG_LOCATION, location));
            params.add(new BasicNameValuePair(TAG_COUNTY, county));
            params.add(new BasicNameValuePair(TAG_TOWN, town));
            params.add(new BasicNameValuePair(TAG_BUILDING, building));
            params.add(new BasicNameValuePair(TAG_EMAIL, email));

            // sending modified data through http request
            // Notice that update client url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_clients,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update client
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
            // dismiss the dialog once product updated
            pDialog.dismiss();
        }
    }

    /**
     * **************************************************************
     * Background Async Task to Delete Product
     */
    class DeleteClient extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditClient.this);
            pDialog.setMessage("Deleting the Client...");
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
                params.add(new BasicNameValuePair("client_id", client_id));

                // getting client details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_clients, "POST", params);

                // check your log for json response
                Log.d("Delete Client", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
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