package ru.cellularwildfire.data;

public final class ForestTypeConditions {
  public static double determineActivationEnergy(int forestType) {
    return switch (forestType) {
      case ForestType.EVERGREEN_NEEDLE_LEAF -> 120_000;
      case ForestType.EVERGREEN_BROADLEAF -> 140_000;
      case ForestType.DECIDUOUS_NEEDLE_LEAF -> 160_000;
      case ForestType.DECIDUOUS_BROADLEAF -> 170_000;
      case ForestType.MIXED -> 150_000;
      default -> Double.POSITIVE_INFINITY;
    };
  }

  public static final class ForestType {
    public static final int EVERGREEN_NEEDLE_LEAF = 1;
    public static final int EVERGREEN_BROADLEAF = 2;
    public static final int DECIDUOUS_NEEDLE_LEAF = 3;
    public static final int DECIDUOUS_BROADLEAF = 4;
    public static final int MIXED = 5;
  }
}
