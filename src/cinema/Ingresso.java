/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinema;

import static cinema.GerenteCinema.password;
import static cinema.GerenteCinema.url;
import static cinema.GerenteCinema.user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author gerso
 */
public class Ingresso {
     private float preco;
     private  int id;
    private boolean reservado;
   private  String date;
   private String hora;
      ArrayList<Ingresso> lista2;
    public Ingresso(float preco,  String date, int id, boolean reservado, String hora) {
        this.preco = preco;
        this.id = id;
        this.hora = hora;
        this.date = date;
        this.reservado = false;
      lista2 = new ArrayList<>();
        
    }

    public String getHora() {
        return hora;
    }
// Crie e inicialize o objeto Filme
GerenteCinema gc = new GerenteCinema();
    public Ingresso() {
       lista2 = new ArrayList<>();
    }



    public float getPreco() {
        return preco;
    }

    public int getId() {
        return id;
    }

    public boolean isReservado() {
        return reservado;
    }
    static String url = "jdbc:postgresql://localhost/Cinema";
    static String user = "postgres";
    static String password = "123";
    public void InserirIngresso(float preco,  String date, int id, boolean reservado, String hora) {
    try (Connection conec = DriverManager.getConnection(url, user, password)) {
        String query = "INSERT INTO ingresso(preco, id, data, reserva, hora) VALUES (?, ?, ?, ?)";
        try (PreparedStatement state = conec.prepareStatement(query)) {
            state.setFloat(1, preco); 
                state.setInt(2, id);
                    state.setString(3, date);
                      state.setBoolean(4, reservado);
                          state.setString(3, hora);
            state.executeUpdate();
            System.out.println("Ingresso inserido com sucesso!");
        }
    } catch (SQLException e) {
        System.out.println("Erro ao inserir ingresso: " + e.getMessage());
    }
}
    
public void reservarIngressoPorCodigo(int id) {
    if (reservado) {
        System.out.println("O ingresso já foi reservado.");
    } else {
        String url = "jdbc:postgresql://localhost/Cinema";
        String usuario = "postgres";
        String senha = "123";

        try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
            // Iniciar uma transação
            connection.setAutoCommit(false);

            String consulta = "SELECT id, preco, reserva FROM ingresso WHERE id = ?";
            String atualizacaoSql = "UPDATE ingresso SET reserva= ? WHERE id = ?";
            PreparedStatement consultaStatement = connection.prepareStatement(consulta);
            PreparedStatement atualizacaoStatement = connection.prepareStatement(atualizacaoSql);

            consultaStatement.setInt(1, id);

            ResultSet resultSet = consultaStatement.executeQuery();

            if (resultSet.next()) {
                int idIngresso = resultSet.getInt("id");
                float preco = resultSet.getFloat("preco");
                boolean isReservado = resultSet.getBoolean("reserva");

                if (!isReservado) {
                    atualizacaoStatement.setBoolean(1, true);
                    atualizacaoStatement.setInt(2, id);
                    int rowsAffected = atualizacaoStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        // Confirmar a transação
                        connection.commit();

                        reservado = true;
                        System.out.println("Ingresso com ID " + idIngresso + " reservado com sucesso.");
                        System.out.println("Preço do ingresso: " + preco);
                     
                    } else {
                        System.out.println("Erro ao tentar reservar ingresso.");
                    }
                } else {
                    System.out.println("Erro: O ingresso já está reservado.");
                }
            } else {
                System.out.println("Erro: ID inválido, ingresso não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar reservar ingresso: Problema de conexão com o banco de dados.");
        }
    }
}
    public void cancelarReserva(int id) {
    String url = "jdbc:postgresql://localhost/Cinema";
    String usuario = "postgres";
    String senha = "123";

    try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
        // Iniciar uma transação
        connection.setAutoCommit(false);

        String consulta = "SELECT * FROM ingresso WHERE id = ?";
        String atualizacaoSql = "UPDATE ingresso SET reserva = ? WHERE id = ?";
        PreparedStatement consultaStatement = connection.prepareStatement(consulta);
        PreparedStatement atualizacaoStatement = connection.prepareStatement(atualizacaoSql);

        consultaStatement.setInt(1, id);

        ResultSet resultSet = consultaStatement.executeQuery();

        if (resultSet.next()) {
            boolean isReservado = resultSet.getBoolean("reserva");

            if (isReservado) {
                atualizacaoStatement.setBoolean(1, false);
                atualizacaoStatement.setInt(2, id);
                int rowsAffected = atualizacaoStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Confirmar a transação
                    connection.commit();
                    reservado = false;
                    System.out.println("Reserva do ingresso com ID " + id + " cancelada com sucesso.");
                } else {
                    System.out.println("Erro ao tentar cancelar a reserva.");
                }
            } else {
                System.out.println("Erro: O ingresso não está reservado.");
            }
        } else {
            System.out.println("Erro: ID inválido, ingresso não encontrado.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erro ao tentar cancelar a reserva: Problema de conexão com o banco de dados.");
    }
}
    
