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

/**
 * Allows user to guess my next trip destination.
 */
function guessMyTrip() {
  const places = 
    ["thailand", "singapore", "tokyo", "australia", "peru",
         "hawaii", "vietnam"];

  let input = document.getElementById("user_input").value;
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
async function getName(){
    const response = await fetch('/data');
    const name = await response.text();
    document.getElementById('showName').innerHTML=name;

}
