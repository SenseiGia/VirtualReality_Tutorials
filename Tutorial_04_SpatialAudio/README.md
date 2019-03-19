# Tutorial 04: Spatial Audio
Note: Before progressing with this tutorial, ensure you followed the steps to prepare both your hardware and software explained at the top level of this repository. It would also be helpful to follow the previous tutorials that lead up to the current.

## Overview of tutorial
This tutorial explores the ability to add audio into Android VR applications and utilize the ability to spatially play audio for a more immersive experience. 

## Create your project
You should be familiar with how to create a project, so create a new sketch and save it with you application name in a memorable location on you computer.

## Including the necessary libraries
Up to this point in the tutorials, we have been able to rely on Processing for all the heavy lifting for simplifying our projects. Even though Processing handles many things, we are not limited from using the native Android Studio Libraries for development. Processing is mostly a visual library so we will have to rely on the MediaPlayer class to create our Audio.

```Javascript
/** Library for Android Virtual Reality */
import processing.vr.*;

/** Libraries to include for Audio */
import android.media.MediaPlayer;     // Media Player for Audio Playback
import android.content.Context;                   // Required
import android.app.Activity;                      // Access to Activity
import android.content.res.AssetFileDescriptor;   // Grab Asset / Audio File
```

## Add the Audio playback
With our newly imported libraries, we can now acccess some essential variables for audio playback on the Android device. In the following example, the audio just plays as soon as the intialization (setup()) method is completed.

```Javascript
import processing.vr.*;
import android.media.MediaPlayer;
import android.content.Context;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;

MediaPlayer mp;
Context context;
Activity act;
AssetFileDescriptor afd;

void setup() {
  /* Blank Screen Application, doesn't really matter */
  fullScreen(Mono);
  
  /* Get Activity and context for application */
  act = this.getActivity();
  mp = new MediaPlayer();
  context = act.getApplicationContext();
  try{
    /* Load Audio File */
  afd = context.getAssets().openFd("Home_15.mp3");
  }catch(IOException e){
    /* Added to allow compilation */
    println("Failed");
  }
  
  /* Start playing audio */
  mp.start();
}

void draw() {
  /* Not used yet */
}
```

## Adding more Models to the project
This Project is improving upon the object load example from the first turoial. This Project will allow for multiple audio files to play, depending on which audio has been activated by a signal change. The audio will come from the proper source and adjust itself based on the tilt of of the Android device.

Start off by creating more models in the project.

```Javascript
import processing.vr.*;
import android.media.MediaPlayer;
import android.content.Context;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;

PShape Model;
PShape Model2;
PShape Model3;

int RotationX = 0;
int RotationY = 0;
int RotationZ = 0;

MediaPlayer mp;
Context context;
Activity act;
AssetFileDescriptor afd;

void setup() {
  /** Size does not work vr applications */
  fullScreen(STEREO);
  cameraUp();
  
  act = this.getActivity();
  mp = new MediaPlayer();
  context = act.getApplicationContext();
  try{
  afd = context.getAssets().openFd("Home_15.mp3");
  }catch(IOException e){
    println("Failed");
  }
  
  /* Load and configure Model */
  Model = loadShape("ar_tutorial.obj");
  Model.scale(45);
  Model.rotateY(0.8 * PI);
  Model.translate(-600, -70, 0);
  
  /* Load and configure Model */
  Model2 = loadShape("mr_tutorial_v3.obj");
  Model2.scale(20);
  Model2.rotateY(1.8);
  Model2.translate(0, -70, 0);
  
  /* Load and configure Model */
  Model3 = loadShape("vr_tutorial_v3.obj");
  Model3.scale(35);
  Model3.rotateY(1.4 * PI);
  Model3.translate(600, -70, 0);
  
  mp.start();
}

void draw() {
  /* Load background, which in this situation is a solid color */
  background(255);
  
  /* Use lights to reveal details on generated model, source exists where? */
  lights();
  
  shape(Model, 0, 0);
  shape(Model2, 0, 0);
  shape(Model3, 0, 0);
}
```

