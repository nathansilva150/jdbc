package org;

import java.sql.SQLException;
import java.util.Scanner;

import org.model.Contato;
import org.dao.ContatoDao;

public class Main {

    private static Scanner SC = new Scanner(System.in);
    public static void main(String[] args) {
        inicio();
    }

    public static void inicio() {
        System.out.print("""
                Lista de contato
                1 -> Cadastrar
                2 -> Editar
                3 -> Listar
                4 -> Buscar por nome
                5 -> Retornar lista de contatos por ID
                Escolha uma opção: 
                """);
        int opcao = SC.nextInt();
        SC.nextLine();

        switch (opcao) {
            case 1 :{
                cadastrarContato();
                break;
            }
            case 2 :{
                editarContato();
                break;
            }
            case 3 :{
                listarContato();
                break;
            }
            case 4 :{
                BuscarContatoPorNome();
                break;
            }
            }
        }
    
    public static void cadastrarContato() {
        System.out.print("Digite o nome do contato: ");
        String nome = SC.nextLine();

        System.out.print("Digite o numero do contato: ");
        String numero = SC.nextLine();

        var contato = new Contato(0, nome, numero);

        var contatoDao = new ContatoDao();

        try {
            contatoDao.salvar(contato);
            System.out.println("Contato salvo!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarContato() {
        System.out.print("Digite o ID do contato que deseja editar: ");
        int id = SC.nextInt();
        SC.nextLine();

        boolean existe = false;

        for (Contato contato : ContatoDao.listar()) {
            if (contato.getId() == id) existe = true;
        }

        if (existe) {
            System.out.print("Digite o novo nome do contato: ");
            String nome = SC.nextLine();

            System.out.print("Digite o novo número do contato: ");
            String numero = SC.nextLine();

            var contatoDao = new ContatoDao();

            try {
                contatoDao.editar(id, nome, numero);
                System.out.println("Contato editado!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Contato não encontrado!");
        }

    }

    public static void listarContato() {
        for(Contato contato : ContatoDao.listar()) {
            System.out.println(contato);
        }
    }

    public static void BuscarContatoPorNome() {
        System.out.print("Digite o nome do contato que deseja buscar: ");
        String nome = SC.nextLine();

        boolean existe = false;

        for (Contato contato : ContatoDao.listar()) {
            if (contato.getNome().toLowerCase().contains(nome)) existe = true;
        }
        if (existe) {
            var contatoDao = new ContatoDao();
            System.out.println(contatoDao.BuscarPorNome(nome));
        } else {
            System.out.println("Contato não encontrado!");
        }
    }
}