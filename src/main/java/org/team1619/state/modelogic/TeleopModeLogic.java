package org.team1619.state.modelogic;

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

		boolean mIsStowed;
		boolean mRollersOn;

		mIsStowed = false;
		mRollersOn = false;

		mIsStowed = fSharedInputValues.getBoolean("ipb_collector_solenoid_position");



	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isReady(String name) {
		switch (name) {

			case "st_drivetrain_percent":
				return true;
			case "st_collector_zero":
				return !fSharedInputValues.getBoolean("ipb_collector_has_been_zeroed");
			case "st_collector_retract":
				return fSharedInputValues.getBooleanRisingEdge("ipb_driver_left_bumper") && fSharedInputValues.getBoolean("ipb_collector_solenoid_position");


			case "st_collector_floor_intake":
				return fSharedInputValues.getBoolean("ipb_driver_left_trigger");

			case "st_collector_extend":
				return fSharedInputValues.getBooleanRisingEdge("ipb_driver_left_trigger") && !fSharedInputValues.getBoolean("ipb_collector_solenoid_position");
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
