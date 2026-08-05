import javax.swing.*;

public class CalculatorView extends JFrame {

    JTextField txtNum1, txtNum2, txtResult;
    JButton btnAdd, btnSub, btnMul, btnDiv;

    public CalculatorView() {

        setTitle("MVC Calculator");
        setSize(400, 300);
        setLayout(null);

        JLabel l1 = new JLabel("First Number");
        l1.setBounds(30, 30, 100, 25);
        add(l1);

        txtNum1 = new JTextField();
        txtNum1.setBounds(150, 30, 150, 25);
        add(txtNum1);

        JLabel l2 = new JLabel("Second Number");
        l2.setBounds(30, 70, 120, 25);
        add(l2);

        txtNum2 = new JTextField();
        txtNum2.setBounds(150, 70, 150, 25);
        add(txtNum2);

        btnAdd = new JButton("+");
        btnAdd.setBounds(30, 120, 60, 30);
        add(btnAdd);

        btnSub = new JButton("-");
        btnSub.setBounds(100, 120, 60, 30);
        add(btnSub);

        btnMul = new JButton("*");
        btnMul.setBounds(170, 120, 60, 30);
        add(btnMul);

        btnDiv = new JButton("/");
        btnDiv.setBounds(240, 120, 60, 30);
        add(btnDiv);

        JLabel l3 = new JLabel("Result");
        l3.setBounds(30, 180, 100, 25);
        add(l3);

        txtResult = new JTextField();
        txtResult.setBounds(150, 180, 150, 25);
        txtResult.setEditable(false);
        add(txtResult);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}