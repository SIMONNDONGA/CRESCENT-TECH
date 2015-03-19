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

public class AddClients extends Activity {
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    EditText inputcustomerName;
    EditText inputcontactperson;
    EditText inputcontacts;
    EditText inputlocation;
    EditText inputemail;
    EditText inputcounty;
    EditText inputtown;
    EditText inputbuilding;
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclient);

        // Edit Text
        inputcustomerName = (EditText) findViewById(R.id.customername);
        inputcontactperson = (EditText) findViewById(R.id.contactperson);
        inputcontacts = (EditText) findViewById(R.id.phoneno);
        inputlocation = (EditText) findViewById(R.id.location);
        inputemail = (EditText) findViewById(R.id.email);
        inputcounty = (EditText) findViewById(R.id.county);
        inputtown = (EditText) findViewById(R.id.town);
        inputbuilding = (EditText) findViewById(R.id.building);

        // Create button
        Button btnCreateclient = (Button) findViewById(R.id.buttonAddclient);

        // button click event
        btnCreateclient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewClient().execute();
            }
        });
    }

    /**
     * Background Async Task to Create new client
     */
    class CreateNewClient extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddClients.this);
            pDialog.setMessage("Adding New Client..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating client
         */
        protected String doInBackground(String... args) {
            String name = inputcustomerName.getText().toString();
            String person = inputcontactperson.getText().toString();
            String contacts = inputcontacts.getText().toString();
            String location = inputlocation.getText().toString();
            String email = inputemail.getText().toString();
            String county = inputcounty.getText().toString();
            String town = inputtown.getText().toString();
            String building = inputbuilding.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("client_name", name));
            params.add(new BasicNameValuePair("contact_person", person));
            params.add(new BasicNameValuePair("contacts", contacts));
            params.add(new BasicNameValuePair("location", location));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("county", county));
            params.add(new BasicNameValuePair("town", town));
            params.add(new BasicNameValuePair("building", building));


            // getting JSON Object
            // Note that create client url accepts POST method
            String url_create_client = "http://10.0.2.2/crescenttech/clients/create_client.php";
            JSONObject json = jsonParser.makeHttpRequest(url_create_client,
                    "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created client
                    Intent i = new Intent(getApplicationContext(), AllClients.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create client
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
