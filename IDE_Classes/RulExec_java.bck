/*
 * RulExec.java
 *
 * Created on 29 kwiecie� 2003, 21:58
 */
//package rulexec;
//import javax.swing.tree.*;
import java.lang.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import java.awt.*;
import java.awt.event.*;


import javax.swing.*;
import javax.swing.text.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.beans.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;


/**
 *
 * @author  pms
 */
public class RulExec extends javax.swing.JFrame {
    
    /** Creates new form RulExec */
    public RulExec() {
        chooser = new JFileChooser();
        rff = new RulFileFilter();
        chooser.setFileFilter(rff);
        
        rootTreeRules = new javax.swing.tree.DefaultMutableTreeNode("\rRules");
        treeModelRules = new javax.swing.tree.DefaultTreeModel(rootTreeRules);
        listenerRules = new TreeRulesModelListener(rootTreeRules, treeModelRules);
        treeModelRules.addTreeModelListener(listenerRules);
        rootTreeObjects = new javax.swing.tree.DefaultMutableTreeNode("\rObjects");
        treeModelObjects = new javax.swing.tree.DefaultTreeModel(rootTreeObjects);
        listenerObjects = new TreeObjectsModelListener(rootTreeObjects, treeModelObjects);
        treeModelObjects.addTreeModelListener(listenerObjects);
        
        initComponents();
        
        jTree2.setEditable(true);
        jTree2.setCellRenderer(new RulesTreeRenderer());
        listenerRules.setTextArea(jTextArea2);
        listenerRules.newEmptyRule();
        jTree1.setEditable(true);
        jTree1.setCellRenderer(new ObjectsTreeRenderer());
        listenerObjects.setTextArea(jTextArea1);
        listenerObjects.newEmptyObject();
        
        LoadOptions();
        //        DefaultMutableTreeNode mtn = new DefaultMutableTreeNode("");
        //        treeModelObjects.insertNodeInto(mtn, rootTreeObjects, rootTreeObjects.getChildCount());
        //        DefaultMutableTreeNode mtm = new DefaultMutableTreeNode("");
        //        treeModelObjects.insertNodeInto(mtm, rootTreeObjects, rootTreeObjects.getChildCount());
        
    }
    protected javax.swing.tree.DefaultMutableTreeNode rootTreeObjects;
    protected javax.swing.tree.DefaultMutableTreeNode rootTreeRules;
    protected javax.swing.tree.DefaultTreeModel treeModelObjects;
    protected javax.swing.tree.DefaultTreeModel treeModelRules;
    protected TreeRulesModelListener listenerRules;
    protected TreeObjectsModelListener listenerObjects;
    private String rulfilename = null;
    private String idlfilename = null;
    private String rulidlfolder = null;
    private Dimension zerosize = null;
    private JFileChooser chooser;
    private RulFileFilter rff;
    
    
    private class RulesTreeRenderer extends DefaultTreeCellRenderer {
        ImageIcon rot_gif = new ImageIcon("rot.gif");
        ImageIcon rul_gif = new ImageIcon("rul.gif");
        ImageIcon and_gif = new ImageIcon("and.gif");
        ImageIcon or_gif  = new ImageIcon("or.gif");
        ImageIcon cla_gif = new ImageIcon("cla.gif");
        ImageIcon if_gif  = new ImageIcon("if.gif");
        ImageIcon thn_gif = new ImageIcon("thn.gif");
        ImageIcon qmk_gif = new ImageIcon("qmk.gif");
        ImageIcon cnd_gif = new ImageIcon("cnd.gif");
        ImageIcon stt_gif = new ImageIcon("stt.gif");
        ImageIcon last_gif = null;
        
        public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {
            
            
            //            System.out.println(tree.getLastSelectedPathComponent() + " / " + value);
            //            System.out.println(value);
            tree.setEditable(true);
            if(tree.getLastSelectedPathComponent()!=null)
                if(tree.getLastSelectedPathComponent().toString().length() > 0)
                    if(tree.getLastSelectedPathComponent().toString().charAt(0) == '\r') tree.setEditable(false);
            
            super.getTreeCellRendererComponent(
            tree, value, sel,
            expanded, leaf, row,
            hasFocus);
            
            //       System.out.println("Editable: " + tree.isEditable());
            
            if(row == 0) last_gif = rot_gif;
            else if(((DefaultMutableTreeNode)value).getParent() == ((DefaultMutableTreeNode)value).getRoot()) last_gif = rul_gif;
            else if(value.toString().compareTo("\rIF") == 0) last_gif = if_gif;
            else if(value.toString().compareTo("\rTHEN") == 0) last_gif = thn_gif;
            else if(((DefaultMutableTreeNode)value).getParent().toString().compareTo("\rTHEN")==0) {
                if(value.toString().length()>0) last_gif = stt_gif;
                else last_gif = qmk_gif;
            }
            else {
                if(value.toString().regionMatches(true, 0, "CLASS:", 0, 6)) last_gif = cla_gif;
                else if(value.toString().compareToIgnoreCase("AND") == 0) last_gif = and_gif;
                else if(value.toString().compareToIgnoreCase("OR") == 0) last_gif = or_gif;
                else if(value.toString().length()>0) last_gif = cnd_gif;
                else last_gif = qmk_gif;
            }
            
            if (!tree.isEnabled()) setDisabledIcon(last_gif);
            else setIcon(last_gif);
            
            return this;
        }
    }
    
    private class ObjectsTreeRenderer extends DefaultTreeCellRenderer {
        ImageIcon rot_gif = new ImageIcon("rot.gif");
        ImageIcon cla_gif = new ImageIcon("cla.gif");
        ImageIcon str_gif = new ImageIcon("str.gif");
        ImageIcon int_gif = new ImageIcon("int.gif");
        ImageIcon flp_gif = new ImageIcon("flp.gif");
        ImageIcon qmk_gif = new ImageIcon("qmk.gif");
        ImageIcon last_gif = null;
        
        public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {
            
            tree.setEditable(true);
            if(tree.getLastSelectedPathComponent()!=null)
                if(tree.getLastSelectedPathComponent().toString().length() > 0)
                    if(tree.getLastSelectedPathComponent().toString().charAt(0) == '\r') tree.setEditable(false);
            
            super.getTreeCellRendererComponent(
            tree, value, sel,
            expanded, leaf, row,
            hasFocus);
            
            if(row == 0) last_gif = rot_gif;
            else if(((DefaultMutableTreeNode)value).getParent() == ((DefaultMutableTreeNode)value).getRoot()) last_gif = cla_gif;
            else if(value.toString().regionMatches(true, 0, "long ", 0, 5)) last_gif = int_gif;
            else if(value.toString().regionMatches(true, 0, "double ", 0, 7)) last_gif = flp_gif;
            else if(value.toString().regionMatches(true, 0, "string ", 0, 7)) last_gif = str_gif;
            else last_gif = qmk_gif;
            
            if (!tree.isEnabled()) setDisabledIcon(last_gif);
            else setIcon(last_gif);
            
            return this;
        }
    }
    
    //==================================================================
    class TreeRulesModelListener implements TreeModelListener {
        private javax.swing.tree.DefaultMutableTreeNode root;
        private javax.swing.tree.DefaultTreeModel model;
        private javax.swing.JTextArea text;
        
        public TreeRulesModelListener(javax.swing.tree.DefaultMutableTreeNode r, javax.swing.tree.DefaultTreeModel m) {
            root = r;
            model = m;
            text = null;
        }
        public void setTextArea(javax.swing.JTextArea t) {
            text = t;
        }
        
        private void BuildBranch(javax.swing.tree.DefaultMutableTreeNode nd, ArrayList al) {
            int size = al.size();
            if(size>0) {
                javax.swing.tree.DefaultMutableTreeNode n
                = new javax.swing.tree.DefaultMutableTreeNode((String)(al.get(0)));
                model.insertNodeInto(n, nd, nd.getChildCount());
                for(int i=1; i<size; i++) {
                    BuildBranch(n, (ArrayList)(al.get(i)));
                }
            }
        }
        
        public void ClearTree() {
            root.removeAllChildren();
            DefaultMutableTreeNode mtn = new DefaultMutableTreeNode("");
            model.insertNodeInto(mtn, root, root.getChildCount());
            model.reload();
        }
        
        public void BuildTree(ArrayList al) {
            if(al == null) return;
            int size = al.size();
            if(size>0) {
                if(al.get(0).toString().compareTo("\rRules") != 0) return;
                
                root.removeAllChildren();
                model.reload();
                for(int i=1; i<size; i++) {
                    BuildBranch(root, (ArrayList)(al.get(i)));
                }
            }
        }
        
        public void newEmptyRule() {
            //    System.out.println("Count" + root.getChildCount());
            for(int i = root.getChildCount()-1; i>=0; i--) {
                String name = model.getChild(root, i).toString();
                if(name.length() == 0) model.removeNodeFromParent((MutableTreeNode)(model.getChild(root, i)));
            }
            DefaultMutableTreeNode mtn = new DefaultMutableTreeNode("");
            model.insertNodeInto(mtn, root, root.getChildCount());
        }
        
        public void newFilledRule(DefaultMutableTreeNode mtn) {
            int count;
            
            if(mtn == null) return;
            count = mtn.getChildCount();
            if(count == 2)
                if(model.getChild(mtn, 0).toString().compareTo("\rIF") == 0
                && model.getChild(mtn, 1).toString().compareTo("\rTHEN") == 0)
                    //&& model.getChild(mtn, 2).toString().compareTo("\rCOMMENT") == 0)
                    return;
            
            for(int i = count -1; i >= 0; i--)
                model.removeNodeFromParent((DefaultMutableTreeNode)(model.getChild(mtn, i)));
            
            DefaultMutableTreeNode mtni = new DefaultMutableTreeNode("\rIF");
            DefaultMutableTreeNode mtnt = new DefaultMutableTreeNode("\rTHEN");
            //    DefaultMutableTreeNode mtnc = new DefaultMutableTreeNode("\rCOMMENT");
            DefaultMutableTreeNode mtnie = new DefaultMutableTreeNode(new String(""));
            DefaultMutableTreeNode mtnte = new DefaultMutableTreeNode(new String(""));
            //    DefaultMutableTreeNode mtntc = new DefaultMutableTreeNode(new String(""));
            
            model.insertNodeInto(mtni, mtn, 0);
            model.insertNodeInto(mtnie, mtni, 0);
            model.insertNodeInto(mtnt, mtn, 1);
            model.insertNodeInto(mtnte, mtnt, 0);
            //  model.insertNodeInto(mtnc, mtn, 2);
            //  model.insertNodeInto(mtntc, mtnc, 0);
        }
        public void newEmptyTHEN(DefaultMutableTreeNode mtn) {
            //    System.out.println("Count" + root.getChildCount());
            DefaultMutableTreeNode prnt = (DefaultMutableTreeNode)(mtn.getParent());
            if(prnt == null) return;
            
            for(int i = prnt.getChildCount()-1; i>=0; i--) {
                String name = model.getChild(prnt, i).toString();
                if(name.length() == 0) model.removeNodeFromParent((MutableTreeNode)(model.getChild(prnt, i)));
            }
            DefaultMutableTreeNode n = new DefaultMutableTreeNode(new String(""));
            model.insertNodeInto(n, prnt, prnt.getChildCount());
        }
        
