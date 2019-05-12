package com.example.lenovo.contactsapp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.Database;
import Model.Contact;
import Util.Constants;

public class MainActivity extends AppCompatActivity {

    private Button addContactDBButton;
    private EditText usernameEditText,passwordEditText;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private Database contactsDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        contactsDB=new Database(this);
      //  contactsDB.deleteAllContact();
        // TODO Check IF there are more one contact pass ContactsActivity
        if (contactsDB.getCountContact()>0){
            Intent passContactsActivity=new Intent(MainActivity.this,ContactsActivity.class);
            startActivity(passContactsActivity);
            finish();


        }


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                // TODO Popup AlertDialog ,add contact and pass ContactsActivity
                popupAlert();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    // Do when click + Button.For open popupAlert alertDialog
    public void popupAlert(){

        alertDialogBuilder=new AlertDialog.Builder(MainActivity.this);
        View v=getLayoutInflater().inflate(R.layout.add_contact_alert,null);
        alertDialogBuilder.setView(v);
        alertDialog=alertDialogBuilder.create();
        alertDialog.show();
        addContactDBButton=(Button) v.findViewById(R.id.registerButton);
        usernameEditText=(EditText)v.findViewById(R.id.usernameEditText);
        passwordEditText=(EditText)v.findViewById(R.id.passwordEditText);

        addContactDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!usernameEditText.getText().toString().isEmpty()&&!passwordEditText.getText().toString().isEmpty())
                addContactToDatabase(view);
                else
                    Toast.makeText(MainActivity.this,"Username or password is empty",Toast.LENGTH_SHORT).show();
            }
        });



    }


    /*

TODO save Contact to Database
TODO pass next activity(Contacts activity)
 */

    private void addContactToDatabase(View v) {
        String username=usernameEditText.getText().toString();
        String password=passwordEditText.getText().toString();

        Contact contact=new Contact(username,password);
        contactsDB.addContact(contact);


        Snackbar.make(v,"Contact is added",Snackbar.LENGTH_LONG).show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                Intent passContactsActivity=new Intent(MainActivity.this,ContactsActivity.class);
                startActivity(passContactsActivity);
                finish();
            }
        },1500);



    }

}
