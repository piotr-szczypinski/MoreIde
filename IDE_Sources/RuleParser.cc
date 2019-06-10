##SaveTo ReteNetwork.txt
//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ParsingResults                    ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================
##WriteParsingResults

//----NETWORK CONFIG----------------------------
##WriteNetworkConfiguration

##SaveTo ReteNode.java
//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteNode.java                     ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================

import java.util.*;
//==============================================================================
// ReteL node implementation. Interface for other nodes.
public abstract class ReteNode
{
// INTERFACE FUNCTONS:
// Function called by one of two input nodes when token is being sent
	public abstract void InputChange(Object calling_node, ReteToken input);
// Function returns no. of tokens on its output
	public abstract int GetToken();
// Function returns token of index
	public abstract ReteToken GetToken(int index);
// Function removes obsolete tokens, which contain info related to modified object
	public abstract void RemoveObsoleteTokens(Object modified_object);

// Function returns report on nodes status to be displayed in the network info window
	public String getReport()
	{
		StringBuffer report = new StringBuffer();
		try
		{
			report.append(this_name);
			report.append(", signature: ");
			report.append(super.toString());
			report.append("\n");
			report.append("No. of tokens stored: ");
			report.append(output_tokens.size());
			report.append("\n");

			for(int k=0; k<output_tokens.size(); k++)
			{
				report.append("\n\t");
				report.append(((ReteToken)output_tokens.get(k)).toString());
				report.append("\n");
				report.append(((ReteToken)output_tokens.get(k)).getReport());
				report.append("\n");
			}
		}
		catch(Throwable e){};

		return report.toString();
	}

	protected String this_name = new String("");
//------------------------------------------------------------------------------
	ReteNode()
	{
		input_node0 = null;
		input_node1 = null;
		output_node = new ArrayList();
		output_tokens = new ArrayList();
	}
//------------------------------------------------------------------------------
	ReteNode(String name)
	{
		input_node0 = null;
		input_node1 = null;
		output_node = new ArrayList();
		output_tokens = new ArrayList();
		this_name = name;
	}

//------------------------------------------------------------------------------
	public String toString()
	{
		return this_name;
	}

//------------------------------------------------------------------------------
	public void SetInputNode(Object calling_node)
	{
		if(input_node0 == null) input_node0 = calling_node;
		else if((input_node0 != calling_node) && input_node1 == null)
			input_node1 = calling_node;
//		else if((input_node0 != calling_node) && (input_node1 != calling_node))
//			System.out.println(":-( Two-input nodes allowed only!!!");
//		else System.out.println(":-( Input node already set!!!");
	}

//------------------------------------------------------------------------------
	public void SetOutputNode(Object calling_node)
	{
		if(!output_node.contains(calling_node))
		{
			output_node.add(calling_node);
		}
//		else System.out.println(":-( Output node already set!!!");
	}

//------------------------------------------------------------------------------
	protected Object input_node0, input_node1;
	protected ArrayList output_node;
	protected ArrayList output_tokens;
}

##SaveTo ReteNodeCondition.java
//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteNodeCondition.java            ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================

import java.util.*;

//==============================================================================
// ReteL Conditional node type Rule. Conditional node of network.
public class ReteNodeCondition extends ReteNode
{
	public ReteNodeCondition(String name)
	{
		input_node0 = null;
		input_node1 = null;
		output_node = new ArrayList();
		output_tokens = new ArrayList();
		this_name = name;
	}

##IfBC
	private boolean hold = true; //hold prevents sending tokens further when BC is used.
	private ArrayList halted_tokens = new ArrayList();

	public void SetTransparent()
	{
		if(hold)
		{
			hold = false;
			output_tokens = halted_tokens; 
			halted_tokens = null;
			int kk = output_tokens.size();
			int ii = output_node.size();
			for(int k=0; k<kk; k++)
				for(int i=0; i<ii; i++)
					((ReteNode)output_node.get(i)).InputChange(this, (ReteToken)output_tokens.get(k));
		}
	}
##EndIf

	public void InputCondition(ReteToken input)
	{
##IfBC
		if(hold)
		{
			halted_tokens.add(input);
		}
		else
		{
			output_tokens.add(input);

			for(int i=0; i<output_node.size(); i++)
				((ReteNode)output_node.get(i)).InputChange(this, input);
		}
##Else
		output_tokens.add(input);

		for(int i=0; i<output_node.size(); i++)
			((ReteNode)output_node.get(i)).InputChange(this, input);
##EndIf
	}

	public void InputChange(Object calling_node, ReteToken input)
	{
		return;
	}

	public int GetToken()
	{
		return output_tokens.size();
	}

	public ReteToken GetToken(int index)
	{
		return (ReteToken)output_tokens.get(index);
	}

	public void RemoveObsoleteTokens(Object modified_object)
	{
		boolean r = false;
##IfBC
		if(hold)
		{
			for(int j=halted_tokens.size()-1; j>=0; j--)
			{
				ReteToken token = (ReteToken)halted_tokens.get(j);
				if(token.IsTokenObsolate(modified_object))
				{
					halted_tokens.remove(j);
				}
			}
		}
		else
		{
			for(int j=output_tokens.size()-1; j>=0; j--)
			{
				ReteToken token = (ReteToken)output_tokens.get(j);
				if(token.IsTokenObsolate(modified_object))
				{
					output_tokens.remove(j); r = true;
				}
			}
			if(r) for(int i=output_node.size()-1; i>=0; i--)
				((ReteNode)output_node.get(i)).RemoveObsoleteTokens(modified_object);
		}
##Else
		for(int j=output_tokens.size()-1; j>=0; j--)
		{
			ReteToken token = (ReteToken)output_tokens.get(j);
			if(token.IsTokenObsolate(modified_object))
			{
				output_tokens.remove(j); r = true;
			}
		}
		if(r) for(int i=output_node.size()-1; i>=0; i--)
			((ReteNode)output_node.get(i)).RemoveObsoleteTokens(modified_object);
##EndIf
	}
}

##SaveTo ReteNodeAND.java
//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteNodeAND.java                  ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================
import java.util.*;

//==============================================================================
// ReteL node type AND
public class ReteNodeAND extends ReteNode
{
	ReteNodeAND(String name)
	{
		input_node0 = null;
		input_node1 = null;
		output_node = new ArrayList();
		output_tokens = new ArrayList();
		this_name = name;
	}

