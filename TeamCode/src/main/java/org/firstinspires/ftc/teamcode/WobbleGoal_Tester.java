package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class WobbleGoal_Tester extends LinearOpMode {

    Servo servo;
    public void runOpMode(){

        servo = hardwareMap.get(Servo.class, "servo");
        waitForStart();
        servo.setPosition(1.0);
    }
}
