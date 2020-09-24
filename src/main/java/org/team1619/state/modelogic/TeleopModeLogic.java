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

	boolean mIsStowed = false;
	boolean mRollersOn= false;



	public TeleopModeLogic(InputValues inputValues, RobotConfiguration robotConfiguration) {
		super(inputValues, robotConfiguration);
	}

	@Override
	public void initialize() {
		sLogger.info("***** TELEOP *****");


	}

	@Override
	public void update() {


		if(fSharedInputValues.getBooleanRisingEdge("ipb_driver_left_bumper")) {

			mRollersOn = false;
			mIsStowed = false;


		}

		if(fSharedInputValues.getBooleanRisingEdge("ipb_driver_left_trigger") ) {

			mRollersOn = !mRollersOn;
			mIsStowed = true;


		}


	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isReady(String name) {
		switch (name) {

			//collector

			case "st_drivetrain_percent":
				return true;
			case "st_collector_zero":
				return !fSharedInputValues.getBoolean("ipb_collector_has_been_zeroed");

			case "st_collector_retract":
				return mIsStowed && !mRollersOn;

			case "st_collector_floor_intake":
				return !mIsStowed && mRollersOn;

			case "st_collector_extend":
				return !mIsStowed && !mRollersOn;


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
