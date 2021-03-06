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
