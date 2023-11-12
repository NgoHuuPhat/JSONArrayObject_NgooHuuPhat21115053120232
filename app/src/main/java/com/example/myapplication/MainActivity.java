package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvName;
    ArrayList<String> arrayCourse;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvName = (ListView) findViewById(R.id.listviewName);
        arrayCourse = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayCourse);
        lvName.setAdapter(adapter);

        new ReadJSON().execute("https://microsoftedge.github.io/Demos/json-dummy-data/64KB.json");
    }
    private class ReadJSON extends AsyncTask<String,Void,String>
    {
        protected String doInBackground(String... strings)
        {
            StringBuilder content = new StringBuilder();
            try
            {
                URL url = new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line ="";

                while((line = bufferedReader.readLine())!=null)
                {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            try
            {
                JSONArray array = new JSONArray(s);

                for(int i = 0;i<array.length();i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    String name = object.getString("name");
                    String language = object.getString("language");
                    arrayCourse.add(name + " - " + language);
                }

                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}