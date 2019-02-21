package org.team3467.robot2019.subsystems.Cargo;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class DisableCrawl extends InstantCommand {

@Override
protected boolean isFinished() {
    return true;
}
    public DisableCrawl() {

    }

    @Override
    protected void execute() {

        if(safetyCheck()) {

            Robot.sub_cargointake.arm_moveDirectlyToPosition(CargoIntake.eCargoIntakeArmPosition.INTAKE);
            Robot.sub_drivetrain.crawlModeEnabled = false;

        } else {
            System.out.println("CRAWL SAFETY CHECK FAILED");
        }


    }

    /**
     * Checks to make sure all subsystems are ready and in position to move the cargo intake into CRAWL mode
     */
    private boolean safetyCheck() {
        boolean passed = false;

        return passed;
    }

}