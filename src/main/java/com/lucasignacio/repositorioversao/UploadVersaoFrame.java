package com.lucasignacio.repositorioversao;

// 1. Importar a classe do MigLayout
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class UploadVersaoFrame extends JFrame {

    private JTextField campoNumeroChamadoUpload;
    private JComboBox<String> comboboxClienteUpload;
    private JComboBox<String> comboboxCidadeUpload;
    private JTextField campoArquivoVersaoUpload;
    private JTextField campoObservacaoChamadoUpload;
    private JButton btnArquivoVersaoUpload;
    private JButton btnCarregarArquivoUpload;
    private JButton btnMudarParaDownloadVersaoChamado;
    private JButton btnMudarParaDownloadVersaoCidade;
    ClienteDAO dao = new ClienteDAO();

    public UploadVersaoFrame() {
        setTitle("Repositório Versão Areatec V. 1.1");
        setSize(770, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

         // Define o ícone da janela (usar PNG no lugar de ICO)
        java.net.URL iconURL = getClass().getResource("/drawable/android_64.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Ícone não encontrado!");
        }
        
        // 1. CRIAÇÃO DO CABEÇALHO COM IMAGEM E TÍTULO
        // =======================================================================
        JPanel painelCabecalho = new JPanel(new BorderLayout(20, 0)); // Painel para o topo      

        // Carrega a imagem da logo
        java.net.URL imageLogoURL = getClass().getResource("/drawable/android_logo.png");
        ImageIcon imageIcon = new ImageIcon(imageLogoURL);
        Image imageLogo = imageIcon.getImage();
        Image newimg = imageLogo.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);

         //Carrega a imagem do button arquivo
        java.net.URL imagePastaURL = getClass().getResource("/drawable/pasta_image1.png");
        ImageIcon imageIconButtonArquivo = new ImageIcon(imagePastaURL);
        Image imageButtonArquivo = imageIconButtonArquivo.getImage();
        Image newimgbuttonArquivo = imageButtonArquivo.getScaledInstance(18, 18, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonArquivo = new ImageIcon(newimgbuttonArquivo);
        
        //Carrega a imagem do button importar
        java.net.URL imageImportarURL = getClass().getResource("/drawable/botao_de_upload.png");
        ImageIcon imageIconButtonImportar = new ImageIcon(imageImportarURL);
        Image imageButtonImportar = imageIconButtonImportar.getImage();
        Image newimgbuttonImportar = imageButtonImportar.getScaledInstance(18, 16, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonImportar = new ImageIcon(newimgbuttonImportar);
        
        //Carrega a imagem do button baixar chamado
        java.net.URL imageBaixarChamadoURL = getClass().getResource("/drawable/download_direto_chamado.png");
        ImageIcon imageIconButtonBaixarChamado = new ImageIcon(imageBaixarChamadoURL);
        Image imageButtonBaixarChamado = imageIconButtonBaixarChamado.getImage();
        Image newimgbuttonBaixarChamado = imageButtonBaixarChamado.getScaledInstance(26, 26, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonBaixarChamado = new ImageIcon(newimgbuttonBaixarChamado);
        
        //Carrega a imagem do button baixar cidade
        java.net.URL imageBaixarCidadeURL = getClass().getResource("/drawable/download_direto_cidade.png");
        ImageIcon imageIconButtonBaixarCidade = new ImageIcon(imageBaixarCidadeURL);
        Image imageButtonBaixarCidade = imageIconButtonBaixarCidade.getImage();
        Image newimgbuttonBaixarCidade = imageButtonBaixarCidade.getScaledInstance(26, 26, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonBaixarCidade = new ImageIcon(newimgbuttonBaixarCidade);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        painelCabecalho.add(imageLabel, BorderLayout.WEST);

        // Cria o título
        JLabel tituloUpload = new JLabel("Importar versão Android", SwingConstants.CENTER);
        tituloUpload.setFont(new Font("Arial", Font.BOLD, 24));
        tituloUpload.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 110));
        painelCabecalho.add(tituloUpload, BorderLayout.CENTER);

        // Adiciona o painel do cabeçalho inteiro na parte NORTE da janela
        add(painelCabecalho, BorderLayout.NORTH);


        // 2. LAYOUT PRINCIPAL COM MIGLAYOUT
        // =======================================================================
        JPanel painelUpload = new JPanel(new MigLayout(
                "wrap 4, insets 10 20 10 20",
                "[align left][grow, fill]10lp[grow, fill][align left]"
        ));

        add(painelUpload, BorderLayout.CENTER);

        // --- LINHA 0 ---
        painelUpload.add(new JLabel("Selecione o Cliente:"));
        comboboxClienteUpload = new JComboBox<>();
        comboboxClienteUpload.setPreferredSize(new Dimension(250, 22));
        painelUpload.add(comboboxClienteUpload, "width pref!,");

        carregarClientes();        
        
        btnArquivoVersaoUpload = new JButton("Arquivo", imageIconButtonArquivo);
        btnArquivoVersaoUpload.setPreferredSize(new Dimension(150, 25));
        painelUpload.add(btnArquivoVersaoUpload, "width pref!, align center, span 2, wrap");
        
        // --- LINHA 1 ---
        painelUpload.add(new JLabel("Selecione a Cidade:"));
        comboboxCidadeUpload = new JComboBox<>();
        comboboxCidadeUpload.setPreferredSize(new Dimension(250, 22));
        painelUpload.add(comboboxCidadeUpload, "width pref!,");
        
        campoArquivoVersaoUpload = new JTextField();
        campoArquivoVersaoUpload.setEditable(false);
        campoArquivoVersaoUpload.setBackground(UIManager.getColor("Panel.background"));
        campoArquivoVersaoUpload.setPreferredSize(new Dimension(280, 22));
        painelUpload.add(campoArquivoVersaoUpload, "width pref!, align center, span 2, wrap");

        // --- LINHA 2 ---
        painelUpload.add(new JLabel("N° Chamado:"));
        campoNumeroChamadoUpload = new JTextField();
        campoNumeroChamadoUpload.setPreferredSize(new Dimension(250, 22));
        painelUpload.add(campoNumeroChamadoUpload, "width pref!, wrap"); 
        
        painelUpload.add(new JLabel("Observação:"),"align left");
        campoObservacaoChamadoUpload = new JTextField();
        campoObservacaoChamadoUpload.setPreferredSize(new Dimension(250,22));
        painelUpload.add(campoObservacaoChamadoUpload, "width pref!, wrap");
        

        

        // --- LINHA 3 ---
        btnCarregarArquivoUpload = new JButton("Carregar", imageIconButtonImportar);
        btnCarregarArquivoUpload.setPreferredSize(new Dimension(100, 25));
        painelUpload.add(btnCarregarArquivoUpload, "gaptop 5, width pref!, wrap");        
        

        // =========================================================================
        // 3. CRIAÇÃO DO RODAPÉ PARA O BOTÃO DE NAVEGAÇÃO
        // =========================================================================       
        
        painelUpload.add(new JLabel(), "pushy, wrap");
        
        // Bloco Novo para alinhar os botões
        btnMudarParaDownloadVersaoChamado = new JButton("Baixar Versão Por Chamado", imageIconButtonBaixarChamado);
        btnMudarParaDownloadVersaoChamado.setPreferredSize(new Dimension(220, 30));
        
        

        btnMudarParaDownloadVersaoCidade = new JButton("Baixar Versão Por Cidade", imageIconButtonBaixarCidade);
        btnMudarParaDownloadVersaoCidade.setPreferredSize(new Dimension(220, 30));

        // Adiciona os dois botões na mesma linha, centralizados como um grupo
        painelUpload.add(btnMudarParaDownloadVersaoChamado, "span 2,  align left, gaptop 10, split 2 ");
        painelUpload.add(new JLabel("Versão 1.1 - 18/08/2025"),"gapleft 80, align left");
        painelUpload.add(btnMudarParaDownloadVersaoCidade, "width pref!, align right, gaptop 10, span 2");

      
       

        // --- Adicionando os Listeners ---
        comboboxClienteUpload.addActionListener(this::verificarComboBox);
        btnArquivoVersaoUpload.addActionListener(this::selecionarArquivo);
        btnCarregarArquivoUpload.addActionListener(this::gravarArquivo);
        btnMudarParaDownloadVersaoChamado.addActionListener(this::mudarTelaDownloadChamadoVersao);
        btnMudarParaDownloadVersaoCidade.addActionListener(this::mudarTelaDownloadCidadeVersao);
        aplicarFiltroNumerico(campoNumeroChamadoUpload);
    }


    private void carregarClientes() {
        // Busca os clientes do banco de dados
        java.util.List<String> clientes = dao.listarClientes();

        // Adiciona cada cliente da lista ao ComboBox
        for (String cliente : clientes) {
            comboboxClienteUpload.addItem(cliente);
        }
        // Define o índice selecionado como -1 (nenhum)
        comboboxClienteUpload.setSelectedIndex(-1);
    }

    private void verificarComboBox(ActionEvent e) {
        String selecionadoCliente = (String) comboboxClienteUpload.getSelectedItem();

        // Limpa o comboBox de cidade antes de preencher com novas cidades
        comboboxCidadeUpload.removeAllItems();

        if (selecionadoCliente != null) {
            java.util.List<String> cidades = dao.listarCidadesPorCliente(selecionadoCliente);
            for (String cidade : cidades) {
                comboboxCidadeUpload.addItem(cidade);
            }
        }
        // Define o índice selecionado como -1 (nenhum)
        comboboxCidadeUpload.setSelectedIndex(-1);
    }

    // Método que é chamado quando o botão "Arquivo" é clicado
    private void selecionarArquivo(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos APK", "apk");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(UploadVersaoFrame.this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            campoArquivoVersaoUpload.setText(selectedFile.getAbsolutePath());
        }
    }

    // Método que é chamado quando o botão "Gravar" é clicado
    private void gravarArquivo(ActionEvent e) {
        String numeroChamado = campoNumeroChamadoUpload.getText().trim();
        String observacao  = campoObservacaoChamadoUpload.getText().trim();
        String caminhoOrigem = campoArquivoVersaoUpload.getText().trim();
        String selecionadoCliente = (String) comboboxClienteUpload.getSelectedItem();
        String selecionadoCidade = (String) comboboxCidadeUpload.getSelectedItem();

        // Bloco de validações
        if (selecionadoCliente == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selecionadoCidade == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma cidade.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (numeroChamado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o número do chamado.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (observacao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a observação.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (caminhoOrigem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um arquivo para gravar.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        // Grava primeiro o chamado para garantir que ele exista no banco
        dao.gravarChamado(numeroChamado, selecionadoCidade, selecionadoCliente, observacao);

        File arquivoOrigem = new File(caminhoOrigem);
        String nomeArquivo = arquivoOrigem.getName();

        if (!arquivoOrigem.exists()) {
            JOptionPane.showMessageDialog(this, "Arquivo selecionado não existe!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Define o diretório de destino e monta o caminho automaticamente
          String destinoPasta = "C:/RepositorioVersao/" + selecionadoCliente + "/" + selecionadoCidade + "/Chamado#" + numeroChamado;
        //String destinoPasta = "P:/RepositorioVersao/" + selecionadoCliente + "/" + selecionadoCidade + "/Chamado#" + numeroChamado;
        File pastaDestino = new File(destinoPasta);

        // Cria a pasta se não existir
        if (!pastaDestino.exists()) {
            pastaDestino.mkdirs();
        }
        
        File arquivo = new File(destinoPasta, nomeArquivo);

        Path destinoFinal = Paths.get(destinoPasta, nomeArquivo);
        try {
            if (!arquivo.exists()){  
            
            Files.copy(arquivoOrigem.toPath(), destinoFinal, StandardCopyOption.REPLACE_EXISTING);
            
            // Grava o registro do arquivo no banco DEPOIS de copiar com sucesso
            dao.gravarArquivo(selecionadoCliente, selecionadoCidade, numeroChamado, destinoFinal.toString(), nomeArquivo);
            
            JOptionPane.showMessageDialog(this, "Arquivo gravado com sucesso em:\n" + destinoFinal);
            } else {
                JOptionPane.showMessageDialog(this, "Esse Arquivo já existe!", "Erro de gravação de arquivo", JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gravar o arquivo:\n" + ex.getMessage(), "Erro de Cópia", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void aplicarFiltroNumerico(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    private void mudarTelaDownloadChamadoVersao(ActionEvent e) {
        // Garante que a nova janela seja criada na thread de eventos da GUI
        SwingUtilities.invokeLater(() -> {         
                DownloadVersaoChamadoFrame frame = new DownloadVersaoChamadoFrame();
                frame.setVisible(true);           
            dispose(); // Fecha a janela atual de Upload
        });
    }
    
       private void mudarTelaDownloadCidadeVersao(ActionEvent e) {
        // Garante que a nova janela seja criada na thread de eventos da GUI
        SwingUtilities.invokeLater(() -> {         
                DownloadVersaoCidadeFrame frame = new DownloadVersaoCidadeFrame();
                frame.setVisible(true);           
            dispose(); // Fecha a janela atual de Upload
        });
    }
    
}