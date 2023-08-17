import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;
import java.io.*;



public class Client implements ActionListener{

    JTextField text1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dos;

    static JFrame f=new JFrame();

    Client(){

    // Header section of the layout
    f.setLayout(null);
    JPanel p1= new JPanel();
    p1.setBackground(new Color(7, 94, 84));  
    p1.setBounds(0, 0, 450, 70);  // to set the color to that given width and height
    p1.setLayout(null);
    f.add(p1);


    // Adding of arrow image
    ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
    Image i2= i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
    ImageIcon i3= new ImageIcon(i2);
    JLabel back = new JLabel( i3);
    back.setBounds(5,20,25,25);  // (left distance, upper distance, image width, image height)
    p1.add(back);


    back.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent ae){
            System.exit(0);
        }
    });

    
    // Adding of profile image
    ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
    Image i5= i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
    ImageIcon i6= new ImageIcon(i5);
    JLabel profile = new JLabel( i6);
    profile.setBounds(40,10,50,50);  // (left distance, upper distance, image width, image height)
    p1.add(profile);


    // Adding of video call image
    ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
    Image i8= i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
    ImageIcon i9= new ImageIcon(i8);
    JLabel video = new JLabel( i9);
    video.setBounds(300,20,30,30);  // (left distance, upper distance, image width, image height)
    p1.add(video);


    // Adding of audio call image
    ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
    Image i11= i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
    ImageIcon i12= new ImageIcon(i11);
    JLabel phone = new JLabel( i12);
    phone.setBounds(360,20,30,30);  // (left distance, upper distance, image width, image height)
    p1.add(phone);


    // Adding of three dot image
    ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
    Image i14= i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
    ImageIcon i15= new ImageIcon(i14);
    JLabel Threeicon = new JLabel( i15);
    Threeicon.setBounds(410,20,10,25);  // (left distance, upper distance, image width, image height)
    p1.add(Threeicon);


    // Labeling of user-name in the header
    JLabel name = new JLabel("Shivansh");
    name.setBounds(110,20, 100, 15);
    name.setForeground(Color.white);
    name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
    p1.add(name);


    // Labeling of active status in the header
    JLabel status = new JLabel("Active now");
    status.setBounds(110,45, 100, 10);
    status.setForeground(Color.white);
    status.setFont(new Font("SAN_SERIF",Font.BOLD,12));
    p1.add(status);


    // Message section layout
    a1 = new JPanel();
    a1.setBounds(5, 75, 445, 570);
    f.add(a1);


    // Adding textfield in the bottom
    text1 = new JTextField();
    text1.setBounds(5,655,310,40);
    text1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
    f.add(text1);

    
    // Adding Send button in the bottom
    JButton send = new JButton("Send");
    send.setBounds(320, 655, 123, 40);
    send.setBackground(new Color(7, 94, 84));
    send.setForeground(Color.white);
    send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
    send.addActionListener(this);
    f.add(send);


    f.setUndecorated(true);  // to remove the frame that is under setTitle
    f.setSize(450, 700);  
    f.setLocation(800, 50); // In this case it is used to set the frame to 200 from x axis and 50 from y axis
    //setLocationRelativeTo(null);  // to keep the display to the center 
    //setDefaultCloseOperation(EXIT_ON_CLOSE);
    f.getContentPane().setBackground(Color.white); //to take the whole layout and color change to white








    f.setVisible(true);
    }

//For priting the output to the screen
    public void actionPerformed(ActionEvent ae){

        try{
         String out =  text1.getText(); 
          
         JPanel p2= formatLabel(out);


         a1.setLayout(new BorderLayout());

         JPanel right = new JPanel(new BorderLayout());
         right.add(p2, BorderLayout.LINE_END);
         vertical.add(right);
         vertical.add(Box.createVerticalStrut(15));
         a1.add(vertical, BorderLayout.PAGE_START);

         dos.writeUTF(out);// to send message
        text1.setText("");
        f.repaint();
        f.invalidate();
        f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    //For priting the output to the screen
    public static JPanel formatLabel(String out){
         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

         JLabel output = new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
         output.setFont(new Font("Tahoma", Font.PLAIN, 16));
         output.setBackground(new Color(37, 211, 102));
         output.setOpaque(true);// to show the color that is set to output
         output.setBorder(new EmptyBorder(15, 15,15,50));
         panel.add(output);

         //to get the time which is set with the message
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);




         return panel;
    }

    public static void main(String[] args) {
        new Client();

        try{
           Socket s= new Socket("120.0.0.1", 5040);
           DataInputStream din = new DataInputStream(s.getInputStream());
           dos = new DataOutputStream(s.getOutputStream());

           while(true){
            a1.setLayout(new BorderLayout());
            String msg = din.readUTF();// to receive/read message
            JPanel panel = formatLabel(msg);

            JPanel left = new JPanel(new BorderLayout());
            left.add(panel, BorderLayout.LINE_START);
            vertical.add(left);

            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            f.validate();
        }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}