package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Basic: Linear OpMode", group="Linear Opmode")

public class ColorSensor_Test {

    ColorSensor col_sensor;

    public void init(){
        //col_sensor = HardwareMap.colorSensor.get("color_sensor");
    }

    //@Override
    public void runOpMode(){
        //waitForStart();

        int red = col_sensor.red();
        int green = col_sensor.green();
        int blue = col_sensor.blue();

        //telemetry.addData("Red: ", red);
        //telemetry.addData("Green: ", green);
        //telemetry.addData("Blue: ", blue);
        //telemetry.update();
    }
}
