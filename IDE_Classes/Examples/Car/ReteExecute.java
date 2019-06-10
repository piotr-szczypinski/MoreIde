//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteExecute.java                  ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================

import java.util.*;
import ReteObjects.*;
import java.lang.*;
import java.lang.reflect.*;
import java.io.*;

public class ReteExecute
{
	private Object oo;
	private Field ff;
	private ReteNetwork network;
	private BufferedReader ois;
	private javax.swing.JTextField jtext;
	private Expert expert = null;

	private float ReadLine_Certainty = 5;

	public float ProbabilityFromCertainty(double C_E_Ep, double ls, double ln)
	{
		double P_H, P_E, O_H, P_H_E, P_H_NOT_E, O_H_E, O_H_NOT_E, P_E_Ep, P_H_Ep;

		if(ls<=0 || ln <= 0) return 1.0f;

		P_H = 0.1; //constant
		P_E = 0.1; //constant

		O_H=P_H/(1.0-P_H); //O_H = 0.1/0.9; constant
		P_H_E = ls*O_H/(1.0+ls*O_H);
		P_H_NOT_E = ln*O_H / (1.0+ln*O_H);
		O_H_E = P_H_E/(1.0-P_H_E);
		O_H_NOT_E = P_H_NOT_E/(1.0-P_H_NOT_E);

		if(C_E_Ep>0)
			P_E_Ep= (C_E_Ep*(1.0-P_E)+5.0*P_E)/5.0;
		else 
			P_E_Ep= (C_E_Ep*P_E+5.0*P_E)/5.0;

		if (P_E_Ep>=0.0 && P_E_Ep<=P_E) 
			P_H_Ep  = P_H_NOT_E + (P_E_Ep/P_E) * (P_E- P_H_NOT_E) ;
		else if (P_E<=P_E_Ep && P_E_Ep <= 1.0)
			P_H_Ep = (P_H - P_H_E*P_E )/(1.0-P_E) + P_E_Ep*(P_H_E - P_H )/(1.0-P_E) ;
		else P_H_Ep = 1.0;

		return (float)P_H_Ep;
	}

	public float ProbabilityFromTotal(double P_H_Ep_total, double ls, double ln)
	{
		double P_H, C_E_Ep;
		P_H = 0.1;

		if(P_H_Ep_total>=0 && P_H_Ep_total<=P_H) 
			C_E_Ep = 5.0*(P_H_Ep_total - P_H)/P_H;
		else if(P_H_Ep_total<=1 && P_H_Ep_total>P_H) 
			C_E_Ep = 5.0*(P_H_Ep_total - P_H)/(1 - P_H);
		else C_E_Ep = 5.0;
		return ProbabilityFromCertainty(C_E_Ep, ls, ln);
	}

	public ReteExecute(ReteNetwork n)
	{
		network = n;
	};

	public ReteExecute(ReteNetwork n, Expert e)
	{
		network = n;
		expert = e;
	};

	private void Print(String s)
	{
		if(expert != null) expert.Print(s);
		else System.out.print(s);
	}

	private String ReadLine(String question) throws IOException
	{
		if(expert != null)
		{
			expert.Certainty = (double)ReadLine_Certainty;
			expert.Question = question;
			Print(question);
			expert.Read();
			ReadLine_Certainty = (float)expert.Certainty;
			return expert.Answer;
		}
		else
		{
			if(ReadLine_Certainty<=5 && ReadLine_Certainty>=-5)
			{
				String answer;
				System.out.print(question);
				ois = new BufferedReader(new InputStreamReader(System.in));
				answer = ois.readLine();
				ois = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Certainty? \n");
				try
				{
					ois = new BufferedReader(new InputStreamReader(System.in));
					ReadLine_Certainty = (new Float(ois.readLine())).floatValue();
				}
				catch(Throwable e){};
				return answer;
			}
			else
			{
				System.out.print(question);
				ois = new BufferedReader(new InputStreamReader(System.in));
				return ois.readLine();
			}
		}
	}

	private void Display(String filename) throws IOException
	{
		if(expert != null) expert.Display(filename);
		else Runtime.getRuntime().exec("cmd /c \""+ filename + "\"");
	}

