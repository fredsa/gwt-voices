/*
 * Copyright 2011 Fred Sauer
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

 
 // See http://livedocs.adobe.com/flash/9.0/ActionScriptLangRefV3/flash/media/Sound.html

 package {
  import flash.external.ExternalInterface;
  import flash.media.Sound;
  import flash.display.Sprite;
  import flash.events.Event;
  import flash.events.IOErrorEvent;
  import flash.media.SoundChannel;
  import flash.net.URLRequest;
  
  public class Voices extends Sprite
  {
    private var sounds:Array = [];
    private var loop:Array = [];
    private var domId:String;
    
    public function Voices() {
      domId = this.root.loaderInfo.parameters.id;
      log("***************************************************************************************************");
      log("Creating Voices '" + domId + "' ...");
      
      log("ExternalInterface.available = " + ExternalInterface.available);
      log("ExternalInterface.objectID = " + ExternalInterface.objectID);
      
      var result:Object;
      
      ExternalInterface.addCallback("createSound", createSound);
      log("addCallback(createSound) -> done");
      
      ExternalInterface.addCallback("playSound", playSound);
      log("addCallback(playSound) -> done");
      
      ExternalInterface.addCallback("stopSound", stopSound);
      log("addCallback(stopSound) -> done");
      
      ExternalInterface.addCallback("setVolume", setVolume);
      log("addCallback(setVolume) -> done");
  
      ExternalInterface.addCallback("setBalance", setBalance);
      log("addCallback(setBalance) -> done");
  
      log("Voices created.");
  
      // notify JavaScript that we are ready
      result = ExternalInterface.call("document.VoicesMovie['" + this.root.loaderInfo.parameters.id + "'].ready");
      log("document.VoicesMovie.ready() ... -> " + result);
    }

    private function createSound(id:Number, url:String):void {
      log("createSound(id=" + id + ", url='" + url + "')...");

      // default to false
      loop[id] = false;
      
      sounds[id] = new Sound();
      sounds[id].addEventListener(Event.COMPLETE, function(event:Event):void {
        log("Event.COMPLETE (=Sound Loaded) id=" + id);
        var result:Object = ExternalInterface.call("document.VoicesMovie['" + domId + "'].soundLoaded", id);
        log("document.VoicesMovie['" + domId + "'].soundLoaded(" + id + ") -> " + result + " / " + typeof result);
      });
//      sounds[id].addEventListener(IOErrorEvent.IO_ERROR, function(event:Event):void {
//        log("IOErrorEvent.IO_ERROR id=" + id);
//        var result:Object = ExternalInterface.call("document.VoicesMovie['" + this.root.loaderInfo.parameters.id + "'].soundLoaded", id);
//        log("document.VoicesMovie['" + this.root.loaderInfo.parameters.id + "'].soundLoaded(" + id + ") -> " + result);
//      });
      sounds[id].load(new URLRequest(url));
      log("...createSound(id=" + id + ", url='" + url + "')");
    }
    
    private function playSound(id:Number):void {
      log("playSound(id=" + id + ")");
      
      var channel:SoundChannel = sounds[id].play(0, loop[id]?int.MAX_VALUE:0);
      channel.addEventListener(Event.SOUND_COMPLETE, function(event:Event):void {
        log("Event.SOUND_COMPLETE (=Playback Completed) id=" + id);
        var result:Object = ExternalInterface.call("document.VoicesMovie['" + this.root.loaderInfo.parameters.id + "'].playbackCompleted", id);
        log("document.VoicesMovie['" + this.root.loaderInfo.parameters.id + "'].playbackCompleted(" + id + ") -> " + result);
      });
    }
    
    private function stopSound(id:Number):void {
      log("stopSound(id=" + id + ")");
      sounds[id].stop();
    }

    private function setVolume(id:Number, volume:Number):void {
      log("setVolume(id=" + id + ", volume=" + volume + "%)");
      sounds[id].setVolume(volume);
    }
    
    private function setBalance(id:Number, balance:Number):void {
      log("setBalance(id=" + id + ", balance=" + balance + ")");
      sounds[id].setPan(balance);
    }

    private function log(text:String):void {
      //ExternalInterface.call("document.VoicesMovie['" + this.root.loaderInfo.parameters.id + "'].log", text);
    }
    
  }
}