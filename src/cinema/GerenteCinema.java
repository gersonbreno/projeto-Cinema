/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinema;

import java.io.SyncFailedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author gerso
 */
public class GerenteCinema {

    Scanner ler = new Scanner(System.in);
    ArrayList<Filme> lista;
    ArrayList<Cliente> lista1;
    ArrayList<Ingresso> lista2;

    public GerenteCinema() {
        lista = new ArrayList<Filme>();
        lista1 = new ArrayList<Cliente>();
      
    }
    static String url = "jdbc:postgresql://localhost/Cinema";
    static String user = "postgres";
    static String password = "123";

    private void atualizarDadosNoBanco(Filme filme) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
          
            Class.forName("org.postgresql.Driver");

      
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/Cinema", "postgres", "123");

           
            String sql = "UPDATE filme  SET nomefilme=?, genero=?, horas=?, codicoFilme=? WHERE codicoFilme=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, filme.getNome());
            stmt.setString(2, filme.getGenero());
            stmt.setString(3, filme.getDuracao());
            stmt.setInt(4, filme.getCodico());
            stmt.setInt(5, filme.getCodico());

           
            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
          
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void atualizarDadosNoBancoCliente(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
         
            Class.forName("org.postgresql.Driver");

           
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/Cinema", "postgres", "123");

           
            String sql = "UPDATE cliente SET cpf=?, nome=?, idade=? WHERE cpf=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setInt(3, cliente.getIdade());
            stmt.setInt(4, cliente.getCpf());

           
            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
          
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void AdicionarFilmes(Filme fi) {
        for (Filme f : this.lista) {
            if (fi.getCodico() == f.getCodico()) {
                System.out.println("Erro: Não é possível cadastrar filmes com codicos iguais");
                return;
            }
        }

       
        try ( Connection connection = DriverManager.getConnection(url, user, password)) {
           
            String sql = "INSERT INTO filme (nomeFilme , genero,horas, codicoFilme) VALUES (?, ?, ?, ?)";

            
            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, fi.getNome());
                preparedStatement.setString(2, fi.getGenero());
                preparedStatement.setString(3, fi.getDuracao());
                preparedStatement.setInt(4, fi.getCodico());
              
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Filme adicionado com sucesso ao banco de dados.");
                    
                    this.lista.add(fi);
                } else {
                    System.out.println("Erro: Não foi possível adicionar o filme ao banco de dados.");
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.out.println("Erro: Falha na conexão com o banco de dados.");
        }
    }

