//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteNetwork.java                  ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================

import java.util.*;
import ReteObjects.*;
import java.io.*;
//import WorkingMemory.*;
//import WMServer.*;
import MemoryServer.*;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableServer.POA;


//import org.omg.CORBA.ORB;
//import org.omg.CORBA.Object;
//import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.*;
//import org.omg.PortableServer.POA;


public class ReteNetwork
{
	private ArrayList ListOfObjects = new ArrayList();
	private ArrayList ListOfNames = new ArrayList();

	private ReteExecute executions;
	private int time_stamp = 0;
	private WMGetSetObject wmserverinterface = null;
	private org.omg.CORBA.Any anyholder;

	private Expert expert = null;
	public boolean stop = false;



//----------------- Rule nodes -----------------
		ReteNodeFinal Rule1_ProblemDeadBattery1 = new ReteNodeFinal("Rule1_ProblemDeadBattery1", -9, 0);
		ReteNodeFinal Rule2_ProblemDeadBattery2 = new ReteNodeFinal("Rule2_ProblemDeadBattery2", -11, 0);
		ReteNodeFinal Rule3_ProblemBadIgnition = new ReteNodeFinal("Rule3_ProblemBadIgnition", -6, 0);
		ReteNodeFinal Rule4_ProblemFuelSystem = new ReteNodeFinal("Rule4_ProblemFuelSystem", -5, 0);
		ReteNodeFinal Rule5_ProblemNoGas = new ReteNodeFinal("Rule5_ProblemNoGas", -8, 0);
		ReteNodeFinal Rule6_ProblemBadStarter = new ReteNodeFinal("Rule6_ProblemBadStarter", -7, 0);
		ReteNodeFinal Rule7_ProblemFloodedEng = new ReteNodeFinal("Rule7_ProblemFloodedEng", -4, 0);
		ReteNodeFinal Rule8_ProblemFuelIgn = new ReteNodeFinal("Rule8_ProblemFuelIgn", -3, 0);
		ReteNodeFinal Rule9_ProblemStartSystem = new ReteNodeFinal("Rule9_ProblemStartSystem", -2, 0);
		ReteNodeFinal Rule10_Start1 = new ReteNodeFinal("Rule10_Start1", -12, 0);
		ReteNodeFinal Rule11_Start = new ReteNodeFinal("Rule11_Start", -1, 0);
		ReteNodeFinal Rule12_FinalDiagnostic = new ReteNodeFinal("Rule12_FinalDiagnostic", -10, 0);

//----------------- Condition nodes -----------------
		ReteNodeCondition Condition1 = new ReteNodeCondition("C1: car.problem == \"unknown\"");
		ReteNodeCondition Condition2 = new ReteNodeCondition("C2: car.init_problem == \"starting_system\"");
		ReteNodeCondition Condition3 = new ReteNodeCondition("C3: car.headlights == \"dim\"");
		ReteNodeCondition Condition4 = new ReteNodeCondition("C4: car.headlights == \"dead\"");
		ReteNodeCondition Condition5 = new ReteNodeCondition("C5: car.init_problem == \"fuel_or_ignition\"");
		ReteNodeCondition Condition6 = new ReteNodeCondition("C6: car.headlights == \"working\"");
		ReteNodeCondition Condition7 = new ReteNodeCondition("C7: car.spark_plug_spark == \"none\"");
		ReteNodeCondition Condition8 = new ReteNodeCondition("C8: car.fuel_gauge_reading == \"full\"");
		ReteNodeCondition Condition9 = new ReteNodeCondition("C9: car.carburetor_gas == \"yes\"");
		ReteNodeCondition Condition10 = new ReteNodeCondition("C10: car.fuel_gauge_reading == \"empty\"");
		ReteNodeCondition Condition11 = new ReteNodeCondition("C11: car.spark_plug_spark == \"exists\"");
		ReteNodeCondition Condition12 = new ReteNodeCondition("C12: car.init_problem == \"unknown\"");
		ReteNodeCondition Condition13 = new ReteNodeCondition("C13: car.ignition_key == \"on\"");
		ReteNodeCondition Condition14 = new ReteNodeCondition("C14: car.engine_turning_over == \"yes\"");
		ReteNodeCondition Condition15 = new ReteNodeCondition("C15: car.engine_turning_over == \"no\"");
		ReteNodeCondition Condition16 = new ReteNodeCondition("C16: COSMOS_START.init_status == 1");
		ReteNodeCondition Condition17 = new ReteNodeCondition("C17: car.vehicle_make ==  $11$mak ");
		ReteNodeCondition Condition18 = new ReteNodeCondition("C18: car.ignition_key != \"on\"");
		ReteNodeCondition Condition19 = new ReteNodeCondition("C19: mechanic.first_name ==  $11$name ");
		ReteNodeCondition Condition20 = new ReteNodeCondition("C20: car.problem != \"unknown\"");
		ReteNodeCondition Condition21 = new ReteNodeCondition("C21: car.problem ==  $12$problem ");

//----------------- Logic nodes -----------------
		ReteNodeAND And1 = new ReteNodeAND("And1");
		ReteNodeAND And2 = new ReteNodeAND("And2");
		ReteNodeAND And3 = new ReteNodeAND("And3");
		ReteNodeAND And4 = new ReteNodeAND("And4");
		ReteNodeAND And5 = new ReteNodeAND("And5");
		ReteNodeAND And6 = new ReteNodeAND("And6");
		ReteNodeAND And7 = new ReteNodeAND("And7");
		ReteNodeAND And8 = new ReteNodeAND("And8");
		ReteNodeAND And9 = new ReteNodeAND("And9");
		ReteNodeAND And10 = new ReteNodeAND("And10");
		ReteNodeAND And11 = new ReteNodeAND("And11");
		ReteNodeAND And12 = new ReteNodeAND("And12");
		ReteNodeAND And13 = new ReteNodeAND("And13");
		ReteNodeAND And14 = new ReteNodeAND("And14");
		ReteNodeAND And15 = new ReteNodeAND("And15");
		ReteNodeAND And16 = new ReteNodeAND("And16");
		ReteNodeAND And17 = new ReteNodeAND("And17");
		ReteNodeAND And18 = new ReteNodeAND("And18");
		ReteNodeAND And19 = new ReteNodeAND("And19");
		ReteNodeAND And20 = new ReteNodeAND("And20");
		ReteNodeAND And21 = new ReteNodeAND("And21");
		ReteNodeAND And22 = new ReteNodeAND("And22");
		ReteNodeAND And23 = new ReteNodeAND("And23");
		ReteNodeAND And24 = new ReteNodeAND("And24");
		ReteNodeAND And25 = new ReteNodeAND("And25");
		ReteNodeAND And26 = new ReteNodeAND("And26");

