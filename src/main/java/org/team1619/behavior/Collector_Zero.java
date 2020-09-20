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
 * Example behavior to copy for other behaviors
 */

public class Collector_Zero implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(Collector_Zero.class);
	private static final Set<String> sSubsystems = Set.of("ss_collector");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;
	private Timer myTimer;
	private long timeoutTime = 500;



	public Collector_Zero(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;


	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);
		myTimer = new Timer();
		myTimer.start(timeoutTime);

	}

	@Override
	public void update() {
		fSharedOutputValues.setBoolean("opb_collectors_deploy", false);
		fSharedOutputValues.setNumeric("opn_collectors_rollers","percent", 0);
		fSharedOutputValues.setBoolean("ipb_collector_has_been_zeroed", true);
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isDone() {
		myTimer.isDone();
		fSharedOutputValues.setBoolean("ipb_collector_has_been_zeroed", false);
		return true;
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}