/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

/*
11/5 TEST PLAN
- moveTicks()
    - Check one motor individually to see if it moves exactly how many ticks it was supposed to move
    - Print encoder position in telemetry
    - Use a timeout to slow down the while loop? What is the significance of that?

- newMoveInches()
    - Test this only if moveTicks() was accurate

 */
@Autonomous(name="Basic: Linear OpMode", group="Linear Opmode") // CHANGE ANNOTATION

public class BasicOpMode_Linear extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor LeftFront = null;
    private DcMotor LeftRear = null;
    private DcMotor RightFront = null;
    private DcMotor RightRear = null;

    // double ENCODER_TICKS_PER_INCH = (288./(2.6* 4 * Math.PI));
    // double ENCODER_TICKS_PER_INCH = (4*Math.PI) / 360.; // basically 0
    // double ENCODER_TICKS_PER_INCH = 100; // 4000      288 40 2.6
    double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);
    // diameter*pi / 360

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Hardware map
        LeftFront = hardwareMap.get(DcMotor.class, "LeftFront");
        LeftRear = hardwareMap.get(DcMotor.class, "LeftRear");
        RightFront = hardwareMap.get(DcMotor.class, "RightFront");
        RightRear = hardwareMap.get(DcMotor.class, "RightRear");

        // Setting encoders
        setUpEncoders();

        waitForStart();

        // Testing each motor individually (backwards)
        LeftFront.setPower(0.5);
        sleep(1000);
        LeftFront.setPower(0);
        sleep(1000);
        RightFront.setPower(-0.5);
        sleep(1000);
        RightFront.setPower(0);
        sleep(1000);
        LeftRear.setPower(0.5);
        sleep(1000);
        LeftRear.setPower(0);
        sleep(1000);
        RightRear.setPower(-0.5);
        sleep(1000);
        RightRear.setPower(0);

        // Moving forward
        LeftFront.setPower(-0.5);
        RightFront.setPower(0.5);
        LeftRear.setPower(-0.5);
        RightRear.setPower(0.5);
        sleep(5000);
        LeftFront.setPower(0);
        RightFront.setPower(0);
        LeftRear.setPower(0);
        RightRear.setPower(0);

        // Moving backward
        LeftFront.setPower(0.5);
        RightFront.setPower(-0.5);
        LeftRear.setPower(0.5);
        RightRear.setPower(-0.5);
        sleep(5000);
        LeftFront.setPower(0);
        RightFront.setPower(0);
        LeftRear.setPower(0);
        RightRear.setPower(0);

        // Move 10 inches forward
        // moveInches(10, 0.25f); // 20 in

        // Strafe 10 inches right
        // strafeRight(10, 0.25f);

        // Strafe 10 inches left
        // strafeRight(-10, 0.25f);

        // Turning 90 degrees right
        // turnRight(90, 0.25f);

        // Turning 90 degrees left
        // turnRight(-90, 0.25f);

        //  11/5 TEST
        moveTicks(1000, LeftFront, 0.25f);
        newMoveInches(10, 0.25f);

    }

    public void moveTicks(int ticks, DcMotor motor, double speed){

        setUpEncoders();
        int motTarget;

        if(opModeIsActive()){

            motTarget = motor.getCurrentPosition() + ticks;
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor.setTargetPosition(motTarget);

            runtime.reset(); // Why?
            motor.setPower(speed);

            while(opModeIsActive() && motor.isBusy()) { // check runtime position as well?
                telemetry.addData("Encoder position", "%.2f", motor.getCurrentPosition());
                telemetry.addData("Target position", "%.2f", motTarget);
                telemetry.update();
            }
        }
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void newMoveInches(int inches,  double speed/*, double timeout*/){

        setUpEncoders();

        int lfTarget;
        int lrTarget;
        int rfTarget;
        int rrTarget;

        if(opModeIsActive()){

            lfTarget = LeftFront.getCurrentPosition() + (int)(ENCODER_TICKS_PER_INCH*inches);
            lrTarget = LeftRear.getCurrentPosition() + (int)(ENCODER_TICKS_PER_INCH*inches);
            rfTarget = RightFront.getCurrentPosition() + (int)(ENCODER_TICKS_PER_INCH*inches);
            rrTarget = RightRear.getCurrentPosition() + (int)(ENCODER_TICKS_PER_INCH*inches);

            setRunToPos();

            LeftFront.setTargetPosition(lfTarget);
            LeftRear.setTargetPosition(lrTarget);
            RightFront.setTargetPosition(rfTarget);
            RightRear.setTargetPosition(rrTarget);

            runtime.reset(); // Why?

            LeftFront.setPower(speed);
            LeftRear.setPower(speed);
            RightFront.setPower(speed);
            RightRear.setPower(speed);

            // Check if runtime is less than timeout parameter here as well?
            while(opModeIsActive() && (LeftFront.isBusy() || LeftRear.isBusy() || RightFront.isBusy() || RightRear.isBusy())){
                telemetry.addData("LF Target", "%.2f", lfTarget);
                telemetry.addData("LR Target", "%.2f", lrTarget);
                telemetry.addData("RF Target", "%.2f", rfTarget);
                telemetry.addData("LF Target", "%.2f", lfTarget);
                telemetry.addData("LR Target", "%.2f", lrTarget);

                telemetry.addData("LF POS", "%.2f", LeftFront.getCurrentPosition());
                telemetry.addData("LR POS", "%.2f", LeftRear.getCurrentPosition());
                telemetry.addData("RF POS", "%.2f", RightFront.getCurrentPosition());
                telemetry.addData("RR POS", "%.2f", RightRear.getCurrentPosition());

                telemetry.update();
            }
            stopAllMotors();

            LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }
    }

    public void stopAllMotors(){
        LeftFront.setPower(0);
        LeftRear.setPower(0);
        RightFront.setPower(0);
        RightRear.setPower(0);
    }

    public void setRunToPos(){
        LeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setUpEncoders(){
        LeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveInches(int inches, double speed){

        // Get current motor positions
        double lfPos = LeftFront.getCurrentPosition();
        double lrPos = LeftRear.getCurrentPosition();
        double rfPos = RightFront.getCurrentPosition();
        double rrPos = RightRear.getCurrentPosition();

        // Calculate new target positions
        lfPos += inches * ENCODER_TICKS_PER_INCH;
        lrPos += inches * ENCODER_TICKS_PER_INCH;
        rfPos += inches * ENCODER_TICKS_PER_INCH;
        rrPos += inches * ENCODER_TICKS_PER_INCH;

        LeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set the motors to the calculated positions
        LeftFront.setTargetPosition((int)lfPos); // integerizing problem
        LeftRear.setTargetPosition((int)lrPos);
        RightFront.setTargetPosition((int)rfPos);
        RightRear.setTargetPosition((int)rrPos);

        LeftFront.setPower(speed);
        LeftRear.setPower(speed);
        RightFront.setPower(speed);
        RightRear.setPower(speed);

        sleep(5000); // shorten
        // whey isnt while loop exeecuting? one isnt busy
        // Waiting for it to complete
        while(LeftFront.isBusy() && LeftRear.isBusy() && RightFront.isBusy() && RightRear.isBusy()){
            telemetry.addLine("Moving forward");
           //  telemetry.addData("Target", "%.2f", lfPos, lrPos, rrPos, rfPos);
           //  telemetry.addData("Actual" , "%.2f", LeftFront.getCurrentPosition() , LeftRear.getCurrentPosition(), RightRear.getCurrentPosition(), RightFront.getCurrentPosition());
            telemetry.update();
            sleep(100);
        }

        // Stop all the motors
        LeftFront.setPower(0);
        LeftRear.setPower(0);
        RightFront.setPower(0);
        RightRear.setPower(0);


    }

    public void strafeRight(int inches, double speed){
        // Get current motor positions
        double lfPos = LeftFront.getCurrentPosition();
        double lrPos = LeftRear.getCurrentPosition();
        double rfPos = RightFront.getCurrentPosition();
        double rrPos = RightRear.getCurrentPosition();

        //calculate target positions
        lfPos += inches * ENCODER_TICKS_PER_INCH;
        lrPos -= inches * ENCODER_TICKS_PER_INCH;
        rfPos -= inches * ENCODER_TICKS_PER_INCH;
        rrPos += inches * ENCODER_TICKS_PER_INCH;

        // Set the motors to the calculated positions
        LeftFront.setTargetPosition((int)lfPos);
        LeftRear.setTargetPosition((int)lrPos);
        RightFront.setTargetPosition((int)rfPos);
        RightRear.setTargetPosition((int)rrPos);

        LeftFront.setPower(speed);
        LeftRear.setPower(speed);
        RightFront.setPower(speed);
        RightRear.setPower(speed);

        // waiting for it to complete
        while(LeftFront.isBusy() && LeftRear.isBusy() && RightFront.isBusy() && RightRear.isBusy()){
            telemetry.addLine("Strafing");
            telemetry.addData("Target" ,"%.2f", lfPos , lrPos, rrPos, rfPos);
            telemetry.addData("Actual" ," %.2f", LeftFront.getCurrentPosition() , LeftRear.getCurrentPosition(), RightRear.getCurrentPosition(), RightFront.getCurrentPosition());
            telemetry.update();
        }

        // Stop all the motors
        LeftFront.setPower(0);
        LeftRear.setPower(0);
        RightFront.setPower(0);
        RightRear.setPower(0);
    }

    public void turnRight(int deg, double speed){

        // Get current motor positions
        int lfPos = LeftFront.getCurrentPosition();
        int lrPos = LeftRear.getCurrentPosition();
        int rfPos = RightFront.getCurrentPosition();
        int rrPos = RightRear.getCurrentPosition();

        //calculate target positions
        lfPos += deg * ENCODER_TICKS_PER_INCH;
        lrPos += deg * ENCODER_TICKS_PER_INCH;
        rfPos -= deg * ENCODER_TICKS_PER_INCH;
        rrPos -= deg * ENCODER_TICKS_PER_INCH;

        // Set the motors to the calculated positions
        LeftFront.setTargetPosition(lfPos);
        LeftRear.setTargetPosition(lrPos);
        RightFront.setTargetPosition(rfPos);
        RightRear.setTargetPosition(rrPos);

        LeftFront.setPower(speed);
        LeftRear.setPower(speed);
        RightFront.setPower(speed);
        RightRear.setPower(speed);

        // Waiting for it to complete
        while(LeftFront.isBusy() && LeftRear.isBusy() && RightFront.isBusy() && RightRear.isBusy()){
            telemetry.addLine("Turning");
            telemetry.addData("Target", "%.2f", lfPos , lrPos, rrPos, rfPos);
            telemetry.addData("Actual" , "%.2f", LeftFront.getCurrentPosition() , LeftRear.getCurrentPosition(), RightRear.getCurrentPosition(), RightFront.getCurrentPosition());
            telemetry.update();
        }

        // Stop all the motors
        LeftFront.setPower(0);
        LeftRear.setPower(0);
        RightFront.setPower(0);
        RightRear.setPower(0);
    }

}
