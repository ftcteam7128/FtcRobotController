package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Basic: Linear OpMode", group="Linear Opmode")

public class ColorSensor_Test extends LinearOpMode{

    ColorSensor col_sensor;
    DcMotor LeftFront;
    DcMotor LeftRear;
    DcMotor RightFront;
    DcMotor RightRear;

    //@Override
    public void runOpMode(){
        //waitForStart();

        col_sensor = hardwareMap.colorSensor.get("color_sensor");
        LeftFront  = hardwareMap.get(DcMotor.class, "LeftFront");
        LeftRear  = hardwareMap.get(DcMotor.class, "LeftRear");
        RightFront  = hardwareMap.get(DcMotor.class, "RightFront");
        RightRear  = hardwareMap.get(DcMotor.class, "RightRear");

        int red = col_sensor.red();
        int green = col_sensor.green();
        int blue = col_sensor.blue();

        while (red < 240 && green < 240 && blue < 240){
            telemetry.addData("Status", "No white line detected");
            telemetry.update();
            /*
            LeftFront.setPower(0.25f);
            RightRear.setPower(0.25f);
            RightFront.setPower(0.25f);
            LeftRear.setPower(0.25f);
             */
        }

        telemetry.addData("Status", "Found white line");
        telemetry.update();
        /*
        LeftFront.setPower(0);
        RightRear.setPower(0);
        RightFront.setPower(0);
        LeftRear.setPower(0);
        */
        //telemetry.addData("Red: ", red);
        //telemetry.addData("Green: ", green);
        //telemetry.addData("Blue: ", blue);
        //telemetry.update();
    }
}
