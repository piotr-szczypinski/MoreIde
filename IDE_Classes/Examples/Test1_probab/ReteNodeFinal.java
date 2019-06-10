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

	ReteNodeFinal(int i, int o)
	{
		node_merit = i;
		objc_merit = o;
	}

	public void InputChange(Object calling_node, ReteToken input)
	{
		if(input_node0 == calling_node)
		{
			double O_H_Ep_total, O_H;
			O_H = 0.1/0.9; //O_H = P_H/(1.0-P_H); P_H = 0.1;
			O_H_Ep_total = O_H * input.LSp;
			if(O_H_Ep_total >= 0)
				input.P_H_Ep_total = O_H_Ep_total/(1.0+O_H_Ep_total);
			else input.P_H_Ep_total = -2.0;

			if(input.Evaluate())
			{

				input.node_merit = node_merit;
				input.node_meats = input.timestamps[objc_merit];
				
				int ii = 0;
				for(int i = 0; i < input.objectsno; i++)
					if(ii < input.timestamps[i] && input.object[i] != null) ii = input.timestamps[i];
				input.node_lexts = ii;
				ReteConflictSet.FireRule(input);
			}
		}
		else
		{
			return;
		}
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
    //System.out.println("Node merit: " + node_merit + ", token removed, tokens firing the node: " + ((ReteNode)input_node0).GetToken());
		//System.out.println("Token removed");
		//System.out.println("No. of tokens firing the node: " + ((ReteNode)input_node0).GetToken());
	}
}

