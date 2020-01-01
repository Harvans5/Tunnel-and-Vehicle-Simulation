package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import java.util.*;

/**
 * 
 * The class for the Basic Tunnel, extending Tunnel.
 * @author cs131a
 *
 */
public class BasicTunnel extends Tunnel{
	//Stores all vehicles that are currently in the tunnel
	LinkedList<Vehicle> l = new LinkedList<Vehicle>();
	/**
	 * Creates a new instance of a basic tunnel with the given name
	 * @param name the name of the basic tunnel
	 */
	public BasicTunnel(String name) {
		super(name);
	}

	@Override
	/**
	 * Method checks to make sure that the vehicle is elgible to enter the tunnel
	 * @param the vehicle that is trying to enter the tunnel
	 */
	protected synchronized boolean tryToEnterInner(Vehicle vehicle) {
		/**
		 * First checks to make sure that if there are any vehicles in the tunnel that those vehicles are heading in the same direction
		 * as the vehicle trying to enter
		 */
		if(l.size() == 0) {
			l.add(vehicle);
			return true;
		//Addition to the basic tunnel that if the vehicle trying to enter is an ambulance it is allowed entrance
		} else if(vehicle instanceof Ambulance) {
			l.add(vehicle);
			return true;
		} else {
			if(vehicle instanceof Car) {
				return checkTunnelCars(vehicle);
			} else {
				return checkTunnelSleds(vehicle);
			}
		}
	}

	@Override
	/**
	 * Removes the vehicle from the tunnel
	 * @param vehicle trying to be removed from the tunnel
	 */
	public synchronized void exitTunnelInner(Vehicle vehicle) {
		int i = 0; 
		//Finds location of vehicle trying to be removed from the linkedlist
		for(int x = 0; x < l.size(); x++) {
			if(l.get(x).equals(vehicle)) {
				i = x;
			}
		}
		//Removes the vehicle from the linkedlist which represents the tunnel
		l.remove(i);
		
	}
	/**
	 * If the vehicle trying to be added to the tunnel is a car this method will check 
	 * to make sure that all vehicles that are already in the tunnel are cars as well and there are less than 
	 * 3 cars in the tunnel
	 * @param vehicle trying to be added to the linkedlist
	 * @return boolean of wether or not the car is elgible to enter the tunnel
	 */
	public boolean checkTunnelCars(Vehicle vehicle) {
		//Checks if all vehicles in the tunnel are cars
		for(int i = 0; i < l.size(); i++) {
			Vehicle curr = l.get(i);
			if(!(curr instanceof Car)) {
				return false;
			}
		}
		//checks to make sure there is less than 3 cars already in the tunnel
		if(l.size() >= 3) {
			return false;
		}
		if(!(l.get(0).getDirection().equals(vehicle.getDirection()))) {
			return false;
		}
		//If the car is able to enter the tunnel it is added to the linkedlist
		l.add(vehicle);
		return true;
	}
	/**
	 * If the vehicle trying to be added to the tunnel is a sled this method will check to 
	 * make sure that the list is empty because there can only be 1 sled in the tunnel at once
	 * @param vehicle trying to be added to the linked list
	 * @return wether or not the sled can be added
	 */
	public boolean checkTunnelSleds(Vehicle vehicle) {
		if(l.isEmpty()) {
			l.add(vehicle);
			return true;
		}
		return false;
	}
	
}