	public void InputChange(Object calling_node, ReteToken input)
	{
##IfDebug
		/*DEBUG*/System.out.println("In AND " + toString());
##EndIf

		if(input_node0 == calling_node)
		{
			int t = ((ReteNode)input_node1).GetToken();

			for(int i = 0; i < t; i++)
			{
				ReteToken token = ReteToken.Join(input, ((ReteNode)input_node1).GetToken(i));
//				ReteToken.TokenPrint(input);
//				ReteToken.TokenPrint(((ReteNode)input_node1).GetToken(i));
//				ReteToken.TokenPrint(token);
				if(token!=null)
				{
					output_tokens.add(token);
##IfDebug
					/*DEBUG*/System.out.println("Input1 AND " + toString()+ " joined tokens# "  + output_tokens.size());
##EndIf
					for(int j=0; j<output_node.size(); j++)
						((ReteNode)output_node.get(j)).InputChange(this, token);
				}
			}
		}
		else if(input_node1 == calling_node)
		{
			int t = ((ReteNode)input_node0).GetToken();
			for(int i = 0; i < t; i++)
			{
				ReteToken token = ReteToken.Join(input, ((ReteNode)input_node0).GetToken(i));
//				ReteToken.TokenPrint(input);
//				ReteToken.TokenPrint(((ReteNode)input_node0).GetToken(i));
//				ReteToken.TokenPrint(token);
				if(token!=null)
				{
					output_tokens.add(token);
##IfDebug
					/*DEBUG*/System.out.println("Input2 AND " + toString()+ " joined tokens# "  + output_tokens.size());
##EndIf
					for(int j=0; j<output_node.size(); j++)
						((ReteNode)output_node.get(j)).InputChange(this, token);
				}
			}
		}
		else
		{
##IfDebug
			/*DEBUG*/System.out.println(":-( Network connection error!!!");
##EndIf
			return;
		}
	}

	public int GetToken()
	{
		return output_tokens.size();
	}

	public ReteToken GetToken(int index)
	{
		return (ReteToken)output_tokens.get(index);
	}

	public void RemoveObsoleteTokens(Object modified_object)
	{
		boolean r = false;

		for(int j=output_tokens.size()-1; j>=0; j--)
		{
			ReteToken token = (ReteToken)output_tokens.get(j);
			if(token.IsTokenObsolate(modified_object))
			{
				output_tokens.remove(j); r = true;
			}
		}
		if(r) for(int i=output_node.size()-1; i>=0; i--)
			((ReteNode)output_node.get(i)).RemoveObsoleteTokens(modified_object);
	}
}

##SaveTo ReteNodeOR.java
//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteNodeOR.java                   ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================
import java.util.*;
//==============================================================================
// ReteL node type OR
public class ReteNodeOR extends ReteNode
{
	int tokens_in_node0, tokens_in_node1;

	ReteNodeOR(String name)
	{
		input_node0 = null;
		input_node1 = null;
		output_node = new ArrayList();
		output_tokens = new ArrayList();
		this_name = name;
	}

	public void InputChange(Object calling_node, ReteToken input)
	{
##IfDebug
		/*DEBUG*/System.out.println("In OR>>> " + toString());
##EndIf
		for(int i=0; i<output_node.size(); i++)
			((ReteNode)output_node.get(i)).InputChange(this, input);
	}

	public int GetToken()
	{
		tokens_in_node0 = ((ReteNode)input_node0).GetToken();
		tokens_in_node1 = ((ReteNode)input_node1).GetToken();
		return tokens_in_node0 + tokens_in_node1;
	}

	public ReteToken GetToken(int index)
	{
		if(index >= 0 && index < tokens_in_node0)
			return ((ReteNode)input_node0).GetToken(index);
		else
			return ((ReteNode)input_node1).GetToken(index - tokens_in_node0);
	}

	public void RemoveObsoleteTokens(Object modified_object)
	{
		for(int i=0; i<output_node.size(); i++)
			((ReteNode)output_node.get(i)).RemoveObsoleteTokens(modified_object);
	}
}

##SaveTo ReteNodeFinal.java
//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteNodeFinal.java                ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================
import java.util.*;

//==============================================================================
// ReteL node type Rule. Final node of network.
public class ReteNodeFinal extends ReteNode
{
	private int node_merit;
	private int objc_merit;

	ReteNodeFinal(String name, int i, int o)
	{
		node_merit = i;
		objc_merit = o;
		input_node0 = null;
		input_node1 = null;
		output_node = new ArrayList();
		output_tokens = new ArrayList();
		this_name = name;
	}

	public String getReport()
	{
		StringBuffer report = new StringBuffer();
		try
		{
			report.append(this_name);
			report.append(", signature: ");
			report.append(super.toString());
			report.append("\n");
			report.append("No. of tokens fireing: " + GetToken());
			report.append("\n");
		}
		catch(Throwable e){};
		return report.toString();
	}

	public void InputChange(Object calling_node, ReteToken input)
	{
		if(input_node0 == calling_node)
		{
##IfII
			double O_H_Ep_total, O_H;
			O_H = 0.1/0.9; //O_H = P_H/(1.0-P_H); P_H = 0.1;
			O_H_Ep_total = O_H * input.LSp;
			if(O_H_Ep_total >= 0)
				input.P_H_Ep_total = O_H_Ep_total/(1.0+O_H_Ep_total);
			else input.P_H_Ep_total = -2.0;

##EndIf
			if(input.Evaluate())
			{
##IfDebug
				/*DEBUG*/System.out.println("Final " + toString() + " evaluated, fireing rule");
##EndIf

				input.node_merit = node_merit;
				input.node_meats = input.timestamps[objc_merit];
				
				int ii = 0;
				for(int i = 0; i < input.objectsno; i++)
					if(ii < input.timestamps[i] && input.object[i] != null) ii = input.timestamps[i];
				input.node_lexts = ii;
				ReteConflictSet.FireRule(input);
			}
##IfDebug
				/*DEBUG*/else System.out.println("Final " + toString() + " not evaluated, not fireing rule");
##EndIf
		}
		else
		{
##IfDebug
			/*DEBUG*/System.out.println(":-( Network connection error!!!");
##EndIf
			return;
		}
##IfDebug
		/*DEBUG*/System.out.println("Node merit: " + node_merit + ", token added, tokens firing the node: " + ((ReteNode)calling_node).GetToken());
##EndIf
	}

	public int GetToken()
	{
		return ((ReteNode)input_node0).GetToken();
	}

	public ReteToken GetToken(int index)
	{
		return ((ReteNode)input_node0).GetToken(index);
	}

	public void RemoveObsoleteTokens(Object modified_object)
	{
		ReteConflictSet.UnFireRule(modified_object);
    //System.out.println("Node merit: " + node_merit + ", token removed, tokens firing the node: " + ((ReteNode)input_node0).GetToken());
		//System.out.println("Token removed");
		//System.out.println("No. of tokens firing the node: " + ((ReteNode)input_node0).GetToken());
	}
}

##SaveTo ReteConflictSet.java
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
##IfDebug
    /*DEBUG*/for(int i = 0; i < token_list.size(); i++) System.out.println("###+ " + token_list.get(i)+ " node:" + ((ReteToken)token_list.get(i)).node_merit);
    /*DEBUG*/if(token_list.size() <= 0) System.out.println("###+ " + "no tokens");
