package calculator;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.*;

public class Calculator extends JFrame {
    Stack<Character> inputStack = new Stack<>();
    boolean negation = false;
    public Calculator() {
        super("Calculator");
        GridBagConstraints c = new GridBagConstraints();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(260,400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        initComponents(c);

        setVisible(true);
    }

    private void initComponents(GridBagConstraints c) {
        int gap = 5;

        JLabel result = new JLabel();
        result.setName("ResultLabel");
        result.setFont(result.getFont().deriveFont(22f));
        result.setHorizontalAlignment(SwingConstants.RIGHT);
        c.insets = new Insets(0,0,30,5);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth =  GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.BOTH;
        add(result, c);

        JLabel equation = new JLabel();
        equation.setName("EquationLabel");
        equation.setForeground(Color.BLUE);
        equation.setHorizontalAlignment(SwingConstants.RIGHT);
        c.insets = new Insets(0,0,30,5);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth =  GridBagConstraints.REMAINDER;
        add(equation, c);

        JButton parentheses = new JButton("()" );
        c.insets = new Insets(gap,0,0,0);
        parentheses.setName("Parentheses");
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 10; // Set extra padding to 50 pixels on each side
        c.ipady = 10; // Set extra padding to 20 pixels on each side
        add(parentheses, c);
        parentheses.addActionListener(e -> inputHandler(equation, inputParentheses(equation, inputStack)));
        parentheses.setBackground(Color.GRAY.brighter());

        JButton ce = new JButton("CE" );
        c.insets = new Insets(gap,gap,0,0);
        ce.setName("CE");
        c.gridx = 1;
        c.gridy = 2;
        add(ce, c);
        ce.addActionListener(e -> {
            equation.setText("");
            clearState();
        });
        ce.setBackground(Color.GRAY.brighter());

        JButton clear = new JButton("C" );
        clear.setName("Clear");
        c.insets = new Insets(gap,gap,0,0);  //top padding
        c.gridx = 2;
        c.gridy = 2;
        add(clear, c);
        clear.addActionListener(e -> {
            equation.setText("");
            clearState();
        });
        clear.setBackground(Color.GRAY.brighter());

        JButton delete = new JButton("Del" );
        c.insets = new Insets(gap,gap,0,gap);
        delete.setName("Delete");
        c.gridx = 3;
        c.gridy = 2;
        add(delete, c);
        delete.addActionListener(e -> {
            String str = equation.getText();
            equation.setText(str.substring(0, str.length()-1));
            if (getLastChar(str) == '(' || getLastChar(str) == ')') {
                if(!inputStack.isEmpty()) inputStack.pop();
            }
        });
        delete.setBackground(Color.GRAY.brighter());

        JButton pwr2 = new JButton("X\u00B2" );
        c.insets = new Insets(gap,0,0,0);
        pwr2.setName("PowerTwo");
        c.gridx = 0;
        c.gridy = 3;
        add(pwr2, c);
        pwr2.addActionListener(e -> inputHandler(equation, "^(2)"));
        pwr2.setBackground(Color.GRAY.brighter());

        JButton pwrY = new JButton("<html>X<sup>y</sup></html>" );
        pwrY.setName("PowerY");
        c.insets = new Insets(gap,gap,0,0);
        c.gridx = 1;
        c.gridy = 3;
        add(pwrY, c);
        pwrY.addActionListener(e -> inputHandler(equation, "^"));
        pwrY.setBackground(Color.GRAY.brighter());

        JButton sqrt = new JButton("\u221A" );
        sqrt.setName("SquareRoot");
        c.gridx = 2;
        c.gridy = 3;
        add(sqrt, c);
        sqrt.addActionListener(e -> {
            inputHandler(equation, "\u221A(");
            inputStack.push('(');
        });
        sqrt.setBackground(Color.GRAY.brighter());

        JButton div = new JButton("\u00F7" );
        c.insets = new Insets(gap,gap,0,gap);
        div.setName("Divide");
        c.gridx = 3;
        c.gridy = 3;
        add(div, c);
        div.addActionListener(e -> inputHandler(equation, "\u00F7"));
        div.setBackground(Color.GRAY.brighter());

        JButton btn7 = new JButton("7" );
        c.insets = new Insets(gap,0,0,0);
        btn7.setName("Seven");
        c.gridx = 0;
        c.gridy = 4;
        add(btn7, c);
        btn7.addActionListener(e -> inputHandler(equation, "7"));

        JButton btn8 = new JButton("8" );
        btn8.setName("Eight");
        c.insets = new Insets(gap,gap,0,0);
        c.gridx = 1;
        c.gridy = 4;
        add(btn8, c);
        btn8.addActionListener(e -> inputHandler(equation, "8"));

        JButton btn9 = new JButton("9" );
        btn9.setName("Nine");
        c.gridx = 2;
        c.gridy = 4;
        add(btn9, c);
        btn9.addActionListener(e -> inputHandler(equation, "9"));

        JButton mul = new JButton("\u00D7" );
        c.insets = new Insets(gap,gap,0,gap);
        mul.setName("Multiply");
        c.gridx = 3;
        c.gridy = 4;
        add(mul, c);
        mul.addActionListener(e -> inputHandler(equation, "\u00D7"));
        mul.setBackground(Color.GRAY.brighter());


        JButton btn4 = new JButton("4" );
        btn4.setName("Four");
        c.insets = new Insets(gap,0,0,0);
        c.gridx = 0;
        c.gridy = 5;
        add(btn4,c);
        btn4.addActionListener(e -> inputHandler(equation, "4"));

        JButton btn5 = new JButton("5" );
        btn5.setName("Five");
        c.insets = new Insets(gap,gap,0,0);
        c.gridx = 1;
        c.gridy = 5;
        add(btn5,c);
        btn5.addActionListener(e -> inputHandler(equation, "5"));

        JButton btn6 = new JButton("6" );
        btn6.setName("Six");
        c.gridx = 2;
        c.gridy = 5;
        add(btn6, c);
        btn6.addActionListener(e -> inputHandler(equation, "6"));

        JButton sub = new JButton("-" );
        c.insets = new Insets(gap,gap,0,gap);
        sub.setName("Subtract");
        c.gridx = 3;
        c.gridy = 5;
        add(sub, c);
        sub.addActionListener(e -> inputHandler(equation, "-"));
        sub.setBackground(Color.GRAY.brighter());

        JButton btn1 = new JButton("1" );
        btn1.setName("One");
        c.insets = new Insets(gap,0,0,0);
        c.gridx = 0;
        c.gridy = 6;
        add(btn1, c);
        btn1.addActionListener(e -> inputHandler(equation, "1"));

        JButton btn2 = new JButton("2" );
        btn2.setName("Two");
        c.insets = new Insets(gap,gap,0,0);
        c.gridx = 1;
        c.gridy = 6;
        add(btn2,c);
        btn2.addActionListener(e -> inputHandler(equation, "2"));

        JButton btn3 = new JButton("3" );
        btn3.setName("Three");
        c.gridx = 2;
        c.gridy = 6;
        add(btn3,c);
        btn3.addActionListener(e -> inputHandler(equation, "3"));

        JButton add = new JButton("\u002B" );
        c.insets = new Insets(gap,gap,0,gap);
        add.setName("Add");
        c.gridx = 3;
        c.gridy = 6;
        add(add, c);
        add.addActionListener(e -> inputHandler(equation, "\u002B"));
        add.setBackground(Color.GRAY.brighter());

        JButton plusMinus = new JButton("\u00B1" );
        plusMinus.setName("PlusMinus");
        c.insets = new Insets(gap,0,0,0);
        c.gridx = 0;
        c.gridy = 7;
        add(plusMinus, c);
        plusMinus.addActionListener(e -> setNegation(equation));

        JButton btn0 = new JButton("0" );
        btn0.setName("Zero");
        c.insets = new Insets(gap,gap,0,0);
        c.gridx = 1;
        c.gridy = 7;
        add(btn0, c);
        btn0.addActionListener(e -> inputHandler(equation, "0"));

        JButton dot = new JButton("." );
        dot.setName("Dot");
        c.gridx = 2;
        c.gridy = 7;
        add(dot, c);
        dot.addActionListener(e -> inputHandler(equation, "."));

        JButton solve = new JButton("=" );
        c.insets = new Insets(gap,gap,0,gap);
        solve.setName("Equals");
        c.gridx = 3;
        c.gridy = 7;
        add(solve, c);



        solve.addActionListener(e -> {
            String input = equation.getText();
            if (isOperator(input.charAt(input.length()-1))) {  // if equation end with an operator
                equation.setForeground(Color.RED.darker());
            } else if (input.charAt(input.length()-1) == '0' && input.charAt(input.length()-2) == '\u00F7') {
                equation.setForeground(Color.RED.darker());
            }else {
                result.setText(evaluate(input));
            }

        });
    }
    void clearState() {
        this.negation = false;
        this.inputStack.clear();
    }
    void setNegation(JLabel equation) {
        String eq = equation.getText();
        if ((eq.length() == 0 || eq.matches("\\d")) && !this.negation) {
            equation.setText("(-" + eq);
            this.inputStack.push('(');
            this.negation = true;
        } else if (eq.matches("^\\(-\\d*") && this.negation) {
            equation.setText(eq.substring(2));
            clearState();
        }else if (!negation){
            equation.setText("(-(" + eq+ "))");
            this.negation = true;
        } else if (eq.matches("^\\(-.+\\)\\)$")) {
            equation.setText(eq.substring(3,eq.length()-2));
            this.negation = false;
        }
    }
    static char getLastChar(String s) {
        if(s.length() >= 1) {
            return s.charAt(s.length()-1);
        } else {
            return '0';
        }

    }
     static String inputParentheses(JLabel input, Stack<Character>inputStack) {
        String equation = input.getText();

        if (isOperator(getLastChar(equation)) || getLastChar(equation) == '(' || inputStack.isEmpty()) {
            inputStack.push('(');
            return "(";
        }
        else if (inputStack.peek() == '(') {
            inputStack.pop();
            return ")";
        }
         return "0";
     }
    static void inputHandler(JLabel input, String s) {

        String existing = input.getText();
        System.out.println("Input" +  existing + s);
        input.setText(testInput(existing, s));
    }
    // test input to errors
    public static String testInput(String existing, String s) {
        if (s.length() == 1 ) { // is s is 1 char
            char input = s.charAt(0);
            if (existing.length() == 0  && isOperator(input) ) {  // if users try to enter an operator as the first character;
                return existing;
            }
            else if (existing.length() == 1 && existing.charAt(0) == '.' ){ // .6 -> 0.6
                return "0." + s.charAt(0);
            }
            else if (existing.length() != 0 && existing.charAt(existing.length() - 1) == '.' && !Character.isDigit(input)){  // 7. -> 7.0
                return existing + "0" + input;
            }
            else if(existing.length() != 0 && isOperator(existing.charAt(existing.length()-1)) && isOperator(input)){ // 2+- -> 2-
                return existing.substring(0, existing.length() - 1) + input;
            }
            return existing + input;
        } else if(s.matches("^\\^\\d+")){
            return existing + s;
        }
        return existing + s;

    }
    public static String evaluate(String expression) {
        System.out.println("expression: " +  expression);
        String postfix;
        if(expression.matches("^\\(-.+")) {
            String newEx = expression.substring(1);
            postfix = infixToPostfix("(0"+ newEx);
        } else {
            postfix = infixToPostfix(expression);
        }
        if (postfix.matches("Unbalanced parentheses")){
            return postfix;
        } else {
            return String.valueOf(evaluatePostfix(postfix));
        }

    }
    public static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                postfix.append(c);
                // Handle multi-digit numbers
                while (i + 1 < infix.length() && (Character.isDigit(infix.charAt(i + 1))||(infix.charAt(i + 1) == '.'))) {
                    postfix.append(infix.charAt(++i));
                }
                postfix.append(' ');
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(' ');
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    //System.out.println("Invalid expression");
                    return "Invalid expression";
                    //throw new IllegalArgumentException("Unbalanced parentheses");
                }
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek()) && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(' ');
                }
                stack.push(c);
            } else if (!Character.isWhitespace(c)) {
                return "Invalid expression";
                //System.out.println("Invalid expression");
                //throw new IllegalArgumentException("Invalid character: " + c);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                return "Invalid expression";
                //System.out.println("Invalid expression");
                //throw new IllegalArgumentException("Unbalanced parentheses");
            }
            postfix.append(stack.pop()).append(' ');
        }

        return postfix.toString().trim();
    }

    public static String evaluatePostfix(String postfix) {
        System.out.println("postfix :" + postfix);
        Stack<Float> stack  = new Stack<>();
        DecimalFormat df = new DecimalFormat("#.###");
        Scanner scanner = new Scanner(postfix);

        while (scanner.hasNext()) {
            if (scanner.hasNextFloat()) {
                stack.push(scanner.nextFloat());
            } else {
                char operator = scanner.next().charAt(0);
                if (operator == '√') {
                    float a = stack.pop();
                    stack.push((float) Math.sqrt(a));
                } else {
                    float b = stack.pop();
                    float a = stack.pop();

                    switch (operator) {
                        case '\u002B' -> stack.push(a + b);
                        case '-' -> stack.push(a - b);
                        case '\u00D7' -> stack.push(a * b);
                        case '\u00F7' -> stack.push(a / b);
                        case '^' -> stack.push((float) Math.pow(a, b));
                    }
                }

            }
        }
        return df.format(stack.pop());
    }

    public static boolean isOperator(char c) {
        return c == '\u002B' || c == '-' || c == '\u00D7' || c == '\u00F7' || c == '^' || c == '√';
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '\u002B':
            case '-':
                return 1;
            case '\u00D7':
            case '\u00F7':
                return 2;
            case '√':
            case '^':
                return 3;
            default:
                return -1;
        }
    }

}

