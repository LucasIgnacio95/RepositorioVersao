package com.lucasignacio.repositorioversao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
// Importações necessárias para trabalhar com listas e com JDBC

/**
 * Classe DAO (Data Access Object) para operações com dados de clientes e cidades no banco.
 */
public class ClienteDAO {
   

    /**
     * Recupera todos os nomes de clientes do banco de dados, ordenados alfabeticamente.
     * @return Lista de nomes de clientes.
     */
    public List<String> listarClientes() {
        List<String> clientes = new ArrayList<>();
        // Lista que armazenará os nomes dos clientes encontrados

        try (Connection con = ConexaoSQL.conectar()) {
            // Abre a conexão com o banco de dados usando a classe ConexaoSQL

            String sql = "SELECT cliente FROM CLIENTES";
            // Consulta SQL que busca todos os nomes da tabela "clientes" ordenados alfabeticamente

            PreparedStatement ps = con.prepareStatement(sql);
            // Prepara a consulta (previne SQL Injection)

            ResultSet rs = ps.executeQuery();
            // Executa a consulta e retorna os resultados

            while (rs.next()) {
                // Percorre o resultado linha por linha
                clientes.add(rs.getString("cliente"));
                // Adiciona o nome do cliente à lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, imprime no console
        }

        return clientes;
        // Retorna a lista preenchida
    }

    /**
     * Recupera todas as cidades associadas a um cliente específico.
     * @param nomeCliente Nome do cliente desejado.
     * @return Lista de nomes das cidades vinculadas ao cliente.
     */
    public List<String> listarCidadesPorCliente(String nomeCliente) {
        List<String> cidades = new ArrayList<>();
        // Lista que armazenará os nomes das cidades associadas ao cliente

        try (Connection con = ConexaoSQL.conectar()) {
            // Abre a conexão com o banco de dados

            // Consulta SQL:
            // - Une a tabela "cidades" com "clientes" pela chave estrangeira cliente_id
            // - Filtra para retornar apenas as cidades do cliente informado
            // - DISTINCT evita cidades repetidas
            // - ORDER BY ordena alfabeticamente
            String sql = """
                SELECT DISTINCT c.cidade
                FROM CIDADES c
                INNER JOIN CLIENTES cli ON cli.id = c.id_cliente
                WHERE cli.cliente = ?
                ORDER BY c.cidade
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            // Prepara a consulta SQL com parâmetro

            ps.setString(1, nomeCliente);
            // Substitui o "?" na consulta pelo nome do cliente recebido no método

            ResultSet rs = ps.executeQuery();
            // Executa a consulta e obtém os resultados

            while (rs.next()) {
                cidades.add(rs.getString("cidade"));
                // Adiciona o nome da cidade à lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, imprime no console
        }

        return cidades;
        // Retorna a lista preenchida
    }
    
    public List<ArquivoInfo> listarArquivosPorCidade(String nomeCidade) {
         // Agora criamos UMA ÚNICA lista que vai guardar objetos ArquivoInfo
        List<ArquivoInfo> resultados = new ArrayList<>();
        String sql = """
                SELECT  nq.id_chamado,
                	cli.cliente,
                	ci.cidade,
                	cha.chamado,
                	observacao,
                	caminho_arquivo,
                	nq.nome_arquivo
                        FROM ARQUIVOS nq
                                INNER JOIN CLIENTES cli ON nq.id_cliente = cli.id                       
                                INNER JOIN CIDADES ci ON  nq.id_cidade =  ci.id                    
                                INNER JOIN CHAMADOS cha ON  nq.id_chamado = cha.id
                				WHERE cidade = ?
            """;

        try (Connection con = ConexaoSQL.conectar()) {
            // Abre a conexão com o banco de dados

            // Consulta SQL:
            // - Une a tabela "cidades" com "clientes" pela chave estrangeira cliente_id
            // - Filtra para retornar apenas as cidades do cliente informado
            // - DISTINCT evita cidades repetidas
            // - ORDER BY ordena alfabeticamente
            

            PreparedStatement ps = con.prepareStatement(sql);
            // Prepara a consulta SQL com parâmetro

            ps.setString(1, nomeCidade);
            // Substitui o "?" na consulta pelo nome do cliente recebido no método

            ResultSet rs = ps.executeQuery();
            // Executa a consulta e obtém os resultados

            while (rs.next()) {
                int idArquivo = (rs.getInt("id_chamado"));
                // Adiciona o nome do cliente à lista
                String clientes =(rs.getString("cliente"));
                // Adiciona o nome do cidade à lista
                String cidades =(rs.getString("cidade"));
                // Adiciona o número do chamado à lista
                int chamadoInt = (rs.getInt("chamado"));
                // Adiciona a observação à lista
                String observacoes =(rs.getString("observacao"));
                // Adiciona o caminho do arquivo à lista
                String caminhoArquivos = (rs.getString("caminho_arquivo"));
                // Adiciona o nome do arquivo à lista
                String nomeArquivos = (rs.getString("nome_arquivo"));
                
                
                //String chamadoStr = String.valueOf(chamadoInt);
                
                 // Cria um novo objeto "pacote" com os dados da linha
                ArquivoInfo info  = new ArquivoInfo(idArquivo, clientes, cidades, chamadoInt, observacoes, caminhoArquivos, nomeArquivos);
            
                // Adiciona o pacote completo à nossa lista de resultados
                resultados.add(info);
            }           
           
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, imprime no console
        }
                
        return resultados;        
        // Retorna a lista preenchida
    }

    
    
    public List<String> listarNomeArquivo(String numeroChamado, String nomeCliente, String nomeCidade){
         List<String> novoNumeroChamado = new ArrayList<>();
        
        try (Connection con = ConexaoSQL.conectar()) {
            // Abre a conexão com o banco de dados
            
            String sql = """
                SELECT                          
                nq.nome_arquivo
                FROM ARQUIVOS nq
                INNER JOIN CHAMADOS cha ON nq.id_chamado = cha.id
                INNER JOIN CLIENTES cli ON nq.id_cliente = cli.id                       
                INNER JOIN CIDADES ci ON nq.id_cidade = ci.id                                          
                WHERE 
                         cha.chamado = ? AND
                         cli.cliente = ? AND
                         ci.cidade = ?                	              
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            // Prepara a consulta SQL com parâmetro

            
            ps.setString(1, numeroChamado);
            // Substitui o "?" na consulta pelo numero do chamado recebido no método            
            
            ps.setString(2, nomeCliente);
            // Substitui o "?" na consulta pelo nome do cliente recebido no método
            
            ps.setString(3, nomeCidade);
            // Substitui o "?" na consulta pelo numero da cidade recebido no método         
            
                        
            ResultSet rs = ps.executeQuery();
            // Executa a consulta e obtém os resultados
            
            while (rs.next()) {
                novoNumeroChamado.add(rs.getString("nome_arquivo"));
                // Adiciona o chamado à lista
            }            

        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, imprime no console
        }

        return novoNumeroChamado;
        // Retorna a lista preenchida
    }

    
    public List<String> listarCaminho(String nomeArquivo) {
        
        List<String> novoCaminhoOrigem = new ArrayList<>();
    
         try (Connection con = ConexaoSQL.conectar()) {
            // Abre a conexão com o banco de dados
            
            String sql = """
                SELECT
                caminho_arquivo
                FROM ARQUIVOS 
                     WHERE nome_arquivo = ?                        
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            // Prepara a consulta SQL com parâmetro

            ps.setString(1, nomeArquivo);
            // Substitui o "?" na consulta pelo numero do chamado recebido no método
                        
            ResultSet rs = ps.executeQuery();
            // Executa a consulta e obtém os resultados
            
            while (rs.next()) {
                novoCaminhoOrigem.add(rs.getString("caminho_arquivo"));
                // Adiciona o chamado à lista
            }            

        } catch (SQLException e) {
            e.printStackTrace();
            // Em caso de erro, imprime no console
        }

        return novoCaminhoOrigem;
        // Retorna a lista preenchida    
    }
    
    
    
    //public void gravarAqruivo()
    
    public void gravarChamado(String numeroChamado, String nomeCidade, String nomeCliente, String nomeObservacao) {
        
        try(Connection con = ConexaoSQL.conectar()) {
        
        
         String sql ="""
                INSERT INTO CHAMADOS (chamado, id_cidade, id_cliente, observacao)
                     VALUES (?,
                     (SELECT TOP 1 id FROM CIDADES WHERE cidade = ?),
                     (SELECT TOP 1 id FROM CLIENTES WHERE cliente = ?),
                     ?
                ) """;
        
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, numeroChamado);
        ps.setString(2, nomeCidade);
        ps.setString(3, nomeCliente);
        ps.setString(4, nomeObservacao);
        
        
        ps.executeUpdate();
        
        System.out.println("Chamado gravado com sucesso");
        
       } catch (SQLException e) {
           e.printStackTrace();
       }    
    }
    
    public void gravarArquivo(String nomeCliente, String nomeCidade, String numeroChamado, String caminhoArquivo, String nomeArquivo ){
    
            
        try(Connection con = ConexaoSQL.conectar()) {
            
            String sql ="""
                INSERT INTO ARQUIVOS (id_chamado, id_cliente, id_cidade, caminho_arquivo, nome_arquivo)
                        VALUES  
                        ((SELECT TOP 1 id FROM CHAMADOS WHERE chamado = ?),
                        (SELECT TOP 1 id FROM CLIENTES WHERE cliente = ?),
                        (SELECT TOP 1 id FROM CIDADES WHERE cidade = ?),
                        ?,?)
                   """;
            
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, numeroChamado);
        ps.setString(2, nomeCliente);
        ps.setString(3, nomeCidade);        
        ps.setString(4, caminhoArquivo);
        ps.setString(5, nomeArquivo);        
        
        ps.executeUpdate();
        
        System.out.println("Registro de arquivo salvo no banco com sucesso");
    
        } catch (SQLException e) {
           e.printStackTrace();
       }   
    
    }
    
    public void excluirArquivo (String numeroChamado){
        try(Connection con = ConexaoSQL.conectar()) {
            
            String sql ="""
                   DELETE FROM CHAMADOS
                      WHERE chamado = ?
                   """;
            
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, numeroChamado);         
        
        ps.executeUpdate();
        
        System.out.println("Registro de arquivo excluído no banco com sucesso");
    
        } catch (SQLException e) {
           e.printStackTrace();
       }   
        
    }
    
    public void atualizarObservacao (int idArquivo, String novaObservacao){
                
        try(Connection con = ConexaoSQL.conectar()) {
            
            String sql ="""
                   UPDATE CHAMADOS
                      SET observacao = ?  
                      WHERE id = ?
                   """;
            
        PreparedStatement ps = con.prepareStatement(sql);
        
        ps.setString(1, novaObservacao);         
        ps.setInt(2, idArquivo);         
        
        
        ps.executeUpdate();
        
        System.out.println("Registro de observação alterado no banco com sucesso");
    
        } catch (SQLException e) {
           e.printStackTrace();
       }   
    }
    
}

