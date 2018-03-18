package com.example.n8tech.taskcan.Models;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
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

    public static class UpdateUser extends AsyncTask<User, Void, String> {

        @Override
        protected String doInBackground(User... users) {
            verifySettings();
            Log.i("Testing", client.toString());
            Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

            for (User user : users){
                Log.i("Testing", user.getEmail());
                Index index = new Index.Builder(user).index("cmput301w18t06").type("user").id(user.getId()).build();
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


            Log.i("testing", "Using id");
            Get get = new Get.Builder("cmput301w18t06", search_params[0]).type("user").build();

            Log.i("Testing", get.getPathToResult());

            try {
                JestResult result = client.execute(get);

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

    public static class SearchUser extends AsyncTask<String, Void, ArrayList<User>> {

        @Override
        protected ArrayList<User> doInBackground(String... search_params) {
            verifySettings();
            ArrayList<User> userList = new ArrayList<User>();

            Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

            //String query = "?q=email:testing";
            //Implement SearchSourceBuilder

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("email", search_params[0]));

            Search search = new Search.Builder(searchSourceBuilder.toString())
                            .addIndex("cmput301w18t06")
                            .addType("user")
                            .build();

            Log.i("Testing", search.getData(gson));

            //Log.i("Testing", search.getPathToResult());
            JestResult result;

            try {
                result = client.execute(search);

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

    public static class AddTask extends AsyncTask<Task, Void, String> {

        @Override
        protected String doInBackground(Task... tasks) {
            verifySettings();
            Log.i("Testing", client.toString());
            Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

            for (Task task : tasks){
                //Log.i("Testing", user.getEmail());
                Index index = new Index.Builder(task).index("cmput301w18t06").type("task").build();
                Log.i("Testing", index.getData(gson));
                Log.i("Testing", index.getURI());
                Log.i("Testing", index.getRestMethodName());

                try {
                    DocumentResult result = client.execute(index);

                    Log.i("Testing", result.getJsonString());
                    if(result.isSucceeded()) {
                        task.setId(result.getId());
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

    public static class UpdateTask extends AsyncTask<Task, Void, String> {

        @Override
        protected String doInBackground(Task... tasks) {
            verifySettings();
            Log.i("Testing", client.toString());
            Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

            for (Task task : tasks){
                //Log.i("Testing", user.getEmail());
                Index index = new Index.Builder(task).index("cmput301w18t06").type("task").id(task.getId()).build();
                Log.i("Testing", index.getData(gson));
                Log.i("Testing", index.getURI());
                Log.i("Testing", index.getRestMethodName());

                try {
                    DocumentResult result = client.execute(index);

                    Log.i("Testing", result.getJsonString());
                    if(result.isSucceeded()) {
                        task.setId(result.getId());
                    }
                    else {
                        Log.i("Error", "Elasticsearch was not able to add the task");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and add the task");
                    return "NetworkError";

                }
            }
            return "NoNetworkError";
        }

    }

    public static class GetTask extends AsyncTask<String, Void, ArrayList<Task>> {

        @Override
        protected ArrayList<Task> doInBackground(String... search_params) {
            verifySettings();
            ArrayList<Task> taskList = new ArrayList<Task>();

            //String query = "?q=email:testing";
            //Implement SearchSourceBuilder


            Log.i("testing", "Using id");
            Get get = new Get.Builder("cmput301w18t06", search_params[0]).type("task").build();

            Log.i("Testing", get.getPathToResult());

            try {
                JestResult result = client.execute(get);

                if(result.isSucceeded()) {
                    taskList = (ArrayList<Task>) result.getSourceAsObjectList(Task.class);
                }
                else {
                    Log.i("Error", "The search query has failed");
                }
            } catch (Exception e) {
                //When no connection this occurs
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return taskList;
        }
    }

    public static class SearchTask extends AsyncTask<String, Void, ArrayList<Task>> {

        @Override
        protected ArrayList<Task> doInBackground(String... search_params) {
            verifySettings();
            ArrayList<Task> taskList = new ArrayList<Task>();

            Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

            //String query = "?q=email:testing";
            //Implement SearchSourceBuilder

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("taskTitle", search_params[0]));

            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex("cmput301w18t06")
                    .addType("task")
                    .build();

            Log.i("Testing", search.getData(gson));

            //Log.i("Testing", search.getPathToResult());
            JestResult result;

            try {
                result = client.execute(search);

                if(result.isSucceeded()) {
                    taskList = (ArrayList<Task>) result.getSourceAsObjectList(Task.class);
                }
                else {
                    Log.i("Error", "The search query has failed");
                }
            } catch (Exception e) {
                //When no connection this occurs
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return taskList;
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
