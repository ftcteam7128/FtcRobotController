package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Rect;
import org.opencv.core.Point;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

/*
STRATEGY:
  1. Input the camera frame image as a matrix
  2. Set orange parts of the image as white and the bg as black
  3. Extract the region of interest into a submatrix through hard-coded values
        a) in our case, its the dimensions for the max number of rings, 4
  4. Figure out the % of white in the submatrix
  5. Use this % to figure out how many rings there are
        a) Zero: ~0%
        b) One: ~25%
        c) Four: ~100%
 */

@Autonomous (name = "Ring Detector", group = "Auto")
public class RingScanner_Tester extends LinearOpMode {

    // Declare a phone camera
    OpenCvCamera cam;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        int cameraMonitorViewId;
        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        // cam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        cam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        RingScanner scanner = new RingScanner(telemetry);
        cam.setPipeline(scanner);

        cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened(){
                cam.startStreaming(320,240,OpenCvCameraRotation.UPRIGHT);
            }
        });

        // waitForStart();

        while(opModeIsActive()) {
            telemetry.addLine("Scanning");
            telemetry.update();

            sleep(500);
        }

        cam.stopStreaming();
    }


    public class RingScanner extends OpenCvPipeline {

        Telemetry telemetry;
        Mat mat = new Mat();

        // ----------------- x and y are opposite vertices of a triangle -----------------
        // -------     need to determine by testing camera 10 in away from the rings  -------

        int width = 320;
        int height = 240;

        // (0, 0) is top left
        // Point four_topLeft = new Point(0.25*width, 0.05*height);
       //  Point four_botRight = new Point(width - (0.1*width), height - (0.1*height));

        // Point four_topLeft = new Point(0.3*width, 0.4*height);
        // Point four_botRight = new Point(width - (0.2*width), height - (0.1*height));

        Point four_topLeft = new Point(0.3*width, 0);
        Point four_botRight = new Point(width - (0.2*width), height);

        // ----------------- Defining rectangle for our region of interest -----------------

        Rect FOUR_RINGS_ROI = new Rect(four_topLeft, four_botRight);


        public RingScanner(Telemetry t) {
            telemetry = t;
        }

        @Override
        public Mat processFrame(Mat input) {

            // Converting matrix from RGB --> HSV
            // HSV: Hue - color, Saturation - intensity, value - brightness
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV_FULL); // range: 0-360

            // Creating an HSV range to detect orange (ring color)
            // Will only be considered orange if all three values are within this range
            // chart: http://www.workwithcolor.com/orange-brown-color-hue-range-01.htm
            // Scalar lowHSV = new Scalar(24, 100, 50);
            // Scalar highHSV = new Scalar(39, 100, 85);
            Scalar lowHSV = new Scalar(26, 200, 150); // lowered brightness from 150
            Scalar highHSV = new Scalar(50, 255, 255);

            // Thresholding - showing the part of the image that is orange
            // Parameters: source matrix, lower bound, higher bound, destination matrix
            // After this, regions with HSV will be white, bg black
            Core.inRange(mat, lowHSV, highHSV, mat);

            // Extracting ROIs through a submat
            // submat: sub-matrix, a portion of the orginal
            Mat rings = mat.submat(FOUR_RINGS_ROI);

            // Checking what % of the matrix became white
            // (white pixels/area of sub-matrix) / 255
            double rings_percent = Core.sumElems(rings).val[0] / FOUR_RINGS_ROI.area() / 255;

            // Releasing the sub-matrix
            rings.release();

            telemetry.addData("percent color match", rings_percent);
            // telemetry.update();

            sleep(500); // added bc telem was getting overrided

            // 0 rings - 0                     0.001 --> 0 w/o box
            // 1 ring = 0.06 --> 1             0.06  --> 1 w/o box
            // 2 rings - 0.046 --> 4           0.029  --> 1 w/o box
            // 3 rings - 0.11 --> 4            0.03  --> 4 w/o box
            // 4 rings - 0.25 or 0.27 --> 4    0.058 w/o box

            // Figuring out how many rings there are
            /*
            if (rings_percent <= 0.0) { // 005
                telemetry.addData("# of rings", "ZERO");
                sleep(1000);
            }
            */

            /*
            if(rings_percent <= 0.001){
                telemetry.addData("# of rings", "ZERO");
                sleep(1000);
            }
            else if (rings_percent <= 0.1) { //0.1
                telemetry.addData("# of rings", "ONE");
                sleep(1000);
            }
            else if(rings_percent >= 0.14){
                telemetry.addData("# of rings", "FOUR");
                sleep(1000);
            }
            */
            if(rings_percent >= 0.14){
                telemetry.addData("# of rings", "FOUR");
                sleep(1000);
            }
            else if (rings_percent >= 0.008) { //0.1
                telemetry.addData("# of rings", "ONE");
                sleep(1000);
            }
            else{
                telemetry.addData("# of rings", "ZERO");
                sleep(1000);
            }

            telemetry.update();

            // Converting the gray-scale image back to RGB
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

            // Setting the ring outline as green
            Scalar border = new Scalar(0, 255, 0);

            // Drawing the ROI rectangle onto the matrix
            Imgproc.rectangle(mat, four_topLeft, four_botRight, border);

            return mat;
        }


    }
}