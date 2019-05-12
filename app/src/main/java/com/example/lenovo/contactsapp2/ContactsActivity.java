package com.example.lenovo.contactsapp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.Adapter;
import Database.Database;
import Model.Contact;

public class ContactsActivity extends AppCompatActivity {
    private RecyclerView contactsRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<Contact>contacts;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private Button addContactDBButton;
    private EditText usernameEditText,passwordEditText;
    private Database contactsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                popupAlert();
            }
        });

        setupUI();


    }

    public void setupUI(){

        contactsDB=new Database(this);
        contacts=new ArrayList<>();
        contacts=contactsDB.getAllContact();
        adapter=new Adapter(ContactsActivity.this,contacts);
        contactsRecyclerView=(RecyclerView)findViewById(R.id.contactsRecylerView);
        contactsRecyclerView.setHasFixedSize(true);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(adapter);
    }

    public void popupAlert() {

        alertDialogBuilder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.add_contact_alert, null);
        alertDialogBuilder.setView(v);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        addContactDBButton=(Button) v.findViewById(R.id.registerButton);
        usernameEditText=(EditText)v.findViewById(R.id.usernameEditText);
        passwordEditText=(EditText)v.findViewById(R.id.passwordEditText);

        addContactDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /*
            TODO notify when item was added
            */
            public void onClick(View view) {
                if (!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()){
                    addContactToDatabase(view);
                alertDialog.dismiss();

            }
                else
                    Toast.makeText(ContactsActivity.this,"Username or password is empty",Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void addContactToDatabase(View v) {
        String username=usernameEditText.getText().toString();
        String password=passwordEditText.getText().toString();

        Contact contact=new Contact(username,password);
        contactsDB.addContact(contact);

        setupUI();
        Log.d("addedContact","Contact is added");

        Snackbar.make(v,"Contact is added",Snackbar.LENGTH_LONG).show();




    }

}
