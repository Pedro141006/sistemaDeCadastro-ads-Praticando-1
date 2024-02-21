import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventoManager {
    private List<Evento> eventos;
    private Scanner scanner;

    public EventoManager() {
        eventos = new ArrayList<>();
        scanner = new Scanner(System.in);
        carregarEventos();
    }

    @SuppressWarnings("unchecked")
    private void carregarEventos() {
        try {
            FileInputStream fis = new FileInputStream("events.data");
            ObjectInputStream ois = new ObjectInputStream(fis);
            eventos = (List<Evento>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void salvarEventos() {
        try {
            FileOutputStream fos = new FileOutputStream("events.data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(eventos);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarEvento() {
    System.out.println("Cadastro de Evento");
    System.out.print("Nome do evento: ");
    String nome = scanner.nextLine();
    System.out.print("Endereço: ");
    String endereco = scanner.nextLine();
    System.out.print("Categoria: ");
    String categoria = scanner.nextLine();
    System.out.print("Horário (yyyy-MM-dd HH:mm): ");
    LocalDateTime horario = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    System.out.print("Descrição: ");
    String descricao = scanner.nextLine();

    Evento evento = new Evento(nome, endereco, categoria, horario, descricao);
    eventos.add(evento);
    System.out.println("Evento cadastrado com sucesso!");
}


    public void consultarEventos() {
        System.out.println("Eventos Cadastrados:");
        for (Evento evento : eventos) {
            System.out.println(evento.getNome() + " - " + evento.getCategoria() + " - " + evento.getHorario());
        }
    }

    public void participarEvento() {
        consultarEventos();
        System.out.print("Digite o nome do evento que deseja participar: ");
        String nomeEvento = scanner.nextLine();
        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nomeEvento)) {
                System.out.println("Você está participando do evento: " + evento.getNome());
                return;
            }
        }
        System.out.println("Evento não encontrado.");
    }

    public void cancelarParticipacao() {
        System.out.println("Eventos que você está participando:");
        System.out.print("Digite o nome do evento que deseja cancelar a participação: ");
        String nomeEvento = scanner.nextLine();
        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nomeEvento)) {
                System.out.println("Participação cancelada no evento: " + evento.getNome());
                return;
            }
        }
        System.out.println("Evento não encontrado.");
    }

    public void ordenarEventosPorHorario() {
        eventos.sort((e1, e2) -> e1.getHorario().compareTo(e2.getHorario()));

        System.out.println("Eventos ordenados por horário:");
        for (Evento evento : eventos) {
            System.out.println(evento.getNome() + " - " + evento.getHorario());
        }
    }

    public void verificarEventoAtual() {
        LocalDateTime agora = LocalDateTime.now();

        for (Evento evento : eventos) {
            if (agora.isEqual(evento.getHorario())) {
                System.out.println("O evento " + evento.getNome() + " está ocorrendo agora.");
                return;
            }
        }

        System.out.println("Nenhum evento está ocorrendo no momento.");
    }

    public void informarEventosPassados() {
        LocalDateTime agora = LocalDateTime.now();

        System.out.println("Eventos que já ocorreram:");

        for (Evento evento : eventos) {
            if (evento.getHorario().isBefore(agora)) {
                System.out.println(evento.getNome() + " - " + evento.getHorario());
            }
        }
    }

    public void fechar() {
        scanner.close();
        salvarEventos();
    }

    public static void main(String[] args) {
        EventoManager manager = new EventoManager();
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
    
        do {
            System.out.println("===== Menu =====");
            System.out.println("1. Cadastrar evento");
            System.out.println("2. Consultar eventos");
            System.out.println("3. Participar de um evento");
            System.out.println("4. Cancelar participação em um evento");
            System.out.println("5. Ordenar eventos por horário");
            System.out.println("6. Verificar se há eventos ocorrendo agora");
            System.out.println("7. Informar eventos passados");
            System.out.println("8. Cadastrar usuário");
            System.out.println("9. Fechar programa");
    
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha
    
            switch (opcao) {
                case 1:
                    manager.cadastrarEvento();
                    break;
                case 2:
                    manager.consultarEventos();
                    break;
                case 3:
                    manager.participarEvento();
                    break;
                case 4:
                    manager.cancelarParticipacao();
                    break;
                case 5:
                    manager.ordenarEventosPorHorario();
                    break;
                case 6:
                    manager.verificarEventoAtual();
                    break;
                case 7:
                    manager.informarEventosPassados();
                    break;
                case 8:
                    cadastrarUsuario();
                    break;
                case 9:
                    manager.fechar();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 9);
    
        scanner.close();
    }
    
    public static void cadastrarUsuario() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Cadastro de Usuário");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        System.out.print("Email: ");
        String email = scanner.nextLine();
    
        @SuppressWarnings("unused")
        Usuario usuario = new Usuario(nome, idade, email);
        System.out.println("Usuário cadastrado com sucesso!");
    }
    
    
}

class Evento implements Serializable {
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

class Usuario implements Serializable {
    private String nome;
    private int idade;
    private String email;

    public Usuario(String nome, int idade, String email) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}