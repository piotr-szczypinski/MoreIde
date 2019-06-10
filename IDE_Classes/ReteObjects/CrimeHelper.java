package ReteObjects;


/**
* ReteObjects/CrimeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ReteObjects.idl
* Thursday, February 12, 2004 5:35:30 PM EST
*/

abstract public class CrimeHelper
{
  private static String  _id = "IDL:ReteObjects/Crime:1.0";

  public static void insert (org.omg.CORBA.Any a, ReteObjects.Crime that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ReteObjects.Crime extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [5];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "InBedroom",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "InDressingRoom",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "InStudy",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "CrimeInPrivateDomain",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[4] = new org.omg.CORBA.StructMember (
            "VictimeKnewAndTrustedKiller",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (ReteObjects.CrimeHelper.id (), "Crime", _members0);
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

  public static ReteObjects.Crime read (org.omg.CORBA.portable.InputStream istream)
  {
    ReteObjects.Crime value = new ReteObjects.Crime ();
    value.InBedroom = istream.read_string ();
    value.InDressingRoom = istream.read_string ();
    value.InStudy = istream.read_string ();
    value.CrimeInPrivateDomain = istream.read_string ();
    value.VictimeKnewAndTrustedKiller = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ReteObjects.Crime value)
  {
    ostream.write_string (value.InBedroom);
    ostream.write_string (value.InDressingRoom);
    ostream.write_string (value.InStudy);
    ostream.write_string (value.CrimeInPrivateDomain);
    ostream.write_string (value.VictimeKnewAndTrustedKiller);
  }

}