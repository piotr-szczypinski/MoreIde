package ReteObjects;

/**
* ReteObjects/CrimeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ReteObjects.idl
* Thursday, February 12, 2004 5:35:30 PM EST
*/

public final class CrimeHolder implements org.omg.CORBA.portable.Streamable
{
  public ReteObjects.Crime value = null;

  public CrimeHolder ()
  {
  }

  public CrimeHolder (ReteObjects.Crime initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ReteObjects.CrimeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ReteObjects.CrimeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ReteObjects.CrimeHelper.type ();
  }

}