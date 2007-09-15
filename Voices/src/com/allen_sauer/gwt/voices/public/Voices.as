/*
 * Copyright 2007 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
import flash.external.ExternalInterface;

class Voices
{
  var sounds:Array = [];
  
  function Voices() {
    Voices.log("Creating Voices...");

    Voices.log("ExternalInterface.available = " + ExternalInterface.available);
    
    var wasSuccessful:Boolean;
    
    wasSuccessful = ExternalInterface.addCallback("createSound", this, createSound);
    Voices.log("addCallback(createSound) -> " + wasSuccessful);
    
    wasSuccessful = ExternalInterface.addCallback("playSound", this, playSound);
    Voices.log("addCallback(playSound) -> " + wasSuccessful);
    
    Voices.log("Voices created.");

    // notify JavaScript that we are ready
    ExternalInterface.call("document.VoicesMovie.ready");
  }

  function createSound(id:Number, url:String, streaming:Boolean):Void {
    sounds[id] = new Sound();
    sounds[id].onLoad = function() {
      ExternalInterface.call("document.VoicesMovie.soundLoaded", id);
    }
    sounds[id].onSoundComplete = function() {
      ExternalInterface.call("document.VoicesMovie.soundCompleted", id);
    }
    sounds[id].loadSound(url, streaming);
  }
  
  function playSound(id:Number):Void {
    sounds[id].start();
  }
  
  static function log(text:String) {
//    ExternalInterface.call("document.VoicesMovie.log", text);
  }
  
  static function main(mc:MovieClip) {
    var app:Voices = new Voices();
  }
}