import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Part2 implements Behavior {

	private boolean suppressed = false;
	public static boolean donePart2;
	private DifferentialPilot pilote;
	private LightSensor lightSensor;

	public Part2(DifferentialPilot pilote, LightSensor lightSensor) {
		this.pilote = pilote;
		this.lightSensor = lightSensor;
	}

	public boolean takeControl() {
		return MartinController.Part1IsDone;
	}

	public void action() {
		suppressed = false;
		pilote.forward();
		while (!suppressed && lightSensor.getFloodlight() == 30) {
			Thread.yield();// libere le processeur
		}
		pilote.stop();
		pilote.travel(11.0);// A VERIFIER
		pilote.arc(0, -90, true);
		while (!suppressed && pilote.isMoving()) {
			Thread.yield();// libere le processeur
		}
		pilote.stop();
		pilote.backward();
		while (!suppressed && lightSensor.getFloodlight() == 40) {
			Thread.yield();
		}
		pilote.stop();
	}

	/**
	 * @pre --
	 * @post {suppressed == true}, ce qui provoque l'arret de {action}.
	 */
	public void suppress() {
		suppressed = true;
	}

}
