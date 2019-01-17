package win.prashant.swissstationboard;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView textViewname,textViewc,textViewo,textViewt;   //textViewno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.Listviewdemo);
        new JsonCategory().execute("http://transport.opendata.ch/v1/stationboard?station=Aarau&limit=10");
    }

    private class JsonCategory extends AsyncTask<String, String, List<Station_board>> {
        @Override
        protected List<Station_board> doInBackground(String... strings) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader= new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                //responce

                String finalJson = buffer.toString();
                JSONObject parentobject = new JSONObject(finalJson);
                JSONArray parentarray = parentobject.getJSONArray("stationboard");
                List<Station_board> routelist = new ArrayList<>();

                // is in for loop to read all the date line coum by colum
                for (int i =0; i < parentarray.length(); i++){
                    JSONObject finalObject = parentarray.getJSONObject(i);

                    Station_board station_board1 = new Station_board();


                    String sname = finalObject.getString("name");
                    station_board1.setName(sname);
                    String scat = finalObject.getString("category");
                    station_board1.setCategory(scat);
                    String sopt = finalObject.getString("operator");
                    station_board1.setOperator(sopt);
                    String sto = finalObject.getString("to");
                    station_board1.setTo(sto);

                    routelist.add(station_board1);

                }

                return routelist;




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // show the result when background method is finished

        @Override
        protected void onPostExecute(List<Station_board> station_boards) {
            super.onPostExecute(station_boards);

            // now we will make array adater to customize the
            StationBoardAdapter pp = new  StationBoardAdapter(MainActivity.this,R.layout.customlistview,station_boards);
            listView.setAdapter(pp);
        }
    }


    private class StationBoardAdapter  extends ArrayAdapter {
        public List<Station_board>station_boards;
        private int resources;
        private LayoutInflater inflater;

        public  StationBoardAdapter(Context context,int resources,List<Station_board>objects){
            super(context,resources,objects);
            station_boards = objects;
            this.resources= resources;
            inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        public View getView(int position , View convertView, ViewGroup parent){

            if (convertView == null)
            {
                convertView = inflater.inflate(resources,null);
            }
            textViewname = convertView.findViewById(R.id.editTextstationname);
            textViewname.setText(station_boards.get(position).getName());

            textViewc = convertView.findViewById(R.id.textViewcat);
            textViewc.setText(station_boards.get(position).getCategory());

            textViewo = convertView.findViewById(R.id.textViewoperator);
            textViewo.setText(station_boards.get(position).getCategory());

            textViewt = convertView.findViewById(R.id.textViewto);
            textViewt.setText(station_boards.get(position).getCategory());

           /* textViewno = convertView.findViewById(R.id.textViewto);
            textViewno.setText(station_boards.get(position).getCategory());*/



            return convertView;

        }
    }
}
