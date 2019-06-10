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
  int crm = 0;
  public boolean stop = false;



//----------------- Rule nodes -----------------
		ReteNodeFinal Rule1_InStudy = new ReteNodeFinal(-3, 0);
		ReteNodeFinal Rule2_InBedroom = new ReteNodeFinal(-4, 0);
		ReteNodeFinal Rule3_InDressingRoom = new ReteNodeFinal(-5, 0);
		ReteNodeFinal Rule4_Footprints = new ReteNodeFinal(-6, 0);
		ReteNodeFinal Rule5_WindowsDoorsLocked = new ReteNodeFinal(-7, 0);
		ReteNodeFinal Rule6_FornitureDisturbed = new ReteNodeFinal(-8, 0);
		ReteNodeFinal Rule7_MarksFromAttacker = new ReteNodeFinal(-9, 0);
		ReteNodeFinal Rule8_CrimeInPrivateDomain = new ReteNodeFinal(-1, 0);
		ReteNodeFinal Rule9_NoSignOfBrakein = new ReteNodeFinal(-10, 0);
		ReteNodeFinal Rule10_VictimeDidNotStrugle = new ReteNodeFinal(-11, 0);
		ReteNodeFinal Rule11_Start = new ReteNodeFinal(-12, 0);
		ReteNodeFinal Rule12_VictimeNewAndTrustedKiller = new ReteNodeFinal(-2, 0);
		ReteNodeFinal Rule13_PrintAnswer = new ReteNodeFinal(-13, 0);

//----------------- Condition nodes -----------------
		ReteNodeCondition Condition1 = new ReteNodeCondition();
		ReteNodeCondition Condition2 = new ReteNodeCondition();
		ReteNodeCondition Condition3 = new ReteNodeCondition();
		ReteNodeCondition Condition4 = new ReteNodeCondition();
		ReteNodeCondition Condition5 = new ReteNodeCondition();
		ReteNodeCondition Condition6 = new ReteNodeCondition();
		ReteNodeCondition Condition7 = new ReteNodeCondition();
		ReteNodeCondition Condition8 = new ReteNodeCondition();
		ReteNodeCondition Condition9 = new ReteNodeCondition();
		ReteNodeCondition Condition10 = new ReteNodeCondition();
		ReteNodeCondition Condition11 = new ReteNodeCondition();
		ReteNodeCondition Condition12 = new ReteNodeCondition();
		ReteNodeCondition Condition13 = new ReteNodeCondition();
		ReteNodeCondition Condition14 = new ReteNodeCondition();
		ReteNodeCondition Condition15 = new ReteNodeCondition();
		ReteNodeCondition Condition16 = new ReteNodeCondition();
		ReteNodeCondition Condition17 = new ReteNodeCondition();
		ReteNodeCondition Condition18 = new ReteNodeCondition();
		ReteNodeCondition Condition19 = new ReteNodeCondition();
		ReteNodeCondition Condition20 = new ReteNodeCondition();
		ReteNodeCondition Condition21 = new ReteNodeCondition();
		ReteNodeCondition Condition22 = new ReteNodeCondition();
		ReteNodeCondition Condition23 = new ReteNodeCondition();
		ReteNodeCondition Condition24 = new ReteNodeCondition();
		ReteNodeCondition Condition25 = new ReteNodeCondition();
		ReteNodeCondition Condition26 = new ReteNodeCondition();

//----------------- Logic nodes -----------------
		ReteNodeAND And1 = new ReteNodeAND();
		ReteNodeAND And2 = new ReteNodeAND();
		ReteNodeAND And3 = new ReteNodeAND();
		ReteNodeAND And4 = new ReteNodeAND();
		ReteNodeAND And5 = new ReteNodeAND();
		ReteNodeAND And6 = new ReteNodeAND();
		ReteNodeAND And7 = new ReteNodeAND();
		ReteNodeOR Or8 = new ReteNodeOR();
		ReteNodeOR Or9 = new ReteNodeOR();
		ReteNodeOR Or10 = new ReteNodeOR();
		ReteNodeAND And11 = new ReteNodeAND();
		ReteNodeAND And12 = new ReteNodeAND();
		ReteNodeAND And13 = new ReteNodeAND();

