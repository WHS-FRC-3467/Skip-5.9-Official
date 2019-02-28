package org.team3467.robot2019.robot;

public final class RobotGlobal {

    //limelight ip address: 10.34.67.57:5801

    //Addresses for Talons, Victors, etc.
        // right side
    public final static int DRIVEBASE_TALON_R = 1;
    public final static int DRIVEBASE_VICTOR_R1 = 2;
    public final static int DRIVEBASE_VICTOR_R2 = 3;
         // left side
    public final static int DRIVEBASE_TALON_L = 6;
    public final static int DRIVEBASE_VICTOR_L1 = 7;
    public final static int DRIVEBASE_VICTOR_L2 = 8;
    
    public final static int CARGO_INTAKE_ARM_1 = 9;
    public final static int CARGO_INTAKE_ROLLER = 5;
    public final static int CARGO_INTAKE_ARM_2 = 4;

    public final static int HATCH_GRABBER = 10;
    public final static int HATCH_ACTUATOR = 13;

    public final static int CARGO_HOLD = 11;
    public final static int CARGO_LIFT = 12;

    public final static int GYRO = 11;

    //Motor direction constants
    public static final int DIRECTION_NORMAL = 1;
    public static final int DIRECTION_REVERSE = -1;

    //Side constants
    public static final int SIDE_LEFT = 0;
    public static final int SIDE_RIGHT = 1;


}

/*

M1: Talon for right drivebase, controlling the two left DB victors, 40 amp, 1 BOB
M2: 1st right DB victor, 40
M3: 2nd right DB victor, 40
M4: 1st right cargo intake talon, 40, BOB to limit switch NEEDED
M5: 2nd right cargo intake talon, 30
M6: Talon for left drivebase, controlling the two left DB victors, port 0 on PDP (40) 
M7: 1st left DB victor, port 1 on PDP (40)
M8: 2nd left DB victor, port 2 on PDP (40)
M9: Left cargo intake talon, port 3 on PDP (40), one BOB to encoder
M10: 1st talon for hatch, port 7 on PDP (30) 
M11: 2nd talon for hatch(seat motor), port 6 on PDP (30), one BOB to limit switch 
M12: 1 talon for 4-bar, port 5 on PDP(30)
M13: 1 talon for cargo hold, port 4 on PDP(30)



Issues:
Limelight
MS Cam
Shuffleboard

ToDo:

CargoIntake:
CargoIntake.java:
    - Set encoder readingss for position enum
    - Tune PID params

CargoLift:
LiftToPosition.java:
    - Make a regualr Command (not Instant)
    - Report stats
*/