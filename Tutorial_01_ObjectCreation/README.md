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
plane()         // Does not work
box()
sphere()
cylinder()      // Does not work
cone()          // Does not work
ellipsoid()     // Does not work
torus()         // Does not work
/* Addtional #D objects can be installed through the many tools that exist for processing */
```

**IMAGE**

### Another way to create 3D objects
Outside of basic 3D shapes which can be modified from some parameters, virtual reality developers can also import 3D Models. They can be created by third party software (Blender, Unreal Engine) or even borrowed from other creators (Poly by Google). This provides the ability to use complex models with textures within our applications.

**IMAGE**

With the previous example demonstrating how to create 2D shapes, it provides very little control for changes to that shape as it is only created in that draw instance. In the following example we will create 3D objects with variables to allow modification throughout the program. The function that allows for this is ``` createShape() ```, which allows us to load models and both 2D and 3D shapes into objects.

```Javascript
/** In this first part, we just create the few Processing Shape objects for global use,
 * allowing for access between multiple functions. Shape location is passed into shape() in
 * the draw function.
 */
import processing.vr.*;

/** Global Variables */
PShape topShape;
PShape centerShape;
PShape bottomShape;
PShape leftShape;
PShape rightShape;
void setup() {
  /**
   * Android VR Applications cannot use size(), use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(STEREO);

  cameraUp();    // Comment out for Processing's traditional coordinate system
  
  /**
   * Create Shape allows us to define a new shape which allows us to create a PShape type object */
  centerShape = createShape(SPHERE, 100);         // 100 unit radius
  topShape = createShape(BOX, 100, 100, 100);     // 100x100x100 box
  bottomShape = createShape(BOX, 100, 100, 100);
  leftShape = createShape(BOX, 100, 100, 100);
  rightShape = createShape(BOX, 100, 100, 100);
}

void draw() {
  /* Create a white background to see 3D creations */
  background(0);
    
  /* Create our 5 shapes */
  shape(centerShape, 0, 0);
  shape(topShape, 0, 500);
  shape(bottomShape, 0, -500);
  shape(leftShape, -400, 0);
  shape(rightShape, 400, 0);
}
```

Rather than using shape() to input the objects coordinates, we can also use the translate function in settings to initially set the object in space once drawn. The translation from the shapes translate() function is additive to the coordinates of shape() function. 

```Javascript
/** The following code produces the same results as the code above. Rather than decide the coordinates when drawing the shape, use the translate member function to update the location of the object. This will be useful once we start attempting animation and interaction */

void setup() {
  /**
   * Android VR Applications cannot use size(), use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(STEREO);

  cameraUp();    // Comment out for Processing's traditional coordinate system
  
  centerShape = createShape(SPHERE, 75);
  // Don't need a translate as it it defaults to (0, 0, 0);
  topShape = createShape(BOX, 100, 100, 100);
  topShape.translate(0, 500, 0);    // Move Shape up 150 pixels
  bottomShape = createShape(BOX, 100, 100, 100);
  bottomShape.translate(0, -500, 0);// Move Shape down 150 pixels
  leftShape = createShape(BOX, 100, 100, 100);
  leftShape.translate(-500, 0, 0);  // Move Shape left 150 pixels
  rightShape = createShape(BOX, 100, 100, 100);
  rightShape.translate(500, 0, 0);  // Move Shape right 150 pixels
}

void draw() {
  /* Create a white background to see 3D creations */
  background(0);
    
  /* Create our 5 shapes */
  shape(centerShape, 0, 0);
  shape(topShape, 0, 0);
  shape(bottomShape, 0, 0);
  shape(leftShape, 0, 0);
  shape(rightShape, 0, 0);
}
```

PShape's translate function allows for easy modification of an objects location in the 3D space without having to perform any matrix transformation. (possible, but complex for this set of tutorials at this time). The translate function is culmative, meaning each call adds to the previous calls. This allows for your changes to persist. In the following example, we use the continously called draw() function to move the objects in various directions with translate().

```Javascript
/** 
 * This Modification the code block allows for simple object movement during the virtual reality
 * simulation. As seen with the center object, comple movements can be done with a mixture of 
 * translates and rotations. This example has the boxes floating away and the sphere moving
 * towards the user and away.
 */

int counter = 0;

