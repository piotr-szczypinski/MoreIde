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
	static final int variablesno = 0;
	static final int checksno = 0;




//----------------- Evaluate function -----------------
	private static boolean Evaluate(ReteToken token)
	{
		return true;
	};

    public static ReteToken Join(ReteToken t1, ReteToken t2)
    {
        boolean evaluate = false;
        ReteToken token = new ReteToken();

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

			/*
            if(t1.variable[i] == t2.variable[i]) token.variable[i] = t1.variable[i];
            else if(Double.isNaN(t1.variable[i])) token.variable[i] = t2.variable[i];
            else if(Double.isNaN(t2.variable[i])) token.variable[i] = t1.variable[i];
            else {token = null; return null;};*/
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

