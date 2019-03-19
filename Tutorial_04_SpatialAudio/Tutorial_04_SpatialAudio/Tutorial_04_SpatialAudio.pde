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
