package io.highway.api;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

import io.highway.api.Vehicle;

public class HighwayTrafficSimulator {

	private static DecimalFormat plateFormatNumber = new DecimalFormat("0000");
	private static String[] STATE = { "TN", "AP", "KL", "KA", "AB", "NM", "AR" };
	
	private static String[] TYPES = { "CAR", "MINI-BUS", "CAMPER-VAN", "TRUCK", "CAR" };

	private static String[] COLORS = { "Blue", "White", "Silver", "Black", "Metalic Green", "Orange", "Yellow" };

	public static Vehicle getVehicle() {

		String state = STATE[ThreadLocalRandom.current().nextInt(0, 6)];
		StringBuffer sb = new StringBuffer(state);
		sb.append("-");
		sb.append(plateFormatNumber.format(ThreadLocalRandom.current().nextInt(0, 9999)));
		String carPlateNumber = sb.toString();

		Integer speed = ThreadLocalRandom.current().nextInt(80, 200);

		String color = COLORS[ThreadLocalRandom.current().nextInt(0, 6)];
		String type = TYPES[ThreadLocalRandom.current().nextInt(0, 4)];

		return new Vehicle(carPlateNumber, speed, color,type);

	}

}