void draw() {
  /* Create a white background to see 3D creations */
  background(0);
    
  /* Create our 5 shapes */
  shape(centerShape, 0, 0);
  shape(topShape, 0, 0);
  shape(bottomShape, 0, 0);
  shape(leftShape, 0, 0);
  shape(rightShape, 0, 0);

  /* Move or objects in various directions */
  topShape.translate(0, 0.1, 0);
  bottomShape.translate(0. -.1, 0);
  leftShape.translate(-0.2, 0, 0);
  rightShape.translate(0.2, 0, 0);

  centerShape.rotateX(0.1);
  if(counter <= 10){
    counter++;
    centerShape.translate(0, 0, 0.2);
  }else if(counter > 10){
    counter++;
    centerShape.translate(0, 0, -0.2);
  }else if(counter == 20){
    counter = 0;
  }
}
```

Now that we have established some understanding on how translate works, we can integrate ```rotate()``` into our application for more movements. Rotate just rotates the shape by the specified angle that is passed into the function. The angle should be passed in radians (0 to 2*PI) for clarity. In the next piece of code, rather than translate, we focus on rotates, despite having the ability to do both at same time. 

```Javascript
/** 
 * This modification explores the rotate function. Reduce size of shapes with translate
 * function to fit everything within screen and reduce clutter.
 */

int counter = 0;

void draw() {
  /* Create a white background to see 3D creations */
  background(0);
    
  /* Create our 5 shapes */
  shape(centerShape, 0, 0);
  shape(topShape, 0, 0);
  shape(bottomShape, 0, 0);
  shape(leftShape, 0, 0);
  shape(rightShape, 0, 0);

  /* Move or objects in various directions */
  topShape.rotateY(0.1);
  bottomShape.rotateY(-0.3);
  leftShape.rotateX(0.4);
  rightShape.rotateX(0.2);

  centerShape.rotateX(0.1);
  if(counter <= 10){
    counter++;
    centerShape.translate(0, 0, 0.2);
  }else if(counter > 10){
    counter++;
    centerShape.translate(0, 0, -0.2);
  }else if(counter == 20){
    counter = 0;
  }
}
```

Note that there is no collision between the Shapes. This is because they are unaware of each other and lack the ability to interact between each other. This must be handled by the code and can be done by using the shapes vertices along with it's size to write collision code. This will be done in a later tutorial. 

For the final part of this tutorial, we will introduce two concepts at the same time. The first will be the ability to import models, which in this example with be an .obj file of the Kettering Bulldog Logo. The second concept will be scale, the last of the 3 big shape transformations.

To load a model within a Processing generated application, start off by adding the files necessary to the project. This should be done by navigating to "Add File..." command under the Sketch header in the toolbar. This will create a data folder within your project folder and store your added file. For most .obj files, a .mtl, texture file containing information about model's shading and textures, should be added to the project as well. You can use the .obj and .mtl files from this repository or pull a model form poly.google.com.

```Javascript
import processing.vr.*;

/** Global Variables */
PShape bulldogModel;

int counter = 0;

void setup() {
  /**
   * Android VR Applications cannot use size(), use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(STEREO);

  cameraUp();    // Comment out for Processing's traditional coordinate system
  
  bulldogModel = loadShape("bulldog_logo.obj");
  bulldogModel.scale(30);
  bulldogModel.rotateX(0.5 * PI);
  //bulldogModel.translate(-30, 0, 500);
}

void draw() {
  /* Create a white background to see 3D creations */
  background(0);
  
  /* Lights is useful for 3D shapes as it provides further shading of shapes */
  lights();

  /* Move or objects in various directions */
  shape(bulldogModel, 0, 0);
  
  translate(-30, 0, 500);
  bulldogModel.rotateY(0.01);
}
```
**Note: Scale is direct, meaning that in the example above, the object is being scaled up 30x. Blenders scaling is much smaller, requiring a large upscale in Processing/application.**

Now that you have the basics of object creation down, you should be able to create an application where:
1.) Moving Model in center (Bullddog)
2.) Change Background and provide lighting to objects
3.) Have basic shapes move around center Model (Rotate)
4.) Change colors of objects.

```Javascript
import processing.vr.*;

/** Global Variables */
PShape bulldogModel;
PShape randomShape1;
PShape randomShape2;

int counter = 0;

void setup() {
  /**
   * Android VR Applications cannot use size(), use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(STEREO);

  cameraUp();    // Comment out for Processing's traditional coordinate system
  
  bulldogModel = loadShape("bulldog_logo.obj");
  bulldogModel.scale(40);
  bulldogModel.rotateX(0.5 * PI);
  bulldogModel.translate(-30, 0, 0);
  
  randomShape1 = createShape(SPHERE, 30);
  randomShape1.translate(0, 300, 0);
  randomShape2 = createShape(BOX, 30, 30, 30);
  randomShape2.translate(300, 0, 0);
}

void draw() {
  /* Create a white background to see 3D creations */
  background(200);
  
  /* Lights is useful for 3D shapes as it provides further shading of shapes */
  lights();

  /* Move or objects in various directions */
  shape(bulldogModel, 0, 0);
  shape(randomShape1, 0, 0);
  shape(randomShape2, 0, 0);
  
  bulldogModel.rotateY(0.01);
  randomShape2.rotateZ(0.01);
  randomShape1.rotateX(0.01);
}
```