	public static void SetField(Object oo, Field ff, Object io, int typeofvariable) throws IllegalArgumentException, IllegalAccessException
	{
// typeofvariable
// 0 - no variable defined;
// 1 - String
// 2 - Integer
// 3 - Double
		if(typeofvariable == 0) return;
		try{
			if(ff.getType().getName().compareTo("java.lang.String")==0)
			{
				ff.set(oo, io.toString());
			}
			else if(typeofvariable == 1)
			{
				if(ff.getType().getName().compareTo("double")==0)
				{
					ff.setDouble(oo, ((Number)io).doubleValue());
				}
				else if(ff.getType().getName().compareTo("int")==0)
				{
					ff.setInt(oo, ((Number)io).intValue());
				}
			}
			else
			{
				if(ff.getType().getName().compareTo("double")==0)
				{
					ff.setDouble(oo, (new Double((String)io)).doubleValue());
				}
				else if(ff.getType().getName().compareTo("int")==0)
				{
					ff.setInt(oo, (new Integer((String)io)).intValue());
				}
			}
		}
		catch(Throwable e)
		{
		};
	}
	public static void SetField(Object oo, Field ff, float dd) throws IllegalArgumentException, IllegalAccessException
	{
		if(ff.getType().getName().compareTo("float")==0)
		{
			ff.setFloat(oo, dd);
		}
	}
	public static void SetField(Object oo, Field ff, double dd) throws IllegalArgumentException, IllegalAccessException
	{
		if(ff.getType().getName().compareTo("double")==0)
		{
			ff.setDouble(oo, dd);
		}
		else if(ff.getType().getName().compareTo("int")==0)
		{
			ff.setInt(oo, (int)dd);
		}
		else if(ff.getType().getName().compareTo("java.lang.String")==0)
		{
			ff.set(oo, (new Double(dd)).toString());
		}
	}
	public static void SetField(Object oo, Field ff, int ii) throws IllegalArgumentException, IllegalAccessException
	{
		if(ff.getType().getName().compareTo("int")==0)
		{
			ff.setInt(oo, ii);
		}
		else if(ff.getType().getName().compareTo("double")==0)
		{
			ff.setDouble(oo, (double)ii);
		}
		else if(ff.getType().getName().compareTo("java.lang.String")==0)
		{
			ff.set(oo, (new Integer(ii)).toString());
		}
	}
	public static void SetField(Object oo, Field ff, String ss) throws IllegalArgumentException, IllegalAccessException
	{
		if(ff.getType().getName().compareTo("java.lang.String")==0)
		{
			ff.set(oo, ss);
		}
	}


	public void Execute_ProblemDeadBattery1(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("problem");
			SetField(oo, ff, " has a dead battery");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 10000, 0.001));
		}
		network.MemSetObject(oo, null);
// DISPLAY: "battery.jpg"
		Display("battery.jpg");
	}


	public void Execute_ProblemDeadBattery2(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("problem");
			SetField(oo, ff, " has a dead_battery");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 13000, 0.001));
		}
		network.MemSetObject(oo, null);
// DISPLAY: "battery.jpg"
		Display("battery.jpg");
	}


	public void Execute_ProblemBadIgnition(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("problem");
			SetField(oo, ff, " has a bad ignition system");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 1000, 0.001));
		}
		network.MemSetObject(oo, null);
// DISPLAY: "ignition.jpg"
		Display("ignition.jpg");
	}


	public void Execute_ProblemFuelSystem(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("problem");
			SetField(oo, ff, " has a faulty fuel system");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 1000, 0.001));
		}
		network.MemSetObject(oo, null);
// DISPLAY: "fuel.jpg"
		Display("fuel.jpg");
	}


	public void Execute_ProblemNoGas(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("problem");
			SetField(oo, ff, " is out of gas");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 1000, 0.001));
		}
		network.MemSetObject(oo, null);
// DISPLAY: "gas.jpg"
		Display("gas.jpg");
	}


	public void Execute_ProblemBadStarter(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("problem");
			SetField(oo, ff, " has a bad starter");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 1000, 0.001));
		}
		network.MemSetObject(oo, null);
// DISPLAY: "starter.jpg"
		Display("starter.jpg");
	}


	public void Execute_ProblemFloodedEng(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("problem");
			SetField(oo, ff, "flooded_engine");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 1000, 0.001));
		}
		network.MemSetObject(oo, null);
