package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class Red_Auto {

    // Declare OpMode members
    private ElapsedTime runtime = new ElapsedTime();
    // private Telemetry telemetry;
    private OpMode mode;

    private DcMotor LeftFront = null;
    private DcMotor LeftRear = null;
    private DcMotor RightFront = null;
    private DcMotor RightRear = null;

    double ENCODER_TICKS_PER_INCH = ((28 * 40)/2.6)/(Math.PI*4);

    // shooter
    // servo, shooter wheels
    Servo shooter;

    @Override
    public void runOpMode(){

        // 1. Start (already holding a wobble goal and 3 rings)
        // 2. Use enocders and the color sensor to find the rings
        // 3. Scan and figure out how many rings there are (0, 1, 4)
        // 4. Move to the target goal (A, B, C)
        // 5. Drop the wobble goal
        // 6. Move back to the launch zone
        // 7. Shoot the rings

        telemetry.addData("Status", "Initialized Auto_Test");
        telemetry.update();

        // Hardware map
        LeftFront  = hardwareMap.get(DcMotor.class, "LeftFront");
        LeftRear  = hardwareMap.get(DcMotor.class, "LeftRear");
        RightFront  = hardwareMap.get(DcMotor.class, "RightFront");
        RightRear  = hardwareMap.get(DcMotor.class, "RightRear");
    }
}