//	int timer = 0;
	public static void main(String args[]) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		ReteNetwork rn= new ReteNetwork();
    
		rn.CreateNetwork();
    rn.ConnectMemory(args);
    rn.StartInferencing();
	}

  public static ReteNetwork WinMain(Expert expert, WMGetSetObject memimpl, Any any) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		ReteNetwork rn= new ReteNetwork();
    rn.expert = expert;
    rn.wmserverinterface = memimpl;
    rn.crm = expert.crm;

    rn.CreateNetwork();
	  rn.anyholder = any;
    rn.StartInferencing();
    return rn;
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
		Condition22.SetTransparent();
		Condition23.SetTransparent();
		Condition24.SetTransparent();
		Condition25.SetTransparent();
		Condition26.SetTransparent();
	}
	
  public void ConnectMemory(String args[]) throws org.omg.CORBA.TypeCodePackage.BadKind
  {
    try
    {
      // create and initialize the ORB
      ORB orb = ORB.init(args, null);

      anyholder = orb.create_any();

      // get the root naming context
      org.omg.CORBA.Object objRef = 
        orb.resolve_initial_references("NameService");

      // Use NamingContextExt instead of NamingContext. This is 
      // part of the Interoperable Naming Service.  
      NamingContextExt ncRef = 
        NamingContextExtHelper.narrow(objRef);
 
      // resolve the Object Reference in Naming
      String name = "WMGetSetObject";
      wmserverinterface = WMGetSetObjectHelper.narrow(ncRef.resolve_str(name));

      org.omg.CORBA.StringHolder on = new org.omg.CORBA.StringHolder();
		  on.value = "... --- ...";

      try{wmserverinterface.WMGetItem(0, on);}
      catch(Throwable e){wmserverinterface = null;}

//      System.out.println("ConnectMemory wmserverinterface= " + wmserverinterface);
/*
      ORB orb = ORB.init(args, null);
      org.omg.CORBA.Object obj = orb.string_to_object("corbaname::localhost:2900#AddServer");
      anyholder = orb.create_any();
      wmserverinterface = WMGetSetObjectHelper.narrow(obj);
*/
    } 
    catch ( Exception e ) 
    {
//       System.err.println( "Exception connecting to memory server..." + e );
//       e.printStackTrace( );
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
		if(obj.getClass().getName().compareTo("ReteObjects.Crime")==0)
		{
//  Crime:InStudy:!=:"yes"  1:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InStudy) == 1)
			{
				if(((Crime)obj).InStudy != null)
				if(((Crime)obj).InStudy.compareTo("yes")!=0)
					Condition1.InputCondition(token);
			}

//  Crime:InStudy:!=:"no"  2:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InStudy) == 1)
			{
				if(((Crime)obj).InStudy != null)
				if(((Crime)obj).InStudy.compareTo("no")!=0)
					Condition2.InputCondition(token);
			}

//  Crime:InBedroom:!=:"yes"  3:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InBedroom) == 1)
			{
				if(((Crime)obj).InBedroom != null)
				if(((Crime)obj).InBedroom.compareTo("yes")!=0)
					Condition3.InputCondition(token);
			}

//  Crime:InBedroom:!=:"no"  4:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InBedroom) == 1)
			{
				if(((Crime)obj).InBedroom != null)
				if(((Crime)obj).InBedroom.compareTo("no")!=0)
					Condition4.InputCondition(token);
			}

//  Crime:InDressingRoom:!=:"yes"  5:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InDressingRoom) == 1)
			{
				if(((Crime)obj).InDressingRoom != null)
				if(((Crime)obj).InDressingRoom.compareTo("yes")!=0)
					Condition5.InputCondition(token);
			}

//  Crime:InDressingRoom:!=:"no"  6:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InDressingRoom) == 1)
			{
				if(((Crime)obj).InDressingRoom != null)
				if(((Crime)obj).InDressingRoom.compareTo("no")!=0)
					Condition6.InputCondition(token);
			}

//  Crime:Footprints:!=:"yes"  7:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).Footprints) == 1)
			{
				if(((Crime)obj).Footprints != null)
				if(((Crime)obj).Footprints.compareTo("yes")!=0)
					Condition7.InputCondition(token);
			}

//  Crime:Footprints:!=:"no"  8:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).Footprints) == 1)
			{
				if(((Crime)obj).Footprints != null)
				if(((Crime)obj).Footprints.compareTo("no")!=0)
					Condition8.InputCondition(token);
			}

//  Crime:WindowsDoorsLocked:!=:"yes"  9:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).WindowsDoorsLocked) == 1)
			{
				if(((Crime)obj).WindowsDoorsLocked != null)
				if(((Crime)obj).WindowsDoorsLocked.compareTo("yes")!=0)
					Condition9.InputCondition(token);
			}

//  Crime:WindowsDoorsLocked:!=:"no"  10:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).WindowsDoorsLocked) == 1)
			{
				if(((Crime)obj).WindowsDoorsLocked != null)
				if(((Crime)obj).WindowsDoorsLocked.compareTo("no")!=0)
					Condition10.InputCondition(token);
			}

//  Crime:FornitureDisturbed:!=:"yes"  11:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).FornitureDisturbed) == 1)
			{
				if(((Crime)obj).FornitureDisturbed != null)
				if(((Crime)obj).FornitureDisturbed.compareTo("yes")!=0)
					Condition11.InputCondition(token);
			}

//  Crime:FornitureDisturbed:!=:"no"  12:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).FornitureDisturbed) == 1)
			{
				if(((Crime)obj).FornitureDisturbed != null)
				if(((Crime)obj).FornitureDisturbed.compareTo("no")!=0)
					Condition12.InputCondition(token);
			}

//  Crime:MarksFromAttacker:!=:"yes"  13:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).MarksFromAttacker) == 1)
			{
				if(((Crime)obj).MarksFromAttacker != null)
				if(((Crime)obj).MarksFromAttacker.compareTo("yes")!=0)
					Condition13.InputCondition(token);
			}

//  Crime:MarksFromAttacker:!=:"no"  14:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).MarksFromAttacker) == 1)
			{
				if(((Crime)obj).MarksFromAttacker != null)
				if(((Crime)obj).MarksFromAttacker.compareTo("no")!=0)
					Condition14.InputCondition(token);
			}

//  Crime:InStudy:==:"yes"  15:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InStudy) == 1)
			{
				if(((Crime)obj).InStudy != null)
				if(((Crime)obj).InStudy.compareTo("yes")==0)
					Condition15.InputCondition(token);
			}

//  Crime:InBedroom:==:"yes"  16:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InBedroom) == 1)
			{
				if(((Crime)obj).InBedroom != null)
				if(((Crime)obj).InBedroom.compareTo("yes")==0)
					Condition16.InputCondition(token);
			}

//  Crime:InDressingRoom:==:"yes"  17:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).InDressingRoom) == 1)
			{
				if(((Crime)obj).InDressingRoom != null)
				if(((Crime)obj).InDressingRoom.compareTo("yes")==0)
					Condition17.InputCondition(token);
			}

//  Crime:Footprints:==:"no"  18:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).Footprints) == 1)
			{
				if(((Crime)obj).Footprints != null)
				if(((Crime)obj).Footprints.compareTo("no")==0)
					Condition18.InputCondition(token);
			}

//  Crime:WindowsDoorsLocked:==:"yes"  19:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).WindowsDoorsLocked) == 1)
			{
				if(((Crime)obj).WindowsDoorsLocked != null)
				if(((Crime)obj).WindowsDoorsLocked.compareTo("yes")==0)
					Condition19.InputCondition(token);
			}

//  Crime:FornitureDisturbed:==:"no"  20:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).FornitureDisturbed) == 1)
			{
				if(((Crime)obj).FornitureDisturbed != null)
				if(((Crime)obj).FornitureDisturbed.compareTo("no")==0)
					Condition20.InputCondition(token);
			}

//  Crime:MarksFromAttacker:==:"no"  21:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).MarksFromAttacker) == 1)
			{
				if(((Crime)obj).MarksFromAttacker != null)
				if(((Crime)obj).MarksFromAttacker.compareTo("no")==0)
					Condition21.InputCondition(token);
			}

//  Crime:VictimeDidNotStrugle:==:"yes"  23:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).VictimeDidNotStrugle) == 1)
			{
				if(((Crime)obj).VictimeDidNotStrugle != null)
				if(((Crime)obj).VictimeDidNotStrugle.compareTo("yes")==0)
					Condition23.InputCondition(token);
			}

//  Crime:NoSignOfBrakein:==:"yes"  24:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).NoSignOfBrakein) == 1)
			{
				if(((Crime)obj).NoSignOfBrakein != null)
				if(((Crime)obj).NoSignOfBrakein.compareTo("yes")==0)
					Condition24.InputCondition(token);
			}

//  Crime:CrimeInPrivateDomain:==:"yes"  25:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).CrimeInPrivateDomain) == 1)
			{
				if(((Crime)obj).CrimeInPrivateDomain != null)
				if(((Crime)obj).CrimeInPrivateDomain.compareTo("yes")==0)
					Condition25.InputCondition(token);
			}

//  Crime:VictimeKnewAndTrustedKiller:==:"yes"  26:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((Crime)obj).VictimeKnewAndTrustedKiller) == 1)
			{
				if(((Crime)obj).VictimeKnewAndTrustedKiller != null)
				if(((Crime)obj).VictimeKnewAndTrustedKiller.compareTo("yes")==0)
					Condition26.InputCondition(token);
			}

		}
		else if(obj.getClass().getName().compareTo("ReteObjects.COSMOS_START")==0)
		{
//  COSMOS_START:init_status:==:1  22:0:0:1
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((COSMOS_START)obj).init_status) == 2 || TypeOfObject(((COSMOS_START)obj).init_status) == 3)
			{
				if(((COSMOS_START)obj).init_status == 1)
					Condition22.InputCondition(token);
			}

		}
		return false;
	}

	public void UnFeedNetwork(Object obj)
	{
    ReteConflictSet.RemoveTokensWithObsoleteObjects(obj);

		if(obj.getClass().getName().compareTo("ReteObjects.Crime")==0)
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
			Condition16.RemoveObsoleteTokens(obj);
			Condition17.RemoveObsoleteTokens(obj);
			Condition18.RemoveObsoleteTokens(obj);
			Condition19.RemoveObsoleteTokens(obj);
			Condition20.RemoveObsoleteTokens(obj);
			Condition21.RemoveObsoleteTokens(obj);
			Condition23.RemoveObsoleteTokens(obj);
			Condition24.RemoveObsoleteTokens(obj);
			Condition25.RemoveObsoleteTokens(obj);
			Condition26.RemoveObsoleteTokens(obj);
		}
		else if(obj.getClass().getName().compareTo("ReteObjects.COSMOS_START")==0)
		{
			Condition22.RemoveObsoleteTokens(obj);
		}
	}

	public int CreateNetwork()
	{


//----------------- Connections -----------------
		Rule1_InStudy.SetInputNode( And1 );
		And1.SetOutputNode( Rule1_InStudy );
		Rule2_InBedroom.SetInputNode( And2 );
		And2.SetOutputNode( Rule2_InBedroom );
		Rule3_InDressingRoom.SetInputNode( And3 );
		And3.SetOutputNode( Rule3_InDressingRoom );
		Rule4_Footprints.SetInputNode( And4 );
		And4.SetOutputNode( Rule4_Footprints );
		Rule5_WindowsDoorsLocked.SetInputNode( And5 );
		And5.SetOutputNode( Rule5_WindowsDoorsLocked );
		Rule6_FornitureDisturbed.SetInputNode( And6 );
		And6.SetOutputNode( Rule6_FornitureDisturbed );
		Rule7_MarksFromAttacker.SetInputNode( And7 );
		And7.SetOutputNode( Rule7_MarksFromAttacker );
		Rule8_CrimeInPrivateDomain.SetInputNode( Or9 );
		Or9.SetOutputNode( Rule8_CrimeInPrivateDomain );
		Rule9_NoSignOfBrakein.SetInputNode( Or10 );
		Or10.SetOutputNode( Rule9_NoSignOfBrakein );
		Rule10_VictimeDidNotStrugle.SetInputNode( And11 );
		And11.SetOutputNode( Rule10_VictimeDidNotStrugle );
		Rule11_Start.SetInputNode( Condition22 );
		Condition22.SetOutputNode( Rule11_Start );
		Rule12_VictimeNewAndTrustedKiller.SetInputNode( And13 );
		And13.SetOutputNode( Rule12_VictimeNewAndTrustedKiller );
		Rule13_PrintAnswer.SetInputNode( Condition26 );
		Condition26.SetOutputNode( Rule13_PrintAnswer );

		And1.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And1 );
		And1.SetInputNode( Condition2 );
		Condition2.SetOutputNode( And1 );
		And2.SetInputNode( Condition3 );
		Condition3.SetOutputNode( And2 );
		And2.SetInputNode( Condition4 );
		Condition4.SetOutputNode( And2 );
		And3.SetInputNode( Condition5 );
		Condition5.SetOutputNode( And3 );
		And3.SetInputNode( Condition6 );
		Condition6.SetOutputNode( And3 );
		And4.SetInputNode( Condition7 );
		Condition7.SetOutputNode( And4 );
		And4.SetInputNode( Condition8 );
		Condition8.SetOutputNode( And4 );
		And5.SetInputNode( Condition9 );
		Condition9.SetOutputNode( And5 );
		And5.SetInputNode( Condition10 );
		Condition10.SetOutputNode( And5 );
		And6.SetInputNode( Condition11 );
		Condition11.SetOutputNode( And6 );
		And6.SetInputNode( Condition12 );
		Condition12.SetOutputNode( And6 );
		And7.SetInputNode( Condition13 );
		Condition13.SetOutputNode( And7 );
		And7.SetInputNode( Condition14 );
		Condition14.SetOutputNode( And7 );
		Or8.SetInputNode( Condition16 );
		Condition16.SetOutputNode( Or8 );
		Or8.SetInputNode( Condition17 );
		Condition17.SetOutputNode( Or8 );
		Or9.SetInputNode( Condition15 );
		Condition15.SetOutputNode( Or9 );
		Or9.SetInputNode( Or8 );
		Or8.SetOutputNode( Or9 );
		Or10.SetInputNode( Condition18 );
		Condition18.SetOutputNode( Or10 );
		Or10.SetInputNode( Condition19 );
		Condition19.SetOutputNode( Or10 );
		And11.SetInputNode( Condition20 );
		Condition20.SetOutputNode( And11 );
		And11.SetInputNode( Condition21 );
		Condition21.SetOutputNode( And11 );
		And12.SetInputNode( Condition24 );
		Condition24.SetOutputNode( And12 );
		And12.SetInputNode( Condition25 );
		Condition25.SetOutputNode( And12 );
		And13.SetInputNode( Condition23 );
		Condition23.SetOutputNode( And13 );
		And13.SetInputNode( And12 );
		And12.SetOutputNode( And13 );
		return 0;
	}

	public void BackwardChainingSeq(ReteExecute executions)throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
			boolean empty = true;
			ArrayList frl;
			boolean[] fired_rules = new boolean[13];
			for(int i=12; i>=0; i--) fired_rules[i] = false;

			ReteConflictSet.BackwardMode(13);
