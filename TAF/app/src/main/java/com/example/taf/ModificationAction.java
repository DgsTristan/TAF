package com.example.taf;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ModificationAction extends AppCompatActivity {
    private TextView champ;
    private TextView editName;
    private TextView editAdresse1;
    private TextView editAdresse2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_action);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        editName = (TextView) findViewById(R.id.editName);
        editAdresse1 = (TextView) findViewById(R.id.editAdress);
        editAdresse2 = (TextView) findViewById(R.id.editAdress2);
        setSupportActionBar(toolbar);

        LayoutInflater factory = getLayoutInflater();
        View v = factory.inflate(R.layout.activity_fiche_client,null);
        champ = (TextView) v.findViewById(R.id.nom_ficheClient); // déclarer champ et afficher le champ récupéré dans l'activité de modification
        editName.setText(getIntent().getStringExtra("nom"));
        editAdresse1.setText(getIntent().getStringExtra("adresse1"));
        editAdresse2.setText(getIntent().getStringExtra("adresse2"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FicheClient.class));
            }
        });

    }

}