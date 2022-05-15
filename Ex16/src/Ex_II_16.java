/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author PC
 */
package homework4;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

class Subscriber { // in Polish: Abonent

    public final static String[] LABELS = {
        "Name",
        "Surname",
        "Phone number",
        "Minutes for free",
        "Subscription payment",
        "Price for minute inside network",
        "Price for minute outside network"
    };
    private HashMap<String,String> subscriberData = new HashMap<String,String>();

    public Subscriber(String[] dataArray) {
        for(int i=0;i<LABELS.length;i++)
            subscriberData.put(LABELS[i],dataArray[i]);
    }

    // method returns a value of one of a subscriber field
    public String get(int fieldIdx) {
        return subscriberData.get(LABELS[fieldIdx]);
    }

    // a method updates a value of one of a subscriber field
    public void set(int fieldIdx,String newValue) {
        subscriberData.put(LABELS[fieldIdx], newValue);
    }

    public String toString() {
        return get(0) + " " + get(1);
    }    
}

// --------------------------------------------------------------------

// panel for editing subscriber data
// we place it into window of subscriber (object of SubscriberWindow class)
class SubscriberPanel extends JPanel {
 
 // an array of textfields for edit subscriber data
    private JTextField[] textFields = new JTextField[Subscriber.LABELS.length];

    public SubscriberPanel() {
        // layout is a grid (new row for each subscriber feature and two columns) with 5px padding
        setLayout(new GridLayout(Subscriber.LABELS.length,2,5,5));

        // creates and adds textfields to the panel
        for(int i=0;i<Subscriber.LABELS.length;i++) {
            add(new JLabel(Subscriber.LABELS[i]));
            textFields[i] = new JTextField();
            add(textFields[i]);
        }

        // defines a border
        TitledBorder border = new TitledBorder("Subscriber data");
        border.setTitleColor(Color.RED);
        setBorder(border);                
    }
          
    public Subscriber getEdited() {
        String[] data = new String[textFields.length];
        for(int i=0; i<data.length; i++) data[i] = textFields[i].getText();
        return new Subscriber(data);
    }

    // fills textfields using data of giving as parameter subscriber
    public void setEdited(Subscriber subscriber) {
        if(subscriber == null) return;
        for(int i=0; i<textFields.length; i++) textFields[i].setText(subscriber.get(i));
    }
    
}
// --------------------------------------------------------------------

// panel which includes a list of subscribers component
class SubscriberListPanel extends JPanel
{
    // subscribers data container
    private ArrayList<Subscriber> subscribers = new ArrayList<Subscriber> ();
    // JList component
    private JList subscribersJList = new JList();

    public SubscriberListPanel() {

        setLayout(new BorderLayout());

        JPanel p = new JPanel(new GridLayout(1,1,5,5));

        // here add to our JList component scrolling (use JScrollPane object) 
        // and border (see picture in exercise II.16)
        // and place it into panel p defined above
        
        //subscribersJList.setBorder(new TitledBorder(new LineBorder(Color.red), "Subscribers list"));
        subscribersJList.setBorder(new TitledBorder("<html><font color=red>Subscribers list"));
        // below adds scroll panel rather than adding JList directly
        p.add(new JScrollPane(subscribersJList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)); 
        add(p);

     // data for testing
        subscribers.add(new Subscriber(new String[]{"Jan", "Kowalski","111","222","333","444","555"}));
        subscribers.add(new Subscriber(new String[]{"Agata", "Kowalska","1111","2222","3333","4444","5555"}));
        subscribersJList.setListData(subscribers.toArray());
    }        
 public void ExportSubscribersToFile(){
        try {                
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream("data.txt")));
               if(subscribers != null && subscribers.size()>0)
                 
