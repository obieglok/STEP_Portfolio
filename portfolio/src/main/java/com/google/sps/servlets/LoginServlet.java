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

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

// Servlet that checks if a user is logged in
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
    //Checks if the user is logged in or not.

    UserService userService = UserServiceFactory.getUserService();
    response.setContentType("application/json");
    Map<String, String> logMap = new HashMap<String,String>();

    //if user is  logged out create a login URL.
    if(!userService.isUserLoggedIn()){
      String loginUrl = userService.createLoginURL("/comments.html");
      logMap.put("email","");
      logMap.put("loginUrl", loginUrl);
    }
    else if(userService.isUserLoggedIn()){
      //else if user is logged in create a logout URL
      String email = userService.getCurrentUser().getEmail();
      String logoutUrl = userService.createLogoutURL("/comments.html");
      logMap.put("email", email);
      logMap.put("logoutUrl", logoutUrl);
    }
    response.getWriter().println(convertToJson(logMap));
  }
    private String convertToJson(Map<String,String> logMap) {
    /* Converts the logMap into a Json String
    *
    *Arguments: Hash Mao that stores the users email and login/logout URL
    *
    *Returns: json string of the hash map
    *
    */
    Gson gson = new Gson();
    String json = gson.toJson(logMap);
    return json;
  }
}