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
	public void InputChange(Object calling_node, ReteToken input)
	{

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
					for(int j=0; j<output_node.size(); j++)
						((ReteNode)output_node.get(j)).InputChange(this, token);
				}
			}
		}
		else
		{
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

