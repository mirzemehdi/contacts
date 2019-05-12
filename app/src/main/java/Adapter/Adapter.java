package Adapter;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lenovo.contactsapp2.ContactsActivity;
import com.example.lenovo.contactsapp2.MainActivity;
import com.example.lenovo.contactsapp2.R;

import java.util.List;

import Database.Database;
import Model.Contact;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<Contact> contacts;

    public Adapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }



    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_model,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        holder.usernameTextView.setText("Username: "+contacts.get(position).getUsername());
        holder.passwordTextView.setText("Password: "+contacts.get(position).getPassword());
        holder.dateTextView.setText("Added on: "+contacts.get(position).getAddedDate());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView usernameTextView,passwordTextView,dateTextView;
        private Button editButton,deleteButton;
        private Database contactsDb;

        public ViewHolder(View itemView,Context ctx) {
            super(itemView);

            context=ctx;
            usernameTextView=(TextView)itemView.findViewById(R.id.usernameTextView);
            passwordTextView=(TextView)itemView.findViewById(R.id.passwordTextView);
            dateTextView=(TextView)itemView.findViewById(R.id.addedDateTextView);
            editButton=(Button)itemView.findViewById(R.id.editButton);

            deleteButton=(Button)itemView.findViewById(R.id.deleteButton);
            contactsDb=new Database(context);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareContact(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.editButton:

                    editItem(contacts.get(getAdapterPosition()));
                    break;
                case R.id.deleteButton:
                    deleteItem();
                    break;


            }
        }


        //Share Contact
        public void shareContact(int adapterPosition){

            Contact contact=contacts.get(adapterPosition);
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            String text="Id: "+contact.getId()+"\nUsername: "+contact.getUsername()+"\nPassword: "+contact.getPassword()+
                    "\nAdded Date: "+contact.getAddedDate();
            intent.putExtra(Intent.EXTRA_SUBJECT,"ContactInfos");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"mirze@gmail.com"});
            intent.putExtra(Intent.EXTRA_TEXT,text);

            try {
                context.startActivity(Intent.createChooser(intent,"Send Contact..."));

            }catch (ActivityNotFoundException e){
                Toast.makeText(context.getApplicationContext(),"Install mail ",Toast.LENGTH_LONG).show();
            }

        }

        //Delete Contact
        public void deleteItem(){

            AlertDialog.Builder alertBuiler=new AlertDialog.Builder(context);
            View view=LayoutInflater.from(context).inflate(R.layout.delete_confirmation,null);
            alertBuiler.setView(view);
            final AlertDialog alertDialog=alertBuiler.create();
            alertDialog.show();
            Button yesButton=(Button)view.findViewById(R.id.yesButton);
            Button noButton=(Button)view.findViewById(R.id.noButton);

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactsDb.deleteContact(contacts.get(getAdapterPosition()).getId());
                    contacts.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();


                    if (contactsDb.getCountContact()<1){
                        Intent passMainActivity=new Intent(context, MainActivity.class);
                        passMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(passMainActivity);



                    }
                }
            });







        }
        //Edit Contact
        public void editItem(final Contact contact){
            AlertDialog.Builder alertBuiler=new AlertDialog.Builder(context);
            View view=LayoutInflater.from(context).inflate(R.layout.add_contact_alert,null);
            TextView editTextView=(TextView)view.findViewById(R.id.createAccountTextView);
            editTextView.setText("Edit Contact");
            alertBuiler.setView(view);
            final AlertDialog alertDialog=alertBuiler.create();
            alertDialog.show();
            final EditText usernameEditText=(EditText)view.findViewById(R.id.usernameEditText);
            final EditText passwordEditText=(EditText)view.findViewById(R.id.passwordEditText);
            Button registerButton=(Button)view.findViewById(R.id.registerButton);



            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username=usernameEditText.getText().toString();
                    String password=passwordEditText.getText().toString();
                    if (!username.isEmpty()&&!password.isEmpty()){
                        contact.setUsername(username);
                        contact.setPassword(password);
                        contactsDb.updateContact(contact);
                        notifyItemChanged(getAdapterPosition(),contact);
                        alertDialog.dismiss();

                    }
                }
            });






        }

    }
}
