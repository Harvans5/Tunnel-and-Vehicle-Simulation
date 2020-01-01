package cs131.pa2.CarsTunnels;

import java.util.Collection;

import cs131.pa2.Abstract.Direction;
import cs131.pa2.Abstract.Factory;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

/**
 * The class implementing the Factory interface for creating instances of classes
 * @author cs131a
 *
 */
public class ConcreteFactory implements Factory {

    /**
     * Returns the basic tunnel that was created
     * @param name, the name of the tunnel
     */
    public Tunnel createNewBasicTunnel(String name){
    		return new BasicTunnel(name);   
    }

    /**
     * returns the car that was created
     * @param name, The name of the car
     * @param direction, the direction that the car is heading
     */
    public Vehicle createNewCar(String name, Direction direction){
    	return new Car(name,direction);   
    }

    /**
     * returns the sled that was created
     * @param name, the name of the sled
     * @param direction, the direction that the sled is heading
     */
    public Vehicle createNewSled(String name, Direction direction){
    		return new Sled(name, direction);   
    }

    /**
     * returns the priority scheduler created
     * @param name, name of priority scheduler
     * @param tunnels, collection of tunnels that the priority scheduler will contain
     * @param log, log for the scheduler
     */
    public Tunnel createNewPriorityScheduler(String name, Collection<Tunnel> tunnels, Log log){
    		return new PriorityScheduler(name, tunnels, log);
    }
    /**
     * returns the ambulance that was created
     * @param name, The name of the ambulance
     * @param direction, the direction that the ambulance is heading
     */
	public Vehicle createNewAmbulance(String name, Direction direction) {
		return new Ambulance(name, direction);
	}

	 /**
     * returns the preemptive priority scheduler created
     * @param name, name of preemptive priority scheduler
     * @param tunnels, collection of tunnels that the preemptive priority scheduler will contain
     * @param log, log for the scheduler
     */
	public Tunnel createNewPreemptivePriorityScheduler(String name, Collection<Tunnel> tunnels, Log log) {
		return new PreemptivePriorityScheduler(name, tunnels, log);
	}
}
