package org.usfirst.frc.falcons6443.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author Ivan Kenevich, Shivashriganesh Mahato
 */
public class AutonomousMove extends SimpleCommand implements PIDOutput {

    private double pidOutput;
    private double distance, charge, time;
    private int direction;

    private final double TIME_TO_ROLl_TO_A_STOP = 0.5;

    /**
     * Constructor for SimpleCommand.
     */
    public AutonomousMove(double distance, double charge, boolean isReversed) {
        super("Autonomous Movement");

        requires(driveTrain);
        requires(navigation);

        this.distance = distance;
        this.charge = charge;

        time = 0;
        direction = isReversed ? -1 : 1;
    }

    @Override
    public void initialize() {
        initPIDController();

        // magic numbers   WOOOOOOOH
        time = (-0.037819 * charge + 0.74216) * distance + (-0.3676 * charge + 4.26483);
        if (time < 0) {
            time = 0;
            DriverStation.reportWarning("Time is less than 0. Make sure distance is greater than or equal to 2",
                    false);
        }
        setTimeout(time);
    }

    private void initPIDController() {
        navigation.reset();
        navigation.initPIDController(this);
        navigation.pid.setOutputRange(-0.05, 0.05);
        navigation.pid.setSetpoint((direction > 0) ? 0 : 179);
        navigation.enablePID();
    }

    @Override
    public void execute() {
        driveTrain.tankDrive(direction * (0.6 + pidOutput), direction * (0.6 - pidOutput));
    }

    @Override
    protected boolean isFinished() {
        if (isTimedOut()) {
            driveTrain.tankDrive(0, 0);
            Timer.delay(TIME_TO_ROLl_TO_A_STOP);
            navigation.freePID();
            return true;
        }
        return false;
    }

    @Override
    public void pidWrite(double output) {
        pidOutput = output;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public double getTime() {
        return time;
    }
}
