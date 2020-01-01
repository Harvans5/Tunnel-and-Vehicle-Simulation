package cs131.pa2.CarsTunnels;

import java.util.Comparator;

import cs131.pa2.Abstract.Vehicle;

public class VehicleCompare implements Comparator<Vehicle>{

	public int compare(Vehicle v1, Vehicle v2) {
		return v2.getPriority() - v1.getPriority();
	}

}
