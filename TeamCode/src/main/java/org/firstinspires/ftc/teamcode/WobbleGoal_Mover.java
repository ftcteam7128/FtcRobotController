package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class WobbleGoal_Mover extends LinearOpMode {

    // Core hex motor to initialize facing up
    // Enocders to move up/down
    // Later use gamepad buttons to move

    private DcMotor baseMotor = null;
    private Servo gateServo = null;
    private Telemetry telem = null;
    double upPos = 0;
    double leftPos = 0;
    double rightPos = 0;

    @Override
    public void runOpMode(){
        baseMotor = hardwareMap.get(DcMotor.class, "BaseMotor");
        gateServo = hardwareMap.get(Servo.class, "GateServo");

        baseMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        baseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // manually store/set the desired position (facing up)
        // we only need to check the first time (we can save the actual position then)
        upPos = baseMotor.getCurrentPosition();
        telem.addData("Desired pos: ", upPos);

        baseMotor.setTargetPosition((int) upPos);
        baseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        baseMotor.setPower(0.25f);

        while(opModeIsActive()){
            boolean changeGateState = gamepad2.y;
            // boolean moveLeft = gamepad2.x;
            // boolean moveRight = gamepad2.b;
            double moveDirec = gamepad2.left_stick_x;
            // double stickPos = gamepad2.right_stick_y;

            if (changeGateState) { wobbleGoalPincher(); }
            // if (moveLeft) { moveLeft(); }
            // if (moveRight) { moveRight(); }

            // baseMotor.setTargetPosition((int)(baseMotor.getCurrentPosition() + moveDirec));
            baseMotor.setPower(moveDirec);
        }
    }
    /*
    public void loop(){

        boolean changeGateState = gamepad2.y;
        // boolean moveLeft = gamepad2.x;
        // boolean moveRight = gamepad2.b;
        double moveDirec = gamepad2.left_stick_x;
       // double stickPos = gamepad2.right_stick_y;

        if (changeGateState) { wobbleGoalPincher(); }
        // if (moveLeft) { moveLeft(); }
        // if (moveRight) { moveRight(); }

        baseMotor.setTargetPosition(baseMotor.getCurrentPosition() + moveDirec);
        baseMotor.setPower(0.25f);

    }
*/
    public void wobbleGoalPincher(){

        if(gateServo.getPosition() < 0.5 ) gateServo.setPosition(1.0);
        else gateServo.setPosition(0);
    }



    public void moveLeft (){
        if (baseMotor.getCurrentPosition()==upPos){
            baseMotor.setTargetPosition(leftPos);
            baseMotor.setPower(0.25f);
        }
        else {
            baseMotor.setTargetPosition(upPos);
            baseMotor.setPower(0.25f);
        }
    }
    public void moveRight (){
        if (baseMotor.getCurrentPosition()==upPos){
            baseMotor.setTargetPosition(rightPos);
            baseMotor.setPower(0.25f);
        }
        else {
            baseMotor.setTargetPosition(upPos);
            baseMotor.setPower(0.25f);
        }
    }


}
