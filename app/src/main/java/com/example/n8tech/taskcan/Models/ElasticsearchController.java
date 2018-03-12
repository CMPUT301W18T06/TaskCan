package com.example.n8tech.taskcan.Models;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by m_qui on 3/11/2018.
 */

public class ElasticsearchController {

    private static JestDroidClient client;

    public static class AddUser extends AsyncTask<User, Void, String> {

        @Override
        protected String doInBackground(User... users) {
            verifySettings();
            Log.i("Testing", client.toString());
            Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

            for (User user : users){
                Log.i("Testing", user.getEmail());
                Index index = new Index.Builder(user).index("cmput301w18t06").type("user").build();
                Log.i("Testing", index.getData(gson));
                Log.i("Testing", index.getURI());
                Log.i("Testing", index.getRestMethodName());

                try {
                    DocumentResult result = client.execute(index);

                    Log.i("Testing", result.getJsonString());
                    if(result.isSucceeded()) {
                        user.setId(result.getId());
                    }
                    else {
                        Log.i("Error", "Elasticsearch was not able to add the user");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and add the user");
                    return "NetworkError";

                }
            }
            return "NoNetworkError";
        }

    }

    public static class GetUser extends AsyncTask<String, Void, ArrayList<User>> {

        @Override
        protected ArrayList<User> doInBackground(String... search_params) {
            verifySettings();
            ArrayList<User> userList = new ArrayList<User>();

            //String query = "?q=email:testing";
            //Implement SearchSourceBuilder
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"filtered\" : {\n" +
                    "            \"query\" : {\n" +
                    "                \"query_string\" : {\n" +
                    "                    \"query\" : \"test\"\n" +
                    "                }\n" +
                    "            },\n" +
                    "            \"filter\" : {\n" +
                    "                \"term\" : { \"" + search_params[0] + "\" : \"" + search_params[1] + "\" }\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

            Log.i("Testing", query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t06")
                    .addType("user")
                    .build();

            Log.i("Testing", search.getPathToResult());

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    userList = (ArrayList<User>) result.getSourceAsObjectList(User.class);
                }
                else {
                    Log.i("Error", "The search query has failed");
                }
            } catch (Exception e) {
                //When no connection this occurs
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return userList;
        }
    }

    public static void verifySettings() {

        if(client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
