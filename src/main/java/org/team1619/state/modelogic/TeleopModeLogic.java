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

	private boolean collectorIsDown;
	private boolean rollersAreOn;

	public TeleopModeLogic(InputValues inputValues, RobotConfiguration robotConfiguration) {
		super(inputValues, robotConfiguration);
		collectorIsDown = true;
		rollersAreOn = false;
	}

	@Override
	public void initialize() {
		sLogger.info("***** TELEOP *****");
	}

	@Override
	public void update() {

		if(fSharedInputValues.getBooleanRisingEdge("ipb_operator_left_trigger")) {
			collectorIsDown = true;
			rollersAreOn = !rollersAreOn;
		}
		if(fSharedInputValues.getBooleanRisingEdge("ipb_operator_left_bumper")) {
			collectorIsDown = false;
			rollersAreOn = false;
		}

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isReady(String name) {
		switch (name) {
			case "st_collector_zero":
				return !fSharedInputValues.getBoolean("ipb_collector_has_been_zeroed");
			case "st_collector_floor_intake":
				return collectorIsDown && rollersAreOn;
			case "st_collector_extend":
				return collectorIsDown && !rollersAreOn;
			case "st_collector_retract":
				return !collectorIsDown && !rollersAreOn;
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
