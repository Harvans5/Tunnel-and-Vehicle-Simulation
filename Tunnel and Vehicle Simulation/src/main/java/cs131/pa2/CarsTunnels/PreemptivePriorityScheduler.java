package cs131.pa2.CarsTunnels;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The preemptive priority scheduler assigns vehicles to tunnels based on their priority and supports ambulances.
 * It extends the Tunnel class.
 * @author cs131a
 *
 */
public class PreemptivePriorityScheduler extends Tunnel{
	//Lock & condition for the vehicle threads
	Lock l = new ReentrantLock();
	Condition enter = l.newCondition();
	//List for tunnels, vehicles and then a map for keeping track of what tunnel the vehicles are in
	LinkedList<BasicTunnel> tunnels = new LinkedList<BasicTunnel>();
	PriorityQueue<Vehicle> vehicles = new PriorityQueue<Vehicle>(new VehicleCompare());
	HashMap<Vehicle, BasicTunnel> hm = new HashMap<Vehicle, BasicTunnel>();
	/**
	 * Creates a new instance of the class PreemptivePriorityScheduler with given name by calling the constructor of the super class
	 * @param name the name of the preemptive priority scheduler to create
	 */
	public PreemptivePriorityScheduler(String name) {
		super(name);
	}
	/**
	 * Creates a preemptive priority scheduler using the super class and then casting the collection of tunnels into a linked list
	 * of type basic tunnel
	 * @param name, the name of the priority scheduler
	 * @param tunnels, all the tunnels that this scheduler needs to take care of
	 * @param log, the priority schedulers log
	 */
	public PreemptivePriorityScheduler(String name, Collection<Tunnel> tunnels, Log log) {
		super(name, log);
		Iterator<Tunnel>i = tunnels.iterator();
		while(i.hasNext()) {
			BasicTunnel t = (BasicTunnel)i.next();
			this.tunnels.add(t);
		}
	}

	/**
	 * This class gets passed a vehicle and tries to enter it to a tunnel if it has the highest priority or 
	 * if no other vehicle is waiting to enter
	 * @param vehicle, the vehicle that is trying to enter a tunnel
	 * @return boolean, on wether or not the vehicle could enter
	 */
	public boolean tryToEnterInner(Vehicle vehicle) {
		try {
			//Adds vehicle to prioirty queue and locks the thread of the vehicle
			l.lock();
			vehicles.add(vehicle);
			//Checks if the vehicle isn't highest priority or if it can't enter and if so 
			//it is supposed to wait
			while(!(vehicle.equals(vehicles.peek()) && checkAdd(vehicle))) {
				enter.await();
			}
			//Once the while loop exits the highest priority vehicle was added and is now removed from the waiting list
			//The thread is then unlocked once the vehicle has entered
			vehicles.poll();
			l.unlock();
			return true;
		} catch(InterruptedException e){
			e.printStackTrace();
		} 
		return false;
	}
	/**
	 * Checks if any of the tunnels has room and is compatible for the vehicle to enter
	 * @param vehicle trying to enter a tunnel
	 * @return boolean on wether or not the vehicle can enter any of the tunnels
	 */
	public boolean checkAdd(Vehicle vehicle) {
		//Iterates over all tunnels and calls the try to enter method to see if any of the vehicles can enter
		Iterator<BasicTunnel> curr = tunnels.iterator();
		while(curr.hasNext()) {
			BasicTunnel t = curr.next();
			if(t.tryToEnter(vehicle)) {
				hm.put(vehicle, t);
				//If a tunnel is compatible it exits the method returning true
				return true;
			}
		}
		return false;
	}
	/**
	 * Removes the vehicle from the tunnel
	 * @param vehicle trying to be removed from the tunnel
	 */
	public void exitTunnelInner(Vehicle vehicle) {
		//Locks the thread
		l.lock();
		//Gets the vehicles corresponding tunnel that is trying to exit and calls exit on that tunnel for the vehicle exiting
		BasicTunnel t = hm.get(vehicle);
		t.exitTunnelInner(vehicle);
		hm.remove(vehicle);
		enter.signalAll();
		l.unlock();
	}
	
}

