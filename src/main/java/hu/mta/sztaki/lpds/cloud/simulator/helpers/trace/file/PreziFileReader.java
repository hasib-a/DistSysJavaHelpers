package hu.mta.sztaki.lpds.cloud.simulator.helpers.trace.file;

import java.lang.reflect.InvocationTargetException;

import hu.mta.sztaki.lpds.cloud.simulator.helpers.job.Job;

public class PreziFileReader extends TraceFileReaderFoundation {

	public PreziFileReader(String fileName, int from, int to, boolean allowReadingFurther,
			Class<? extends Job> jobType) throws SecurityException, NoSuchMethodException {
		super("LOG: FORMAT", fileName, from, to, allowReadingFurther, jobType);
		// TODO Auto-generated constructor stub
	}

	protected boolean isTraceLine(String line) {
		// TODO Auto-generated method stub
		// split line of every space char
		String[] lineArr = line.split(" ");
		
		try {
			Integer.parseInt(lineArr[0]); // parse 0th element as int
			Float.parseFloat(lineArr[1]); // parse 1st element as float
		} catch (Exception e) {
			System.out.println("Exception catched: Unable to find trace line. Please inspect with debugger");
			return false;
		}
		
		if(lineArr[2].equals(null)){
			return false;
		}
		
		if(lineArr[3].equals(null)){
			return false;
		}		
		return true; //force to return true, allows program to continue. useful for debugging
	}

	protected Job createJobFromLine(String line)
			throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		try {
			String[] jobArr = line.split(" "); // split line on empty char to populate array
			String Id = jobArr[2];
			long submit = Integer.parseInt(jobArr[0]);
			long queuetimeSecs = 1;
			float jobDuration = Float.parseFloat(jobArr[1]);
			
			long ppMem = 512;
			String user = null;
			String group = null;
			String executable = jobArr[3];
			String preceeding = null;
			long delayAfter = 0;
			int nprocs =1;
			
			// TODO: check if user, group and preceeding are being filled out
			return jobCreator.newInstance(Id, submit , queuetimeSecs, (long) jobDuration, nprocs, -1, ppMem, user, group, executable, preceeding, delayAfter);
		} catch (ArrayIndexOutOfBoundsException ex1) {
			System.out.println("Incomplete line, ignoring to allow program to continue. Please inspect with debugger");
			return null;
		}
	}
	
	@Override
	protected void metaDataCollector(String line) {
		// TODO Auto-generated method stub
		
	}
	
}