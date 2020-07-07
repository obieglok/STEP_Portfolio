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

import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/** Servlet that returns comments*/
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private ArrayList<String> comments = new ArrayList<String>();


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    /* Gets a request from user to get comments
    *
    * Arguments: 
    *   request: provides request information from the HTTP servlet
    *   response: response object where servlet will write information on
    */

    populateComments(comments);
    String json = convertToJsonUsingGson(comments);
    response.setContentType("application/json;");
    response.getWriter().println(json);
    response.getWriter().println(counter);
  }
    
  private void populateComments(ArrayList comments)
  {
    /*Fills the comments ArrayList with hard coded comments. */
    comments.add("Great Blog!");
    comments.add("Amazing travel photos!");
    comments.add("You should make our website for us!");
  }
  private String convertToJsonUsingGson(ArrayList comments) {
    /* Converts the comments ArrayList to a json string using Gson
    *
    *Arguments: comments ArrayList that is populated with comments
    *Returns: json String of the comments ArrayList
    *
    */
    Gson gson = new Gson();
    String json = gson.toJson(comments);
    return json;
  }
}
