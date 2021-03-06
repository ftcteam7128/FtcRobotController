package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
// import com.qualcomm.robotcore.hardware.Servo.MIN_POSITION;
// import com.qualcomm.robotcore.hardware.Servo.MAX_POSITION;

@TeleOp(name="Basic: PickUpShooter_Test", group="Iterative Opmode")
public class PickUpShooter_Test_New extends LinearOpMode {

    // need to add motor that controls slide below (silding taken care of by encoders)
    // full unit up - switch is off -> encoder = 0 -> bring down before pick up
    // move full unit at once

    DcMotor rightIntakeMotor;
    Servo shootServo;
    DcMotor shooterMotor;
    DcMotor baseMotor;


    // pick up angles
    double zero_rings_angle = 0;
    double one_rings_angle = 0;
    double four_rings_angle = 0;

    double shootAngle = 45;

    // double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);
    double ENCODER_TICKS_PER_REVOLUTION = 288;

    /*
    public void runOpMode() {
        rightIntakeMotor = hardwareMap.get(DcMotor.class, "intake");
        shootServo = hardwareMap.get(Servo.class, "shoot");
        shooterMotor = hardwareMap.get(DcMotor.class, "shoot");

        shootServo.setPosition(0.5);

        waitForStart();

        rightIntakeMotor.setPower(0.5f); // increased
        sleep(5000);
        rightIntakeMotor.setPower(0);

        while (opModeIsActive()) {
            boolean push = gamepad2.x;
            boolean shoot = gamepad2.b;

            if (push) {
                // intakeServo.setPosition(Range.clip(0.5, 0, 1));
                // intakeServo.setPosition(intakeServo.getPosition()+0.5);
                telemetry.addLine("Pushing");
                telemetry.update();
                shootServo.setPosition(0.7); // min: -1, max: 1 // change back to 0.15
            }
            if (shoot) {
                shooterMotor.setPower(-1.0f);
                sleep(5000);
                shooterMotor.setPower(0);
            }

            sleep(1000);
        }
    }
     */

    public void runOpMode() {
        rightIntakeMotor = hardwareMap.get(DcMotor.class, "intake");
        shootServo = hardwareMap.get(Servo.class, "shoot");
        shooterMotor = hardwareMap.get(DcMotor.class, "shoot");
        baseMotor = hardwareMap.get(DcMotor.class, "base");

        // use ring scanning to figure out how many rings are there
        // set the final angle based on that (lets assume there's one ring for now)

        double angle = one_rings_angle;

        shootServo.setPosition(0.15);
        baseMotor.setTargetPosition((int)angle);

        waitForStart();

        rightIntakeMotor.setPower(0.5f); // increased
        sleep(5000);
        rightIntakeMotor.setPower(0);

        while (opModeIsActive()) {
            boolean intake = gamepad2.x;
            boolean shoot = gamepad2.b;

            if (intake) {
                // intakeServo.setPosition(Range.clip(0.5, 0, 1));
                // intakeServo.setPosition(intakeServo.getPosition()+0.5);
                telemetry.addLine("Intaking");
                telemetry.update();
                // shootServo.setPosition(0.7); // min: -1, max: 1 // change back to 0.15
                rightIntakeMotor.setPower(0.5);

                // move to shooting angle (45 deg)
                // NEED TO FIX
                baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                baseMotor.setTargetPosition((int)shootAngle);
            }
            else rightIntakeMotor.setPower(0);

            if (shoot) {
                shootServo.setPosition(0.7); // min: -1, max: 1 // change back to 0.15
                shooterMotor.setPower(-1.0f);
                sleep(5000);
                shooterMotor.setPower(0);
            }
            else shootServo.setPosition(0.15);

            sleep(1000);
        }
    }
}