package org.team3467.robot2019.subsystems.Cargo;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.command.Command;

public class CrawlBot extends Command {

    public CrawlBot() {
        requires(Robot.sub_cargointake);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        if(Robot.sub_drivetrain.crawlModeEnabled) {

            if(Robot.robot_oi.getDriverLeftTrigger() == 0 && Robot.robot_oi.getDriverRightTrigger()  > 0) {
                    Robot.sub_cargointake.roller_setRollers(Robot.robot_oi.getDriverRightTrigger(), RobotGlobal.DIRECTION_NORMAL);
            } else {
                Robot.sub_cargointake.roller_setRollers(Robot.robot_oi.getDriverLeftTrigger(), RobotGlobal.DIRECTION_REVERSE);

            }

        }
    }


}