package org.team1619.behavior;

import org.uacr.models.behavior.Behavior;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.OutputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.Config;
import org.uacr.utilities.Timer;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

import java.util.Set;

/**
 * States for the new Collector
 */

public class Collector_States implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Collector_States.class);
	private static final Set<String> sSubsystems = Set.of("ss_collector");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;

	private boolean fSolenoidPosition;
	private double fRollerSpeed;


	public Collector_States(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;

		fSolenoidPosition = false;
		fRollerSpeed = 0.0;

	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);
		fSolenoidPosition = config.getBoolean("solenoid_position", false);
		fRollerSpeed = config.getDouble("roller_speed", 0.0);


	}

	@Override
	public void update() {


			fSharedOutputValues.setNumeric("opn_collectors_rollers", "percent", fRollerSpeed);
			fSharedOutputValues.setBoolean("opb_collectors_deploy", fSolenoidPosition);

	}

	@Override
	public void dispose() {
		fSharedOutputValues.setNumeric("opn_collectors_rollers", "percent", 0);
		fSharedOutputValues.setBoolean("opb_collectors_deploy", false);	}

	@Override
	public boolean isDone() {
		fSharedOutputValues.setBoolean("ipb_collector_has_been_zeroed", false);
		return true;
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}