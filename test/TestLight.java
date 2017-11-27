import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.LCD;

public class TestLight {
	public static void main(String[] args) {
		LightSensor sensor=new LightSensor(SensorPort.S3);
		 LCD.drawInt(sensor.readValue(),0,5);
		 LCD.drawInt(10,0,0);
}	
}