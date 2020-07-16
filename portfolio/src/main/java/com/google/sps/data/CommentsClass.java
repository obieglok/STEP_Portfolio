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

package com.google.sps.data;

import java.util.Date;

public final class CommentsClass{
  /*Class that creates comments from users input  */
  private final Date currentTime;
  private final String author;
  private final String comment;
  private final long id;

  public CommentsClass(Date currentTime, String author, String comment,long id)
  {
    /* Constructor for the comments Class
    * Arguments: 
    *  currentTime= Time that the comment was created.
    *  author = User who wrote the comment.
    *  comment = The comment the user wrote.
    *  id = id taken from the entity key value that identifies the comment.
    *
    */
    this.currentTime = currentTime;
    this.author = author;
    this.comment = comment;
    this.id = id;
  }
  public Date getCurrentTime(){
    /*Getter method to get the current time*/ 
    return currentTime;
  }
  public String getAuthor(){
    /*Getter method to get the author's name */
    return author;
  }
  public String getComment(){
    /*Getter method to get the comment */
    return comment;
  }
  public long getId(){
    /* Getter method to get the comment id*/
    return id;
  }
}