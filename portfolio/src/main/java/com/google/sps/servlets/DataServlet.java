// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;
import com.google.sps.data.CommentsClass;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.Date;

/** Servlet that returns comments*/
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    /*Sends data of the new comment to the server */
    Date currentTime = new Date();
    String username = getParameter(request, "username", "");
    String comment = getParameter(request, "comment", "");

    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("author", username);
    commentEntity.setProperty("comment", comment);
    commentEntity.setProperty("date", currentTime);
    commentEntity.setProperty("id", commentEntity.getKey().getId());

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    Gson gson =new Gson();
    response.setContentType("application/json");
    response.getWriter().println(gson.toJson(commentEntity));
    response.sendRedirect("/comments.html");

  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue){
    /* Gets Parameters from the Users Page
     *
     * Return: Returns the requested parameter or the default value if the parameter
     *  wasn't specified by the User.   
     */
    String value = request.getParameter(name);
    if(value == null){
        return defaultValue;
    }
    return value;
  }

  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    /* Gets data from the server and writes it to the user
    *
    * Arguments: 
    *   request: provides request information from the HTTP servlet
    *   response: response object where servlet will write information on
    */
    Query query = new Query("Comment").addSort("date", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<CommentsClass> commentsList = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String author = (String) entity.getProperty("author");
      String comment = (String) entity.getProperty("comment");
      Date date = (Date) entity.getProperty("date");
      
      CommentsClass commentR = new CommentsClass(date, author, comment, id);
      commentsList.add(commentR);
    }

    String json = convertToJsonUsingGson(commentsList);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertToJsonUsingGson(List<CommentsClass> comments) {
    /* Converts the comments List to a json string using Gson
    *
    *Arguments: comments ArrayList that is populated with comments
    *
    *Returns: json string of the comments
    *
    */
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }
}