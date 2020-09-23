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
 * Zeroes the Collector at start up and waits 500 milliseconds
 */

public class Collector_Zero implements Behavior {

    private static final Logger sLogger = LogManager.getLogger(Collector_Zero.class);
    private static final Set<String> sSubsystems = Set.of("ss_collector");

    private final InputValues fSharedInputValues;
    private final OutputValues fSharedOutputValues;

    private Timer fDelay;

    public Collector_Zero(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
        fSharedInputValues = inputValues;
        fSharedOutputValues = outputValues;
        fDelay = new Timer();
    }

    @Override
    public void initialize(String stateName, Config config) {
        sLogger.debug("Entering state {}", stateName);

        boolean solenoidPosition = config.getBoolean("solenoid_position", true);
        double rollerSpeed = config.getDouble("roller_speed", 0.0);
        int delay = config.getInt("time_delay", 500);

        fSharedOutputValues.setBoolean("opb_collector_extend", solenoidPosition);
        fSharedOutputValues.setNumeric("opn_collector_rollers", "percent", rollerSpeed);
        fSharedInputValues.setBoolean("ipn_collector_position", solenoidPosition);
        fDelay.start(delay);

    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isDone() {
        if(fDelay.isDone()) {
            fSharedInputValues.setBoolean("ipb_collector_has_been_zeroed", true);
            sLogger.debug("Collector -> Zeroed");
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getSubsystems() {
        return sSubsystems;
    }
}