        public void set0inTHEN(DefaultMutableTreeNode prnt) {
            if(prnt == null) return;
            
            for(int i = prnt.getChildCount()-1; i>=0; i--) {
                String name = model.getChild(prnt, i).toString();
                if(name.length() == 0) model.removeNodeFromParent((MutableTreeNode)(model.getChild(prnt, i)));
            }
            DefaultMutableTreeNode n = new DefaultMutableTreeNode(new String(""));
            model.insertNodeInto(n, prnt, prnt.getChildCount());
        }
        
        
        public void set0inIF(DefaultMutableTreeNode mtn) {
            int count;
            if(mtn == null) return;
            count = mtn.getChildCount();
            for(int i = count -1; i >= 0; i--)
                model.removeNodeFromParent((DefaultMutableTreeNode)(model.getChild(mtn, i)));
        }
        public void set1inIF(DefaultMutableTreeNode mtn) {
            int count;
            if(mtn == null) return;
            count = mtn.getChildCount();
            if(count == 1) return;
            if(count == 0) {
                DefaultMutableTreeNode mtni = new DefaultMutableTreeNode(new String(""));
                model.insertNodeInto(mtni, mtn, 0);
                return;
            }
            
            for(int i = count -1; i >= 1; i--)
                model.removeNodeFromParent((DefaultMutableTreeNode)(model.getChild(mtn, i)));
        }
        public void set2inIF(DefaultMutableTreeNode mtn) {
            int count;
            if(mtn == null) return;
            count = mtn.getChildCount();
            if(count == 2) return;
            if(count == 1) {
                DefaultMutableTreeNode mtni = new DefaultMutableTreeNode(new String(""));
                model.insertNodeInto(mtni, mtn, 0);
                return;
            }
            if(count == 0) {
                DefaultMutableTreeNode mtni = new DefaultMutableTreeNode(new String(""));
                DefaultMutableTreeNode mtnj = new DefaultMutableTreeNode(new String(""));
                model.insertNodeInto(mtni, mtn, 0);
                model.insertNodeInto(mtnj, mtn, 1);
                return;
            }
            
            for(int i = count -1; i >= 2; i--)
                model.removeNodeFromParent((DefaultMutableTreeNode)(model.getChild(mtn, i)));
        }
        
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)
            (e.getTreePath().getLastPathComponent());
            
            try {
                int index = e.getChildIndices()[0];
                node = (DefaultMutableTreeNode)
                (node.getChildAt(index));
            } catch (NullPointerException exc) {}
            
            if(((DefaultMutableTreeNode)node).getParent() == ((DefaultMutableTreeNode)node).getRoot()) {
                if(node.toString().length() > 0) newFilledRule((DefaultMutableTreeNode)node);
                newEmptyRule();
            }
            else if(((DefaultMutableTreeNode)node).getParent().toString().compareTo("\rTHEN")==0) {
                String nds = node.toString();
                if((nds.regionMatches(true, 0, "MODIFY", 0, 6))
                ||(nds.regionMatches(true, 0, "MAKE", 0, 4))
                || (nds.regionMatches(true, 0, "PRINT", 0, 5)))
                    set0inTHEN(node);
                else set0inIF(node);
                newEmptyTHEN(node);
            }
            
