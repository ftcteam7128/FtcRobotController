package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MechWheelOps {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor LeftFront = null;
    private DcMotor LeftRear = null;
    private DcMotor RightFront = null;
    private DcMotor RightRear = null;

    private OpMode opMode;
    private Telemetry telemetry;
    double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);

    public MechWheelOps(OpMode mode, DcMotor lf, DcMotor lr, DcMotor rf, DcMotor rr, Telemetry telem){
        this.LeftFront = lf;
        this.LeftRear = lr;
        this.RightFront = rf;
        this.RightRear = rr;

        this.opMode = mode;
        this.telemetry = telem;
    }

    public void moveInches(int inches, double speed){
        telemetry.addLine("INSIDE MOVEINCHES");
        // sleep(1000);
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

            sleep(5000);

            while (LeftFront.isBusy()) {
                telemetry.addLine("Moving forward");
                telemetry.addData("Target LF", lfPos);
                telemetry.addData("Actual LF", LeftFront.getCurrentPosition());
                //  telemetry.addData("Actual" , "%.2f", LeftFront.getCurrentPosition() , LeftRear.getCurrentPosition(), RightRear.getCurrentPosition(), RightFront.getCurrentPosition());
                telemetry.update();
            }

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


    public void setUpEncoders(){
        LeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
