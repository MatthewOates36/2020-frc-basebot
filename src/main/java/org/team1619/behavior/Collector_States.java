package org.team1619.behavior;

import org.uacr.models.behavior.Behavior;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.OutputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.Config;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

import java.util.Set;

/**
 * Example behavior to copy for other behaviors
 */

public class Collector_States implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Collector_States.class);
	private static final Set<String> sSubsystems = Set.of("ss_collector");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;

	private boolean mSolenoidPosition;
	private double mRollerSpeed;

	public Collector_States(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;
	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

		mRollerSpeed = config.getDouble("roller_speed");
		mSolenoidPosition = config.getBoolean("solenoid_position");
	}

	@Override
	public void update() {

		fSharedOutputValues.setNumeric("opn_collector_roller", "percent", mRollerSpeed);
		fSharedOutputValues.setBoolean("opb_collector_deploy", mSolenoidPosition);

	}

	@Override
	public void dispose() {
		fSharedOutputValues.setNumeric("opn_collector_roller", "percent", 0.0);
		fSharedOutputValues.setBoolean("opb_collector_deploy", false);
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}
