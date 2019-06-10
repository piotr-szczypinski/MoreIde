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

//------------------------------------------------------------------------------
	ReteNode()
	{
		input_node0 = null;
		input_node1 = null;
		output_node = new ArrayList();
		output_tokens = new ArrayList();
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

