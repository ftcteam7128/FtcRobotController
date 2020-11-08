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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

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

public class Auto_Test extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor LeftFront = null;
    private DcMotor LeftRear = null;
    private DcMotor RightFront = null;
    private DcMotor RightRear = null;
    private Telemetry telem;

    // double ENCODER_TICKS_PER_INCH = (288./(2.6* 4 * Math.PI));
    // double ENCODER_TICKS_PER_INCH = (4*Math.PI) / 360.; // basically 0
    // double ENCODER_TICKS_PER_INCH = 100; // 4000      288 40 2.6
    double ENCODER_TICKS_PER_INCH = ((28 * 40) / 2.6) / (Math.PI * 4);
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

        MechWheelOps ops = new MechWheelOps(this, LeftFront, LeftRear, RightFront, RightRear, telem);

        // Setting encoders
        LeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // ops.setUpEncoders();

        waitForStart();

        ops.moveInches(10, 0.25f);
        ops.turnRight(90, 0.25f);
        ops.strafeRight(10, 0.25f);
        ops.turnRight(-90, 0.25f);
        ops.strafeRight(-10, 0.25f);


    }
}