               for (int i = 0; i < subscribers.size(); i++) {
                   Subscriber s = subscribers.get(i);
                   for (int j = 0; j < Subscriber.LABELS.length; j++) {
                       out.write(s.get(j)+';');
                   }
                   out.newLine();
                   
                   
                 
            }
                out.close();
        } catch (IOException ex) { System.out.println(ex); }
        
 }
 public void loadSubscribersFromFile(){
       File file = new File("data.txt");
                    if(file.exists() && file.isFile()) {          
                try (BufferedReader in = new BufferedReader(new FileReader(file))) {
                    while(true) {
                        String s = in.readLine();
                        if(s == null) break;
                        String data[] = s.split(";");
                       
                        var subscriber = new Subscriber(data);
                        subscribers.add(subscriber);
                         subscribersJList.setListData(subscribers.toArray());
                    }
                } catch(IOException ex) { System.out.println(ex); }
 }
 }
 
    // refreshing after edit
    public void save(Subscriber s) {
        // TO COMPLETE
        /*
         * Depending on selected item
         * update or add item (subscriber) to container
         * refresh view of the JList.
         * A string array provides a subscriber data.
         * (use right methods of ArrayList and Subscriber)
         * 
        */
        int i = getSubscriberIndex();
        if(i >= 0) { 
            subscribers.set(i, s);            
        }
        else {
            subscribers.add(s);            
            i = subscribers.size()-1;            
        }
        subscribersJList.setListData(subscribers.toArray()); 
        subscribersJList.setSelectedIndex(i);        
    }

    public void remove() {
      // TO COMPLETE
        /*     
         * Removes selected element from subscribers container and refreshing JList view
        */
        int i = getSubscriberIndex();
        if(i<0 || i>=subscribers.size()) return;
        subscribers.remove(i);
        subscribersJList.setListData(subscribers.toArray());
        
        if(i>0) subscribersJList.setSelectedIndex(i-1);
    }       
    
    // returns an index of subscriber selected from the list
    public int getSubscriberIndex() { return subscribersJList.getSelectedIndex(); }
    
    public Subscriber getSelectedSubscriber() { 
        int i = subscribersJList.getSelectedIndex();
        if(i>=0) return subscribers.get(subscribersJList.getSelectedIndex()); 
        return null;
    }
    
    public void clearSelection() { subscribersJList.clearSelection(); }
}
// --------------------------------------------------------------------

// a window in which SubscriberPanel is shown
class SubscriberWindow extends JDialog  implements ActionListener { 

    private MainWindow mainWindow;
    private SubscriberPanel subscriberPanel = new SubscriberPanel();

    public SubscriberWindow(MainWindow c) {
        
        setSize(400, 300);
        setLocation(50, 100);        
        setModalityType(DEFAULT_MODALITY_TYPE);        
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
               
        /* TO COMPLETE:
         * to this window insert subscriber panel and a button "Accept"
           use a vertical box
        */
        setTitle("Edit selected subscriber");
        mainWindow = c;
        Box box = Box.createVerticalBox();
        //box.add(mainWindow.getSubscriberPanel());
        box.add(subscriberPanel);
        box.add(Box.createVerticalStrut(10));
        
        JButton accept = new JButton("Accept");
        accept.addActionListener(this);
        box.add(accept);                
        add(box);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
      // TO COMPLETE:
        /*
         * write a code executed when a "Accept" button has been pressed
         *
        */        
        SaveChanges();
        dispose();
    }    
    
    public void Edit(Subscriber s) {                
        if(s == null) return;
        subscriberPanel.setEdited(s);        
        setVisible(true);
    }
    
    // writes values from panel to the selection list of subscribers
    public void SaveChanges() {        
        SubscriberListPanel listPanel = mainWindow.getSubscriberListPanel();
        listPanel.save(subscriberPanel.getEdited());
    }
}

class MainWindow extends JFrame implements ActionListener {
    
    private SubscriberWindow subscriberWindow = new SubscriberWindow(this);
    private SubscriberListPanel subscriberListPanel = new SubscriberListPanel();
    
    private JMenuItem mExit, mNew, mEdit, mRemove, mSave, mLoad;