##EndIf
		}
		else // backward chaining
		{
			int index = -token.node_merit-1;
			if(!token__list[index].contains(token))
			{
				token__list[index].add(token);
##IfDebug
    /*DEBUG*/for(int i = 0; i < token__list[index].size(); i++) System.out.println("###+ " + token__list[index].get(i)+ " node:" + ((ReteToken)token__list[index].get(i)).node_merit);
    /*DEBUG*/if(token__list[index].size() <= 0) System.out.println("###+ no tokens with merit" + token.node_merit);
##EndIf
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
##IfDebug
					/*DEBUG*/for(int i = 0; i < token_list.size(); i++) System.out.println("###- " + token_list.get(i)+ " node:" + ((ReteToken)token_list.get(i)).node_merit);
					/*DEBUG*/if(token_list.size() <= 0) System.out.println("###- " + "no tokens");
##EndIf
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
##IfDebug
    /*DEBUG*/for(int i = 0; i < token__list[index].size(); i++) System.out.println("###- " + token__list[index].get(i)+ " node:" + ((ReteToken)token_list.get(i)).node_merit);
    /*DEBUG*/if(token__list[index].size() <= 0) System.out.println("###- no tokens with merit" + token.node_merit);
##EndIf

				}
			}
		}
	}

##IfLEX
//Forward Chaining Lex section start
	public static ReteToken GetFiredToken()
	{
		int i = token_list.size();
		int best_node_lexts = 0;
		int best_node_meats = 0;
		int best_node_merit = Integer.MAX_VALUE;
		ReteToken best_token = null;
		if(i <= 0) return null;
// First available
//		return (ReteToken)token_list.get(0);
// last
//		return (ReteToken) token_list.get(i-1);
// Forward chaining Lex:
		for(int j=i-1; j>=0; j--)
		{
			ReteToken token = (ReteToken)token_list.get(j);

			if(token.node_lexts > best_node_lexts)
			{
				best_node_lexts = token.node_lexts;
				best_node_merit = token.node_merit;
				best_token = token;
			}
			else if(token.node_lexts == best_node_lexts)
			{
				if(token.node_merit > best_node_merit) // Merit is negative value
				{
					best_node_merit = token.node_merit;
					best_token = token;
				}
			}
		}
		return best_token;
	}
//Forward Chaining Lex section end
##EndIf
##IfMEA
//Forward Chaining MEA section start
	public static ReteToken GetFiredToken()
	{
		int i = token_list.size();
		int best_node_lexts = 0;
		int best_node_meats = 0;
		int best_node_merit = Integer.MAX_VALUE;
		ReteToken best_token = null;
		if(i <= 0) return null;
// Forward chaining MEA:
		for(int j=i-1; j>=0; j--)
		{
			ReteToken token = (ReteToken)token_list.get(j);

			if(token.node_meats > best_node_meats)
			{
				best_node_meats = token.node_meats;
				best_node_lexts = token.node_lexts;
				best_node_merit = token.node_merit;
				best_token = token;
			}
			else if(token.node_meats == best_node_meats)
			{
				if(token.node_lexts > best_node_lexts)
				{
					best_node_lexts = token.node_lexts;
					best_node_merit = token.node_merit;
					best_token = token;
				}
				else if(token.node_lexts == best_node_lexts)
				{
					if(token.node_merit > best_node_merit) // Merit is negative value
					{
						best_node_merit = token.node_merit;
						best_token = token;
					}
				}
			}
		}
		return best_token;
	}
//Forward Chaining MEA section end
##EndIf

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
##IfDebug
				/*DEBUG*/System.out.println("###R- " + token__list[j].get(i)+ " merit:" + t.node_merit);
##EndIf
			}
		}
	}
}


##SaveTo ReteToken.java
//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  ReteToken.java                    ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================
import ReteObjects.*;

public class ReteToken
{
	public int node_merit = 0; // rule importance according to no. of conditions and rule position
	public int node_meats = 0; // rule importance according to time stamp of object in first condition
	public int node_lexts = 0; // rule importance according to highest time stamp

##WriteTokenArrays
##WriteTokenEvaluateFunction

	public String getReport()
	{
		StringBuffer report = new StringBuffer();
		try
		{

##IfII
			report.append("\tP_H_Ep_total = ");
			report.append(P_H_Ep_total);
			report.append(", P_H_Ep = ");
			report.append(P_H_Ep);
			report.append(", LSp = ");
			report.append(LSp);
			report.append("\n");
##EndIf

			for(int i = 0; i<objectsno; i++)
			{
				report.append("\t\t");
				if(object[i] == null) report.append("[o]");
				else
				{
					report.append(object[i].toString());
					report.append(", timestamp: ");
					report.append(timestamps[i]);
				}
				report.append("\n");
			}
			for(int i = 0; i<variablesno; i++)
			{
				report.append("\t\t");
				if(variable[i] == null) report.append("[.]");
				else report.append(variable[i].toString());
				report.append("\n");
			}
		}
		catch(Throwable e){};
		return report.toString();
	}

	public static ReteToken Join(ReteToken t1, ReteToken t2)
	{
		boolean evaluate = false;
		ReteToken token = new ReteToken();
##IfII
		token.P_H_Ep = (t1.P_H_Ep<t2.P_H_Ep ? t1.P_H_Ep : t2.P_H_Ep);
		token.LSp = t1.LSp * t2.LSp;
##EndIf

		for(int i = 0; i < objectsno; i++)
		{
			if(t1.object[i] == t2.object[i]) {token.object[i] = t1.object[i]; token.timestamps[i] = t1.timestamps[i];}
			else if(t1.object[i] == null) {token.object[i] = t2.object[i]; token.timestamps[i] = t2.timestamps[i];}
			else if(t2.object[i] == null) {token.object[i] = t1.object[i]; token.timestamps[i] = t1.timestamps[i];}
			else {token = null; return null;};
		}
		for(int i = 0; i < variablesno; i++)
		{
			if(t1.typeofvariable[i] == 0 && t2.typeofvariable[i] == 0)
			{
				token.typeofvariable[i] = 0;
			}
			else if(t1.typeofvariable[i] == t2.typeofvariable[i])
			{
				token.typeofvariable[i] = t1.typeofvariable[i];
				if(t1.variable[i] == null && t2.variable[i] == null) token.variable[i] = null;
				else if (t1.variable[i] == null || t2.variable[i] == null) {token = null; return null;};
				if( ((Comparable)(t1.variable[i])).compareTo(t2.variable[i]) == 0 )
	 				token.variable[i] = t1.variable[i];
				else {token = null; return null;};
			}
			else if(t1.typeofvariable[i] == 0)
			{
				token.typeofvariable[i] = t2.typeofvariable[i];
				token.variable[i] = t2.variable[i];
			}
			else if(t2.typeofvariable[i] == 0)
			{
				token.typeofvariable[i] = t1.typeofvariable[i];
				token.variable[i] = t1.variable[i];
			}
			else {token = null; return null;};
		}
		for(int i = 0; i < checksno; i++)
		{
			if(t1.check[i] == 0 && t2.check[i] == 0) token.check[i] = 0;
			else if(t1.check[i] == 2 || t2.check[i] == 2) token.check[i] = 2;
			else {token.check[i] = 1; evaluate = true;};
		}
		if(evaluate)
		{
			if(Evaluate(token)) return token;
			token = null;
			return null;
		}
		else return token;
	}

