package ReteObjects;


/**
* ReteObjects/COSMOS_STARTHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from test1.idl
* Friday, March 12, 2004 3:16:51 PM EST
*/

abstract public class COSMOS_STARTHelper
{
  private static String  _id = "IDL:ReteObjects/COSMOS_START:1.0";

  public static void insert (org.omg.CORBA.Any a, ReteObjects.COSMOS_START that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ReteObjects.COSMOS_START extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[0] = new org.omg.CORBA.StructMember (
            "init_status",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[1] = new org.omg.CORBA.StructMember (
            "P_H_Ep_init_status",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (ReteObjects.COSMOS_STARTHelper.id (), "COSMOS_START", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static ReteObjects.COSMOS_START read (org.omg.CORBA.portable.InputStream istream)
  {
    ReteObjects.COSMOS_START value = new ReteObjects.COSMOS_START ();
    value.init_status = istream.read_long ();
    value._P_H_Ep_init_status = istream.read_float ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ReteObjects.COSMOS_START value)
  {
    ostream.write_long (value.init_status);
    ostream.write_float (value._P_H_Ep_init_status);
  }

}
