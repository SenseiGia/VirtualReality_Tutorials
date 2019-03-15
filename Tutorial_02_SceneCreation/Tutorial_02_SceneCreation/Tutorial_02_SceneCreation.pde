import processing.vr.*;

/** Global Variables */
PShape bulldogModel;

int counter = 0;

int x = -30;
int y = 0;

void setup() {
  /**
   * Android VR Applications cannot use size(), use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(STEREO);

  cameraUp();    // Comment out for Processing's traditional coordinate system
  
  bulldogModel = loadShape("bulldog_logo.obj");
  bulldogModel.scale(40);
  bulldogModel.rotateX(0.5 * PI);
  bulldogModel.translate(0, 0, 0);
}

void draw() {
  /* Create a white background to see 3D creations */
  background(200);
  
  /* Lights is useful for 3D shapes as it provides further shading of shapes */
  lights();

  /* Move or objects in various directions */
  shape(bulldogModel, 0, 0);
  
  if(mousePressed){
    x = mouseX;
    y = mouseY;
  }else{
    // Do nothing
  }
  shape(bulldogModel, 0, 0);

  
  bulldogModel.rotateY(0.01);
}


//boolean mouseInRegion(){
//  if(eyes
//}

//void randomizePoints(){
//  if(mousePressed) {
    
//  }
//}
