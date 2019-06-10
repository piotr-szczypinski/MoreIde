package MemoryServer;


/**
* MemoryServer/_WMGetSetObjectStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from MemoryServer.idl
* Thursday, February 12, 2004 11:11:32 AM EST
*/

public class _WMGetSetObjectStub extends org.omg.CORBA.portable.ObjectImpl implements MemoryServer.WMGetSetObject
{

  public void WMSetObject (org.omg.CORBA.Any obj, String name)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("WMSetObject", true);
       $out.write_any (obj);
       $out.write_string (name);
       $in = _invoke ($out);
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       WMSetObject (obj, name);
    } finally {
        _releaseReply ($in);
    }
  } // WMSetObject

  public void WMDeleteObject (String name)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("WMDeleteObject", true);
       $out.write_string (name);
       $in = _invoke ($out);
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       WMDeleteObject (name);
    } finally {
        _releaseReply ($in);
    }
  } // WMDeleteObject

  public void WMGetObject (int tin, org.omg.CORBA.IntHolder tmax, org.omg.CORBA.StringHolder on, org.omg.CORBA.AnyHolder obj)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("WMGetObject", true);
       $out.write_long (tin);
       $in = _invoke ($out);
       tmax.value = $in.read_long ();
       on.value = $in.read_string ();
       obj.value = $in.read_any ();
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       WMGetObject (tin, tmax, on, obj);
    } finally {
        _releaseReply ($in);
    }
  } // WMGetObject

  public void WMWaitForChange (int ti)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("WMWaitForChange", true);
       $out.write_long (ti);
       $in = _invoke ($out);
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       WMWaitForChange (ti);
    } finally {
        _releaseReply ($in);
    }
  } // WMWaitForChange

  public void WMGetItem (int index, org.omg.CORBA.StringHolder name)
  {
    org.omg.CORBA.portable.InputStream $in = null;
    try {
       org.omg.CORBA.portable.OutputStream $out = _request ("WMGetItem", true);
       $out.write_long (index);
       $in = _invoke ($out);
       name.value = $in.read_string ();
    } catch (org.omg.CORBA.portable.ApplicationException $ex) {
       $in = $ex.getInputStream ();
       String _id = $ex.getId ();
       throw new org.omg.CORBA.MARSHAL (_id);
    } catch (org.omg.CORBA.portable.RemarshalException $rm) {
       WMGetItem (index, name);
    } finally {
        _releaseReply ($in);
    }
  } // WMGetItem

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:MemoryServer/WMGetSetObject:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _WMGetSetObjectStub
