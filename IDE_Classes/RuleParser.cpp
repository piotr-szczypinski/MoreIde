//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  JavaRETE/MORE                     ===*===*====*=*===*=*====*====*=========
//==  RuleParser source for JavaCC      ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================

PARSER_BEGIN(RuleParser)

import java.util.*;
import java.io.*;
import java.lang.*;

//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  JavaRETE/MORE                     ===*===*====*=*===*=*====*====*=========
//==  RuleParser source                 ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================

// Parser class definition
public class RuleParser
{
	public static void main(String args[]) throws ParseException, IOException
/*
	{
		System.out.println("TREE: ============================");
		RuleParser parser = new RuleParser(System.in);
		PrintTree(parser.StartTree(), 0);
	}
*/
	{
		System.out.println("\nPARSING WARNINGS: ============================");

		RuleParser parser = new RuleParser(System.in);
		parser.Start();

//		System.out.println("No. of rules: " + rules_count);
//		System.out.println("No. of conds: " + cond_blocks);
		System.out.println("\nPARSING RESULTS: =============================");

		System.out.println("\n----Conditions: reduced list --------------------");
		for(int i=0; i< reduced_list.size(); i++)
			System.out.println((i+1)+"\t"+reduced_list.get(i).toString());


		System.out.println("\n----Variables	 ------------------------------");
		for(int i=0; i< aliasname_list.size(); i++)
			System.out.println((i+1) + "\t"+aliasname_list.get(i).toString());//+" -> "+alias_list.get(i).toString());

		System.out.println("\n----Conditions	------------------------------");
		System.out.println("index of condition : object in token : -check or variable index");
		for(int i=0; i< tokensender_list.size(); i++)
			System.out.println((i+1)+"\t"+tokensender_list.get(i).toString()
				+"\t"+slot_list.get(i).toString());

		System.out.println("\n----objects sent------------------------------");
		for(int i=0; i< slot_list.size(); i++)
			System.out.println((i+1)+"\t"+slot_list.get(i).toString());

/*
		System.out.println("\n----checks at token sent list-----------------");
		for(int i=0; i< anyalias_list.size(); i++)
			System.out.print(anyalias_list.get(i).toString()+"\t");
*/
		System.out.println("\n----Nodes		 --------------------------------");
		for(int i=0; i< node_list.size(); i++)
			System.out.println((i+1)+"\t"+node_list.get(i).toString());

		System.out.println("\n----Rules			-------------------------------");
		for(int i=0; i< rule_list.size(); i++)
			System.out.println((i+1)+"\t"+rule_list.get(i).toString());

		System.out.println("\n----RulesVIR	 -------------------------------");
		for(int i=0; i< RulesVIR_List.size(); i++)
		{
			System.out.print((i+1)+"\t");
			ArrayList ar = (ArrayList)RulesVIR_List.get(i);
			for(int j=0; j< ar.size(); j++)
			{
				System.out.print(" " + ar.get(j).toString());
			}
			System.out.println("");
		}

		System.out.println("\n----RulesCIR	 -------------------------------");
		for(int i=0; i< RulesVIR_List.size(); i++)
		{
			System.out.print((i+1)+"\t");
			ArrayList ar = (ArrayList)RulesCIR_List.get(i);
			for(int j=0; j< ar.size(); j++)
			{
				System.out.print(" " + ar.get(j).toString());
			}
			System.out.println("");
		}

		System.out.println("\n----Variables	 ------------------------------");
		for(int i=0; i< aliasname_list.size(); i++)
			System.out.println((i+1) + "\t"+aliasname_list.get(i).toString());//+" -> "+alias_list.get(i).toString());
		System.out.println("\n----Additional Variables ---------------------");
		for(int i=0; i< aliasnamd_list.size(); i++)
			System.out.println((i+1) + "\t"+aliasnamd_list.get(i).toString());


		System.out.println("\n----MAKE list	 ------------------------------");
		for(int i=0; i< MAKE_list.size(); i++)
		System.out.println((i+1) + "\t"+MAKE_list.get(i).toString());
		System.out.println("\n----MODIFY list ------------------------------");
		for(int i=0; i< MODIFY_list.size(); i++)
		System.out.println((i+1) + "\t"+MODIFY_list.get(i).toString());


/*

		System.out.println("\n----class_variables_list ---------------------");
		for(int i=0; i< class_variables_list.size(); i++)
		System.out.println((i+1) + "\t"+class_variables_list.get(i).toString());
		
		System.out.println("\n----classX_objects_list ---------------------");
		for(int i=0; i< classX_objects_list.size(); i++)
		System.out.println((i+1) + "\t"+classX_objects_list.get(i).toString());
		
		System.out.println("\n----class_objectsX_list ---------------------");
		for(int i=0; i< class_objectsX_list.size(); i++)
			System.out.println((i+1) + "\t"+class_objectsX_list.get(i).toString());
*/

		System.out.println("\n----------------------------------------------");

//		System.out.println(execute_code);



		System.out.println("\n\nCREATING CLASSES: ============================");
		CreateSourceFiles(null);
	}

//==============================================================================
	public static void PrintTree(ArrayList al, int tabs)
	{
		StringBuffer a = new StringBuffer("");
		int ss = al.size();
		for(int i = 0; i<tabs; i++) a.append(".");

		if(ss>0)
		{
				System.out.println(a.toString() + al.get(0));
				for(int i = 1; i < ss; i++) PrintTree((ArrayList)(al.get(i)), tabs+1);
		}
	}
//==============================================================================
	public static void PrintList(ArrayList al)
	{
		for(int i=0; i< al.size(); i++)
			System.out.println((i+1)+"\t"+al.get(i).toString());
	}

/*
//==============================================================================
	public static ArrayList BuildTree(String input) throws ParseException, IOException
	{
		StringReader sr = new StringReader(input);
		RuleParser parser = new RuleParser(sr);
		return parser.StartTree();
	}
*/
//==============================================================================
	public static void ClearStaticVars()
	{
		noofobjects = 0;
		noofvariables = 0;
		noofchecks = 0;
		variabletype = 0;
		rules_count = 0;
		noofconditions = 0;
		maxnoofconditions = 0;
		meaobject = -1;
		alias_condition = true;
		anyalias_condition = false;
		slot_list = new ArrayList();
		class_variables_list = new ArrayList();
		classX_objects_list = new ArrayList();
		class_objectsX_list = new ArrayList();

		reduced_list = new ArrayList();
		tokensender_list = new ArrayList();
		node_list = new ArrayList();
		rulename_list = new ArrayList();
		rule_list = new ArrayList();
		rulemerit_list = new ArrayList();
		anyalias_list = new ArrayList();
		aliasname_list = new ArrayList();
		aliasnamd_list = new ArrayList();
		execute_code = new StringBuffer();
		object_list = new ArrayList();
		varvar_list = new ArrayList();
		idltree_list = new ArrayList();

		MAKE_list = new ArrayList();
		MODIFY_list = new ArrayList();
		RulesVIR_List = new ArrayList();
		VarsInRule_List = new ArrayList();
		RulesCIR_List = new ArrayList();
		CondsInRule_List = new ArrayList();
	}

	//==============================================================================
	ArrayList StartClasses(ArrayList al) throws ParseException
	{
			if(al == null) class_variables_list = new ArrayList();
			else class_variables_list = al;
			return _StartClasses();
	}

//==============================================================================
//	public static void SaveClasses(String path) throws IOException
//	{
//		CreateCalsses(path);
//	}

//==============================================================================
	static public boolean modeDebug = false;

	static public boolean modeLEX = true;
	static public boolean modeMEA = false;
	static public boolean modeBC = false;
	static public boolean modeII = false;
	static public boolean modePEM = false;

	static public int temporary_writing_mode = 0;

	static public String modeServerArgs = "";

	static int nooftreerows = 0;
	static int noofobjects = 0;
	static int noofvariables = 0;
	static int noofchecks = 0;
	static int variabletype = 0; //0 - unknown, 1 - int, 2 - double, 4 - string, 8 - $variable

	static int rules_count = 0;
	static int noofconditions = 0;
	static int maxnoofconditions = 0;
	static int meaobject = -1;

//	static int cond_blocks = 0;
	static String object_name;
	static String current_rulename;

	static int variable_type = 0;
// alias_condition & anyalias_condition are egzamined in slot_opt()
// There is alias substitution { sth==$alias }
	static boolean alias_condition = true;
// There is any alias in the current expression { sth relation sth $alias sth }
	static boolean anyalias_condition = false;

// Current class and object
	static Token class_token, obj_token;

// String list of every relation (condition) statement { class:object:variable relation expression }
	static ArrayList slot_list = new ArrayList();

// String list of objects variables { class:variable }
	static ArrayList class_variables_list = new ArrayList();
	static ArrayList classX_objects_list = new ArrayList();
	static ArrayList class_objectsX_list = new ArrayList();


	static ArrayList reduced_list = new ArrayList();
	static ArrayList tokensender_list = new ArrayList();

// String list of every AND/OR node { operator input1 input2 }
	static ArrayList node_list = new ArrayList();
// String list of rule names { rulename }
	static ArrayList rulename_list = new ArrayList();
// String list of rule names with info on input { rulename:input }
	static ArrayList rule_list = new ArrayList();
	static ArrayList rulemerit_list = new ArrayList();

// Integer list of entries in slot_list where the aliases are substituted
// static ArrayList alias_list = new ArrayList();
// Integer list of entries in slot_list where the aliases are to check
	static ArrayList anyalias_list = new ArrayList();
// String list of alias names { $Rule_noAlias_name }
	static ArrayList aliasname_list = new ArrayList();


// String list of alias names { $Rule_noAlias_name }
	static ArrayList aliasnamd_list = new ArrayList();
	static StringBuffer execute_code = new StringBuffer();


// String list of objects. Rule local: cleared for every rule.
	static ArrayList object_list = new ArrayList();

// String list of objects. Rule local: cleared for every rule.
	static ArrayList varvar_list = new ArrayList();

// String list of tree items created according to IDL file.
	static ArrayList idltree_list = new ArrayList();

// String list of execute items for BC connections.
	static ArrayList MAKE_list = new ArrayList();
	static ArrayList MODIFY_list = new ArrayList();

// Lists of objects variables used in rules
	static ArrayList RulesVIR_List = new ArrayList();
	static ArrayList VarsInRule_List = new ArrayList();

	static ArrayList RulesCIR_List = new ArrayList();
	static ArrayList CondsInRule_List = new ArrayList();
	