	public String listOfMemoryObjects()
	{
		StringBuffer sb = new StringBuffer();
			for(int i = 0; i < ListOfNames.size(); i++) sb.append("\t" + ListOfNames.get(i) + " = " + ListOfObjects.get(i) + "\n");
		return sb.toString();
	}

	public static void main(String args[]) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		ReteNetwork rn= new ReteNetwork();
		rn.expert = null;
		rn.CreateNetwork();
		rn.wmserverinterface = null;
		System.out.println("Inferencing with transient memory.");

		rn.StartInferencing();
	}

	public static void WinMain(Expert expert) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		ReteNetwork rn= new ReteNetwork();
		rn.expert = expert;
		if(expert!=null) expert.rn = rn;
		rn.CreateNetwork();
		rn.wmserverinterface = null;
		if(expert != null) expert.PrintMessage("Inferencing with transient memory.");

		if(expert != null) expert.Certainty =  5.0;
		rn.StartInferencing();
	}

	public void Stop()
	{
		stop = true;
		Thread.yield();
		Thread.yield();
	}
	public void SetAllTransparent()
	{

		Condition1.SetTransparent();
		Condition2.SetTransparent();
		Condition3.SetTransparent();
		Condition4.SetTransparent();
		Condition5.SetTransparent();
		Condition6.SetTransparent();
		Condition7.SetTransparent();
		Condition8.SetTransparent();
		Condition9.SetTransparent();
		Condition10.SetTransparent();
		Condition11.SetTransparent();
		Condition12.SetTransparent();
		Condition13.SetTransparent();
		Condition14.SetTransparent();
		Condition15.SetTransparent();
		Condition16.SetTransparent();
		Condition17.SetTransparent();
		Condition18.SetTransparent();
		Condition19.SetTransparent();
		Condition20.SetTransparent();
		Condition21.SetTransparent();
	}
	
	public void ConnectMemory(String args[]) throws org.omg.CORBA.TypeCodePackage.BadKind
	{
		try
		{
			if(expert != null) expert.PrintMessage("Initializing ORB...");
			else System.out.println("Initializing ORB...");
		// create and initialize the ORB
			ORB orb = ORB.init(args, null);
			anyholder = orb.create_any();
			// get the root naming context
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

			// Use NamingContextExt instead of NamingContext. This is 
			// part of the Interoperable Naming Service.	
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			if(expert != null) expert.PrintMessage("Connecting to memory server...");
			else System.out.println("Connecting to memory server...");
			// resolve the Object Reference in Naming
			String name = "WMGetSetObject";
			wmserverinterface = WMGetSetObjectHelper.narrow(ncRef.resolve_str(name));
			StringBuffer sb = new StringBuffer();
			for(int ar = 0; ar < args.length; ar++) sb.append(args[ar] + " ");
			if(wmserverinterface == null) 
			{
				if(expert != null) expert.PrintMessage("No memory server available. Inferencing with transient memory.");
				else System.out.println("No memory server available. Inferencing with transient memory.");
			}
			else 
			{
				if(expert != null) expert.PrintMessage("Inferencing with persistent memory - " + sb.toString());
				else System.out.println("Inferencing with persistent memory - " + sb.toString());
			}
		} 
		catch ( Exception e ) 
		{
			if(expert != null) expert.PrintMessage("Exception connecting to memory server..." + e);
			else
			{
				System.err.println( "Exception connecting to memory server..." + e );
				e.printStackTrace( );
			}
		}
	}

	public String TextList()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\tObjects list:\n");
		for(int i = 0; i < ListOfObjects.size(); i++) sb.append("\t\t" + ListOfObjects.get(i) + " : " + ListOfNames.get(i) + "\n");
		return sb.toString();
	}

