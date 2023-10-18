/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinema;

/**
 *
 * @author gerso
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Cinemas {
    private String nome;
    private String endereco;
    private String Rua; 
private  String Cidade;
	private  String Bairro;
    private int cnpj;

    public Cinemas(String nome, String Rua, String Cidade, String Bairro, int cnpj) {
        this.nome = nome;
        this.endereco = endereco;
        this.Rua = Rua;
        this.Cidade = Cidade;
        this.Bairro = Bairro;
        this.cnpj = cnpj;
    }

    public Cinemas() {
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getRua() {
        return Rua;
    }

    public String getCidade() {
        return Cidade;
    }

    public String getBairro() {
        return Bairro;
    }

    public int getCnpj() {
        return cnpj;
    }

    @Override
    public String toString() {
        return "Cinemas{" + "nome=" + nome + ", endereco=" + endereco + ", Rua=" + Rua + ", Cidade=" + Cidade + ", Bairro=" + Bairro + ", cnpj=" + cnpj + '}';
    }
      public void exibirTodosOsDadosDoBanco() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Estabeleça a conexão com o banco de dados
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/Cinema", "postgres", "123");

            // Crie uma consulta SQL para buscar todos os ingressos
            String sql = "SELECT * FROM cinema";
            stmt = conn.prepareStatement(sql);

            // Execute a consulta
            rs = stmt.executeQuery();

            // Processar e exibir os resultados
            while (rs.next()) {
                String nome = rs.getString("nomefantaasia");
              String rua = rs.getString("Rua");
               String cidade = rs.getString("cidade");
              String bairro= rs.getString("bairro");
               int cnpj = rs.getInt("cnpj");
             
                // Você pode obter outros atributos do ingresso aqui, se necessário
                System.out.println("nome: " + nome+" rua: "+ rua+ " cidade: "+cidade+ " bairro: "+ bairro + " Cpnj: "+ cnpj);
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Feche as conexões e recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
      
            
    
}
