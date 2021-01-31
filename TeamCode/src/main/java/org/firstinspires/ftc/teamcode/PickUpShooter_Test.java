package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class PickUpShooter_Test extends OpMode{

    DcMotor rightIntakeMotor;
    Servo intakeServo;
    DcMotor shooterMotor;

    // double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);
    double ENCODER_TICKS_PER_REVOLUTION = 288;


    public void init(){
        rightIntakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
        intakeServo = hardwareMap.get(Servo.class, "IntakeServo");
        shooterMotor = hardwareMap.get(DcMotor.class, "ShooterMotor");

        intakeServo.setPosition(0);

        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int targetPos = (int) (rightIntakeMotor.getCurrentPosition() + (5*ENCODER_TICKS_PER_REVOLUTION));
        rightIntakeMotor.setTargetPosition(targetPos);
        rightIntakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightIntakeMotor.setPower(0.25f);
        while(rightIntakeMotor.isBusy()){
            telemetry.update("Intaking");
        }
        rightIntakeMotor.setPower(0);
    }

    public void loop(){

        boolean push = gamepad2.x;
        boolean shoot = gamepad2.b;

        if (push) { push(); }
        if (shoot) { shoot(); }

    }

    // ---------------------------- Mech methods ----------------------------

    public void push(){
        // move servo 90 degrees
        intakeServo.setPosition(0.5);
    }

    public void shoot() {
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int targetPos = (int) (shooterMotor.getCurrentPosition() + (2*ENCODER_TICKS_PER_REVOLUTION));
        shooterMotor.setTargetPosition(targetPos);
        shooterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooterMotor.setPower(0.25f);
        while(shooterMotor.isBusy()){
            telemetry.update("Shooting");
        }
        shooterMotor.setPower(0);
    }

    // figure out how to add ring scanning
}
