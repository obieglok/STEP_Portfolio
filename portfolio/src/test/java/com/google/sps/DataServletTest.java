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

import com.google.sps.data.CommentsClass;
import com.google.sps.servlets.DataServlet;
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
import java.util.Date;
import java.io.*;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;
import static org.mockito.Mockito.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import static org.junit.Assert.assertTrue;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.util.Date;
import java.text.SimpleDateFormat;
@RunWith(JUnit4.class)
public final class DataServletTest extends DataServlet{
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
    DataServlet servlet = new DataServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);

    //set the parameters that will be requested to test values
    when(request.getParameter("username")).thenReturn("Klaudia");
    when(request.getParameter("comment")).thenReturn("Awesome Blog");

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    servlet.doPost(request, response);
    String result = stringWriter.toString();
    Assert.assertTrue(result.contains("\"author\":\"Klaudia\",\"comment\":\"Awesome Blog\""));
  }
  
  @Test
  public void testDoGetFunction() throws IOException{
    /*Tests the doGet function to see if the entities are retrieved from the
    * server correctly.
    */
    DataServlet servlet = new DataServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);

    Date date = new Date();
    //Set Date to match the json format
    SimpleDateFormat DateFor = new SimpleDateFormat(" MMM dd, yyyy hh:mm:ss aaa");
    DateFor = new SimpleDateFormat("\"MMM dd, yyyy h:mm:ss aaa\"");
    String stringDate = DateFor.format(date);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // Create a new comment entity for testing purposes
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("author", "klaudiaob");
    commentEntity.setProperty("comment", "Great Blog!");
    commentEntity.setProperty("date", date);

    datastore.put(commentEntity);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);
    servlet.doGet(request, response);

    String result = stringWriter.toString();
    String predictedResult= "\"currentTime\":"+stringDate+
      ",\"author\":\"klaudiaob\",\"comment\":\"Great Blog!\"";
    Assert.assertTrue(result.contains(predictedResult));
  }
}