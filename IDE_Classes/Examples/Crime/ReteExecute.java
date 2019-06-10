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


	public void Execute_InStudy(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Did the crime occured in the victims study? (yes/no)" name = s:s
		Print("Did the crime occured in the victims study? (yes/no)");
		String  $0$s = ReadLine();
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("InStudy");
			SetField(oo, ff,  $0$s );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_InBedroom(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Did the crime occured in the victims bedroom? (yes/no)" name = s:s
		Print("Did the crime occured in the victims bedroom? (yes/no)");
		String  $1$s = ReadLine();
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("InBedroom");
			SetField(oo, ff,  $1$s );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_InDressingRoom(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Did the crime occured in the victims dressing room? (yes/no)" name = s:s
		Print("Did the crime occured in the victims dressing room? (yes/no)");
		String  $2$s = ReadLine();
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("InDressingRoom");
			SetField(oo, ff,  $2$s );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_Footprints(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Are there footprints lead from outside to the body? (yes/no)" name = s:s
		Print("Are there footprints lead from outside to the body? (yes/no)");
		String  $3$s = ReadLine();
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("Footprints");
			SetField(oo, ff,  $3$s );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_WindowsDoorsLocked(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Are the windows and outer doors locked and free of signs of tampering? (yes/no)" name = s:s
		Print("Are the windows and outer doors locked and free of signs of tampering? (yes/no)");
		String  $4$s = ReadLine();
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("WindowsDoorsLocked");
			SetField(oo, ff,  $4$s );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_FornitureDisturbed(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Is the forniture disturbed? (yes/no)" name = s:s
		Print("Is the forniture disturbed? (yes/no)");
		String  $5$s = ReadLine();
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("FornitureDisturbed");
			SetField(oo, ff,  $5$s );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_MarksFromAttacker(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// READ PROMPT: object = "Are there any other marks from the attacker except for the wound? (yes/no)" name = s:s
		Print("Are there any other marks from the attacker except for the wound? (yes/no)");
		String  $6$s = ReadLine();
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("MarksFromAttacker");
			SetField(oo, ff,  $6$s );
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_CrimeInPrivateDomain(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("CrimeInPrivateDomain");
			SetField(oo, ff, "yes");
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_NoSignOfBrakein(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("NoSignOfBrakein");
			SetField(oo, ff, "yes");
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_VictimeDidNotStrugle(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("VictimeDidNotStrugle");
			SetField(oo, ff, "yes");
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_Start(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MAKE: class = Crime object = GarthwaiteCase
		Crime GarthwaiteCase = new Crime();
		GarthwaiteCase.VictimeKnewAndTrustedKiller="unknown";
		GarthwaiteCase.InStudy="unknown";
		GarthwaiteCase.InBedroom="unknown";
		GarthwaiteCase.InDressingRoom="unknown";
		GarthwaiteCase.Footprints="unknown";
		GarthwaiteCase.WindowsDoorsLocked="unknown";
		GarthwaiteCase.FornitureDisturbed="unknown";
		GarthwaiteCase.MarksFromAttacker="unknown";
		GarthwaiteCase.VictimeDidNotStrugle="unknown";
		GarthwaiteCase.NoSignOfBrakein="unknown";
		GarthwaiteCase.CrimeInPrivateDomain="unknown";
		network.MemSetObject((Object)GarthwaiteCase, "GarthwaiteCase");

// MODIFY: object = x
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("init_status");
			SetField(oo, ff, 2);
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_VictimeNewAndTrustedKiller(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// MODIFY: object = GarthwaiteCase
		oo = token.object[0];
		if(oo != null)
		{
			ff = oo.getClass().getDeclaredField("VictimeKnewAndTrustedKiller");
			SetField(oo, ff, "yes");
		}
		network.MemSetObject(oo, null);
	}


	public void Execute_PrintAnswer(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
// PRINT
		Print((String)"The victime knew and trusted killer");
		Print("\n");
	}
  
  public void ExecuteRule(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
  {
    switch(token.node_merit)
    {
		case -3: Execute_InStudy(token); break;
		case -4: Execute_InBedroom(token); break;
		case -5: Execute_InDressingRoom(token); break;
		case -6: Execute_Footprints(token); break;
		case -7: Execute_WindowsDoorsLocked(token); break;
		case -8: Execute_FornitureDisturbed(token); break;
		case -9: Execute_MarksFromAttacker(token); break;
		case -1: Execute_CrimeInPrivateDomain(token); break;
		case -10: Execute_NoSignOfBrakein(token); break;
		case -11: Execute_VictimeDidNotStrugle(token); break;
		case -12: Execute_Start(token); break;
		case -2: Execute_VictimeNewAndTrustedKiller(token); break;
		case -13: Execute_PrintAnswer(token); break;
    }
  }
}

