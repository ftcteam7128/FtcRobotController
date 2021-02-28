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

    // double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);
    double ENCODER_TICKS_PER_REVOLUTION = 288;

    public void runOpMode() {
        rightIntakeMotor = hardwareMap.get(DcMotor.class, "intake");
        shootServo = hardwareMap.get(Servo.class, "shoot");
        shooterMotor = hardwareMap.get(DcMotor.class, "shoot");

        shootServo.setPosition(0.5);

        /*
        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int targetPos = (int) (rightIntakeMotor.getCurrentPosition() + (5 * ENCODER_TICKS_PER_REVOLUTION));
        rightIntakeMotor.setTargetPosition(targetPos);
        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightIntakeMotor.setPower(0.25f);
        while (rightIntakeMotor.isBusy()) {
            telemetry.addLine("Intaking");
            telemetry.update();
        }
        rightIntakeMotor.setPower(0);
        */

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
}