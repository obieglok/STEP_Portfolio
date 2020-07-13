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


function guessMyTrip() {
/**
 * Allows user to guess my next trip destination.
 */
  const places = 
    ["thailand", "singapore", "tokyo", "australia", "peru",
         "hawaii", "vietnam"];

  let input = document.getElementById("user_input").value;

  /** Checks if the place is part of the trip destination list, and
  returns either postive or negative feedback */
  if (places.includes(input.toLowerCase())) {
      document.getElementById('display').innerHTML = 
        "Well done you guessed it correctly! " 
        + input 
        + " is one of the places I will be travelling" 
        + " to on my Round the World Trip in 2022!";
  }
  else {
    document.getElementById('display').innerHTML = 
        "Unfortunately you guessed it wrong!, Try Again!";
  }
}
function moreFacts(){
/**
 * Allows the user to find out more facts about myself
   by randomly selecting a fact when a button is clicked.
 */
  const facts = ["I have hosted Jeff Weiner in Trinity College",
    "I took part in my first ever hackathon with JP Morgan in 2019.",
    "Once JP Morgan flew me business class to London! (amazing experience)",
    "I spend a lot of time volunteering in college.",
    "I would love to have a job which allows me to travel.",
    "I am preparing for a 5 month backpacking trip !"];

  let rand = Math.floor(Math.random() * facts.length);
  document.getElementById("displayFact").innerHTML= facts[rand];
  document.getElementById("displayFact").style.border="double";
}
function getComments(){
/**
 * Get's comments from the server and displays them in the table
 * by creating new rows.
 */
  fetch('/data').then(response => response.json()).then((comments) => {
    const displayComment = document.getElementById('comment') ;
    comments.forEach((comment) => {
      /*For Each comment we create a seperate paragraph 
      * which includes the date at which the comment was
      * created on, the author and the comment itself.
      */
      const commentElement = document.createElement('P');
      commentElement.className = 'commentSection';
      
      const userComment = document.createElement('span');
      userComment.innerText ="On " + comment.currentTime + " , " 
        + comment.author +" said: " + "\n" +"\n" + comment.comment
        + "\n" + "\n"; 

      const deleteCommentButton = document.createElement('button');
      deleteCommentButton.className = "button";
      deleteCommentButton.innerText = "Delete";
      deleteCommentButton.addEventListener('click', () => {
        deleteComment(comment);

        commentElement.remove();
      })

      commentElement.appendChild(userComment);
      commentElement.appendChild(deleteCommentButton);
      displayComment.appendChild(commentElement);

      })
 });
}

function deleteComment(comment){
  /* Deletes the comment for which the delete button was 
  *  clicked.
  */
  const parameter = new URLSearchParams();
  parameter.append('id', comment.id);
  fetch('/delete-comment',{method: 'POST', body: parameter});
}

function createMap() {
/*Function to create a Google Maps Map
* It includes markers for all the locations on my Round the World
* Trip and a line which connects all the places.
*
*/
  let myLatlng = new google.maps.LatLng(53.3498, -6.2603);
  let mapOptions = {
    zoom: 5, 
    center: myLatlng,
    mapTypeId: 'satellite'
  };
  let map = new google.maps.Map(document.getElementById('map'),
    mapOptions);

  addLandmark(map, 53.3498, -6.2603, 'Dublin',
    'This is where I am based and my journey will start here.');

  addLandmark(map, 1.3521, 103.8198, 'Singapore',
    'This is will be the first stop of my journey! Some of the things' +
    'we planed to do include going to Universal studios and' +
    'going to see night time safari');

  addLandmark(map, 13.7563, 100.5018, 'Bangkok',
    'Afer a week in Singapore we will land in Bangkok, Thailand '+ 
    'here we will spend around a week before continuing on to travel'
    +'all around thailand for around a month');

  addLandmark(map, 12.5657, 104.9910, 'Cambodia',
    'After a  little over a month of travelling in Thailand we '+ 
    'will continue on to Cambodia where we will spend a couple of'+
    'weeks');

  addLandmark(map, 14.0583, 108.2772, 'Vietnam', 
    'After some time in Cambodia, we will be spending some time in'+
    'Vietnam. Cannot wait to experience the culture!');

  addLandmark(map, 36.2048, 138.2529, 'Japan', 
    'After Vietnam we will fly to Japan and spend some time here'+
    'Can\'t wait for some good sushi!');

  addLandmark(map, -36.848461, 174.8860, 'New Zealand',
    'Then we will spend couple of weeks hiking in New Zealand!');

  addLandmark(map, 19.8968, -155.844437, 'Hawaii', 
    'Then a nice week long break in Hawaii to soak up some sun!');

  addLandmark(map, -9.1900, -75.0152, 'Peru', 
    'And our last stop! Cannot wait to climb up Machu Picchu!');
    
  //Connects all the destinations with a line for easy viewing
  let flightPlanCoordinates = [
    {lat: 53.3498, lng: -6.2603},
    {lat: 1.3521, lng: 103.8198},
    {lat: 13.7563, lng: 100.5018},
    {lat: 12.5657, lng: 104.9910},
    {lat: 14.0583, lng: 108.2772},
    {lat: 36.2048, lng: 138.2529},
    {lat: -36.848461, lng: 174.8860},
    {lat: 19.8968, lng: -155.844437},
    {lat: -9.1900, lng: -75.0152},
  ];
  let flightPath = new google.maps.Polyline({
    path: flightPlanCoordinates,
    geodesic: true,
    strokeColor: '#FF0000',
    strokeOpacity: 1.0,
    strokeWeight: 2,
  });

  flightPath.setMap(map);
}
function addLandmark(map, lat, lng, title, description){
/*Function that creates a landmark pin on the Google Maps Map 
* Arguments: 
* map - the Google Map where the landmark pins will be displayed on
* lat - the latitude of the location
* lng - the longtitude of the location
* title - the name of the location
* desccription - description of the landmark
*/
  const marker = new google.maps.Marker(
    {position: {lat: lat, lng: lng}, map: map, title: title ,
    animation: google.maps.Animation.DROP});

  const infoWindow = new google.maps.InfoWindow({content: description});
  marker.addListener('click', () => {
    infoWindow.open(map, marker);
  });
}