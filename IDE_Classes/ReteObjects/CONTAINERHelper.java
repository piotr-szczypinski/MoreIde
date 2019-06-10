package ReteObjects;


/**
* ReteObjects/containerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ReteObjects.idl
* Friday, March 12, 2004 3:45:26 PM EST
*/

abstract public class containerHelper
{
  private static String  _id = "IDL:ReteObjects/container:1.0";

  public static void insert (org.omg.CORBA.Any a, ReteObjects.container that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ReteObjects.container extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [6];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[0] = new org.omg.CORBA.StructMember (
            "variable1",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[1] = new org.omg.CORBA.StructMember (
            "P_H_Ep_variable1",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[2] = new org.omg.CORBA.StructMember (
            "variable2",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[3] = new org.omg.CORBA.StructMember (
            "P_H_Ep_variable2",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[4] = new org.omg.CORBA.StructMember (
            "variable3",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[5] = new org.omg.CORBA.StructMember (
            "P_H_Ep_variable3",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (ReteObjects.containerHelper.id (), "container", _members0);
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

  public static ReteObjects.container read (org.omg.CORBA.portable.InputStream istream)
  {
    ReteObjects.container value = new ReteObjects.container ();
    value.variable1 = istream.read_double ();
    value.P_H_Ep_variable1 = istream.read_float ();
    value.variable2 = istream.read_double ();
    value.P_H_Ep_variable2 = istream.read_float ();
    value.variable3 = istream.read_double ();
    value.P_H_Ep_variable3 = istream.read_float ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ReteObjects.container value)
  {
    ostream.write_double (value.variable1);
    ostream.write_float (value.P_H_Ep_variable1);
    ostream.write_double (value.variable2);
    ostream.write_float (value.P_H_Ep_variable2);
    ostream.write_double (value.variable3);
    ostream.write_float (value.P_H_Ep_variable3);
  }

}