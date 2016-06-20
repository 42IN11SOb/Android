package com.avans.in11sob.pep_android.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.avans.in11sob.pep_android.Api.Models.Profile;
import com.avans.in11sob.pep_android.Api.Models.Color;
import com.avans.in11sob.pep_android.R;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.HashMap;
import java.util.Map;

public class ScannerActivity extends Activity implements CvCameraViewListener2 {
    private final static String TAG = "PEP::Scanner";
    private static int WindowWidth;
    private static int WindowHeight;
    Profile pro = Profile.getInstance();
    public LinearLayout mColorView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded Successfully");
                    mOpenCvCameraView.enableView();
                    break;
                }
                default: {
                    super.onManagerConnected(status);
                }
            }
            super.onManagerConnected(status);
        }
    };
    private JavaCameraView mOpenCvCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("SCANNNER::", "Scanner create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mOpenCvCameraView = (JavaCameraView) findViewById(R.id.scannerCameraView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mColorView = (LinearLayout) findViewById(R.id.scanColor);
    }

    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onDestroy() {
        super.onDestroy();
        if(mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
//            rowCol.clear();
        }
    }

    Mat newMat;
//    Map<Double, Double>rowCol = new HashMap<Double, Double>();
    @Override
    public void onCameraViewStarted(int width, int height) {
//        Log.e("BREEDTE:: ", String.valueOf(width));Log.e("HOOGTE:: ",String.valueOf(height));
        WindowWidth = width;
        WindowHeight = height;
        // TL   TC  TR
        //              TL::    ROW*.25, COL*.25    300*250
        //              TC::    ROW*.50, COL*.25    600*250
        //              TR::    ROW*.75, COL*.25    900*250
        // CL   CC  CR
        //              CR::    ROW*.25, COL*.50    300*500
        //              CC::    ROW*.50, COL*.50    600*500
        //              CL::    ROW*.75, COL*.50    900*500
        // BL   BC  BR
        //              BL::    ROW*.25, COL*.75    300*750
        //              BC::    ROW*.50, COL*.75    600*750
        //              BR::    ROW*.75, COL*.75    600*750
//        rowCol.put((WindowWidth*0.25),(WindowHeight*0.25));
//        rowCol.put((WindowWidth*0.50),(WindowHeight*0.25));
//        rowCol.put((WindowWidth*0.75),(WindowHeight*0.25));
//
//        rowCol.put((WindowWidth*0.25),(WindowHeight*0.50));
//        rowCol.put((WindowWidth*0.50),(WindowHeight*0.50));
//        rowCol.put((WindowWidth*0.75),(WindowHeight*0.50));
//
//        rowCol.put((WindowWidth*0.25),(WindowHeight*0.75));
//        rowCol.put((WindowWidth*0.50),(WindowHeight*0.75));
//        rowCol.put((WindowWidth*0.75),(WindowHeight*0.75));

        newMat = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        newMat.release();
    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        int rows = inputFrame.rgba().rows();
        int clms = inputFrame.rgba().cols();

//        Log.e("PRINTING RGB ROW:: ", String.valueOf(inputFrame.rgba().rows())); // HOOGTE
//        Log.e("PRINTING RGB COL:: ", String.valueOf(inputFrame.rgba().cols())); // WIDTH
        double[] data = inputFrame.rgba().get((rows/2), (clms/2));
//        for (Map.Entry<Double, Double> entry : rowCol.entrySet()) {
//            double[]data = inputFrame.rgba().get(entry.getKey().intValue(), entry.getValue().intValue());
//            data[0]; // R
//            data[1]; // G
//            data[2]; // B
//        }
        compareColor((int)data[0], (int)data[1], (int)data[2]);
//        Log.e("data 0::", String.valueOf(data[0]));
//        Log.e("data 1::", String.valueOf(data[1]));
//        Log.e("data 2::", String.valueOf(data[2]));

        return inputFrame.rgba();
    }

    public void compareColor(int R, int G, int B) {
        final int Rc = R;
        final int Gc = G;
        final int Bc = B;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mColorView.setBackgroundColor(android.graphics.Color.rgb(Rc, Gc, Bc)); //
            }
        });
        // get profilecolors
        int margin = 30;
        for (Color rgb : pro.data.passport.season.colors) {
            if(rgb.color.r > (R - margin) && rgb.color.r < (R + margin) &&
                    rgb.color.g > (G - margin) && rgb.color.g < (G + margin) &&
                    rgb.color.b > (B - margin) && rgb.color.b < (B + margin)) {
                Log.e("SUCCESS", "YOU HAVE FOUND A COLOUR " + rgb.color.name);
                return;
            }
        }
    }
}
