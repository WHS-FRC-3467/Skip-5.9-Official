## Destination: Deep Space

This is the repository for all official, working, and completed builds for the Team 3467 2019 robot.

* PLEASE KEEP DEVELOPMENT BUILDS IN Skip-5.9-Development

|Subsystem|PDP Slots|Controllers|CAN IDs| Motor|Sensors|
|---------|---------|-----------|-------|------|-------|
|[Drivebase](#subsystem-drivebase)|6|2 Talon, 4 Victor (slaves)|1->6|miniCIM|Encoder on each side of drivebase, each connected to the Talon SRX for that side |
|[CargoLift](#subsystem-cargolift)|1|Talon SRX|7|RS-775pro|VP EncoderSlice|
|[CargoIntake](#subsystem-cargointake)|3|Arm: 2 Talon SRX Roller: 1 Victor|8->10|RS-775pro|Encoder on CargoArm|
|[CargoHold](#subsystem-cargohold)|1|Victor SPX|11|BAG motor|Limit switch or photosensor to detect when Cargo is in|
|[HatchGrabber](#subsystem-hatchgrabber)|2|Victor SPX|12-13|Bosch Seat motor|Internal Hall Effect sensors
|[Limelight](#subsystem-limelight)|1|||||
|[LEDs](#subsystem-leds)|1||||    Needs buck converter module (12V -> 5V)
|[Lidar](#subsystem-lidar)|1|||||               1
|[Gyro](#subsystem-gyro)|1||14||Pigeon IMU on CAN - attached to Talon or direct to CANbus|               1
|Totals:|15||||

## Subsystem: Drivebase

*Methods:*

*Commands:*

    DriveBot() - teleoperated driving
    <other commands as needed>

## Subsystem: CargoLift

*Methods:*

*Commands:*

    PositionLift(enum liftPosition)
        enumeration liftPosition:
            Home / LoadingStation / CargoShip / Rocket_L1 / Rocket_L2 / Rocket_L3
*Other Notes:*

    Use Talon's MotionMagic() to position CargoLift


## Subsystem: CargoIntake

*Methods:*

*Commands:*

    StowIntake()
    PositionIntake()
    PositionForLift()
    LiftWithIntake()
    IntakeCargo()
    ReleaseCargo()
    CrawlForward()
    CrawlBackward()

*Other Notes:*

    Will need different speeds for controlling Cargo vs. crawling forward using CargoRoller.
    Use Talon's MotionMagic() to position CargoArm

## Subsystem: CargoHold

*Methods:*

*Commands:*

    IntakeCargo()
    ReleaseCargo(enum ReleaseSpeed)
        enumeration for release speed in case we need to "shoot" the cargo into a target rather than gently spit it out

## Subsystem: HatchGrabber

*Methods:*

*Commands:*

    deployGrabber()
    stowGrabber()
    grabHatch()
    releaseHatch()

## Subsystem: Limelight

*Methods:*
    getTx()
    getTy()
    getTa()
    hasTarget()
    getLED()

*Commands:*
    enableLED()
    disableLED()


## Subsystem: LEDs

*Methods:*
    isEnabled()

*Commands:*
    enable()
    disable()
    setEffect()


## Subsystem: Lidar

*Methods:*
    getDistance(int side)

*Commands:*
    zeroDistance(int side)


## Subsystem: Gyro

*Methods:*
    
*Commands:*

    getCurrentAngle()
    zeroAngle()
