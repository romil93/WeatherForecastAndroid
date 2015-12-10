package com.romil93.weatherforecast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.graphics.Color;
import android.widget.TextView;
import android.net.Uri;

import com.romil93.weatherforecast.library.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
    Button button;
    Button search;
    Button clear;

    ImageButton forecastIo;
    Spinner spinner;

    String temperature = "us";

    String streetAddress;
    EditText streetAddressForm;

    String cityName;
    EditText cityAddressForm;

    String state;

    RadioGroup radioGroup;

    RadioButton fahrenheit;
    RadioButton celsius;

    JSONObject resultJson = null;

    private static String url = "";

    //JSON Node Names
    private static final String TAG_USER = "user";

    JSONArray user = null;


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("The Data is COMING YOUR WAY ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            return jParser.getJSONFromUrl(url);
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            resultJson = json;

            Intent resultIntent =  new Intent(MainActivity.this, ResultActivity.class);
            resultIntent.putExtra("city", cityName+", "+state);
            resultIntent.putExtra("json", resultJson.toString());
            resultIntent.putExtra("unit", temperature);
            startActivity(resultIntent);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        button = (Button)findViewById(R.id.aboutButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =  new Intent(MainActivity.this, AboutActivity.class);
                startActivity(myIntent);
            }
        });

        //Setting Fahrenheit as the checked by default
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        fahrenheit = (RadioButton) findViewById(R.id.radio_fahrenheit);
        radioGroup.check(fahrenheit.getId());

        //Setting Forecast.io link
        forecastIo = (ImageButton) findViewById(R.id.imageButton);

        forecastIo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://www.forecast.io/"));
                startActivity(viewIntent);
            }
        });

        // //Setting the dropdown of the state
        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("Select");
        categories.add("Alabama");
        categories.add("Alaska");
        categories.add("Arizona");
        categories.add("Arkansas");
        categories.add("California");
        categories.add("Colorado");
        categories.add("Connecticut");
        categories.add("Delaware");
        categories.add("District Of Columbia");
        categories.add("Florida");
        categories.add("Georgia");
        categories.add("Hawaii");
        categories.add("Idaho");
        categories.add("Illinois");
        categories.add("Indiana");
        categories.add("Iowa");
        categories.add("Kansas");
        categories.add("Kentucky");
        categories.add("Louisiana");
        categories.add("Maine");
        categories.add("Maryland");
        categories.add("Massachusetts");
        categories.add("Michigan");
        categories.add("Minnesota");
        categories.add("Mississippi");
        categories.add("Missouri");
        categories.add("Montana");
        categories.add("Nebraska");
        categories.add("Nevada");
        categories.add("New Hampshire");
        categories.add("New Jersey");
        categories.add("New Mexico");
        categories.add("New York");
        categories.add("North Carolina");
        categories.add("North Dakota");
        categories.add("Ohio");
        categories.add("Oklahoma");
        categories.add("Oregon");
        categories.add("Pennsylvania");
        categories.add("Rhode Island");
        categories.add("South Carolina");
        categories.add("South Dakota");
        categories.add("Tennessee");
        categories.add("Texas");
        categories.add("Utah");
        categories.add("Vermont");
        categories.add("Virginia");
        categories.add("Washington");
        categories.add("West Virginia");
        categories.add("Wisconsin");
        categories.add("Wyoming");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        ////Search button
        search = (Button) findViewById(R.id.searchButton);
        clear = (Button) findViewById(R.id.clearButton);

        streetAddressForm = (EditText) findViewById(R.id.mEdttxt1);
        cityAddressForm = (EditText) findViewById(R.id.mEdttxt2);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                streetAddress = streetAddressForm.getText().toString();

                cityName = cityAddressForm.getText().toString();
                int position = spinner.getSelectedItemPosition();
                state = "";

                switch (position) {
                    case 0:
                        state = "";
                        break;
                    case 1:
                        state = "AL";
                        break;
                    case 2:
                        state = "AK";
                        break;
                    case 3:
                        state = "AZ";
                        break;
                    case 4:
                        state = "AR";
                        break;
                    case 5:
                        state = "CA";
                        break;
                    case 6:
                        state = "CO";
                        break;
                    case 7:
                        state = "CT";
                        break;
                    case 8:
                        state = "DE";
                        break;
                    case 9:
                        state = "DC";
                        break;
                    case 10:
                        state = "FL";
                        break;
                    case 11:
                        state = "GA";
                        break;
                    case 12:
                        state = "HI";
                        break;
                    case 13:
                        state = "ID";
                        break;
                    case 14:
                        state = "IL";
                        break;
                    case 15:
                        state = "IN";
                        break;
                    case 16:
                        state = "IA";
                        break;
                    case 17:
                        state = "KS";
                        break;
                    case 18:
                        state = "KY";
                        break;
                    case 19:
                        state = "LA";
                        break;
                    case 20:
                        state = "ME";
                        break;
                    case 21:
                        state = "MD";
                        break;
                    case 22:
                        state = "MA";
                        break;
                    case 23:
                        state = "MI";
                        break;
                    case 24:
                        state = "MN";
                        break;
                    case 25:
                        state = "MS";
                        break;
                    case 26:
                        state = "MO";
                        break;
                    case 27:
                        state = "MT";
                        break;
                    case 28:
                        state = "NE";
                        break;
                    case 29:
                        state = "NV";
                        break;
                    case 30:
                        state = "NH";
                        break;
                    case 31:
                        state = "NJ";
                        break;
                    case 32:
                        state = "NM";
                        break;
                    case 33:
                        state = "NY";
                        break;
                    case 34:
                        state = "NC";
                        break;
                    case 35:
                        state = "ND";
                        break;
                    case 36:
                        state = "OH";
                        break;
                    case 37:
                        state = "OK";
                        break;
                    case 38:
                        state = "OR";
                        break;
                    case 39:
                        state = "PA";
                        break;
                    case 40:
                        state = "RI";
                        break;
                    case 41:
                        state = "SC";
                        break;
                    case 42:
                        state = "SD";
                        break;
                    case 43:
                        state = "TN";
                        break;
                    case 44:
                        state = "TX";
                        break;
                    case 45:
                        state = "UT";
                        break;
                    case 46:
                        state = "VT";
                        break;
                    case 47:
                        state = "VA";
                        break;
                    case 48:
                        state = "WA";
                        break;
                    case 49:
                        state = "WV";
                        break;
                    case 50:
                        state = "WI";
                        break;
                    case 51:
                        state = "WY";
                        break;
                }

                String error = "Please enter the following fields:\n";
                int flag = 0;
                if(Objects.equals(streetAddress, "")) {
                    flag += 1;
                    error = error + flag + ". Street Address\n";
                    streetAddressForm.setError("Please Enter the street address");
                }
                if(Objects.equals(cityName, "")) {
                    flag += 1;
                    error = error + flag + ". City Name\n";
                    cityAddressForm.setError("Please Enter the City Name");
                }
                if(Objects.equals(state,"")) {
                    flag += 1;
                    error = error + flag + ". State\n";

                    TextView errorText = (TextView)spinner.getSelectedView();
                    errorText.setError("anything here, just to add the icon");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Please Choose a state");//changes the selected item text to this
                }

                if(flag != 0) {
                    TextView errorMsg = (TextView) findViewById(R.id.textView4);
                    errorMsg.setTextColor(Color.RED);
                    errorMsg.setTextSize(20);
                    errorMsg.setText(error);
                }

                if(!streetAddress.equals("") && !cityName.equals("") && !state.equals("")) {
                    url = "http://weather-forecast-csci571.elasticbeanstalk.com/test.php?street_address="+streetAddress.replaceAll(" ","+")+"&city_name="+cityName.replaceAll(" ","+")+"&state="+state+"&temperature="+temperature;
                    Log.d("URL",url);
                    new JSONParse().execute();
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                streetAddressForm.setText("");
                cityAddressForm.setText("");
                spinner.setSelection(0);
                radioGroup.check(fahrenheit.getId());
                TextView errorMsg = (TextView) findViewById(R.id.textView4);
                errorMsg.setText("");
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        state = parent.getItemAtPosition(position).toString();

        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + state, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_fahrenheit:
                if (checked)
                    temperature = "us";
                    break;
            case R.id.radio_celsius:
                if (checked)
                    temperature = "si";
                    break;
        }
    }
}
