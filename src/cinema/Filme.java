/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author gerso
 */
public class Filme {
     private String nome;
    private String genero;
    private String duracao;
    private int codico;
    private String date;

    public Filme(String nome, String genero, String duracao, int codico) {
        this.nome = nome;
        this.genero = genero;
        this.duracao = duracao;
        this.codico = codico;
     
    }

    public Filme() {
        GerenteCinema f = new GerenteCinema();
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public String getDuracao() {
        return duracao;
    }

    public int getCodico() {
        return codico;
    }

    public String getDate() {
        return date;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public void setCodico(int codico) {
        this.codico = codico;
    }

    public void setDate(String date) {
        this.date = date;
    }
    static String url = "jdbc:postgresql://localhost/Cinema";
    static String user = "postgres";
    static String password = "123";
 public  void listarFilmesPorIngresso(int idIngresso) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT f.nomeFilme " +
                           "FROM tem t " +
                           "JOIN filme f ON t.fk_filme_codicoFilme = f.codicoFilme " +
                           "WHERE t.fk_ingresso_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idIngresso);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nomeFilme = resultSet.getString("nomeFilme");
                System.out.println("Nome do Filme: " + nomeFilme);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "Filme{" + "nome=" + nome + ", genero=" + genero + ", duracao=" + duracao + ", codico=" + codico  + '}';
    }
    
    
}
