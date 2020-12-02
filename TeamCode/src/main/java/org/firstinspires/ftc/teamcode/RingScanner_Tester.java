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

/*
STRATEGY:
  1. Input the camera frame image as a matrix
  2. Set yellow parts of the image as white and the bg as black
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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        cam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        RingScanner scanner = new RingScanner(telemetry);
        cam.setPipeline(scanner);

        // Streaming
        cam.openCameraDeviceAsync(
                () -> cam.startStreaming(240, 320, OpenCvCameraRotation.UPRIGHT)
        );

        waitForStart();

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
        // -----------------        need to determine by testing         -----------------

        Point four_topLeft = new Point(0, 0);
        Point four_bottomRight = new Point(0, 0);


        // ----------------- Defining rectangle for our region of interest -----------------

        Rect FOUR_RINGS_ROI = new Rect(four_topLeft, four_bottomRight);


        public RingScanner(Telemetry t) {
            telemetry = t;
        }

        @Override
        public Mat processFrame(Mat input) {

            // Converting matrix from RGB --> HSV
            // HSV: Hue - color, Saturation - intensity, value - brightness
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV); // range: 0-180

            // Creating an HSV range to detect yellow (ring color)
            // Will only be considered yellow if all three values are within this range
            Scalar lowHSV = new Scalar(23, 50, 70);
            Scalar highHSV = new Scalar(32, 255, 255);
            
            // Thresholding - showing the part of the image that is yellow
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

            telemetry.addData("% color match", "rings_percent(%.2f)");
            telemetry.update();

            // Figuring out how many rings there are
            if (rings_percent <= 0.05) {
                telemetry.addData("# of rings", "ZERO");
            } else if (rings_percent <= 0.25) {
                telemetry.addData("# of rings", "ONE");
            } else {
                telemetry.addData("# of rings", "FOUR");
            }
            telemetry.update();

            // Converting the gray-scale image back to RGB
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

            // Setting the ring outline as green
            Scalar border = new Scalar(0, 255, 0);

            // Drawing the ROI rectangle onto the matrix
            Imgproc.rectangle(mat, four_topLeft, four_bottomRight, border);

            return mat;
        }


    }
}