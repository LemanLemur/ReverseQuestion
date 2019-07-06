package com.example.reversequestions;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateProfile extends Activity {

    private EditText editText;
    private ProfileDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile_activity);
        editText = (EditText) findViewById(R.id.editText);
        db = new ProfileDB(this);
    }

    public void createProfile(View view){
        if(editText.getText().length()>0){
            ProfileTmp.name = editText.getText().toString();
            db.insertData(ProfileTmp.name, 0, 0, 0);
            Cursor resP = db.getAllData();
            if(resP.moveToNext()) {
                ProfileTmp.id = resP.getString(0);
            }
            Toast.makeText(getApplicationContext(), "Profil został stworzony!", Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            Toast.makeText(getApplicationContext(), "Imię nie może być puste!", Toast.LENGTH_SHORT).show();
        }
    }
}
