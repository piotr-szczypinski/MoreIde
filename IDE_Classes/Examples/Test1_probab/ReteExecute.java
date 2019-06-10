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

	private String ReadLine() throws IOException
	{
		if(expert != null) return expert.Read();

		ois = new BufferedReader(new InputStreamReader(System.in));
		return ois.readLine();
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


	public void Execute_Input1(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Variable 1?" name = d:h
		Print("Variable 1?");
		double $0$h = (new Double(ReadLine())).doubleValue();
		Print("Certainty measure?");
		float _Certainty_$0$h = (new Float(ReadLine())).floatValue();
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("variable1");
			SetField(oo, ff,  $0$h );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_variable1");
			SetField(oo, ff, ProbabilityFromCertainty(_Certainty_$0$h , 2000, 0.001));
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_Input2(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Variable 2?" name = d:h
		Print("Variable 2?");
		double $1$h = (new Double(ReadLine())).doubleValue();
		Print("Certainty measure?");
		float _Certainty_$1$h = (new Float(ReadLine())).floatValue();
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("variable2");
			SetField(oo, ff,  $1$h );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_variable2");
			SetField(oo, ff, ProbabilityFromCertainty(_Certainty_$1$h , 1000, 0.005));
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_Start1(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MAKE: class = container object = object
		container object = new container();
		object.variable1=0.0;
			object._P_H_Ep_variable1 = 1.0f;
		object.variable2=0.0;
			object._P_H_Ep_variable2 = 1.0f;
		network.MemSetObject((Object)object, "object");

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
	}


	public void Execute_Final(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// PRINT
		Print((String)"Finito OK ");
		Print("\n");
	}


	public void Execute_TestRul(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("variable3");
			SetField(oo, ff,  token.variable[1] , token.typeofvariable[1] );
			ff = oo.getClass().getDeclaredField("_P_H_Ep_variable3");
			SetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, 10000, 0.0001));
		}
		network.MemSetObject(oo, null);
	}
	
	public void ExecuteRule(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		Print(">>>> P_H_Ep_total = " + token.P_H_Ep_total + "\n");
		Print(">>>> P_H_Ep = " + token.P_H_Ep + "\n");
		Print(">>>> LSp = " + token.LSp + "\n");
		Print("\n");

		switch(token.node_merit)
		{
		case -1: Execute_Input1(token); break;
		case -2: Execute_Input2(token); break;
		case -4: Execute_Start1(token); break;
		case -3: Execute_Final(token); break;
		case -5: Execute_TestRul(token); break;
		}
	}
}

