package com.example.taf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import  android.view.View;
import android.widget.AdapterView;
import  android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListeMateriel extends AppCompatActivity {
    EditText inputSearch;
    private TextView listView ;
    private RequestQueue Rqueu;
    private ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_materiel);

        Rqueu = Volley.newRequestQueue(this);
        ListView listView = (ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.liste_materiel,R.id.libelle,arrayList);
        listView.setAdapter(adapter);

        jsonParse();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if  ( position==0)
                {
                    Intent intent = new Intent(ListeMateriel.this,Imprimante.class);
                    startActivity(intent);
                }
                if  ( position==1)
                {
                    Intent intent = new Intent(ListeMateriel.this,Switch.class);
                    startActivity(intent);
                }
                if  ( position==2)
                {
                    Intent intent = new Intent(ListeMateriel.this,Routeur.class);
                    startActivity(intent);
                }
                if  ( position==3)
                {
                    Intent intent = new Intent(ListeMateriel.this,Serveur.class);
                    startActivity(intent);
                }
                if  ( position==4)
                {
                    Intent intent = new Intent(ListeMateriel.this,Ordinateur.class);
                    startActivity(intent);
                }
            }
        });
     /*   inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputSearch.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                inputSearch.setError(null);
            }
        });
*/

    }

    private void jsonParse(){
        String url = "https://api.myjson.com/bins/11a457";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for ( int i = 0 ; i< response.length();i++){
                        JSONObject type_materiel = response.getJSONObject(i);
                        String libelle =type_materiel.getString("libelle");

                        arrayList.add(libelle);
                        adapter.notifyDataSetChanged();
                    }
                }catch (JSONException e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Rqueu.add(req);

    }


}



