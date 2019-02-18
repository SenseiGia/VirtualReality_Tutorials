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