	Object[] object = new Object[objectsno];
	int[] timestamps = new int[objectsno];
	//	double[] variable = new double[variablesno];
	Object[] variable = new Object[variablesno];
	int[] typeofvariable = new int[variablesno];
##IfII
//Inexact Inferencing section begin
	double P_H_Ep_total = 1.0;
	double P_H_Ep = 1.0;
	double LSp = 1.0;
	//float[] certaintyofvariable = new float[variablesno];
	//float[] LSofvariable = new float[variablesno];
	//float[] LNofvariable = new float[variablesno];

	public void LSp_from_P_H_Ep()
	{
		double O_H_Ep, O_H;
		O_H = 0.1/0.9; //O_H = P_H/(1.0-P_H); P_H = 0.1;

		if(P_H_Ep < 1.0) O_H_Ep = P_H_Ep/(1.0 - P_H_Ep);
		else O_H_Ep = O_H;
		LSp = O_H_Ep/O_H;
	}
//Inexact Inferencing section end
##EndIf
	int[] check = new int[checksno];

//Function used to check if object should be removed
	public boolean IsTokenObsolate(Object modified_object)
	{
		for(int i = 0; i < objectsno; i++)
			if(modified_object == object[i]) return true;
		return false;
	}

// Returns false if there are unevaluated or false checks in token
// Used by rule node
	public boolean Evaluate()
	{
		for(int i = 0; i < checksno; i++)
			if(check[i] == 1 || check[i] == 3) return false;
		return true;
	}

// Clears token
// Used by conditional node
	void ClearToken()
	{
##IfII
//Inexact Inferencing section begin
		P_H_Ep_total = 1.0;
		P_H_Ep = 1.0;
		LSp = 1.0;
//Inexact Inferencing section end

##EndIf
		for(int i = 0; i < objectsno; i++) object[i] = null;
		for(int i = 0; i < variablesno; i++) variable[i] = null;
// 0 - no variable defined;
// 1 - String
// 2 - Integer
// 3 - Double
		for(int i = 0; i < variablesno; i++) typeofvariable[i] = 0;

// 0 - no check used;
// 1 - check used, not evaluated
// 2 - check used, evaluated as true
// 3 - check used, evaluated as false (not possible in network)
		for(int i = 0; i < checksno; i++) check[i] = 0;
	}
};

##SaveTo ReteExecute.java
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

##IfII
	private float ReadLine_Certainty = 5;
##Else
	private float ReadLine_Certainty =10;
##EndIf

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
##IfDebug
		/*DEBUG*/System.out.println("Error setting variable: " + e);
		/*DEBUG*/e.printStackTrace( );
##EndIf
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

##WriteExecutions
	
	public void ExecuteRule(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
##IfII
		Print("P_H_Ep_total = " + token.P_H_Ep_total
			+ "   P_H_Ep = " + token.P_H_Ep 
			+ "   LSp = " + token.LSp + "\n");
##EndIf

		switch(token.node_merit)
		{
##WriteDispatchExecution
		}
	}
}

##SaveTo MemoryServer.idl
module MemoryServer 
{
	 interface WMGetSetObject 
	 {
		void WMSetObject(in any obj, in string name);
		void WMDeleteObject(in string name);
		void WMGetObject(in long tin, out long tmax, out string on, out any obj);
		void WMWaitForChange(in long ti);
		void WMGetItem(in long index, out string name);
	};
};

##SaveTo ReteNetwork.java
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

##WriteNetworkNodes
##IfPEM

##WriteServerArgs
##EndIf

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
##IfPEM
		rn.ConnectMemory(servargs);
##Else
		rn.wmserverinterface = null;
		System.out.println("Inferencing with transient memory.");
##EndIf

		rn.StartInferencing();
	}

	public static void WinMain(Expert expert) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		ReteNetwork rn= new ReteNetwork();
		rn.expert = expert;
		if(expert!=null) expert.rn = rn;
		rn.CreateNetwork();
##IfPEM
		rn.ConnectMemory(servargs);
##Else
		rn.wmserverinterface = null;
		if(expert != null) expert.PrintMessage("Inferencing with transient memory.");
##EndIf

##IfII
		if(expert != null) expert.Certainty =  5.0;
##Else
		if(expert != null) expert.Certainty = 10.0;
##EndIf
		rn.StartInferencing();
	}

	public void Stop()
	{
		stop = true;
		Thread.yield();
		Thread.yield();
	}
##IfBC
	public void SetAllTransparent()
	{
##WriteSetTransparent
	}
##EndIf
	
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
##IfDebug
		/*DEBUG*/ System.out.println("Feeding network at: " + time_stamp + " with: " + obj.toString() +"name" + obj.getClass().getName());
##EndIf

##WriteFeedingSlots
		return false;
	}

	public void UnFeedNetwork(Object obj)
	{
		ReteConflictSet.RemoveTokensWithObsoleteObjects(obj);

##WriteUnFeedingSlots
	}

	public int CreateNetwork()
	{
##WriteNetworkConnections
		return 0;
	}

	public void BuildTree(javax.swing.JTree tree)
	{
		javax.swing.tree.DefaultTreeModel model = (javax.swing.tree.DefaultTreeModel)tree.getModel();
		javax.swing.tree.DefaultMutableTreeNode treeNode_0 = new javax.swing.tree.DefaultMutableTreeNode("Conflict Set");
		model.setRoot(treeNode_0);
##WriteNetworkTree

		model.reload();
	}

##IfBC
	public void BackwardChainingSeq(ReteExecute executions)throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
##WriteBackwardChaining

		SetAllTransparent();
	}

