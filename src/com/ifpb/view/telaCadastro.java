package com.ifpb.view;

import com.ifpb.control.FuncionarioDao;
import com.ifpb.control.FuncionarioImpDao;
import com.ifpb.exceptions.CampoNuloException;
import com.ifpb.exceptions.IdadeInvalidaException;
import com.ifpb.model.Funcionario;
import com.ifpb.model.Setor;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.time.format.ResolverStyle.LENIENT;

public class telaCadastro extends JFrame {
    private JPanel contentPane;
    private JFormattedTextField cpffrmfield;
    private JTextField textField1;
    private JTextField textField2;
    private JFormattedTextField datanasc;
    private JFormattedTextField numTel;
    private JComboBox comboBox1;
    private JButton salvarButton;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JButton buttonOK;
    private FuncionarioDao daoFunc;

    public telaCadastro() {

        try{
            daoFunc = new FuncionarioImpDao();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Falha ao abrir o arquivo", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }

        setContentPane(contentPane);
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);
        pack();
        setLocationRelativeTo(null);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textField1.getText();
                String username = textField3.getText();
                String senha = new String(passwordField1.getPassword());
                Setor setor = (Setor) comboBox1.getSelectedItem();

                String cpf = cpffrmfield.getText();
                String telefone = numTel.getText();
                String email = textField2.getText();

                LocalDate nascimento = LocalDate.MIN;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                try{
                    nascimento = LocalDate.parse(datanasc.getText(),formatter);
                    LocalDate nascimentoComp = LocalDate.parse(datanasc.getText(),formatter.withResolverStyle(LENIENT));
                    if (nascimentoComp.getMonthValue() > nascimento.getMonthValue()){
                        JOptionPane.showMessageDialog(null, "O dia excede o limite de dias do mês digitado, portanto no momento de salvar, será corrigido para o último dia do Mês, que é "
                                        +nascimento.lengthOfMonth()+".",
                                "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (DateTimeParseException ex){
                    JOptionPane.showMessageDialog(null, "Data inválida!", "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                }

                Funcionario funcionario =  new Funcionario(username, senha, cpf, nome, email, telefone, nascimento, setor);

                try {
                    if (daoFunc.buscarPorCpf(cpf)!=null){
                        JOptionPane.showMessageDialog(null, "O CPF digitado já pertence a um usuário cadastrado!", "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (daoFunc.buscarPorEmail(email)!=null && !daoFunc.buscarPorEmail(email).getEmail().equals("")){
                        JOptionPane.showMessageDialog(null, "O e-mail digitado já pertence a um usuário cadastrado!", "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (daoFunc.buscarPorUsername(username)!=null){
                        JOptionPane.showMessageDialog(null, "O nome de usuário digitado já pertence a um usuário cadastrado!", "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (daoFunc.salvar(funcionario)) {
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    }
                }catch(IOException | ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "Falha no arquivo", "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                }catch (CampoNuloException ex){
                    JOptionPane.showMessageDialog(null, ex.getMensagem(), "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                }catch (IdadeInvalidaException ex){
                    JOptionPane.showMessageDialog(null, ex.getMensagem(), "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
                }finally{
                    telaLogin telaLogin = new telaLogin();
                    telaLogin.pack();
                    telaLogin.setVisible(true);
                    dispose();
                }
            }
        });

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                telaLogin telaLogin = new telaLogin();
                telaLogin.pack();
                telaLogin.setVisible(true);
                e.getWindow().dispose();
            }
        });
    }

    public static void main(String[] args) {
        telaCadastro dialog = new telaCadastro();
        dialog.pack();
        dialog.setVisible(true);
    }

    private void createUIComponents() {

        MaskFormatter formatterCPF = null;
        MaskFormatter formatterData = null;
        MaskFormatter formatterTel = null;

        try{
            formatterCPF = new MaskFormatter("###.###.###-##");
            formatterData = new MaskFormatter("##/##/####");
            formatterTel = new MaskFormatter("(##)#####-####");
        }catch (ParseException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Mensagem de erro", JOptionPane.ERROR_MESSAGE);
        }

        cpffrmfield = new JFormattedTextField();
        datanasc = new JFormattedTextField();
        numTel = new JFormattedTextField();
        if(formatterCPF != null && formatterData != null && formatterTel!=null){
            formatterCPF.install(cpffrmfield);
            formatterData.install(datanasc);
            formatterTel.install(numTel);
        }

        comboBox1 = new JComboBox(Setor.values());
    }
}
