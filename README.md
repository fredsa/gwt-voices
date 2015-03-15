# Summary #
Library providing easy to use cross-platform browser sound capabilities to [Google Web Toolkit](http://code.google.com/webtoolkit/) (GWT) projects.

# Features #
  * Automatic selection of HTML5 audio, native browser audio and Flash (if installed)
  * Straightforward API
```
SoundController soundController = new SoundController();
    Sound sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG,
        "http(s)/url/to/your/sound/file.mp3");
    sound.play();
```
# Questions? #
If you have questions, please post them on http://groups.google.com/group/gwt-voices and I (or someone else) will try to answer them as best as possible. Using the forum means that others can benefit from any answers and feedback you get. It is always the fastest way to get an answer to a new question.

# Is your project using gwt-voices? #
I'd like to know if you're using gwt-voices on your project, and how useful (or not) this library is to you. You can send me an email at [fred@allen-sauer.com](mailto:fred@allen-sauer.com?subject=gwt-voices).

# Getting started with your own gwt-voices project #
Read the wiki here: http://code.google.com/p/gwt-voices/wiki/GettingStarted

# Working examples #
  * Play [Hornet Blast](http://allen-sauer.com/com.allen_sauer.gwt.game.hornetblast.HornetBlast/HornetBlast.html) or watch it on [YouTube](http://www.youtube.com/watch?v=ViCyl-WNIeI#t=15m30s).
  * Try the [sound demo](http://allen-sauer.com/com.allen_sauer.gwt.voices.demo.VoicesDemo/VoicesDemo.html):

> [![](https://gwt-voices.googlecode.com/files/33637__HerbertBoland__CinematicBoomNorm-2007-09-19.png)](http://allen-sauer.com/com.allen_sauer.gwt.voices.demo.VoicesDemo/VoicesDemo.html)
  * Try Chris Fong's demo: http://www.gwtsite.com/gwt-voices-demo/
# Feedback #
Please let me know what you think. Suggestions are always welcome.

# Other GWT projects by the same author #
| **Project** | **Description** |
|:------------|:----------------|
| [gwt-dnd](http://code.google.com/p/gwt-dnd/) | Provides drag and drop support for your GWT applications. |
| [gwt-log](http://code.google.com/p/gwt-log/) | Provides logging support for your GWT applications. |