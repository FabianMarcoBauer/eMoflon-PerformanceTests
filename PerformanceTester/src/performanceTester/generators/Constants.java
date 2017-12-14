package performanceTester.generators;

import java.util.concurrent.TimeUnit;

public class Constants {
	public final static String blockCodeAdapter = "BlockCodeAdapter";
	public final static String blockDiagramCodeAdapter = "BlockDiagramCodeAdapter";
	public final static String classInhHier2DB = "ClassInhHier2DB";
	public final static String companyToIT = "CompanyToIT";
	public final static String familiesToPersons_V0 = "FamiliesToPersons_V0";
	public final static String familiesToPersons_V1 = "FamiliesToPersons_V1";
	public final static String moDiscoTGG = "MoDiscoTGG";
	public final static String processCodeAdapter = "ProcessCodeAdapter";
	public final static String vhdlTGGCodeAdapter = "VHDLTGGCodeAdapter";
	
	public final static String[] testProjects = {
//			blockCodeAdapter,
//			blockDiagramCodeAdapter,
//			classInhHier2DB,
//			companyToIT,
//			familiesToPersons_V0,
//			familiesToPersons_V1,
			moDiscoTGG,
//			processCodeAdapter,
//			vhdlTGGCodeAdapter
	};
	
	/** The timeout after which a performance test is aborted. */
	public final static int timeout = 30; // in seconds
	
	/** Defines how much memory may be allocated for the heap by the launched JavaVM. */
	public final static String maxMemorySize = "1024M"; //"M" for megabyte, "G" for gigabyte
	
	/** Model sizes for which test cases shall be generated and executed. */
	public final static int[] modelSizes = {
//			10, 
//			50,
			100, 
//			500, 
//			1000, 
//			5000,
//			10000, 
//			50000,
//			100000, 
//			500000,
//			1000000
	};
	
	public final static int deltaSize = 10;

	/** Number of repetitions per test case. Final result is the median of all repetitions. */
	public final static int repetitions = 3;

	//-------------------------  Plots -----------------------------------------------------
	
	/** 
	 * The file format in which the plot should be generated.
	 * Among possible options are "gif" for a .gif file and "pdf" for a .pdf file.
	 * */
	public static final String outputstyle = "pdf";
	
	/** The time unit used in the plots. */
	public static final TimeUnit plotTimeUnit = TimeUnit.MILLISECONDS;

	/**
	 * The model size used for most plots with a fixed size. Should be low enough that most
	 * TGGs/Operationalizations do not timeout/run out of memory for this size.
	 * */
	public static final int standardModelSize = 50;
	
	/**
	 * The model size used for some plots with a fixed size, where the TGG/Operationalization
	 * can handle bigger sizes.
	 * */
	public static final int bigModelSize = 1000;
}