            else if(((DefaultMutableTreeNode)node).getParent().getParent().toString().compareTo("\rTHEN")==0) {
                String nds = node.getParent().toString();
                if((nds.regionMatches(true, 0, "MODIFY", 0, 6))
                ||(nds.regionMatches(true, 0, "MAKE", 0, 4))
                || (nds.regionMatches(true, 0, "PRINT", 0, 5)))
                    set0inTHEN((DefaultMutableTreeNode)(node.getParent()));
            }
            else {
                String nds = node.toString();
                if(nds.regionMatches(true, 0, "CLASS:", 0, 6)) set1inIF(node);
                else if(nds.compareToIgnoreCase("AND") == 0) set2inIF(node);
                else if(nds.compareToIgnoreCase("OR") == 0) set2inIF(node);
                else  set0inIF(node);
            }
            createOutputText();
        }
        
        private String tabs(int i) {
            StringBuffer buf = new StringBuffer();
            for(int ii = 0; ii < i; ii++) buf.append("\t");
            return buf.toString();
        }
        
        private String brunch(DefaultMutableTreeNode ob, int tabs) {
            int count = ob.getChildCount();
            if(count == 2) {
                DefaultMutableTreeNode ob1 = (DefaultMutableTreeNode)(model.getChild(ob, 0));
                DefaultMutableTreeNode ob2 = (DefaultMutableTreeNode)(model.getChild(ob, 1));
                return tabs(tabs) + "(\r\n" + brunch(ob1, tabs+1) +" "+ ob.toString() + "\r\n" + brunch(ob2, tabs+1) + ")";
            }
            else if(count == 1) {
                DefaultMutableTreeNode ob1 = (DefaultMutableTreeNode)(model.getChild(ob, 0));
                return tabs(tabs) + "(" + ob.toString() + "\r\n" + brunch(ob1, tabs+1) + ")";
            }
            else {
                return tabs(tabs) + "(" + ob.toString() + ")";
            }
        }
        
        private String ifbrunch(DefaultMutableTreeNode ob, int tabs) {
            int count = ob.getChildCount();
            if(count == 1) {
                DefaultMutableTreeNode ob1 = (DefaultMutableTreeNode)(model.getChild(ob, 0));
                return tabs(tabs) + "IF\r\n" + brunch(ob1, tabs+1);
            }
            return "// if";
        }
        
        private String createOutput4Then(DefaultMutableTreeNode nd) {
            StringBuffer buf = new StringBuffer();
            String nds = nd.toString();
            
            if(nds.regionMatches(true, 0, "MODIFY", 0, 6)) {
                String[] str;
                str = nds.split("\\*");
                
                if(str.length >= 2) {
                    buf.append("\r\n\t\t(" + str[0] +"\r\n");
                    int ocount = nd.getChildCount()-1;
                    for(int ii = 0; ii<ocount; ii++) {
                        DefaultMutableTreeNode obd = (DefaultMutableTreeNode)(model.getChild(nd, ii));
                        buf.append("\t\t\t(" + obd.toString() +")\r\n");
                    }
                    buf.append("\t\t" + str[1] + ")");
                }
                else if(str.length < 2) {
                    buf.append("\r\n\t\t(" + nds +"\r\n");
                    int ocount = nd.getChildCount()-1;
                    for(int ii = 0; ii<ocount; ii++) {
                        DefaultMutableTreeNode obd = (DefaultMutableTreeNode)(model.getChild(nd, ii));
                        buf.append("\t\t\t(" + obd.toString() +")\r\n");
                    }
                    buf.append("\t\t1000 0.001)");
                }
            }
            else if(nds.regionMatches(true, 0, "MAKE", 0, 4)) {
                buf.append("\r\n\t\t(" + nds +"\r\n");
                int ocount = nd.getChildCount()-1;
                for(int ii = 0; ii<ocount; ii++) {
                    DefaultMutableTreeNode obd = (DefaultMutableTreeNode)(model.getChild(nd, ii));
                    buf.append("\t\t\t(" + obd.toString() +")\r\n");
                }
                buf.append("\t\t)");
            }
            else if(nds.regionMatches(true, 0, "PRINT", 0, 5)) {
                buf.append("\r\n\t\t(" + nds +"\r\n");
                int ocount = nd.getChildCount()-1;
                for(int ii = 0; ii<ocount; ii++) {
                    DefaultMutableTreeNode obd = (DefaultMutableTreeNode)(model.getChild(nd, ii));
                    if(ii==0) buf.append("\t\t\t" + obd.toString() +"\r\n");
                    else buf.append("\t\t\t| " + obd.toString() +"\r\n");
                }
                buf.append("\t\t)");
            }
            
            
            else buf.append("\r\n\t\t(" + nds +")");
            
            return buf.toString();
        }
        private void createOutputText() {
            whatdone = 0;
            StringBuffer buf = new StringBuffer();
            buf.append("//***   Rules   ***\r\n");
            
            int rulcount, varcount;
            rulcount = root.getChildCount()-1;
            for(int i = 0; i<rulcount; i++) {
                DefaultMutableTreeNode ob = (DefaultMutableTreeNode)(model.getChild(root, i));
                buf.append("(RULE: " + ob.toString() +"\r\n");
                
                varcount = ob.getChildCount();
                if(varcount >= 2) {
                    DefaultMutableTreeNode vr = (DefaultMutableTreeNode)(model.getChild(ob, 0));
                    buf.append(ifbrunch(vr, 1) + "\r\n");
                    
                    vr = (DefaultMutableTreeNode)(model.getChild(ob, 1));
                    buf.append("\tTHEN (");
                    int ocount = vr.getChildCount()-1;
                    for(int ii = 0; ii<ocount; ii++) {
                        buf.append(createOutput4Then((DefaultMutableTreeNode)(model.getChild(vr, ii))));
                    }
                    buf.append("\r\n\t) COMMENT: \"\"");
                }
                buf.append(")\r\n\r\n");
            }
            
            if(text!=null)
                text.setText(buf.toString());
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }
    
    class TreeObjectsModelListener implements TreeModelListener {
        private javax.swing.tree.DefaultMutableTreeNode root;
        private javax.swing.tree.DefaultTreeModel model;
        private javax.swing.JTextArea text;
        
        private void BuildBranch(javax.swing.tree.DefaultMutableTreeNode nd, ArrayList al) {
            int size = al.size();
            if(size>0) {
                javax.swing.tree.DefaultMutableTreeNode n
                = new javax.swing.tree.DefaultMutableTreeNode((String)(al.get(0)));
                model.insertNodeInto(n, nd, nd.getChildCount());
                for(int i=1; i<size; i++) {
                    BuildBranch(n, (ArrayList)(al.get(i)));
                }
            }
        }
        
        public void ClearTree() {
            root.removeAllChildren();
            DefaultMutableTreeNode mtn = new DefaultMutableTreeNode("");
            model.insertNodeInto(mtn, root, root.getChildCount());
            model.reload();
        }
        
        public void BuildTree(ArrayList al) {
            if(al == null) return;
            int size = al.size();
            if(size>0) {
                if(al.get(0).toString().compareTo("\rClasses") != 0) return;
                
                root.removeAllChildren();
                model.reload();
                for(int i=1; i<size; i++) {
                    BuildBranch(root, (ArrayList)(al.get(i)));
                }
            }
        }
        
        public TreeObjectsModelListener(javax.swing.tree.DefaultMutableTreeNode r,
        javax.swing.tree.DefaultTreeModel m) {
            root = r;
            model = m;
            text = null;
        }
        public void setTextArea(javax.swing.JTextArea t) {
            text = t;
        }
        public void newEmptyObject() {
            for(int i = root.getChildCount()-1; i>=0; i--) {
                String name = model.getChild(root, i).toString();
                if(name.length() == 0) model.removeNodeFromParent((MutableTreeNode)(model.getChild(root, i)));
            }
            DefaultMutableTreeNode mtn = new DefaultMutableTreeNode("");
            model.insertNodeInto(mtn, root, root.getChildCount());
        }
        
        public void newNotEmptyObject(DefaultMutableTreeNode mtn) {
            if(mtn == null) return;
            
            for(int i = mtn.getChildCount()-1; i>=0; i--) {
                String name = model.getChild(mtn, i).toString();
                if(name.length() == 0) model.removeNodeFromParent((MutableTreeNode)(model.getChild(mtn, i)));
            }
            DefaultMutableTreeNode n = new DefaultMutableTreeNode("");
            model.insertNodeInto(n, mtn, mtn.getChildCount());
        }
        
        public void createOutputText() {
            whatdone = 0;
            StringBuffer buf = new StringBuffer();
            buf.append("module ReteObjects{\r\n");
            
            int objcount, varcount;
            objcount = root.getChildCount()-1;
            for(int i = 0; i<objcount; i++) {
                DefaultMutableTreeNode ob = (DefaultMutableTreeNode)(model.getChild(root, i));
                //if(i == 0)
                buf.append("\tstruct " + ob.toString() +"{\r\n");
                //else buf.append(" struct " + ob.toString() +"{\r\n");
                varcount = ob.getChildCount()-1;
                for(int j = 0; j<varcount; j++) {
                    DefaultMutableTreeNode vr = (DefaultMutableTreeNode)(model.getChild(ob, j));
                    buf.append("\t\t" + vr.toString() +";\r\n");
                    
                    //PMS - C -certainty dodawac tylko w przypadku analizy z propagacja prawdopodobienstwa
                    if(jCheckBox1.isSelected()) {
                        if(vr.toString().regionMatches(true, 0, "long ", 0, 5))
												{
                            buf.append("\t\t\tfloat _P_H_Ep_" + vr.toString().substring(5) +";\r\n");
                            //buf.append("\t\t\tfloat _LS_" + vr.toString().substring(5) +";\r\n");
                            //buf.append("\t\t\tfloat _LN_" + vr.toString().substring(5) +";\r\n");
												}
                        else if(vr.toString().regionMatches(true, 0, "double ", 0, 7))
												{
                            buf.append("\t\t\tfloat _P_H_Ep_" + vr.toString().substring(7) +";\r\n");
                            //buf.append("\t\t\tfloat _LS_" + vr.toString().substring(7) +";\r\n");
                            //buf.append("\t\t\tfloat _LN_" + vr.toString().substring(7) +";\r\n");
												}
                        else if(vr.toString().regionMatches(true, 0, "string ", 0, 7))
												{
                            buf.append("\t\t\tfloat _P_H_Ep_" + vr.toString().substring(7) +";\r\n");
                            //buf.append("\t\t\tfloat _LS_" + vr.toString().substring(7) +";\r\n");
                            //buf.append("\t\t\tfloat _LN_" + vr.toString().substring(7) +";\r\n");
												}
                    }
                }
                buf.append("\t};\r\n");
            }
            buf.append("};");
            
            if(text!=null)
                text.setText(buf.toString());
        }
        
        
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)
            (e.getTreePath().getLastPathComponent());
            
            /*
             * If the event lists children, then the changed
             * node is the child of the node we've already
             * gotten.  Otherwise, the changed node and the
             * specified node are the same.
             */
            try {
                int index = e.getChildIndices()[0];
                node = (DefaultMutableTreeNode)
                (node.getChildAt(index));
            } catch (NullPointerException exc) {}
            
            if(((DefaultMutableTreeNode)node).getParent() == ((DefaultMutableTreeNode)node).getRoot()) {
                newNotEmptyObject(node);
                newEmptyObject();
            }
            else {
                String[] s = node.toString().split(" ");
                int sl = s.length;
                int l = 0;
                StringBuffer ns = new StringBuffer();
                for(int i = 0; i < sl; i++) {
                    if(s[i].length()>0) {
                        if(i>l) s[l] = s[i];
                        l++;
                    }
                }
                if(l == 0) node.setUserObject("");
                else if(l == 1) node.setUserObject("string " + s[0]);
                else if(l > 1) {
                    char c = s[0].charAt(0);
                    if(c == 'd' || c == 'f' || c == 'D' || c == 'F') node.setUserObject("double " + s[1]);
                    else if(c == 'i' || c == 'l' || c == 'I' || c == 'L') node.setUserObject("long " + s[1]);
                    else node.setUserObject("string " + s[1]);
                }
                model.reload(node);
                //model.nodeChanged(node);
                newNotEmptyObject((DefaultMutableTreeNode)(((DefaultMutableTreeNode)node).getParent()));
            }
            createOutputText();
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItemRule = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItem1Edit = new javax.swing.JMenuItem();
        jMenuItem1Clear = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        jMenuItem1Ex = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItemBind = new javax.swing.JMenuItem();
        jMenuItemDisplay = new javax.swing.JMenuItem();
        jMenuItemExecute = new javax.swing.JMenuItem();
        jMenuItemMake = new javax.swing.JMenuItem();
        jMenuItemModify = new javax.swing.JMenuItem();
        jMenuItemPrint = new javax.swing.JMenuItem();
        jMenuItemRead = new javax.swing.JMenuItem();
        jMenuItemRemove = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItem2Edit = new javax.swing.JMenuItem();
        jMenuItem2Clear = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JSeparator();
        jMenuItem2Ex = new javax.swing.JMenuItem();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jMenuItemIPrint = new javax.swing.JMenuItem();
        jMenuItemSPrint = new javax.swing.JMenuItem();
        jMenuItemNPrint = new javax.swing.JMenuItem();
        jMenuItemEPrint = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItem3Edit = new javax.swing.JMenuItem();
        jMenuItem3Clear = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        jMenuItem3Ex = new javax.swing.JMenuItem();
        jPopupMenu4 = new javax.swing.JPopupMenu();
        jMenuItemIModify = new javax.swing.JMenuItem();
        jMenuItemSModify = new javax.swing.JMenuItem();
        jMenuItemNModify = new javax.swing.JMenuItem();
        jMenuItemEModify = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        jMenuItem4Edit = new javax.swing.JMenuItem();
        jMenuItem4Clear = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JSeparator();
        jMenuItem4Ex = new javax.swing.JMenuItem();
        jPopupMenu5 = new javax.swing.JPopupMenu();
        jMenuItemAND = new javax.swing.JMenuItem();
        jMenuItemOR = new javax.swing.JMenuItem();
        jMenuItemCLASS = new javax.swing.JMenuItem();
        jMenuItemCONDITION = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        jMenuItem5Edit = new javax.swing.JMenuItem();
        jMenuItem5Clear = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JSeparator();
        jMenuItem5Ex = new javax.swing.JMenuItem();
        jPopupMenu6 = new javax.swing.JPopupMenu();
        jMenuItemClassName = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JSeparator();
        jMenuItem6Edit = new javax.swing.JMenuItem();
        jMenuItem6Clear = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JSeparator();
        jMenuItem6Ex = new javax.swing.JMenuItem();
        jPopupMenu7 = new javax.swing.JPopupMenu();
        jMenuItemIneger = new javax.swing.JMenuItem();
        jMenuItemDouble = new javax.swing.JMenuItem();
        jMenuItemString = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JSeparator();
        jMenuItem7Edit = new javax.swing.JMenuItem();
        jMenuItem7Clear = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JSeparator();
        jMenuItem7Ex = new javax.swing.JMenuItem();
        jFrameImage = new javax.swing.JFrame();
        jScrollPane6 = new javax.swing.JScrollPane();
        jLabelImage = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonNew = new javax.swing.JButton();
        jButtonOpen = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonRun = new javax.swing.JButton();
        jButtonMemory = new javax.swing.JButton();
        jSplitPane3 = new javax.swing.JSplitPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree(treeModelRules);
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree(/*rootTreeRules*/treeModelObjects);
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField23 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jSeparator17 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemLoad = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemDefClass = new javax.swing.JMenuItem();
        jMenuItemAugClass = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JSeparator();
        jMenuItemParse = new javax.swing.JMenuItem();
        jMenuItemCompile = new javax.swing.JMenuItem();
        jMenuItemRun = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemServer = new javax.swing.JMenuItem();
        jMenuItemImage = new javax.swing.JMenuItem();

        jPopupMenu1.setFont(new java.awt.Font("Dialog", 1, 10));
        jPopupMenu1.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuItemRule.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemRule.setText("<identifier> <numeric>");
        jMenuItemRule.setBorder(null);
        jMenuItemRule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuItemRule);

        jPopupMenu1.add(jSeparator2);

        jMenuItem1Edit.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem1Edit.setText("Edit");
        jMenuItem1Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1EditActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuItem1Edit);

        jMenuItem1Clear.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem1Clear.setText("Clear");
        jMenuItem1Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ClearActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuItem1Clear);

        jPopupMenu1.add(jSeparator7);

        jMenuItem1Ex.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem1Ex.setText("ProblemNoGas 10");
        jMenuItem1Ex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuItem1Ex);

        jPopupMenu2.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemBind.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemBind.setText("BIND VAR: $<identifier> <expression>");
        jMenuItemBind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemBind);

        jMenuItemDisplay.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemDisplay.setText("DISPLAY <filename>");
        jMenuItemDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemDisplay);

        jMenuItemExecute.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemExecute.setText("EXECUTE <filename>");
        jMenuItemExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemExecute);

        jMenuItemMake.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemMake.setText("MAKE CLASS: <identifier> OBJ: <identifier>");
        jMenuItemMake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemMake);

        jMenuItemModify.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemModify.setText("MODIFY OBJ: $<identifier> * <numeric> <numeric>");
        jMenuItemModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemModify);

        jMenuItemPrint.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemPrint.setText("PRINT");
        jMenuItemPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemPrint);

        jMenuItemRead.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemRead.setText("READ PROMPT: <\"string\"> VAR: $<identifier> TYPE: <identifier>");
        jMenuItemRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemRead);

        jMenuItemRemove.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemRemove.setText("REMOVE OBJ: $<identifier>");
        jMenuItemRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemRemove);

        jPopupMenu2.add(jSeparator3);

        jMenuItem2Edit.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem2Edit.setText("Edit");
        jMenuItem2Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1EditActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItem2Edit);

        jMenuItem2Clear.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem2Clear.setText("Clear");
        jMenuItem2Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ClearActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItem2Clear);

        jPopupMenu2.add(jSeparator8);

        jMenuItem2Ex.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem2Ex.setText("MODIFY OBJ: $x * 1000 0.001");
        jMenuItem2Ex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItem2Ex);

        jPopupMenu3.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemIPrint.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemIPrint.setText("$<identifier>");
        jMenuItemIPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu3.add(jMenuItemIPrint);

        jMenuItemSPrint.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemSPrint.setText("<\"string\">");
        jMenuItemSPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu3.add(jMenuItemSPrint);

        jMenuItemNPrint.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemNPrint.setText("<numeric>");
        jMenuItemNPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu3.add(jMenuItemNPrint);

        jMenuItemEPrint.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemEPrint.setText("<expression>");
        jMenuItemEPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu3.add(jMenuItemEPrint);

        jPopupMenu3.add(jSeparator4);

        jMenuItem3Edit.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem3Edit.setText("Edit");
        jMenuItem3Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1EditActionPerformed(evt);
            }
        });

        jPopupMenu3.add(jMenuItem3Edit);

        jMenuItem3Clear.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem3Clear.setText("Clear");
        jMenuItem3Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ClearActionPerformed(evt);
            }
        });

        jPopupMenu3.add(jMenuItem3Clear);

        jPopupMenu3.add(jSeparator9);

        jMenuItem3Ex.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem3Ex.setText("\"Hello\"");
        jMenuItem3Ex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu3.add(jMenuItem3Ex);

        jPopupMenu4.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemIModify.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemIModify.setText("<identifier> $<identifier>");
        jMenuItemIModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu4.add(jMenuItemIModify);

        jMenuItemSModify.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemSModify.setText("<identifier> <\"string\">");
        jMenuItemSModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu4.add(jMenuItemSModify);

        jMenuItemNModify.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemNModify.setText("<identifier> <numeric>");
        jMenuItemNModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu4.add(jMenuItemNModify);

        jMenuItemEModify.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemEModify.setText("<identifier> <expression>");
        jMenuItemEModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu4.add(jMenuItemEModify);

        jPopupMenu4.add(jSeparator5);

        jMenuItem4Edit.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem4Edit.setText("Edit");
        jMenuItem4Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1EditActionPerformed(evt);
            }
        });

        jPopupMenu4.add(jMenuItem4Edit);

        jMenuItem4Clear.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem4Clear.setText("Clear");
        jMenuItem4Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ClearActionPerformed(evt);
            }
        });

        jPopupMenu4.add(jMenuItem4Clear);

        jPopupMenu4.add(jSeparator10);

        jMenuItem4Ex.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem4Ex.setText("x $y+10-$z");
        jMenuItem4Ex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu4.add(jMenuItem4Ex);

        jPopupMenu5.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemAND.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemAND.setText("AND");
        jMenuItemAND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu5.add(jMenuItemAND);

        jMenuItemOR.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemOR.setText("OR");
        jMenuItemOR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu5.add(jMenuItemOR);

        jMenuItemCLASS.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemCLASS.setText("CLASS: <identifier> OBJ: $<identifier>");
        jMenuItemCLASS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu5.add(jMenuItemCLASS);

        jMenuItemCONDITION.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemCONDITION.setText("<identifier> <relation> <expression>");
        jMenuItemCONDITION.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu5.add(jMenuItemCONDITION);

        jPopupMenu5.add(jSeparator6);

        jMenuItem5Edit.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem5Edit.setText("Edit");
        jMenuItem5Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1EditActionPerformed(evt);
            }
        });

        jPopupMenu5.add(jMenuItem5Edit);

        jMenuItem5Clear.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem5Clear.setText("Clear");
        jMenuItem5Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ClearActionPerformed(evt);
            }
        });

        jPopupMenu5.add(jMenuItem5Clear);

        jPopupMenu5.add(jSeparator11);

        jMenuItem5Ex.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem5Ex.setText("x <= $y+10-$z");
        jMenuItem5Ex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTemplateActionPerformed(evt);
            }
        });

        jPopupMenu5.add(jMenuItem5Ex);

        jMenuItemClassName.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemClassName.setText("<identifier>");
        jMenuItemClassName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteClassActionPerformed(evt);
            }
        });

        jPopupMenu6.add(jMenuItemClassName);

        jPopupMenu6.add(jSeparator12);

        jMenuItem6Edit.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem6Edit.setText("Edit");
        jMenuItem6Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6EditActionPerformed(evt);
            }
        });

        jPopupMenu6.add(jMenuItem6Edit);

        jMenuItem6Clear.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem6Clear.setText("Clear");
        jMenuItem6Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ClearActionPerformed(evt);
            }
        });

        jPopupMenu6.add(jMenuItem6Clear);

        jPopupMenu6.add(jSeparator13);

        jMenuItem6Ex.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem6Ex.setText("mechanic");
        jMenuItem6Ex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteClassActionPerformed(evt);
            }
        });

        jPopupMenu6.add(jMenuItem6Ex);

        jMenuItemIneger.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemIneger.setText("i <identifier>");
        jMenuItemIneger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteClassActionPerformed(evt);
            }
        });

        jPopupMenu7.add(jMenuItemIneger);

        jMenuItemDouble.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemDouble.setText("d <identifier>");
        jMenuItemDouble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteClassActionPerformed(evt);
            }
        });

        jPopupMenu7.add(jMenuItemDouble);

        jMenuItemString.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItemString.setText("s <identifier>");
        jMenuItemString.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteClassActionPerformed(evt);
            }
        });

        jPopupMenu7.add(jMenuItemString);

        jPopupMenu7.add(jSeparator14);

        jMenuItem7Edit.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem7Edit.setText("Edit");
        jMenuItem7Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6EditActionPerformed(evt);
            }
        });

        jPopupMenu7.add(jMenuItem7Edit);

        jMenuItem7Clear.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem7Clear.setText("Clear");
        jMenuItem7Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ClearActionPerformed(evt);
            }
        });

        jPopupMenu7.add(jMenuItem7Clear);

        jPopupMenu7.add(jSeparator15);

        jMenuItem7Ex.setFont(new java.awt.Font("Dialog", 1, 10));
        jMenuItem7Ex.setText("s name");
        jMenuItem7Ex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteClassActionPerformed(evt);
            }
        });

        jPopupMenu7.add(jMenuItem7Ex);

        jScrollPane6.setBorder(null);
        jLabelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScrollPane6.setViewportView(jLabelImage);

        jFrameImage.getContentPane().add(jScrollPane6, java.awt.BorderLayout.CENTER);

        setTitle(" MORE Integrated Development Environment");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jToolBar1.setBorder(null);
        jButtonNew.setFont(new java.awt.Font("Dialog", 1, 10));
        jButtonNew.setIcon(new javax.swing.ImageIcon("new.gif"));
        jButtonNew.setText("New  ");
        jButtonNew.setBorder(null);
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });

        jToolBar1.add(jButtonNew);

        jButtonOpen.setFont(new java.awt.Font("Dialog", 1, 10));
        jButtonOpen.setIcon(new javax.swing.ImageIcon("opn.gif"));
        jButtonOpen.setText("Open  ");
        jButtonOpen.setBorder(null);
        jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenActionPerformed(evt);
            }
        });

        jToolBar1.add(jButtonOpen);

        jButtonSave.setFont(new java.awt.Font("Dialog", 1, 10));
        jButtonSave.setIcon(new javax.swing.ImageIcon("sav.gif"));
        jButtonSave.setText("Save  ");
        jButtonSave.setBorder(null);
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jToolBar1.add(jButtonSave);

        jButtonRun.setFont(new java.awt.Font("Dialog", 1, 10));
        jButtonRun.setIcon(new javax.swing.ImageIcon("run.gif"));
        jButtonRun.setText("Run  ");
        jButtonRun.setToolTipText("Run expert system");
        jButtonRun.setBorder(null);
        jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunActionPerformed(evt);
            }
        });

        jToolBar1.add(jButtonRun);

        jButtonMemory.setFont(new java.awt.Font("Dialog", 1, 10));
        jButtonMemory.setIcon(new javax.swing.ImageIcon("mem.gif"));
        jButtonMemory.setText("Memory  ");
        jButtonMemory.setBorder(null);
        jButtonMemory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMemoryActionPerformed(evt);
            }
        });

        jToolBar1.add(jButtonMemory);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jSplitPane3.setBorder(null);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jTabbedPane1.setName("");
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(205, 202));
        jSplitPane2.setBorder(null);
        jScrollPane4.setBorder(null);
        jTree2.setEditable(true);
        jTree2.setShowsRootHandles(true);
        jTree2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTree2FocusGained(evt);
            }
        });
        jTree2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree2MouseClicked(evt);
            }
        });

        jScrollPane4.setViewportView(jTree2);

        jSplitPane2.setLeftComponent(jScrollPane4);

        jScrollPane3.setBorder(null);
        jTextArea2.setLineWrap(true);
        jTextArea2.setTabSize(2);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextArea2CaretUpdate(evt);
            }
        });
        jTextArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea2KeyPressed(evt);
            }
        });

        jScrollPane3.setViewportView(jTextArea2);

        jSplitPane2.setRightComponent(jScrollPane3);

        jTabbedPane1.addTab("Rules", jSplitPane2);

        jSplitPane1.setBorder(null);
        jScrollPane5.setBorder(null);
        jTree1.setEditable(true);
        jTree1.setShowsRootHandles(true);
        jTree1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTree1FocusGained(evt);
            }
        });
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });

        jScrollPane5.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane5);

        jScrollPane2.setBorder(null);
        jTextArea1.setTabSize(2);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextArea1CaretUpdate(evt);
            }
        });
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea1KeyPressed(evt);
            }
        });

        jScrollPane2.setViewportView(jTextArea1);

        jSplitPane1.setRightComponent(jScrollPane2);

        jTabbedPane1.addTab("Classes", jSplitPane1);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(98301, 200));
        jPanel2.setLayout(new java.awt.GridLayout(8, 1));

        jPanel2.setBorder(new javax.swing.border.TitledBorder("Tools"));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel1.setText("java compiler:");
        jPanel2.add(jLabel1);

        jTextField2.setText("javac");
        jPanel2.add(jTextField2);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel11.setText("java engine:");
        jPanel2.add(jLabel11);

        jTextField21.setText("java");
        jPanel2.add(jTextField21);

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel12.setText("idl to java compiler:");
        jPanel2.add(jLabel12);

        jTextField22.setText("idlj -fall");
        jPanel2.add(jTextField22);

        jPanel1.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridLayout(8, 1));

        jPanel3.setBorder(new javax.swing.border.TitledBorder("Memory"));
        jRadioButton1.setFont(new java.awt.Font("Dialog", 1, 10));
        jRadioButton1.setText("Transient");
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jPanel3.add(jRadioButton1);

        jRadioButton2.setFont(new java.awt.Font("Dialog", 1, 10));
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Persistent");
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jPanel3.add(jRadioButton2);

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel13.setText("ORB daemon:");
        jPanel3.add(jLabel13);

        jTextField23.setText("orbd");
        jPanel3.add(jTextField23);

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel14.setText("server name argument:");
        jLabel14.setToolTipText("-ORBInitialHost <host name>");
        jPanel3.add(jLabel14);

        jTextField24.setText("-ORBInitialHost");
        jTextField24.setToolTipText("-ORBInitialHost <host name>");
        jTextField24.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField24PropertyChange(evt);
            }
        });

        jPanel3.add(jTextField24);

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 10));
        jLabel15.setText("port number:");
        jLabel15.setToolTipText("-ORBInitialPort <number>");
        jPanel3.add(jLabel15);

        jTextField25.setText("-ORBInitialPort 2500");
        jTextField25.setToolTipText("-ORBInitialPort <number>");
        jTextField25.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField25PropertyChange(evt);
            }
        });

        jPanel3.add(jTextField25);

        jPanel1.add(jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(8, 1));

        jPanel4.setBorder(new javax.swing.border.TitledBorder("Engine"));
        jRadioButton3.setFont(new java.awt.Font("Dialog", 1, 10));
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("Forward Chaining (Lex)");
        buttonGroup2.add(jRadioButton3);
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jPanel4.add(jRadioButton3);

        jRadioButton4.setFont(new java.awt.Font("Dialog", 1, 10));
        jRadioButton4.setText("Forward chaining (MEA)");
        buttonGroup2.add(jRadioButton4);
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jPanel4.add(jRadioButton4);

        jRadioButton5.setFont(new java.awt.Font("Dialog", 1, 10));
        jRadioButton5.setText("Backward Chaining");
        buttonGroup2.add(jRadioButton5);
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        jPanel4.add(jRadioButton5);

        jSeparator17.setFont(new java.awt.Font("Dialog", 0, 10));
        jSeparator17.setMaximumSize(new java.awt.Dimension(32767, 4));
        jPanel4.add(jSeparator17);

        jCheckBox1.setFont(new java.awt.Font("Dialog", 1, 10));
        jCheckBox1.setText("Inexact Inference");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jPanel4.add(jCheckBox1);

        jPanel1.add(jPanel4);

        jTabbedPane1.addTab("Options", jPanel1);

        jSplitPane3.setLeftComponent(jTabbedPane1);

        jScrollPane1.setBorder(null);
        jTextArea3.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea3.setEditable(false);
        jTextArea3.setFont(new java.awt.Font("Dialog", 0, 10));
        jTextArea3.setForeground(new java.awt.Color(204, 204, 204));
        jTextArea3.setTabSize(2);
        jTextArea3.setToolTipText("");
        jTextArea3.setCaretColor(new java.awt.Color(255, 255, 0));
        jTextArea3.setMargin(new java.awt.Insets(0, 4, 0, 0));
        jTextArea3.setSelectedTextColor(new java.awt.Color(192, 192, 192));
        jTextArea3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea3KeyPressed(evt);
            }
        });

        jScrollPane1.setViewportView(jTextArea3);

        jSplitPane3.setRightComponent(jScrollPane1);

        getContentPane().add(jSplitPane3, java.awt.BorderLayout.CENTER);

        jTextField1.setBackground(new java.awt.Color(204, 204, 204));
        jTextField1.setEditable(false);
        jTextField1.setText("ver. 1.00");
        jTextField1.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
        getContentPane().add(jTextField1, java.awt.BorderLayout.SOUTH);

        jMenu1.setText("File");
        jMenuItemNew.setText("New");
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItemNew);

        jMenuItemLoad.setText("Load...");
        jMenuItemLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLoadActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItemLoad);

        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItemSave);

        jMenuItemSaveAs.setText("Save as...");
        jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveAsActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItemSaveAs);

        jMenu1.add(jSeparator1);

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Build");
        jMenuItemDefClass.setText("Define classes");
        jMenuItemDefClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDefClassActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemDefClass);

        jMenuItemAugClass.setText("Augment classes");
        jMenuItemAugClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAugClassActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemAugClass);

        jMenu2.add(jSeparator16);

        jMenuItemParse.setText("Parse");
        jMenuItemParse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemParseActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemParse);

        jMenuItemCompile.setText("Compile");
        jMenuItemCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCompileActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemCompile);

        jMenuItemRun.setText("Run");
        jMenuItemRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRunActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemRun);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Tools");
        jMenuItemServer.setText("Start memory server");
        jMenuItemServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemServerActionPerformed(evt);
            }
        });

        jMenu3.add(jMenuItemServer);

        jMenuItemImage.setText("Image frame...");
        jMenuItemImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemImageActionPerformed(evt);
            }
        });

        jMenu3.add(jMenuItemImage);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }//GEN-END:initComponents
    
    private void jTextField25PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField25PropertyChange
        whatdone = 0;
    }//GEN-LAST:event_jTextField25PropertyChange
    
    private void jTextField24PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField24PropertyChange
        whatdone = 0;
    }//GEN-LAST:event_jTextField24PropertyChange
    
    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        whatdone = 0;
    }//GEN-LAST:event_jRadioButton5ActionPerformed
    
    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        whatdone = 0;
    }//GEN-LAST:event_jRadioButton4ActionPerformed
    
    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        whatdone = 0;
    }//GEN-LAST:event_jRadioButton3ActionPerformed
    
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(!text1_changed) 
           listenerObjects.createOutputText();
        whatdone = 0;
    }//GEN-LAST:event_jCheckBox1ActionPerformed
    
    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        jLabel13.setEnabled(true);
        jTextField23.setEnabled(true);
        jLabel14.setEnabled(true);
        jTextField24.setEnabled(true);
        jLabel15.setEnabled(true);
        jTextField25.setEnabled(true);
        whatdone = 0;
    }//GEN-LAST:event_jRadioButton2ActionPerformed
    
    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        jLabel13.setEnabled(false);
        jTextField23.setEnabled(false);
        jLabel14.setEnabled(false);
        jTextField24.setEnabled(false);
        jLabel15.setEnabled(false);
        jTextField25.setEnabled(false);
        whatdone = 0;
    }//GEN-LAST:event_jRadioButton1ActionPerformed
    
    private void jTextArea3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea3KeyPressed
        try {
            if(evt.getKeyCode()==evt.VK_ENTER) {
                int off = jTextArea3.getCaretPosition();
                int lin = jTextArea3.getLineOfOffset(off);
                int beg = jTextArea3.getLineStartOffset(lin);
                String str = jTextArea3.getText(beg, off-beg) +"\r\n";
                
                System.out.println("Enter pressed: " + str);
            }
        } catch(BadLocationException e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_jTextArea3KeyPressed
    
    private void jMenuItemImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemImageActionPerformed
        // Add your handling code here:
        try {
            JFileChooser chooser = new JFileChooser();
            ImgFileFilter rff = new ImgFileFilter();
            chooser.setFileFilter(rff);
            int returnVal = chooser.showOpenDialog(jf);
            
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                if(zerosize == null) {
                    jFrameImage.show();
                    Dimension fdim = jFrameImage.getSize();
                    Dimension pdim = jScrollPane6.getSize();
                    zerosize = new Dimension(fdim.width - pdim.width, fdim.height - pdim.height);
                }
                ImageIcon ii = new javax.swing.ImageIcon(chooser.getSelectedFile().getPath());
                jLabelImage.setIcon(ii);
                Rectangle rr = jFrameImage.getBounds();
                jFrameImage.setBounds(rr.x, rr.y, zerosize.width + ii.getIconWidth(), zerosize.height + ii.getIconHeight());
                jFrameImage.setTitle(chooser.getSelectedFile().getName());
                jFrameImage.show();
            }
        }
        catch (Throwable exc) {
            jTextArea3.append(exc.getMessage() + "\r\n");
        }
    }//GEN-LAST:event_jMenuItemImageActionPerformed
    
    public class ImgFileFilter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File f) {
            if(f == null) return false;
            if(f.isDirectory()) return true;
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
                String ext = filename.substring(i+1).toLowerCase();
                if(ext != null) {
                    if(ext.compareTo("jpg")==0) return true;
                    else if(ext.compareTo("jpe")==0) return true;
                    else if(ext.compareTo("jpeg")==0) return true;
                    else if(ext.compareTo("gif")==0) return true;
                    else if(ext.compareTo("png")==0) return true;
                }
            }
            return false;
        }
        public String getDescription() {
            return "Image files (*.jpg; *.gif; *.png)";
        }
    }
    
    private void jMenuItemServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemServerActionPerformed
        // Add your handling code here:
        
        try {
/*
            DynamicClassLoader cl = new DynamicClassLoader();
            Class c = cl.loadClass("WorkingMemory.class", "WorkingMemory");
            Object o = c.newInstance();
            Class[] r = new Class[] {(new String[]{}).getClass()};
            Method m = c.getMethod("main", r);
            System.out.print("Starting memory module\r\n");
            Object[] oo = new Object[] {""};
            m.invoke(o, oo);
*/
            SaveOptions();
            Runtime.getRuntime().exec(jTextField21.getText()+" WorkingMemory", null, null);
        } catch (Throwable exc) {
            System.err.println(exc.getMessage());
            exc.printStackTrace();
        }
        
        
        
    }//GEN-LAST:event_jMenuItemServerActionPerformed
    
    class DynamicClassLoader extends ClassLoader {
        public synchronized Class loadClass(String filename, String classname) {
            byte[] data = getClassData(filename);
            if(data != null) {
                Class c = defineClass(classname, data, 0, data.length);
                resolveClass(c);
                return c;
            }
            else return null;
        }
        byte[] getClassData(String filename) {
            int l;
            byte[] b;
            try {
                File inputFile = new File(filename);
                l = (int)(inputFile.length());
                b = new byte[l];
                FileInputStream inputStream = new FileInputStream(inputFile);
                inputStream.read(b, 0, l);
                inputStream.close();
                return b;
            }
            catch(IOException e) {
                System.err.println(e);
                e.printStackTrace();
            }
            return null;
        }
    };
    
    private void jMenuItemRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRunActionPerformed
        
        jTextArea3.setText("");
        CompileThread p = new CompileThread(jTextArea3, 127);
        p.start();
    }//GEN-LAST:event_jMenuItemRunActionPerformed
    
    private void jMenuItemCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCompileActionPerformed
        jTextArea3.setText("");
        CompileThread p = new CompileThread(jTextArea3, 63);
        p.start();
    }//GEN-LAST:event_jMenuItemCompileActionPerformed
    
    private void jMenuItemParseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemParseActionPerformed
        jTextArea3.setText("");
        CompileThread p = new CompileThread(jTextArea3, 6);
        p.start();