public void comprarIngresso(int id) throws SQLException {
    carregarIngresso();

    try (Connection connection = DriverManager.getConnection(url, user, password)) {
        // Defina a consulta SQL para recuperar informações do ingresso
        String consultaSql = "SELECT * FROM Ingresso WHERE id = ?";
        
        try (PreparedStatement consultaStatement = connection.prepareStatement(consultaSql)) {
            consultaStatement.setInt(1, id);
            ResultSet resultSet = consultaStatement.executeQuery();

            if (resultSet.next()) {
                int ingressoId = resultSet.getInt("id"); // Substitua "id" pelo nome correto da coluna na tabela
                float ingressoPreco = resultSet.getFloat("preco");
                String dataCompra = resultSet.getString("data");
               String hora = resultSet.getString("hora");

                if (!reservado) {
                    System.out.println("==============compra ingresso=============================================");
                    System.out.println("Ingresso comprado com sucesso!");
                    System.out.println("ID do ingresso: " + ingressoId);
                    System.out.println("Preço do ingresso: R$" + ingressoPreco);
                    System.out.println("Data da Compra: " + dataCompra);
                     System.out.println("hora da Compra: " + hora);
                     gerarComprovante(id);
                 
                   //reservado = true;
                } else {
                    System.out.println("Erro: O ingresso esta reservado.");
                }
            } else {
                System.out.println("Erro: Ingresso não encontrado com o ID fornecido.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erro ao acessar o banco de dados.");
    }
}
      
      public void gerarComprovante(int codigo) {
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
        // Defina a consulta SQL para recuperar informações do ingresso
        String consultaSql = "SELECT * FROM Ingresso WHERE id = ?";
        
        try (PreparedStatement consultaStatement = connection.prepareStatement(consultaSql)) {
            consultaStatement.setInt(1, codigo);
            ResultSet resultSet = consultaStatement.executeQuery();

            if (resultSet.next()) {
                int ingressoId = resultSet.getInt("id"); // Substitua "id" pelo nome correto da coluna na tabela
               
                float ingressoPreco = resultSet.getFloat("preco");
                String dataCompra = resultSet.getString("data");
                 String hora = resultSet.getString("hora");
               boolean reservado = resultSet.getBoolean("reserva");
                System.out.println("==================Comprovante da compra=============================================");
                System.out.println("ID do Ingresso: " + ingressoId);
                System.out.println("Preço do Ingresso: R$" + ingressoPreco);
                System.out.println("Data da Compra: " + dataCompra);
                  System.out.println("hora da Compra: " + hora);
                System.out.println("Status da Reserva: " + (reservado ? "Reservado" : "Não Reservado"));

                System.out.println("Obrigado por sua compra!");
                System.out.println("=====================================================================================");
            } else {
                System.out.println("Ingresso não encontrado para o código do filme: " + codigo);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Erro ao acessar o banco de dados.");
    }
}



    // Método para remover um ingresso do banco de dados e do ArrayList
   
public void AdicionarIngresso(Ingresso in) {
    for (Ingresso i : this.lista2) {
        if (in.getId()== i.getId()) {
            System.out.println("Erro: Não é possível cadastrar ingresso com ID iguais");
            return;
        }
    }

    // Criar a conexão com o banco de dados
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
        // Definir a consulta SQL para inserção
        String sql = "INSERT INTO Ingresso(preco,  id, data, hora) VALUES (?, ?, ? , ?)";
        
        // Criar um PreparedStatement para evitar injeção de SQL
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFloat(1, in.getPreco());
           
            preparedStatement.setInt(2, in.getId());
            preparedStatement.setString(3, in.date);
            preparedStatement.setString(4, in.hora);
            // Executar a consulta de inserção
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Ingresso adicionado com sucesso ao banco de dados.");
                // Adicione o ingresso à lista após a inserção bem-sucedida
                this.lista2.add(in);
            } else {
                System.out.println("Erro: Não foi possível adicionar o ingresso ao banco de dados.");
            }
        }
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        System.out.println("Erro: Falha na conexão com o banco de dados.");
    }
}

public void RemoverIngresso(int codico) throws java.sql.SQLException {
        for (Ingresso in : lista2) {
            if (in.getId() == codico) {
                try (Connection connection = DriverManager.getConnection(url, user, password)) {
                    String sql = "DELETE FROM Ingresso WHERE id = ?";
                    
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setInt(1, codico);
                        preparedStatement.executeUpdate();
                    }
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                    System.err.println("Erro: Falha ao remover ingresso do banco de dados.");
                }
                
                lista2.remove(in); // Remove o filme da lista
                System.out.println("O ingresso foi removido com sucesso: " + in);
                return;
            }
        }
        System.out.println("id não encontrado. Tente novamente.");
    }
public void carregarIngresso() throws java.sql.SQLException {
     lista2.clear(); // Limpa a lista antes de adicionar filmes
    Set<Ingresso> ingressosSet = new HashSet<>();

    try (Connection connection = DriverManager.getConnection(url, user, password)) {
        String sql = "SELECT preco, id, data, reserva, hora FROM ingresso";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                float preco = resultSet.getFloat("preco");
                int id = resultSet.getInt("id");
                String data = resultSet.getString("data");
                boolean reservados = resultSet.getBoolean("reserva");
                 String hora = resultSet.getString("hora");
 lista2.clear(); // Limpa a lista antes de adicionar filmes
                Ingresso ingresso = new Ingresso(preco,  data, id,reservados,hora);
                ingressosSet.add(ingresso);
            }
        }
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
        System.err.println("Erro: Falha na conexão com o banco de dados.");
    }

    lista2.addAll(ingressosSet);
}

   
   
   
   
   public void listaTodosingresso(){
        for (Ingresso in : lista2) {
            
            System.out.println(in);
           
        }
    }

    @Override
    public String toString() {
        return "Ingresso{" + "preco=" + preco + ", id=" + id + ", reservado=" + reservado  + ", date=" + date  + ", hora="+hora+ '}';
    }

}
