package ReteObjects;

/**
* ReteObjects/carHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from car.idl
* czwartek, 15 wrzesie� 2005 13:34:43 CEST
*/

public final class carHolder implements org.omg.CORBA.portable.Streamable
{
  public ReteObjects.car value = null;

  public carHolder ()
  {
  }

  public carHolder (ReteObjects.car initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ReteObjects.carHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ReteObjects.carHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ReteObjects.carHelper.type ();
  }

}
