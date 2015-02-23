package com.guidebook.myapplication;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "https://www.guidebook.com/service/v2/upcomingGuides";

    // JSON Node names
    private static final String DATA = "data";
    private static final String TAG_START_DATE = "startDate";
    private static final String TAG_END_DATE = "endDate";
    private static final String TAG_NAME = "name";
    private static final String TAG_URL = "url";
    private static final String TAG_OBJ_TYPE = "objType";
    private static final String TAG_ICON = "icon";

    private static final String TAG_VENUE = "venue";
    private static final String TAG_CITY = "city";
    private static final String TAG_STATE = "state";

    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();



        // Calling async task to get json
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            //pDialog = new ProgressDialog(MainActivity.this);
            //pDialog.setMessage("Please wait...");
            //pDialog.setCancelable(false);
           // pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(DATA);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String startDate = c.getString(TAG_START_DATE);

                        String endDate = c.getString(TAG_END_DATE);


                        String name = c.getString(TAG_NAME);


                        String URL = c.getString(TAG_URL);



                        JSONObject venue = c.getJSONObject(TAG_VENUE);
                        String city = venue.getString(TAG_CITY);
                        String state = venue.getString(TAG_STATE);
                        String objType = c.getString(TAG_OBJ_TYPE);

                        String iconURL = c.getString(TAG_ICON);


                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put(TAG_START_DATE, "Start Date: "+ startDate);
                        contact.put(TAG_END_DATE, "End Date: "+endDate);
                        contact.put(TAG_NAME, name);
                        contact.put(TAG_URL, "www.guidebook.com"+URL);
                        contact.put(TAG_CITY, city+","+state);
                        contact.put(TAG_ICON, iconURL);
                        Log.e("ICON URL:", iconURL);


                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            //if (pDialog.isShowing()) pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[] {TAG_START_DATE, TAG_END_DATE,TAG_NAME,TAG_URL, TAG_CITY ,TAG_ICON

            }, new int[] { R.id.name,
                    R.id.email, R.id.mobile, R.id.eventName, R.id.city_state, R.id.icon });

            setListAdapter(adapter);
        }

    }

}