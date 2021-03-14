package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class WobbleGoal_Tester extends LinearOpMode {

    // Core hex motor to initialize facing up
    // Enocders to move up/down
    // Later use gamepad buttons to move

    private DcMotor baseMotor = null;
    private Servo gateServo = null;
    private Telemetry telem = null;\

    private int motorInitAngle = 0; // FIND BY TESTING
    private int targetMotorAngle = 0; // FIND BY TESTING

    private int initServoAngle = 0; // FIND BY TESTING
    private int targetServoAngle = 0; // FIND BY TESTING

    @Override
    public void runOpMode(){

        baseMotor = hardwareMap.get(DcMotor.class, "BaseMotor");
        gateServo = hardwareMap.get(Servo.class, "GateServo");

        baseMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        baseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        baseMotor.setTargetPosition(motorInitAngle);
        baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // baseMotor.setPower(0.25f);

        gateServo.setPosition(initServoAngle);

        waitForStart();

        while(opModeIsActive()){
            boolean moveMotor = gamepad1.a;
            boolean pinch = gamepad1.y;

            if(moveMotor){
                if(baseMotor.getCurrentPosition() < targetMotorAngle) baseMotor.setPower(0.5f);
                else baseMotor.setPower(-0.5f);
            }

            if(pinch) {
                if (gateServo.getPosition() < targetServoAngle)
                    gateServo.setPosition(targetServoAngle);
                else gateServo.setPosition(initServoAngle);
            }

        }
    }

}
