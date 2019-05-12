package Util;

import android.content.Context;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.lenovo.contactsapp2.R;

import java.util.ArrayList;

import Database.Database;
import Model.Contact;

public class Constants {


    public final static String DATABASE_NAME="contactsDB";
    public final static String TABLE_NAME="contacts";
    public final static int DATABASE_VERSION=1;

    //Columns

    public final static String KEY_ID="id";
    public final static String KEY_USERNAME="username";
    public final static String KEY_PASSWORD="password";
    public final static String KEY_ADDED_DATE="addedDate";


    //AlertDialogButtons and EditTexts
     private EditText usernameEditText;
     private EditText passwordEditText;
     private Button addContactDBButton;
     private AlertDialog alertDialog;



}