// Returns 1, 2 or 3 if variable is of type 
// String, Integer or Double respectively; 0 otherwise.
	public static int TypeOfObject(Object x)
	{
		String xx = x.getClass().getName();
		if(xx.compareTo("java.lang.String")==0) return 1;
		if(xx.compareTo("java.lang.Integer")==0) return 2;
		if(xx.compareTo("java.lang.Double")==0) return 3;
		return 0;
	}
	public static int TypeOfObject(String x){return 1;}
	public static int TypeOfObject(Integer x){return 2;}
	public static int TypeOfObject(Double x){return 3;}
	public static int TypeOfObject(int x){return 2;}
	public static int TypeOfObject(double x){return 3;}

	public static String ReturnClass(String x){return x;}
	public static Double ReturnClass(double x){return new Double(x);}
	public static Integer ReturnClass(int x){return new Integer(x);}
	public static Double ReturnClass(Double x){return x;}
	public static Integer ReturnClass(Integer x){return x;}


	public boolean FeedNetwork(Object obj, int time_stamp)
	{
		ReteToken token;

		if(obj.getClass().getName().compareTo("ReteObjects.car")==0)
		{
//	car:problem:==:"unknown"	1:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_problem;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).problem) == 1)
			{
				if(((car)obj).problem != null)
				if(((car)obj).problem.compareTo("unknown")==0)
					Condition1.InputCondition(token);
			}
//	car:init_problem:==:"starting_system"	2:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_init_problem;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).init_problem) == 1)
			{
				if(((car)obj).init_problem != null)
				if(((car)obj).init_problem.compareTo("starting_system")==0)
					Condition2.InputCondition(token);
			}
//	car:headlights:==:"dim"	3:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_headlights;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).headlights) == 1)
			{
				if(((car)obj).headlights != null)
				if(((car)obj).headlights.compareTo("dim")==0)
					Condition3.InputCondition(token);
			}
//	car:headlights:==:"dead"	4:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_headlights;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).headlights) == 1)
			{
				if(((car)obj).headlights != null)
				if(((car)obj).headlights.compareTo("dead")==0)
					Condition4.InputCondition(token);
			}
//	car:init_problem:==:"fuel_or_ignition"	5:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_init_problem;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).init_problem) == 1)
			{
				if(((car)obj).init_problem != null)
				if(((car)obj).init_problem.compareTo("fuel_or_ignition")==0)
					Condition5.InputCondition(token);
			}
//	car:headlights:==:"working"	6:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_headlights;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).headlights) == 1)
			{
				if(((car)obj).headlights != null)
				if(((car)obj).headlights.compareTo("working")==0)
					Condition6.InputCondition(token);
			}
//	car:spark_plug_spark:==:"none"	7:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_spark_plug_spark;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).spark_plug_spark) == 1)
			{
				if(((car)obj).spark_plug_spark != null)
				if(((car)obj).spark_plug_spark.compareTo("none")==0)
					Condition7.InputCondition(token);
			}
//	car:fuel_gauge_reading:==:"full"	8:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_fuel_gauge_reading;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).fuel_gauge_reading) == 1)
			{
				if(((car)obj).fuel_gauge_reading != null)
				if(((car)obj).fuel_gauge_reading.compareTo("full")==0)
					Condition8.InputCondition(token);
			}
//	car:carburetor_gas:==:"yes"	9:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_carburetor_gas;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).carburetor_gas) == 1)
			{
				if(((car)obj).carburetor_gas != null)
				if(((car)obj).carburetor_gas.compareTo("yes")==0)
					Condition9.InputCondition(token);
			}
//	car:fuel_gauge_reading:==:"empty"	10:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_fuel_gauge_reading;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).fuel_gauge_reading) == 1)
			{
				if(((car)obj).fuel_gauge_reading != null)
				if(((car)obj).fuel_gauge_reading.compareTo("empty")==0)
					Condition10.InputCondition(token);
			}
//	car:spark_plug_spark:==:"exists"	11:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_spark_plug_spark;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).spark_plug_spark) == 1)
			{
				if(((car)obj).spark_plug_spark != null)
				if(((car)obj).spark_plug_spark.compareTo("exists")==0)
					Condition11.InputCondition(token);
			}
//	car:init_problem:==:"unknown"	12:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_init_problem;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).init_problem) == 1)
			{
				if(((car)obj).init_problem != null)
				if(((car)obj).init_problem.compareTo("unknown")==0)
					Condition12.InputCondition(token);
			}
//	car:ignition_key:==:"on"	13:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_ignition_key;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).ignition_key) == 1)
			{
				if(((car)obj).ignition_key != null)
				if(((car)obj).ignition_key.compareTo("on")==0)
					Condition13.InputCondition(token);
			}
//	car:engine_turning_over:==:"yes"	14:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_engine_turning_over;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).engine_turning_over) == 1)
			{
				if(((car)obj).engine_turning_over != null)
				if(((car)obj).engine_turning_over.compareTo("yes")==0)
					Condition14.InputCondition(token);
			}
//	car:engine_turning_over:==:"no"	15:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_engine_turning_over;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).engine_turning_over) == 1)
			{
				if(((car)obj).engine_turning_over != null)
				if(((car)obj).engine_turning_over.compareTo("no")==0)
					Condition15.InputCondition(token);
			}
//	car:vehicle_make:==: $11$mak 	17:0:1:8
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_vehicle_make;
			token.LSp_from_P_H_Ep();
			token.variable[0] = ReturnClass(((car)obj).vehicle_make);
			token.typeofvariable[0] = TypeOfObject(((car)obj).vehicle_make);
			Condition17.InputCondition(token);

//	car:ignition_key:!=:"on"	18:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_ignition_key;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).ignition_key) == 1)
			{
				if(((car)obj).ignition_key != null)
				if(((car)obj).ignition_key.compareTo("on")!=0)
					Condition18.InputCondition(token);
			}
//	car:problem:!=:"unknown"	20:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_problem;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((car)obj).problem) == 1)
			{
				if(((car)obj).problem != null)
				if(((car)obj).problem.compareTo("unknown")!=0)
					Condition20.InputCondition(token);
			}
//	car:problem:==: $12$problem 	21:0:3:8
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((car)obj)._P_H_Ep_problem;
			token.LSp_from_P_H_Ep();
			token.variable[2] = ReturnClass(((car)obj).problem);
			token.typeofvariable[2] = TypeOfObject(((car)obj).problem);
			Condition21.InputCondition(token);

		}
		else if(obj.getClass().getName().compareTo("ReteObjects.COSMOS_START")==0)
		{
//	COSMOS_START:init_status:==:1	16:0:0:1
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.P_H_Ep = ((COSMOS_START)obj)._P_H_Ep_init_status;
			token.LSp_from_P_H_Ep();
			if(TypeOfObject(((COSMOS_START)obj).init_status) == 2 || TypeOfObject(((COSMOS_START)obj).init_status) == 3)
			{
				if(((COSMOS_START)obj).init_status == 1)
					Condition16.InputCondition(token);
			}

		}
		else if(obj.getClass().getName().compareTo("ReteObjects.mechanic")==0)
		{
//	mechanic:first_name:==: $11$name 	19:1:2:8
			token = new ReteToken();
			token.ClearToken();
			token.object[1] = obj;
			token.timestamps[1] = time_stamp;
			token.P_H_Ep = ((mechanic)obj)._P_H_Ep_first_name;
			token.LSp_from_P_H_Ep();
			token.variable[1] = ReturnClass(((mechanic)obj).first_name);
			token.typeofvariable[1] = TypeOfObject(((mechanic)obj).first_name);
			Condition19.InputCondition(token);

		}
		return false;
	}

	public void UnFeedNetwork(Object obj)
	{
		ReteConflictSet.RemoveTokensWithObsoleteObjects(obj);

		if(obj.getClass().getName().compareTo("ReteObjects.car")==0)
		{
			Condition1.RemoveObsoleteTokens(obj);
			Condition2.RemoveObsoleteTokens(obj);
			Condition3.RemoveObsoleteTokens(obj);
			Condition4.RemoveObsoleteTokens(obj);
			Condition5.RemoveObsoleteTokens(obj);
			Condition6.RemoveObsoleteTokens(obj);
			Condition7.RemoveObsoleteTokens(obj);
			Condition8.RemoveObsoleteTokens(obj);
			Condition9.RemoveObsoleteTokens(obj);
			Condition10.RemoveObsoleteTokens(obj);
			Condition11.RemoveObsoleteTokens(obj);
			Condition12.RemoveObsoleteTokens(obj);
			Condition13.RemoveObsoleteTokens(obj);
			Condition14.RemoveObsoleteTokens(obj);
			Condition15.RemoveObsoleteTokens(obj);
			Condition17.RemoveObsoleteTokens(obj);
			Condition18.RemoveObsoleteTokens(obj);
			Condition20.RemoveObsoleteTokens(obj);
			Condition21.RemoveObsoleteTokens(obj);
		}
		else if(obj.getClass().getName().compareTo("ReteObjects.COSMOS_START")==0)
		{
			Condition16.RemoveObsoleteTokens(obj);
		}
		else if(obj.getClass().getName().compareTo("ReteObjects.mechanic")==0)
		{
			Condition19.RemoveObsoleteTokens(obj);
		}
	}

	public int CreateNetwork()
	{


//----------------- Connections -----------------
		Rule1_ProblemDeadBattery1.SetInputNode( And2 );
		And2.SetOutputNode( Rule1_ProblemDeadBattery1 );
		Rule2_ProblemDeadBattery2.SetInputNode( And3 );
		And3.SetOutputNode( Rule2_ProblemDeadBattery2 );
		Rule3_ProblemBadIgnition.SetInputNode( And6 );
		And6.SetOutputNode( Rule3_ProblemBadIgnition );
		Rule4_ProblemFuelSystem.SetInputNode( And9 );
		And9.SetOutputNode( Rule4_ProblemFuelSystem );
		Rule5_ProblemNoGas.SetInputNode( And11 );
		And11.SetOutputNode( Rule5_ProblemNoGas );
		Rule6_ProblemBadStarter.SetInputNode( And13 );
		And13.SetOutputNode( Rule6_ProblemBadStarter );
		Rule7_ProblemFloodedEng.SetInputNode( And16 );
		And16.SetOutputNode( Rule7_ProblemFloodedEng );
		Rule8_ProblemFuelIgn.SetInputNode( And19 );
		And19.SetOutputNode( Rule8_ProblemFuelIgn );
		Rule9_ProblemStartSystem.SetInputNode( And22 );
		And22.SetOutputNode( Rule9_ProblemStartSystem );
		Rule10_Start1.SetInputNode( Condition16 );
		Condition16.SetOutputNode( Rule10_Start1 );
		Rule11_Start.SetInputNode( And25 );
		And25.SetOutputNode( Rule11_Start );
		Rule12_FinalDiagnostic.SetInputNode( And26 );
		And26.SetOutputNode( Rule12_FinalDiagnostic );

		And1.SetInputNode( Condition2 );
		Condition2.SetOutputNode( And1 );
		And1.SetInputNode( Condition3 );
		Condition3.SetOutputNode( And1 );
		And2.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And2 );
		And2.SetInputNode( And1 );
		And1.SetOutputNode( And2 );
		And3.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And3 );
		And3.SetInputNode( Condition4 );
		Condition4.SetOutputNode( And3 );
		And4.SetInputNode( Condition6 );
		Condition6.SetOutputNode( And4 );
		And4.SetInputNode( Condition7 );
		Condition7.SetOutputNode( And4 );
		And5.SetInputNode( Condition5 );
		Condition5.SetOutputNode( And5 );
		And5.SetInputNode( And4 );
		And4.SetOutputNode( And5 );
		And6.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And6 );
		And6.SetInputNode( And5 );
		And5.SetOutputNode( And6 );
		And7.SetInputNode( Condition8 );
		Condition8.SetOutputNode( And7 );
		And7.SetInputNode( Condition9 );
		Condition9.SetOutputNode( And7 );
		And8.SetInputNode( Condition5 );
		Condition5.SetOutputNode( And8 );
		And8.SetInputNode( And7 );
		And7.SetOutputNode( And8 );
		And9.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And9 );
		And9.SetInputNode( And8 );
		And8.SetOutputNode( And9 );
		And10.SetInputNode( Condition5 );
		Condition5.SetOutputNode( And10 );
		And10.SetInputNode( Condition10 );
		Condition10.SetOutputNode( And10 );
		And11.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And11 );
		And11.SetInputNode( And10 );
		And10.SetOutputNode( And11 );
		And12.SetInputNode( Condition2 );
		Condition2.SetOutputNode( And12 );
		And12.SetInputNode( Condition6 );
		Condition6.SetOutputNode( And12 );
		And13.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And13 );
		And13.SetInputNode( And12 );
		And12.SetOutputNode( And13 );
		And14.SetInputNode( Condition9 );
		Condition9.SetOutputNode( And14 );
		And14.SetInputNode( Condition11 );
		Condition11.SetOutputNode( And14 );
		And15.SetInputNode( Condition5 );
		Condition5.SetOutputNode( And15 );
		And15.SetInputNode( And14 );
		And14.SetOutputNode( And15 );
		And16.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And16 );
		And16.SetInputNode( And15 );
		And15.SetOutputNode( And16 );
		And17.SetInputNode( Condition13 );
		Condition13.SetOutputNode( And17 );
		And17.SetInputNode( Condition14 );
		Condition14.SetOutputNode( And17 );
		And18.SetInputNode( Condition12 );
		Condition12.SetOutputNode( And18 );
		And18.SetInputNode( And17 );
		And17.SetOutputNode( And18 );
		And19.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And19 );
		And19.SetInputNode( And18 );
		And18.SetOutputNode( And19 );
		And20.SetInputNode( Condition13 );
		Condition13.SetOutputNode( And20 );
		And20.SetInputNode( Condition15 );
		Condition15.SetOutputNode( And20 );
		And21.SetInputNode( Condition12 );
		Condition12.SetOutputNode( And21 );
		And21.SetInputNode( And20 );
		And20.SetOutputNode( And21 );
		And22.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And22 );
		And22.SetInputNode( And21 );
		And21.SetOutputNode( And22 );
		And23.SetInputNode( Condition17 );
		Condition17.SetOutputNode( And23 );
		And23.SetInputNode( Condition18 );
		Condition18.SetOutputNode( And23 );
		And24.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And24 );
		And24.SetInputNode( And23 );
		And23.SetOutputNode( And24 );
		And25.SetInputNode( And24 );
		And24.SetOutputNode( And25 );
		And25.SetInputNode( Condition19 );
		Condition19.SetOutputNode( And25 );
		And26.SetInputNode( Condition20 );
		Condition20.SetOutputNode( And26 );
		And26.SetInputNode( Condition21 );
		Condition21.SetOutputNode( And26 );
		return 0;
	}

	public void BuildTree(javax.swing.JTree tree)
	{
		javax.swing.tree.DefaultTreeModel model = (javax.swing.tree.DefaultTreeModel)tree.getModel();
		javax.swing.tree.DefaultMutableTreeNode treeNode_0 = new javax.swing.tree.DefaultMutableTreeNode("Conflict Set");
		model.setRoot(treeNode_0);

		javax.swing.tree.DefaultMutableTreeNode treeNode_1 = new javax.swing.tree.DefaultMutableTreeNode(Rule12_FinalDiagnostic);
		treeNode_0.add(treeNode_1);
		javax.swing.tree.DefaultMutableTreeNode treeNode_2 = new javax.swing.tree.DefaultMutableTreeNode(And26);
		treeNode_1.add(treeNode_2);
			javax.swing.tree.DefaultMutableTreeNode treeNode_3 = new javax.swing.tree.DefaultMutableTreeNode(Condition20);
			treeNode_2.add(treeNode_3);
			javax.swing.tree.DefaultMutableTreeNode treeNode_4 = new javax.swing.tree.DefaultMutableTreeNode(Condition21);
			treeNode_2.add(treeNode_4);

		javax.swing.tree.DefaultMutableTreeNode treeNode_5 = new javax.swing.tree.DefaultMutableTreeNode(Rule11_Start);
		treeNode_0.add(treeNode_5);
		javax.swing.tree.DefaultMutableTreeNode treeNode_6 = new javax.swing.tree.DefaultMutableTreeNode(And25);
		treeNode_5.add(treeNode_6);
			javax.swing.tree.DefaultMutableTreeNode treeNode_7 = new javax.swing.tree.DefaultMutableTreeNode(And24);
			treeNode_6.add(treeNode_7);
				javax.swing.tree.DefaultMutableTreeNode treeNode_8 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
				treeNode_7.add(treeNode_8);
				javax.swing.tree.DefaultMutableTreeNode treeNode_9 = new javax.swing.tree.DefaultMutableTreeNode(And23);
				treeNode_7.add(treeNode_9);
					javax.swing.tree.DefaultMutableTreeNode treeNode_10 = new javax.swing.tree.DefaultMutableTreeNode(Condition17);
					treeNode_9.add(treeNode_10);
					javax.swing.tree.DefaultMutableTreeNode treeNode_11 = new javax.swing.tree.DefaultMutableTreeNode(Condition18);
					treeNode_9.add(treeNode_11);
			javax.swing.tree.DefaultMutableTreeNode treeNode_12 = new javax.swing.tree.DefaultMutableTreeNode(Condition19);
			treeNode_6.add(treeNode_12);

		javax.swing.tree.DefaultMutableTreeNode treeNode_13 = new javax.swing.tree.DefaultMutableTreeNode(Rule10_Start1);
		treeNode_0.add(treeNode_13);
		javax.swing.tree.DefaultMutableTreeNode treeNode_14 = new javax.swing.tree.DefaultMutableTreeNode(Condition16);
		treeNode_13.add(treeNode_14);

		javax.swing.tree.DefaultMutableTreeNode treeNode_15 = new javax.swing.tree.DefaultMutableTreeNode(Rule9_ProblemStartSystem);
		treeNode_0.add(treeNode_15);
		javax.swing.tree.DefaultMutableTreeNode treeNode_16 = new javax.swing.tree.DefaultMutableTreeNode(And22);
		treeNode_15.add(treeNode_16);
			javax.swing.tree.DefaultMutableTreeNode treeNode_17 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_16.add(treeNode_17);
			javax.swing.tree.DefaultMutableTreeNode treeNode_18 = new javax.swing.tree.DefaultMutableTreeNode(And21);
			treeNode_16.add(treeNode_18);
				javax.swing.tree.DefaultMutableTreeNode treeNode_19 = new javax.swing.tree.DefaultMutableTreeNode(Condition12);
				treeNode_18.add(treeNode_19);
				javax.swing.tree.DefaultMutableTreeNode treeNode_20 = new javax.swing.tree.DefaultMutableTreeNode(And20);
				treeNode_18.add(treeNode_20);
					javax.swing.tree.DefaultMutableTreeNode treeNode_21 = new javax.swing.tree.DefaultMutableTreeNode(Condition13);
					treeNode_20.add(treeNode_21);
					javax.swing.tree.DefaultMutableTreeNode treeNode_22 = new javax.swing.tree.DefaultMutableTreeNode(Condition15);
					treeNode_20.add(treeNode_22);

		javax.swing.tree.DefaultMutableTreeNode treeNode_23 = new javax.swing.tree.DefaultMutableTreeNode(Rule8_ProblemFuelIgn);
		treeNode_0.add(treeNode_23);
		javax.swing.tree.DefaultMutableTreeNode treeNode_24 = new javax.swing.tree.DefaultMutableTreeNode(And19);
		treeNode_23.add(treeNode_24);
			javax.swing.tree.DefaultMutableTreeNode treeNode_25 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_24.add(treeNode_25);
			javax.swing.tree.DefaultMutableTreeNode treeNode_26 = new javax.swing.tree.DefaultMutableTreeNode(And18);
			treeNode_24.add(treeNode_26);
				javax.swing.tree.DefaultMutableTreeNode treeNode_27 = new javax.swing.tree.DefaultMutableTreeNode(Condition12);
				treeNode_26.add(treeNode_27);
				javax.swing.tree.DefaultMutableTreeNode treeNode_28 = new javax.swing.tree.DefaultMutableTreeNode(And17);
				treeNode_26.add(treeNode_28);
					javax.swing.tree.DefaultMutableTreeNode treeNode_29 = new javax.swing.tree.DefaultMutableTreeNode(Condition13);
					treeNode_28.add(treeNode_29);
					javax.swing.tree.DefaultMutableTreeNode treeNode_30 = new javax.swing.tree.DefaultMutableTreeNode(Condition14);
					treeNode_28.add(treeNode_30);

		javax.swing.tree.DefaultMutableTreeNode treeNode_31 = new javax.swing.tree.DefaultMutableTreeNode(Rule7_ProblemFloodedEng);
		treeNode_0.add(treeNode_31);
		javax.swing.tree.DefaultMutableTreeNode treeNode_32 = new javax.swing.tree.DefaultMutableTreeNode(And16);
		treeNode_31.add(treeNode_32);
			javax.swing.tree.DefaultMutableTreeNode treeNode_33 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_32.add(treeNode_33);
			javax.swing.tree.DefaultMutableTreeNode treeNode_34 = new javax.swing.tree.DefaultMutableTreeNode(And15);
			treeNode_32.add(treeNode_34);
				javax.swing.tree.DefaultMutableTreeNode treeNode_35 = new javax.swing.tree.DefaultMutableTreeNode(Condition5);
				treeNode_34.add(treeNode_35);
				javax.swing.tree.DefaultMutableTreeNode treeNode_36 = new javax.swing.tree.DefaultMutableTreeNode(And14);
				treeNode_34.add(treeNode_36);
					javax.swing.tree.DefaultMutableTreeNode treeNode_37 = new javax.swing.tree.DefaultMutableTreeNode(Condition9);
					treeNode_36.add(treeNode_37);
					javax.swing.tree.DefaultMutableTreeNode treeNode_38 = new javax.swing.tree.DefaultMutableTreeNode(Condition11);
					treeNode_36.add(treeNode_38);

		javax.swing.tree.DefaultMutableTreeNode treeNode_39 = new javax.swing.tree.DefaultMutableTreeNode(Rule6_ProblemBadStarter);
		treeNode_0.add(treeNode_39);
		javax.swing.tree.DefaultMutableTreeNode treeNode_40 = new javax.swing.tree.DefaultMutableTreeNode(And13);
		treeNode_39.add(treeNode_40);
			javax.swing.tree.DefaultMutableTreeNode treeNode_41 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_40.add(treeNode_41);
			javax.swing.tree.DefaultMutableTreeNode treeNode_42 = new javax.swing.tree.DefaultMutableTreeNode(And12);
			treeNode_40.add(treeNode_42);
				javax.swing.tree.DefaultMutableTreeNode treeNode_43 = new javax.swing.tree.DefaultMutableTreeNode(Condition2);
				treeNode_42.add(treeNode_43);
				javax.swing.tree.DefaultMutableTreeNode treeNode_44 = new javax.swing.tree.DefaultMutableTreeNode(Condition6);
				treeNode_42.add(treeNode_44);

		javax.swing.tree.DefaultMutableTreeNode treeNode_45 = new javax.swing.tree.DefaultMutableTreeNode(Rule5_ProblemNoGas);
		treeNode_0.add(treeNode_45);
		javax.swing.tree.DefaultMutableTreeNode treeNode_46 = new javax.swing.tree.DefaultMutableTreeNode(And11);
		treeNode_45.add(treeNode_46);
			javax.swing.tree.DefaultMutableTreeNode treeNode_47 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_46.add(treeNode_47);
			javax.swing.tree.DefaultMutableTreeNode treeNode_48 = new javax.swing.tree.DefaultMutableTreeNode(And10);
			treeNode_46.add(treeNode_48);
				javax.swing.tree.DefaultMutableTreeNode treeNode_49 = new javax.swing.tree.DefaultMutableTreeNode(Condition5);
				treeNode_48.add(treeNode_49);
				javax.swing.tree.DefaultMutableTreeNode treeNode_50 = new javax.swing.tree.DefaultMutableTreeNode(Condition10);
				treeNode_48.add(treeNode_50);

		javax.swing.tree.DefaultMutableTreeNode treeNode_51 = new javax.swing.tree.DefaultMutableTreeNode(Rule4_ProblemFuelSystem);
		treeNode_0.add(treeNode_51);
		javax.swing.tree.DefaultMutableTreeNode treeNode_52 = new javax.swing.tree.DefaultMutableTreeNode(And9);
		treeNode_51.add(treeNode_52);
			javax.swing.tree.DefaultMutableTreeNode treeNode_53 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_52.add(treeNode_53);
			javax.swing.tree.DefaultMutableTreeNode treeNode_54 = new javax.swing.tree.DefaultMutableTreeNode(And8);
			treeNode_52.add(treeNode_54);
				javax.swing.tree.DefaultMutableTreeNode treeNode_55 = new javax.swing.tree.DefaultMutableTreeNode(Condition5);
				treeNode_54.add(treeNode_55);
				javax.swing.tree.DefaultMutableTreeNode treeNode_56 = new javax.swing.tree.DefaultMutableTreeNode(And7);
				treeNode_54.add(treeNode_56);
					javax.swing.tree.DefaultMutableTreeNode treeNode_57 = new javax.swing.tree.DefaultMutableTreeNode(Condition8);
					treeNode_56.add(treeNode_57);
					javax.swing.tree.DefaultMutableTreeNode treeNode_58 = new javax.swing.tree.DefaultMutableTreeNode(Condition9);
					treeNode_56.add(treeNode_58);

		javax.swing.tree.DefaultMutableTreeNode treeNode_59 = new javax.swing.tree.DefaultMutableTreeNode(Rule3_ProblemBadIgnition);
		treeNode_0.add(treeNode_59);
		javax.swing.tree.DefaultMutableTreeNode treeNode_60 = new javax.swing.tree.DefaultMutableTreeNode(And6);
		treeNode_59.add(treeNode_60);
			javax.swing.tree.DefaultMutableTreeNode treeNode_61 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_60.add(treeNode_61);
			javax.swing.tree.DefaultMutableTreeNode treeNode_62 = new javax.swing.tree.DefaultMutableTreeNode(And5);
			treeNode_60.add(treeNode_62);
				javax.swing.tree.DefaultMutableTreeNode treeNode_63 = new javax.swing.tree.DefaultMutableTreeNode(Condition5);
				treeNode_62.add(treeNode_63);
				javax.swing.tree.DefaultMutableTreeNode treeNode_64 = new javax.swing.tree.DefaultMutableTreeNode(And4);
				treeNode_62.add(treeNode_64);
					javax.swing.tree.DefaultMutableTreeNode treeNode_65 = new javax.swing.tree.DefaultMutableTreeNode(Condition6);
					treeNode_64.add(treeNode_65);
					javax.swing.tree.DefaultMutableTreeNode treeNode_66 = new javax.swing.tree.DefaultMutableTreeNode(Condition7);
					treeNode_64.add(treeNode_66);

		javax.swing.tree.DefaultMutableTreeNode treeNode_67 = new javax.swing.tree.DefaultMutableTreeNode(Rule2_ProblemDeadBattery2);
		treeNode_0.add(treeNode_67);
		javax.swing.tree.DefaultMutableTreeNode treeNode_68 = new javax.swing.tree.DefaultMutableTreeNode(And3);
		treeNode_67.add(treeNode_68);
			javax.swing.tree.DefaultMutableTreeNode treeNode_69 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_68.add(treeNode_69);
			javax.swing.tree.DefaultMutableTreeNode treeNode_70 = new javax.swing.tree.DefaultMutableTreeNode(Condition4);
			treeNode_68.add(treeNode_70);

		javax.swing.tree.DefaultMutableTreeNode treeNode_71 = new javax.swing.tree.DefaultMutableTreeNode(Rule1_ProblemDeadBattery1);
		treeNode_0.add(treeNode_71);
		javax.swing.tree.DefaultMutableTreeNode treeNode_72 = new javax.swing.tree.DefaultMutableTreeNode(And2);
		treeNode_71.add(treeNode_72);
			javax.swing.tree.DefaultMutableTreeNode treeNode_73 = new javax.swing.tree.DefaultMutableTreeNode(Condition1);
			treeNode_72.add(treeNode_73);
			javax.swing.tree.DefaultMutableTreeNode treeNode_74 = new javax.swing.tree.DefaultMutableTreeNode(And1);
			treeNode_72.add(treeNode_74);
				javax.swing.tree.DefaultMutableTreeNode treeNode_75 = new javax.swing.tree.DefaultMutableTreeNode(Condition2);
				treeNode_74.add(treeNode_75);
				javax.swing.tree.DefaultMutableTreeNode treeNode_76 = new javax.swing.tree.DefaultMutableTreeNode(Condition3);
				treeNode_74.add(treeNode_76);

		model.reload();
	}

	public void BackwardChainingSeq(ReteExecute executions)throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		ArrayList frl;
		ReteConflictSet.BackwardMode(12);
		//Fresh rule #[12]
		//EnteringRule_12
		//NodeAND_26
			//A_OpenCondition_20
			Condition20.SetTransparent();
			//EnteringRule_10
				//R_OpenCondition_16
			Condition16.SetTransparent();
			//ExecutingRule_10
			frl = ReteConflictSet.GetFiredOfMerit(-12);
			for(int i = 0; i < frl.size(); i++)
				executions.ExecuteRule((ReteToken)frl.get(i));
			//EnteringRule_1
			//NodeAND_2
				//A_OpenCondition_1
				Condition1.SetTransparent();
				//EnteringRule_2
				//NodeAND_3
					//A_OpenCondition_1
					//A_OpenCondition_4
					Condition4.SetTransparent();
					//EnteringRule_8
					//NodeAND_19
						//A_OpenCondition_1
					//NodeAND_18
						//A_OpenCondition_12
						Condition12.SetTransparent();
						//EnteringRule_9
						//NodeAND_22
							//A_OpenCondition_1
						//NodeAND_21
							//A_OpenCondition_12
						//NodeAND_20
							//A_OpenCondition_13
							Condition13.SetTransparent();
							//EnteringRule_11
							//NodeAND_25
							//NodeAND_24
								//A_OpenCondition_1
							//NodeAND_23
								//A_OpenCondition_17
								Condition17.SetTransparent();
								//A_OpenCondition_18
								Condition18.SetTransparent();
								//A_OpenCondition_19
								Condition19.SetTransparent();
							//ExecutingRule_11
							frl = ReteConflictSet.GetFiredOfMerit(-1);
							for(int i = 0; i < frl.size(); i++)
								executions.ExecuteRule((ReteToken)frl.get(i));
							//A_OpenCondition_15
							Condition15.SetTransparent();
						//ExecutingRule_9
						frl = ReteConflictSet.GetFiredOfMerit(-2);
						for(int i = 0; i < frl.size(); i++)
							executions.ExecuteRule((ReteToken)frl.get(i));
					//NodeAND_17
						//A_OpenCondition_13
						//A_OpenCondition_14
						Condition14.SetTransparent();
					//ExecutingRule_8
					frl = ReteConflictSet.GetFiredOfMerit(-3);
					for(int i = 0; i < frl.size(); i++)
						executions.ExecuteRule((ReteToken)frl.get(i));
				//ExecutingRule_2
				frl = ReteConflictSet.GetFiredOfMerit(-11);
				for(int i = 0; i < frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				//EnteringRule_3
				//NodeAND_6
					//A_OpenCondition_1
				//NodeAND_5
					//A_OpenCondition_5
					Condition5.SetTransparent();
				//NodeAND_4
					//A_OpenCondition_6
					Condition6.SetTransparent();
					//A_OpenCondition_7
					Condition7.SetTransparent();
				//ExecutingRule_3
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i < frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				//EnteringRule_4
				//NodeAND_9
					//A_OpenCondition_1
				//NodeAND_8
					//A_OpenCondition_5
				//NodeAND_7
					//A_OpenCondition_8
					Condition8.SetTransparent();
					//A_OpenCondition_9
					Condition9.SetTransparent();
				//ExecutingRule_4
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i < frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				//EnteringRule_5
				//NodeAND_11
					//A_OpenCondition_1
				//NodeAND_10
					//A_OpenCondition_5
					//A_OpenCondition_10
					Condition10.SetTransparent();
				//ExecutingRule_5
				frl = ReteConflictSet.GetFiredOfMerit(-8);
				for(int i = 0; i < frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				//EnteringRule_6
				//NodeAND_13
					//A_OpenCondition_1
				//NodeAND_12
					//A_OpenCondition_2
					Condition2.SetTransparent();
					//A_OpenCondition_6
				//ExecutingRule_6
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i < frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				//EnteringRule_7
				//NodeAND_16
					//A_OpenCondition_1
				//NodeAND_15
					//A_OpenCondition_5
				//NodeAND_14
					//A_OpenCondition_9
					//A_OpenCondition_11
					Condition11.SetTransparent();
				//ExecutingRule_7
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i < frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
			//NodeAND_1
				//A_OpenCondition_2
				//A_OpenCondition_3
				Condition3.SetTransparent();
			//ExecutingRule_1
			frl = ReteConflictSet.GetFiredOfMerit(-9);
			for(int i = 0; i < frl.size(); i++)
				executions.ExecuteRule((ReteToken)frl.get(i));
			//A_OpenCondition_21
			Condition21.SetTransparent();
		//ExecutingRule_12
		frl = ReteConflictSet.GetFiredOfMerit(-10);
		for(int i = 0; i < frl.size(); i++)
			executions.ExecuteRule((ReteToken)frl.get(i));
		//Fresh rule #[11]
		//Fresh rule #[10]
		//Fresh rule #[9]
		//Fresh rule #[8]
		//Fresh rule #[7]
		//Fresh rule #[6]
		//Fresh rule #[5]
		//Fresh rule #[4]
		//Fresh rule #[3]
		//Fresh rule #[2]
		//Fresh rule #[1]

		SetAllTransparent();
	}

	public void StartInferencing() throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		ReteToken token;
		executions = new ReteExecute(this, expert); 


//Backward Chaining section start
		COSMOS_START __starter = new COSMOS_START();
		__starter.init_status = 1;
		MemSetObject(__starter, "__starter");
		BackwardChainingSeq(executions);
//Backward Chaining section end


	}

	public void MemSetObject(Object obj, String name) 
	throws org.omg.CORBA.TypeCodePackage.BadKind, java.lang.IllegalAccessException
	{
		if(wmserverinterface==null)
		{
			time_stamp ++;
			if(name == null && obj != null)
			{
				UnFeedNetwork(obj);
				FeedNetwork(obj, time_stamp);
				return;
			}
			
			int i = ListOfNames.indexOf(name);
			if(i<0)
			{
				ListOfObjects.add(obj);
				ListOfNames.add(name);
				FeedNetwork(obj, time_stamp);
			}
			else 
			{
				if(obj == null)
				{
					Object oo = ListOfObjects.get(i);
					if(oo != null)
					{
						UnFeedNetwork(oo);
						//ReteConflictSet.RemoveTokensWithObsoleteObjects(oo);
						ListOfObjects.set(i, null);
					}
				}
				else
				{
					Object oo = ListOfObjects.get(i);
					UnFeedNetwork(oo);
					//ReteConflictSet.RemoveTokensWithObsoleteObjects(oo);
					ListOfObjects.set(i, obj);
					FeedNetwork(obj, time_stamp);
				}
			}
		}
		else
		{
			if(name == null)
			{
				if(obj == null) return;
				int i = ListOfObjects.indexOf(obj);
				if(i<0) return;
				name = (String)ListOfNames.get(i);
			}

			if(obj == null)
			{
				wmserverinterface.WMDeleteObject(name);
				LoadObjectsFromWM(true);
			}
			else
			{
					Class objclass = obj.getClass();
					String classname = objclass.getName();
// Setting up null strings in object				
					java.lang.reflect.Field[] fields = objclass.getFields();
					for(int f = 0; f < fields.length; f++)
					{
						if(fields[f].getType() == classname.getClass())
							if(fields[f].get(obj) == null) fields[f].set(obj, "");
					}

//			(else) if(obj.getClass().getName().compareTo("ReteObjects.classname")==0) RzeczHelper.insert(anyholder, (classname)obj);

				boolean set = true;
		if(classname.compareTo("ReteObjects.car")==0) carHelper.insert(anyholder, (car)obj);
				else if(classname.compareTo("ReteObjects.COSMOS_START")==0) COSMOS_STARTHelper.insert(anyholder, (COSMOS_START)obj);
				else if(classname.compareTo("ReteObjects.mechanic")==0) mechanicHelper.insert(anyholder, (mechanic)obj);
						else set = false;
				if(set)
				{
					wmserverinterface.WMSetObject(anyholder, name);
					LoadObjectsFromWM(true);
				}
			}
		}
	}