//Fresh rule #[12]
//Rule #[12]
			Condition26.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
			}
			while(!empty);
//Rule #[0]
			Condition1.SetTransparent();
			Condition2.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[1]
			Condition3.SetTransparent();
			Condition4.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[2]
			Condition5.SetTransparent();
			Condition6.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[3]
			Condition7.SetTransparent();
			Condition8.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[4]
			Condition9.SetTransparent();
			Condition10.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[4] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[5]
			Condition11.SetTransparent();
			Condition12.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-8);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[5] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[4] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[6]
			Condition13.SetTransparent();
			Condition14.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-9);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[6] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-8);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[5] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[4] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[10]
			Condition22.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-12);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[10] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-9);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[6] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-8);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[5] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[4] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[9]
			Condition20.SetTransparent();
			Condition21.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-12);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[10] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-11);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[9] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-9);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[6] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-8);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[5] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[4] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[8]
			Condition18.SetTransparent();
			Condition19.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-12);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[10] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-11);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[9] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-10);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[8] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-9);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[6] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-8);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[5] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[4] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[7]
			Condition15.SetTransparent();
			Condition16.SetTransparent();
			Condition17.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-12);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[10] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-11);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[9] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-10);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[8] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-1);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[7] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-9);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[6] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-8);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[5] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[4] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[11]
			Condition23.SetTransparent();
			Condition24.SetTransparent();
			Condition25.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-13);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-2);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[11] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-12);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[10] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-11);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[9] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-10);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[8] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-1);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[7] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-9);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[6] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-8);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[5] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-7);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[4] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-6);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[3] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-5);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
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
//Fresh rule #[0]
	}

	public void StartInferencing() throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
  {
    ReteToken token;
    executions = new ReteExecute(this, expert); 

		if(crm == 3) 
		{
		COSMOS_START __starter = new COSMOS_START();
		__starter.init_status = 1;
		MemSetObject(__starter, "__starter");
			BackwardChainingSeq(executions);
		}
		else
		{
			SetAllTransparent();
      ReteConflictSet.ForwardMode();
		COSMOS_START __starter = new COSMOS_START();
		__starter.init_status = 1;
		MemSetObject(__starter, "__starter");
			do
			{
//				if(wmserverinterface!=null)	LoadObjectsFromWM();
				
				token = ReteConflictSet.GetFiredToken(crm);
				if(token != null)
				{
					ReteConflictSet.RemoveTokensFromFiredRule(token);
					executions.ExecuteRule(token);
					token = null;
				}
				else if(wmserverinterface!=null) LoadObjectsFromWM(true);
			}
	//    while(true);
	//    while((token != null) && (!stop));
			while(!stop);
		}
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
      //for( i = 0; i < ListOfNames.size(); i++) System.out.println("***+" + ListOfObjects.get(i) + " : " + ListOfNames.get(i));
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

//      (else) if(obj.getClass().getName().compareTo("ReteObjects.classname")==0) RzeczHelper.insert(anyholder, (classname)obj);

        boolean set = true;
		if(classname.compareTo("ReteObjects.Crime")==0) CrimeHelper.insert(anyholder, (Crime)obj);
				else if(classname.compareTo("ReteObjects.COSMOS_START")==0) COSMOS_STARTHelper.insert(anyholder, (COSMOS_START)obj);
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
//    if(wmserverinterface == null) return;
//    expert.Print("+");
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

/*
        do{
          wmserverinterface.WMGetObject(time_stamp, ti, on, ah);
          if(stop) return;
        }while(time_stamp>=ti.value);
*/

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

		if(ah.value.type().name().compareTo("Crime")==0) obj = CrimeHelper.extract(ah.value);
				else if(ah.value.type().name().compareTo("COSMOS_START")==0) obj = COSMOS_STARTHelper.extract(ah.value);
		
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

