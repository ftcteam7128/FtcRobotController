package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecWheelOps {

    private double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    private Telemetry telemetry;
    private OpMode mode;

    private DcMotor LeftFront = null;
    private DcMotor LeftRear = null;
    private DcMotor RightFront = null;
    private DcMotor RightRear = null;

    // -------------------------------- CONSTRUCTOR --------------------------------------------

    public MecWheelOps(Telemetry t, OpMode m, DcMotor lf, DcMotor lr, DcMotor rf, DcMotor rr){
        telemetry = t;
        mode = m;
        LeftFront = lf;
        LeftRear = lr;
        RightFront = rf;
        RightRear = rr;
    }

    // ------------------------------------------ METHODS ------------------------------------------

    public void moveTicks(int ticks, DcMotor motor, double speed){

        setUpEncoders();
        int motTarget;

        // ---------------- Code that was inside of the if (opModeIsActive()) ----------------

        motTarget = motor.getCurrentPosition() + ticks;
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setTargetPosition(motTarget);
        motor.setPower(speed);
        while(motor.isBusy()) { // check runtime position as well?
            telemetry.addData("Encoder position", motor.getCurrentPosition());
            telemetry.addData("Target position", motTarget);
            telemetry.update();
        }

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveForSeconds(int secs, double speed){
        LeftFront.setPower(speed);
        LeftRear.setPower(speed);
        RightFront.setPower(speed);
        RightRear.setPower(speed);

        // sleep(secs*1000);
        runtime.reset();
        while(runtime.time() < secs){
            telemetry.addLine("moving");
            telemetry.update();
        }

        LeftFront.setPower(0);
        LeftRear.setPower(0);
        RightFront.setPower(0);
        RightRear.setPower(0);
    }

    public void moveInches(int inches, double speed){
        telemetry.addLine("INSIDE MOVEINCHES");
        // sleep(1000);
        setUpEncoders();

        // ---------------- Code that was inside of the if (opModeIsActive()) ----------------


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


    public void strafeRight(int inches, double speed){

        setUpEncoders();

        // if(opModeIsActive()) {
            // Get current motor positions
            int lfPos = LeftFront.getCurrentPosition();
            int lrPos = LeftRear.getCurrentPosition();
            int rfPos = RightFront.getCurrentPosition();
            int rrPos = RightRear.getCurrentPosition();

            //calculate target positions
            lfPos += inches * ENCODER_TICKS_PER_INCH;
            lrPos -= inches * ENCODER_TICKS_PER_INCH;
            rfPos -= inches * ENCODER_TICKS_PER_INCH;
            rrPos += inches * ENCODER_TICKS_PER_INCH;

            // Set the motors to the calculated positions
            LeftFront.setTargetPosition(lfPos);
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

            // waiting for it to complete
            while (LeftFront.isBusy() && LeftRear.isBusy() && RightFront.isBusy() && RightRear.isBusy()) {
                telemetry.addLine("Strafing");
                telemetry.addData("Target LF", lfPos);
                telemetry.addData("Actual LF", LeftFront.getCurrentPosition());
                // telemetry.addData("Target", "%.2f", lfPos, lrPos, rrPos, rfPos);
                // telemetry.addData("Actual", " %.2f", LeftFront.getCurrentPosition(), LeftRear.getCurrentPosition(), RightRear.getCurrentPosition(), RightFront.getCurrentPosition());
                telemetry.update();
            }
        // }

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

    // ----------------------------------- HELPER METHODS -----------------------------------

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


}
