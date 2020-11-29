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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


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

@Autonomous(name="Basic: Linear OpMode", group="Linear Opmode")
public class Auto_Test extends LinearOpMode {

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    // private Telemetry telemetry;
    private OpMode mode;

    private DcMotor LeftFront = null;
    private DcMotor LeftRear = null;
    private DcMotor RightFront = null;
    private DcMotor RightRear = null;

    private Servo shooter = null;

    double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized Auto_Test");
        telemetry.update();

        // Hardware map
        LeftFront  = hardwareMap.get(DcMotor.class, "LeftFront");
        LeftRear  = hardwareMap.get(DcMotor.class, "LeftRear");
        RightFront  = hardwareMap.get(DcMotor.class, "RightFront");
        RightRear  = hardwareMap.get(DcMotor.class, "RightRear");
        shooter = hardwareMap.get(Servo.class, "shooterServo");
        
        // ---------------------------------- NEW ----------------------------------
        LeftFront.setDirection(DcMotor.Direction.REVERSE);
        LeftRear.setDirection(DcMotor.Direction.REVERSE);
        RightFront.setDirection(DcMotor.Direction.FORWARD);
        RightFront.setDirection(DcMotor.Direction.FORWARD);

        LeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // ---------------- CREATING INSTANCE OF MECWHEELOPS CLASS ----------------
        // MecWheelOps ops = new MecWheelOps(telemetry, this, LeftFront, LeftRear, RightFront, RightRear);
        // ops.setUpEncoders();
        setUpEncoders();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // --------------------------- TESTS ---------------------------
        // Moving LeftFront 1000 ticks
        // ops.moveTicks(1000, LeftFront, 0.25f);
        // moveTicks(1000, LeftFront, 0.25f);

        // Moving the robot forward for 5 seconds
        // ops.moveForSeconds(5, 0.25f);
        // moveForSeconds(5, 0.25f);

        // Moving the robot backwards for 5 seconds
        // ops.moveForSeconds(5, 0.25f);
        // moveForSeconds(5, -0.25f);

        // Moving 10 inches forward
        // ops.moveInches(10, 0.25f);
        // moveInches(10, 0.25f);

        // Moving 10 inches backward
        // ops.moveInches(-10, 0.25f);
        moveInches(10, -0.25f);
    }

    public void moveForSeconds(int secs, double speed){
        LeftFront.setPower(speed);
        LeftRear.setPower(speed);
        RightFront.setPower(speed);
        RightRear.setPower(speed);

        sleep(secs*1000);

        LeftFront.setPower(0);
        LeftRear.setPower(0);
        RightFront.setPower(0);
        RightRear.setPower(0);
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
                telemetry.addData("Encoder position", motor.getCurrentPosition());
                telemetry.addData("Target position", motTarget);
                telemetry.update();
            }
        }
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveInches(int inches, double speed){
        telemetry.addLine("INSIDE MOVEINCHES");
        sleep(1000);
        setUpEncoders();

        if(opModeIsActive()) {
            // Get current motor positions
            int lfPos = LeftFront.getCurrentPosition();
            int lrPos = LeftRear.getCurrentPosition();
            int rfPos = RightFront.getCurrentPosition();
            int rrPos = RightRear.getCurrentPosition();

            // Calculate new target positions
            lfPos += inches * ENCODER_TICKS_PER_INCH;
            lrPos += inches * ENCODER_TICKS_PER_INCH;
            rfPos += inches * ENCODER_TICKS_PER_INCH;
            rrPos += inches * ENCODER_TICKS_PER_INCH;

            // Set the motors to the calculated positions
            LeftFront.setTargetPosition(lfPos); // integerizing problem
            LeftRear.setTargetPosition(lrPos);
            RightFront.setTargetPosition(rfPos);
            RightRear.setTargetPosition(rrPos);

            LeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LeftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LeftFront.setPower(speed);
            LeftRear.setPower(speed);
            RightFront.setPower(speed);
            RightRear.setPower(speed);

            // sleep(5000);

            while (LeftFront.isBusy()) {
                telemetry.addLine("Moving forward");
                telemetry.addData("Target LF", lfPos);
                telemetry.addData("Actual LF", LeftFront.getCurrentPosition());
                //  telemetry.addData("Actual" , "%.2f", LeftFront.getCurrentPosition() , LeftRear.getCurrentPosition(), RightRear.getCurrentPosition(), RightFront.getCurrentPosition());
                telemetry.update();
                sleep(200);
            }
            sleep(200);
        }

        // Stop all the motors
        LeftFront.setPower(0);
        LeftRear.setPower(0);
        RightFront.setPower(0);
        RightRear.setPower(0);

        LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}
