//package rulexec;

import java.util.*;
import java.lang.*;
import java.io.*;
import MemoryServer.*;

import org.omg.CORBA.*;

//=============================================================================
class WMServant extends WMGetSetObjectPOA {
	private ORB orb;
	private ArrayList ListOfObjects;// = new ArrayList();
	private ArrayList ListOfNames;// = new ArrayList();
//	private ArrayList ListOfTimes;// = new ArrayList();
	private org.omg.CORBA.Any anynull;
  private java.awt.event.ActionListener listner;

  private String javc_s = "javac";
  private String idlj_s = "idlj -fall";
	private int enterings = 0;
	private boolean stop = false;

//=============================================================================
	public void Reset()
	{
        ListOfObjects = new ArrayList();
        ListOfNames = new ArrayList();
	}

//=============================================================================
	private class SaveLoadMemory
	{
		private ArrayList namelist;
		private ArrayList anyslist;
		private ArrayList objectlist;
		private ArrayList longtypelist;
		private ArrayList shorttypelist;
		
		public java.lang.Object[] LoadMemory(boolean compress, ORB orb)
		throws java.lang.NoSuchMethodException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException,
		java.lang.reflect.InvocationTargetException, java.io.IOException
		{
			//Optional, may've been already compiled at saving.
			try 
			{
				Compile(); 
			} 
			catch(Throwable e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			
			this.namelist = new ArrayList();
			objectlist = new ArrayList();
			longtypelist = new ArrayList();
			this.anyslist = new ArrayList();
			
			FileInputStream in = new FileInputStream("ReteObjects.mem");
			ObjectInputStream instream = new ObjectInputStream(in);

			this.namelist = (ArrayList)instream.readObject();
			longtypelist = (ArrayList)instream.readObject();
			objectlist = (ArrayList)instream.readObject();
			ConvertObjects2Anys(orb);
			
			if(compress)
			{
				ArrayList names = this.namelist;
				ArrayList anys = this.anyslist;
				this.namelist=new ArrayList();
				this.anyslist=new ArrayList();
				
				Compress(names, anys);
			}
			instream.close();
			in.close();
			return new java.lang.Object[] {this.namelist, this.anyslist};			
		} 
		
		public void SaveMemory(ArrayList namelist, ArrayList anyslist, boolean compress) 
		throws java.io.FileNotFoundException, java.io.IOException, java.lang.InterruptedException,
		java.lang.NoSuchMethodException, java.lang.InstantiationException, java.lang.IllegalAccessException,
		java.lang.reflect.InvocationTargetException, java.lang.ClassNotFoundException
		{
			objectlist = new ArrayList();
			shorttypelist = new ArrayList();
			longtypelist = new ArrayList();
			
			if(compress)
			{
				this.namelist=new ArrayList();
				this.anyslist=new ArrayList();
				Compress(namelist, anyslist);
			}
			else
			{
				this.namelist=namelist;
				this.anyslist=anyslist;
			}
			
			FileOutputStream out = new FileOutputStream("ReteObjects.mem");
			ObjectOutputStream outstream = new ObjectOutputStream(out);

			outstream.writeObject(this.namelist);
			SaveIDL();
			Compile();
			outstream.writeObject(longtypelist);
			ConvertAnys2Objects();
			outstream.writeObject(objectlist);

			outstream.flush();
			outstream.close();
			out.close();
		}

		private void Compress(ArrayList names, ArrayList anys)
		{
			int size = names.size();
			size = (anys.size()<size ?anys.size() :size);
			for(int i = 0; i < size; i++)
			{
				if(names.get(i) != null && anys.get(i) != null)
				{
					namelist.add(names.get(i));
					anyslist.add(anys.get(i));
				}
			}
		}
	
		private String GetIDLLine(int value)
		{
			String type;
			switch(value)
			{
				case org.omg.CORBA.TCKind._tk_null: type = "null"; break;
				case org.omg.CORBA.TCKind._tk_void: type = "void"; break;
				case org.omg.CORBA.TCKind._tk_short: type = "short"; break;
				case org.omg.CORBA.TCKind._tk_long: type = "long"; break;
				case org.omg.CORBA.TCKind._tk_ushort: type = "ushort"; break;
				case org.omg.CORBA.TCKind._tk_ulong: type = "ulong"; break;
				case org.omg.CORBA.TCKind._tk_float: type = "float"; break;
				case org.omg.CORBA.TCKind._tk_double: type = "double"; break;
				case org.omg.CORBA.TCKind._tk_boolean: type = "boolean"; break;
				case org.omg.CORBA.TCKind._tk_char: type = "char"; break;
				case org.omg.CORBA.TCKind._tk_octet: type = "octet"; break;
				case org.omg.CORBA.TCKind._tk_any: type = "any"; break;
				case org.omg.CORBA.TCKind._tk_TypeCode: type = "TypeCode"; break;
				case org.omg.CORBA.TCKind._tk_Principal: type = "Principal"; break;
				case org.omg.CORBA.TCKind._tk_objref: type = "objref"; break;
				case org.omg.CORBA.TCKind._tk_struct: type = "struct"; break;
				case org.omg.CORBA.TCKind._tk_union: type = "union"; break;
				case org.omg.CORBA.TCKind._tk_enum: type = "enum"; break;
				case org.omg.CORBA.TCKind._tk_string: type = "string"; break;
				case org.omg.CORBA.TCKind._tk_sequence: type = "sequence"; break;
				case org.omg.CORBA.TCKind._tk_array: type = "array"; break;
				case org.omg.CORBA.TCKind._tk_alias: type = "alias"; break;
				case org.omg.CORBA.TCKind._tk_except: type = "except"; break;
				case org.omg.CORBA.TCKind._tk_longlong: type = "longlong"; break;
				case org.omg.CORBA.TCKind._tk_ulonglong: type = "ulonglong"; break;
				case org.omg.CORBA.TCKind._tk_longdouble: type = "longdouble"; break;
				case org.omg.CORBA.TCKind._tk_wchar: type = "wchar"; break;
				case org.omg.CORBA.TCKind._tk_wstring: type = "wstring"; break;
				case org.omg.CORBA.TCKind._tk_fixed: type = "fixed"; break;
				case org.omg.CORBA.TCKind._tk_value: type = "value"; break;
				case org.omg.CORBA.TCKind._tk_value_box: type = "value_box"; break;
				case org.omg.CORBA.TCKind._tk_native: type = "native"; break;
				default: type = "?@#$%?";
			}
			return type;
		}

		private void SaveIDL() throws java.io.FileNotFoundException, java.io.IOException
		{
			try{
				FileOutputStream out = new FileOutputStream("ReteObjects.idl");
				PrintStream ps = new PrintStream(out);
			
				ps.print("module ReteObjects\r\n{\r\n");
				int alsize = anyslist.size();
				for(int ia = 0; ia < alsize; ia++)
				{
					TypeCode tc = ((Any)anyslist.get(ia)).type();
					String tstr = GetIDLLine(tc.kind().value());
					
					longtypelist.add(tc.name());
					
					if(!shorttypelist.contains(tc.name()))
					{
						shorttypelist.add(tc.name());
						
						ps.print("\t"+ tstr +" "+tc.name());
						int mc = tc.member_count();
						if(mc > 0)
						{
							ps.print("\r\n\t{\r\n");
							for(int i = 0; i < mc; i++)
								ps.print("\t\t"+GetIDLLine(tc.member_type(i).kind().value()) + " " + tc.member_name(i) +";\r\n");
							ps.print("\t};\r\n\r\n");
						}
						else
						{
							ps.print(";\r\n\r\n");
						}
					}
				}				
				ps.print("};\r\n");
			}
			catch(Throwable e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		private void Compile() throws java.io.IOException, java.lang.InterruptedException
		{
			Process p = Runtime.getRuntime().exec(idlj_s+" ReteObjects.idl");
			p.waitFor();
			p = Runtime.getRuntime().exec(javc_s+" ReteObjects" + File.separator + "*.java");
			p.waitFor();
		}
		
		private class DynamicClassLoader extends ClassLoader
		{
			ArrayList ln = new ArrayList();
			ArrayList lc = new ArrayList();
			
			public synchronized Class loadClass(String filename, String classname)
			{
				int index = ln.indexOf(classname);
				
				if(index>=0)
					return (Class)lc.get(index);

				byte[] data = getClassData(filename);
				if(data != null)
				{
					Class c = defineClass(classname, data, 0, data.length);
					resolveClass(c);
					ln.add(classname);
					lc.add(c);
					return c;
				}
				else return null;
			}
			byte[] getClassData(String filename)
			{
				int l;
				byte[] b;
				try
				{
					File inputFile = new File(filename);
					l = (int)(inputFile.length());
					b = new byte[l];
					FileInputStream inputStream = new FileInputStream(inputFile);
					inputStream.read(b, 0, l);
					inputStream.close();
					return b;
				}
				catch(IOException e)
				{
					System.err.println(e);
					e.printStackTrace();
				}
				return null;
			}
		}
	
		private void ConvertObjects2Anys(ORB orb) 
		throws java.lang.NoSuchMethodException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException,
		java.lang.reflect.InvocationTargetException
		{
			File ff = new File("");
			DynamicClassLoader cl = new DynamicClassLoader();
			int size;
			org.omg.CORBA.Any any;
			String classname;
			
			size = namelist.size();
			size = (longtypelist.size()<size ?longtypelist.size() :size);
			size = (objectlist.size()<size ?objectlist.size() :size);
			
			for(int i = 0; i < size; i++)
			{
				any = orb.create_any();
				
				classname = (String) longtypelist.get(i);
				Class c = cl.loadClass("ReteObjects"+ff.separator+ classname +"Helper.class", 
					"ReteObjects/"+ classname + "Helper");
				java.lang.reflect.Method m = c.getMethod("insert", 
					new Class[] {Class.forName("org.omg.CORBA.Any"), Class.forName("ReteObjects."+classname)});
				m.invoke(null, new java.lang.Object[] {any, objectlist.get(i)}); 
				anyslist.add(any);

				// Java docs say that: 
				// "If the underlying method is static, then the specified obj argument is ignored. It may be null."
				// Not true! It must be null! --PMS
			}
		}
	
		private void ConvertAnys2Objects() 
		throws java.lang.NoSuchMethodException, java.lang.InstantiationException, java.lang.IllegalAccessException,
		java.lang.reflect.InvocationTargetException, java.lang.ClassNotFoundException
		{
			File ff = new File("");
			DynamicClassLoader cl = new DynamicClassLoader();
			int size = anyslist.size();
			org.omg.CORBA.Any any;
			String classname;
			
			for(int i = 0; i < size; i++)
			{
				any = (Any) anyslist.get(i);
				classname = (String) longtypelist.get(i);
				Class c = cl.loadClass("ReteObjects"+ff.separator+ classname +"Helper.class", "ReteObjects/"+ classname + "Helper");

				java.lang.reflect.Method m = c.getMethod("extract", new Class[] {Class.forName("org.omg.CORBA.Any")});
				objectlist.add(m.invoke(null, new java.lang.Object[] {any})); 
				// Java docs say that: 
				// "If the underlying method is static, then the specified obj argument is ignored. It may be null."
				// Not true! It must be null! --PMS
			}
		}
	}

//=============================================================================
	public WMServant(ORB orb, java.awt.event.ActionListener listner, boolean load, String idlj_s, String javc_s) 
	{
        
        this.idlj_s = idlj_s;
        this.javc_s = javc_s;
        this.orb = orb; 
        anynull = orb.create_any();
        //anynull.insert_string("");
        this.listner = listner;

        try
        {
            ListOfObjects = new ArrayList();
            ListOfNames = new ArrayList();
            
			if(load)
			{            
				java.lang.Object[] lists = (new SaveLoadMemory()).LoadMemory(true, orb);
				if(lists.length == 2)
				{
					if(((ArrayList)lists[0]).size() == ((ArrayList)lists[1]).size())
					{
						ListOfNames = (ArrayList)lists[0];
						ListOfObjects  = (ArrayList)lists[1];
					}
				}
			}
			for(int i = 0; i < ListOfNames.size(); i++)
	            listner.actionPerformed(new java.awt.event.ActionEvent((java.lang.Object)this, 1, (String)ListOfNames.get(i)));
        } 
        catch (Throwable e) //(java.io.FileNotFoundException e) 
        {
            ListOfObjects = new ArrayList();
            ListOfNames = new ArrayList();
//            listner.actionPerformed(new java.awt.event.ActionEvent((java.lang.Object)this, 1, "Error = " + e));
//            StackTraceElement el[] = e.getStackTrace();
//            for(int i = 0; i < el.length; i++) 
//				listner.actionPerformed(new java.awt.event.ActionEvent((java.lang.Object)this, 1, el[i].toString() ));
//				System.err.println( "Exception in Servlet..." + e );
//				e.printStackTrace( );
        }
  	}

//=============================================================================
        public synchronized void WMSaveMemory(PrintStream pstr)
        {
            try 
            {
				stop = true;
				notifyAll();
				pstr.println( "Saving memory, stopping threads...");
				
				if(enterings>0) wait(500);
				if(enterings>0) wait(500);
				if(enterings>0) wait(500);
				if(enterings>0) wait(500);
				if(enterings>0) wait(500);
				if(enterings>0) wait(500);
				if(enterings>0) wait(500);
				if(enterings>0) wait(500);
				
                (new SaveLoadMemory()).SaveMemory(ListOfNames, ListOfObjects, true);
            }
            catch (Throwable e) //(java.io.FileNotFoundException e) 
            {
                pstr.println( "Exception in Servlet..." + e );
                StackTraceElement el[] = e.getStackTrace();
                for(int i = 0; i < el.length; i++) 
                    pstr.println( el[i].toString() );
            }	
        }

//=============================================================================
	public synchronized void WMGetItem(int index, org.omg.CORBA.StringHolder name)
	{
		String str;
		int i = ListOfNames.size();

		if(index<i && index >= 0) name.value = (String)ListOfNames.get(index);
		else name.value = "";
	}

//=============================================================================
	public synchronized org.omg.CORBA.Any WMGetItem(String name)
	{
		String str;
		enterings ++;
		if(stop) {enterings --; return null;}
		
		int i = ListOfNames.lastIndexOf(name);
                if(i<0) {enterings --; return null;}
                java.lang.Object o = ListOfObjects.get(i);
                if(o == null) {enterings --; return null;}
                enterings --;
                return (Any) o;
	}

//=============================================================================
	public synchronized void WMSetObject(org.omg.CORBA.Any obj, String name)
	{
/*Debug*/System.out.println( "WMSetObject_in list_size = " + ListOfNames.size());	
	
		enterings ++;
		if(stop) {enterings --; return;}

		int i = ListOfNames.lastIndexOf(name);
		
		if(i<0)
		{
			ListOfObjects.add(obj);
			ListOfNames.add( name );
			notifyAll();
	 		listner.actionPerformed(new java.awt.event.ActionEvent((java.lang.Object)this, 1, name));
		}
		else 
		{
			ListOfObjects.set(i, null);

			ListOfObjects.add(null);
			ListOfNames.add( (String)ListOfNames.get(i) );

			ListOfObjects.add(obj);
			ListOfNames.add( (String)ListOfNames.get(i) );
			notifyAll();
	 		listner.actionPerformed(new java.awt.event.ActionEvent((java.lang.Object)this, 0, name));
		}
		enterings --;
		notifyAll();
/*Debug*/System.out.println( "WMSetObject_in list_size = " + ListOfNames.size());	
		
	}
	
//=============================================================================
	public synchronized void WMDeleteObject(String name)
	{
/*Debug*/System.out.println( "WMDeleteObject_in list_size = " + ListOfNames.size());	
	
		enterings ++;
		if(stop) {enterings --; return;}
		int i = ListOfNames.lastIndexOf(name);
		if(i>=0)
		{
			ListOfObjects.set(i, null);

		    ListOfObjects.add(null);
			ListOfNames.add( (String)ListOfNames.get(i) );
			notifyAll();
	 		listner.actionPerformed(new java.awt.event.ActionEvent((java.lang.Object)this, -1, name));
		}
		enterings --;
		notifyAll();
/*Debug*/System.out.println( "WMDeleteObject_out list_size = " + ListOfNames.size());	
	}


//=============================================================================
// ti - time requested, 
// function does not return until something changes after ti.
	public synchronized void WMWaitForChange(int ti)
	{

/*Debug*/System.out.println( "WMWaitForChange_in timer = " + ti + " list_size = " + ListOfNames.size());	
		
		
		enterings ++;
		if(stop) {enterings --; return;}
		try
		{
			while(ti >= ListOfNames.size() && (!stop)) wait();//Thread.yield();
			enterings --;
			notifyAll();
		}
		catch ( Exception e ) 
		{
			System.err.println( "Exception in Servlet..." + e );
			e.printStackTrace( );
			enterings --;
		}
/*Debug*/System.out.println( "WMWaitForChange_out");	
	}

//=============================================================================
// ti - time requested and object index on return
// (if object is new ti>0, if modified ti is negative),
// on - object name, objh - object itself
	public synchronized void WMGetObject(int tin, org.omg.CORBA.IntHolder tmax, org.omg.CORBA.StringHolder on, org.omg.CORBA.AnyHolder objh)
	{
		try
		{
			enterings ++;
			if(stop) {tmax.value = -1; enterings --; return;}
			
			int size = ListOfNames.size();
			if(tin >= size || tin < 0)
			{
				tmax.value = size; 
				on.value = ""; 
				objh.value = anynull; 
				enterings --;
				return;
			}
			tmax.value = size; 
			on.value = (String)ListOfNames.get(tin);
			java.lang.Object o = ListOfObjects.get(tin);
			if(o == null) objh.value = anynull; //object was deleted
			else objh.value = (org.omg.CORBA.Any)o; //object
			enterings --;
		}
		catch ( Exception e )
		{
			System.err.println( "Exception in Servlet..." + e );
			e.printStackTrace( );
			enterings --;
		}
	}
}