/*
	public Object MemGetObject(String name)
	{
		int i = ListOfNames.indexOf(name);
		if(i>=0) return ListOfObjects.get(i);
		else return null;
	}
*/
	public void LoadObjectsFromWM(boolean wait) throws org.omg.CORBA.TypeCodePackage.BadKind
	{

		if(wait) wmserverinterface.WMWaitForChange(time_stamp);

		org.omg.CORBA.IntHolder ti = new org.omg.CORBA.IntHolder();
			ti.value = 0;
		org.omg.CORBA.StringHolder on = new org.omg.CORBA.StringHolder();
 			on.value = "";
		org.omg.CORBA.AnyHolder ah = new org.omg.CORBA.AnyHolder();

		try
		{
			int tmax = -1;
			do
			{
				Object obj = null;
				wmserverinterface.WMGetObject(time_stamp, ti, on, ah);
				if(ti.value<0) {stop = true; return;}
				if(tmax < 0) tmax = ti.value;
				if(ah.value == null || ah.value.type().kind() == org.omg.CORBA.TCKind.tk_null)
				{
					if(on.value != null)
					{
						int i = ListOfNames.indexOf(on.value);
						if(i>=0)
						{
							UnFeedNetwork(ListOfObjects.get(i));
							ListOfObjects.set(i, null);
						}
					}
				}
				else
				{

		if(ah.value.type().name().compareTo("car")==0) obj = carHelper.extract(ah.value);
				else if(ah.value.type().name().compareTo("COSMOS_START")==0) obj = COSMOS_STARTHelper.extract(ah.value);
				else if(ah.value.type().name().compareTo("mechanic")==0) obj = mechanicHelper.extract(ah.value);
		
					if(on.value != null)
					{
						int i = ListOfNames.indexOf(on.value);
						if(i>=0)
						{
							Object oo = ListOfObjects.get(i);
							if(oo != null) UnFeedNetwork(oo);
							ListOfObjects.set(i, obj);
						}
						else
						{
							ListOfObjects.add(obj);
							ListOfNames.add(on.value);
						}
						FeedNetwork(obj, time_stamp+1);
					}
				}
				time_stamp++;
				if(ti.value < tmax) break;
			}while(time_stamp < tmax);
		}
		catch(Throwable e){}
		Thread.yield();
	}
}