	private static void InsertVariablesToClasses(String class_name, int variable_type, ArrayList nt, ArrayList at)
	{
		int c, atsize, i, k;
		ArrayList nn;
		
		c = -1;
		atsize = at.size();
		for(i = 0; i < atsize; i++)
			if(((ArrayList)(at.get(i))).get(0).toString().compareTo(class_name) == 0) c = i;
		if(c<0)
		{
			nn = new ArrayList();
			nn.add(class_name);
			c = at.size();
			at.add(nn);
		}
		else nn = (ArrayList)(at.get(c));

		if(variable_type == 0)
		{
			for(i = 0; i < nt.size(); i++)
			{
				boolean insert = true;
				for(k = 1; k < nn.size(); k++)
				{
					if		 (((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"long "	 + nt.get(i).toString()	) == 0) insert = false;
					else if(((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"double " + nt.get(i).toString()	) == 0) insert = false;
					else if(((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"string " + nt.get(i).toString()	) == 0) insert = false;
				}
				if(insert)
				{	
					ArrayList nnn = new ArrayList();
					nnn.add("string " + nt.get(i).toString());
					nn.add(nnn);
				}
			}
		}
		else if(variable_type == 1)
		{
			for(i = 0; i < nt.size(); i++)
			{
				boolean insert = true;
				for(k = 1; k < nn.size(); k++)
				{
					if		 (((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"long "	 + nt.get(i).toString()	) == 0) insert = false;
					else if(((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"double " + nt.get(i).toString()	) == 0) insert = false;
					else if(((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"string " + nt.get(i).toString()	) == 0) insert = false;
				}
				if(insert)
				{	
					ArrayList nnn = new ArrayList();
					nnn.add("long " + nt.get(i).toString());
					nn.add(nnn);
				}
			}
		}
		else if(variable_type == 2)
		{
			for(i = 0; i < nt.size(); i++)
			{
				boolean insert = true;
				int subst = -1;

				for(k = 1; k < nn.size(); k++)
				{
					if		 (((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"long "	 + nt.get(i).toString()	) == 0) subst = k;
					else if(((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"double " + nt.get(i).toString()	) == 0) insert = false;
					else if(((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"string " + nt.get(i).toString()	) == 0) insert = false;
					
				}
				if(subst >= 0)
				{	
					ArrayList nnn = new ArrayList();
					nnn.add("double " + nt.get(i).toString());
					nn.set(subst, nnn);
				}
				else if(insert)
				{	
					ArrayList nnn = new ArrayList();
					nnn.add("double " + nt.get(i).toString());
					nn.add(nnn);
				}
			}
		}
		else
		{
			for(i = 0; i < nt.size(); i++)
			{
				boolean insert = true;
				int subst = -1;

				for(k = 1; k < nn.size(); k++)
				{
					if (((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"long "	 + nt.get(i).toString()	) == 0) subst = k;
					else if(((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"double " + nt.get(i).toString()	) == 0) subst = k;
					else if(((ArrayList)(nn.get(k))).get(0).toString().compareTo(	"string " + nt.get(i).toString()	) == 0) insert = false;
				}
				if(subst >= 0)
				{	
					ArrayList nnn = new ArrayList();
					nnn.add("string " + nt.get(i).toString());
					nn.set(subst, nnn);
				}
				else if(insert)
				{	
					ArrayList nnn = new ArrayList();
					nnn.add("string " + nt.get(i).toString());
					nn.add(nnn);
				}
			}
		}
	}

//==============================================================================
// Creates Java source files for MORE network program
	public static void CreateSourceFiles(String path) throws IOException
	{
		boolean exclude = false;
				String[] str;
				File inputFile = new File("RuleParser.cc");
				FileReader inputStream = new FileReader(inputFile);
				BufferedReader input = new BufferedReader(inputStream, 1024);
				String inputstring = new String();
				File outputFile;
				FileWriter out = null;

				while((inputstring = input.readLine()) != null)
				{
						if(inputstring.length()>=2)
						if(inputstring.charAt(0)=='#' && inputstring.charAt(1)=='#')
//						if(inputstring.substring(0, 1).compareTo("##")==0)
						{
								str = inputstring.split(" ");

								if(str[0].compareTo("##SaveTo")==0)
								{
										if(out != null) out.close();
										
										if(path == null)outputFile = new File(str[1]);
										else outputFile = new File(path + str[1]);

										out = new FileWriter(outputFile);
										continue;
								}

								if(out != null)
								{
// Writes sections dependent on inference network
										if(str[0].compareTo("##WriteTokenArrays")==0 && !exclude) WriteTokenArrays(out);
										else if(str[0].compareTo("##WriteTokenEvaluateFunction")==0 && !exclude) WriteTokenEvaluateFunction(out);
										else if(str[0].compareTo("##WriteNetworkNodes")==0 && !exclude) WriteNetworkNodes(out);
										else if(str[0].compareTo("##WriteNetworkConnections")==0 && !exclude) WriteNetworkConnections(out);
										else if(str[0].compareTo("##WriteFeedingSlots")==0 && !exclude) WriteFeedingSlots(out);
										else if(str[0].compareTo("##WriteUnFeedingSlots")==0 && !exclude) WriteUnFeedingSlots(out);
										else if(str[0].compareTo("##WriteExecutions")==0 && !exclude) WriteExecutions(out);
										else if(str[0].compareTo("##WriteDispatchExecution")==0 && !exclude) WriteDispatchExecution(out);
										else if(str[0].compareTo("##WriteStarter")==0 && !exclude) WriteStarter(out);
										else if(str[0].compareTo("##WriteConvertToAny")==0 && !exclude) WriteConvertToAny(out);
										else if(str[0].compareTo("##WriteConvertFromAny")==0 && !exclude) WriteConvertFromAny(out);
										else if(str[0].compareTo("##WriteSetTransparent")==0 && !exclude) WriteSetTransparent(out);
										else if(str[0].compareTo("##WriteBackwardChaining")==0 && !exclude) WriteBackwardChaining(out);
										else if(str[0].compareTo("##WriteParsingResults")==0 && !exclude) WriteParsingResults(out);
										else if(str[0].compareTo("##WriteServerArgs")==0 && !exclude) WriteServerArgs(out);
										else if(str[0].compareTo("##WriteNetworkConfiguration")==0 && !exclude) 
											{temporary_writing_mode = 0; WriteTreeConfiguration(out);}
										else if(str[0].compareTo("##WriteNetworkTree")==0 && !exclude)
											{temporary_writing_mode = 1; WriteTreeConfiguration(out);}


// Copies section of RuleParser.cc
										else if(str[0].compareTo("##EndIf")==0) exclude = false;
										else if(str[0].compareTo("##Else")==0) {if(exclude) exclude = false; else exclude = true;}
// Copies section of RuleParser.cc
										else if(str[0].compareTo("##IfPEM")==0) {if(modePEM) exclude = false; else exclude = true;}

// Copies section of RuleParser.cc if in Forward Chaining mode
										else if(str[0].compareTo("##IfLEX")==0) {if(modeLEX) exclude = false; else exclude = true;}
										else if(str[0].compareTo("##IfMEA")==0) {if(modeMEA) exclude = false; else exclude = true;}
// Copies section of RuleParser.cc if in Backward Chaining mode
										else if(str[0].compareTo("##IfBC")==0) {if(modeBC) exclude = false; else exclude = true;}
// Copies section of RuleParser.cc if Inexact Inferencing on
										else if(str[0].compareTo("##IfII")==0) {if(modeII) exclude = false; else exclude = true;}

										else if(str[0].compareTo("##IfDebug")==0) {if(modeDebug) exclude = false; else exclude = true;}
										continue;
								}
								continue;
						}
// Copies regular sections independent from rules
						if((out != null) && (!exclude))
						{
								out.write(inputstring, 0, inputstring.length());
								out.write("\n", 0, 1);
						}
				}
				inputStream.close();
				input.close();
				if(out != null) out.close();
		}



	public static void WriteParsingResults(FileWriter out) throws IOException
	{
 		out.write("\n----Conditions: reduced list --------------------\n");
		out.write("// Unique conditions (may be multiplied in different rules)\n");
		out.write("// index\tobject:variable_name:relation:expression\n");
		for(int i=0; i< reduced_list.size(); i++)
			out.write((i+1)+"\t"+reduced_list.get(i).toString()+ "\n");

		out.write("\n----Variables    ------------------------------\n");
		out.write("// Variables used in LHS of rule\n");
		out.write("// index\t&rule_no&variable_name\n");
		for(int i=0; i< aliasname_list.size(); i++)
			out.write((i+1) + "\t"+aliasname_list.get(i).toString()+ "\n");//+" -> "+alias_list.get(i).toString());
		
		out.write("\n----Additional Variables ---------------------\n");
		out.write("// Variables used only in RHS of rule\n");
		out.write("// index\t&rule_no&variable_name\n");
		for(int i=0; i< aliasnamd_list.size(); i++)
			out.write((i+1) + "\t"+aliasnamd_list.get(i).toString()+ "\n");

		out.write("\n----Conditions  ------------------------------\n");
		out.write("// List of all conditions\n");
		out.write("// index of condition : object in token : -check or variable index\n");
		for(int i=0; i< tokensender_list.size(); i++)
			out.write((i+1)+"\t"+tokensender_list.get(i).toString()
				+"\t"+slot_list.get(i).toString()+ "\n");

 		out.write("\n----objects sent------------------------------\n");
		for(int i=0; i< slot_list.size(); i++)
			out.write((i+1)+"\t"+slot_list.get(i).toString()+ "\n");

/*
		out.write("\n----checks at token sent list-----------------");
		for(int i=0; i< anyalias_list.size(); i++)
			out.write(anyalias_list.get(i).toString()+"\t");
*/
 		out.write("\n----Nodes     --------------------------------\n");
		for(int i=0; i< node_list.size(); i++)
			out.write((i+1)+"\t"+node_list.get(i).toString()+ "\n");

		out.write("\n----Rules      -------------------------------\n");
		for(int i=0; i< rule_list.size(); i++)
			out.write((i+1)+"\t"+((Integer)rulemerit_list.get(i)).intValue()+"\t"+rule_list.get(i).toString()+ "\n");

		out.write("\n----RulesVIR   -------------------------------\n");
		out.write("// Dependencies between rules and variables (used in backward chaining)\n");
		for(int i=0; i< RulesVIR_List.size(); i++)
		{
			out.write((i+1)+"\t");
			ArrayList ar = (ArrayList)RulesVIR_List.get(i);
			for(int j=0; j< ar.size(); j++)
			{
				out.write(" " + ar.get(j).toString());
			}
			out.write("\n");
		}
		out.write("\n----RulesCIR   -------------------------------\n");
		out.write("// Dependencies between rules and entries in MODIFY list\n");
		for(int i=0; i< RulesVIR_List.size(); i++)
		{
			out.write((i+1)+"\t");
			ArrayList ar = (ArrayList)RulesCIR_List.get(i);
			for(int j=0; j< ar.size(); j++)
			{
				out.write(" " + ar.get(j).toString());
			}
			out.write("\n");
		}
	
		out.write("\n----MAKE list   ------------------------------\n");
		for(int i=0; i< MAKE_list.size(); i++)
		out.write((i+1) + "\t"+MAKE_list.get(i).toString()+ "\n");
		out.write("\n----MODIFY list ------------------------------\n");
		for(int i=0; i< MODIFY_list.size(); i++)
		out.write((i+1) + "\t"+MODIFY_list.get(i).toString()+ "\n");
	}

	public static void WriteServerArgs(FileWriter out) throws IOException
	{
		boolean first = true;
		String str[] = modeServerArgs.split(" ");
		
		out.write("\t\tpublic static String[] servargs = {");
		for(int i=0; i<str.length; i++)
		{
			if(first)
			{
				if(str[i].length()>0) out.write("\"" +str[i]+ "\"");
				first = false;
			}
			else
			{
				if(str[i].length()>0) out.write(", \"" +str[i]+ "\"");
			}
		}
		out.write("};\n");
	}

	public static void WriteTreeConfigurationWrite(FileWriter out, String mom, int mom_index, String kid, int kid_index, int tabs) throws IOException
	{
		String outputstring = new String();
		if(temporary_writing_mode == 0)
		{
			WriteTabs(out, tabs);
			outputstring = "[" + mom_index + "]" + mom + " -> " + "[" + kid_index + "]" + kid + "\n";
			out.write(outputstring, 0, outputstring.length());
		}
		else
		{
			WriteTabs(out, tabs);
			outputstring = "javax.swing.tree.DefaultMutableTreeNode treeNode_" 
				+ kid_index + " = new javax.swing.tree.DefaultMutableTreeNode(" + kid + ");\n"; 
			out.write(outputstring, 0, outputstring.length());
			WriteTabs(out, tabs);
			outputstring = "treeNode_" + mom_index + ".add(treeNode_" + kid_index + ");\n";
			out.write(outputstring, 0, outputstring.length());
		}
	}

	public static void WriteTreeConfigurationBranch(FileWriter out, String node_name, int inp, String mom, int mom_index, int tabs) throws IOException
	{
		if(inp > 0)
		{
//Only one condition in rule
			String[] strc = tokensender_list.get(inp-1).toString().split(":");
			Integer iic = new Integer(strc[0]);
			nooftreerows++;
			WriteTreeConfigurationWrite(out, mom, mom_index, "Condition" + iic.intValue(), nooftreerows, tabs);
		}
		else
		{
//Go up to the main node in rule
			String kid;
			if(node_name.compareTo("OR")==0)
				kid = "Or" + (-inp);
			else
				kid = "And" + (-inp);

			nooftreerows++;
			WriteTreeConfigurationWrite(out, mom, mom_index, kid, nooftreerows, tabs);
			WriteTreeConfigurationNode(out, kid, nooftreerows, -inp-1, tabs+1);
		}
	}

	public static void WriteTreeConfigurationNode(FileWriter out, String mom, int mom_index, int node, int tabs) throws IOException
	{
		String outputstring = new String();
		String[] str = node_list.get(node).toString().split(" ");
		for(int k=1; k<=2; k++)
		{
			int inp = (new Integer(str[k])).intValue();
			WriteTreeConfigurationBranch(out, str[0], inp, mom, mom_index, tabs);
		}
	}

	public static void WriteTreeConfigurationRule(FileWriter out, int rule, int tabs) throws IOException
	{
		String outputstring = new String();
		String[] str;
		int inp;

		str = rule_list.get(rule).toString().split(":");
		Integer integer = new Integer(str[1]);
		inp = integer.intValue();
		String kid = "Rule" + (rule+1) + "_" + str[0];
		
		if(inp<=0) str = node_list.get(-inp-1).toString().split(" ");
		nooftreerows++;
		WriteTreeConfigurationWrite(out, "root", 0, kid, nooftreerows, tabs);
		WriteTreeConfigurationBranch(out, str[0], inp, kid, nooftreerows, tabs);
	}

	public static void WriteTreeConfiguration(FileWriter out) throws IOException
	{
		nooftreerows=0;
		String outputstring = new String();
		for(rules_count=rule_list.size()-1; rules_count>=0; rules_count--)
		{
			out.write("\n", 0, 1);
			WriteTreeConfigurationRule(out, rules_count, 2);
		}
	}

	public static void WriteTabs(FileWriter out, int tabs) throws IOException
	{
		for(int i=0; i<tabs; i++ ) out.write("\t", 0, 1);
	}

	public static void WriteBackwardChainingCondition(FileWriter out, boolean[] rules, boolean[] opened, int cond, int tabs) throws IOException
	{
		if(opened[cond]) return;

		String outputstring = new String();
		String variable;
		String[] sy;
		String[] st;
	
		WriteTabs(out, tabs);
		outputstring = "Condition"+ (cond+1) + ".SetTransparent();\n";
		out.write(outputstring, 0, outputstring.length());
		sy = reduced_list.get(cond).toString().split(":");

		opened[cond] = true;

		for(int m=0; m< MAKE_list.size(); m++)
		{
			st = MAKE_list.get(m).toString().split(":");
			if(st[0].compareTo(sy[0]) == 0)
			{
				st = MAKE_list.get(m).toString().split(" ");
				WriteBackwardChainingRule(out, rules, opened, (new Integer(st[1])).intValue(), tabs);
			}
		}

		variable = sy[0]+":"+sy[1];
		for(int m=0; m< MODIFY_list.size(); m++)
		{
			st = MODIFY_list.get(m).toString().split(" ");
			if(st[0].compareTo(variable) == 0)
			{
				WriteBackwardChainingRule(out, rules, opened, (new Integer(st[1])).intValue(), tabs);
			}
		}

/*
		variable = sy[0]+":"+sy[1];
		st = MODIFY_list.get(0).toString().split(" ");
		if(st[0].compareTo(variable) == 0)
		{
			WriteBackwardChainingRule(out, rules, opened, (new Integer(st[1])).intValue(), tabs);
		}
		boolean[] openedn = new boolean[reduced_list.size()];
		for(int i=reduced_list.size()-1; i>=0; i--) openedn[i] = opened[i];

		for(int m=1; m< MODIFY_list.size(); m++)
		{
			st = MODIFY_list.get(m).toString().split(" ");
			if(st[0].compareTo(variable) == 0)
			{
				WriteBackwardChainingRule(out, rules, openedn, (new Integer(st[1])).intValue(), tabs);
			}
		}
*/
	}

	public static void WriteBackwardChainingNode(FileWriter out, boolean[] rules, boolean[] opened, int node, int tabs) throws IOException
	{
		String outputstring = new String();
		String[] str;

		str = node_list.get(node).toString().split(" ");
		Integer i1 = new Integer(str[1]);
		Integer i2 = new Integer(str[2]);

		if(str[0].compareTo("OR")==0)
		{
			WriteTabs(out, tabs);
			outputstring = "//NodeOR_"+ (node+1) + "\n";
			out.write(outputstring, 0, outputstring.length());

			if(i1.intValue() > 0)
			{
//Connected to condition
				String[] strc = tokensender_list.get(i1.intValue()-1).toString().split(":");
				Integer iic = new Integer(strc[0]);
				WriteTabs(out, tabs+1);
				outputstring = "//O_OpenCondition_"+ (iic.intValue()) + "\n";
				out.write(outputstring, 0, outputstring.length());
				WriteBackwardChainingCondition(out, rules, opened, iic.intValue()-1, tabs+1);
			}
			else
			{
//Go up to the main node in rule
				WriteBackwardChainingNode(out, rules, opened, -i1.intValue()-1, tabs+1);
			}
			WriteTabs(out, tabs);
			outputstring = "if(Or" + (node+1) + ".GetToken() <= 0)\n";
			out.write(outputstring, 0, outputstring.length());
			WriteTabs(out, tabs);
			outputstring = "{\n";
			out.write(outputstring, 0, outputstring.length());

			boolean[] openedn = new boolean[reduced_list.size()];
			for(int i=reduced_list.size()-1; i>=0; i--) openedn[i] = opened[i];

			if(i2.intValue() > 0)
			{
//Connected to condition
				String[] strc = tokensender_list.get(i2.intValue()-1).toString().split(":");
				Integer iic = new Integer(strc[0]);
				WriteTabs(out, tabs+1);
				outputstring = "//O_OpenCondition_"+ (iic.intValue()) + "\n";
				out.write(outputstring, 0, outputstring.length());
				WriteBackwardChainingCondition(out, rules, openedn, iic.intValue()-1, tabs+1);
			}
			else
			{
//Go up to the main node in rule
				WriteBackwardChainingNode(out, rules, openedn, -i2.intValue()-1, tabs+1);	
			}
			WriteTabs(out, tabs);
			outputstring = "}\n";
			out.write(outputstring, 0, outputstring.length());
		}
		else
		{
			WriteTabs(out, tabs);
			outputstring = "//NodeAND_"+ (node+1) + "\n";
			out.write(outputstring, 0, outputstring.length());

			if(i1.intValue() > 0)
			{
//Connected to condition
				String[] strc = tokensender_list.get(i1.intValue()-1).toString().split(":");
				Integer iic = new Integer(strc[0]);
				WriteTabs(out, tabs+1);
				outputstring = "//A_OpenCondition_"+ (iic.intValue()) + "\n";
				out.write(outputstring, 0, outputstring.length());
				WriteBackwardChainingCondition(out, rules, opened, iic.intValue()-1, tabs+1);
			}
			else
			{
//Go up to the main node in rule
				WriteBackwardChainingNode(out, rules, opened, -i1.intValue()-1, tabs);	
			}
			if(i2.intValue() > 0)
			{
//Connected to condition
				String[] strc = tokensender_list.get(i2.intValue()-1).toString().split(":");
				Integer iic = new Integer(strc[0]);
				WriteTabs(out, tabs+1);
				outputstring = "//A_OpenCondition_"+ (iic.intValue()) + "\n";
				out.write(outputstring, 0, outputstring.length());
				WriteBackwardChainingCondition(out, rules, opened, iic.intValue()-1, tabs+1);
			}
			else
			{
//Go up to the main node in rule
				WriteBackwardChainingNode(out, rules, opened, -i2.intValue()-1, tabs);	
			}
		}
	}

	public static void WriteBackwardChainingRule(FileWriter out, boolean[] rules, boolean[] opened, int rule, int tabs) throws IOException
	{
		String outputstring = new String();
		if(rules[rule])
		{
			rules[rule] = false;
			WriteTabs(out, tabs);
			outputstring = "//EnteringRule_"+ (rule+1) + "\n";
			out.write(outputstring, 0, outputstring.length());

			String[] str, st;
			int inp;

			str = rule_list.get(rule).toString().split(":");
			Integer integer = new Integer(str[1]);
			inp = integer.intValue();

			if(inp > 0)
			{
//Only one condition in rule
				String[] strc = tokensender_list.get(inp-1).toString().split(":");
				Integer iic = new Integer(strc[0]);
				WriteTabs(out, tabs+1);
				outputstring = "//R_OpenCondition_"+ (iic.intValue()) + "\n";
				out.write(outputstring, 0, outputstring.length());
				WriteBackwardChainingCondition(out, rules, opened, iic.intValue()-1, tabs);
			}
			else
			{
//Go up to the main node in rule
				WriteBackwardChainingNode(out, rules, opened, -inp-1, tabs);
			}
			WriteTabs(out, tabs);
			outputstring = "//ExecutingRule_"+ (rule+1) + "\n";
			out.write(outputstring, 0, outputstring.length());

			WriteTabs(out, tabs);
			outputstring = "frl = ReteConflictSet.GetFiredOfMerit("+ ((Integer)rulemerit_list.get(rule)).intValue()+");\n";
			out.write(outputstring, 0, outputstring.length());

			if(modeDebug)
			{
				outputstring = "System.out.println(\"Executing_rule_"+ (rule+1) +"_merit_"+
					((Integer)rulemerit_list.get(rule)).intValue()+" _with_\" + frl.size() + \"_tokens\");\n";
				out.write(outputstring, 0, outputstring.length());
			}

			WriteTabs(out, tabs);
			outputstring = "for(int i = 0; i < frl.size(); i++)\n";
			out.write(outputstring, 0, outputstring.length());	
			WriteTabs(out, tabs);
			outputstring = "\texecutions.ExecuteRule((ReteToken)frl.get(i));\n";
			out.write(outputstring, 0, outputstring.length());	

			if(modeDebug)
			{
				outputstring = "System.out.println(\"Rule_executed_"+ (rule+1) +"_merit_"+ (
					(Integer)rulemerit_list.get(rule)).intValue() +"\");\n";
				out.write(outputstring, 0, outputstring.length());
			}
		}
	}

// Saves Backward chaining sequence
	public static void WriteBackwardChaining(FileWriter out) throws IOException
	{
		String outputstring = new String();

		boolean[] rules = new boolean[rule_list.size()];
		for(int i=rule_list.size()-1; i>=0; i--) rules[i] = true;

		boolean[] opened = new boolean[reduced_list.size()];
		for(int i=reduced_list.size()-1; i>=0; i--) opened[i] = false;

		outputstring = "\t\tArrayList frl;\n";
		out.write(outputstring, 0, outputstring.length());

		outputstring = "\t\tReteConflictSet.BackwardMode("+ rule_list.size() +");\n";
		out.write(outputstring, 0, outputstring.length());

		for(rules_count=rule_list.size()-1; rules_count>=0; rules_count--)
		{
			outputstring = "\t\t//Fresh rule #["+(rules_count+1)+"]\n";
			out.write(outputstring, 0, outputstring.length());
			WriteBackwardChainingRule(out, rules, opened, rules_count, 2);
		}
	}


// Saves nodes definitions
	public static void WriteNetworkNodes(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String[] str;

		outputstring = "\n\n//----------------- Rule nodes -----------------";
		out.write(outputstring, 0, outputstring.length());

		for(int i=0; i< rule_list.size(); i++)
		{
			str = rule_list.get(i).toString().split(":");
			outputstring = "\n\t\tReteNodeFinal Rule" + (i+1) + "_" + str[0] +" = new ReteNodeFinal(\"Rule" + (i+1) + "_" + str[0] +"\", "+
				+ ((Integer)rulemerit_list.get(i)).intValue()+ ", " +str[4] +");";
			out.write(outputstring, 0, outputstring.length());
		}

		outputstring = "\n\n//----------------- Condition nodes -----------------";
		out.write(outputstring, 0, outputstring.length());

		for(int i=0; i< reduced_list.size(); i++)
		{
//			str = reduced_list.get(i).toString().split(":");
			String relation = new String(reduced_list.get(i).toString());
			relation = relation.replaceFirst(":", ".");
			relation = relation.replaceAll(":", " ");
			relation = relation.replaceAll("\"", "\\\\\"");

			outputstring = "\n\t\tReteNodeCondition Condition"+ (i+1) +" = new ReteNodeCondition(\"C"+ (i+1) + ": " + relation + "\");";
			out.write(outputstring, 0, outputstring.length());
		}

		outputstring = "\n\n//----------------- Logic nodes -----------------";
		out.write(outputstring, 0, outputstring.length());

		for(int i=0; i< node_list.size(); i++)
		{
			str = node_list.get(i).toString().split(" ");

			if(str[0].compareTo("OR")==0)
				outputstring = "\n\t\tReteNodeOR Or"+ (i+1) +" = new ReteNodeOR(\"Or"+ (i+1) + "\");";
			else
				outputstring = "\n\t\tReteNodeAND And"+ (i+1) +" = new ReteNodeAND(\"And"+ (i+1) + "\");";

			out.write(outputstring, 0, outputstring.length());
		}
		out.write("\n", 0, 1);
	}

// Sets all condition nodes as transparent, to imediately send token forward.
	public static void WriteSetTransparent(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String[] str;

		for(int i=0; i< reduced_list.size(); i++)
		{
			str = reduced_list.get(i).toString().split(":");

			outputstring = "\n\t\tCondition"+ (i+1) +".SetTransparent();";
			out.write(outputstring, 0, outputstring.length());
		}
		out.write("\n", 0, 1);
	}

// Saves connectivity
	public static void WriteNetworkConnections(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String[] str, st;
		int inp;

		outputstring = "\n\n//----------------- Connections -----------------";
		out.write(outputstring, 0, outputstring.length());

		for(int i=0; i< rule_list.size(); i++)
		{
			str = rule_list.get(i).toString().split(":");

			Integer integer = new Integer(str[1]);
			inp = integer.intValue();

// Rules
			if(inp > 0)
			{
				String[] strc = tokensender_list.get(inp-1).toString().split(":");
				Integer iic = new Integer(strc[0]);

				outputstring = "\n\t\tRule" + (i+1) + "_" + str[0] +
											 ".SetInputNode( Condition"+ (iic.intValue()) + " );\n" +
											 "\t\tCondition" + (iic.intValue()) + ".SetOutputNode( Rule" +
								 (i+1) + "_" + str[0] + " );";
			}
			else
			{
				st = node_list.get(-inp-1).toString().split(" ");
				if(st[0].compareTo("OR") == 0)
				{
					outputstring = "\n\t\tRule" + (i+1) + "_" + str[0] +
											 ".SetInputNode( Or"+ (-inp) + " );\n" +
											 "\t\tOr" + (-inp) + ".SetOutputNode( Rule" +
								 (i+1) + "_" + str[0] + " );";
				}

				else
				{
					outputstring = "\n\t\tRule" + (i+1) + "_" + str[0] +
											 ".SetInputNode( And"+ (-inp) + " );\n" +
											 "\t\tAnd" + (-inp) + ".SetOutputNode( Rule" +
								 (i+1) + "_" + str[0] + " );";
				}
			}
			out.write(outputstring, 0, outputstring.length());
		}

		out.write("\n", 0, 1);

// AND OR
		for(int i=0; i< node_list.size(); i++)
		{
			str = node_list.get(i).toString().split(" ");
			Integer integer = new Integer(str[1]);
			inp = integer.intValue();

			if(inp > 0)
			{
				String[] strc = tokensender_list.get(inp-1).toString().split(":");
				Integer iic = new Integer(strc[0]);
				
				if(str[0].compareTo("OR")==0)
				{
					outputstring = "\n\t\tOr" + (i+1) + ".SetInputNode( Condition"+ (iic.intValue()) + " );" +
											 "\n\t\tCondition" + (iic.intValue()) + ".SetOutputNode( Or" + (i+1) + " );";
				}

				else
				{
					outputstring = "\n\t\tAnd" + (i+1) + ".SetInputNode( Condition"+ (iic.intValue()) + " );" +
											 "\n\t\tCondition" + (iic.intValue()) + ".SetOutputNode( And" + (i+1) + " );";
				}
			}

			else
			{
				st = node_list.get(-inp-1).toString().split(" ");

				if(str[0].compareTo("OR")==0)
				{
					if(st[0].compareTo("OR")==0)
					{
						outputstring = "\n\t\tOr" + (i+1) + ".SetInputNode( Or"+ (-inp) + " );" +
												 "\n\t\tOr" + (-inp) + ".SetOutputNode( Or" + (i+1) + " );";
					}

					else
					{
						outputstring = "\n\t\tOr" + (i+1) + ".SetInputNode( And"+ (-inp) + " );" +
												 "\n\t\tAnd" + (-inp) + ".SetOutputNode( Or" + (i+1) + " );";
					}
				}

				else
				{
					if(st[0].compareTo("OR")==0)
					{
						outputstring = "\n\t\tAnd" + (i+1) + ".SetInputNode( Or"+ (-inp) + " );" +
												 "\n\t\tOr" + (-inp) + ".SetOutputNode( And" + (i+1) + " );";
					}

					else
					{
						outputstring = "\n\t\tAnd" + (i+1) + ".SetInputNode( And"+ (-inp) + " );" +
												 "\n\t\tAnd" + (-inp) + ".SetOutputNode( And" + (i+1) + " );";
					}
				}
			}
			out.write(outputstring, 0, outputstring.length());

			integer = new Integer(str[2]);
			inp = integer.intValue();

			if(inp > 0)
			{

				String[] strc = tokensender_list.get(inp-1).toString().split(":");
				Integer iic = new Integer(strc[0]);

				if(str[0].compareTo("OR")==0)
				{
					outputstring = "\n\t\tOr" + (i+1) + ".SetInputNode( Condition"+ (iic.intValue()) + " );" +
											 "\n\t\tCondition" + (iic.intValue()) + ".SetOutputNode( Or" + (i+1) + " );";
				}

				else
				{
					outputstring = "\n\t\tAnd" + (i+1) + ".SetInputNode( Condition"+ (iic.intValue()) + " );" +
											 "\n\t\tCondition" + (iic.intValue()) + ".SetOutputNode( And" + (i+1) + " );";
				}
			}

			else
			{
				st = node_list.get(-inp-1).toString().split(" ");

				if(str[0].compareTo("OR")==0)
				{
					if(st[0].compareTo("OR")==0)
					{
						outputstring = "\n\t\tOr" + (i+1) + ".SetInputNode( Or"+ (-inp) + " );" +
												 "\n\t\tOr" + (-inp) + ".SetOutputNode( Or" + (i+1) + " );";
					}

					else
					{
						outputstring = "\n\t\tOr" + (i+1) + ".SetInputNode( And"+ (-inp) + " );" +
												 "\n\t\tAnd" + (-inp) + ".SetOutputNode( Or" + (i+1) + " );";
					}
				}

				else
				{
					if(st[0].compareTo("OR")==0)
					{
						outputstring = "\n\t\tAnd" + (i+1) + ".SetInputNode( Or"+ (-inp) + " );" +
												 "\n\t\tOr" + (-inp) + ".SetOutputNode( And" + (i+1) + " );";
					}

					else
					{
						outputstring = "\n\t\tAnd" + (i+1) + ".SetInputNode( And"+ (-inp) + " );" +
												 "\n\t\tAnd" + (-inp) + ".SetOutputNode( And" + (i+1) + " );";
					}
				}
			}
			out.write(outputstring, 0, outputstring.length());
		}
		out.write("\n", 0, 1);
	}

// Saves token arrays
	public static void WriteExecutions(FileWriter out) throws IOException
	{
		out.write(execute_code.toString(), 0, execute_code.length());
		out.write("\t}\n", 0, 3);
	}

	public static void WriteDispatchExecution(FileWriter out) throws IOException
	{
		String[] str;
		String outputstring = new String();

		for(int i=0; i< rulename_list.size(); i++)
		{
			str = rule_list.get(i).toString().split(":");

//			outputstring = "\t\tcase " +(i+1)+ ": Execute_"+ str[0] +"(token); break;\n";
			outputstring = "\t\tcase " +((Integer)rulemerit_list.get(i)).intValue()+ ": Execute_"+ str[0] +"(token); break;\n";
			out.write(outputstring, 0, outputstring.length());
		}
	}

	public static void WriteStarter(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String[] sto;
		int k = 0;
		for(int i=0; i< reduced_list.size(); i++)
		{
			sto = reduced_list.get(i).toString().split(":");
			if(sto[0].compareTo("COSMOS_START") == 0) k++;
		}
		if(k>0)
		{
			outputstring = "\t\tCOSMOS_START __starter = new COSMOS_START();\n";
			out.write(outputstring, 0, outputstring.length());
			outputstring = "\t\t__starter.init_status = 1;\n\t\tMemSetObject(__starter, \"__starter\");\n";
			out.write(outputstring, 0, outputstring.length());
		}
		else
		{
			outputstring = "\t\tif(wmserverinterface!=null)	LoadObjectsFromWM(false);\n";
			out.write(outputstring, 0, outputstring.length());
		}
	}

// Saves token arrays
	public static void WriteTokenArrays(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String str = new String();
//		String[] str;

		outputstring = "\n//----------------- Token array sizes -----------------";
		out.write(outputstring, 0, outputstring.length());

		outputstring = "\n\tstatic final int objectsno = " + noofobjects +";";//##NoOfObjects
		out.write(outputstring, 0, outputstring.length());
		
		outputstring = "\n\tstatic final int variablesno = " + aliasname_list.size() +";";//##NoOfVariables
		out.write(outputstring, 0, outputstring.length());
		
		outputstring = "\n\tstatic final int checksno = " + anyalias_list.size() +";";//##NoOfChecks
		out.write(outputstring, 0, outputstring.length());

/*
		outputstring = "\n\n//----------------- Token arrays -----------------";
		out.write(outputstring, 0, outputstring.length());

		outputstring = "\n\tstatic final String[] variablenm =\n\t{";
		out.write(outputstring, 0, outputstring.length());

		for(int i=0; i<aliasname_list.size()-1; i++)
		{
			outputstring = "\n\t\t\"" + aliasname_list.get(i).toString().trim() + "\",";
			out.write(outputstring, 0, outputstring.length());
		}
		if(aliasname_list.size()>0)
		{
			outputstring = "\n\t\t\"" + aliasname_list.get(aliasname_list.size()-1).toString().trim() + "\"\n";
			out.write(outputstring, 0, outputstring.length());
		}
		outputstring = "\t};"
		out.write(outputstring, 0, outputstring.length());
*/
/*
		outputstring = "\n\tstatic final String[] checknm =\n\t{";
		out.write(outputstring, 0, outputstring.length());

		for(int i=0; i<anyalias_list.size()-1; i++)
		{
			str = reduced_list.get(((Integer)anyalias_list.get(i)).intValue()).toString();

			outputstring = "\n\t\t\"" + str + "\",";
			out.write(outputstring, 0, outputstring.length());
		}
		outputstring = "\n\t\t\"" +
			reduced_list.get(((Integer)anyalias_list.get(anyalias_list.size()-1)).intValue()).toString()
			+ "\"\n\t}";
		out.write(outputstring, 0, outputstring.length());
*/
		out.write("\n", 0, 1);
	}


// Saves token arrays
	public static void WriteTokenEvaluateFunction(FileWriter out) throws IOException
	{
		String outputstring = new String();
		boolean[] vars = new boolean[aliasname_list.size()];

//		MAKE_list = new ArrayList();

		outputstring = "\n//----------------- Evaluate function -----------------";
		out.write(outputstring, 0, outputstring.length());

		for(int i=0; i<anyalias_list.size(); i++)
		{
			boolean dostring = true;
			StringBuffer sb = new StringBuffer();
			String[] str, st;

			str = reduced_list.get(((Integer)anyalias_list.get(i)).intValue()-1).toString().split(":");
			st = str[3].split(" ");

/*//PMS 
			if(modeII)
			{
				ArrayList temp_list = new ArrayList();
				for(int k = 0; k<st.length; k++)
				{
					String ss = " " + st[k] + " ";
					int ind = aliasname_list.indexOf(ss);
					if(ind >= 0)
					{
						Integer iind = new Integer(ind);
						if(!temp_list.contains(iind)) temp_list.add(iind);
					}
				}
				outputstring = "\n\tprivate static int VarsInCheck"+ i +"[] = {";
				out.write(outputstring, 0, outputstring.length());
				for(int k = 0; k < temp_list.size(); k++)
				{
					if(k == temp_list.size()-1)
					{
						outputstring = temp_list.get(k).toString();
						out.write(outputstring, 0, outputstring.length());
					}
					else
					{
						outputstring = temp_list.get(k).toString()+", ";
						out.write(outputstring, 0, outputstring.length());
					}
				}
				outputstring = "};\n";
				out.write(outputstring, 0, outputstring.length());
			}
*/
			outputstring = "\n\tprivate static boolean Evaluate"+ i +"(Integer var, ReteToken token)\n\t{\n";
			out.write(outputstring, 0, outputstring.length());

			outputstring = "\t\tif(var == null) return false;\n";
			out.write(outputstring, 0, outputstring.length());
			
			outputstring = "\t\tif(var.doubleValue() " + str[2] + " ";
			out.write(outputstring, 0, outputstring.length());

			for(int k = 0; k<st.length; k++)
			{
				String ss = " " + st[k] + " ";
				int ind = aliasname_list.indexOf(ss);

				if(ind >= 0)
				{
					outputstring = "((Number)(token.variable[" + ind + "])).doubleValue()";
					out.write(outputstring, 0, outputstring.length());
				}
				else
				{
					if(st[k].length() > 0 && st[k].compareTo("+") != 0)
						dostring = false;
					out.write(st[k], 0, st[k].length());
				}
			}
			outputstring = ") return true;\n\t\treturn false;\n\t}";
			out.write(outputstring, 0, outputstring.length());



			outputstring = "\n\tprivate static boolean Evaluate"+ i +"(int var, ReteToken token)\n\t{\n";
			out.write(outputstring, 0, outputstring.length());
			outputstring = "\t\tif((double)var " + str[2] + " ";
			out.write(outputstring, 0, outputstring.length());

			for(int k = 0; k<st.length; k++)
			{
				String ss = " " + st[k] + " ";
				int ind = aliasname_list.indexOf(ss);

				if(ind >= 0)
				{
					outputstring = "((Number)(token.variable[" + ind + "])).doubleValue()";
					out.write(outputstring, 0, outputstring.length());
				}
				else
				{
					if(st[k].length() > 0 && st[k].compareTo("+") != 0)
						dostring = false;
					out.write(st[k], 0, st[k].length());
				}
			}
			outputstring = ") return true;\n\t\treturn false;\n\t}";
			out.write(outputstring, 0, outputstring.length());

			outputstring = "\n\tprivate static boolean Evaluate"+ i +"(Double var, ReteToken token)\n\t{\n";
			out.write(outputstring, 0, outputstring.length());

			outputstring = "\t\tif(var == null) return false;\n";
			out.write(outputstring, 0, outputstring.length());

			
			outputstring = "\t\tif(var.doubleValue() " + str[2] + " ";
			out.write(outputstring, 0, outputstring.length());

			for(int k = 0; k<st.length; k++)
			{
				String ss = " " + st[k] + " ";
				int ind = aliasname_list.indexOf(ss);

				if(ind >= 0)
				{
					outputstring = "((Number)(token.variable[" + ind + "])).doubleValue()";
					out.write(outputstring, 0, outputstring.length());
				}
				else
				{
					out.write(st[k], 0, st[k].length());
				}
			}
			outputstring = ") return true;\n\t\treturn false;\n\t}";
			out.write(outputstring, 0, outputstring.length());

			outputstring = "\n\tprivate static boolean Evaluate"+ i +"(double var, ReteToken token)\n\t{\n";
			out.write(outputstring, 0, outputstring.length());
			outputstring = "\t\tif(var " + str[2] + " ";
			out.write(outputstring, 0, outputstring.length());

			for(int k = 0; k<st.length; k++)
			{
				String ss = " " + st[k] + " ";
				int ind = aliasname_list.indexOf(ss);

				if(ind >= 0)
				{
					outputstring = "((Number)(token.variable[" + ind + "])).doubleValue()";
					out.write(outputstring, 0, outputstring.length());
				}
				else
				{
					out.write(st[k], 0, st[k].length());
				}
			}
			outputstring = ") return true;\n\t\treturn false;\n\t}";
			out.write(outputstring, 0, outputstring.length());

			if(dostring)
			{
				outputstring = "\n\tprivate static boolean Evaluate"+ i +"(String var, ReteToken token)\n\t{\n";
				out.write(outputstring, 0, outputstring.length());

				outputstring = "\t\tif(var == null) return false;\n";
				out.write(outputstring, 0, outputstring.length());

				outputstring = "\t\tif(var.compareTo(";
				out.write(outputstring, 0, outputstring.length());

				for(int k = 0; k<st.length; k++)
				{
					String ss = " " + st[k] + " ";
					int ind = aliasname_list.indexOf(ss);
	
					if(ind >= 0)
					{
						outputstring = "token.variable[" + ind + "].toString()";
						out.write(outputstring, 0, outputstring.length());
					}
					else
					{
						out.write(st[k], 0, st[k].length());
					}
				}
				outputstring = ") "+ str[2] + " 0) return true;\n\t\treturn false;\n\t}";
				out.write(outputstring, 0, outputstring.length());
			}
			out.write("\n", 0, 1);
		}

		outputstring = "\n\tprivate static boolean Evaluate(ReteToken token)\n\t{";
		out.write(outputstring, 0, outputstring.length());

		for(int i=0; i<anyalias_list.size(); i++)
		{
			StringBuffer sb = new StringBuffer();
			String[] str, st;

			str = reduced_list.get(((Integer)anyalias_list.get(i)).intValue()-1).toString().split(" ");
			for(int j = 0; j<aliasname_list.size(); j++) vars[j] = false;
			for(int j = 0; j<str.length; j++)
			{
				String ss = " " + str[j] + " ";
				int ind = aliasname_list.indexOf(ss);
				if(ind >= 0)
				{
					vars[ind] = true;
				}
			}

			for(int j = 0; j<aliasname_list.size(); j++)
			{
				if(vars[j])
				{
					sb.append("(token.typeofvariable[" + j + "]!=0) || ");
				}
			}

			if(sb.length()>4)
			{
				boolean dostring = true;

				sb.delete(sb.length()-4, sb.length());

				outputstring = "\n\t\tif(token.check[" + i + "]==1)\n\t\t\tif(";
				out.write(outputstring, 0, outputstring.length());
				out.write(sb.toString(), 0, sb.length());
				outputstring = ")\n\t\t\t{\n";
				out.write(outputstring, 0, outputstring.length());

				str = reduced_list.get(((Integer)anyalias_list.get(i)).intValue()-1).toString().split(":");
/*				
				outputstring = "\t\t\t\tif((("+ str[0] +")token.object["+ i +"])."
					+ str[1] +" == null) {token.check["+ i +"]=3; return false;}\n";
				out.write(outputstring, 0, outputstring.length());
*/
				outputstring = "\t\t\t\tif(Evaluate"+ i +"((("+ str[0] +")token.object["+ i +"])."
					+ str[1] +", token))\n\t\t\t\t{\n";
				out.write(outputstring, 0, outputstring.length());
					
				outputstring = "\t\t\t\t\ttoken.check["+ i +"] = 2;\n";
				out.write(outputstring, 0, outputstring.length());
				/*if(modeII)
				{
					outputstring = "\t\t\t\t\ttoken.certainty = Certainty(" + 
						"(("+ str[0] +")token.object["+ i +"])._Certainty_" + str[1] + ", " +
						"(("+ str[0] +")token.object["+ i +"])._LS_" + str[1] + ", " +
						"(("+ str[0] +")token.object["+ i +"])._LN_" + str[1] + ", " +
						"VarsInCheck" + i + ", true);\n";
					out.write(outputstring, 0, outputstring.length());
				}*/
				outputstring = "\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n";
				out.write(outputstring, 0, outputstring.length());
				outputstring = "\t\t\t\t\ttoken.check["+ i +"] = 3;\n";
				out.write(outputstring, 0, outputstring.length());
				/*if(modeII)
				{
					outputstring = "\t\t\t\t\ttoken.certainty = Certainty(" + 
						"(("+ str[0] +")token.object["+ i +"])._Certainty_" + str[1] + ", " +
						"(("+ str[0] +")token.object["+ i +"])._LS_" + str[1] + ", " +
						"(("+ str[0] +")token.object["+ i +"])._LN_" + str[1] + ", " +
						"VarsInCheck" + i + ", false);\n";
					out.write(outputstring, 0, outputstring.length());
				}*/
				outputstring = "\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}";
				out.write(outputstring, 0, outputstring.length());
			}
		}
		outputstring = "\n\t\treturn true;\n\t};";
		out.write(outputstring, 0, outputstring.length());
		out.write("\n", 0, 1);
	}

	public static void WriteFeedingSlots(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String[] sto;
		String[] str;
		String[] st;
		ArrayList object_list = new ArrayList();

		for(int i=0; i< reduced_list.size(); i++)
		{
			sto = reduced_list.get(i).toString().split(":");
			if(!object_list.contains(sto[0])) object_list.add(sto[0]);
		}

		for(int obn=0; obn < object_list.size(); obn++)
		{
			int last_cond = 0;
			sto = object_list.get(obn).toString().split(":");

			if(obn>0)
				outputstring = "\t\telse if(obj.getClass().getName().compareTo(\"ReteObjects." + sto[0] + "\")==0)\n\t\t{\n";
			else
				outputstring = "\t\tif(obj.getClass().getName().compareTo(\"ReteObjects." + sto[0] + "\")==0)\n\t\t{\n";
			out.write(outputstring, 0, outputstring.length());

			for(int i=0; i< tokensender_list.size(); i++)
			{
				str = tokensender_list.get(i).toString().split(":");
				int ii = (new Integer(str[0])).intValue();
				st = reduced_list.get(ii-1).toString().split(":");
				
				if(last_cond<ii)
				{
					last_cond = ii;
					if(sto[0].compareTo(st[0]) == 0)
					{
//						str = tokensender_list.get(i).toString().split(":");
//						Integer ii = new Integer(str[0]);
						Integer oo = new Integer(str[1]);
						Integer cc = new Integer(str[2]);
						Integer vv = new Integer(str[3]);

						outputstring = "//	"+ reduced_list.get(ii-1).toString() + "	" + tokensender_list.get(i).toString() +"\n";
						out.write(outputstring, 0, outputstring.length());

						outputstring = "\t\t\ttoken = new ReteToken();\n\t\t\ttoken.ClearToken();\n";
						out.write(outputstring, 0, outputstring.length());
						outputstring = "\t\t\ttoken.object[" + oo.intValue() + "] = obj;\n";
						out.write(outputstring, 0, outputstring.length());
 						outputstring = "\t\t\ttoken.timestamps[" + oo.intValue() + "] = time_stamp;\n";
						out.write(outputstring, 0, outputstring.length());

						if(modeII)
						{
							outputstring = "\t\t\ttoken.P_H_Ep = (("+ st[0] +")obj)._P_H_Ep_" + st[1] + ";\n";
							out.write(outputstring, 0, outputstring.length());
							outputstring = "\t\t\ttoken.LSp_from_P_H_Ep();\n";
							out.write(outputstring, 0, outputstring.length());
						}

		//instant evaluation
						if(cc.intValue()==0)
						{
							if(vv.intValue()==4)
							{
								outputstring = "\t\t\tif(TypeOfObject((("+ st[0] +")obj)."+ st[1] +") == 1)\n";
								out.write(outputstring, 0, outputstring.length());
								outputstring = "\t\t\t{\n";
								out.write(outputstring, 0, outputstring.length());

								outputstring = "\t\t\t\tif((("+ st[0] +")obj)."+ st[1] +" != null)\n";
								out.write(outputstring, 0, outputstring.length());
								outputstring = "\t\t\t\tif((("+ st[0] +")obj)."+ st[1] 
									+".compareTo("+ st[3] +")"+ st[2] +"0)\n\t\t\t\t\tCondition"+ ii
									+".InputCondition(token);\n";
								out.write(outputstring, 0, outputstring.length());
								outputstring = "\t\t\t}\n";
								out.write(outputstring, 0, outputstring.length());

							}
							if(vv.intValue()==1 || vv.intValue()==2 || vv.intValue()==3)
							{
								outputstring = "\t\t\tif(TypeOfObject((("+ st[0] +")obj)."+ st[1] +") == 2 || TypeOfObject((("+ st[0] +")obj)."+ st[1] +") == 3)\n";
								out.write(outputstring, 0, outputstring.length());
								outputstring = "\t\t\t{\n";
								out.write(outputstring, 0, outputstring.length());
		
								outputstring = "\t\t\t\tif((("+ st[0] +")obj)."+st[1]+" "
									+st[2]+" "+st[3]+")\n\t\t\t\t\tCondition"+ ii
									+".InputCondition(token);\n";
								
								out.write(outputstring, 0, outputstring.length());
								outputstring = "\t\t\t}\n\n";
								out.write(outputstring, 0, outputstring.length());
							}
						}

		//check
						else if(cc.intValue()<0)
						{
							outputstring = "\t\t\ttoken.check["+ (-cc.intValue()-1) +"] = 1;\n";
							out.write(outputstring, 0, outputstring.length());
							outputstring = "\t\t\tCondition"+ ii +".InputCondition(token);\n\n";
							out.write(outputstring, 0, outputstring.length());
						}

		//variable substitution
						else
						{
							outputstring = "\t\t\ttoken.variable["+ (cc.intValue()-1) +"] = ReturnClass((("+ st[0] +")obj)."+ st[1] +");\n";
							out.write(outputstring, 0, outputstring.length());
							outputstring = "\t\t\ttoken.typeofvariable["+ (cc.intValue()-1) +"] = TypeOfObject((("+ st[0] +")obj)."+ st[1] +");\n";
							out.write(outputstring, 0, outputstring.length());
							outputstring = "\t\t\tCondition"+ ii +".InputCondition(token);\n\n";
							out.write(outputstring, 0, outputstring.length());
						}
					}
				}
			}
			outputstring = "\t\t}\n";
			out.write(outputstring, 0, outputstring.length());
		}
	}







	public static void WriteUnFeedingSlots(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String[] sto;
		String[] str;
		String[] st;
		ArrayList object_list = new ArrayList();

		for(int i=0; i< reduced_list.size(); i++)
		{
			sto = reduced_list.get(i).toString().split(":");
			if(!object_list.contains(sto[0])) object_list.add(sto[0]);
		}

		for(int obn=0; obn < object_list.size(); obn++)
		{
			int last_cond = 0;
			sto = object_list.get(obn).toString().split(":");

			if(obn>0)
				outputstring = "\t\telse if(obj.getClass().getName().compareTo(\"ReteObjects." + sto[0] + "\")==0)\n\t\t{\n";
			else
				outputstring = "\t\tif(obj.getClass().getName().compareTo(\"ReteObjects." + sto[0] + "\")==0)\n\t\t{\n";
			out.write(outputstring, 0, outputstring.length());

			for(int i=0; i< tokensender_list.size(); i++)
			{
				str = tokensender_list.get(i).toString().split(":");
				int ii = (new Integer(str[0])).intValue();
				st = reduced_list.get(ii-1).toString().split(":");
				
				if(last_cond<ii)
				{
					last_cond = ii;
					if( sto[0].compareTo(st[0]) == 0)
					{
						outputstring = "\t\t\tCondition"+ ii +".RemoveObsoleteTokens(obj);\n";
						out.write(outputstring, 0, outputstring.length());
					}
				}
			}
			outputstring = "\t\t}\n";
			out.write(outputstring, 0, outputstring.length());
		}
	}

	public static void WriteConvertToAny(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String[] sto;
		ArrayList object_list = new ArrayList();

		for(int i=0; i< reduced_list.size(); i++)
		{
			sto = reduced_list.get(i).toString().split(":");
			if(!object_list.contains(sto[0])) object_list.add(sto[0]);
		}

		for(int obn=0; obn < object_list.size(); obn++)
		{
			sto = object_list.get(obn).toString().split(":");
/*
			if(obn>0)
				outputstring = "\t\telse if(obj.getClass().getName().compareTo(\"ReteObjects." + sto[0] + "\")==0) "
				+ sto[0] + "Helper.insert(anyholder, ("+ sto[0] +")obj);\n\t\t";
			else
				outputstring = "\t\tif(obj.getClass().getName().compareTo(\"ReteObjects." + sto[0] + "\")==0) "
				+ sto[0] + "Helper.insert(anyholder, ("+ sto[0] +")obj);\n\t\t";
*/
			if(obn>0)
				outputstring = "\t\telse if(classname.compareTo(\"ReteObjects." + sto[0] + "\")==0) "
				+ sto[0] + "Helper.insert(anyholder, ("+ sto[0] +")obj);\n\t\t";
			else
				outputstring = "\t\tif(classname.compareTo(\"ReteObjects." + sto[0] + "\")==0) "
				+ sto[0] + "Helper.insert(anyholder, ("+ sto[0] +")obj);\n\t\t";
			out.write(outputstring, 0, outputstring.length());
		}
	}

	public static void WriteConvertFromAny(FileWriter out) throws IOException
	{
		String outputstring = new String();
		String[] sto;
		ArrayList object_list = new ArrayList();

		for(int i=0; i< reduced_list.size(); i++)
		{
			sto = reduced_list.get(i).toString().split(":");
			if(!object_list.contains(sto[0])) object_list.add(sto[0]);
		}

		for(int obn=0; obn < object_list.size(); obn++)
		{
			sto = object_list.get(obn).toString().split(":");

			if(obn>0)
				outputstring = "\t\telse if(ah.value.type().name().compareTo(\"" + sto[0] + "\")==0) obj = "
				+ sto[0] + "Helper.extract(ah.value);\n\t\t";
			else
				outputstring = "\t\tif(ah.value.type().name().compareTo(\"" + sto[0] + "\")==0) obj = "
				+ sto[0] + "Helper.extract(ah.value);\n\t\t";
			out.write(outputstring, 0, outputstring.length());
		}
	}
}

PARSER_END(RuleParser)


//==============================================================================
// Neglected token definitions
SKIP : 
{
	" "
	|	"\t"
	|	"\n"
	|	"\r"
	|	<"//" (~["\n","\r"])* ("\n" | "\r" | "\r\n")>
	|	<"/*" (~["*"])* "*" ("*" | ~["*","/"] (~["*"])* "*")* "/">
		|	<"COMMENT" (" " | "\t")* ":" (" " | "\t")* "\"" (~["\""])* "\"">

}

// Token definitions important for parsing
TOKEN:
{
	//<NUM: (["0"-"9"])* (["."])? (["0"-"9"])+>
	<RULE: ("RULE")>
	| <CLASS: ("CLASS")>
	| <OBJ: ("OBJ")>
	| <IF: ("IF")>
	| <THEN: ("THEN")>
	| <MODIFY: ("MODIFY")>
	| <MAKE: ("MAKE")>
	| <MAKET: ("MAKET")>
	| <REMOVE: ("REMOVE")>
	| <PRINT: ("PRINT")>
	| <READ: ("READ")>
	| <PROMPT: ("PROMPT")>
	| <BIND: ("BIND")>
	| <VAR: ("VAR")>
	| <EXECUTE: ("EXECUTE")>
	| <DISPLAY: ("DISPLAY")>
	| <TYPE: ("TYPE")>
	
	| <OR: ("OR" | "||")>
	| <AND: ("AND" | "&&")>

	| <GT: ("GT" | ">")>
	| <GE: ("GE" | ">=")>
	| <LT: ("LT" | "<")>
	| <LE: ("LE" | "<=")>
	| <EQ: ("EQ" | "==")>
	| <NE: ("NE" | "!=")>

	| <MODULE: ("module")>
	| <STRUCT: ("struct")>
	| <STRING: ("string")>
	| <LONG:	 ("long")>
	| <DOUBLE: ("double")>
	| <FLOAT: ("float")>
}


/*
	Below TOKEN definitions after:
	C grammar defintion for use with JavaCC
	Contributed by Doug South (dsouth@squirrel.com.au) 21/3/97
*/

TOKEN : {
 <IDENTIFIER: <LETTER> (<LETTER> | <DIGIT>)*>
//|	<#LETTER: ["$","A"-"Z","_","a"-"z"]>
|	<#LETTER: ["A"-"Z","_","a"-"z"]>
|	<#DIGIT: ["0"-"9"]>
}

TOKEN : {
	<INTEGER_LITERAL: <DECIMAL_LITERAL> (["l","L"])? | <HEX_LITERAL> (["l","L"])? | <OCTAL_LITERAL> (["l","L"])?>
	|	<#DECIMAL_LITERAL: ["1"-"9"] (["0"-"9"])*>
	|	<#HEX_LITERAL: "0" ["x","X"] (["0"-"9","a"-"f","A"-"F"])+>
	|	<#OCTAL_LITERAL: "0" (["0"-"7"])*>
	|	<FLOATING_POINT_LITERAL: (["0"-"9"])+ "." (["0"-"9"])* (<EXPONENT>)? (["f","F","d","D"])? | "." (["0"-"9"])+ (<EXPONENT>)? (["f","F","d","D"])? | (["0"-"9"])+ <EXPONENT> (["f","F","d","D"])? | (["0"-"9"])+ (<EXPONENT>)? ["f","F","d","D"]>
	|	<#EXPONENT: ["e","E"] (["+","-"])? (["0"-"9"])+>
	|	<CHARACTER_LITERAL: "\'" (~["\'","\\","\n","\r"] | "\\" (["n","t","b","r","f","\\","\'","\""] | ["0"-"7"] (["0"-"7"])? | ["0"-"3"] ["0"-"7"] ["0"-"7"])) "\'">
	|	<STRING_LITERAL: "\"" (~["\"","\\","\n","\r"] | "\\" (["n","t","b","r","f","\\","\'","\""] | ["0"-"7"] (["0"-"7"])? | ["0"-"3"] ["0"-"7"] ["0"-"7"]))* "\"">
}

/*
TOKEN :
{
	<WHATEVER: (~["{","}"," ","\t","\n","\r"])*>
}
*/

//==============================================================================
void Start():
{
}
{

/*
	(rule())+ <EOF>
	{
		int aku = 0;
		for(int j=maxnoofconditions; j >=0; j--)
		for(int i=0; i< rulemerit_list.size(); i++)
		{
			if(((Integer)rulemerit_list.get(i)).intValue()==j) 
			{
				aku++;
				rulemerit_list.set(i, new Integer(-aku)); 
			}
		}
	}
*/



	(rule())+ <EOF>
	{
		int aku = 0;
		int max_rul_merit;
		int max_rul_priority;
		int rul_with_max_merit;
		do
		{
			max_rul_merit = -1;
			max_rul_priority = -1;
			rul_with_max_merit = -1;

			for(int i=rulemerit_list.size()-1; i>=0; i--)
			{	
				if(max_rul_merit<((Integer)rulemerit_list.get(i)).intValue())
				{
					max_rul_merit = ((Integer)rulemerit_list.get(i)).intValue();
					rul_with_max_merit = i;

					String[] str;
					str = rule_list.get(i).toString().split(":");
					max_rul_priority = (new Integer(str[5])).intValue();
				}
				else if(max_rul_merit >= 0 && max_rul_merit==((Integer)rulemerit_list.get(i)).intValue())
				{
					String[] str;
					str = rule_list.get(i).toString().split(":");
					int rul_priority = (new Integer(str[5])).intValue();

					if(max_rul_priority < rul_priority)  
					{
						rul_with_max_merit = i;
						max_rul_priority = rul_priority;
					}
				}
			}			
			if(rul_with_max_merit >= 0) 
			{
				aku++;
				rulemerit_list.set(rul_with_max_merit, new Integer(-aku)); 
			}
		}while(rul_with_max_merit >= 0);
	}
}

//==============================================================================
void rule():
{
	int i_slot;
	String current_rulename;
	String s_ruleprior;
}
{
	"(" <RULE> ":" current_rulename = rulename() s_ruleprior = ruleprior() <IF> i_slot = condblock() <THEN> actblock() ")"
	{
		rules_count++;
//		System.out.println("Rule: " + rules_count);
		rule_list.add(current_rulename + ":" + i_slot + ":" + object_list.size() + ":" + noofconditions + ":" + meaobject + ":" + s_ruleprior);
		rulemerit_list.add(new Integer(noofconditions));

		RulesVIR_List.add(VarsInRule_List);
		VarsInRule_List = new ArrayList();

		RulesCIR_List.add(CondsInRule_List);
		CondsInRule_List = new ArrayList();

/*
		System.out.println("Objects of " + s_rulename);
		for(int i=0; i< object_list.size(); i++)
			System.out.println((i+1)+"\t"+object_list.get(i).toString());

		System.out.println("Vars of " + s_rulename);
		for(int i=0; i< varvar_list.size(); i++)
			System.out.println((i+1)+"\t"+varvar_list.get(i).toString());
*/
		if(maxnoofconditions<noofconditions) maxnoofconditions = noofconditions;
		noofconditions = 0;
		if(noofobjects < object_list.size()) noofobjects = object_list.size();

		object_list.clear();
		varvar_list.clear();
		meaobject = -1;

//		execute_code.append("\t}\n\n");

		classX_objects_list.clear();
		class_objectsX_list.clear();
	}
}


//==============================================================================
String rulename():
{
	Token rule_token;
	String s_rule;
}
{
	rule_token = <IDENTIFIER>
	{
		s_rule = rule_token.image;// + ":" + (slot_list.size()+1) + ":" + (-node_list.size()-1);
		
		//if(rule_list.contains(s_rule)) System.out.println("\t---Rule " + s_rule + "already defined");
		//rule_list.add(s_rule);
		
		if(rulename_list.contains(rule_token.image)) 
			System.out.println("("+rule_token.beginLine +")\tRule " + rule_token.image	+" already defined");
		rulename_list.add(s_rule);
	
		if(rules_count>0) execute_code.append("\t}\n\n");
		execute_code.append("\n\tpublic void Execute_"+ s_rule 
			+ "(ReteToken token) throws NoSuchFieldException, IllegalAccessException, IOException, org.omg.CORBA.TypeCodePackage.BadKind\n\t{\n");
		
		return s_rule;
	}
}

//==============================================================================
String ruleprior():
{
	Token t;
}
{
	t = <FLOATING_POINT_LITERAL>
	{
		return t.image;
	}
	| t = <INTEGER_LITERAL>
	{
		return t.image;
	}
}

//==============================================================================
int condblock():
{
//	Token object_token;
	Token slot_token;
	int i_slot;
	String s_node;
	String s_node2;
	int slot1, slot2;
}
{
	LOOKAHEAD(2) "(" <CLASS> ":" class_token = <IDENTIFIER> <OBJ> ":" "$" obj_token =<IDENTIFIER> i_slot = slot_ops()")"
	{
//		cond_blocks+=100;

		if(!class_objectsX_list.contains(obj_token.image))
		{ 
			class_objectsX_list.add(obj_token.image);
			classX_objects_list.add(class_token.image);
		}

		return i_slot;
	}
	| "(" slot1 = condblock() (slot_token = <OR>|slot_token = <AND>) slot2 = condblock() ")"
	{
		noofconditions++;
//		cond_blocks++;
		if(slot_token.image.compareTo("OR")==0) 
		{
			s_node = "OR " + slot1 +" "+ slot2;
			s_node2 = "OR " + slot2 +" "+ slot1;
			if(node_list.contains(s_node))
			{
//				System.out.println(s_node + "+");
				return -(node_list.indexOf(s_node)+1);
			}
			else if(node_list.contains(s_node2))
			{
//				System.out.println(s_node2 + "+");
				return -(node_list.indexOf(s_node2)+1);
			}
			else
			{
//				System.out.println(s_node + "-");
				node_list.add(s_node);
				return -node_list.size();
			}
		}
		else
		{
			s_node = "AND " + slot1 +" "+ slot2;
			s_node2 = "AND " + slot2 +" "+ slot1;
			if(node_list.contains(s_node))
			{
//				System.out.println(s_node + "+");
				return -(node_list.indexOf(s_node)+1);
			}
			else if(node_list.contains(s_node2))
			{
//				System.out.println(s_node2 + "+");
				return -(node_list.indexOf(s_node2)+1);
			}
			else
			{
//				System.out.println(s_node + "-");
				node_list.add(s_node);
				return -node_list.size();
			}
		}
	}
}

//==============================================================================
int slot_ops():
{
	Token slot_token;
	String s_expr;
	String s_lop;
	String s_slot;
	String s_reduced;
	String s_variable;
	String s_node;
	String s_node2;
//	boolean already;
	int slot1, slot2;
	Integer no_of_alias;
	int object, to_reduce;
}
{
	LOOKAHEAD(2) "(" slot_token = <IDENTIFIER> s_lop = lop() s_expr = expr()")"
	{
		object = object_list.indexOf(obj_token.image);
		if(object<0) 
		{
			object = object_list.size();
			object_list.add(obj_token.image);
		}	

		if(meaobject<0) meaobject = object;

		s_slot = class_token.image + ":" + slot_token.image;
		if(!VarsInRule_List.contains(s_slot)) VarsInRule_List.add(s_slot);

		s_slot = class_token.image + ":" + object + ":" + slot_token.image 
				 + ":" + s_lop + ":" + s_expr;
		s_reduced = class_token.image + ":" + slot_token.image 
				 + ":" + s_lop + ":" + s_expr;
		s_variable = class_token.image + ":" + slot_token.image;

		if(!(s_lop == "==" || s_lop == "EQ")) alias_condition = false;

		if(slot_list.contains(s_slot))
		{
//			System.out.println("\t--Already in");
			alias_condition = true;
			anyalias_condition = false;
			variabletype = 0;

			Integer trds = new Integer(reduced_list.indexOf(s_reduced)+1);
			if(!CondsInRule_List.contains(trds)) CondsInRule_List.add(trds);

			return slot_list.indexOf(s_slot)+1;
		}
		else
		{
			int type;
			int ind = 0;
			if(reduced_list.contains(s_reduced))
			{
				to_reduce=reduced_list.indexOf(s_reduced)+1;
			}
			else
			{
				reduced_list.add(s_reduced);
				to_reduce = reduced_list.size();
			}

			Integer trds = new Integer(to_reduce);
			if(!CondsInRule_List.contains(trds)) CondsInRule_List.add(trds);
//			System.out.println("\t++Added");

			if(anyalias_condition)
			{
				if(!alias_condition)
				{	
					no_of_alias = new Integer(slot_list.size()+1);
					anyalias_list.add(no_of_alias);
					ind = -anyalias_list.size();
				}
			}

			if(alias_condition)
			{
				ind = aliasname_list.indexOf(s_expr);
				if(ind < 0)
				{
					aliasname_list.add(s_expr);
					ind = aliasname_list.size();
				}
				else ind++;
			}

			slot_list.add(s_slot);
			tokensender_list.add((to_reduce) + ":" + (object) + ":" + ind + ":" + variabletype);
			
			if((variabletype & 3) > 0 && (variabletype & 4) > 0)
				System.out.println("(" +slot_token.beginLine+ ")\tMixed strings and numbers in: " + s_reduced);
			variabletype = 0;

			alias_condition = true;
			anyalias_condition = false;

			return slot_list.size();
		}
	}
	| "(" slot1 = slot_ops() (slot_token = <OR>| slot_token = <AND>) slot2 = slot_ops()")"
	{
		noofconditions++;
		if(slot_token.image.compareTo("OR")==0) 
		{
			s_node = "OR " + slot1 +" "+ slot2;
			s_node2 = "OR " + slot2 +" "+ slot1;
			if(node_list.contains(s_node))
			{
//				System.out.println(s_node + "+");
				return -(node_list.indexOf(s_node)+1);
			}
			else if(node_list.contains(s_node2))
			{
//				System.out.println(s_node2 + "+");
				return -(node_list.indexOf(s_node2)+1);
			}
			else
			{
//				System.out.println(s_node + "-");
				node_list.add(s_node);
				return -node_list.size();
			}
		}
		else
		{
			s_node = "AND " + slot1 +" "+ slot2;
			s_node2 = "AND " + slot2 +" "+ slot1;
			if(node_list.contains(s_node))
			{
//				System.out.println(s_node + "+");
				return -(node_list.indexOf(s_node)+1);
			}
			else if(node_list.contains(s_node2))
			{
//				System.out.println(s_node2 + "+");
				return -(node_list.indexOf(s_node2)+1);
			}
			else
			{
//				System.out.println(s_node + "-");
				node_list.add(s_node);
				return -node_list.size();
			}
		}
	}
}

//==============================================================================
void actblock():
{}
{
	"(" (actionstatement())+ ")"
}

//==============================================================================
void actionstatement():
{
	Token t_class;
	Token t_object;

	Token t_name;
	//StringBuffer buffer = new StringBuffer();
	ArrayList vsub_list = new ArrayList();
	String s_expr;
	String outputstring;
	class_variables_list.clear();
	String s_lsno;
	String s_lnno;
}
{
	LOOKAHEAD(2) "(" <MODIFY>
		(
		("(" <OBJ> ":" "$" t_object = <IDENTIFIER> ("(" variableitem(vsub_list) ")")* ")" )
		|(<OBJ> ":" "$" t_object = <IDENTIFIER> ("(" variableitem(vsub_list) ")")* )
		)
		s_lsno = lsno() s_lnno = lnno()")"
	{
/*
		outputstring = "// MODIFY: object = " + t_object.image + "\n";
		execute_code.append(outputstring);

		for(int i=0; i< vsub_list.size(); i++)
		{
			String[] sp = vsub_list.get(i).toString().split("=");

			outputstring = "\t\too = token.object[" + object_list.indexOf(t_object.image) + "];\n";
			execute_code.append(outputstring);
			outputstring = "\t\tif(oo != null)\n";
			execute_code.append(outputstring);
			outputstring = "\t\t{\n";
			execute_code.append(outputstring);
			outputstring = "\t\t\tff = oo.getClass().getDeclaredField(\"" + sp[0] + "\");\n";
			execute_code.append(outputstring);
			outputstring = "\t\t\tSetField(oo, ff, " + sp[1] + ");\n";
			execute_code.append(outputstring);
			outputstring = "\t\t\tnetwork.MemSetObject(oo);\n";
			execute_code.append(outputstring);
			outputstring = "\t\t}\n";
			execute_code.append(outputstring);
		}
*/

		int coi = class_objectsX_list.indexOf(t_object.image);
		if(coi>=0)	
			for(int i=0; i< class_variables_list.size(); i++)
				MODIFY_list.add(classX_objects_list.get(coi)+":"+class_variables_list.get(i)+" "+rules_count+" "+s_lsno+" "+s_lnno);

		outputstring = "// MODIFY: object = " + t_object.image + "\n";
		execute_code.append(outputstring);

		outputstring = "\t\too = token.object[" + object_list.indexOf(t_object.image) + "];\n";
		execute_code.append(outputstring);
		outputstring = "\t\tif(oo != null)\n";
		execute_code.append(outputstring);
		outputstring = "\t\t{\n";
		execute_code.append(outputstring);

		for(int i=0; i< vsub_list.size(); i++)
		{
			String[] sp = vsub_list.get(i).toString().split("=");

			outputstring = "\t\t\tff = oo.getClass().getDeclaredField(\"" + sp[0] + "\");\n";
			execute_code.append(outputstring);

			if(sp[1].startsWith("token.variable"))
			{
				outputstring = "\t\t\tSetField(oo, ff, " + sp[1] + ", token.typeofvariable" + sp[1].substring(14) + ");\n";
				execute_code.append(outputstring);
			}
			else if(sp[1].startsWith(" token.variable"))
			{
				outputstring = "\t\t\tSetField(oo, ff, " + sp[1] + ", token.typeofvariable" + sp[1].substring(15) + ");\n";
				execute_code.append(outputstring);
			}
			else
			{
				outputstring = "\t\t\tSetField(oo, ff, " + sp[1] + ");\n";
				execute_code.append(outputstring);
			}

			if(modeII)
			{
				outputstring = "\t\t\tff = oo.getClass().getDeclaredField(\"_P_H_Ep_" + sp[0] + "\");\n";
				execute_code.append(outputstring);
// There are three possibilities for sp[1]:
// - local variable set with READ PROMPT - $Rule_no$Name_of_variable
// - constant value - 1263
// - global variable - token.variable[1]
				if(sp[1].startsWith(" $"))
				{
					outputstring = "\t\t\tSetField(oo, ff, ProbabilityFromCertainty(_Certainty_" + sp[1].substring(1)+ ", " + s_lsno + ", " + s_lnno + "));\n";
					execute_code.append(outputstring);
				}
				else
				{
					outputstring = "\t\t\tSetField(oo, ff, ProbabilityFromTotal(token.P_H_Ep_total, " + s_lsno + ", " + s_lnno + "));\n";
					execute_code.append(outputstring);
				}
			}

/*
			if(modeII)
			{
				String temp;
// There are three possibilities for sp[1]:
// - local variable set with READ PROMPT - $Rule_no$Name_of_variable
// - constant value - 1263
// - global variable - token.variable[1]
				if(sp[1].startsWith(" $") temp = "_Certainty_" + sp[1].substring(1);
				else if(sp[1].startsWith("token.variable") temp = "token.certaintyofvariable" + sp[14].substring(1);
				else temp = "_Certainty_of_rule_";
				outputstring = "\t\t\tff = oo.getClass().getDeclaredField(\"_Certainty_" + sp[0] + "\");\n";
				execute_code.append(outputstring);
				outputstring = "\t\t\tSetField(oo, ff, " + temp + ");\n";
				execute_code.append(outputstring);
				outputstring = "\t\t\tff = oo.getClass().getDeclaredField(\"_LS_" + sp[0] + "\");\n";
				execute_code.append(outputstring);
				outputstring = "\t\t\tSetField(oo, ff, " + s_lsno + ");\n";
				execute_code.append(outputstring);
				outputstring = "\t\t\tff = oo.getClass().getDeclaredField(\"_LN_" + sp[0] + "\");\n";
				execute_code.append(outputstring);
				outputstring = "\t\t\tSetField(oo, ff, " + s_lnno + ");\n";
				execute_code.append(outputstring);
			}
*/
		}

			
		outputstring = "\t\t}\n";
		execute_code.append(outputstring);
//		outputstring = "\t\telse network.MemSetObject(oo, null);\n";
		outputstring = "\t\tnetwork.MemSetObject(oo, null);\n";
		execute_code.append(outputstring);
	}

	|(
/*
	LOOKAHEAD(2) "(" <MAKE> 
		(
		"(" <CLASS> ":" <IDENTIFIER> <OBJ> ":" "$" <IDENTIFIER> ("(" <IDENTIFIER> expr_ia() ")")* ")" ")"
		|<CLASS> ":" <IDENTIFIER> <OBJ> ":" "$" <IDENTIFIER> ("(" <IDENTIFIER> expr_ia() ")")* ")"
		)
	|(
*/
	
	LOOKAHEAD(2) "(" <MAKE> 
		(
		"(" <CLASS> ":" t_class = <IDENTIFIER> <OBJ> ":" t_object = <IDENTIFIER> ("(" variableitem(vsub_list) ")")* ")" ")"
		|<CLASS> ":" t_class = <IDENTIFIER> <OBJ> ":" t_object = <IDENTIFIER> ("(" variableitem(vsub_list) ")")* ")"
		)
	{

		MAKE_list.add(t_class.image+":"+t_object.image+" "+rules_count);

		outputstring = "// MAKE: class = " + t_class.image + " object = " + t_object.image + "\n";
		execute_code.append(outputstring);

		outputstring = "\t\t"+ t_class.image + " " + t_object.image + " = new " + t_class.image +"();\n";
		execute_code.append(outputstring);

		for(int i=0; i< vsub_list.size(); i++)
		{
			outputstring = "\t\t" + t_object.image +"."+ vsub_list.get(i).toString()+";\n";
			execute_code.append(outputstring);
			if(modeII)
			{
				String name = vsub_list.get(i).toString().substring(0, vsub_list.get(i).toString().indexOf("="));
/*
				outputstring = "\t\t\t" + t_object.image +"._LS_"+ name + " = 0;\n";
				execute_code.append(outputstring);
				outputstring = "\t\t\t" + t_object.image +"._LN_"+ name + " = 0;\n";
				execute_code.append(outputstring);
*/
				outputstring = "\t\t\t" + t_object.image +"._P_H_Ep_"+ name + " = 1.0f;\n";
				execute_code.append(outputstring);
			}
		}
	 
		outputstring = "\t\tnetwork.MemSetObject((Object)" + t_object.image + ", \"" + t_object.image + "\");\n\n";
		execute_code.append(outputstring);
	}
	|(
	LOOKAHEAD(2)"(" <REMOVE> <OBJ> ":" "$" t_object = <IDENTIFIER> ")"
	{
		outputstring = "// REMOVE: object = " + t_object.image + "\n";
		execute_code.append(outputstring);

		outputstring = "\t\tnetwork.MemSetObject(null, \"" + t_object.image + "\");\n\n";
		execute_code.append(outputstring);
	}
	|(
	LOOKAHEAD(2)"(" <PRINT> s_expr = expr_ia() (LOOKAHEAD(2) ("," | "|")? variableitem2(vsub_list))*")"
	{
		outputstring = "// PRINT\n";
		execute_code.append(outputstring);


//???????????????????????????????????????????????????????????????????????????


		vsub_list.add(0, s_expr);
		for(int k = 0; k < vsub_list.size(); k++)
		{
			outputstring = "\t\tPrint((String)" + vsub_list.get(k) + ");\n";
			//outputstring = "\t\tSystem.out.print(" + vsub_list.get(k) + ");\n";
			execute_code.append(outputstring);
		};
		outputstring = "\t\tPrint(\"\\n\");\n";
//		outputstring = "\t\tSystem.out.print(\"\\n\");\n";
		execute_code.append(outputstring);
	}
	|(
	LOOKAHEAD(2)"(" <READ> <PROMPT> ":" t_object = <STRING_LITERAL> (",")? <VAR> ":" "$" t_name = <IDENTIFIER> (",")? <TYPE> ":" t_class = <IDENTIFIER>")"
	{
		outputstring = "// READ PROMPT: object = " + t_object.image + " name = " + t_class.image + ":" + t_name.image+ "\n";
		execute_code.append(outputstring);

		//outputstring = "\t\tSystem.out.print(" + t_object.image + ");\n";
		//outputstring = "\t\tPrint(" + t_object.image + ");\n";
		//execute_code.append(outputstring);

		if(t_class.image.compareTo("S")==0 || t_class.image.compareTo("s")==0)
			outputstring = "\t\tString $" + (rules_count+1) + "$" + t_name.image + " = ReadLine(" + t_object.image + ");\n";
		else if(t_class.image.compareTo("D")==0 || t_class.image.compareTo("d")==0)
			outputstring = "\t\tdouble $" + (rules_count+1) + "$" + t_name.image + " = (new Double(ReadLine(" + t_object.image + "))).doubleValue();\n";
		else if(t_class.image.compareTo("I")==0 || t_class.image.compareTo("i")==0)
			outputstring = "\t\tint $" + (rules_count+1) + "$" + t_name.image + " = (new Integer(ReadLine(" + t_object.image + "))).intValue();\n";
		else outputstring = "";
		execute_code.append(outputstring);

		if(modeII)
		{
			outputstring = "\t\tfloat _Certainty_$" + (rules_count+1) + "$" + t_name.image + " = ReadLine_Certainty;\n";
			execute_code.append(outputstring);
		}
	}
	|(
	LOOKAHEAD(2)"(" <BIND> <VAR> ":" "$" t_name = <IDENTIFIER> s_expr = expr_ia()")"
	{
		outputstring = "// BIND: " + t_name.image+ " = " + s_expr + "\n";
		execute_code.append(outputstring);
		outputstring = "\t\t$" + (rules_count+1) + "$" + t_name.image+ " = " + s_expr + ";\n";
		execute_code.append(outputstring);
	}
	|(
	LOOKAHEAD(2)"(" <EXECUTE> t_name = <IDENTIFIER> ")"
	{
		outputstring = "// EXECUTE: " + t_name.image + "\n";
		execute_code.append(outputstring);
		outputstring = "\t\tRuntime.getRuntime().exec(\"" + t_name.image + "\");\n";
		execute_code.append(outputstring);
	}
	| 
	"(" <DISPLAY> t_name = <STRING_LITERAL> ")"
	{
		String[] st;
		st = t_name.image.split("\"");

		outputstring = "// DISPLAY: " + t_name.image + "\n";
		execute_code.append(outputstring);
//NT Win2000	
//		outputstring = "\t\tRuntime.getRuntime().exec(\"cmd /c \\\""+ st[1] +"\\\"\");\n";
		outputstring = "\t\tDisplay(\"" + st[1] + "\");\n";
		execute_code.append(outputstring);
//Win9x
//		outputstring = "Runtime.getRuntime().exec(\"start "+ st[0] +"\");"
//		execute_code.append(outputstring);
	}
	))))))//)
}



//==============================================================================
void variableitem(ArrayList vsub_list):
{
	String s_subst;
	Token t_iden; // iDEN < GSM
}
{
	t_iden = <IDENTIFIER> s_subst = expr_ia() 
	{
		vsub_list.add(t_iden.image +"="+ s_subst);
		class_variables_list.add(t_iden.image);
	}
}


//==============================================================================
void variableitem2(ArrayList vsub_list):
{
	String s_subst;
}
{
	s_subst = expr_ia() 
	{
		vsub_list.add(s_subst);
	}
}

//==============================================================================
String lop():
{
	String s_return;
}
{
	<GT>{s_return = ">"; return s_return;} 
	| <GE> {s_return = ">="; return s_return;} 
	| <LT> {s_return = "<"; return s_return;} 
	| <LE> {s_return = "<="; return s_return;} 
	| <EQ> {s_return = "=="; return s_return;} 
	| <NE>{s_return = "!="; return s_return;} 
}

//==============================================================================
String expr():
{
	String s_return;
	String s_next = "";
}
{
	s_return = basicexpr() (s_next = nextexpr())?
	{
		if(s_next.length()>0) alias_condition = false;
		return s_return + s_next;
	}

	| "("s_next = expr()")"
	{
		return "(" + s_next + ")";
	}
}

//==============================================================================
String nextexpr():
{
	String s_next = "";
	String s_aop;
	String s_expr;
	String s_return;
}
{
	s_aop = aop() s_expr = expr() (LOOKAHEAD(2) s_next = expr())?
	{
		s_return = s_aop + s_expr + s_next;
		return s_return;
	}
}

//==============================================================================
String basicexpr():
{
	String basic_here;
}
{
	//0 - unknown, 1 - int, 2 - double, 4 - string, 8 - $variable
	<FLOATING_POINT_LITERAL>
	{
		//if(variabletype < 2) variabletype = 2;
		//else if(variabletype > 2) variabletype = 99;
		variabletype = variabletype | 2;
		alias_condition = false;
		basic_here = token.image;
		return basic_here;
	}
	| <INTEGER_LITERAL>
	{
		//if(variabletype < 1) variabletype = 1;
		//else if(variabletype > 2) variabletype = 99;
		variabletype = variabletype | 1;
		alias_condition = false;
		basic_here = token.image;
		return basic_here;
	}
	| <STRING_LITERAL>
	{
		//if(variabletype != 3 && variabletype != 0)	variabletype = 99;
		//else variabletype = 3;
		variabletype = variabletype | 4;
		alias_condition = false;
		basic_here = token.image;
		return basic_here;
	}
	| "$" <IDENTIFIER>
	{
		variabletype = variabletype | 8;
		anyalias_condition = true;
		basic_here = " $" + (rules_count+1) + "$" + token.image + " ";
//		System.out.println("-------------------:" + basic_here + "	 " +slot_list.size());
		return basic_here;
	}
/*
	| <VAR>
	{
		alias_condition = false;
		basic_here = "$VAR";
		return basic_here;
	}
*/
}

// _ia called from actionstatement()
//==============================================================================

String expr_ia():
{
	String s_return;
	String s_next = "";
}
{
	s_return = basicexpr_ia() (s_next = nextexpr_ia())?
	{
		return s_return + s_next;
	}

	| "("s_next = expr()")"
	{
		return "(" + s_next + ")";
	}
}

//==============================================================================
String nextexpr_ia():
{
	String s_next = "";
	String s_aop;
	String s_expr;
	String s_return;
}
{
	s_aop = aop() s_expr = expr_ia() (LOOKAHEAD(2) s_next = expr_ia())?
	{
		s_return = s_aop + s_expr + s_next;
		return s_return;
	}
}



//==============================================================================
String basicexpr_ia():
{
	String basic_here;
}
{
	//0 - unknown, 1 - int, 2 - double, 4 - string, 8 - $variable
	<FLOATING_POINT_LITERAL>
	{
		basic_here = token.image;
		return basic_here;
	}
	| <INTEGER_LITERAL>
	{
		basic_here = token.image;
		return basic_here;
	}
	| <STRING_LITERAL>
	{
		basic_here = token.image;
		return basic_here;
	}
	| "$" <IDENTIFIER>
	{
		basic_here = " $" + (rules_count+1) + "$" + token.image + " ";

		if(!aliasname_list.contains(basic_here) &&
			 !aliasnamd_list.contains(basic_here))
		{
			aliasnamd_list.add(basic_here);
		}

		int indx = aliasname_list.indexOf(basic_here);
		if(indx>=0)
		{
			basic_here = " token.variable[" + indx + "] ";
		}

		return basic_here;
	}
}

//==============================================================================
String lsno():
{
	Token t;
}
{
	t = <FLOATING_POINT_LITERAL>
	{
		return t.image;
	}
	| t = <INTEGER_LITERAL>
	{
		return t.image;
	}
}

//==============================================================================
String lnno():
{
	Token t;
}
{
	t = <FLOATING_POINT_LITERAL>
	{
		return t.image;
	}
	| t = <INTEGER_LITERAL>
	{
		return t.image;
	}
}

//==============================================================================
String aop():
{
	String s_return;
}
{
	"+"{s_return= "+"; return s_return;} 
	|"-"{s_return= "-"; return s_return;} 
	|"*"{s_return= "*"; return s_return;} 
	|"/"{s_return= "/"; return s_return;} 
}


//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//====	 RUL -> TREE Converter	================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
ArrayList StartTree():
{
	ArrayList at = new ArrayList();
	ArrayList nt_ = new ArrayList();
}
{
	<MODULE> <IDENTIFIER> "{" (structure(at))* "}" (";")*
	{
		at.add(0, "\rClasses");
		nt_.add("");
		at.add(nt_);
		return at;
	}
	| (rule_tree(at))+ <EOF>
	{
		at.add(0, "\rRules");
		nt_.add("");
		at.add(nt_);
		return at;
	}
	
}

//==============================================================================
void rule_tree(ArrayList at):
{
	String s_rulename;
	String s_ruleprior;
	ArrayList nt = new ArrayList();
	ArrayList nti = new ArrayList();
	ArrayList ntt = new ArrayList();
}
{
	"(" <RULE> ":" s_rulename = rulename_tree() s_ruleprior = ruleprior_tree() <IF> condblock_tree(nti) <THEN> actblock_tree(nt) ")"
	{
		nti.add(0, "\rIF");
		nt.add(0, s_rulename + " " + s_ruleprior);
		nt.add(1, nti);
		at.add(nt);
	}
}
//==============================================================================
void structure(ArrayList at):
{
	Token token;
	ArrayList nt = new ArrayList();
	ArrayList nt_ = new ArrayList();
}
{
	<STRUCT> token = <IDENTIFIER> "{" (declaration(nt))* "}" (";")*
	{
		nt.add(0, token.image);
		nt_.add("");
		nt.add(nt_);

		at.add(nt);
	}
}
//==============================================================================
void declaration(ArrayList at):
{
	Token token;
	ArrayList nt = new ArrayList();;
}
{
	<STRING> token = <IDENTIFIER> (";")+
	{
		nt.add("string " + token.image);
		at.add(nt);
	}
	| <LONG> token = <IDENTIFIER> (";")+
	{
		nt.add("long " + token.image);
		at.add(nt);
	}
	| <DOUBLE> token = <IDENTIFIER> (";")+
	{
		nt.add("double " + token.image);
		at.add(nt);
	}
//PMS - zalozenie, ze jest propagacja prawdopodobienstwa, floaty tylko dla LS i LN (float _ls_zmienna)
	| <FLOAT> token = <IDENTIFIER> (";")+
	{
//		nt.add("double " + token.image);
//		at.add(nt);
	}
}
//==============================================================================
String rulename_tree():
{
	Token rule_token;
}
{
	rule_token = <IDENTIFIER>
	{
		return rule_token.image;
	}
}
//==============================================================================
String ruleprior_tree():
{
	Token t;
}
{
	t = <FLOATING_POINT_LITERAL>
	{
		return token.image;
	}
	|t = <INTEGER_LITERAL>
	{
		return token.image;
	}
}
//==============================================================================
void condblock_tree(ArrayList at):
{
	ArrayList nt = new ArrayList();
	ArrayList nnt = new ArrayList();
	ArrayList nnt1 = new ArrayList();
	ArrayList nnt2 = new ArrayList();
	Token slot_token;
}
{
	LOOKAHEAD(2) "(" <CLASS> ":" class_token = <IDENTIFIER> <OBJ> ":" "$" obj_token =<IDENTIFIER> slot_ops_tree(nt)")"
	{
		nt.add(0, "CLASS: " + class_token.image + " OBJ: $" + obj_token.image);
		at.add(nt);
	}

	| "(" condblock_tree(nt) (slot_token = <OR>|slot_token = <AND>) condblock_tree(nt) ")"
	{
		nt.add(0, slot_token.image);
		at.add(nt);
	}
}

//==============================================================================
void slot_ops_tree(ArrayList at):
{
	ArrayList nnt = new ArrayList();
//	ArrayList nnt1 = new ArrayList();
//	ArrayList nnt2 = new ArrayList();
	String s_lop;
	String s_expr;
	Token slot_token;
}
{
	LOOKAHEAD(2) "(" slot_token = <IDENTIFIER> s_lop = lop() s_expr = expr_tree()")"
	{
		nnt.add(0, slot_token.image + s_lop + s_expr);
		at.add(nnt);
	}
	| "(" slot_ops_tree(nnt) (slot_token = <OR>| slot_token = <AND>) slot_ops_tree(nnt)")"
	{
		nnt.add(0, slot_token.image);
//		nnt.add(nnt);
//		nnt.add(nnt);
		at.add(nnt);
	}
}

//==============================================================================
void actblock_tree(ArrayList at):
{
	ArrayList nt = new ArrayList();
	ArrayList nt_ = new ArrayList();
}
{
	"(" (actionstatement_tree(nt))+ ")"
	{
		nt.add(0, "\rTHEN");
		nt_.add("");
		nt.add(nt_);
		at.add(nt);
	}
}

//==============================================================================
void actionstatement_tree(ArrayList at):
{
	Token t_class;
	Token t_object;
	Token t_name;
	String s1, s2;
	String s_expr;
	ArrayList nt = new ArrayList();
	ArrayList nt_ = new ArrayList();
}
{
	LOOKAHEAD(2) "(" <MODIFY>
		(
		("(" <OBJ> ":" "$" t_object = <IDENTIFIER> ("(" variableitem_tree(nt) ")")* ")" )
		|(<OBJ> ":" "$" t_object = <IDENTIFIER> ("(" variableitem_tree(nt) ")")* )
		)
		s1 = lno_tree() s2 = lno_tree()")"
	{
		nt.add(0, "MODIFY OBJ: $" + t_object.image +" * "+ s1 + " " + s2);
		nt_.add("");
		nt.add(nt_);
		at.add(nt);
	}
/*
	|(
	LOOKAHEAD(2) "(" <MAKE> 
		(
		"(" <CLASS> ":" <IDENTIFIER> <OBJ> ":" "$" <IDENTIFIER> ("(" <IDENTIFIER> expr_ia() ")")* ")" ")"
		|<CLASS> ":" <IDENTIFIER> <OBJ> ":" "$" <IDENTIFIER> ("(" <IDENTIFIER> expr_ia() ")")* ")"
		)*/
	|(
	LOOKAHEAD(2) "(" <MAKE> 
		(
		"(" <CLASS> ":" t_class = <IDENTIFIER> <OBJ> ":" t_object = <IDENTIFIER> ("(" variableitem_tree(nt) ")")* ")" ")"
		|<CLASS> ":" t_class = <IDENTIFIER> <OBJ> ":" t_object = <IDENTIFIER> ("(" variableitem_tree(nt) ")")* ")"
		)
	{
		nt.add(0, "MAKE CLASS: " + t_class.image + " OBJ: " + t_object.image);
		nt_.add("");
		nt.add(nt_);
		at.add(nt);
	}
	|(
	LOOKAHEAD(2)"(" <REMOVE> <OBJ> ":" "$" t_object = <IDENTIFIER> ")"
	{
		nt.add(0, "REMOVE OBJ: $" + t_object.image);
		at.add(nt);
	}
	|(
	LOOKAHEAD(2)"(" <PRINT> s_expr = expr_tree() (LOOKAHEAD(2) ("," | "|")? variableitem2_tree(nt))*")"
	{
		ArrayList nnt = new ArrayList();

		nnt.add(s_expr);
		nt.add(0, nnt);
		nt.add(0, "PRINT " );
		nt_.add("");
		nt.add(nt_);
		at.add(nt);
	}
	|(
	LOOKAHEAD(2)"(" <READ> <PROMPT> ":" t_object = <STRING_LITERAL> (",")? <VAR> ":" "$" t_name = <IDENTIFIER> (",")? <TYPE> ":" t_class = <IDENTIFIER>")"
	{
		nt.add(0, "READ PROMPT: " + t_object.image + " VAR: $" + t_name.image + " TYPE: " + t_class.image);
		at.add(nt);
	}
	|(
	LOOKAHEAD(2)"(" <BIND> <VAR> ":" "$" t_name = <IDENTIFIER> s_expr = expr_tree()")"
	{
		nt.add(0, "BIND VAR: $" + t_name.image + " " + s_expr);
		at.add(nt);
	}
	|(
	LOOKAHEAD(2)"(" <EXECUTE> t_name = <IDENTIFIER> ")"
	{
		nt.add(0, "EXECUTE " + t_name.image);
		at.add(nt);
	}
	| 
	"(" <DISPLAY> t_name = <STRING_LITERAL> ")"
	{
		nt.add(0, "DISPLAY " + t_name.image);
		at.add(nt);
	}
	))))))//)
}
//==============================================================================
void variableitem_tree(ArrayList at):
{
	ArrayList nt = new ArrayList();
	String s_subst;
	Token t_iden;
}
{
	t_iden = <IDENTIFIER> s_subst = expr_tree() 
	{
		nt.add(t_iden.image + " " + s_subst);
		at.add(nt);
	}
}

//==============================================================================
void variableitem2_tree(ArrayList at):
{
	ArrayList nt = new ArrayList();
	String s_subst;
}
{
	s_subst = expr_tree() 
	{
		nt.add(s_subst);
		at.add(nt);
	}
}

//==============================================================================
String expr_tree():
{
	String s_return;
	String s_next = "";
}
{
	s_return = basicexpr_tree() (s_next = nextexpr_tree())?
	{
		if(s_next.length()>0) alias_condition = false;
		return s_return + s_next;
	}

	| "("s_next = expr_tree()")"
	{
		return "(" + s_next + ")";
	}
}

//==============================================================================
String nextexpr_tree():
{
	String s_next = "";
	String s_aop;
	String s_expr;
	String s_return;
}
{
	s_aop = aop() s_expr = expr_tree() (LOOKAHEAD(2) s_next = expr_tree())?
	{
		s_return = s_aop + s_expr + s_next;
		return s_return;
	}
}

//==============================================================================
String basicexpr_tree():
{
}
{
	<FLOATING_POINT_LITERAL>
	{
		return token.image;
	}
	| <INTEGER_LITERAL>
	{
		return token.image;
	}
	| <STRING_LITERAL>
	{
		return token.image;
	}
	| "$" <IDENTIFIER>
	{
		return "$" + token.image;
	}
/*
	| <VAR>
	{
		alias_condition = false;
		basic_here = "$VAR";
		return basic_here;
	}
*/
}

//==============================================================================
String lno_tree():
{
	Token t;
}
{
	(t = <FLOATING_POINT_LITERAL> | t = <INTEGER_LITERAL>)
	{
		return t.image;
	}
}



//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//====	 RUL -> CLASS Converter	===============================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
//==============================================================================
ArrayList _StartClasses():
{
}
{
	(rule_class())+ <EOF>
	{
		class_variables_list.add(0, "\rClasses");
		// PrintTree(class_variables_list, 0);
		return class_variables_list;
	}
}

//==============================================================================
void rule_class():
{
}
{
	"(" <RULE> ":" rulename_class() ruleprior_class() <IF> condblock_class() <THEN> actblock_class() ")"
	{
		classX_objects_list.clear();
		class_objectsX_list.clear();

		variable_type = 0;
//		class_token = null;
	}
}
//==============================================================================
void rulename_class():
{
}
{
	<IDENTIFIER>
	{
	}
}
//==============================================================================
void ruleprior_class():
{
}
{
	<FLOATING_POINT_LITERAL>
	{
	}
	|<INTEGER_LITERAL>
	{
	}
}
//==============================================================================
void condblock_class():
{
	ArrayList nt = new ArrayList();
	Token t_class;
	Token t_object;
}
{
	LOOKAHEAD(2) "(" <CLASS> ":" t_class = <IDENTIFIER> <OBJ> ":" "$" t_object = <IDENTIFIER> slot_ops_class(nt)")"
	{
		InsertVariablesToClasses(t_class.image, variable_type, nt, class_variables_list);

		if(!class_objectsX_list.contains(t_object.image))
		{ 
			class_objectsX_list.add(t_object.image);
			classX_objects_list.add(t_class.image);
		}
		variable_type = 0;
		class_token = null;
	}
	| "(" condblock_class() (<OR>|<AND>) condblock_class() ")"
	{
	}
}

//==============================================================================
void slot_ops_class(ArrayList nt):
{
	Token tok;
}
{
	LOOKAHEAD(2) "(" tok = <IDENTIFIER> lop() expr_class()")"
	{
		if(!nt.contains(tok.image)) nt.add(tok.image);
	}
	| "(" slot_ops_class(nt) (<OR>|<AND>) slot_ops_class(nt)")"
	{
	}
}

//==============================================================================
void actblock_class():
{
}
{
	"(" (actionstatement_class())+ ")"
	{
	}
}

//==============================================================================
void actionstatement_class():
{
	ArrayList nt = new ArrayList();
	Token t_class;
	Token t_object;
}
{
	LOOKAHEAD(2) "(" <MODIFY>
		(
		("(" <OBJ> ":" "$" t_object = <IDENTIFIER> ("(" variableitem_class(nt) ")")* ")" )
		|(<OBJ> ":" "$" t_object = <IDENTIFIER> ("(" variableitem_class(nt) ")")* )
		)
		lno_class() lno_class()")"
	{
		int ind = class_objectsX_list.indexOf(t_object.image);
		if(ind>0)
			InsertVariablesToClasses(classX_objects_list.get(ind).toString(), variable_type, nt, class_variables_list);
		variable_type = 0;
	}
	|(
	LOOKAHEAD(2) "(" <MAKE> 
		(
		"(" <CLASS> ":" t_class = <IDENTIFIER> <OBJ> ":" <IDENTIFIER> ("(" variableitem_class(nt) ")")* ")" ")"
		|<CLASS> ":" t_class = <IDENTIFIER> <OBJ> ":" <IDENTIFIER> ("(" variableitem_class(nt) ")")* ")"
		)
	{
		InsertVariablesToClasses(t_class.image, variable_type, nt, class_variables_list);
		variable_type = 0;
	}
	|(
	LOOKAHEAD(2)"(" <REMOVE> <OBJ> ":" "$" <IDENTIFIER> ")"
	{
	}
	|(
	LOOKAHEAD(2)"(" <PRINT> expr_tree() (LOOKAHEAD(2) ("," | "|")? variableitem2_class())*")"
	{
	}
	|(
	LOOKAHEAD(2)"(" <READ> <PROMPT> ":" t_object = <STRING_LITERAL> (",")? <VAR> ":" "$" <IDENTIFIER> (",")? <TYPE> ":" <IDENTIFIER>")"
	{
	}
	|(
	LOOKAHEAD(2)"(" <BIND> <VAR> ":" "$" <IDENTIFIER> expr_tree()")"
	{
	}
	|(
	LOOKAHEAD(2)"(" <EXECUTE> <IDENTIFIER> ")"
	{
	}
	| 
	"(" <DISPLAY> <STRING_LITERAL> ")"
	{
	}
	))))))//)
}
//==============================================================================
void variableitem_class(ArrayList nt):
{
	Token tok;
}
{
	tok = <IDENTIFIER> expr_class() 
	{
		if(!nt.contains(tok.image)) nt.add(tok.image);
	}
}

//==============================================================================
void variableitem2_class():
{
}
{
	expr_tree() 
	{
	}
}

//==============================================================================
void expr_class():
{
}
{

	basicexpr_class() (nextexpr_class())?
	{
	}
	| "("expr_class()")"
	{
	}
}

//==============================================================================
void nextexpr_class():
{
}
{
	aop() expr_class() (LOOKAHEAD(2) expr_class())?
	{
	}
}

//==============================================================================
void basicexpr_class():
{
	//Token tok;
}
{
	<FLOATING_POINT_LITERAL>
	{
		if(variable_type < 2) variable_type = 2;
	}
	| <INTEGER_LITERAL>
	{
		if(variable_type < 1) variable_type = 1;
	}
	| <STRING_LITERAL>
	{
		if(variable_type < 3) variable_type = 3;
	}
	| "$" <IDENTIFIER>
	{
//		if(!nt.contains(tok.image)) nt.add(tok.image);
	}
/*
	| <VAR>
	{
		alias_condition = false;
		basic_here = "$VAR";
		return basic_here;
	}
*/
}

//==============================================================================
void lno_class():
{
}
{
	(<FLOATING_POINT_LITERAL> | <INTEGER_LITERAL>)
	{
	}
}


