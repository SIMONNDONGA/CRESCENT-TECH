package com.example.crescenttechticketing;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllClientsTech extends ListActivity {
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_CLIENTS = "clients";
    private static final String TAG_CLIENT_ID = "client_id";
    private static final String TAG_NAME = "client_name";
    private static final String TAG_PERSON = "contact_person";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_CONTACTS = "contacts";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_COUNTY = "county";
    private static final String TAG_TOWN = "town";
    private static final String TAG_BUILDING = "building";
    // url to get all products list
    private static String url_all_clients = "http://10.0.2.2/crescenttech/clients/get_all_clients.php";
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> clientsList;
    // products JSONArray
    JSONArray clients = null;
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_clients);
        //Hashmap for ListView
        clientsList = new ArrayList<HashMap<String, String>>();

        // Loading client in Background Thread
        new LoadAllClients().execute();

        // Get listview


    }

    // Response from Edit client Activity


    /**
     * Background Async Task to Load all clients by making HTTP Request
     */
    class LoadAllClients extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllClientsTech.this);
            pDialog.setMessage("Checking Clients. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All clients from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_clients, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Clients: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of clients
                    clients = json.getJSONArray(TAG_CLIENTS);


                    // looping through All clients
                    for (int i = 0; i < clients.length(); i++) {
                        JSONObject c = clients.getJSONObject(i);

                        // Storing each json item in variable
                        String client_id = c.getString(TAG_CLIENT_ID);
                        String name = c.getString(TAG_NAME);
                        String person = c.getString(TAG_PERSON);
                        String contacts = c.getString(TAG_CONTACTS);
                        String location = c.getString(TAG_LOCATION);
                        String county = c.getString(TAG_COUNTY);
                        String town = c.getString(TAG_TOWN);
                        String building = c.getString(TAG_BUILDING);
                        String email = c.getString(TAG_EMAIL);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_CLIENT_ID, client_id);
                        map.put(TAG_NAME, name);
                        map.put(TAG_PERSON, person);
                        map.put(TAG_CONTACTS, contacts);
                        map.put(TAG_LOCATION, location);
                        map.put(TAG_COUNTY, county);
                        map.put(TAG_TOWN, town);
                        map.put(TAG_BUILDING, building);
                        map.put(TAG_EMAIL, email);

                        // adding HashList to ArrayList
                        clientsList.add(map);
                    }
                } else {
                    // no clients found
                    // Launch Add New clients Activity
                    Intent i = new Intent(getApplicationContext(),
                            AddClients.class);
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
            // dismiss the dialog after getting all clients
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AllClientsTech.this, clientsList,
                            R.layout.list_clients_tech, new String[]{TAG_CLIENT_ID,
                            TAG_NAME, TAG_PERSON, TAG_CONTACTS, TAG_LOCATION, TAG_COUNTY, TAG_TOWN, TAG_BUILDING, TAG_EMAIL},
                            new int[]{R.id.client_id_tech, R.id.client_name_tech, R.id.client_contactperson_tech, R.id.client_number_tech, R.id.location, R.id.county, R.id.client_town_tech, R.id.client_building_tech, R.id.client_email_tech});
                    // updating listview
                    setListAdapter(adapter);
                }
            });


        }
    }
}