/*
        jTextArea3.setText("");
        String str = jTextArea2.getText().replace('\t', ' ');
        try {
            if(parser == null) parser = new RuleParser(new StringReader(str));
            else
            {
                parser.ReInit(new StringReader(str));
                parser.ClearStaticVars();
            }
            parser.Start();
            parser.CreateSourceFiles(null);
            jTextArea3.append("Parsing done\r\n");
        } catch (Throwable exc) {
            System.err.println(exc.getMessage());
            jTextArea3.append(exc.getMessage() + "\r\n");
        }
 */
    }//GEN-LAST:event_jMenuItemParseActionPerformed
    
    private void jMenuItemAugClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAugClassActionPerformed
        ExtractClassDefsFromRules(true);
    }//GEN-LAST:event_jMenuItemAugClassActionPerformed
    
    private void jMenuItemDefClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDefClassActionPerformed
        ExtractClassDefsFromRules(false);
    }//GEN-LAST:event_jMenuItemDefClassActionPerformed
    
    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        if(idlfilename == null || rulfilename == null || rulidlfolder == null) {
            if(evt == null) return;
            jMenuItemSaveAsActionPerformed(evt);
        }
        else {
            try {
                File file = new File(rulidlfolder+idlfilename);
                FileWriter writer = new FileWriter(file);
                String outputstring = jTextArea1.getText();
                writer.write(outputstring, 0, outputstring.length());
                writer.close();
            }
            catch (Throwable exc) {
                jTextArea3.append(exc.getMessage() + "\r\n");
            }
            try {
                File file = new File(rulidlfolder+rulfilename);
                FileWriter writer = new FileWriter(file);
                String outputstring = jTextArea2.getText();
                writer.write(outputstring, 0, outputstring.length());
                writer.close();
            }
            catch (Throwable exc) {
                jTextArea3.append(exc.getMessage() + "\r\n");
            }
        }
    }//GEN-LAST:event_jMenuItemSaveActionPerformed
    
    private void jButtonMemoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMemoryActionPerformed
        jMenuItemServerActionPerformed(null);
    }//GEN-LAST:event_jButtonMemoryActionPerformed
    
    private void jMenuItemSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveAsActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();
            RulFileFilter rff = new RulFileFilter();
            chooser.setFileFilter(rff);
            int returnVal = chooser.showSaveDialog(jf);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getPath();
                String name = chooser.getSelectedFile().getName();
                String iame = "rule.idl";
                String dir  = "";
                int i = path.length()-name.length();
                if(i > 0) dir = path.substring(0, i);
                i = name.lastIndexOf('.');
                if(i>0 && i<path.length()-1)
                    iame = name.substring(0, i) + ".idl";
                else iame = name + ".idl";
                rulfilename = name;
                idlfilename = iame;
                rulidlfolder = dir;
                jMenuItemSaveActionPerformed(null);
                setTitle("MORE IDE - " + rulfilename + ", " +idlfilename);
            }
        }
        catch(Throwable e) {
            jTextArea3.append(e.getMessage() + "\r\n");
        }
    }//GEN-LAST:event_jMenuItemSaveAsActionPerformed
    
    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewActionPerformed
        whatdone = 0;
        rulfilename = null;
        idlfilename = null;
        rulidlfolder = null;
        
        jTextArea1.setText("");
        jTextArea2.setText("");
        
        listenerObjects.ClearTree();
        listenerRules.ClearTree();
        
        setTitle("MORE Integrated Development Environment");
    }//GEN-LAST:event_jMenuItemNewActionPerformed
    
    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
        jMenuItemNewActionPerformed(evt);
    }//GEN-LAST:event_jButtonNewActionPerformed
    
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        jMenuItemSaveActionPerformed(evt);
    }//GEN-LAST:event_jButtonSaveActionPerformed
    
    private void jButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenActionPerformed
        jMenuItemLoadActionPerformed(evt);
    }//GEN-LAST:event_jButtonOpenActionPerformed
    
    private void jMenuItem7ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ClearActionPerformed
        TreePath tp = jTree1.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)(jTree1.getLastSelectedPathComponent());
        if(node == null) return;
        boolean edit = true;
        if(node.toString().length() > 0)
            if(node.toString().charAt(0) == '\r') edit = false;
        jTree1.setEditable(edit);
        if(edit) {
            node.setUserObject(new String(""));
            jTree1.startEditingAtPath(tp);
            jTree1.stopEditing();
        }
    }//GEN-LAST:event_jMenuItem7ClearActionPerformed
    
    private void jMenuItem6EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6EditActionPerformed
        // Add your handling code here:
        TreePath tp = jTree1.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)(jTree1.getLastSelectedPathComponent());
        if(node == null) return;
        boolean edit = true;
        if(node.toString().length() > 0)
            if(node.toString().charAt(0) == '\r') edit = false;
        jTree1.setEditable(edit);
        if(edit)
            jTree1.startEditingAtPath(tp);
    }//GEN-LAST:event_jMenuItem6EditActionPerformed
    
    private void jMenuItemPasteClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPasteClassActionPerformed
        // Add your handling code here:
        TreePath tp = jTree1.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)(jTree1.getLastSelectedPathComponent());
        if(node == null) return;
        boolean edit = true;
        if(node.toString().length() > 0)
            if(node.toString().charAt(0) == '\r') edit = false;
        jTree1.setEditable(edit);
        if(edit) {
            Object prevob = node.getUserObject();
            node.setUserObject(evt.getActionCommand());
            jTree1.startEditingAtPath(tp);
            node.setUserObject(prevob);
        }
    }//GEN-LAST:event_jMenuItemPasteClassActionPerformed
    
    private void jMenuItem1ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ClearActionPerformed
        // Add your handling code here:
        TreePath tp = jTree2.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)(jTree2.getLastSelectedPathComponent());
        if(node == null) return;
        boolean edit = true;
        if(node.toString().length() > 0)
            if(node.toString().charAt(0) == '\r') edit = false;
        jTree2.setEditable(edit);
        if(edit) {
            node.setUserObject(new String(""));
            jTree2.startEditingAtPath(tp);
            jTree2.stopEditing();
        }
    }//GEN-LAST:event_jMenuItem1ClearActionPerformed
    
    private void jMenuItem1EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1EditActionPerformed
        // Add your handling code here:
        TreePath tp = jTree2.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)(jTree2.getLastSelectedPathComponent());
        if(node == null) return;
        boolean edit = true;
        if(node.toString().length() > 0)
            if(node.toString().charAt(0) == '\r') edit = false;
        jTree2.setEditable(edit);
        if(edit)
            jTree2.startEditingAtPath(tp);
    }//GEN-LAST:event_jMenuItem1EditActionPerformed
    
    private void jMenuItemTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTemplateActionPerformed
        // Add your handling code here:
        
        //        System.out.println("paramString: "+evt.paramString());
        //        System.out.println("actionString: "+evt.getActionCommand());
        
        TreePath tp = jTree2.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)(jTree2.getLastSelectedPathComponent());
        if(node == null) return;
        boolean edit = true;
        if(node.toString().length() > 0)
            if(node.toString().charAt(0) == '\r') edit = false;
        jTree2.setEditable(edit);
        if(edit) {
            Object prevob = node.getUserObject();
            node.setUserObject(evt.getActionCommand());
            jTree2.startEditingAtPath(tp);
            node.setUserObject(prevob);
        }
    }//GEN-LAST:event_jMenuItemTemplateActionPerformed
    
    private void jTextArea2CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextArea2CaretUpdate
        // Add your handling code here:
        try {
            int off = evt.getDot();
            int lin = jTextArea2.getLineOfOffset(evt.getDot());
            int row = off - jTextArea2.getLineStartOffset(lin);
            jTextField1.setText(" " + (lin+1) + ": " + row);
        }
        catch(BadLocationException e){}
    }//GEN-LAST:event_jTextArea2CaretUpdate
    
    private void jTextArea1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextArea1CaretUpdate
        //jTextField1.setText("b " + jTextArea1.getCaretPosition());
        try {
            int off = evt.getDot();
            int lin = jTextArea1.getLineOfOffset(evt.getDot());
            int row = off - jTextArea1.getLineStartOffset(lin);
            jTextField1.setText(" " + (lin+1) + ": " + row);
        }
        catch(BadLocationException e){}
    }//GEN-LAST:event_jTextArea1CaretUpdate
    
    private void jTextArea2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyPressed
        text2_changed = true;
        whatdone = 0;
    }//GEN-LAST:event_jTextArea2KeyPressed
    
    private void jTextArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyPressed
        text1_changed = true;
        whatdone = 0;
    }//GEN-LAST:event_jTextArea1KeyPressed
    
    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        if(evt.getButton() == 3) {
            jTree1.setSelectionRow(jTree1.getRowForLocation(evt.getX(), evt.getY()));
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)(jTree1.getLastSelectedPathComponent());
            
            if(node == null) return;
            if(node.toString().length() > 0) if(node.toString().charAt(0) == '\r') return;
            
            if(node.getParent() == node.getRoot())
                jPopupMenu6.show((Component)jTree1, evt.getX(), evt.getY());
            else
                jPopupMenu7.show((Component)jTree1, evt.getX(), evt.getY());
            
            jTextField1.setText(" " + (jTree2.getRowForPath(jTree1.getSelectionPath())+1));
        }
        else if(evt.getButton() == 1) {
            TreePath tp = jTree1.getSelectionPath();
            jTextField1.setText(" " + (jTree1.getRowForPath(tp)+1));
        }
        
    }//GEN-LAST:event_jTree1MouseClicked
    
    private void jTree2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree2MouseClicked
        // Add your handling code here:
        //System.out.println(evt.getButton());
        if(evt.getButton() == 3) {
            jTree2.setSelectionRow(jTree2.getRowForLocation(evt.getX(), evt.getY()));
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)(jTree2.getLastSelectedPathComponent());
            
            if(node == null) return;
            if(node.toString().length() > 0) if(node.toString().charAt(0) == '\r') return;
            
            if(node.getParent() == node.getRoot())
                jPopupMenu1.show((Component)jTree2, evt.getX(), evt.getY());
            
            else if(node.getParent().toString().compareTo("\rTHEN")==0)
                jPopupMenu2.show((Component)jTree2, evt.getX(), evt.getY());
            
            else if(node.getParent().getParent().toString().compareTo("\rTHEN")==0) {
                if(node.getParent().toString().regionMatches(true, 0, "PRINT", 0, 5))
                    jPopupMenu3.show((Component)jTree2, evt.getX(), evt.getY());
                else
                    jPopupMenu4.show((Component)jTree2, evt.getX(), evt.getY());
            }
            else
                jPopupMenu5.show((Component)jTree2, evt.getX(), evt.getY());
            
            jTextField1.setText(" " + (jTree2.getRowForPath(jTree1.getSelectionPath())+1));