    public void RemoverFilme(int codico) throws java.sql.SQLException {
        for (Filme f : lista) {
            if (f.getCodico() == codico) {
                try ( Connection connection = DriverManager.getConnection(url, user, password)) {
                    String sql = "DELETE FROM filme WHERE codicofilme = ?";

                    try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setInt(1, f.getCodico());
                        preparedStatement.executeUpdate();
                    }
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                    System.err.println("Erro: Falha ao remover o filme do banco de dados.");
                }

                lista.remove(f); // Remove o filme da lista
                System.out.println("O Filme foi removido com sucesso: " + f);
                return;
            }
        }
        System.out.println("Nome não encontrado. Tente novamente.");
    }

    public void BuscaFilme(String nomefilme) throws java.sql.SQLException {
        try ( Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT nomeFilme, genero,horas, codicoFilme  FROM filme WHERE nomefilme = ?";

            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nomefilme);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                   nomefilme = resultSet.getString("nomeFilme");
                    String genero = resultSet.getString("genero");
                   int codico = resultSet.getInt("codicoFilme");
                    String duracao = resultSet.getString("horas");

                    Filme filme = new Filme(nomefilme, genero, duracao, codico);
                    System.out.println("Filme encontrado: " + filme);
                } else {
                    System.out.println("O Filme não foi encontrado. Tente novamente.");
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.err.println("Erro: Falha na busca do filme no banco de dados.");
        }
    }

    public void carregarFilmes() throws java.sql.SQLException {
        lista.clear(); 
        try ( Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT nomeFilme , genero, horas, codicoFilme FROM filme";

            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String nome = resultSet.getString("nomeFilme");
                    String genero = resultSet.getString("genero");
                    String duracao = resultSet.getString("horas");
                    int codigo = resultSet.getInt("codicoFilme");

                    Filme filme = new Filme(nome, genero, duracao, codigo);
                    lista.add(filme);
                    System.out.println(filme);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.err.println("Erro: Falha na conexão com o banco de dados.");
        }
    }

    public void listaTodosFilme() {
        for (Filme f : lista) {

            System.out.println(f);
            lista.clear(); 
        }
    }

    public void menuEdiçaoFilme() {
        System.out.println("=============Oque voce deseja editar==========================================");
        System.out.println("1 - nome");
        System.out.println("2 - genero");
      
        System.out.println("7 sair");
    }

    public void EditaFilme(String nome) throws SQLException {
        int op;
        for (Filme f : lista) {
            if (f.getNome().equals(nome)) {
                do {
                    System.out.println(" Digite o novo nome que voce deseja editar");
                    menuEdiçaoFilme();
                    op = ler.nextInt();
                    switch (op) {
                        case 1:
                            System.out.println(" Digite o novo nome que voce deseja editar");
                            nome = ler.next();
                            f.setNome(nome);
                            atualizarDadosNoBanco(f);
                            break;
                        case 2:
                            System.out.println(" Digite o novo genero que voce deseja editar");
                            String genero = ler.next();
                            f.setGenero(genero);
                            atualizarDadosNoBanco(f);
                            break;
                        
                          

                        case 7:
                            System.out.println("saindo.....");
                            break;
                        default:
                            throw new AssertionError();
                    }

                } while (op != 7);
                System.out.println("Dados do Filme editado: " + f);
                return;
            }
        }
        System.out.println("O Filme nao encontrado nao foi encontrado tente novamente");
    }

    
  
    public void CadastrarCliente(Cliente cl) throws java.sql.SQLException {
        for (Cliente c : this.lista1) {
            if (cl.getCpf() == c.getCpf()) {
                System.out.println("Erro: Não é possível cadastrar cliente com dois CPF iguais");
                return;
            }
        }

        try ( Connection connection = DriverManager.getConnection(url, user, password)) {
            String sqlCliente = "INSERT INTO cliente (nome, idade, cpf) VALUES (?, ?, ?)";

            try ( PreparedStatement preparedStatementCliente = connection.prepareStatement(sqlCliente)) {
                preparedStatementCliente.setString(1, cl.getNome());
                preparedStatementCliente.setInt(2, cl.getIdade());
                preparedStatementCliente.setLong(3, cl.getCpf());

                preparedStatementCliente.executeUpdate();
            }

            String sqlTelefone = "INSERT INTO TelefoneCliente (cpf,Telefone) VALUES (?, ?)";

            try ( PreparedStatement preparedStatementTelefone = connection.prepareStatement(sqlTelefone)) {
                preparedStatementTelefone.setInt(1, cl.getCpf());
                preparedStatementTelefone.setInt(2, cl.getTelefone());
                preparedStatementTelefone.executeUpdate();
            }
            String sqlEmailCliente = "INSERT INTO EmailCliente (cpf, Email) VALUES (?, ?)";

            try ( PreparedStatement preparedStatementEmail = connection.prepareStatement(sqlEmailCliente)) {
                preparedStatementEmail.setLong(1, cl.getCpf());
                preparedStatementEmail.setString(2, cl.getEmail());
                preparedStatementEmail.executeUpdate();
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.err.println("Erro: Falha ao cadastrar o cliente no banco de dados.");
            return;
        }

        lista1.add(cl);
        System.out.println("==============================================================");
        System.out.println("Cadastro realizado com sucesso");
    }

    public void RemoverCliente(int cpf) throws java.sql.SQLException {
        try ( Connection connection = DriverManager.getConnection(url, user, password)) {
         
            connection.setAutoCommit(false);

            try {
               
                String sqlRemoveEmailCliente = "DELETE FROM EmailCliente WHERE cpf = ?";
                try ( PreparedStatement preparedStatementEmailCliente = connection.prepareStatement(sqlRemoveEmailCliente)) {
                    preparedStatementEmailCliente.setInt(1, cpf);
                    preparedStatementEmailCliente.executeUpdate();
                }

                
                String sqlRemoveTelefone = "DELETE FROM TelefoneCliente WHERE cpf = ?";
                try ( PreparedStatement preparedStatementTelefone = connection.prepareStatement(sqlRemoveTelefone)) {
                    preparedStatementTelefone.setInt(1, cpf);
                    preparedStatementTelefone.executeUpdate();
                }

               
                String sqlRemoveCliente = "DELETE FROM cliente WHERE cpf = ?";
                try ( PreparedStatement preparedStatementCliente = connection.prepareStatement(sqlRemoveCliente)) {
                    preparedStatementCliente.setInt(1, cpf);
                    int rowsDeleted = preparedStatementCliente.executeUpdate();

                    if (rowsDeleted > 0) {
                        System.out.println("O Cliente foi removido com sucesso.");
                        connection.commit(); 
                    } else {
                        System.out.println("CPF não encontrado. Tente novamente.");
                        connection.rollback(); // Reverter a transação
                    }
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
                System.err.println("Erro: Falha na remoção do cliente no banco de dados.");
                connection.rollback(); 
            } finally {
                connection.setAutoCommit(true); 
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.err.println("Erro: Falha na conexão com o banco de dados.");
        }
    }

    public void BuscaCliente(int cpf) throws java.sql.SQLException {
        try ( Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT c.nome, c.idade, c.cpf, t.telefone, e.email "
                    + "FROM cliente c "
                    + "LEFT JOIN telefonecliente t ON c.cpf = t.cpf "
                    + "LEFT JOIN EmailCliente e ON c.cpf = e.cpf "
                    + "WHERE c.cpf = ?";

            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, cpf);
                ResultSet resultSet = preparedStatement.executeQuery();

                boolean clienteEncontrado = false; 
                while (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    int idade = resultSet.getInt("idade");
                    cpf = resultSet.getInt("cpf");
                    int telefone = resultSet.getInt("telefone");
                    String email = resultSet.getString("email");

                    Cliente cliente = new Cliente(nome, idade, email, telefone, cpf);
                    cliente.setTelefone(telefone);
                    cliente.setEmail(email);
                    System.out.println("Cliente encontrado: " + cliente);

                    clienteEncontrado = true; 
                }

                if (!clienteEncontrado) { 
                    System.out.println("O Cliente não foi encontrado. Tente novamente.");
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            System.err.println("Erro: Falha na busca do cliente no banco de dados.");
        }
    }

    public void carregarClientes() {
        lista1.clear();

        try ( Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT c.cpf, c.nome, c.idade, e.email, t.telefone "
                    + "FROM cliente c "
                    + "LEFT JOIN EmailCliente e ON c.cpf = e.cpf "
                    + "LEFT JOIN TelefoneCliente t ON c.cpf = t.cpf";

            try ( PreparedStatement preparedStatement = connection.prepareStatement(sql);  ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    int idade = resultSet.getInt("idade");
                    int cpf = resultSet.getInt("cpf");
                    int telefone = resultSet.getInt("telefone");
                    String email = resultSet.getString("email");

                    Cliente cliente = new Cliente(nome, idade, email, telefone, cpf);
                    lista1.add(cliente); 

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro: Falha ao carregar clientes do banco de dados.");
        }
    }

    public void listaTodosClintes() {
        for (Cliente c : lista1) {

            System.out.println(c);

        }
    }

    public void menuEdiçao() {
        System.out.println("=============Oque voce deseja editar==========================================");
        System.out.println("1 - nome");
        System.out.println("2 - idade");

        System.out.println("7 sair");
    }

    public void EditarCliente(int cpf) {
        int op;
        for (Cliente c : lista1) {
            if (c.getCpf() == cpf) {
                do {
                    System.out.println(" Digite o seu cpf para editar");
                    menuEdiçao();
                    op = ler.nextInt();
                    switch (op) {
                     
                        case 1:
                            System.out.println(" Digite o novo nome que voce deseja editar");
                            String nome = ler.next();
                            c.setNome(nome);
                            atualizarDadosNoBancoCliente(c);
                            break;
                        case 2:
                            System.out.println(" Digite o nova idade que voce deseja editar");
                            int idade = ler.nextInt();
                            c.setIdade(idade);
                            atualizarDadosNoBancoCliente(c);
                            break;

                        case 7:
                            System.out.println("saindo.....");
                            break;
                        default:
                            throw new AssertionError();
                    }

                } while (op != 7);
                System.out.println("Dados Cliente editado: " + c);
                return;
            }
        }
        System.out.println("O Cliente nao foi encontrado tente novamente");
    }

    ////area de menus
    public void menuFilme() {
        System.out.println("1 - Cadastrar Filme, Ingresso ");
        System.out.println("2 - Buscar Filme");
        System.out.println("3 - Remover Filme");
        System.out.println("4 - Lista todos os Filmes");
        System.out.println("5 - Busca Cliente");
        System.out.println("6 - Remover Cliente");
        System.out.println("7 - Editar Filme");
        System.out.println("8 - Lista todos os clientes");
        System.out.println("9 - remover ingresso");
        System.out.println("11 - Voltar");
    }

    public void menuPrincipal() {
        System.out.println("=================================================================================");
        System.out.println("======================Bem vindo===================================================");
        System.out.println("===================================================================================");
        System.out.println("1- para area admintrativa do Cinema");
        System.out.println("2 - para area do cliente");
        System.out.println("3 - para sair do sistema");
        System.out.println("===================================================================================");
    }

    public void menuCliente() {
        System.out.println("1 - ralizar cadastro");
        System.out.println("2 - Busca Filme");
        System.out.println("3 - excluir contar");
        System.out.println("4 - reservar Ingresso");
        System.out.println("5 -  cancelar reserva Ingresso ");
        System.out.println("6 - Comprar ingresso e Ver comprovante");
        System.out.println("7 - Lista todos os Filmes Disponivel");
        System.out.println("8 - Editar dados pessoais");
        System.out.println("9 - exibir todos os ingressos diponiveis");
        System.out.println("0 - sair do menu");
    }

}
