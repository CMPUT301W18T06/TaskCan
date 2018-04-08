package com.example.n8tech.taskcan.Controller;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.n8tech.taskcan.Models.Bid;
import com.example.n8tech.taskcan.Models.CurrentUserSingleton;
import com.example.n8tech.taskcan.Models.Image;
import com.example.n8tech.taskcan.Models.Task;
import com.example.n8tech.taskcan.Models.TaskList;
import com.example.n8tech.taskcan.Models.User;
import com.example.n8tech.taskcan.Models.UserList;
import com.google.android.gms.maps.model.LatLng;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.Collection;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.MultiSearch;
import io.searchbox.core.Search;

/**
 * ElasticsearchController encapsulates all methods and fields related to ElasticSearch implementation.
 *
 * @author CMPUT301W18T06
 */

public class ElasticsearchController {
    private static String ELASTIC_SEARCH_NAME = "cmput301w18t06";
    private static String NETWORK_ERROR = "NetworkError";
    private static String ACCESS_ERROR = "AccessError";
    private static String NO_NETWORK_ERROR = "NoNetworkError";

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
                user.setId(update("user", user, user.getId()));
            }
            return NO_NETWORK_ERROR;
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

    public static class DeleteUser extends AsyncTask<User, Void, String> {

        @Override
        protected String doInBackground(User... search_params) {
            verifySettings();
            for(User user : search_params) {

                Delete del = new Delete.Builder(user.getId()).index("cmput301w18t06").type("user").build();

                try {
                    JestResult result = client.execute(del);

                    if (!result.isSucceeded()) {
                        Log.i("Error", "The search query has failed");
                    }
                } catch (Exception e) {
                    //When no connection this occurs
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                    return "NetworkError";
                }
            }
            return "NoNetworkError";
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
                task.setId(update("task", task, task.getId()));
            }
            return NO_NETWORK_ERROR;
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

    public static class DeleteTask extends AsyncTask<Task, Void, String> {

        @Override
        protected String doInBackground(Task... search_params) {
            verifySettings();
            for(Task task : search_params) {

                Delete del = new Delete.Builder(task.getId()).index("cmput301w18t06").type("task").build();

                try {
                    JestResult result = client.execute(del);

                    if (!result.isSucceeded()) {
                        Log.i("Error", "The search query has failed");
                    }
                } catch (Exception e) {
                    //When no connection this occurs
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                    return "NetworkError";
                }
            }
            return "NoNetworkError";
        }
    }

    public static class SearchTask extends AsyncTask<String, Void, TaskList> {

        @Override
        protected TaskList doInBackground(String... search_params) {
            verifySettings();
            ArrayList<Task> tempList = new ArrayList<Task>();
            TaskList taskList = new TaskList();
            int start = 0;
            int oldSize = 0;
            int newSize = -1;
            JestResult result;

            while(oldSize != newSize) {
                oldSize = newSize;

                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                searchSourceBuilder.from(start).size(100);

                Search search = new Search.Builder(searchSourceBuilder.toString())
                        .addIndex("cmput301w18t06")
                        .addType("task")
                        .build();

                try {
                    result = client.execute(search);

                    if (result.isSucceeded()) {
                        tempList = (ArrayList<Task>) result.getSourceAsObjectList(Task.class);
                        for (Task task : tempList) {
                            if (!task.getStatus().equals("Completed") && !task.getStatus().equals("Assigned")) {
                                if(task.getTaskTitle().toLowerCase().contains(search_params[0].toLowerCase()) ||
                                        task.getDescription().toLowerCase().contains(search_params[0].toLowerCase())) {
                                    taskList.addTask(task);
                                }
                            }
                        }
                    } else {
                        Log.i("Error", "The search query has failed");
                    }
                } catch (Exception e) {
                    //When no connection this occurs
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }
                newSize = taskList.getSize();
                start = start + 100;
            }

            return taskList;
        }
    }

    public static class SearchLocation extends AsyncTask<String, Void, TaskList> {

        private LatLng currentLocation;
        private double radius;

        public SearchLocation(LatLng location, double radius){
            this.currentLocation = location;
            this.radius = radius;
        }

        @Override
        protected TaskList doInBackground(String... search_params) {
            verifySettings();
            ArrayList<Task> tempList = new ArrayList<Task>();
            TaskList taskList = new TaskList();
            float[] distResults = new float[1];
            int start = 0;
            int oldSize = 0;
            int newSize = -1;
            JestResult result;

            while(oldSize != newSize) {
                oldSize = newSize;

                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                searchSourceBuilder.from(start).size(100);

                Search search = new Search.Builder(searchSourceBuilder.toString())
                        .addIndex("cmput301w18t06")
                        .addType("task")
                        .build();


                try {
                    result = client.execute(search);

                    if (result.isSucceeded()) {
                        tempList = (ArrayList<Task>) result.getSourceAsObjectList(Task.class);
                        for (Task task : tempList) {
                            if (task.getLocation() != null) {
                                Location.distanceBetween(this.currentLocation.latitude, this.currentLocation.longitude,
                                        task.getLocation().latitude, task.getLocation().longitude,
                                        distResults);
                                Log.i("dist: ", String.valueOf(distResults[0]));
                                if (distResults[0] <= radius && !task.getStatus().equals("Completed") && !task.getStatus().equals("Assigned")) {
                                    taskList.addTask(task);
                                }

                            } else {
                                Log.i("Error", "The search query has failed");
                            }
                        }
                    }
                } catch (Exception e) {
                    //When no connection this occurs
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }
                newSize = taskList.getSize();
                start = start + 100;
            }
            return taskList;
        }
    }

    public static class SearchCategoryTask extends AsyncTask<String, Void, TaskList> {

        @Override
        protected TaskList doInBackground(String... search_params) {
            verifySettings();
            ArrayList<Task> tempList = new ArrayList<Task>();
            TaskList taskList = new TaskList();
            int start = 0;
            int oldSize = 0;
            int newSize = -1;
            JestResult result;

            while(oldSize != newSize) {
                oldSize = newSize;

                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                searchSourceBuilder.query(QueryBuilders.matchQuery("category", search_params[0]));
                searchSourceBuilder.from(start).size(100);

                Search search = new Search.Builder(searchSourceBuilder.toString())
                        .addIndex("cmput301w18t06")
                        .addType("task")
                        .build();

                try {
                    result = client.execute(search);

                    if(result.isSucceeded()) {
                        tempList = (ArrayList<Task>) result.getSourceAsObjectList(Task.class);
                        for (Task task : tempList) {
                            if(!task.getStatus().equals("Completed") && !task.getStatus().equals("Assigned"))
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
                newSize = taskList.getSize();
                start = start + 100;
            }
            return taskList;
        }
    }

    public static class AddImage extends AsyncTask<Image, Void, String> {

        @Override
        protected String doInBackground(Image... images) {
            verifySettings();
            String id = "";

            for (Image image : images){
                id = "";

                Index index = new Index.Builder(image).index("cmput301w18t06").type("image").build();

                try {
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded()) {
                        id = result.getId();
                    }
                    else {
                        Log.i("Error", "Elasticsearch was not able to add the user");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and add the user");
                    return "NetworkError";

                }
            }
            return id;
        }

    }

    public static class GetImage extends AsyncTask<String, Void, Image> {

        @Override
        protected Image doInBackground(String... search_params) {
            verifySettings();
            Image image = null;

            Get get = new Get.Builder("cmput301w18t06", search_params[0]).type("image").build();

            try {
                JestResult result = client.execute(get);

                if(result.isSucceeded()) {
                    image = result.getSourceAsObject(Image.class);
                }
                else {
                    Log.i("Error", "The search query has failed");
                }
            } catch (Exception e) {
                //When no connection this occurs
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return image;
        }
    }

    public static class DeleteImage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... search_params) {
            verifySettings();
            for(String id : search_params) {

                Delete del = new Delete.Builder(id).index("cmput301w18t06").type("image").build();

                try {
                    JestResult result = client.execute(del);

                    if (!result.isSucceeded()) {
                        Log.i("Error", "The search query has failed");
                    }
                } catch (Exception e) {
                    //When no connection this occurs
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                    return "NetworkError";
                }
            }
            return "NoNetworkError";
        }
    }


    public static <T> String update(String content, T object, String objectID) {
        Index index = new Index.Builder(object).index(ELASTIC_SEARCH_NAME).type(content).id(objectID).build();

        try {
            DocumentResult result = client.execute(index);

            Log.i("Testing", result.getJsonString());
            if(result.isSucceeded()) {
                return result.getId();
            }
            else {
                Log.i("Error", "Elasticsearch was not able to update");
                return ACCESS_ERROR;
            }
        } catch (Exception e) {
            Log.i("Error", "The application failed to build and update");
            return NETWORK_ERROR;

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
