package com.lucasignacio.repositorioversao;

//package com.lucasignacio.repositorioareatec;
//
//// 1. Importar a classe do MigLayout
//import net.miginfocom.swing.MigLayout;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import javax.swing.*;
//import javax.swing.text.AbstractDocument;
//import javax.swing.text.AttributeSet;
//import javax.swing.text.BadLocationException;
//import javax.swing.text.DocumentFilter;
//
//// O nome da classe foi mantido como Teste, conforme o seu código
//public class Teste extends JFrame {
//
//    private JTextField campoNumeroChamado;
//    private JComboBox<String> comboboxCliente;
//    private JComboBox<String> comboboxCidade;
//    private JTextField campoArquivoVersao;
//    private JButton btnArquivoVersao;
//    private JButton btnGravarArquivo;
//    ClienteVersao clienteVersao = new ClienteVersao();
//    ClienteDAO dao = new ClienteDAO();
//
//    public Teste() {
//        setTitle("Upload Areatec");
//        setSize(720, 600);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//        
//         // 1. CRIAÇÃO DO CABEÇALHO COM IMAGEM E TÍTULO
//        // =======================================================================
//        JPanel painelCabecalho = new JPanel(new BorderLayout(20, 0)); // Painel para o topo
//
//        // Carrega a imagem da logo
//        // Lembre-se que o caminho "src/..." funciona ao rodar na IDE, mas pode falhar em um JAR.
//        ImageIcon imageIcon = new ImageIcon("src/main/java/drawable/android_logo1.png");
//        Image imageLogo = imageIcon.getImage();
//        Image newimg = imageLogo.getScaledInstance(120, 80, java.awt.Image.SCALE_SMOOTH);
//        imageIcon = new ImageIcon(newimg);
//
//        JLabel imageLabel = new JLabel(imageIcon);
//        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
//        painelCabecalho.add(imageLabel, BorderLayout.WEST); // Adiciona a imagem à ESQUERDA do painel do cabeçalho
//
//        // Cria o título
//        JLabel tituloUpload = new JLabel("Importar versão Android", SwingConstants.CENTER);
//        tituloUpload.setFont(new Font("Arial", Font.BOLD, 24));
//        tituloUpload.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 110));
//        painelCabecalho.add(tituloUpload, BorderLayout.CENTER); // Adiciona o título ao CENTRO do painel do cabeçalho
//
//        // Adiciona o painel do cabeçalho inteiro na parte NORTE da janela
//        add(painelCabecalho, BorderLayout.NORTH);
//
//        // =======================================================================
//        // AQUI COMEÇA A MÁGICA DO MIGLAYOUT
//        // =======================================================================
//
//        // =======================================================================
//    // 2. LAYOUT PRINCIPAL COM MIGLAYOUT
//    // =======================================================================
//    JPanel painelUpload = new JPanel(new MigLayout(
//            "wrap 4, insets 10 40 10 40",
//            "[align left][grow, fill]10lp[align left][grow, fill]"
//    ));
//
//    JPanel painelWrapper = new JPanel(new BorderLayout());
//    painelWrapper.add(painelUpload, BorderLayout.NORTH);
//    add(painelWrapper, BorderLayout.CENTER);
//
//    // --- LINHA 0 ---
//    painelUpload.add(new JLabel("Cliente:"));
//    comboboxCliente = new JComboBox<>();
//    painelUpload.add(comboboxCliente, "span");    
//    
//    carregarClientes(); 
//    // --- LINHA 1 ---
//    painelUpload.add(new JLabel("Cidade:"));
//    comboboxCidade = new JComboBox<>();
//    painelUpload.add(comboboxCidade, "span");
//
//    // --- LINHA 2 ---
//    painelUpload.add(new JLabel("N° Chamado:"));
//    campoNumeroChamado = new JTextField();
//    painelUpload.add(campoNumeroChamado,  "span, wrap");
//
//    btnArquivoVersao = new JButton("Arquivo");
//    btnArquivoVersao.setPreferredSize(new Dimension(100,20));
//    painelUpload.add(btnArquivoVersao, "width pref!");
//
//    campoArquivoVersao = new JTextField();
//    campoArquivoVersao.setEditable(false);
//    campoArquivoVersao.setBackground(Color.WHITE);
//    painelUpload.add(campoArquivoVersao, "span");
//
//    // --- LINHA 3 ---
//    btnGravarArquivo = new JButton("Gravar");
//    btnGravarArquivo.setPreferredSize(new Dimension(100,20));
//    painelUpload.add(btnGravarArquivo, "gaptop 5, width pref!");
//
//    // --- Adicionando os Listeners ---
//    comboboxCliente.addActionListener(this::verificarComboBox);
//    btnArquivoVersao.addActionListener(this::selecionarArquivo);
//    btnGravarArquivo.addActionListener(this::gravarArquivo);
//    aplicarFiltroNumerico(campoNumeroChamado);
//}
//
//
//     private void carregarClientes() {
//    // Adiciona uma instrução inicial como primeiro item
//    comboboxCliente.addItem("Selecione um Cliente...");
//
//    // Busca os clientes do banco de dados
//    java.util.List<String> clientes = dao.listarClientes();
//
//    // Adiciona cada cliente da lista ao ComboBox
//    for (String cliente : clientes) {
//        comboboxCliente.addItem(cliente);
//    }
//    
//    }
//
//    private void verificarComboBox(ActionEvent e) {
//
//        String selecionadoCliente = (String) comboboxCliente.getSelectedItem();
//
//        // Limpa o comboBox de cidade antes de preencher com novas cidades
//        comboboxCidade.removeAllItems();
//
//       
//        java.util.List<String> cidades = dao.listarCidadesPorCliente(selecionadoCliente);
//
//        comboboxCidade.removeAllItems();
//        for (String cidade : cidades) {
//        comboboxCidade.addItem(cidade);
//        }
//    }
//    
//   // Método que é chamado quando o botão "Arquivo" é clicado
//    private void selecionarArquivo(ActionEvent e) {
//        JFileChooser fileChooser = new JFileChooser();// Cria uma nova janela de seleção de arquivos    	
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);  // Define que o JFileChooser só permitirá a seleção de arquivos (não pastas)
//
//        // Abre o diálogo de seleção de arquivo e armazena o resultado (se o usuário clicou OK ou Cancelar)
//        int result = fileChooser.showOpenDialog(Teste.this);
//
//        // Verifica se o usuário clicou em "Abrir" (ou seja, selecionou um arquivo com sucesso)
//        if (result == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile(); // Obtém o arquivo selecionado pelo usuário
//            campoArquivoVersao.setText(selectedFile.getAbsolutePath()); // Mostra o caminho completo do arquivo selecionado no campo de texto
//        }
//
//    }
//
//    // Método que é chamado quando o botão "Gravar" é clicado
//    private void gravarArquivo(ActionEvent e) {
//        String numeroChamado = campoNumeroChamado.getText(); // pega o texto do número do chamado
//        String caminhoOrigem = campoArquivoVersao.getText(); // pega o caminho do campo de texto
//        String selecionadoCliente = (String) comboboxCliente.getSelectedItem(); // pega o combobox do cliente      	 
//        String selecionadoCidade = (String) comboboxCidade.getSelectedItem(); // pega o combobox do cidade    	 
//
//        if (numeroChamado == null || numeroChamado.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Informe o número do chamado.", "Erro", JOptionPane.ERROR_MESSAGE);
//            return;
//        } else {
//            ClienteVersao clienteVersao = new ClienteVersao();
//            clienteVersao.setNumeroChamado(numeroChamado);
//        }
//        
//        dao.gravarChamado(numeroChamado, selecionadoCidade, selecionadoCliente);
//
//        if (caminhoOrigem == null || caminhoOrigem.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Selecione um arquivo de gravar.", "Erro", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        File arquivoOrigem = new File(caminhoOrigem);
//
//        if (!arquivoOrigem.exists()) {
//            JOptionPane.showMessageDialog(this, "Arquivo selecionado não existe!", "Erro", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Define o diretório de destino e monsta o caminho automaticamente
//        String destinoPasta = "C:/RepositorioVersão/" + selecionadoCliente + "/" + selecionadoCidade;
//        File pastaDestino = new File(destinoPasta);
//
//        // Cria a pasta se não existir
//        if (!pastaDestino.exists()) {
//            pastaDestino.mkdirs();
//        }
//
//        Path destinoFinal = Paths.get(destinoPasta, arquivoOrigem.getName());
//        try {
//            Files.copy(arquivoOrigem.toPath(), destinoFinal, StandardCopyOption.REPLACE_EXISTING);
//            JOptionPane.showMessageDialog(this, "Arquico gravado com sucesso em:\n" + destinoFinal);
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(this, "Erro ao gravar o arquivo:\n!" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//            ex.printStackTrace();
//        }
//    }
//
//    private void aplicarFiltroNumerico(JTextField campo) {
//        // Converte o documento do JTextField para AbstractDocument, que permite aplicar um filtro personalizado
//        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
//
//            // Este método é chamado quando o usuário tenta inserir texto (ex: digitando ou colando)
//            @Override
//            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
//                // Verifica se a string inserida contém apenas dígitos (0 a 9)
//                if (string.matches("\\d+")) {
//                    // Se for numérica, permite a inserção
//                    super.insertString(fb, offset, string, attr);
//                }
//                // Caso contrário, ignora a inserção (não insere nada)
//            }
//
//            // Este método é chamado quando o usuário tenta substituir parte do texto (ex: seleciona e digita algo novo)
//            @Override
//            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
//                // Verifica se a string substituta é composta apenas por dígitos
//                if (string.matches("\\d+")) {
//                    // Se for numérica, permite a substituição
//                    super.replace(fb, offset, length, string, attr);
//                    // Caso contrário, ignora a substituição
//                }
//            }
//
//            // Este método é chamado quando o usuário apaga texto (ex: tecla Backspace ou Delete)
//            @Override
//            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
//                // Sempre permite remoção, pois não afeta a integridade numérica do campo
//                super.remove(fb, offset, length);
//            }
//        });
//    }
//
//}
