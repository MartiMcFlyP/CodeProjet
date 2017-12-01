import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class TestLight {
	public static void main(String[] args) throws Exception {
		LightSensor lightSensor = new LightSensor(SensorPort.S3);
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S1);
		TouchSensor bouton = new TouchSensor(SensorPort.S2);

		 while (true) {
		 LCD.drawInt(lightSensor.getLightValue(), 4, 0, 0);
		 LCD.drawInt(lightSensor.getNormalizedLightValue(), 4, 0, 1);
		 LCD.drawInt(lightSensor.getFloodlight(), 4, 0, 5);
		 LCD.drawInt(SensorPort.S3.readRawValue(), 4, 0, 2);
		 LCD.drawInt(lightSensor.readValue(), 4, 0, 3);
		 LCD.drawInt((int) sonar.getDistance(), 4, 0, 6);
		 }

//		DifferentialPilot pilote = new DifferentialPilot(MartinController.DIAM_ROUE, MartinController.DIST_ROUE,
//				Motor.C, Motor.A);
		// pilote.setRotateSpeed(3600);
		// while (true) {
		// pilote.arc(0, 180, true);
		// while (!bouton.isPressed()) {
		// Thread.yield();
		// }
		// pilote.stop();
		// }

//		pilote.setRotateSpeed(30);
//		pilote.setTravelSpeed(2);
//		boolean gauche = true;
//		boolean lineFound = false;
//		
//		if (lightSensor.getLightValue() <= 36) {
//			//90
//			lineFound = true;
//		} else {
//			pilote.rotate(40);
//			while (lightSensor.getLightValue() > 36 && pilote.isMoving()) {
//				Thread.yield();
//			}
//			pilote.stop();
//			if (lightSensor.getLightValue() <= 36) {
//				lineFound = true;
//			} else {
//				gauche = false;
//				pilote.rotate(-80);
//				while (lightSensor.getLightValue() > 36 && pilote.isMoving()) {
//					Thread.yield();
//				}
//				pilote.stop();
//				if (lightSensor.getLightValue() <= 36) {
//					lineFound = true;
//				} else {
//					//line not found ??? we are lost
//				}
//			}	
//		}
		
	}
}
