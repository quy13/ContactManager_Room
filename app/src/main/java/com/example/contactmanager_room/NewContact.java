package com.example.contactmanager_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactmanager_room.model.Contact;
import com.example.contactmanager_room.model.ContactViewModel;

public class NewContact extends AppCompatActivity {

    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION_REPLY = "occupation_reply";
    private EditText enterName;
    private EditText enterOccupation;
    private Button   saveInfoButton;

    private ContactViewModel contactViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        enterName = findViewById(R.id.enter_name_text);
        enterOccupation = findViewById(R.id.enter_occupation_text);
        saveInfoButton = findViewById(R.id.save_button);

        //creating the view model
        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(
                NewContact.this
                        .getApplication())
                .create(ContactViewModel.class);


        saveInfoButton.setOnClickListener(v -> {

            Intent replyIntent = new Intent();

            //checking if any of the EditText is empty
            if(!TextUtils.isEmpty(enterName.getText())
                    && !TextUtils.isEmpty(enterOccupation.getText())) {

                String name       = enterName.getText().toString();
                String occupation = enterOccupation.getText().toString();

                //put data inside Intent
                replyIntent.putExtra(NAME_REPLY, name);
                replyIntent.putExtra(OCCUPATION_REPLY, occupation);
                setResult(RESULT_OK, replyIntent);

                //Contact contact = new Contact(name, occupation);

                //insert data
                //ContactViewModel.insert(contact);

            }else {
//              Toast.makeText(this,R.string.empty,Toast.LENGTH_SHORT)
//                        .show();
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();
        });
    }
}