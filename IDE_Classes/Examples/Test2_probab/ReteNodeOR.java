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
	public void InputChange(Object calling_node, ReteToken input)
	{
		/*DEBUG*/System.out.println("In OR>>>");
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

