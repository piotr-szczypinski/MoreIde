//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteConflictSet.java              ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================
import java.util.*;

//==============================================================================
// ReteL node type Rule. Final node of network.
public class ReteConflictSet
{
	private static boolean mode = true; // true - FC, false - BC
  private static ArrayList token_list = new ArrayList();
  private static ArrayList token__list[];
  //private static ArrayList node_list = new ArrayList();

	public static String getReport()
	{
		StringBuffer report = new StringBuffer();
		try
		{
			if(mode) report.append("Conflict Set: FORWARD CHAINING mode\n");
			else report.append("Conflict Set: BACKWARD CHAINING mode\n");
			if(token__list!=null)
			{
				report.append("Rules fired:\n");
				for(int i=0; i<token__list.length; i++)
				{
					report.append("\tRule");
					report.append(i+1);
					report.append(": ");
					report.append(token__list[i].size());
					report.append("x\n");
				}
			}
			if(token_list!=null)
			{
				report.append("Rules fired:\n");
				for(int ii=0; ii<token_list.size(); ii++)
				{
					report.append("\tRule");
					report.append(-((ReteToken)token_list.get(ii)).node_merit);
					report.append("\n");
				}
			}
		}
		catch(Throwable e){};
		return report.toString();
	}

	public static void FireRule(ReteToken token)
	{
    if(mode) // forward chaining
		{
			if(!token_list.contains(token)) token_list.add(token);
		}
		else // backward chaining
		{
			int index = -token.node_merit-1;
			if(!token__list[index].contains(token))
			{
				token__list[index].add(token);
			}
		}
	}

	public static void BackwardMode(int rules)
	{
		mode = false;
		token_list = null;
		token__list = new ArrayList[rules];
		for(int i = 0; i < rules; i++)
		{
			token__list[i] = new ArrayList();
		}
	}

	public static void ForwardMode()
	{
		mode = true;
		token_list = new ArrayList();
		token__list = null;
	}

	public static ArrayList GetFiredOfMerit(int merit)
	{
		int index = -merit-1;
		ArrayList al = token__list[index];
		token__list[index] = new ArrayList();
		return al;
	}

	public static String TextList()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\tConflict set:\n");
		for(int i = 0; i < token_list.size(); i++) sb.append("\t\t" + token_list.get(i)+ " node:" + ((ReteToken)token_list.get(i)).node_merit + "\n");
		return sb.toString();
	}

	public static void UnFireRule(Object modified_object)
	{
		if(mode) // forward chaining
		{
			for(int j=token_list.size()-1; j>=0; j--)
			{
				ReteToken token = (ReteToken)token_list.get(j);
				if(token.IsTokenObsolate(token_list))
				{
					token_list.remove(j);
				}
			}
		}
		else // backward chaining
		{
			for(int index=0;  index<token__list.length; index++)
			for(int j=token__list[index].size()-1; j>=0; j--)
			{
				ReteToken token = (ReteToken)token__list[index].get(j);
				if(token.IsTokenObsolate(token__list[index]))
				{
					token__list[index].remove(j);

				}
			}
		}
	}


 	public static void RemoveFiredToken(ReteToken token)
	{
		if(token == null) return;
		int i = token_list.indexOf(token);
		if(i>=0) token_list.remove(i);
		//for( i = 0; i < token_list.size(); i++) System.out.println("###- " + token_list.get(i)+ " node:" + ((ReteToken)token_list.get(i)).node_merit);
		//if(token_list.size() <= 0) System.out.println("###- " + "no tokens");
	}

 	public static void RemoveTokensFromFiredRule(ReteToken token)
	{
		int token_merit;

		if(token == null) return;
		int i = token_list.indexOf(token);

		if(i>=0)
		{
			token_merit = token.node_merit;
			token_list.remove(i);

			for(int j=token_list.size()-1; j>=0; j--)
			{
				ReteToken tokn = (ReteToken)token_list.get(j);
				if(tokn.node_merit == token_merit) token_list.remove(j);
			}
		}
		//for( i = 0; i < token_list.size(); i++) System.out.println("###--- " + token_list.get(i)+ " node:" + ((ReteToken)token_list.get(i)).node_merit);
		//if(token_list.size() <= 0) System.out.println("###--- " + "no tokens");
	}

	public static void RemoveTokensWithObsoleteObjects(Object obj)
	{
		if(obj == null) return;

		if(mode)
		for(int j=token_list.size()-1; j>=0; j--)
		{
			ReteToken t = (ReteToken)token_list.get(j);
			if(t.IsTokenObsolate(obj)) token_list.remove(j);
		}
		else
		for(int j=token__list.length-1; j>=0; j--)
		{
			ArrayList al = (ArrayList)token__list[j];
			for(int i=al.size()-1; i>=0; i--)
			{
				ReteToken t = (ReteToken)al.get(i);
				if(t.IsTokenObsolate(obj)) al.remove(i);
			}
		}
	}
}