    public MainWindow()
    {
        super("Subscribers list");

        JPanel mainPanel = new JPanel(new GridLayout(1,1,5,5));
        mainPanel.add(subscriberListPanel);
        add(mainPanel);

      // TO COMPLETE        
        // construct a menu according to ex. II.16
        JMenuBar mb = new JMenuBar();
        JMenu mProgram = new JMenu("Program");
        JMenu mOperations = new JMenu("Operations");
         JMenu mData = new JMenu("Data");//„Save subscribers” and „Load subscribers” to this submenu. 
        
        mExit = new JMenuItem("Exit");        
        mExit.addActionListener(this);
        mProgram.add(mExit);
        mNew = new JMenuItem("New subscriber"); 
        mNew.addActionListener(this);
        mEdit = new JMenuItem("Edit");
        mEdit.addActionListener(this);
        mRemove = new JMenuItem("Remove");
        mRemove.addActionListener(this);
       mSave = new JMenuItem("Save subscribers");
       mLoad = new JMenuItem("Load subscribers");
        mSave.addActionListener(this);
        mLoad.addActionListener(this);
        mOperations.add(mNew);
        mOperations.add(mEdit);
        mOperations.add(mRemove);
        mData.add(mSave);
        mData.add(mLoad);
        mb.add(mProgram);        
        mb.add(mOperations);
        mb.add(mData);
        setJMenuBar(mb);
        
        //KEYEVENTS 
          subscriberWindow.addKeyListener(new MKeyListener());
          Action saveAction = new AbstractAction("Save") {
 
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Saving...");
    }
};
          
 KeyStroke keyStrokeToLoad
    = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
mLoad.setAccelerator(keyStrokeToLoad);

 KeyStroke keyStrokeToSave
    = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);
mSave.setAccelerator(keyStrokeToSave);


//DOESNT WORK
saveAction.putValue(Action.ACCELERATOR_KEY,
        KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
 
mData.setAction(saveAction);

        this.addWindowListener(new WindowAdapter(){
             @Override
                public void windowClosing(WindowEvent e){
                   subscriberListPanel.ExportSubscribersToFile();
                }
                @Override
                public void windowOpened(WindowEvent e) {
                   subscriberListPanel.loadSubscribersFromFile();
                }
            });
        setSize(400,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e)
    {
        // TO COMPLETE:
        // the menu handling
        Object menuSelection = e.getSource();
        if(menuSelection == mExit) System.exit(0);
        else if(menuSelection == mRemove) subscriberListPanel.remove();
            else {                
                if(menuSelection == mEdit) {                    
                    if(subscriberListPanel.getSubscriberIndex() != -1)
                        subscriberWindow.Edit(subscriberListPanel.getSelectedSubscriber());
                }
                if(menuSelection == mNew) {
                    subscriberListPanel.clearSelection();
                    subscriberWindow.Edit(new Subscriber(new String[7]));                                        
                }
            }
               if (menuSelection == mSave){//First of these commands should write subscribers from container to file
                   System.out.println("Saving");
                   subscriberListPanel.ExportSubscribersToFile();
        
                    }
               if(menuSelection == mLoad){//second one should read them from the file to the container
                   System.out.println("Loading");
                    subscriberListPanel.loadSubscribersFromFile();
           // selectedProductIndex = 0;
                    }
                   
               
                  
                   
             
    }
    
    public SubscriberListPanel getSubscriberListPanel() {
        return subscriberListPanel;
    }
     
    
    public static void main(String[] args)
    {
        new MainWindow();
    }        
    
}

class MKeyListener extends KeyAdapter {
 
    @Override
    public void keyPressed(KeyEvent event) {
 
  char ch = event.getKeyChar();
  
  
                  if ((ch == KeyEvent.VK_C) && ((event.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                    System.out.println("woot!");
                }
 
  if (ch == 'a' ||ch == 'b'||ch == 'c' ) {
 
System.out.println(event.getKeyChar());
 
  }
 
  if (event.getKeyCode() == KeyEvent.VK_HOME) {
 
System.out.println("Key codes: " + event.getKeyCode());
 
  }
    }
    
       
}