package com.example.taf;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FicheClient extends AppCompatActivity {
    private Button btListeContact;
    private Button btMateriel;
    private MenuItem itemEdit;
    private TextView tvTest;
    private RequestQueue mQueue;
    public TextView nomClient;
    private TextView adresseClient;
    private TextView telClient;
    private String nom;
    private String adresse1;
    private String adresse2;
    private int id;
    private String cpville;
    private String villeNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_client);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btListeContact = (Button) findViewById(R.id.btListeContact);
        btMateriel = (Button) findViewById(R.id.btMateriel);
        nomClient = (TextView) findViewById(R.id.nom_ficheClient);
        adresseClient = (TextView) findViewById(R.id.adresse_ficheClient);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        tvTest = findViewById(R.id.tvTest);
        mQueue = Volley.newRequestQueue(this);

        jsonParse();
        btListeContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FicheClient.this, "L'id est " + id, Toast.LENGTH_SHORT).show();
                Intent addData = new Intent(FicheClient.this, ListContacts.class);
                addData.putExtra("clientId", id);
                startActivity(addData);
            }
        });
        btMateriel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FicheClient.this, ListeMateriel.class));
            }
        });

    }

    private void jsonParse(){
        String url = "https://api.myjson.com/bins/17u87z";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    id = getIntent().getIntExtra("clientId", id);
                    JSONObject client = response.getJSONObject(id);
                    nom = client.getString("clt_nom");
                    adresse1 = client.getString("clt_adr1");
                    adresse2 = client.getString("clt_adr2");
                    nomClient.setText(nomClient.getText() + " " + nom);
                    JSONObject ville = client.getJSONObject("ville");
                    Toast.makeText(FicheClient.this, "Mon id est " + id,Toast.LENGTH_SHORT).show();
                    if(ville.length() == 0){
                        adresseClient.setText(adresseClient.getText() + adresse1 + "\n" + adresse2);
                    }else{

                        cpville = ville.getString("ville_cp");
                        villeNom = ville.getString("ville_nom");
                        adresseClient.setText(adresseClient.getText() + adresse1 + "\n" + adresse2 + "\n" + cpville + " " + villeNom);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fiche_client, menu);
        MenuItem itemEdit = menu.findItem(R.id.modificationficheClient);
        itemEdit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent addData = new Intent(FicheClient.this, ModificationAction.class);
                addData.putExtra("nom", nom);
                addData.putExtra("adresse1",adresse1);
                addData.putExtra("adresse2",adresse2);
                startActivity(addData);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.modificationficheClient) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