// DISPLAY: "flooded.jpg"
		Display("flooded.jpg");
	}


	public void Execute_ProblemFuelIgn(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "how are your headlights (working/dim/dead) ?" name = s:h
		String $8$h = ReadLine("how are your headlights (working/dim/dead) ?");
		float _Certainty_$8$h = ReadLine_Certainty;
// READ PROMPT: object = "and spark plug spark (none/exists)?" name = s:s
		String $8$s = ReadLine("and spark plug spark (none/exists)?");
		float _Certainty_$8$s = ReadLine_Certainty;
// READ PROMPT: object = "status of your fuel gauge (full/empty):" name = s:g
		String $8$g = ReadLine("status of your fuel gauge (full/empty):");
		float _Certainty_$8$g = ReadLine_Certainty;
// READ PROMPT: object = "smell on your carburetor (yes/no) ?" name = s:carbu
		String $8$carbu = ReadLine("smell on your carburetor (yes/no) ?");
		float _Certainty_$8$carbu = ReadLine_Certainty;
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("init_problem");
			SetField(oo, ff, "fuel_or_ignition");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_init_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 1000, 0.001));
			ff = oo.getClass().getDeclaredField("headlights");
			SetField(oo, ff,  $8$h );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_headlights");
			SetField(oo, ff, ProbabilityFromCertainty(_Certainty_$8$h , 1000, 0.001));
			ff = oo.getClass().getDeclaredField("spark_plug_spark");
			SetField(oo, ff,  $8$s );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_spark_plug_spark");
			SetField(oo, ff, ProbabilityFromCertainty(_Certainty_$8$s , 1000, 0.001));
			ff = oo.getClass().getDeclaredField("carburetor_gas");
			SetField(oo, ff,  $8$carbu );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_carburetor_gas");
			SetField(oo, ff, ProbabilityFromCertainty(_Certainty_$8$carbu , 1000, 0.001));
			ff = oo.getClass().getDeclaredField("fuel_gauge_reading");
			SetField(oo, ff,  $8$g );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_fuel_gauge_reading");
			SetField(oo, ff, ProbabilityFromCertainty(_Certainty_$8$g , 1000, 0.001));
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_ProblemStartSystem(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "what about your headlights (working/dim/dead)?" name = s:h
		String $9$h = ReadLine("what about your headlights (working/dim/dead)?");
		float _Certainty_$9$h = ReadLine_Certainty;
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("init_problem");
			SetField(oo, ff, "starting_system");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_init_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 2000, 0.001));
			ff = oo.getClass().getDeclaredField("headlights");
			SetField(oo, ff,  $9$h );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_headlights");
			SetField(oo, ff, ProbabilityFromCertainty(_Certainty_$9$h , 2000, 0.001));
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_Start1(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MAKE: class = car object = Acar
		car Acar = new car();
		Acar.problem="unknown";
			Acar._P_H_Ep_problem = 1.0f;
		Acar.init_problem="unknown";
			Acar._P_H_Ep_init_problem = 1.0f;
		Acar.vehicle_make="HONDA";
			Acar._P_H_Ep_vehicle_make = 1.0f;
		Acar.ignition_key="off";
			Acar._P_H_Ep_ignition_key = 1.0f;
		network.MemSetObject((Object)Acar, "Acar");

// MAKE: class = mechanic object = Amec
		mechanic Amec = new mechanic();
		Amec.first_name="albert";
			Amec._P_H_Ep_first_name = 1.0f;
		network.MemSetObject((Object)Amec, "Amec");

// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("init_status");
			SetField(oo, ff, 2);
			ff = oo.getClass().getDeclaredField("_P_H_Ep_init_status");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 1000, 0.001));
		}
		network.MemSetObject(oo, null);
// PRINT
		Print((String)"Creating a car instance ");
		Print((String)" vehicle_make = HONDA");
		Print((String)".\n");
		Print("\n");
	}


	public void Execute_Start(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// PRINT
		Print((String)"Hello sir my name is ");
		Print((String) token.variable[1] );
		Print((String)".\n");
		Print("\n");
// PRINT
		Print((String)"Please turn on the key of your ");
		Print((String) token.variable[0] );
		Print((String)".\n");
		Print("\n");
// READ PROMPT: object = "Is the engine turning over (yes/no)? " name = s:ans
		String $11$ans = ReadLine("Is the engine turning over (yes/no)? ");
		float _Certainty_$11$ans = ReadLine_Certainty;
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("init_problem");
			SetField(oo, ff, "unknown");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_init_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 2000, 0.001));
			ff = oo.getClass().getDeclaredField("ignition_key");
			SetField(oo, ff, "on");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_ignition_key");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 2000, 0.001));
			ff = oo.getClass().getDeclaredField("engine_turning_over");
			SetField(oo, ff,  $11$ans );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_engine_turning_over");
			SetField(oo, ff, ProbabilityFromCertainty(_Certainty_$11$ans , 2000, 0.001));
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_FinalDiagnostic(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// PRINT
		Print((String)"sir I guess your car ");
		Print((String) token.variable[2] );
		Print("\n");
// MODIFY: object = c
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("ignition_key");
			SetField(oo, ff, "off");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_ignition_key");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 2000, 0.001));
			ff = oo.getClass().getDeclaredField("problem");
			SetField(oo, ff, "unknown");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 2000, 0.001));
			ff = oo.getClass().getDeclaredField("init_problem");
			SetField(oo, ff, "done");
			ff = oo.getClass().getDeclaredField("_P_H_Ep_init_problem");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 2000, 0.001));
		}
		network.MemSetObject(oo, null);
	}
	
	public void ExecuteRule(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		Print("P_H_Ep_total = " + token.P_H_Ep_total
			+ "   P_H_Ep = " + token.P_H_Ep 
			+ "   LSp = " + token.LSp + "\n");

		switch(token.node_merit)
		{
		case -9: Execute_ProblemDeadBattery1(token); break;
		case -11: Execute_ProblemDeadBattery2(token); break;
		case -6: Execute_ProblemBadIgnition(token); break;
		case -5: Execute_ProblemFuelSystem(token); break;
		case -8: Execute_ProblemNoGas(token); break;
		case -7: Execute_ProblemBadStarter(token); break;
		case -4: Execute_ProblemFloodedEng(token); break;
		case -3: Execute_ProblemFuelIgn(token); break;
		case -2: Execute_ProblemStartSystem(token); break;
		case -12: Execute_Start1(token); break;
		case -1: Execute_Start(token); break;
		case -10: Execute_FinalDiagnostic(token); break;
		}
	}
}

