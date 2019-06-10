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
	private boolean hold = true; //hold prevents sending tokens further when BC is used.

	public void SetTransparent()
	{
		if(hold)
		{
			hold = false;
			int kk = output_tokens.size();
			int ii = output_node.size();
			for(int k=0; k<kk; k++)
				for(int i=0; i<ii; i++)
					((ReteNode)output_node.get(i)).InputChange(this, (ReteToken)output_tokens.get(k));
		}
	}

	public void InputCondition(ReteToken input)
	{
		output_tokens.add(input);
		
		if(!hold) for(int i=0; i<output_node.size(); i++)
			((ReteNode)output_node.get(i)).InputChange(this, input);
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

