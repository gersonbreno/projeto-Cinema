/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cinema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.sql.PreparedStatement;
import org.postgresql.util.PSQLException;
/**
 *
 * @author gerso
 */
public class Cinema {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        
        Scanner ler = new Scanner(System.in);
     GerenteCinema gc = new GerenteCinema();
       int  opFilme ;
       int op = 0;
       
 
   
     
     Filme filme = null;
     
     Cliente cliente = null;
   
   

     Ingresso ingresso = null;
     Ingresso in = new Ingresso();
   
 
 
 
 
 
 in.carregarIngresso();;

Filme fil = new Filme();

       //System.out.println(filme);
       
      do {
               try {
                
            
            gc.menuPrincipal(); //menu principal
            
             
        //    
            op = ler.nextInt();
            switch (op) {
                case 1:
                    //usar um do whilhe aqui

                    //varivel de admintrativo
                    String nome = null,
                     genero = null,
                     duracao = null,
                     direto = null;
                    do {

                        gc.menuFilme();   // menu administrativo do cimema
                        opFilme = ler.nextInt();
                        switch (opFilme) {
                            case 1:
                                System.out.println("digite o nome do Filme: ");
                                nome = ler.next();
                                System.out.println("digite o genero do Filme: ");
                                genero = ler.next();
                                System.out.println("digite a Duraçao do Filme em horas e minutos:  ");
                                duracao = ler.next();
                                System.out.println("digite o codico do Filme: ");
                                int codico = ler.nextInt();
                                filme = new Filme(nome, genero, duracao,codico);
                                gc.AdicionarFilmes(filme);

                               
                                  

                                

                                 System.out.println("digite o preco do ingresso: ");
                                float preco = ler.nextFloat();
                                System.out.println("digite o id do ingresso: ");
                                int id = ler.nextInt();
                                System.out.println("digite a data do ingresso: ");
                                String data = ler.next();
                                System.out.println("digite a hora do ingresso: ");
                                String hora = ler.next();
                               ingresso = new Ingresso(preco, data,id, false,hora);
                            
                              
                                ingresso.AdicionarIngresso(ingresso);
                            
                                inserirRelacaoFilmeIngresso(codico, id);
                           
                                break;

                            case 2:
                                System.out.println(" Digite o nome Filme que voce deseja Buscar: ");
                               String nomefilme = ler.next();
                                gc.BuscaFilme(nomefilme);
                                break;
                            case 3:
                                System.out.println(" Digite o codico filme que voce deseja remover: ");
                                  
                                int codicofilme = ler.nextInt();
                           
                                removerFilme(codicofilme);
                                gc.RemoverFilme(codicofilme);
                               
                                break;

                            case 4:
                              
                                gc.carregarFilmes();
                              //  gc.listaTodosFilme();
                                break;
                            case 5:
                                System.out.println(" Digite o cpf do cliente que voce deseja Buscar: ");
                                int cpf = ler.nextInt();
                                gc.BuscaCliente(cpf);
                                break;
                            case 6:
                                System.out.println(" Digite o cpf do cliente que voce deseja remover: ");
                                int cpfrmv = ler.nextInt();
                                gc.RemoverCliente(cpfrmv);
                                break;
                            case 7:
                                System.out.println(" Digite o nome do Filme que voce deseja editar: ");
                                String nomeedit = ler.next();
                                gc.EditaFilme(nomeedit);
                                break;
                            case 8:
                                gc.carregarClientes();
                                gc.listaTodosClintes();

                                break;
                                 case 9:
                              System.out.println(" Digite o ID od ingresso para remover: ");
                                int rmin = ler.nextInt();
                                     removerRelacaoFilmeIngresso(rmin);
                                        in.RemoverIngresso(rmin);
                                 break;
                            case 11:
                                System.out.println("Vontando...");
                                break;
                            default:
                                throw new AssertionError();

                        }
                    } while (opFilme != 11);
                    break;
                case 2:
                    gc.menuCliente();   // menu cliente
                 int   opCliente = ler.nextInt();
                    switch (opCliente) {
                        case 1:
                            System.out.println("Informe seu nome: ");
                            String nomeCL = ler.next();
                            System.out.println("Informe sua idade: ");
                            int idade = ler.nextInt();
                            System.out.println("Informe seu enderco de email: ");
                            String email = ler.next();
                            System.out.println("Informe seu telefone: ");
                            int telefone = ler.nextInt();
                            System.out.println("Informe seu CPF: ");
                            int cpf = ler.nextInt();
                           cliente = new Cliente(nomeCL, idade, email, telefone, cpf);
                            gc.CadastrarCliente(cliente);

                            break;

                        case 2:
                            System.out.println(" Digite o codico do Filme que voce deseja Buscar: ");
                              String nomefil = ler.next();
                              
                            gc.BuscaFilme(nomefil);
                            break;

                        case 3:
                            System.out.println(" Digite seu cpf para confirma a exclusao da conta: ");
                            int cpfrmv = ler.nextInt();
                            gc.RemoverCliente(cpfrmv);

                            break;

                        case 4:
                     
                               System.out.println("digite o id do ingresso");
                               int idIngresso = ler.nextInt();
                            in.reservarIngressoPorCodigo(idIngresso);
                         
           
                           
                           

                            break;
                        case 5:
                           
                             
                                  System.out.println("digite o id do ingresso para cancelar a reserva");
                               int idIngressos = ler.nextInt();
                            in.cancelarReserva(idIngressos);
      
                           
                          
                            break;

                        case 6:
                          
                             System.out.println(" Digite o id do ingresso para comprar: ");
                          int  compra = ler.nextInt();
                            
                            in.comprarIngresso(compra);
                            obterNomeEGeneroDoFilme(compra);
                            break;

                       
                            
                           
                         

                        case 7:
                           gc.carregarFilmes();
                        
                            break;
                        case 8:
                            System.out.println("Informe o seu cpf Para editar sua informçoes");
                            int cpfinf = ler.nextInt();
                                 gc.EditarCliente(cpfinf);
                         
            
                            break;
                        case 9:
                            
                            in.carregarIngresso();
                           in.listaTodosingresso();
                            break;
                        default:
                            throw new AssertionError();
                    }
                    break;
               
                    case 3:
                    System.out.println("saindo...");
                    break;
                default:
                    throw new AssertionError();
            } 
} catch (ArithmeticException | InputMismatchException | ArrayIndexOutOfBoundsException | AssertionError | NullPointerException | PSQLException e) {
    System.out.println("erro ao inserir dados tente novamente");
                ler.nextLine();
            }
        } while (op != 3);
      
      
      
      
      

       
     
    }
    static String url = "jdbc:postgresql://localhost/Cinema";
    static String user = "postgres";
    static String password = "123";
 public static void inserirRelacaoFilmeIngresso(int idFilme, int idIngresso) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO tem (fk_filme_codicoFilme, fk_ingresso_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idFilme);
            preparedStatement.setInt(2, idIngresso);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Relação Filme-Ingresso inserida com sucesso.");
            } else {
                System.out.println("Falha ao inserir a relação Filme-Ingresso.");
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public static void obterNomeEGeneroDoFilme(int idIngresso) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT f.nomeFilme, f.genero " +
                           "FROM tem t " +
                           "JOIN filme f ON t.fk_filme_codicoFilme = f.codicoFilme " +
                           "WHERE t.fk_ingresso_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idIngresso);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nomeFilme = resultSet.getString("nomeFilme");
                String genero = resultSet.getString("genero");
                System.out.println("============dados do filme===========================================");
                System.out.println("Nome do Filme: " + nomeFilme);
                System.out.println("Gênero do Filme: " + genero);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void removerRelacaoFilmeIngresso(int idIngresso) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "DELETE FROM tem WHERE  fk_ingresso_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idIngresso);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Relação Filme-Ingresso removida com sucesso.");
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static void removerFilme(int codicofilme) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "DELETE FROM tem WHERE  fk_filme_codicofilme = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, codicofilme);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Relação Filme removida com sucesso.");
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
