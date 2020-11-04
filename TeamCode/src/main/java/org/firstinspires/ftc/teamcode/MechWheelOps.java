package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class MechWheelOps {

    private DcMotor LeftFront = null;
    private DcMotor LeftRear = null;
    private DcMotor RightFront = null;
    private DcMotor RightRear = null;

    private OpMode opMode;
    private Telemetry telemetry;
    double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);

    public MechWheelOps(OpMode mode, DcMotor lf, DcMotor lr, DcMotor rf, DcMotor rr){
        LeftFront = lf;
        LeftRear = lr;
        RightFront = rf;
        RightRear = rr;

        opMode = mode;
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
