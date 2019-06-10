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
		ReteNodeFinal Rule1_Input1 = new ReteNodeFinal(-1, 0);
		ReteNodeFinal Rule2_Input2 = new ReteNodeFinal(-2, 0);
		ReteNodeFinal Rule3_Start1 = new ReteNodeFinal(-4, 0);
		ReteNodeFinal Rule4_Final = new ReteNodeFinal(-3, 0);

//----------------- Condition nodes -----------------
		ReteNodeCondition Condition1 = new ReteNodeCondition();
		ReteNodeCondition Condition2 = new ReteNodeCondition();
		ReteNodeCondition Condition3 = new ReteNodeCondition();
		ReteNodeCondition Condition4 = new ReteNodeCondition();
		ReteNodeCondition Condition5 = new ReteNodeCondition();
		ReteNodeCondition Condition6 = new ReteNodeCondition();

//----------------- Logic nodes -----------------
		ReteNodeAND And1 = new ReteNodeAND();
		ReteNodeAND And2 = new ReteNodeAND();
		ReteNodeAND And3 = new ReteNodeAND();

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
		if(obj.getClass().getName().compareTo("ReteObjects.container")==0)
		{
//  container:variable1:==:"unknown"  1:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((container)obj).variable1) == 1)
			{
				if(((container)obj).variable1 != null)
				if(((container)obj).variable1.compareTo("unknown")==0)
					Condition1.InputCondition(token);
			}

//  container:variable2:==:"unknown"  2:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((container)obj).variable2) == 1)
			{
				if(((container)obj).variable2 != null)
				if(((container)obj).variable2.compareTo("unknown")==0)
					Condition2.InputCondition(token);
			}

//  container:variable1:!=:"unknown"  3:0:0:4
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((container)obj).variable1) == 1)
			{
				if(((container)obj).variable1 != null)
				if(((container)obj).variable1.compareTo("unknown")!=0)
					Condition3.InputCondition(token);
			}

//  container:variable1:==: $3$variable1   5:0:1:8
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.variable[0] = ReturnClass(((container)obj).variable1);
			token.typeofvariable[0] = TypeOfObject(((container)obj).variable1);
			Condition5.InputCondition(token);

//  container:variable2:==: $3$variable1   6:0:1:8
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			token.variable[0] = ReturnClass(((container)obj).variable2);
			token.typeofvariable[0] = TypeOfObject(((container)obj).variable2);
			Condition6.InputCondition(token);

		}
		else if(obj.getClass().getName().compareTo("ReteObjects.COSMOS_START")==0)
		{
//  COSMOS_START:init_status:==:1  4:0:0:1
			token = new ReteToken();
			token.ClearToken();
			token.object[0] = obj;
			token.timestamps[0] = time_stamp;
			if(TypeOfObject(((COSMOS_START)obj).init_status) == 2 || TypeOfObject(((COSMOS_START)obj).init_status) == 3)
			{
				if(((COSMOS_START)obj).init_status == 1)
					Condition4.InputCondition(token);
			}

		}
		return false;
	}

	public void UnFeedNetwork(Object obj)
	{
    ReteConflictSet.RemoveTokensWithObsoleteObjects(obj);

		if(obj.getClass().getName().compareTo("ReteObjects.container")==0)
		{
			Condition1.RemoveObsoleteTokens(obj);
			Condition2.RemoveObsoleteTokens(obj);
			Condition3.RemoveObsoleteTokens(obj);
			Condition5.RemoveObsoleteTokens(obj);
			Condition6.RemoveObsoleteTokens(obj);
		}
		else if(obj.getClass().getName().compareTo("ReteObjects.COSMOS_START")==0)
		{
			Condition4.RemoveObsoleteTokens(obj);
		}
	}

	public int CreateNetwork()
	{


//----------------- Connections -----------------
		Rule1_Input1.SetInputNode( And1 );
		And1.SetOutputNode( Rule1_Input1 );
		Rule2_Input2.SetInputNode( And2 );
		And2.SetOutputNode( Rule2_Input2 );
		Rule3_Start1.SetInputNode( Condition4 );
		Condition4.SetOutputNode( Rule3_Start1 );
		Rule4_Final.SetInputNode( And3 );
		And3.SetOutputNode( Rule4_Final );

		And1.SetInputNode( Condition1 );
		Condition1.SetOutputNode( And1 );
		And1.SetInputNode( Condition2 );
		Condition2.SetOutputNode( And1 );
		And2.SetInputNode( Condition3 );
		Condition3.SetOutputNode( And2 );
		And2.SetInputNode( Condition2 );
		Condition2.SetOutputNode( And2 );
		And3.SetInputNode( Condition5 );
		Condition5.SetOutputNode( And3 );
		And3.SetInputNode( Condition6 );
		Condition6.SetOutputNode( And3 );
		return 0;
	}

	public void BackwardChainingSeq(ReteExecute executions)throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
			boolean empty = true;
			ArrayList frl;
			boolean[] fired_rules = new boolean[4];
			for(int i=3; i>=0; i--) fired_rules[i] = false;

			ReteConflictSet.BackwardMode(4);
//Fresh rule #[3]
//Rule #[3]
			Condition5.SetTransparent();
			Condition6.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-3);
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
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-1);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[1]
			Condition3.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-2);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-1);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
//Rule #[2]
			Condition4.SetTransparent();
			do
			{
				empty = true;
				frl = ReteConflictSet.GetFiredOfMerit(-3);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) return;
				frl = ReteConflictSet.GetFiredOfMerit(-4);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[2] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-2);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[1] = true; empty = false;}
				frl = ReteConflictSet.GetFiredOfMerit(-1);
				for(int i = 0; i<frl.size(); i++)
					executions.ExecuteRule((ReteToken)frl.get(i));
				if(frl.size()>0) {fired_rules[0] = true; empty = false;}
			}
			while(!empty);
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
      /*DEBUG*/for( i = 0; i < ListOfNames.size(); i++) System.out.println("***+" + ListOfObjects.get(i) + " : " + ListOfNames.get(i));
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
		if(classname.compareTo("ReteObjects.container")==0) containerHelper.insert(anyholder, (container)obj);
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

		if(ah.value.type().name().compareTo("container")==0) obj = containerHelper.extract(ah.value);
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

