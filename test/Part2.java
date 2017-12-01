import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Part2 implements Behavior {

	private boolean suppressed = false;
	private DifferentialPilot pilote;
	private LightSensor lightSensor;
	private UltrasonicSensor sonar;

	public Part2(DifferentialPilot pilote,UltrasonicSensor sonar, LightSensor lightSensor) {
		this.pilote = pilote;
		this.sonar = sonar;
		this.lightSensor = lightSensor;
	}

	public boolean takeControl() {
		return MartinController.ReperageIsDone && !MartinController.Part2IsDone;
	}

	public void action() {
		suppressed = false;
		pilote.forward();
		while (!suppressed && lightSensor.getLightValue() > 44) {
			Thread.yield();// libere le processeur
		}
		pilote.stop();
		MartinController.avionX = 119 - sonar.getDistance();
		pilote.travel(18);// avance pour pouvoir s'aligner
		pilote.arc(-10, 90, true); // se positionne sur la ligne
		while (!suppressed && pilote.isMoving()) {
			Thread.yield();// libere le processeur
		}
		pilote.stop();
		pilote.backward(); // le robot recule jussqua l avion
		while (!suppressed && ((int) lightSensor.getLightValue()) > 36) {
			Thread.yield();
		}
		pilote.stop();
		pilote.travel(-4);
		Motor.B.rotate(830); // ferme la pince
		MartinController.Part2IsDone = true; // part2 est finie
	}

	/**
	 * @pre --
	 * @post {suppressed == true}, ce qui provoque l'arret de {action}.
	 */
	public void suppress() {
		suppressed = true;
	}

}
