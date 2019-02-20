package org.team3467.robot2019.robot;

public final class RobotGlobal {

    //limelight ip address: 10.34.67.57:5801

    //Addresses for Talons, Victors, etc.
        // left side
    public final static int DRIVEBASE_VICTOR_1 = 8;
    public final static int DRIVEBASE_VICTOR_2 = 11;
    public final static int DRIVEBASE_TALON_3 = 33;
         // right side
    public final static int DRIVEBASE_VICTOR_4 = 12;
    public final static int DRIVEBASE_VICTOR_5 = 14;
    public final static int DRIVEBASE_TALON_6 = 35;

    public final static int CARGO_LIFT = 4;
    public final static int CARGO_INTAKE_ARM_1 = 45;
    public final static int CARGO_INTAKE_ARM_2 = 46;
    public final static int CARGO_INTAKE_ROLLER = 10;
    public final static int CARGO_HOLD =  40;

    public final static int HATCH_GRABBER = 12;
    public final static int HATCH_ACTUATOR = 13;

    public final static int GYRO = 14;

    //Motor direction constants
    public static final int DIRECTION_NORMAL = 1;
    public static final int DIRECTION_REVERSE = -1;

    //Side constants
    public static final int SIDE_LEFT = 0;
    public static final int SIDE_RIGHT = 1;

    // PDP Channels
    public static final int PDP_CARGO_HOLD_CHAN = 11;  // TODO: Set this correctly

}