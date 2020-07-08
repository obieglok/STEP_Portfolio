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
    private  Date currentTime;
    private final String author;
    private final String comment;
    private final long id;

    public CommentsClass(Date currentTime, String author, String comment,long id)
    {
        this.currentTime = currentTime;
        this.author = author;
        this.comment = comment;
        this.id = id;
    }
    public Date getCurrentTime(){
        return currentTime;
    }
    public String getAuthor(){
        return author;
    }
    public String getComment(){
        return comment;
    }
    public long getId(){
        return id;
    }
}