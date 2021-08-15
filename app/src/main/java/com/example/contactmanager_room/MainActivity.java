package com.example.contactmanager_room;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.contactmanager_room.model.Contact;
import com.example.contactmanager_room.model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    private ContactViewModel contactViewModel;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        //creating the view model
        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this
                .getApplication())
                .create(ContactViewModel.class);

        //getting the data from the contact view model
        //& observe when the data is change/update
        contactViewModel.getAllContact().observe(this, contacts -> {

            StringBuilder builder = new StringBuilder();

            //iterating/looping through contactlist then put each name,occupation in each String in builder class
            for (Contact contact : contacts) {
                builder.append(" - ")
                        .append(contact.getName())
                        .append(" ")
                        .append(contact.getOccupation());

                Log.d("TAG", "onCreate: " + contact.getName() + " Occupation: " + contact.getOccupation());
            }
            // set text from builder class into the textView
            textView.setText(builder.toString());

        });

        //setting the FloatingActionButton to navigate to NewContact Activity
        FloatingActionButton fab =findViewById(R.id.add_contact_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewContact.class);

//          this method is deprecated won't use
//          startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            activityResultLauncher.launch(intent);
        });
    }


    //old
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
//            Log.d("TAG", "onActivityResult: " + data.getStringExtra(NewContact.NAME_REPLY));
//            Log.d("TAG", "onActivityResult: " + data.getStringExtra(NewContact.OCCUPATION_REPLY));
//        }
//    }

    //new
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if(result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    assert intent != null;

                    String name = intent.getStringExtra(NewContact.NAME_REPLY);
                    String occupation = intent.getStringExtra(NewContact.OCCUPATION_REPLY);

                    Contact contact = new Contact(name, occupation);
                    ContactViewModel.insert(contact);
                }
            });
}