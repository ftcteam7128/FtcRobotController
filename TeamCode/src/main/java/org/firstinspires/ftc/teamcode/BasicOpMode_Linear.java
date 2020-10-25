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

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")

public class BasicOpMode_Linear extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor LeftFront = null;
    private DcMotor LeftRear = null;
    private DcMotor RightFront = null;
    private DcMotor RightRear = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        // leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        // rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        LeftFront = hardwareMap.get(DcMotor.class, "LeftFront");
        LeftRear = hardwareMap.get(DcMotor.class, "LeftRear");
        RightFront = hardwareMap.get(DcMotor.class, "RightFront");
        RightRear = hardwareMap.get(DcMotor.class, "RightRear");

        // Testing each motor individualy
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



    }

    public void moveInches(int inches, double speed){

        // Get current motor positions
        double lfPos = LeftFront.getCurrentPosition();
        double lrPos = LeftRear.getCurrentPosition();
        double rfPos = RightFront.getCurrentPosition();
        double rrPos = RightRear.getCurrentPosition();

        double ENCODER_TICKS_PER_INCH = (288./(2.6* 4 * Math.PI));

        // Calculate new target positions
        lfPos += inches * ENCODER_TICKS_PER_INCH;
        lrPos += inches * ENCODER_TICKS_PER_INCH;
        rfPos += inches * ENCODER_TICKS_PER_INCH;
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
            telemetry.addLine("Moving forward");
            telemetry.addData("Target" , %.2f, %.2f, %,2f, %.2f, lfPos , lrPos, rrPos, rfPos);  telemetry.addData("Actual" , %.2f, %.2f, %,2f, %.2f, LeftFront.getCurrentPosition() , LeftRear.getCurrentPosition(), RightRear.getCurrentPosition(), RightFront.getCurrentPosition());
            telemetry.update();
        }

        // Stop all the motors
        LeftFront.setPower(0);
        LeftRear.setPower(0);
        RightFront.setPower(0);
        RightRear.setPower(0);
    }

}
