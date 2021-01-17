package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.imgproc.Imgproc;

@Autonomous
public class WobbleGoal_Tester extends LinearOpMode {

    OpenCvCamera cam;

    public void runOpMode(){

        waitForStart();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        cam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        WobbleScanner scanner = new WobbleScanner(telemetry);
        cam.setPipeline(scanner);

        cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened(){
                cam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
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

    public class WobbleScanner extends OpenCvPipeline {
        Telemetry telemetry;
        Mat mat = new Mat();

        public WobbleScanner(Telemetry t){
            telemetry = t;
        }

        @Override
        public Mat processFrame(Mat input){
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV_FULL);

            // darkest: 229*, 83%, 38%
            // lightest: 229, 51%, 87%
            Scalar lowHSV = new Scalar(229, 83, 38);
            Scalar highHSV = new Scalar(229, 51, 87);
            Core.inRange(mat, lowHSV, highHSV, mat);
            Mat goal = mat.submat(GOAL_ROI);

            double goal_percent = Core.sumElems(goal).val[0] / GOAL_ROI.area() / 255;

            goal.release();

            telemetry.addData("percent color match", goal_percent);

            if (goal_percent >= 0.85){
                telemetry.addData("Wobble goal: ", true);
            } else {
                telemetry.addData("Wobble goal: ", false);
            }
            telemetry.update();

            // Converting the gray-scale image back to RGB
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

            // Setting the ring outline as green
            Scalar border = new Scalar(0, 255, 0);

            // Drawing the ROI rectangle onto the matrix
            Imgproc.rectangle(mat, goal_topLeft, goal_botRight, border);

            return mat;
        }


    }
}
