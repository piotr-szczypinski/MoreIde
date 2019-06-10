MoreIde
This project started in 2002 under supervision of Dr Ram D. Sriram (NIST). 
Some technologies used in the project might be obsolete.

-----
Knowledge-based expert system (KBES) is an application that contains knowledge
established by human expert, usually represented by if-then rules, and inference 
engine which is a set of algorithms that concludes about known and incoming data 
according to the knowledge.

MORE Integrated Development Environment (IDE) is a tool for building object-oriented 
KBESs. MORE IDE supports forward chaining and backward chaining mechanisms. Forward 
chaining uses modified object-oriented RETE network algorithm (MORE) first introduced 
in COSMOS software. The backward chaining is an object oriented implementation of KAS 
inference network and it incorporates a Bayesian network propagation algorithm. 

MORE IDE was written in Java. Graphical user interface (GUI) employs Java Swing 
technology. Rule definition parser is based on JavaCC. Communication between KBES 
and WM depends on Corba technology. The system requires Java 2 Platform SE from 
Sun microsystems including java compiler (javac), java louncher (java), CORBA 
object request broker daemon (orbd) and IDL-to-Java Compiler (idlj).
