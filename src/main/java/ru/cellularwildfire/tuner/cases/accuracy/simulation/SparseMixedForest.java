package ru.cellularwildfire.tuner.cases.accuracy.simulation;

import ru.cellularwildfire.algorithms.ThermalAlgorithm;
import ru.cellularwildfire.data.ForestTypeConditions.ForestType;
import ru.cellularwildfire.models.Simulation;
import ru.cellularwildfire.services.Simulator;
import ru.cellularwildfire.tuner.experiment.Assessment;
import ru.cellularwildfire.tuner.experiment.TuneCase;
import ru.cellularwildfire.tuner.services.UniformTerrainService;
import ru.cellularwildfire.tuner.services.UniformWeatherService;

public final class SparseMixedForest extends TuneCase {
  private static final int FOREST_TYPE = ForestType.MIXED;
  private static final double FUEL = 0.1;
  private static final double AIR_TEMPERATURE = 25;
  private static final double AIR_HUMIDITY = 0.4;
  private static final double WIND_X = 2;
  private static final double WIND_Y = 1;

  @Override
  public void assess(ThermalAlgorithm algorithm, Assessment assessment)
      throws TuneCaseFailedException {
    Simulator simulator =
        new Simulator(
            new UniformTerrainService(FOREST_TYPE, FUEL, 0),
            new UniformWeatherService(AIR_TEMPERATURE, AIR_HUMIDITY, WIND_X, WIND_Y),
            algorithm);
    Simulation simulation = startDefaultSimulation(simulator);

    while (hasBurningCells(simulation)) {
      simulator.tryProgressSimulation(simulation, simulation.getSteps().size());
      if (9 <= countDamagedCells(simulation)) {
        assessment.failure("Sparse mixed forest burns.");
      }
    }
    assessment.victory();
  }
}
