import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class StudentRegistrationSystem extends JFrame implements ActionListener {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";

    JTextField txtName, txtRoll, txtMobile, txtEmail;
    JTextArea txtAddress;
    JComboBox<String> cbBranch, cbSemester, cbDay, cbMonth, cbYear;
    JRadioButton rbMale, rbFemale;
    JCheckBox chkTerms;
    JButton btnSubmit, btnPreview, btnReset, btnExit;
    JLabel status;

    public StudentRegistrationSystem() {
        setTitle("Student Registration System");
        setSize(700,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(10,10));
        main.setBorder(new EmptyBorder(15,15,15,15));
        main.setBackground(new Color(240,248,255));

        JLabel title=new JLabel("STUDENT REGISTRATION SYSTEM",SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,24));
        title.setForeground(new Color(25,70,160));
        main.add(title,BorderLayout.NORTH);

        JPanel form=new JPanel(new GridBagLayout());
        form.setBorder(new TitledBorder("Student Details"));
        form.setBackground(Color.white);
        GridBagConstraints g=new GridBagConstraints();
        g.insets=new Insets(8,8,8,8);
        g.anchor=GridBagConstraints.WEST;
        g.fill=GridBagConstraints.HORIZONTAL;

        txtName=new JTextField(20);
        txtRoll=new JTextField();
        txtMobile=new JTextField();
        txtEmail=new JTextField();
        txtAddress=new JTextArea(4,20);
        txtAddress.setLineWrap(true);

        cbBranch=new JComboBox<>(new String[]{"Select","CSE","IT","AI & DS","ECE","Mechanical","Civil"});
        cbSemester=new JComboBox<>(new String[]{"Select","1","2","3","4","5","6","7","8"});
        cbDay=new JComboBox<>();
        for(int i=1;i<=31;i++) cbDay.addItem(""+i);
        cbMonth=new JComboBox<>(new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});
        cbYear=new JComboBox<>();
        for(int i=2026;i>=1990;i--) cbYear.addItem(""+i);

        rbMale=new JRadioButton("Male");
        rbFemale=new JRadioButton("Female");
        rbMale.setBackground(Color.white);
        rbFemale.setBackground(Color.white);
        ButtonGroup bg=new ButtonGroup();
        bg.add(rbMale); bg.add(rbFemale);

        chkTerms=new JCheckBox("I accept Terms and Conditions");
        chkTerms.setBackground(Color.white);

        btnSubmit=new JButton("Submit");
        btnPreview=new JButton("Preview");
        btnReset=new JButton("Reset");
        btnExit=new JButton("Exit");

        JButton[] bs={btnSubmit,btnPreview,btnReset,btnExit};
        for(JButton b:bs){ b.addActionListener(this); }

        int y=0;
        addRow(form,g,y++,"Name",txtName);
        addRow(form,g,y++,"Roll No",txtRoll);
        addRow(form,g,y++,"Branch",cbBranch);
        addRow(form,g,y++,"Semester",cbSemester);

        g.gridx=0;g.gridy=y;form.add(new JLabel("Gender"),g);
        JPanel gp=new JPanel();gp.setBackground(Color.white);gp.add(rbMale);gp.add(rbFemale);
        g.gridx=1;form.add(gp,g); y++;

        addRow(form,g,y++,"Mobile",txtMobile);
        addRow(form,g,y++,"Email",txtEmail);

        g.gridx=0;g.gridy=y;form.add(new JLabel("Date of Birth"),g);
        JPanel dp=new JPanel();dp.setBackground(Color.white);dp.add(cbDay);dp.add(cbMonth);dp.add(cbYear);
        g.gridx=1;form.add(dp,g); y++;

        g.gridx=0;g.gridy=y;form.add(new JLabel("Address"),g);
        g.gridx=1;form.add(new JScrollPane(txtAddress),g); y++;

        g.gridx=0;g.gridy=y;g.gridwidth=2;form.add(chkTerms,g); y++;

        JPanel bp=new JPanel();bp.setBackground(Color.white);
        bp.add(btnSubmit);bp.add(btnPreview);bp.add(btnReset);bp.add(btnExit);
        g.gridy=y;form.add(bp,g);

        main.add(form,BorderLayout.CENTER);

        status=new JLabel(" Ready   "+new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a").format(new Date()));
        status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        main.add(status,BorderLayout.SOUTH);

        setJMenuBar(menu());
        add(main);
        JOptionPane.showMessageDialog(this,"Welcome to Student Registration System");
    }

    JMenuBar menu(){
        JMenuBar mb=new JMenuBar();
        JMenu file=new JMenu("File");
        JMenu help=new JMenu("Help");
        JMenuItem reset=new JMenuItem("Reset");
        JMenuItem exit=new JMenuItem("Exit");
        JMenuItem about=new JMenuItem("About");
        reset.addActionListener(e->reset());
        exit.addActionListener(e->dispose());
        about.addActionListener(e->JOptionPane.showMessageDialog(this,"Student Registration System\nVersion 1.0\nDeveloped using Java Swing"));
        file.add(reset);file.add(exit);
        help.add(about);
        mb.add(file);mb.add(help);
        return mb;
    }

    void addRow(JPanel p,GridBagConstraints g,int y,String l,Component c){
        g.gridwidth=1;
        g.gridx=0;g.gridy=y;p.add(new JLabel(l),g);
        g.gridx=1;p.add(c,g);
    }

    boolean validateData(){
        if(txtName.getText().trim().isEmpty()||!txtName.getText().matches("[A-Za-z ]+")){msg("Enter valid name");return false;}
        if(!txtRoll.getText().matches("\\d+")){msg("Roll number must contain digits");return false;}
        if(cbBranch.getSelectedIndex()==0){msg("Select branch");return false;}
        if(cbSemester.getSelectedIndex()==0){msg("Select semester");return false;}
        if(!rbMale.isSelected()&&!rbFemale.isSelected()){msg("Select gender");return false;}
        if(!txtMobile.getText().matches("\\d{10}")){msg("Enter valid 10-digit mobile");return false;}
        if(!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")){msg("Enter valid email");return false;}
        if(txtAddress.getText().trim().isEmpty()){msg("Enter address");return false;}
        if(!chkTerms.isSelected()){msg("Accept terms");return false;}
        return true;
    }

    void msg(String s){JOptionPane.showMessageDialog(this,s);}

    boolean saveToDatabase(){
        String sql = "INSERT INTO students (name,roll_no,branch,semester,gender,mobile,email,dob,address) VALUES (?,?,?,?,?,?,?,?,?)";
        String dob = cbDay.getSelectedItem()+"-"+cbMonth.getSelectedItem()+"-"+cbYear.getSelectedItem();
        try(Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,txtName.getText().trim());
            ps.setString(2,txtRoll.getText().trim());
            ps.setString(3,cbBranch.getSelectedItem().toString());
            ps.setString(4,cbSemester.getSelectedItem().toString());
            ps.setString(5,rbMale.isSelected()?"Male":"Female");
            ps.setString(6,txtMobile.getText().trim());
            ps.setString(7,txtEmail.getText().trim());
            ps.setString(8,dob);
            ps.setString(9,txtAddress.getText().trim());
            ps.executeUpdate();
            return true;
        }catch(SQLException ex){
            msg("Database error: "+ex.getMessage());
            return false;
        }
    }

    String details(){
        return "Name : "+txtName.getText()+
                "\nRoll : "+txtRoll.getText()+
                "\nBranch : "+cbBranch.getSelectedItem()+
                "\nSemester : "+cbSemester.getSelectedItem()+
                "\nGender : "+(rbMale.isSelected()?"Male":"Female")+
                "\nMobile : "+txtMobile.getText()+
                "\nEmail : "+txtEmail.getText()+
                "\nDOB : "+cbDay.getSelectedItem()+"-"+cbMonth.getSelectedItem()+"-"+cbYear.getSelectedItem()+
                "\nAddress : "+txtAddress.getText();
    }

    void reset(){
        txtName.setText("");txtRoll.setText("");txtMobile.setText("");txtEmail.setText("");txtAddress.setText("");
        cbBranch.setSelectedIndex(0);cbSemester.setSelectedIndex(0);
        rbMale.setSelected(false);rbFemale.setSelected(false);chkTerms.setSelected(false);
        status.setText(" Form Reset");
    }

    public void actionPerformed(ActionEvent e){
        Object s=e.getSource();
        if(s==btnPreview){
            JOptionPane.showMessageDialog(this,details(),"Preview",JOptionPane.INFORMATION_MESSAGE);
        }else if(s==btnSubmit){
            if(validateData()){
                if(saveToDatabase()){
                    JOptionPane.showMessageDialog(this,"Registration Successful!\n\n"+details()+"\n\nSaved to database 'student'");
                    status.setText(" Registration Successful - Saved to DB");
                    reset();
                }
            }
        }else if(s==btnReset){
            if(JOptionPane.showConfirmDialog(this,"Reset form?","Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                reset();
        }else if(s==btnExit){
            if(JOptionPane.showConfirmDialog(this,"Exit application?","Exit",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                System.exit(0);
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new StudentRegistrationSystem().setVisible(true));
    }
}
