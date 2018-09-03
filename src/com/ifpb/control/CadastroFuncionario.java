package com.ifpb.control;
import com.ifpb.model.Funcionario;
import java.util.List;
import java.util.ArrayList;

/**
 * Esta classe controla a entrada e saída de informações referentes ao cadastro de um funcionário.
 * @author Hexcz
 * @author lethiciacl
 * @version 1.0
 * @since 1.0
 * */

public class CadastroFuncionario {

    private List<Funcionario> funcionarios;


    /**
     * CONSTRUTORES.
     * */
    public CadastroFuncionario(){
        funcionarios = new ArrayList<>();
    }

    /**
     * Esta função tem por objetivo procurar uma posição na lista de funcionários.
     * @param funcionario - recebe um parâmetro do tipo Funcionario.
     * @return int - retorna um inteiro que corresponde à posição na lista.
     * Se o valor do retorno for "-1", significa que o funcionário não existe na lista.
     * */

    private int searchIndex(Funcionario funcionario){
        return funcionarios.indexOf(funcionario);
    }

    /**
     * Esta função tem por objetivo criar um novo funcionário na lista.
     * @param funcionario - recebe um parâmetro do tipo Funcionário.
     * @return true - se foi possível criar o funcionário.
     * @return false - se não foi possível criar o funcionário.
     * */

    public boolean create(Funcionario funcionario){
        if(searchIndex(funcionario)<0){
           funcionarios.add(funcionario);
            return true;
        }
        return false;
    }


    /**
     * Esta função tem por objetivo deletar um funcionário na lista.
     * @param funcionario - recebe um parâmetro do tipo Funcionário.
     * @return true - se foi possível deletar o funcionário.
     * @return false - se não foi possível deletar o funcionário.
     * */

    public boolean delete(Funcionario funcionario){
       if(funcionarios.indexOf(funcionario)<0) {
           return false;
       }

       funcionarios.remove(funcionario);
       return true;
    }

    /**
     * Esta função tem por objetivo criar um novo funcionário na lista.
     * @param cpf - recebe um parâmetro do tipo String.
     * @return f - retorna o funcionário cujo cpf corresponde ao informado.
     * @return null - se não houver funcionário com o cpf informado.
     * */

    public Funcionario read(String cpf){

        for(Funcionario f : funcionarios){
            if(f.getCpf() == cpf){
                return f;
            }
        }
        return null;
    }


    /**
     * Esta função tem por objetivo atualizar um funcionário na lista.
     * @param funcionario - recebe um parâmetro do tipo Funcionário.
     * @return true - se foi possível atualizar o funcionário.
     * @return false - se não foi possível atualizar o funcionário.
     * */

    public boolean update(Funcionario funcionario){
        for(Funcionario f : funcionarios){
            if(f.getCpf() == funcionario.getCpf()){
                funcionarios.set(funcionarios.indexOf(f), funcionario);
                return true;
            }
        }
        return false;
    }


    /**
     * Esta função tem por objetivo listar os funcionários da lista.
     * @return List - retorna uma lista de funcionários.
     * */

    public List<Funcionario> list(){
        return funcionarios;
    }

}