/*
                boolean edit = true;
                jTree2.setSelectionRow(jTree2.getRowForLocation(evt.getX(), evt.getY()));
                Object path = jTree2.getLastSelectedPathComponent();
                if(path != null)
                    if(path.toString().length() > 0)
                        if(path.toString().charAt(0) == '\r') edit = false;
                jTree2.setEditable(edit);
                TreePath tp = jTree2.getSelectionPath();
                System.out.println("1editing: "+jTree2.isEditing());
                if(edit) jTree2.startEditingAtPath(tp);
                System.out.println("2editing: "+jTree2.isEditing());
                jTextField1.setText(" " + (jTree2.getRowForPath(tp)+1));
 */
        }
        else if(evt.getButton() == 1) {
            TreePath tp = jTree2.getSelectionPath();
            jTextField1.setText(" " + (jTree2.getRowForPath(tp)+1));
        }
    }//GEN-LAST:event_jTree2MouseClicked
    
    private void jTree1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTree1FocusGained
        // Add your handling code here:
        if(text1_changed) {
            ArrayList al = null;
            text1_changed = false;
            String str = jTextArea1.getText().replace('\t', ' ');
            try {
                jTextArea3.setText("");
                if(parser == null) parser = new RuleParser(new StringReader(str));
                else parser.ReInit(new StringReader(str));
                al = parser.StartTree();
            } catch (Throwable exc) {
                System.err.println(exc.getMessage());
                jTextArea3.append(exc.getMessage() + "\r\n");
                //exc.printStackTrace();
            }
            if(al!=null) listenerObjects.BuildTree(al);
        }
    }//GEN-LAST:event_jTree1FocusGained
    
    private void jTree2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTree2FocusGained
        // Add your handling code here:
        if(text2_changed) {
            ArrayList al = null;
            text2_changed = false;
            String str = jTextArea2.getText().replace('\t', ' ');
            try {
                jTextArea3.setText("");
                if(parser == null) parser = new RuleParser(new StringReader(str));
                else parser.ReInit(new StringReader(str));
                al = parser.StartTree();
            } catch (Throwable exc) {
                System.err.println(exc.getMessage());
                jTextArea3.append(exc.getMessage() + "\r\n");
                //exc.printStackTrace();
            }
            if(al!=null) listenerRules.BuildTree(al);
        }
    }//GEN-LAST:event_jTree2FocusGained
    
    //====================================================================================================
    private void ExtractClassDefsFromRules(boolean augment) {
        ArrayList al = null;
        String str = jTextArea1.getText().replace('\t', ' ');
        if(augment) {
            try {
                if(parser == null) parser = new RuleParser(new StringReader(str));
                else {
                    parser.ReInit(new StringReader(str));
                    parser.ClearStaticVars();
                }
                al = parser.StartTree();
                
                int alsize = al.size();
                for(int i = 1; i < alsize; i++) {
                    ArrayList ai = (ArrayList)(al.get(i));
                    ai.remove(ai.size()-1);
                }
                if(alsize>1) al.remove(alsize-1);
                if(alsize>0) al.remove(0);
                
            } catch (Throwable exc) {
                System.err.println(exc.getMessage());
                exc.printStackTrace();
                al = null;
            }
        }
        
        str = jTextArea2.getText().replace('\t', ' ');
        try {
            if(parser == null) parser = new RuleParser(new StringReader(str));
            else {
                parser.ReInit(new StringReader(str));
                parser.ClearStaticVars();
            }
            al = parser.StartClasses(al);
        } catch (Throwable exc) {
            System.err.println(exc.getMessage());
            jTextArea3.append(exc.getMessage() + "\r\n");
            exc.printStackTrace();
            al = null;
        }
        if(al!=null) {
            ArrayList ai = new ArrayList();
            int alsize = al.size();
            ai.add("");
            al.add(ai);
            for(int i = 1; i < alsize; i++)
                ((ArrayList)(al.get(i))).add(ai);
            
            listenerObjects.BuildTree(al);
            listenerObjects.createOutputText();
        }
    }
    
        private void jMenuItemLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLoadActionPerformed
            try {
                int returnVal = chooser.showOpenDialog(jf);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getPath();
                    String name = chooser.getSelectedFile().getName();
                    String iame = "rule.idl";
                    String dir  = "";
                    int i = path.length()-name.length();
                    if(i > 0) dir = path.substring(0, i);
                    i = name.lastIndexOf('.');
                    if(i>0 && i<path.length()-1)
                        iame = name.substring(0, i) + ".idl";
                    else iame = name + ".idl";
                    rulfilename = name;
                    idlfilename = iame;
                    rulidlfolder = dir;
                    jLoadAction();
                }
            }
            catch (Throwable exc) {
                System.err.println(exc.getMessage());
                exc.printStackTrace();
            }
        }//GEN-LAST:event_jMenuItemLoadActionPerformed
        
        private void jLoadAction()
        {
            try {
                char[] c;
                int l;
                String s;
                File inputFile = new File(rulidlfolder + rulfilename);
                FileReader inputStream = new FileReader(inputFile);
                l = (int)(inputFile.length());
                c = new char[l];
                inputStream.read(c, 0, l);
                inputStream.close();
                s = new String(c);
                jTextArea2.setText(s);
                text2_changed = true;
                whatdone = 0;
                jTree2FocusGained(null);
                
                setTitle("MORE IDE - " + rulfilename + ", " +idlfilename);
                
                try{
                    inputFile = new File(rulidlfolder + idlfilename);
                    inputStream = new FileReader(inputFile);
                    l = (int)(inputFile.length());
                    c = new char[l];
                    inputStream.read(c, 0, l);
                    inputStream.close();
                    s = new String(c);
                    jTextArea1.setText(s);
                    text1_changed = true;
                    whatdone = 0;
                    jTree1FocusGained(null);
                }
                catch(IOException e) {
                    jTextArea3.append(e.getMessage() + "\r\n");
                    jTextArea3.append("Extracting class definitions from "+ rulfilename +"\r\n");
                    ExtractClassDefsFromRules(false);
                }
            }
            catch(IOException e) {
                jTextArea3.append(e.getMessage() + "\r\n");
                //					System.err.println(e);
                //					e.printStackTrace();
            }
        }

        public class RulFileFilter extends javax.swing.filechooser.FileFilter {
            public boolean accept(File f) {
                if(f == null) return false;
                if(f.isDirectory()) return true;
                String filename = f.getName();
                int i = filename.lastIndexOf('.');
                if(i>0 && i<filename.length()-1) {
                    String ext = filename.substring(i+1).toLowerCase();
                    if(ext != null) if(ext.compareTo("rul")==0) return true;
                }
                return false;
            }
            public String getDescription() {
                return "Rul files (*.rul)";
            }
        }
        
        private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
            jMenuItemRunActionPerformed(evt);
        }//GEN-LAST:event_jButtonRunActionPerformed
        
        public void EnableButtons(boolean enable) {
            jButtonRun.setEnabled(enable);
            jMenu1.setEnabled(enable);
            jMenu2.setEnabled(enable);
            jButtonNew.setEnabled(enable);
            jButtonOpen.setEnabled(enable);
            jButtonSave.setEnabled(enable);
        }
        
        class CompileThread extends Thread {
            Process p;
            JTextArea ta;
            int mode;
            
            public CompileThread(JTextArea t, int m) {
                ta = t;
                mode = m;
            }
            
            private boolean wywal() {
                String lino = "";
                String line = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                boolean iny = false;
                boolean iey = false;
                
                try{
                    while((line = ie.readLine()) != null) {
                        if(iey) ta.append(line + "\r\n");
                        else {
                            ta.append("\r\n\r\n" +line + "\r\n");
                            iey = true;
                        }
                    }
                    if(iey) return iey;
                    
                    while((line = in.readLine()) != null) {
                        if(iny) ta.append(line + "\r\n");
                        else {
                            ta.append("\r\n\r\n" +line + "\r\n");
                            iny = true;
                        }
                    }
                }
                
                catch (Throwable exc) {
                    ta.append(exc.getMessage() + "\r\n");
                    System.err.println(exc.getMessage());
                    exc.printStackTrace();
                    iey = true;
                }
                return iey;
            }
            
            
            public void run() {
                // mode =   1 - save idl, 2 - save rul, 4 - parse rul,
                //          8 - compile objects-idl, 16 - compile memory-idl, 32 - compile ruls, 64 - run expert
                
                
                try {
                    EnableButtons(false);
                    if((mode&1)!=0) //Saving IDL file
                    {
                        if((whatdone&1) == 0) {
                            
                            if(idlfilename == null || rulidlfolder == null) {
                                jMenuItemSaveAsActionPerformed(null);
                            }
                            else {
                                ta.append("Saving IDL file: " +idlfilename+ "... ");
                                File file = new File(rulidlfolder + idlfilename);
                                FileWriter writer = new FileWriter(file);
                                String outputstring = jTextArea1.getText();
                                writer.write(outputstring, 0, outputstring.length());
                                writer.close();
                                ta.append("DONE\r\n");
                            }
                        }
                        whatdone|=1;
                    }
                    if((mode&2)!=0) //Saving RUL file
                    {
                        if((whatdone&2) == 0) {
                            if(rulfilename == null || rulidlfolder == null) {
                                jMenuItemSaveAsActionPerformed(null);
                            }
                            else {
                                ta.append("Saving RUL file: " +rulfilename+ "... ");
                                File file = new File(rulidlfolder + rulfilename);
                                FileWriter writer = new FileWriter(file);
                                String outputstring = jTextArea2.getText();
                                writer.write(outputstring, 0, outputstring.length());
                                writer.close();
                                ta.append("DONE\r\n");
                            }
                        }
                        whatdone|=2;
                    }
                    if((mode&4)!=0) //Parse RUL file
                    {
                        if((whatdone&4) == 0) {
                            
                            ta.append("Converting rules into java sources... ");
                            if(parser == null) parser = new RuleParser(new StringReader(jTextArea2.getText()));
                            else {
                                parser.ReInit(new StringReader(jTextArea2.getText()));
                                parser.ClearStaticVars();
                            }
                            parser.modeLEX = false;
                            parser.modeMEA = false;
                            parser.modeBC = false; // Backward Chaining
                            parser.modeII = false; // Inexact Enferencing
                            parser.modePEM = true; // PErsistent Memory
                            parser.modeDebug = false;

                            if(jRadioButton4.isSelected()) parser.modeMEA = true;
                            else if(jRadioButton5.isSelected()) parser.modeBC = true;
                            else parser.modeLEX = true;
                            if(jCheckBox1.isSelected()) parser.modeII = true;
                            if(jRadioButton1.isSelected()) parser.modePEM = false;

                            parser.modeServerArgs = jTextField25.getText();
                            
                            parser.Start();
                            parser.CreateSourceFiles(rulidlfolder);
                            ta.append("DONE\r\n");
                        }
                        whatdone|=4;
                    }
                    
                    EnableButtons(true);
                    
                    if((mode&8)!=0) //Compile IDL - objects
                    {
                        if((whatdone&8) == 0) {
                            ta.append("Converting objects into java sources... ");
                            File ff = new File(rulidlfolder);
                            p = Runtime.getRuntime().exec(jTextField22.getText()+ " " + idlfilename, null, ff);
                            if(wywal()) return;
                            p.waitFor();
                            ta.append("DONE\r\n");
                            
                            ta.append("Compiling objects files... ");
                            p = Runtime.getRuntime().exec(jTextField2.getText()+ " ." + ff.separator + "ReteObjects" + ff.separator + "*.java", null, ff);
                            if(wywal()) return;
                            p.waitFor();
                            ta.append("DONE\r\n");
                        }
                        whatdone|=8;
                    }
                    
                    if((mode&16)!=0) //Compile IDL - memory
                    {
                        if((whatdone&16) == 0) {
                            
                            ta.append("Converting memory interface into java sources... ");
                            File ff = new File(rulidlfolder);
                            p = Runtime.getRuntime().exec(jTextField22.getText()+ " ." + ff.separator + "MemoryServer.idl", null, ff);
                            if(wywal()) return;
                            p.waitFor();
                            ta.append("DONE\r\n");
                            
                            ta.append("Compiling memory interface files... ");
                            p = Runtime.getRuntime().exec(jTextField2.getText()+ " ." + ff.separator + "MemoryServer" + ff.separator + "*.java", null, ff);
                            if(wywal()) return;
                            p.waitFor();
                            ta.append("DONE\r\n");
                            whatdone|=16;
                        }
                    }
                    
                    if((mode&32)!=0) //Compile RUL
                    {
                        if((whatdone&32) == 0) {
                            
                            ta.append("Compiling rete network files... ");
                            File ff = new File(rulidlfolder);
                            p = Runtime.getRuntime().exec(jTextField2.getText()+" Rete*.java Expert.java", null, ff);
                            if(wywal()) return;
                            p.waitFor();
                            ta.append("DONE\r\n");
                        }
                        whatdone|=32;
                    }
                    if((mode&64)!=0) //Run Expert
                    {
                        ta.append("Starting expert system... ");
                        File ff = new File(rulidlfolder);
                        p = Runtime.getRuntime().exec(jTextField21.getText()+ " Expert", null, ff);
                        if(wywal()) return;
                        p.waitFor();
                        ta.append("DONE\r\n");
                    }
                    
                    //						EnableButtons(true);
                    
                } catch (Throwable exc) {
                    
                    ta.append(exc.getMessage() + "\r\n");
                    System.err.println(exc.getMessage());
                    exc.printStackTrace();
                    EnableButtons(true);
                    
                }
            }
            class DynamicClassLoader extends ClassLoader {
                public synchronized Class loadClass(String filename, String classname) {
                    byte[] data = getClassData(filename);
                    if(data != null) {
                        Class c = defineClass(classname, data, 0, data.length);
                        resolveClass(c);
                        return c;
                    }
                    else return null;
                }
                byte[] getClassData(String filename) {
                    int l;
                    byte[] b;
                    try {
                        File inputFile = new File(filename);
                        l = (int)(inputFile.length());
                        b = new byte[l];
                        FileInputStream inputStream = new FileInputStream(inputFile);
                        inputStream.read(b, 0, l);
                        inputStream.close();
                        return b;
                    }
                    catch(IOException e) {
                        System.err.println(e);
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        };
        
        private void SaveOptions() {
            try {
                int sw = 0;
                
                FileOutputStream out = new FileOutputStream("Options.mem");
                ObjectOutputStream outstream = new ObjectOutputStream(out);
                
                //javac, java, idlj, orbd,
                outstream.writeObject(jTextField2.getText());
                outstream.writeObject(jTextField21.getText());
                outstream.writeObject(jTextField22.getText());
                outstream.writeObject(jTextField23.getText());
                
                //server name, port
                outstream.writeObject(jTextField24.getText());
                outstream.writeObject(jTextField25.getText());
                
                //transient/persistent
                if(jRadioButton1.isSelected()) sw = 1;
                else sw = 2;
                outstream.writeInt(sw);
                
                //lex/mea/back chaining
                if(jRadioButton4.isSelected()) sw = 2;
                else if(jRadioButton5.isSelected()) sw = 3;
                else sw = 1;
                outstream.writeInt(sw);
                
                //Inexact inferencing
                if(jCheckBox1.isSelected()) sw = 1;
                else sw = 0;
                outstream.writeInt(sw);
                
                outstream.writeObject(rulfilename);
                outstream.writeObject(idlfilename);
                outstream.writeObject(rulidlfolder);
                
                outstream.flush();
                outstream.close();
                out.close();
            }
            catch(Throwable e) {};
            
        }
        
        private void LoadOptions() {
            try {
                FileInputStream in = new FileInputStream("Options.mem");
                ObjectInputStream instream = new ObjectInputStream(in);
                
                //javac, java, idlj, orbd,
                jTextField2.setText((String)instream.readObject());
                jTextField21.setText((String)instream.readObject());
                jTextField22.setText((String)instream.readObject());
                jTextField23.setText((String)instream.readObject());
                
                //server name, port
                jTextField24.setText((String)instream.readObject());
                jTextField25.setText((String)instream.readObject());
                
                //transient/persistent
                switch(instream.readInt()) {
                    case 1: jRadioButton1.setSelected(true); jRadioButton1ActionPerformed(null); break;
                    default: jRadioButton2.setSelected(true); jRadioButton2ActionPerformed(null);
                }
                
                switch(instream.readInt()) {
                    case 2: jRadioButton4.setSelected(true); break;
                    case 3: jRadioButton5.setSelected(true); break;
                    default: jRadioButton3.setSelected(true);
                }
                
                switch(instream.readInt()) {
                    case 1: jCheckBox1.setSelected(true); break;
                    default: jCheckBox1.setSelected(false);
                }
                rulfilename = (String)instream.readObject();
                idlfilename = (String)instream.readObject();
                rulidlfolder = (String)instream.readObject();
                
                instream.close();
                in.close();

                jLoadAction();
            }
            catch(Throwable e) {};
        }
        
        
        
        
        private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
            SaveOptions();
            System.exit(0);
        }//GEN-LAST:event_jMenuItemExitActionPerformed
        
        /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        SaveOptions();
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //System.out.println(System.getProperty("user.dir"));
        jf = new RulExec();
        jf.setExtendedState(jf.MAXIMIZED_BOTH);
        jf.show();
    }
    
    private boolean text1_changed = false;
    private boolean text2_changed = false;
    
    public int whatdone = 0;
    private RuleParser parser = null;
    //    private DefaultMutableTreeNode menunode = null;
    private static JFrame jf = null;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButtonMemory;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonOpen;
    private javax.swing.JButton jButtonRun;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JFrame jFrameImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1Clear;
    private javax.swing.JMenuItem jMenuItem1Edit;
    private javax.swing.JMenuItem jMenuItem1Ex;
    private javax.swing.JMenuItem jMenuItem2Clear;
    private javax.swing.JMenuItem jMenuItem2Edit;
    private javax.swing.JMenuItem jMenuItem2Ex;
    private javax.swing.JMenuItem jMenuItem3Clear;
    private javax.swing.JMenuItem jMenuItem3Edit;
    private javax.swing.JMenuItem jMenuItem3Ex;
    private javax.swing.JMenuItem jMenuItem4Clear;
    private javax.swing.JMenuItem jMenuItem4Edit;
    private javax.swing.JMenuItem jMenuItem4Ex;
    private javax.swing.JMenuItem jMenuItem5Clear;
    private javax.swing.JMenuItem jMenuItem5Edit;
    private javax.swing.JMenuItem jMenuItem5Ex;
    private javax.swing.JMenuItem jMenuItem6Clear;
    private javax.swing.JMenuItem jMenuItem6Edit;
    private javax.swing.JMenuItem jMenuItem6Ex;
    private javax.swing.JMenuItem jMenuItem7Clear;
    private javax.swing.JMenuItem jMenuItem7Edit;
    private javax.swing.JMenuItem jMenuItem7Ex;
    private javax.swing.JMenuItem jMenuItemAND;
    private javax.swing.JMenuItem jMenuItemAugClass;
    private javax.swing.JMenuItem jMenuItemBind;
    private javax.swing.JMenuItem jMenuItemCLASS;
    private javax.swing.JMenuItem jMenuItemCONDITION;
    private javax.swing.JMenuItem jMenuItemClassName;
    private javax.swing.JMenuItem jMenuItemCompile;
    private javax.swing.JMenuItem jMenuItemDefClass;
    private javax.swing.JMenuItem jMenuItemDisplay;
    private javax.swing.JMenuItem jMenuItemDouble;
    private javax.swing.JMenuItem jMenuItemEModify;
    private javax.swing.JMenuItem jMenuItemEPrint;
    private javax.swing.JMenuItem jMenuItemExecute;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemIModify;
    private javax.swing.JMenuItem jMenuItemIPrint;
    private javax.swing.JMenuItem jMenuItemImage;
    private javax.swing.JMenuItem jMenuItemIneger;
    private javax.swing.JMenuItem jMenuItemLoad;
    private javax.swing.JMenuItem jMenuItemMake;
    private javax.swing.JMenuItem jMenuItemModify;
    private javax.swing.JMenuItem jMenuItemNModify;
    private javax.swing.JMenuItem jMenuItemNPrint;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOR;
    private javax.swing.JMenuItem jMenuItemParse;
    private javax.swing.JMenuItem jMenuItemPrint;
    private javax.swing.JMenuItem jMenuItemRead;
    private javax.swing.JMenuItem jMenuItemRemove;
    private javax.swing.JMenuItem jMenuItemRule;
    private javax.swing.JMenuItem jMenuItemRun;
    private javax.swing.JMenuItem jMenuItemSModify;
    private javax.swing.JMenuItem jMenuItemSPrint;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JMenuItem jMenuItemServer;
    private javax.swing.JMenuItem jMenuItemString;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JPopupMenu jPopupMenu5;
    private javax.swing.JPopupMenu jPopupMenu6;
    private javax.swing.JPopupMenu jPopupMenu7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTree jTree2;
    // End of variables declaration//GEN-END:variables
}
