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
