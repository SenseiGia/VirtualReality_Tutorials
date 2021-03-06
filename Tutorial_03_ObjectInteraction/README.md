# Tutorial 03: Object Interaction
Note: Before progressing with this tutorial, ensure you followed the steps to prepare both your hardware and software explained at the top level of this repository. It would also be helpful to follow the previous tutorials that lead up to the current.

## Overview of tutorial
This tutorial goes through the primary method of interaction with the screen, which is the touchscreen and how to extract valueable information to allow for interaction and processing.

## Create your project
You should be familiar with how to create a project, so create a new sketch and save it with you application name in a memorable location on you computer.

## Using the mouse functions for the project
Processing was originally created for design on a computer, which is genrally used with a mouse. Processing for Android only builds upon it. Therefore, it registers touchscreen presses as if it was a mouse press. Because there is an existing library, Processing has many functions and variables for use when trying to read the screen. 

## Functions for mouse (touchscreen)
Below are some of the common functions related to mouse.
```Javascript
/* Functions */
mouseMoved();
mousePressed();
mouseReleased();

/* Variables */
mousePressed
mouseX
mouseY
```

Now that we know what the mouse API does, lets try implementing the feature in a simple application. The application will invert the color upon press, showcasing that touch has been registerd by the application. Using mousePressed variable allows for constant check, whereas the use of mousePressed function allows you to limit you presses to a single instance. 

```Javascript
import processing.vr.*;

boolean lastMOuseState = false;

void setup() {
  fullScreen(STEREO);
}

void draw() {
    PGraphicsVR pvr = (PGraphicsVR)g;
    if (pvr.eyeType == PVR.LEFT) {
        if(mousePressed && !lastMouseState){
            background(50, 50, 200);
            lastMouseState = true;
        }else{
            background(200, 50, 50);
        }
    } else if (pvr.eyeType == PVR.RIGHT) {
        if(mousePressed && !lastMouseState){
            background(50, 50, 200);
            lastMouseState = true;
        }else{
            background(200, 50, 50);
        }  
    } else if (pvr.eyeType == PVR.MONOCULAR) {
        background(50, 200, 50);
    }
}

void mouseReleased(){
    lastMOuseState = false;
}
```

## Getting Eye Information
When it comes to Virtual reality on an Android device, we are limited by the resources. We have to emphasize tools that we normally neglect, and in this case, it would be our vision. Processing for Android gives us the ability to get coordinate information about where we are looking within the vr application based on tilt of Android device. It handles the complex matrix math and allows us to access variables which have already been calculated for us. The real trick in these applications is the abiltiy to line up the eye coordinate system with shapes coordinate system.
```Javascript
import processing.vr.*;

PGraphicsVR pvr;

float xcamera;
float ycamera;
float zcamera;

void setup() {
  /**
   * Android VR Applications cannot use size(), use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(STEREO);

  cameraUp();    // Comment out for Processing's traditional coordinate system
  
  crosshair = createShape(SPHERE, 10);
}

void draw() {
  /* Create a white background to see 3D creations */
  background(100);
  
  pvr = (PGraphicsVR)g;
    
  float d = 1000;
  xcamera = pvr.cameraX + d * pvr.forwardX;
  ycamera = pvr.cameraY + d * pvr.forwardY;
  
  shape(crosshair, xcamera, ycamera);
}

void mousePressed(){
    print(" CX: " + xcamera  + "  YX: " + ycamera);
  }
}
```

In the example above, we use the newly taught skill to print coordinates to the terminal in the processing GUI. We also continually update a spheres location by continuously creating the object with new points generated by sight. This shows that we are able to read the tilt of the android device reliably and use it for iteraction within applications

## Summary and final assignment

After this tutorial, you should be familiar with how to interface with the touchscreen for user control in the application. You should also be able to understand some matrix algebra which allows us to interface the eye class with objects, comparing the locations between both items.

The final piece of code included in this example holds a small game like program that will randomly change the position of the loaded .obj file and allow for touch interface and eye tracking to interact with the object. The Code provides a small crosshair to show how the eye coordinates translate in various parts of the virutal reality enviornment.

```Javascript
import processing.vr.*;

/** Global Variables */
PShape bulldogModel;
PMatrix3D matrix = new PMatrix3D();
PGraphicsVR pvr;

int counter = 0;

float x = -30;
float y = 0;
float z = 0;

float xcamera;
float ycamera;
float zcamera;

PShape crosshair;

void setup() {
  /**
   * Android VR Applications cannot use size(), use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(STEREO);

  cameraUp();    // Comment out for Processing's traditional coordinate system
  
  /* Load Model */
  bulldogModel = loadShape("bulldog_logo.obj");
  bulldogModel.scale(40);
  bulldogModel.rotateX(0.5 * PI);
  bulldogModel.translate(0, 0, 0);
  
  crosshair = createShape(SPHERE, 10);
}

void draw() {
  /* Create a white background to see 3D creations */
  background(100);
  
  pvr = (PGraphicsVR)g;
  
  pointLight(255, 255, 255, pvr.cameraX, pvr.cameraY, pvr.cameraZ);
  
  float d = 1000;
  xcamera = pvr.cameraX + d * pvr.forwardX;
  ycamera = pvr.cameraY + d * pvr.forwardY;
  
  shape(crosshair, xcamera, ycamera);
  shape(bulldogModel, x, y);
}

void mousePressed(){
    print(" X: " + x  + "  Y: " + y + " Z: " + z);
    print(" CX: " + xcamera  + "  YX: " + ycamera + " ZX: " + zcamera);
    if(abs(xcamera - x) < 100){
    /* Pass the x now check y */
    print("Passed One");
    if(abs(ycamera - y) < 100){
      print("Passed Two");
      x = random(-600, +600);
      y = random(-600, +600);
      z = random(-500, +500);
    }
  }
}

```