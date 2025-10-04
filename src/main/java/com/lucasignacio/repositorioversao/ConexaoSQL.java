package com.lucasignacio.repositorioversao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// Importações para trabalhar com JDBC (conexão com banco de dados)

/**
 * Classe responsável por fornecer conexões com o banco de dados SQL Server.
 */
public class ConexaoSQL {

    // Substitua "LUCAS" pelo nome correto do seu servidor.
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=meu_banco;encrypt=true;trustServerCertificate=true;";

    // Nome do usuário do banco de dados.
    private static final String USUARIO = "meu_usuario";

    // Senha do usuário.
    private static final String SENHA = "minha_senha";


    // URL de conexão JDBC com SQL Server.
    // Substitua "LUCAS" pelo nome correto do seu servidor.
    //private static final String URL = "jdbc:sqlserver://AREASQLCR01:1433;databaseName=ZonaAzul_Hml;encrypt=true;trustServerCertificate=true;";

    // Nome do usuário do banco de dados.
    //private static final String USUARIO = "rep_apk";

    // Senha do usuário.
    //private static final String SENHA = "IC1oqx?W1iA6";
    
    // ---- Banco local Lucas -------------
    //private static final String URL = "jdbc:sqlserver://AREADSKW36:1433;databaseName=REPOSITORIO_AREATEC;encrypt=true;trustServerCertificate=true;";

    // Nome do usuário do banco de dados.
    //private static final String USUARIO = "sa";

    // Senha do usuário.
    //private static final String SENHA = "Nachi2035@";
    

    /**
     * Método estático que tenta conectar ao banco e retorna um objeto Connection.
     * @return Connection ou null em caso de falha.
     */
    public static Connection conectar() {
        try {
            // Tenta se conectar ao banco de dados com os parâmetros definidos
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            // Em caso de erro na conexão, exibe a mensagem no console
            System.err.println("Erro ao conectar com o banco de dados:");
            e.printStackTrace();
            return null;
        }
    }
}
