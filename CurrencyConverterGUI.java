import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverterGUI extends JFrame {
    private JTextField amountTextField;
    private JComboBox<String> fromComboBox;
    private JComboBox<String> toComboBox;
    private JLabel resultLabel;

    public CurrencyConverterGUI() {
        setTitle("Currency Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField(10);
        JLabel fromLabel = new JLabel("From:");
        fromComboBox = new JComboBox<>();
        JLabel toLabel = new JLabel("To:");
        toComboBox = new JComboBox<>();
        JButton convertButton = new JButton("Convert");
        resultLabel = new JLabel();

        // Set layout
        setLayout(new GridLayout(4, 2));

        // Add components to the frame
        add(amountLabel);
        add(amountTextField);
        add(fromLabel);
        add(fromComboBox);
        add(toLabel);
        add(toComboBox);
        add(convertButton);
        add(resultLabel);

        // Add action listener to the convert button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        // Add currency options to the combo boxes
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD"};
        fromComboBox.setModel(new DefaultComboBoxModel<>(currencies));
        toComboBox.setModel(new DefaultComboBoxModel<>(currencies));
    }

    private void convertCurrency() {
        String fromCurrency = (String) fromComboBox.getSelectedItem();
        String toCurrency = (String) toComboBox.getSelectedItem();
        double amount = Double.parseDouble(amountTextField.getText());

        try {
            double exchangeRate = ExchangeRateFetcher.getExchangeRate(fromCurrency, toCurrency);
            double convertedAmount = amount * exchangeRate;
            resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, fromCurrency, convertedAmount, toCurrency));
        } catch (Exception e) {
            resultLabel.setText("Error fetching exchange rate.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CurrencyConverterGUI currencyConverterGUI = new CurrencyConverterGUI();
                currencyConverterGUI.setVisible(true);
            }
        });
    }
}
