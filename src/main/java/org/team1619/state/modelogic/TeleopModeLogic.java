package org.team1619.state.modelogic;

import org.team1619.behavior.Collector_States;
import org.uacr.models.state.State;
import org.uacr.robot.AbstractModeLogic;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

/**
 * Handles the isReady and isDone logic for teleop mode on competition bot
 */

public class TeleopModeLogic extends AbstractModeLogic {
    private boolean collectorExtend = true;
    private boolean rollerPower = true;

    private static final Logger sLogger = LogManager.getLogger(TeleopModeLogic.class);

    public TeleopModeLogic(InputValues inputValues, RobotConfiguration robotConfiguration) {
        super(inputValues, robotConfiguration);
    }

    @Override
    public void initialize() {
        sLogger.info("***** TELEOP *****");
    }

    @Override
    public void update() {
        if (fSharedInputValues.getBooleanRisingEdge("ipb_driver_left_trigger")) {
            collectorExtend = !collectorExtend;
        }
        if (fSharedInputValues.getBooleanRisingEdge("ipb_driver_right_bumper")) {
            rollerPower = !rollerPower;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isReady(String name) {
        switch (name) {
            case "st_drive_percent":
                return true;
            case "st_collector_zero":
                return true;
            case "st_collector_intake_floor":
                return collectorExtend && rollerPower;
            case "st_collector_extend":
                return collectorExtend && !rollerPower;
            case "st_collector_retract":
                return !collectorExtend && !rollerPower;
            default:
                return false;
        }
    }

    @Override
    public boolean isDone(String name, State state) {
        switch (name) {
            default:
                return state.isDone();
        }
    }
}
