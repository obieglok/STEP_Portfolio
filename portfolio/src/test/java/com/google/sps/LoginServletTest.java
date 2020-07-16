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

import com.google.sps.servlets.LoginServlet;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import javax.servlet.http.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import java.io.*;


@RunWith(JUnit4.class)
public final class LoginServletTest extends LoginServlet{
  @Test
  public void testDoGetFunctionUserLoggedOut() throws IOException{
    /*Tests do Get Function for a loggedOut user */
    LocalServiceTestHelper helper = 
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    helper.setUp();

    LoginServlet servlet = new LoginServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);

    //mock User Service to pretend that a user is logged out
    UserService userService = mock(UserService.class);
    when(userService.isUserLoggedIn()).thenReturn(false);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    servlet.doGet(request, response);
    String result = stringWriter.toString();
    String answer = "\"loginUrl\":\"/_ah/login?continue\\u003d%2Fcomments.html\",\"email\":\"\"";
    Assert.assertTrue(result.contains(answer));

    helper.tearDown();
  }
  @Test
  public void testdoGetFunctionUserLoggedIn() throws IOException{
    /*test do getFunction for loggedIn Users.*/

    //Set up Local Service Test Helper to simulate a logged in user with
    // a test@example.com email
    LocalServiceTestHelper helper = 
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())
      .setEnvIsLoggedIn(true).setEnvEmail("test@example.com");
    helper.setUp().setEnvAuthDomain("example.com");

    LoginServlet servlet = new LoginServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);       
    HttpServletResponse response = mock(HttpServletResponse.class);

    //Mock userService to pretend that the user is logged in
    UserService userService = mock(UserService.class);
    when(userService.isUserLoggedIn()).thenReturn(true);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    when(response.getWriter()).thenReturn(writer);

    servlet.doGet(request, response);
    String result = stringWriter.toString();
    String answer = "{\"logoutUrl\":\"/_ah/logout?continue\\u003d%2Fcomments.html\",\"email\":"
      +"\"test@example.com\"}";
    Assert.assertTrue(result.contains(answer));

    helper.tearDown();
  }
}