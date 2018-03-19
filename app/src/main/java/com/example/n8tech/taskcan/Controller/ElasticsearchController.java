package com.example.n8tech.taskcan.Controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;

/**
 * ElasticsearchController encapsulates all methods and fields related to ElasticSearch implementation.
 *
 * @author CMPUT301W18T06
 */

public class ElasticsearchController {

    private static JestDroidClient client;


    public static class AddUser extends AsyncTask<User, Void, String> {

        @Override
        protected String doInBackground(User... users) {
            verifySettings();

            for (User user : users){

                Index index = new Index.Builder(user).index("cmput301w18t06").type("user").build();

                try {
                    DocumentResult result = client.execute(index);

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

            for (User user : users){

                Index index = new Index.Builder(user).index("cmput301w18t06").type("user").id(user.getId()).build();

                try {
                    DocumentResult result = client.execute(index);

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

    public static class GetUser extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... search_params) {
            verifySettings();
            User user = new User();

            Get get = new Get.Builder("cmput301w18t06", search_params[0]).type("user").build();

            try {
                JestResult result = client.execute(get);

                if(result.isSucceeded()) {
                    user = result.getSourceAsObject(User.class);
                }
                else {
                    Log.i("Error", "The search query has failed");
                }
            } catch (Exception e) {
                //When no connection this occurs
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return user;
        }
    }

    public static class SearchUser extends AsyncTask<String, Void, UserList> {

        @Override
        protected UserList doInBackground(String... search_params) {
            verifySettings();
            ArrayList<User> tempList = new ArrayList<>();
            UserList userList = new UserList();

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("username", search_params[0]));

            Search search = new Search.Builder(searchSourceBuilder.toString())
                            .addIndex("cmput301w18t06")
                            .addType("user")
                            .build();

            JestResult result;

            try {
                result = client.execute(search);

                if(result.isSucceeded()) {
                    tempList = (ArrayList<User>) result.getSourceAsObjectList(User.class);
                    for (User user : tempList) {
                        userList.addUser(user);
                    }
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

            for (Task task : tasks){

                Index index = new Index.Builder(task).index("cmput301w18t06").type("task").build();

                try {
                    DocumentResult result = client.execute(index);

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

            for (Task task : tasks){
                Index index = new Index.Builder(task).index("cmput301w18t06").type("task").id(task.getId()).build();

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

    public static class GetTask extends AsyncTask<String, Void, Task> {

        @Override
        protected Task doInBackground(String... search_params) {
            verifySettings();
            Task task = new Task();

            Get get = new Get.Builder("cmput301w18t06", search_params[0]).type("task").build();

            try {
                JestResult result = client.execute(get);

                if(result.isSucceeded()) {
                    task = result.getSourceAsObject(Task.class);
                }
                else {
                    Log.i("Error", "The search query has failed");
                }
            } catch (Exception e) {
                //When no connection this occurs
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return task;
        }
    }

    public static class SearchTask extends AsyncTask<String, Void, TaskList> {

        @Override
        protected TaskList doInBackground(String... search_params) {
            verifySettings();
            ArrayList<Task> tempList = new ArrayList<Task>();
            TaskList taskList = new TaskList();

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.termQuery("description", search_params[0]));

            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex("cmput301w18t06")
                    .addType("task")
                    .build();

            JestResult result;

            try {
                result = client.execute(search);

                if(result.isSucceeded()) {
                    tempList = (ArrayList<Task>) result.getSourceAsObjectList(Task.class);
                    for (Task task : tempList) {
                        taskList.addTask(task);
                    }
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
