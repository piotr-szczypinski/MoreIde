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


//----------------- Token array sizes -----------------
	static final int objectsno = 1;
	static final int variablesno = 2;
	static final int checksno = 1;

//----------------- Evaluate function -----------------
	private static boolean Evaluate0(Integer var, ReteToken token)
	{
		if(var == null) return false;
		if(var.doubleValue() == ((Number)(token.variable[0])).doubleValue()*2-1) return true;
		return false;
	}
	private static boolean Evaluate0(int var, ReteToken token)
	{
		if((double)var == ((Number)(token.variable[0])).doubleValue()*2-1) return true;
		return false;
	}
	private static boolean Evaluate0(Double var, ReteToken token)
	{
		if(var == null) return false;
		if(var.doubleValue() == ((Number)(token.variable[0])).doubleValue()*2-1) return true;
		return false;
	}
	private static boolean Evaluate0(double var, ReteToken token)
	{
		if(var == ((Number)(token.variable[0])).doubleValue()*2-1) return true;
		return false;
	}

	private static boolean Evaluate(ReteToken token)
	{
		if(token.check[0]==1)
			if((token.typeofvariable[0]!=0))
			{
				if(Evaluate0(((container)token.object[0]).variable2, token))
				{
					token.check[0] = 2;
				}
				else
				{
					token.check[0] = 3;
					return false;
				}
			}
		return true;
	};

	public static ReteToken Join(ReteToken t1, ReteToken t2)
	{
		boolean evaluate = false;
		ReteToken token = new ReteToken();
		token.P_H_Ep = (t1.P_H_Ep<t2.P_H_Ep ? t1.P_H_Ep : t2.P_H_Ep);
		token.LSp = t1.LSp * t2.LSp;

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
//Inexact Inferencing section begin
		P_H_Ep_total = 1.0;
		P_H_Ep = 1.0;
		LSp = 1.0;
//Inexact Inferencing section end

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

