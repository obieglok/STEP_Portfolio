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

package com.google.sps;

import com.google.sps.data.MarkerClass;
import com.google.sps.servlets.MarkerServlet;
import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import javax.servlet.http.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.io.*;
import com.google.gson.Gson;
import static org.mockito.Mockito.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import static org.junit.Assert.assertTrue;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.util.List;
@RunWith(JUnit4.class)
public final class MarkerServletTest extends MarkerServlet{
  private final LocalServiceTestHelper helper = 
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
  @Before
  public void setUp() {
    helper.setUp();
  }

  @After
  public void tearDown() {
    helper.tearDown();
  }

  @Test
  public void testdoPostFunction() throws IOException{
  /*Tests the doPost function to see if the parameters get treated properly */
    MarkerServlet servlet = new MarkerServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);

    //set the parameters that will be requested to test values
    when(request.getParameter("lat")).thenReturn("53.3498");
    when(request.getParameter("lng")).thenReturn("-6.2603");
    when(request.getParameter("title")).thenReturn("Dublin");
    when(request.getParameter("content")).thenReturn("This is where you live");

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);
    servlet.doPost(request, response);
    String result = stringWriter.toString();
    String predictedResult = "{\"lat\":53.3498,\"lng\":-6.2603,\"title\":\"Dublin\","+
      "\"content\":\"This is where you live\"}";
    
    Assert.assertTrue(result.contains(predictedResult));
  }
  @Test
  public void testdoGetFunction() throws IOException{
    /*Tests the doGet function to see if marker gets retrieved properly */
    MarkerServlet servlet = new MarkerServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // Create a new marker entity for testing purposes
    Entity markerEntity = new Entity("Marker");
    markerEntity.setProperty("lat", (double)1.3521);
    markerEntity.setProperty("lng", (double)103.8198);
    markerEntity.setProperty("title", "Singapore");
    markerEntity.setProperty("content", "This will be your first stop!");

    datastore.put(markerEntity);
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);
    servlet.doGet(request, response);

    String result = stringWriter.toString();
    String predictedResult = "{\"lat\":1.3521,\"lng\":103.8198,\"title\":\"Singapore\","+
      "\"content\":\"This will be your first stop!\"}";
    Assert.assertTrue(result.contains(predictedResult));
  }
}