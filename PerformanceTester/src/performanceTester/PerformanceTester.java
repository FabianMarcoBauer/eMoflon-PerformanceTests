package performanceTester;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import performanceTester.factories.PerformanceTgg;

public class PerformanceTester {

	private static final String dataLocation = "performance/data/allTestDataPoints.ser";

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		int factoryArgumentIndex = -1;
		if (args.length < 5) {
			System.err.println(
					"Invalid arguments. Expected: <factory> <mode> <modelsize> <deltasize> <repetitions> [factoryArgument]");
			return;
		}
		if (args.length > 5) {
			factoryArgumentIndex = 5;
		}
		PerformanceTgg<?, ?, ?> tgg;
		Class<?> factoryClass = Class.forName(args[0]);
		if (factoryArgumentIndex > 0) {
			try {
				tgg = (PerformanceTgg<?, ?, ?>) factoryClass.getConstructor(String.class)
						.newInstance(args[factoryArgumentIndex]);
			} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
				tgg = (PerformanceTgg<?, ?, ?>) factoryClass.newInstance();
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			}
		} else {
			tgg = (PerformanceTgg<?, ?, ?>) factoryClass.newInstance();
		}

		new PerformanceTester(tgg).execute(Operationalization.valueOf(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
	}

	private PerformanceTgg<?, ?, ?> performanceTgg;
	private List<TestDataPoint> data;

	public PerformanceTester(PerformanceTgg<?, ?, ?> performanceTgg) {
		this.performanceTgg = performanceTgg;
	}

	public void execute(Operationalization mode, int modelsize, int deltasize, int repetitions) {
		loadData();

		data.addAll(performanceTgg.execute(mode, modelsize, deltasize, repetitions));

		saveData();
	}

	@SuppressWarnings("unchecked")
	private List<TestDataPoint> loadData() {
		try {
			FileInputStream file = new FileInputStream(dataLocation);
			ObjectInputStream in = new ObjectInputStream(file);
			data = (List<TestDataPoint>) in.readObject();
			in.close();
			file.close();
		} catch (IOException e) {
			data = new ArrayList<>();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return data;
	}

	private void saveData() {
		try {
			FileOutputStream file = new FileOutputStream(dataLocation);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(data);
			out.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<TestDataPoint> getData() {
		if (data == null)
			loadData();
		
		return data;
	}

}
