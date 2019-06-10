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
// READ PROMPT: object = "Variable 1?" name = s:h
		Print("Variable 1?");
		String  $0$h = ReadLine();
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("variable1");
			SetField(oo, ff,  $0$h );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_Input2(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Variable 2?" name = s:h
		Print("Variable 2?");
		String  $1$h = ReadLine();
// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("variable2");
			SetField(oo, ff,  $1$h );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_Start1(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MAKE: class = container object = object
		container object = new container();
		object.variable1="unknown";
		object.variable2="unknown";
		network.MemSetObject((Object)object, "object");

// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("init_status");
			SetField(oo, ff, 2);
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_Final(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// PRINT
		Print((String)"Finito OK ");
		Print("\n");
	}
  
  public void ExecuteRule(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
  {
    switch(token.node_merit)
    {
		case -1: Execute_Input1(token); break;
		case -2: Execute_Input2(token); break;
		case -4: Execute_Start1(token); break;
		case -3: Execute_Final(token); break;
    }
  }
}