##EndIf
	public void StartInferencing() throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind
	{
		ReteToken token;
		executions = new ReteExecute(this, expert); 

##IfDebug
		/*DEBUG*/ System.out.println("----Starting Inferencing");
##EndIf

##IfBC
//Backward Chaining section start
##WriteStarter
		BackwardChainingSeq(executions);
//Backward Chaining section end
##Else
//Forward Chaining section start
//		SetAllTransparent();
		ReteConflictSet.ForwardMode();
##WriteStarter
		do
		{
//				if(wmserverinterface!=null)	LoadObjectsFromWM();
			token = ReteConflictSet.GetFiredToken();
			if(token != null)
			{
				ReteConflictSet.RemoveTokensFromFiredRule(token);
				executions.ExecuteRule(token);
				token = null;
			}
			else if(wmserverinterface!=null) LoadObjectsFromWM(true);
		}
//		while(true);
//		while((token != null) && (!stop));
		while(!stop);
//Forward Chaining section end
##EndIf

##IfDebug
		/*DEBUG*/ System.out.println("----Inferencing stopped");
##EndIf

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
##IfDebug
			/*DEBUG*/for( i = 0; i < ListOfNames.size(); i++) System.out.println("***+" + ListOfObjects.get(i) + " : " + ListOfNames.get(i));
##EndIf
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
##WriteConvertToAny
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

##WriteConvertFromAny

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

##SaveTo Expert.java
//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  Expert.java                       ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================
import MemoryServer.*;
import ReteObjects.*;
/**
 *
 * @author  pms
 */
public class Expert extends javax.swing.JFrame {
		private boolean reading = false;
		private CheckMemoryThread ithread = null;
		public ReteNetwork rn = null;
    
    public String Question = new String();
    public String Answer = new String();
    public double Certainty;
    
    private synchronized void SetReading(boolean r)
		{
			reading = r;
			notifyAll();
    }
		public void Print(String string)
		{
        jTextArea1.append(string);
		}
		public void PrintMessage(String string)
		{
				jTextField2.setText(string);
		}
    
		public String Read()
		{
				reading = true;
        if(Certainty > 5 || Certainty < -5)
        {
            jSlider1.setVisible(false);
            jTextField3.setVisible(false);
        }    
        else
        {
            jSlider1.setValue((int)(100 * Certainty));
            jSlider1.setVisible(true);
            jTextField3.setVisible(true);        
            jSlider1StateChanged(null);
        }
        jTextField1.setText("");
        jTextArea2.setText(Question);
        jDialogQuery.setVisible(true);
        while(reading == true) 
				{
					try{Thread.yield();}
					catch(Throwable e){}
				}

        if(Certainty > 5 || Certainty < -5)
            Print(Answer + "\r\n");
        else 
            Print(Answer + " {Certainty = " + Certainty + "}\r\n");
				return Answer;
		}
  
		public void Display(String filename)
		{
        javax.swing.ImageIcon ii = new javax.swing.ImageIcon (filename);
        
        java.awt.Rectangle rr = jFrameDisplay.getBounds();
        java.awt.Rectangle r2 = jScrollPane1.getBounds();
        
        jFrameDisplay.setTitle(filename);
        jLabel1.setIcon(ii);
        
        jFrameDisplay.setBounds(rr.x, rr.y, //ii.getIconWidth() + 24, ii.getIconHeight() + 36);  
            rr.width + ii.getIconWidth() - r2.width, 
            rr.height + ii.getIconHeight() - r2.height);
        jFrameDisplay.setVisible(true);
		}
 
		/** Creates new form Expert */
		public Expert() {
				initComponents();
		}

		/** This method is called from within the constructor to
		 * initialize the form.
		 * WARNING: Do NOT modify this code. The content of this method is
		 * always regenerated by the Form Editor.
		 */
    private void initComponents() {//GEN-BEGIN:initComponents
        jFrameDisplay = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jDialogQuery = jDialogQuery = new javax.swing.JDialog(this);
        jDialogQuery.setBounds(10, 380, 320, 160);
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jTextField3 = new javax.swing.JTextField();
        jButtonOK = new javax.swing.JButton();
        jFrameTree = new javax.swing.JFrame();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemDisplay = new javax.swing.JMenuItem();
        jMenuItemNet = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemExit = new javax.swing.JMenuItem();

        jFrameDisplay.setTitle("Display");
        jFrameDisplay.setLocationRelativeTo(this);
        jScrollPane1.setBorder(null);
        jScrollPane1.setAutoscrolls(true);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setAutoscrolls(true);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setOpaque(true);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jScrollPane1.setViewportView(jLabel1);

        jFrameDisplay.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jDialogQuery.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialogQuery.setTitle("Query");
        jDialogQuery.setLocationRelativeTo(this);
        jDialogQuery.setResizable(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jPanel2.add(jTextField1, java.awt.BorderLayout.SOUTH);

        jTextArea2.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea2.setEditable(false);
        jTextArea2.setLineWrap(true);
        jTextArea2.setToolTipText("Select desired text and copy it to answer field by clicking right mouse button");
        jTextArea2.setSelectionColor(new java.awt.Color(255, 255, 255));
        jTextArea2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea2MouseClicked(evt);
            }
        });

        jPanel2.add(jTextArea2, java.awt.BorderLayout.CENTER);

        jDialogQuery.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jSlider1.setFont(new java.awt.Font("Dialog", 0, 8));
        jSlider1.setMajorTickSpacing(100);
        jSlider1.setMaximum(500);
        jSlider1.setMinimum(-500);
        jSlider1.setMinorTickSpacing(50);
        jSlider1.setPaintTicks(true);
        jSlider1.setValue(500);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jPanel3.add(jSlider1, java.awt.BorderLayout.CENTER);

        jTextField3.setBackground(new java.awt.Color(204, 204, 204));
        jTextField3.setEditable(false);
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField3.setText("5.00");
        jTextField3.setBorder(null);
        jTextField3.setMinimumSize(new java.awt.Dimension(20, 16));
        jTextField3.setPreferredSize(new java.awt.Dimension(50, 19));
        jPanel3.add(jTextField3, java.awt.BorderLayout.WEST);

        jButtonOK.setFont(new java.awt.Font("Dialog", 1, 14));
        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        jPanel3.add(jButtonOK, java.awt.BorderLayout.EAST);

        jDialogQuery.getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        jFrameTree.setTitle("Network");
        jSplitPane1.setMinimumSize(new java.awt.Dimension(200, 220));
        jScrollPane3.setBorder(null);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(0, 0));
        jTree1.setBackground(new java.awt.Color(224, 255, 204));
        jTree1.setShowsRootHandles(true);
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });

        jScrollPane3.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane3);

        jScrollPane4.setBorder(null);
        jTextArea3.setBackground(new java.awt.Color(224, 255, 204));
        jTextArea3.setEditable(false);
        jTextArea3.setTabSize(2);
        jTextArea3.setBorder(null);
        jTextArea3.setAutoscrolls(false);
        jScrollPane4.setViewportView(jTextArea3);

        jSplitPane1.setRightComponent(jScrollPane4);

        jFrameTree.getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        setTitle("Expert");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jTextField2.setBackground(new java.awt.Color(204, 204, 204));
        jTextField2.setFont(new java.awt.Font("Dialog", 1, 10));
        jTextField2.setText("Transient Memory");
        jTextField2.setBorder(null);
        getContentPane().add(jTextField2, java.awt.BorderLayout.SOUTH);

        jTextArea1.setBackground(new java.awt.Color(255, 255, 204));
        jTextArea1.setTabSize(2);
        jTextArea1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jTextArea1ComponentResized(evt);
            }
        });

        jScrollPane2.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jMenu2.setText("File");
        jMenuItemDisplay.setText("Show display");
        jMenuItemDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDisplayActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemDisplay);

        jMenuItemNet.setText("Show network");
        jMenuItemNet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNetActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemNet);

        jMenu2.add(jSeparator1);

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemExit);

        jMenuBar2.add(jMenu2);

        setJMenuBar(jMenuBar2);

        pack();
    }//GEN-END:initComponents

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        if(evt.getButton() == 1)
        {
                jTree1.setSelectionRow(jTree1.getRowForLocation(evt.getX(), evt.getY()));
                javax.swing.tree.DefaultMutableTreeNode node = 
                    (javax.swing.tree.DefaultMutableTreeNode)(jTree1.getLastSelectedPathComponent());
                if(node == null) return;
                if(node.isRoot()) jTextArea3.setText(ReteConflictSet.getReport());
                else jTextArea3.setText(((ReteNode)(node.getUserObject())).getReport());
        }

        else if(evt.getButton() == 3)
        {
            jTextArea3.setText("-- Inferencing thread information:\n");
						jTextArea3.append("\t" + ithread.toString() + "\n");
						jTextArea3.append("\tName = " + ithread.getName() + "\n");
						jTextArea3.append("\tPriority = " + ithread.getPriority() + "\n");
						jTextArea3.append("\tActive threads = " + ithread.activeCount() + "\n");
            jTextArea3.append("\n-- Working memory buffer:\n");
						if(rn != null)jTextArea3.append(rn.listOfMemoryObjects());
        }

    }//GEN-LAST:event_jTree1MouseClicked

    private void jTextArea1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTextArea1ComponentResized
        jScrollPane2.getVerticalScrollBar().setValue(
            (int)jScrollPane2.getVerticalScrollBar().getMaximumSize().getHeight()
        );
    }//GEN-LAST:event_jTextArea1ComponentResized

    private void jTextArea2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea2MouseClicked
        if(evt.getButton() == 3)
        {
            jTextField1.setText(jTextArea2.getSelectedText());
        }
    }//GEN-LAST:event_jTextArea2MouseClicked

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        int val = jSlider1.getValue();
        if(val < 0)
            jTextField3.setText("-" + (-val/100) + "." + ((-val)%100)/10 + "" + ((-val)%10));
        else
            jTextField3.setText("" + (val/100) + "." + ((val)%100)/10 + "" + ((val)%10));
    }//GEN-LAST:event_jSlider1StateChanged

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jMenuItemNetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNetActionPerformed
        rn.BuildTree(jTree1);
        jFrameTree.setVisible(true);
    }//GEN-LAST:event_jMenuItemNetActionPerformed

    private void jMenuItemDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDisplayActionPerformed
        jFrameDisplay.setVisible(true);
    }//GEN-LAST:event_jMenuItemDisplayActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        if(Certainty <= 5 && Certainty >= -5)
        {
            Certainty = (new Double(jTextField3.getText())).doubleValue();
        }
        Answer = jTextField1.getText();
        jDialogQuery.setVisible(false);
        SetReading(false);
    }//GEN-LAST:event_jButtonOKActionPerformed

		private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
				if(evt.getKeyCode()== evt.VK_ENTER) jButtonOKActionPerformed(null);
		}//GEN-LAST:event_jTextField1KeyPressed

		/** Exit the Application */
		private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
		if(rn != null)
		{
			rn.Stop();
		}
		System.exit(0);

		}//GEN-LAST:event_exitForm
		/**
		* @param args the command line arguments
		*/
		public static void main(String args[]) 
		{
			Expert expert = new Expert();
      expert.jFrameTree.setBounds(240, 10, 380, 400);
      expert.setBounds(10, 10, 380, 450);
			expert.jTextField2.setText("Starting system.");
			expert.setVisible(true); //show();
      
			expert.Start();
		}

		public void Start() 
		{
			try
			{
				ithread = new CheckMemoryThread(this);
				ithread.setDaemon(true);
				ithread.start();
			}
			catch(Throwable e)
			{
				jTextField2.setText("Cannot start new thread");
			}
		}

		class CheckMemoryThread extends Thread
		{
			Expert ex;
			public CheckMemoryThread(Expert exp)
			{
				ex = exp;
			}

			public void run()
			{
				int ee = 0;
				jTextField2.setText("Starting system..");
				try 
				{
					ReteNetwork.WinMain(ex);
					jTextField2.setText("Inferencing stopped");
				} 
				catch(Throwable e)
				{
					jTextField2.setText("Cannot start inferencing");
				};
			}
		}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonOK;
    private javax.swing.JDialog jDialogQuery;
    private javax.swing.JFrame jFrameDisplay;
    private javax.swing.JFrame jFrameTree;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItemDisplay;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemNet;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
