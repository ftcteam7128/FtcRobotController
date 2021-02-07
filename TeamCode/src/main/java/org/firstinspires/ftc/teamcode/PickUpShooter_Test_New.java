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
public class PickUpShooter_Test_New extends LinearOpMode{

    DcMotor rightIntakeMotor;
    Servo intakeServo;
    DcMotor shooterMotor;

    // double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);
    double ENCODER_TICKS_PER_REVOLUTION = 288;


    public void runOpMode(){
        rightIntakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeServo = hardwareMap.get(Servo.class, "IntakeServo");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooter");

        intakeServo.setPosition(0);

        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int targetPos = (int) (rightIntakeMotor.getCurrentPosition() + (5*ENCODER_TICKS_PER_REVOLUTION));
        rightIntakeMotor.setTargetPosition(targetPos);
        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightIntakeMotor.setPower(0.25f);
        while(rightIntakeMotor.isBusy()){
            telemetry.addLine("Intaking");
            telemetry.update();
        }
        rightIntakeMotor.setPower(0);

        while(opModeIsActive()){
            boolean push = gamepad2.x;
            boolean shoot = gamepad2.b;

            if (push) {
                // intakeServo.setPosition(Range.clip(0.5, 0, 1));
                // intakeServo.setPosition(intakeServo.getPosition()+0.5);
                telemetry.addLine("Pushing");
                telemetry.update();
                intakeServo.setPosition(0.5); // min: -1, max: 1
            }
            if (shoot) {
                shooterMotor.setPower(0.25f);
            }
        }
    }
/*
    @Override
    public void start() {
        intakeServo.setPosition(0);

        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int targetPos = (int) (rightIntakeMotor.getCurrentPosition() + (5*ENCODER_TICKS_PER_REVOLUTION));
        rightIntakeMotor.setTargetPosition(targetPos);
        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightIntakeMotor.setPower(0.25f);
        while(rightIntakeMotor.isBusy()){
            telemetry.addLine("Intaking");
            telemetry.update();
        }
        rightIntakeMotor.setPower(0);
    }
*/
    @Override
    public void loop(){

        boolean push = gamepad2.x;
        boolean shoot = gamepad2.b;

        if (push) {
            // intakeServo.setPosition(Range.clip(0.5, 0, 1));
            // intakeServo.setPosition(intakeServo.getPosition()+0.5);
            telemetry.addLine("Pushing");
            telemetry.update();
            intakeServo.setPosition(0.5); // min: -1, max: 1
        }
        if (shoot) {
            shooterMotor.setPower(0.25f);
            /*
            shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            int targetPos = (int) (shooterMotor.getCurrentPosition() + (2*ENCODER_TICKS_PER_REVOLUTION));
            shooterMotor.setTargetPosition(targetPos);
            shooterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            shooterMotor.setPower(0.25f);
            while(shooterMotor.isBusy()){
                telemetry.addLine("Shooting");
                telemetry.update();
            }
            shooterMotor.setPower(0);
             */
        }

    }

    // ---------------------------- Mech methods ----------------------------

    public void push(){
        // move servo 90 degrees
        // intakeServo.setPosition(0.5);
        intakeServo.setPosition(Range.clip(0.5, 0, 1));
    }

    public void shoot() {
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int targetPos = (int) (shooterMotor.getCurrentPosition() + (2*ENCODER_TICKS_PER_REVOLUTION));
        shooterMotor.setTargetPosition(targetPos);
        shooterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooterMotor.setPower(0.25f);
        while(shooterMotor.isBusy()){
            telemetry.addLine("Shooting");
            telemetry.update();
        }
        shooterMotor.setPower(0);
    }

    // figure out how to add ring scanning
}
