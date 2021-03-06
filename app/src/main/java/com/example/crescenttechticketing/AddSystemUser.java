package com.example.crescenttechticketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class AddSystemUser extends Activity {
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    //private static String KEY_ERROR = "error";
    //private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    //private static String KEY_CONTACTS = "contacts";
    //private static String KEY_DESIGNATION = "designation";
    private static String KEY_CREATED_AT = "created_at";
    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFullName;
    EditText inputcontacts;
    EditText inputdesignation;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);

        // Importing all assets like buttons, text fields
        inputFullName = (EditText) findViewById(R.id.usersname);
        inputEmail = (EditText) findViewById(R.id.email2);
        inputPassword = (EditText) findViewById(R.id.password2);
        //inputcontacts = (EditText) findViewById(R.id.phoneno1);
        //inputdesignation = (EditText) findViewById(R.id.designation);
        btnRegister = (Button) findViewById(R.id.buttonAdd2);
        //btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                //String contacts = inputcontacts.getText().toString();
                //String designation = inputdesignation.getText().toString();
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(name, email, password);

                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        registerErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if (Integer.parseInt(res) == 1) {
                            // user successfully registred
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");

                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                            // Launch Dashboard Screen
                            Intent dashboard = new Intent(getApplicationContext(), AddSystemUser.class);
                            // Close all views before launching Dashboard
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                            // Close Registration Screen
                            finish();
                        } else {
                            // Error in registration
                            registerErrorMsg.setText("Error occured in registration");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
