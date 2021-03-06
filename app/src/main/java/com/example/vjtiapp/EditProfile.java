package com.example.vjtiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity implements OnItemSelectedListener {
    Spinner branch,year;
    String branch_name,year_name;
    Button save;
    DatabaseReference mFireBase;
    String user_name;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        branch = (Spinner)findViewById(R.id.branch);
        year = findViewById(R.id.year);
        save = findViewById(R.id.save);
        mFireBase = FirebaseDatabase.getInstance().getReference();
        name = findViewById(R.id.pro_name);
        name.setText(new PrefManager(EditProfile.this).getName());

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.branches, R.layout.spinner_item);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.year, R.layout.spinner_item);



        branch.setAdapter(adapter1);
        branch.setOnItemSelectedListener(this);


        year.setAdapter(adapter2);
        year.setOnItemSelectedListener(this);

        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(name.getText().toString()!= " ")
                        {
                            user_name = name.getText().toString();
                            mFireBase.child("User's Profile");
                            new PrefManager(EditProfile.this).saveProfileDetails(user_name,branch_name,year_name);
                            AddToUsersProfile();
                            finish();
                            startActivity(new Intent(EditProfile.this,Display_Profile.class));
                        }
                        else{
                            Toast.makeText(EditProfile.this,"Please Enter Name",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        if(parent.getId()==R.id.branch){
            switch (position) {
                case 0:
                    // Whatever you want to happen when the first item gets selected
                    branch_name = "Computer";

                    break;
                case 1:
                    // Whatever you want to happen when the second item gets selected
                    branch_name = "Electronics";

                    break;
                case 2:
                    // Whatever you want to happen when the thrid item gets selected
                    branch_name = "IT";
                    break;
                case 3:
                    // Whatever you want to happen when the thrid item gets selected
                    branch_name = "EXTC";
                    break;
                case 4:
                    // Whatever you want to happen when the thrid item gets selected
                    branch_name = "Civil";
                    break;
                case 5:
                    // Whatever you want to happen when the thrid item gets selected
                    branch_name = "Textile";
                    break;
                case 6:
                    // Whatever you want to happen when the thrid item gets selected
                    branch_name = "Production";
                    break;
                case 7:
                    // Whatever you want to happen when the thrid item gets selected
                    branch_name = "Mechanical";
                    break;
                case 8:
                    // Whatever you want to happen when the thrid item gets selected
                    branch_name = "Electrical";
                    break;
            }
        }


        else{
            switch (position) {
                case 0:
                    // Whatever you want to happen when the first item gets selected
                    year_name = "First";

                    break;
                case 1:
                    // Whatever you want to happen when the second item gets selected
                    year_name = "Second";

                    break;
                case 2:
                    // Whatever you want to happen when the thrid item gets selected
                    year_name = "Third";
                    break;
                case 3:
                    // Whatever you want to happen when the thrid item gets selected
                    year_name = "Final";
                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if(parent.getId()==R.id.branch)
        {
            Toast.makeText(
                    EditProfile.this,"Please select a branch",Toast.LENGTH_LONG
            ).show();

        }
        else{
            Toast.makeText(
                    EditProfile.this,"Please enter year",Toast.LENGTH_LONG
            ).show();
        }
    }

    public void AddToUsersProfile(){
        mFireBase = FirebaseDatabase.getInstance().getReference().child("User's Profile");
        HashMap<String,String> datamap = new HashMap<String,String>();
        datamap.put("Name",user_name);
        datamap.put("Branch",branch_name);
        datamap.put("Year",year_name);

        mFireBase.push().setValue(datamap);
    }

}
