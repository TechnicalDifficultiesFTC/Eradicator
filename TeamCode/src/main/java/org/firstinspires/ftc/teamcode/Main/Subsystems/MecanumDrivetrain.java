package org.firstinspires.ftc.teamcode.Main.Subsystems;

import org.firstinspires.ftc.teamcode.Main.Constants;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

public class MecanumDrivetrain {
    static DcMotor frontLeftMotor;
    static DcMotor backLeftMotor;
    static DcMotor frontRightMotor;
    static DcMotor backRightMotor;

    double y;
    double x;
    double rx;
    double denominator;
    double frontLeftPower;
    double backLeftPower;
    double frontRightPower;
    double backRightPower;
    boolean lowPowerMode;

    double modulator;

    /**
     * Initialize the drivetrain and pass in appropriate motor objects
     * @param FLM Front Left Motor
     * @param BLM Back Left Motor
     * @param FRM Front Right Motor
     * @param BRM Back Right Motor
     */
    public MecanumDrivetrain(DcMotor FLM, DcMotor BLM, DcMotor FRM, DcMotor BRM){
        frontLeftMotor = FLM;
        backLeftMotor = BLM;
        frontRightMotor = FRM;
        backRightMotor = BRM;
        //These motors have to be set to reverse by standard so that Mecanum works
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Sets the zero power behavior of *all* drivetrain motors to input parameter
     * @param zeroPowerBehavior A zeroPowerBehavior which can be found at DcMotor.ZeroPowerBehavior.[Const here]
     */
    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        frontLeftMotor.setZeroPowerBehavior(zeroPowerBehavior);
        backLeftMotor.setZeroPowerBehavior(zeroPowerBehavior);
        frontRightMotor.setZeroPowerBehavior(zeroPowerBehavior);
        backRightMotor.setZeroPowerBehavior(zeroPowerBehavior);
    }

    /**
     * Handle drivetrain logic and update motors as such
     * @param gamepad All input from gamepad (1)
     */
    public void processInput(Gamepad gamepad){

        if (gamepad.dpad_right) {
            lowPowerMode = true;
        }
        else if (gamepad.dpad_left) {
            lowPowerMode = false;
        }

        modulator = lowPowerMode ? Constants.MIN_SPEED : Constants.MAX_SPEED;

        y = -gamepad.left_stick_y;
        x = gamepad.left_stick_x * 1.1;
        rx = gamepad.right_stick_x;

        denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeftPower = ((y + x + rx) / denominator)*modulator;
        backLeftPower = ((y - x + rx) / denominator)*modulator;
        frontRightPower = ((y - x - rx) / denominator)*modulator;
        backRightPower = ((y + x - rx) / denominator)*modulator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }

    /**
     * Returns state of field lowPowerMode
     * @return lowPowerMode
     */
    public boolean isLowPowerMode() {
        return lowPowerMode;
    }

}