package org.team3467.robot2019.robot;

public final class RobotGlobal {

    //Addresses for Talons, Victors, etc.
        // left side
    public final static int DRIVEBASE_VICTOR_1 = 1;
    public final static int DRIVEBASE_VICTOR_2 = 2;
    public final static int DRIVEBASE_TALON_3 = 3;
         // right side
    public final static int DRIVEBASE_VICTOR_4 = 4;
    public final static int DRIVEBASE_VICTOR_5 = 5;
    public final static int DRIVEBASE_TALON_6 = 6;

    public final static int CARGO_LIFT = 7;
    public final static int CARGO_INTAKE_ARM_1 = 8;
    public final static int CARGO_INTAKE_ARM_2 = 9;
    public final static int CARGO_INTAKE_ROLLER = 10;
    public final static int CARGO_HOLD =  11;

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
    public static final int PDP_CARGO_HOLD_CHAN = 3;  // TODO: Set this correctly

}