##SaveTo Expert.form
<?xml version="1.0" encoding="UTF-8" ?>

<Form version="1.0" type="org.netbeans.modules.form.forminfo.JFrameFormInfo">
  <NonVisualComponents>
    <Container class="javax.swing.JFrame" name="jFrameDisplay">
      <Properties>
        <Property name="title" type="java.lang.String" value="Display"/>
        <Property name="locationRelativeTo" type="java.awt.Component" editor="org.netbeans.modules.form.ComponentChooserEditor">
          <ComponentRef name="Form"/>
        </Property>
      </Properties>

      <Layout class="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout"/>
      <SubComponents>
        <Container class="javax.swing.JScrollPane" name="jScrollPane1">
          <Properties>
            <Property name="border" type="javax.swing.border.Border" editor="org.netbeans.modules.form.editors2.BorderEditor">
              <Border info="null"/>
            </Property>
            <Property name="autoscrolls" type="boolean" value="true"/>
          </Properties>
          <Constraints>
            <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
              <BorderConstraints direction="Center"/>
            </Constraint>
          </Constraints>

          <Layout class="org.netbeans.modules.form.compat2.layouts.support.JScrollPaneSupportLayout"/>
          <SubComponents>
            <Component class="javax.swing.JLabel" name="jLabel1">
              <Properties>
                <Property name="horizontalAlignment" type="int" value="2"/>
                <Property name="verticalAlignment" type="int" value="1"/>
                <Property name="alignmentX" type="float" value="0.5"/>
                <Property name="autoscrolls" type="boolean" value="true"/>
                <Property name="horizontalTextPosition" type="int" value="2"/>
                <Property name="opaque" type="boolean" value="true"/>
                <Property name="verticalTextPosition" type="int" value="1"/>
              </Properties>
            </Component>
          </SubComponents>
        </Container>
      </SubComponents>
    </Container>
    <Container class="javax.swing.JDialog" name="jDialogQuery">
      <Properties>
        <Property name="defaultCloseOperation" type="int" value="0"/>
        <Property name="title" type="java.lang.String" value="Query"/>
        <Property name="locationRelativeTo" type="java.awt.Component" editor="org.netbeans.modules.form.ComponentChooserEditor">
          <ComponentRef name="Form"/>
        </Property>
        <Property name="resizable" type="boolean" value="false"/>
      </Properties>
      <AuxValues>
        <AuxValue name="JavaCodeGenerator_CreateCodePost" type="java.lang.String" value="jDialogQuery.setBounds(10, 380, 320, 160);"/>
        <AuxValue name="JavaCodeGenerator_CreateCodeCustom" type="java.lang.String" value="jDialogQuery = new javax.swing.JDialog(this);"/>
      </AuxValues>

      <Layout class="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout"/>
      <SubComponents>
        <Container class="javax.swing.JPanel" name="jPanel2">
          <Constraints>
            <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
              <BorderConstraints direction="Center"/>
            </Constraint>
          </Constraints>

          <Layout class="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout"/>
          <SubComponents>
            <Component class="javax.swing.JTextField" name="jTextField1">
              <Events>
                <EventHandler event="keyPressed" listener="java.awt.event.KeyListener" parameters="java.awt.event.KeyEvent" handler="jTextField1KeyPressed"/>
              </Events>
              <Constraints>
                <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
                  <BorderConstraints direction="South"/>
                </Constraint>
              </Constraints>
            </Component>
            <Component class="javax.swing.JTextArea" name="jTextArea2">
              <Properties>
                <Property name="background" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
                  <Color blue="cc" green="cc" red="cc" type="rgb"/>
                </Property>
                <Property name="editable" type="boolean" value="false"/>
                <Property name="lineWrap" type="boolean" value="true"/>
                <Property name="toolTipText" type="java.lang.String" value="Select desired text and copy it to answer field by clicking right mouse button"/>
                <Property name="selectionColor" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
                  <Color blue="ff" green="ff" red="ff" type="rgb"/>
                </Property>
              </Properties>
              <Events>
                <EventHandler event="mouseClicked" listener="java.awt.event.MouseListener" parameters="java.awt.event.MouseEvent" handler="jTextArea2MouseClicked"/>
              </Events>
              <Constraints>
                <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
                  <BorderConstraints direction="Center"/>
                </Constraint>
              </Constraints>
            </Component>
          </SubComponents>
        </Container>
        <Container class="javax.swing.JPanel" name="jPanel3">
          <Constraints>
            <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
              <BorderConstraints direction="South"/>
            </Constraint>
          </Constraints>

          <Layout class="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout"/>
          <SubComponents>
            <Component class="javax.swing.JSlider" name="jSlider1">
              <Properties>
                <Property name="font" type="java.awt.Font" editor="org.netbeans.beaninfo.editors.FontEditor">
                  <Font name="Dialog" size="8" style="0"/>
                </Property>
                <Property name="majorTickSpacing" type="int" value="100"/>
                <Property name="maximum" type="int" value="500"/>
                <Property name="minimum" type="int" value="-500"/>
                <Property name="minorTickSpacing" type="int" value="50"/>
                <Property name="paintTicks" type="boolean" value="true"/>
                <Property name="value" type="int" value="500"/>
              </Properties>
              <Events>
                <EventHandler event="stateChanged" listener="javax.swing.event.ChangeListener" parameters="javax.swing.event.ChangeEvent" handler="jSlider1StateChanged"/>
              </Events>
              <Constraints>
                <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
                  <BorderConstraints direction="Center"/>
                </Constraint>
              </Constraints>
            </Component>
            <Component class="javax.swing.JTextField" name="jTextField3">
              <Properties>
                <Property name="background" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
                  <Color blue="cc" green="cc" red="cc" type="rgb"/>
                </Property>
                <Property name="editable" type="boolean" value="false"/>
                <Property name="horizontalAlignment" type="int" value="4"/>
                <Property name="text" type="java.lang.String" value="5.00"/>
                <Property name="border" type="javax.swing.border.Border" editor="org.netbeans.modules.form.editors2.BorderEditor">
                  <Border info="null"/>
                </Property>
                <Property name="minimumSize" type="java.awt.Dimension" editor="org.netbeans.beaninfo.editors.DimensionEditor">
                  <Dimension value="[20, 16]"/>
                </Property>
                <Property name="preferredSize" type="java.awt.Dimension" editor="org.netbeans.beaninfo.editors.DimensionEditor">
                  <Dimension value="[50, 19]"/>
                </Property>
              </Properties>
              <Constraints>
                <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
                  <BorderConstraints direction="West"/>
                </Constraint>
              </Constraints>
            </Component>
            <Component class="javax.swing.JButton" name="jButtonOK">
              <Properties>
                <Property name="font" type="java.awt.Font" editor="org.netbeans.beaninfo.editors.FontEditor">
                  <Font name="Dialog" size="14" style="1"/>
                </Property>
                <Property name="text" type="java.lang.String" value="OK"/>
              </Properties>
              <Events>
                <EventHandler event="actionPerformed" listener="java.awt.event.ActionListener" parameters="java.awt.event.ActionEvent" handler="jButtonOKActionPerformed"/>
              </Events>
              <Constraints>
                <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
                  <BorderConstraints direction="East"/>
                </Constraint>
              </Constraints>
            </Component>
          </SubComponents>
        </Container>
      </SubComponents>
    </Container>
    <Container class="javax.swing.JFrame" name="jFrameTree">
      <Properties>
        <Property name="title" type="java.lang.String" value="Network"/>
      </Properties>

      <Layout class="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout"/>
      <SubComponents>
        <Container class="javax.swing.JSplitPane" name="jSplitPane1">
          <Properties>
            <Property name="minimumSize" type="java.awt.Dimension" editor="org.netbeans.beaninfo.editors.DimensionEditor">
              <Dimension value="[200, 220]"/>
            </Property>
          </Properties>
          <Constraints>
            <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
              <BorderConstraints direction="Center"/>
            </Constraint>
          </Constraints>

          <Layout class="org.netbeans.modules.form.compat2.layouts.support.JSplitPaneSupportLayout"/>
          <SubComponents>
            <Container class="javax.swing.JScrollPane" name="jScrollPane3">
              <Properties>
                <Property name="border" type="javax.swing.border.Border" editor="org.netbeans.modules.form.editors2.BorderEditor">
                  <Border info="null"/>
                </Property>
                <Property name="preferredSize" type="java.awt.Dimension" editor="org.netbeans.beaninfo.editors.DimensionEditor">
                  <Dimension value="[0, 0]"/>
                </Property>
              </Properties>
              <Constraints>
                <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.support.JSplitPaneSupportLayout" value="org.netbeans.modules.form.compat2.layouts.support.JSplitPaneSupportLayout$JSplitPaneConstraintsDescription">
                  <JSplitPaneConstraints position="left"/>
                </Constraint>
              </Constraints>

              <Layout class="org.netbeans.modules.form.compat2.layouts.support.JScrollPaneSupportLayout"/>
              <SubComponents>
                <Component class="javax.swing.JTree" name="jTree1">
                  <Properties>
                    <Property name="background" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
                      <Color blue="cc" green="ff" red="e0" type="rgb"/>
                    </Property>
                    <Property name="showsRootHandles" type="boolean" value="true"/>
                  </Properties>
                  <Events>
                    <EventHandler event="mouseClicked" listener="java.awt.event.MouseListener" parameters="java.awt.event.MouseEvent" handler="jTree1MouseClicked"/>
                  </Events>
                </Component>
              </SubComponents>
            </Container>
            <Container class="javax.swing.JScrollPane" name="jScrollPane4">
              <Properties>
                <Property name="border" type="javax.swing.border.Border" editor="org.netbeans.modules.form.editors2.BorderEditor">
                  <Border info="null"/>
                </Property>
              </Properties>
              <Constraints>
                <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.support.JSplitPaneSupportLayout" value="org.netbeans.modules.form.compat2.layouts.support.JSplitPaneSupportLayout$JSplitPaneConstraintsDescription">
                  <JSplitPaneConstraints position="right"/>
                </Constraint>
              </Constraints>

              <Layout class="org.netbeans.modules.form.compat2.layouts.support.JScrollPaneSupportLayout"/>
              <SubComponents>
                <Component class="javax.swing.JTextArea" name="jTextArea3">
                  <Properties>
                    <Property name="background" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
                      <Color blue="cc" green="ff" red="e0" type="rgb"/>
                    </Property>
                    <Property name="editable" type="boolean" value="false"/>
                    <Property name="tabSize" type="int" value="2"/>
                    <Property name="border" type="javax.swing.border.Border" editor="org.netbeans.modules.form.editors2.BorderEditor">
                      <Border info="null"/>
                    </Property>
                    <Property name="autoscrolls" type="boolean" value="false"/>
                  </Properties>
                </Component>
              </SubComponents>
            </Container>
          </SubComponents>
        </Container>
      </SubComponents>
    </Container>
    <Container class="javax.swing.JScrollPane" name="jScrollPane5">

      <Layout class="org.netbeans.modules.form.compat2.layouts.support.JScrollPaneSupportLayout"/>
    </Container>
    <Menu class="javax.swing.JMenuBar" name="jMenuBar2">
      <SubComponents>
        <Menu class="javax.swing.JMenu" name="jMenu2">
          <Properties>
            <Property name="text" type="java.lang.String" value="File"/>
          </Properties>
          <SubComponents>
            <MenuItem class="javax.swing.JMenuItem" name="jMenuItemDisplay">
              <Properties>
                <Property name="text" type="java.lang.String" value="Show display"/>
              </Properties>
              <Events>
                <EventHandler event="actionPerformed" listener="java.awt.event.ActionListener" parameters="java.awt.event.ActionEvent" handler="jMenuItemDisplayActionPerformed"/>
              </Events>
            </MenuItem>
            <MenuItem class="javax.swing.JMenuItem" name="jMenuItemNet">
              <Properties>
                <Property name="text" type="java.lang.String" value="Show network"/>
              </Properties>
              <Events>
                <EventHandler event="actionPerformed" listener="java.awt.event.ActionListener" parameters="java.awt.event.ActionEvent" handler="jMenuItemNetActionPerformed"/>
              </Events>
            </MenuItem>
            <MenuItem class="javax.swing.JSeparator" name="jSeparator1">
            </MenuItem>
            <MenuItem class="javax.swing.JMenuItem" name="jMenuItemExit">
              <Properties>
                <Property name="text" type="java.lang.String" value="Exit"/>
              </Properties>
              <Events>
                <EventHandler event="actionPerformed" listener="java.awt.event.ActionListener" parameters="java.awt.event.ActionEvent" handler="jMenuItemExitActionPerformed"/>
              </Events>
            </MenuItem>
          </SubComponents>
        </Menu>
      </SubComponents>
    </Menu>
  </NonVisualComponents>
  <Properties>
    <Property name="title" type="java.lang.String" value="Expert"/>
  </Properties>
  <SyntheticProperties>
    <SyntheticProperty name="menuBar" type="java.lang.String" value="jMenuBar2"/>
    <SyntheticProperty name="formSizePolicy" type="int" value="1"/>
  </SyntheticProperties>
  <Events>
    <EventHandler event="windowClosing" listener="java.awt.event.WindowListener" parameters="java.awt.event.WindowEvent" handler="exitForm"/>
  </Events>
  <AuxValues>
    <AuxValue name="designerSize" type="java.awt.Dimension" value="-84,-19,0,5,115,114,0,18,106,97,118,97,46,97,119,116,46,68,105,109,101,110,115,105,111,110,65,-114,-39,-41,-84,95,68,20,2,0,2,73,0,6,104,101,105,103,104,116,73,0,5,119,105,100,116,104,120,112,0,0,0,-72,0,0,1,121"/>
  </AuxValues>

  <Layout class="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout"/>
  <SubComponents>
    <Component class="javax.swing.JTextField" name="jTextField2">
      <Properties>
        <Property name="background" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
          <Color blue="cc" green="cc" red="cc" type="rgb"/>
        </Property>
        <Property name="font" type="java.awt.Font" editor="org.netbeans.beaninfo.editors.FontEditor">
          <Font name="Dialog" size="10" style="1"/>
        </Property>
        <Property name="text" type="java.lang.String" value="Transient Memory"/>
        <Property name="border" type="javax.swing.border.Border" editor="org.netbeans.modules.form.editors2.BorderEditor">
          <Border info="null"/>
        </Property>
      </Properties>
      <Constraints>
        <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
          <BorderConstraints direction="South"/>
        </Constraint>
      </Constraints>
    </Component>
    <Container class="javax.swing.JScrollPane" name="jScrollPane2">
      <Constraints>
        <Constraint layoutClass="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout" value="org.netbeans.modules.form.compat2.layouts.DesignBorderLayout$BorderConstraintsDescription">
          <BorderConstraints direction="Center"/>
        </Constraint>
      </Constraints>

      <Layout class="org.netbeans.modules.form.compat2.layouts.support.JScrollPaneSupportLayout"/>
      <SubComponents>
        <Component class="javax.swing.JTextArea" name="jTextArea1">
          <Properties>
            <Property name="background" type="java.awt.Color" editor="org.netbeans.beaninfo.editors.ColorEditor">
              <Color blue="cc" green="ff" red="ff" type="rgb"/>
            </Property>
            <Property name="tabSize" type="int" value="2"/>
          </Properties>
          <Events>
            <EventHandler event="componentResized" listener="java.awt.event.ComponentListener" parameters="java.awt.event.ComponentEvent" handler="jTextArea1ComponentResized"/>
          </Events>
        </Component>
      </SubComponents>
    </Container>
  </SubComponents>
</Form>
