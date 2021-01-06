package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MechGamePad_Test extends OpMode{

    Servo wobbleServo;
    DcMotor leftIntakeMotor;
    DcMotor rightIntakeMotor;
    Servo intakeServo;

    public void init(){
        wobbleServo = hardwareMap.get(Servo.class, "WobbleServo");
    }

    public void loop(){

        boolean pincherMechButtonPressed = gamepad2.a;
        boolean shooterMechButtonPressed = gamepad2.b;
        boolean intakeMechButtonPressed = gamepad2.x;
        // double slideyPos = gamepad2.right_stick_y;

        if (pincherMechButtonPressed) { wobbleGoalPincher(); }
        if (shooterMechButtonPressed) { shoot(); }
        if (intakeMechButtonPressed) { intake(); }

        // if() {

        // }

    }

    // ---------------------------- Mech methods ----------------------------

    public void wobbleGoalPincher(){
        wobbleServo.setPosition(1.0);
    }

    public void intake() {

        leftIntakeMotor.setPower(0.25);
        rightIntakeMotor.setPower(0.25);
        // sleep(5000);

        leftIntakeMotor.setPower(0);
        rightIntakeMotor.setPower(0);
       // sleep(1000);

        intakeServo.setPosition(1.0);
    }

    public void shoot() {
        // Sam
    }

    // figure out how to add ring scanning
}
