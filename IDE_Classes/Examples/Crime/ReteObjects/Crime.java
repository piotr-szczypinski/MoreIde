package ReteObjects;


/**
* ReteObjects/Crime.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from crime.idl
* Thursday, February 12, 2004 11:11:30 AM EST
*/

public final class Crime implements org.omg.CORBA.portable.IDLEntity
{
  public String VictimeKnewAndTrustedKiller = null;
  public String InStudy = null;
  public String InBedroom = null;
  public String InDressingRoom = null;
  public String Footprints = null;
  public String WindowsDoorsLocked = null;
  public String FornitureDisturbed = null;
  public String MarksFromAttacker = null;
  public String VictimeDidNotStrugle = null;
  public String NoSignOfBrakein = null;
  public String CrimeInPrivateDomain = null;

  public Crime ()
  {
  } // ctor

  public Crime (String _VictimeKnewAndTrustedKiller, String _InStudy, String _InBedroom, String _InDressingRoom, String _Footprints, String _WindowsDoorsLocked, String _FornitureDisturbed, String _MarksFromAttacker, String _VictimeDidNotStrugle, String _NoSignOfBrakein, String _CrimeInPrivateDomain)
  {
    VictimeKnewAndTrustedKiller = _VictimeKnewAndTrustedKiller;
    InStudy = _InStudy;
    InBedroom = _InBedroom;
    InDressingRoom = _InDressingRoom;
    Footprints = _Footprints;
    WindowsDoorsLocked = _WindowsDoorsLocked;
    FornitureDisturbed = _FornitureDisturbed;
    MarksFromAttacker = _MarksFromAttacker;
    VictimeDidNotStrugle = _VictimeDidNotStrugle;
    NoSignOfBrakein = _NoSignOfBrakein;
    CrimeInPrivateDomain = _CrimeInPrivateDomain;
  } // ctor

} // class Crime
