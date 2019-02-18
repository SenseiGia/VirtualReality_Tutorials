# Tutorial 01: Scene Creation
Note: Before progressing with this tutorial, ensure you followed the steps to prepare both your hardware and software explained here...

## Create your first project
When opening Android Studio you will be presented with the window below. Select "Start a new Android Studio project".

From here, Create and application with no Activity, as this will allow the tutorial to go more in depth with the various parts of the application design process.

On this window, you will input the desired name of your project in the "Name" text box. The package Name and Save Location will be generated automatically but can be changed if you prefer to. Ensure that the Save location will create a new folder and not just use an existing directory. 

Ensure that you are using Java for Language and Set the Minimum API Version to **API 25: Android 7.1.1 'Nougat'** This does not have to match the Android version on you device, but ensures the greatest amount of backwards compatibiltiy.

Finish the project creation process by clicking finish.

## Set Up the Common VR Dependicies (Google VR)

1. Ensure jcenter() repositor exsits in project level build.gradlew

2. Declare and Android Gradlew plugin dependency, where Google VR SDK projects should use at least gradlew: 2.3.3

```gradlew
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        // Don't need to define v2.3.3 as v3.3.1 is already higher
        classpath 'com.android.tools.build:gradle:3.3.1'
        
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()   // Required for Google VR
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

3. Navigate to your applications level build.gradle file. In this file you will want to ensure to  add the folliwing two lines:
```Java
    // Required for all Google VR apps
    implementation 'com.google.vr:sdk-base:1.160.0'

    // Obj - a simple Wavefront OBJ file loader
    // https://github.com/javagl/Obj
    implementation 'de.javagl:obj:0.2.1'
```

## Start Writing the Application
For the sake of simplfying the tutorial, use the object files provided in the assests folder of this repository. Copy the Copied or Cloned assets folder and paste locally into your project as seen below.

We will also for the sake of time just copy the files that aren't the main activity into you local project. 

Note: All created source files should go under the following directory

**app -> src -> main -> java -> com.example."projectName"**

Once you have assets folder with the necessary .obj and .png files, as well as Texture.java, TestureMesh.java, and Util.Java, you can start writing the activity. Start off by creating your own class file with Android Studio and call it "ApplicationName"Actvitiy, or SceneCreationActivity. Because we created an application with no Acvitiy we will have to define that our Activity implements the GvrView.StereoRender, which proveds the tempolate and tools needed to create a Stereostopic view for our application. 

The SereoRenderer implmentation contains six functions:
```Java
1. void onNewFrame(HeadTransform headTransform);
2. void onDrawEye (Eye eye);
3. void onFinishFrame(Viewport viewport);
4. void onSurfaceChanged(in width, int height);
5. void onSurfaceCreated(EGLConfig config);
6. void onRendererShutdown();
```
TODO: Add explanation of these functions. Breakdown the following code...


```Java
package com.example.tutorial_01_scenecreation;

/** Android Imports */
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
/** Google VR Imports */
import com.google.vr.sdk.base.AndroidCompat;    // VR compatibility on Android
import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;          // Bulk of Google VR???
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;
/** Standard java Imports */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
/** Regex Imports? TODO: learn this one */
import javax.microedition.khronos.egl.EGLConfig;

/* Learn what an extension is... */
public class SceneCreationActivity extends GvrActivity implements GvrView.StereoRenderer {
    /* Constants*/
    private static final String TAG = "SceneCreationActivity";
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 10.0f;
    private static final float FLOOR_HEIGHT = -2.0f;


    /* Variables */
    private static final String[] OBJECT_VERTEX_SHADER_CODE =
            new String[] {
                    "uniform mat4 u_MVP;",
                    "attribute vec4 a_Position;",
                    "attribute vec2 a_UV;",
                    "varying vec2 v_UV;",
                    "",
                    "void main() {",
                    "  v_UV = a_UV;",
                    "  gl_Position = u_MVP * a_Position;",
                    "}",
            };
    private static final String[] OBJECT_FRAGMENT_SHADER_CODE =
            new String[] {
                    "precision mediump float;",
                    "varying vec2 v_UV;",
                    "uniform sampler2D u_Texture;",
                    "",
                    "void main() {",
                    "  // The y coordinate of this sample's textures is reversed compared to",
                    "  // what OpenGL expects, so we invert the y coordinate.",
                    "  gl_FragColor = texture2D(u_Texture, vec2(v_UV.x, 1.0 - v_UV.y));",
                    "}",
            };

    private int objectProgram;

    private int objectPositionParam;
    private int objectUvParam;
    private int objectModelViewProjectionParam;

    private TexturedMesh room;
    private Texture roomTexture;

    private float[] camera;
    private float[] view;
    private float[] headView;
    private float[] modelViewProjection;
    private float[] modelView;


    private float[] modelTarget;
    private float[] modelRoom;

