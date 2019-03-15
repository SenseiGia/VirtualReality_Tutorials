import processing.vr.*;

/** Global Variables */
PShape allShapes;

void setup() {
  /**
   * Android VR Applications cannot use size(), 
   * use fullscreen(MONO) or fullscreen(STEREO)
   */
  fullScreen(STEREO);
  /**
   * cameraup() changes Processing's 3D coordinate system to
   * the more common 3D coordinate system used in Virtual Reality
   */
  //cameraUp();    // Comment out for Processing's traditional coordinate system
  
  allShapes = createShape(GROUP);
  /* Create Shape allows us to define a new shape which allows us to create a PShape type object */
  /* This can be used both 2D shapes and 3D shapes */
  PShape centerShape = createShape(SPHERE, 20, 0, 0);
  centerShape.setFill(color(255));
  centerShape.setStroke(false);
  allShapes.addChild(centerShape);

  //topShape = createShape(BOX, 20, 20, 20, 0, 100);
  //bottomShape = createShape(BOX, 30, 30, 30, 0, -100);
  //leftShape = createShape(BOX, 50, 10, 40, -100, 0);
  //rightShape = createShape(TRIANGLE, 100, 0, 120, 120, 120, -120);
}

void draw() {
  /* Create a white background to see 3D creations */
  background(255);
  
    translate(width/2, height/2);

  
  shape(allShapes);
  
  /* Create our 5 shapes */
  //shape(centerShape, 0, 0);
  //shape(topShape, 0, 0);
  //shape(bottomShape, 0, 0);
  //shape(leftShape, 0, 0);
  //shape(rightShape, 0, 0);
}
