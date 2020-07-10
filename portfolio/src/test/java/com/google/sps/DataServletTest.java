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
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.google.gson.Gson;

@RunWith(JUnit4.class)
public final class DataServletTest extends DataServlet{

  @Test
  public void testConvertingToJSonUsingGson() {
    /*Tests whether the ConvertToJsonUsingGson Function in Data Servlet works */
    List<CommentsClass> commentsList = new ArrayList<>();
    DataServlet servlet = new DataServlet();
    final Date date = new Date();
    SimpleDateFormat DateFor = new SimpleDateFormat(" MMM dd, yyyy hh:mm:ss aaa");
    DateFor = new SimpleDateFormat("\"MMM dd, yyyy hh:mm:ss aaa\"");
    String stringDate = DateFor.format(date);
    CommentsClass trialComment = new CommentsClass(date, "Klaudia","Awesome Blog",2147483649L);
    commentsList.add(trialComment);
    String json = servlet.getJsonString(commentsList);
    String actual= "[{\"currentTime\":"+stringDate+
      ",\"author\":\"Klaudia\",\"comment\":\"Awesome Blog\",\"id\":2147483649}]";
    Assert.assertEquals (actual,json);
  }
}