# Tutorial 01: Object Creation
Note: Before progressing with this tutorial, ensure you followed the steps to prepare both your hardware and software explained at the top level of this repository. It would also be helpful to follow the previous tutorials that lead up to the current.

## Overview of tutorial
This tutorials goes through the many objects that can be created for your Virtual Reality application on your android device. This tutorial will only explore the standard objects along with how to create custom objects and import them. For the sake of using Processing with our examples we will start refering them as Shapes. 

## Create your project
You should be familiar with how to create a project, so create a new sketch and save it with you application name in a memorable location on you computer.

## Creating 2D Shapes
Processing with Android provides the ability to create a 3D environment within our android device, but we are still able to create 2D shapes. Processing, being a library that exists to simplify the creation of visuals, contains many primative 2D objects which can be used immediately.

### 2D objects which can be created:
```javascript
arc()
ellipse()   
circle()    // Does not work, use ellipse() instead
line()
point()
quad()
rect()
square()     // Does not work, use rect() instead
triangle()
/* Addtional 2D objects can be installed through the many tools that exist for processing */
```
Below you can find an example that explains some of the 2D shape API along with the basic example which can be used to place the object. 2D objects with rounded lines will take radian inpurts rather than degrees, so make sure you check your units when creating shapes such as arcs and ellipses. Also take note how the objects exist in the environment based on the parameters used to create the objects.

```Javascript
import processing.vr.*;

int counter = 0;

void setup() {
  /**
   * Android VR Applications cannot use size(), 
   * use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(PVR.STEREO);
  /**
   * cameraup() changes Processing's 3D coordinate system to
   * the more common 3D coordinate system used in Virtual Reality
   */
  cameraUp();    // Comment out for Processing's traditional coordinate system
}

void draw() {
  /* Create a white background to see 2D creations */
  background(255);
  
  /* Create our 5 shapes */
  /* Center shape */
  point(0, 0);
  /* Top shape */
  ellipse(0, 100, 10, 30);
  /* Bottom shape */
  rect(0, -100, 10, 10);
  /* Left shape */
  triangle(-100, 0, -110, 10, -110, -10);
  /* right shape */
  line(100, 0, 125, 125);
}

```

**IMAGE**

## Creating 3D Shapes
Processing with Android also provides a few 3D shapes to work with immediately. Due to the nature of the 3D shapes, they require one more arguement during creation than their 2D counterpart (rect -> box). Outside out the extra arguements/parameters required to create the object, the process is the same.

### 3D objects which can be created:
```javascript
plane()
box()
sphere()
cylinder()
cone()
ellipsoid()
torus()         // A personal favorite
/* Addtional #D objects can be installed through the many tools that exist for processing */
```

**IMAGE**

### Another way to create 3D objects
Outside of basic 3D shapes which can be modified from some parameters, virtual reality developers can also import 3D Models. They can be created by third party software (Blender, Unreal Engine) or even borrowed from other creators (Poly by Google). This provides the ability to create complex models with textures.





## The Various Sections of a Processing VR application
Processing is a flexible software sketchbook that allows for easy programming of complex visuals. If we were to examine the code used for Virtual Reality applications generated within Java or Kotlin from Android Studio, we would find that the code is complex with trying to create objects and scenes with OpenGL. OpenGL is a graphics library used to allow GPU rendering of shapes and objects in virtual world. 

Processing has cut out much of the complex parts and simplified it down to two major parts, allowing developers to focus more on content rather than limitations of complex code handling. The two parts consist of:
* Setup()
* Draw()

### Setup
The setup() part of the VR application allows for complete configuration of the application. This usually handles the constants of the application such as wether the application will use a monoscopic view, or stereoscopic view. It can also be used to load files, such as .obj (Waveform) files for use throughout the application. This fuction is called once at startup, useful for loading and configuring.

```Java
/** Used to configure the example */
void setup(){
    /* Example code to make a Stereotopic VR application */
    fullScreen(STEREO);
}
```

### Draw
The draw() part of the VR application allows for creation of visuals in the Virtual Reality enviornment. From here, you can select which objects to render and modify the view provided by either the Stereostopic or Monoscopic view. In the code block provided below, we create a simple application the interfaces with the multiple views generated by the vr application. This function is called continuously to update the renedered objects.

```Java
import processing.vr.*;  // Virtual Reality Library

/** Used to configure the example */
void setup() {
    /* Create a sterotopic View */
    fullScreen(STEREO);
    //fullScreen(MONO);
}

/** Used to create objects in 3D space with background */
void draw() {
  PGraphicsVR pvr = (PGraphicsVR)g;
  if (pvr.eyeType == PVR.LEFT) {
    background(200, 50, 50);
  } else if (pvr.eyeType == PVR.RIGHT) {
    background(50, 50, 200);
  } else if (pvr.eyeType == PVR.MONOCULAR) {
    background(50, 200, 50);
  }
}
```

### MousePressed
The mousePressed() part of the VR application is an optional part to a virtual reality application creation prcoess. It allows for interaction between the user of the application and the environment itself. You can use this load new objects, move objects, and change anything on touchscreeen input. The following code block builds upon the previous and interts the color of the backgrounds if the the screen is pressed. This function is called on screen presses. 

```Java
import processing.vr.*;  // Virtual Reality Library

/** Used to configure the example */
void setup() {
    /* Create a sterotopic View */
    fullScreen(STEREO);
    //fullScreen(MONO);
}

/** Used to create objects in 3D space with background */
void draw() {
  PGraphicsVR pvr = (PGraphicsVR)g;
  if (pvr.eyeType == PVR.LEFT) {
    background(200, 50, 50);
  } else if (pvr.eyeType == PVR.RIGHT) {
    background(50, 50, 200);
  } else if (pvr.eyeType == PVR.MONOCULAR) {
    background(50, 200, 50);
  }
}

/** Used to provide user ineteractions */
void mousePressed() {
  PGraphicsVR pvr = (PGraphicsVR)g;
  if (pvr.eyeType == PVR.LEFT) {
    background(50, 50, 200);
  } else if (pvr.eyeType == PVR.RIGHT) {
    background(200, 50, 50);
  } else if (pvr.eyeType == PVR.MONOCULAR) {
    background(200, 50, 200);
  }
}
```

## Create simple objects in 3D space
Now that we have esablished the basic parts to a Processing Virtual Reality application, we can now start focusing on the application and create various models and shapes within. Progress through the following tutorials in the main repository to learn more in depth details about the creation process.

## Example Code for basic application

```Java
import processing.vr.*;

PShape grid;
PShape cubes;

void setup() {
  fullScreen(STEREO);
  
  grid = createShape();
  grid.beginShape(LINES);
  grid.stroke(255);
  for (int x = -10000; x < +10000; x += 250) {
    grid.vertex(x, +1000, +10000);
    grid.vertex(x, +1000, -10000);
  }
  for (int z = -10000; z < +10000; z += 250) {
    grid.vertex(+10000, +1000, z);
    grid.vertex(-10000, +1000, z);      
  }  
  grid.endShape();  
  
  cubes = createShape(GROUP);
  for (int i = 0; i < 100; i++) {
    float x = random(-1000, +1000); 
    float y = random(-1000, +1000);
    float z = random(-1000, +1000);    
    float r = random(50, 150);
    PShape cube = createShape(BOX, r, r, r);
    cube.setStroke(false);
    cube.setFill(color(180));
    cube.translate(x, y, z);
    cubes.addChild(cube);
  }
}

void draw() {
  background(0);
  lights();
  translate(width/2, height/2);
  shape(cubes);
  shape(grid);
}
```
