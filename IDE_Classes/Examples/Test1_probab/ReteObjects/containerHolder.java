package ReteObjects;

/**
* ReteObjects/containerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from test1.idl
* Friday, March 12, 2004 3:16:51 PM EST
*/

public final class containerHolder implements org.omg.CORBA.portable.Streamable
{
  public ReteObjects.container value = null;

  public containerHolder ()
  {
  }

  public containerHolder (ReteObjects.container initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ReteObjects.containerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ReteObjects.containerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ReteObjects.containerHelper.type ();
  }

}
