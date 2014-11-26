/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.erikavinicius.dados;

import br.com.erikavinicius.entidade.Departamento;
import br.com.erikavinicius.entidade.Projeto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vinicius
 */
public class BancoDadosAtividade {
    public static void CriarAtividade(int codigo, String nome, int duracao, String cpfEnc) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BancoDadosUtil.getConnection();

            //Código de criar...
            String sql = "INSERT INTO ATIVIDADE(COD_ATIVIDADE, NOME, DURACAO_PREV, FK_ENCARREGADO_CPF) VALUES (?,?,?,?)";
            comando = conexao.prepareStatement(sql);

            comando.setInt(1, codigo);
            comando.setString(2, nome);
            comando.setInt(3, duracao);
            comando.setString(4, cpfEnc);

            comando.execute();

            conexao.commit();
        } catch (Exception e) {
            if (conexao != null && !conexao.isClosed()) {
                conexao.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        }
    }
    public static int ConsultaUltimoCodigo() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        int ultimoCodigo = 0;
        try {
            conexao = BancoDadosUtil.getConnection();

            //Código de criar...
            String sql = "SELECT MAX(COD_PROJETO) FROM PROJETO";
            comando = conexao.prepareStatement(sql);

            resultado = comando.executeQuery();

            while (resultado.next()) {
            //Instancia um novo objeto e atribui os valores vindo do BD
                //(Note que no BD o index inicia por 1)
                ultimoCodigo = (resultado.getInt(1));
             
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (resultado != null && !resultado.isClosed()) {
                resultado.close();
            }
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        }
        return ultimoCodigo;
    }
    public static List<Projeto> ConsultaTodasAtividades() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        List<Projeto> listaProjetos = new ArrayList<Projeto>();
                
        try {
            conexao = BancoDadosUtil.getConnection();

            //Código de criar...
            String sql = "SELECT COD_PROJETO, NOME, DESCRICAO, DATA_INICIO, DATA_TERMINO, FK_ATIVIDADE FROM PROJETO";
            comando = conexao.prepareStatement(sql);

            resultado = comando.executeQuery();

            while (resultado.next()) {
            //Instancia um novo objeto e atribui os valores vindo do BD
                //(Note que no BD o index inicia por 1)
                                
                Projeto projeto = new Projeto();
                projeto.setCodigo(resultado.getInt(1));
                projeto.setNome(resultado.getString(2));
                projeto.setDescricao(resultado.getString(3));
                projeto.setDataInicio(resultado.getString(4));
                projeto.setDataTermino(resultado.getString(5));
                //Adiciona um item à lista que será retornada
                listaProjetos.add(projeto);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (resultado != null && !resultado.isClosed()) {
                resultado.close();
            }
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        }
        return listaProjetos;
    }
    
    public static void removeAtividade(int Codigo) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BancoDadosUtil.getConnection();

            //Código de criar...
            String sql = "DELETE FROM PROJETO WHERE COD_PROJETO ='"+Codigo+"'";
            comando = conexao.prepareStatement(sql);
            
            comando.execute();

            conexao.commit();
        } catch (Exception e) {
            if (conexao != null && !conexao.isClosed()) {
                conexao.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        }
    }
    public static void EditaAtividade(int codigo, String nome, String descricao, String dataInicio, String dataTermino) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        try {
            conexao = BancoDadosUtil.getConnection();

            //Código de criar...
            String sql = "UPDATE PROJETO SET COD_PROJETO = ?, NOME = ?, DESCRICAO = ?, DATA_INICIO = ?, DATA_TERMINO = ? WHERE COD_PROJETO ='"+codigo+"'";
            comando = conexao.prepareStatement(sql);

            comando.setInt(1, codigo);
            comando.setString(2, nome);
            comando.setString(3, descricao);
            comando.setString(4, dataInicio);
            comando.setString(5, dataTermino);
            
            comando.execute();

            conexao.commit();
        } catch (Exception e) {
            if (conexao != null && !conexao.isClosed()) {
                conexao.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        }

    }

}