/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinema;

/**
 *
 * @author gerso
 */
public class Cliente {
   private String nome;
    private int idade;
    private String email;
 
    private int telefone;
    private int cpf;

    public Cliente() {
        this.nome = ""; 
        this.idade = 0;
        this.email = ""; 
      
        this.telefone = 0; 
        this.cpf = 0; 
    }

    public Cliente( String nome, int idade, String email,  int telefone, int cpf) {
  
        this.nome = nome;
        this.idade = idade;
        this.email = email;
       
        this.telefone = telefone;
        this.cpf = cpf;
    }

   

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getEmail() {
        return email;
    }

   

    public int getTelefone() {
        return telefone;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

   

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    } 

    @Override
    public String toString() {
        return "Cliente{" + "nome=" + nome + ", idade=" + idade + ", email=" + email + ", telefone=" + telefone + ", cpf=" + cpf + '}';
    }
    
    
}
