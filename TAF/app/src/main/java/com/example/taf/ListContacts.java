package com.example.taf;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListContacts extends AppCompatActivity {

    private RelativeLayout btnContact;
    private TextView listeContact;
    private RequestQueue mQueue;

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);

        mQueue = Volley.newRequestQueue(this);
        final ListView listeContact = (ListView) findViewById(R.id.listViewContact);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_contact, R.id.nom, arrayList);
        listeContact.setAdapter(adapter);

        jsonParse();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addData = new Intent(ListContacts.this, FicheClient.class);
                addData.putExtra("clientId", id);
                Toast.makeText(ListContacts.this, "L'id est " + id,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),FicheClient.class));
            }
        });

        listeContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                registerForContextMenu(listeContact);
                return false;
            }
        });
        }

    private void jsonParse() {
        String url = "https://api.myjson.com/bins/17u87z";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                        id = getIntent().getIntExtra("clientId", id);
                        JSONObject client = response.getJSONObject(id);
                        JSONArray jsonArrayContact = client.getJSONArray("contacts");
                        for (int j = 0; j < jsonArrayContact.length(); j++){
                            JSONObject contact = jsonArrayContact.getJSONObject(j);
                            String nom = contact.getString("pers_nom");
                            arrayList.add(nom);
                        }
                        adapter.notifyDataSetChanged();

                }catch (JSONException e){

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose an option");
        getMenuInflater().inflate(R.menu.menu_option, menu);
    }
}