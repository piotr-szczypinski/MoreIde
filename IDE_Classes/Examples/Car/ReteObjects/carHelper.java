package ReteObjects;


/**
* ReteObjects/carHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from car.idl
* czwartek, 15 wrzesie� 2005 13:34:43 CEST
*/

abstract public class carHelper
{
  private static String  _id = "IDL:ReteObjects/car/car:1.0";

  public static void insert (org.omg.CORBA.Any a, ReteObjects.car that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ReteObjects.car extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [18];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "problem",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[1] = new org.omg.CORBA.StructMember (
            "P_H_Ep_problem",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "init_problem",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[3] = new org.omg.CORBA.StructMember (
            "P_H_Ep_init_problem",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[4] = new org.omg.CORBA.StructMember (
            "headlights",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[5] = new org.omg.CORBA.StructMember (
            "P_H_Ep_headlights",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[6] = new org.omg.CORBA.StructMember (
            "spark_plug_spark",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[7] = new org.omg.CORBA.StructMember (
            "P_H_Ep_spark_plug_spark",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[8] = new org.omg.CORBA.StructMember (
            "fuel_gauge_reading",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[9] = new org.omg.CORBA.StructMember (
            "P_H_Ep_fuel_gauge_reading",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[10] = new org.omg.CORBA.StructMember (
            "carburetor_gas",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[11] = new org.omg.CORBA.StructMember (
            "P_H_Ep_carburetor_gas",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[12] = new org.omg.CORBA.StructMember (
            "ignition_key",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[13] = new org.omg.CORBA.StructMember (
            "P_H_Ep_ignition_key",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[14] = new org.omg.CORBA.StructMember (
            "engine_turning_over",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[15] = new org.omg.CORBA.StructMember (
            "P_H_Ep_engine_turning_over",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[16] = new org.omg.CORBA.StructMember (
            "vehicle_make",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[17] = new org.omg.CORBA.StructMember (
            "P_H_Ep_vehicle_make",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (ReteObjects.carHelper.id (), "car", _members0);
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

  public static ReteObjects.car read (org.omg.CORBA.portable.InputStream istream)
  {
    ReteObjects.car value = new ReteObjects.car ();
    value.problem = istream.read_string ();
    value._P_H_Ep_problem = istream.read_float ();
    value.init_problem = istream.read_string ();
    value._P_H_Ep_init_problem = istream.read_float ();
    value.headlights = istream.read_string ();
    value._P_H_Ep_headlights = istream.read_float ();
    value.spark_plug_spark = istream.read_string ();
    value._P_H_Ep_spark_plug_spark = istream.read_float ();
    value.fuel_gauge_reading = istream.read_string ();
    value._P_H_Ep_fuel_gauge_reading = istream.read_float ();
    value.carburetor_gas = istream.read_string ();
    value._P_H_Ep_carburetor_gas = istream.read_float ();
    value.ignition_key = istream.read_string ();
    value._P_H_Ep_ignition_key = istream.read_float ();
    value.engine_turning_over = istream.read_string ();
    value._P_H_Ep_engine_turning_over = istream.read_float ();
    value.vehicle_make = istream.read_string ();
    value._P_H_Ep_vehicle_make = istream.read_float ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ReteObjects.car value)
  {
    ostream.write_string (value.problem);
    ostream.write_float (value._P_H_Ep_problem);
    ostream.write_string (value.init_problem);
    ostream.write_float (value._P_H_Ep_init_problem);
    ostream.write_string (value.headlights);
    ostream.write_float (value._P_H_Ep_headlights);
    ostream.write_string (value.spark_plug_spark);
    ostream.write_float (value._P_H_Ep_spark_plug_spark);
    ostream.write_string (value.fuel_gauge_reading);
    ostream.write_float (value._P_H_Ep_fuel_gauge_reading);
    ostream.write_string (value.carburetor_gas);
    ostream.write_float (value._P_H_Ep_carburetor_gas);
    ostream.write_string (value.ignition_key);
    ostream.write_float (value._P_H_Ep_ignition_key);
    ostream.write_string (value.engine_turning_over);
    ostream.write_float (value._P_H_Ep_engine_turning_over);
    ostream.write_string (value.vehicle_make);
    ostream.write_float (value._P_H_Ep_vehicle_make);
  }

}
