package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


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

@Autonomous
public class Intake_Test extends LinearOpMode {

    DcMotor leftIntake;
    DcMotor rightIntake;
    Servo servo;

    @Override
    public void runOpMode() {

        leftIntake = hardwareMap.get(DcMotor.class, "leftMotor");
        rightIntake = hardwareMap.get(DcMotor.class, "rightMotor");
        servo = hardwareMap.get(Servo.class, "servo");

        waitForStart();
        intake();

    }

    public void intake() {

        leftIntake.setPower(0.25);
        rightIntake.setPower(0.25);
        sleep(5000);

        leftIntake.setPower(0);
        rightIntake.setPower(0);
        sleep(1000);

        servo.setPosition(1.0);
    }
}
