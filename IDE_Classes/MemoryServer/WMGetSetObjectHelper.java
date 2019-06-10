package MemoryServer;


/**
* MemoryServer/WMGetSetObjectHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from MemoryServer.idl
* Monday, April 12, 2004 12:29:12 PM EDT
*/

abstract public class WMGetSetObjectHelper
{
  private static String  _id = "IDL:MemoryServer/WMGetSetObject:1.0";

  public static void insert (org.omg.CORBA.Any a, MemoryServer.WMGetSetObject that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static MemoryServer.WMGetSetObject extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (MemoryServer.WMGetSetObjectHelper.id (), "WMGetSetObject");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static MemoryServer.WMGetSetObject read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_WMGetSetObjectStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, MemoryServer.WMGetSetObject value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static MemoryServer.WMGetSetObject narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof MemoryServer.WMGetSetObject)
      return (MemoryServer.WMGetSetObject)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      MemoryServer._WMGetSetObjectStub stub = new MemoryServer._WMGetSetObjectStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
