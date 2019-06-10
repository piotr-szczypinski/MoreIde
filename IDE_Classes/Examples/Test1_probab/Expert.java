//==============================================================================
//==============================================================================
//==                                    ===****=====**=====**=====****==========
//==  Expert.java                       ===*===*====*=*===*=*====*====*=========
//==                                    ===*===*====*==*=*==*=====**============
//==  (C) 2002 by Piotr M. Szczypinski  ===****=====*===*===*=======**==========
//==  NIST - Gaithersburg               ===*========*=======*====*====*=========
//==                                    ===*========*=======*=====****==========
//==============================================================================
//==============================================================================
import MemoryServer.*;
import ReteObjects.*;
/**
 *
 * @author  pms
 */
public class Expert extends javax.swing.JFrame {
		private boolean reading = false;
		private ReteNetwork rn = null;

		private synchronized void SetReading(boolean r)
		{
			reading = r;
			notifyAll();
		}
		public void Print(String string)
		{
				jTextArea1.append(string);
		}
		public void PrintMessage(String string)
		{
				jTextField2.setText(string);
		}
		public synchronized String Read()
		{
				String ret;
				reading = true;
				jTextField1.setEditable(true);
				jTextField1.setText("");
				jTextField1.requestFocus();
				jTextField1.setCaretPosition(0);
				while(reading == true) 
				{
					try{wait();}
					catch(Throwable e){Thread.yield();}
				}
				jTextField1.setEditable(false);
				ret = jTextField1.getText();

				Print(ret + "\r\n");
				return ret;
		}
		public void Display(String filename)
		{
				jLabel1.setIcon(new javax.swing.ImageIcon (filename));
		}
 
		/** Creates new form Expert */
		public Expert() {
				initComponents();
		}

		/** This method is called from within the constructor to
		 * initialize the form.
		 * WARNING: Do NOT modify this code. The content of this method is
		 * always regenerated by the Form Editor.
		 */
		private void initComponents() {//GEN-BEGIN:initComponents
				jSplitPane1 = new javax.swing.JSplitPane();
				jScrollPane1 = new javax.swing.JScrollPane();
				jLabel1 = new javax.swing.JLabel();
				jPanel1 = new javax.swing.JPanel();
				jTextField1 = new javax.swing.JTextField();
				jScrollPane2 = new javax.swing.JScrollPane();
				jTextArea1 = new javax.swing.JTextArea();
				jTextField2 = new javax.swing.JTextField();

				setTitle("Expert");
				addWindowListener(new java.awt.event.WindowAdapter() {
						public void windowClosing(java.awt.event.WindowEvent evt) {
								exitForm(evt);
						}
				});

				jScrollPane1.setBackground(new java.awt.Color(204, 204, 204));
				jLabel1.setBackground(new java.awt.Color(204, 204, 204));
				jScrollPane1.setViewportView(jLabel1);

				jSplitPane1.setLeftComponent(jScrollPane1);

				jPanel1.setLayout(new java.awt.BorderLayout());

				jTextField1.setEditable(false);
				jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
						public void keyPressed(java.awt.event.KeyEvent evt) {
								jTextField1KeyPressed(evt);
						}
				});

				jPanel1.add(jTextField1, java.awt.BorderLayout.SOUTH);

				jScrollPane2.setBorder(null);
				jScrollPane2.setAutoscrolls(true);
				jTextArea1.setBackground(new java.awt.Color(255, 255, 204));
				jTextArea1.setEditable(false);
				jTextArea1.setLineWrap(true);
				jTextArea1.setTabSize(2);
				jTextArea1.setWrapStyleWord(true);
				jScrollPane2.setViewportView(jTextArea1);

				jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

				jSplitPane1.setRightComponent(jPanel1);

				getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

				jTextField2.setBackground(new java.awt.Color(204, 204, 204));
				jTextField2.setFont(new java.awt.Font("Dialog", 1, 10));
				jTextField2.setBorder(null);
				jTextField2.setEditable(false);
				getContentPane().add(jTextField2, java.awt.BorderLayout.SOUTH);
				pack();
		}//GEN-END:initComponents

		private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
				if(evt.getKeyCode()== evt.VK_ENTER) SetReading(false);//reading = false;
		}//GEN-LAST:event_jTextField1KeyPressed

		/** Exit the Application */
		private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
		if(rn != null)
		{
			rn.Stop();
		}
		System.exit(0);

		}//GEN-LAST:event_exitForm
		/**
		* @param args the command line arguments
		*/
		public static void main(String args[]) 
		{
			Expert expert = new Expert();

			expert.jTextField2.setText("Starting system.");
			expert.show();
			expert.Start();
		}

		public void Start() 
		{
			try
			{
				(new CheckMemoryThread(this)).start();
			}
			catch(Throwable e)
			{
				jTextField2.setText("Cannot start new thread");
			}
		}

		class CheckMemoryThread extends Thread
		{
			Expert ex;
			public CheckMemoryThread(Expert exp)
			{
				ex = exp;
			}
 			public void run()
			{
				jTextField2.setText("Starting system..");
				try 
				{
					rn = ReteNetwork.WinMain(ex);
//				Print("\nInferencing stopped\n");
					jTextField2.setText("Inferencing stopped");
				} 
				catch(Throwable e)
				{
//				System.err.println(e.getMessage());
//								e.printStackTrace();
					jTextField2.setText("Cannot start inferencing");
				};
			}
		}

		// Variables declaration - do not modify//GEN-BEGIN:variables
		private javax.swing.JPanel jPanel1;
		private javax.swing.JScrollPane jScrollPane2;
		private javax.swing.JScrollPane jScrollPane1;
		private javax.swing.JSplitPane jSplitPane1;
		private javax.swing.JTextField jTextField2;
		private javax.swing.JTextField jTextField1;
		private javax.swing.JTextArea jTextArea1;
		private javax.swing.JLabel jLabel1;
		// End of variables declaration//GEN-END:variables
}
