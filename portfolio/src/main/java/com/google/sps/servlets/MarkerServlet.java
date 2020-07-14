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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.sps.data.MarkerClass;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import java.util.List;
/** Handles fetching and saving markers data. */
@WebServlet("/markers")
public class MarkerServlet extends HttpServlet {
  /** Accepts a POST request containing a new marker. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
    /*Sends data of the new marker to the server */
    double lat = Double.parseDouble(request.getParameter("lat"));
    double lng = Double.parseDouble(request.getParameter("lng"));
    String content = Jsoup.clean(request.getParameter("content"), Whitelist.none());
    String title = Jsoup.clean(request.getParameter("title"),Whitelist.none());
    MarkerClass marker = new MarkerClass(lat, lng, title, content);
    storeMarker(marker);
    response.sendRedirect("/maps.html");
  }
  /** Stores a marker in Datastore. */
  public void storeMarker(MarkerClass marker) {
    Entity markerEntity = new Entity("Marker");
    markerEntity.setProperty("lat", marker.getLat());
    markerEntity.setProperty("lng", marker.getLng());
    markerEntity.setProperty("title",marker.getTitle());
    markerEntity.setProperty("content", marker.getContent());

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(markerEntity);
  }
    /** Responds with a JSON array containing marker data. */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    /* Gets data from the server and writes it to the user
    *
    * Arguments: 
    *   request: provides request information from the HTTP servlet
    *   response: response object where servlet will write information on
    */
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Marker");
    PreparedQuery results = datastore.prepare(query);
    List<MarkerClass> markerList = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      double lat = (double) entity.getProperty("lat");
      double lng = (double) entity.getProperty("lng");
      String title = (String) entity.getProperty("title");
      String content = (String) entity.getProperty("content");

      MarkerClass marker = new MarkerClass(lat, lng, title, content);
      markerList.add(marker);
    }
    
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(markerList);
    response.getWriter().println(json);
  }
}