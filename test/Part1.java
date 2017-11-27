import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class Part1 implements Behavior {
	private boolean suppressed = false; // pour synchroniser la suppression
	private DifferentialPilot pilote;
	private UltrasonicSensor sonar;
	private TouchSensor button;

	public Part1(DifferentialPilot pilote, UltrasonicSensor sonar, TouchSensor button) {
		super();
		this.pilote = pilote;
		this.sonar = sonar;
		this.button = button;
	}

	public boolean takeControl() {
		if (MartinController.Part1IsDone) {
			return false;
		}
		return true;
	}

	public void action() {
		suppressed = false; // remise a zero
		pilote.setTravelSpeed(16.0);
		pilote.setRotateSpeed(60.0);
		// en attendant
		//pilote.travel(10);
		pilote.arc(10.0, 90.0, true); // entame un virage arriere (sans attendre la fin)
		while (!suppressed && pilote.isMoving()) { // tant que {suppress} n'a pas ete appele
													// et que le virage n'est pas termine...
			Thread.yield(); // ... liberer le processeur
		} // sortie de boucle: {suppress} a ete appele ou le virage est termine
		pilote.stop(); // arreter le robot
		if (sonar.getDistance() <= 35) {
			pilote.backward(); // marche arriere
			while (!suppressed && sonar.getDistance() < 35) {
				Thread.yield();// libere le processeur
			}
		} else {
			pilote.forward(); // marche arriere
			while (!suppressed && sonar.getDistance() < 35) {
				Thread.yield();// libere le processeur
			}
		}
		pilote.stop();

		pilote.arc(0.0, -90.0, true);
		while (!suppressed && pilote.isMoving()) { // tant que {suppress} n'a pas ete appele
			// et que le virage n'est pas termine...
			Thread.yield(); // ... liberer le processeur
		} // sortie de boucle: {suppress} a ete appele ou le virage est termine
		pilote.stop(); // arreter le robot
		pilote.backward(); // marche arriere
		while (!suppressed && !button.isPressed()) {
			Thread.yield();// libere le processeur
		}
		pilote.stop(); // arrete le robot
		MartinController.Part1IsDone = true;
	}

	/**
	 * @pre --
	 * @post {suppressed == true}, ce qui provoque l'arret de {action}.
	 */
	public void suppress() {
		suppressed = true;
	}

}
