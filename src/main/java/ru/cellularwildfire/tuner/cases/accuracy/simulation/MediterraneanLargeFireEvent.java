package ru.cellularwildfire.tuner.cases.accuracy.simulation;

import java.time.Duration;
import ru.cellularwildfire.algorithms.ThermalAlgorithm;
import ru.cellularwildfire.data.ForestTypeConditions.ForestType;
import ru.cellularwildfire.models.Simulation;
import ru.cellularwildfire.services.Simulator;
import ru.cellularwildfire.tuner.experiment.Assessment;
import ru.cellularwildfire.tuner.experiment.TuneCase;
import ru.cellularwildfire.tuner.services.SlopedTerrainService;
import ru.cellularwildfire.tuner.services.UniformWeatherService;

public class MediterraneanLargeFireEvent extends TuneCase {
  private static final int FOREST_TYPE = ForestType.MIXED;
  private static final double FUEL = 0.5;
  private static final double AIR_TEMPERATURE = 30;
  private static final double AIR_HUMIDITY = 0.3;
  private static final double WIND_X = -7;
  private static final double WIND_Y = -5;

  @Override
  public void assess(ThermalAlgorithm algorithm, Assessment assessment)
      throws TuneCaseFailedException {
    Simulator simulator =
        new Simulator(
            new SlopedTerrainService(FOREST_TYPE, FUEL, 10, 180),
            new UniformWeatherService(AIR_TEMPERATURE, AIR_HUMIDITY, WIND_X, WIND_Y),
            algorithm);
    Simulation simulation = startDefaultSimulation(simulator);

    int ticks = (int) Duration.ofHours(7).dividedBy(simulation.getTimeline().getStepDuration());
    simulator.tryProgressSimulation(simulation, ticks);

    double damagedHectares = estimateDamagedHectares(simulation);
    assessment.scoreLogAccuracy(damagedHectares, 20000, 5, "Damaged area");
  }
}