    /**
     * Activity Initialization...,
     * Set the view to Google VR View and init the transformation
     * matrices to render our scene... TODO: Talk about this.
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        /**
         * Super allows for our code to run in addition to code that
         * already exists for onCreate
         */
        super.onCreate(savedInstanceState);

        /**
         * Config Some Settings
         */
        initializeGvrView();

        /**
         * Some data allocation
         */
        camera = new float[16];
        view = new float[16];
        modelViewProjection = new float[16];
        modelView = new float[16];
        // Target object first appears directly in front of user.
//        targetPosition = new float[] {0.0f, 0.0f, -MIN_TARGET_DISTANCE};
//        tempPosition = new float[4];
//        headRotation = new float[4];
        modelTarget = new float[16];
        modelRoom = new float[16];
        headView = new float[16];

//        random = new Random();
    }

    public void initializeGvrView() {
        setContentView(R.layout.common_ui);

        GvrView gvrView = (GvrView) findViewById(R.id.gvr_view);
        gvrView.setEGLConfigChooser(8, 8, 8, 8, 16, 8);

        gvrView.setRenderer(this);
        gvrView.setTransitionViewEnabled(true);

        if (gvrView.setAsyncReprojectionEnabled(true)) {
            // Async reprojection decouples the app framerate from the display framerate,
            // allowing immersive interaction even at the throttled clockrates set by
            // sustained performance mode.
            AndroidCompat.setSustainedPerformanceMode(this, true);
        }

        setGvrView(gvrView);
    }

    /**
     *    TODO: Figure out what this does...
     *    Maybe be used to finish a 3D scene, save some settings?
     */

    @Override
    public void onRendererShutdown() {
        Log.i(TAG, "onRendererShutdown");
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        Log.i(TAG, "onSurfaceChanged");
    }

    @Override
    public void onFinishFrame(Viewport viewport) { }

    public void onSurfaceCreated(EGLConfig config) {
        Log.i(TAG, "onSurfaceCreated");
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        objectProgram = Util.compileProgram(OBJECT_VERTEX_SHADER_CODE, OBJECT_FRAGMENT_SHADER_CODE);


        objectPositionParam = GLES20.glGetAttribLocation(objectProgram, "a_Position");
        objectUvParam = GLES20.glGetAttribLocation(objectProgram, "a_UV");
        objectModelViewProjectionParam = GLES20.glGetUniformLocation(objectProgram, "u_MVP");

        Matrix.setIdentityM(modelRoom, 0);
        Matrix.translateM(modelRoom, 0, 0, FLOOR_HEIGHT, 0);

        // Avoid any delays during start-up due to decoding of sound files.
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                    }
                })
                .start();

        Util.checkGlError("onSurfaceCreated");

        try {
            room = new TexturedMesh(this, "CubeRoom.obj", objectPositionParam, objectUvParam);
            roomTexture = new Texture(this, "CubeRoom_BakedDiffuse.png");
        } catch (IOException e) {
            Log.e(TAG, "Unable to initialize objects", e);
        }
    }

    /**
     * Prepares OpenGL ES before we draw a frame.
     *
     * @param headTransform The head transformation in the new frame.
     */
    @Override
    public void onNewFrame(HeadTransform headTransform) {
        // Build the camera matrix and apply it to the ModelView.
        Matrix.setLookAtM(camera, 0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f);

        headTransform.getHeadView(headView, 0);

        Util.checkGlError("onNewFrame");
    }

    /**
     * Draws a frame for an eye.
     *
     * @param eye The eye to render. Includes all required transformations.
     */
    @Override
    public void onDrawEye(Eye eye) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        // The clear color doesn't matter here because it's completely obscured by
        // the room. However, the color buffer is still cleared because it may
        // improve performance.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Apply the eye transformation to the camera.
        Matrix.multiplyMM(view, 0, eye.getEyeView(), 0, camera, 0);

        // Build the ModelView and ModelViewProjection matrices
        // for calculating the position of the target object.
        float[] perspective = eye.getPerspective(Z_NEAR, Z_FAR);

        Matrix.multiplyMM(modelView, 0, view, 0, modelTarget, 0);
        Matrix.multiplyMM(modelViewProjection, 0, perspective, 0, modelView, 0);
        //drawTarget();

        // Set modelView for the room, so it's drawn in the correct location
        Matrix.multiplyMM(modelView, 0, view, 0, modelRoom, 0);
        Matrix.multiplyMM(modelViewProjection, 0, perspective, 0, modelView, 0);
        drawRoom();
    }

    /** Draw the room. */
    public void drawRoom() {
        GLES20.glUseProgram(objectProgram);
        GLES20.glUniformMatrix4fv(objectModelViewProjectionParam, 1, false, modelViewProjection, 0);
        roomTexture.bind();
        room.draw();
        Util.checkGlError("drawRoom");
    }
}
```




