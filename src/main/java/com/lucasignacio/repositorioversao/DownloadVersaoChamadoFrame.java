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
import java.nio.file.attribute.FileTime;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DownloadVersaoChamadoFrame extends JFrame {
    
    private JTextField campoNumeroChamadoDownload;
    private JComboBox<String> comboboxClienteDownload;
    private JComboBox<String> comboboxCidadeDownload;
    private JTextField campoArquivoVersaoDownload;
    private JButton btnPesquisarVersao;
    private JButton btnGravarArquivoDownload;    
    private JButton btnMudarParaUploadVersao;
    private JButton btnMudarParaDownloadVersaoCidade;
    
    ClienteDAO dao = new ClienteDAO();
    
    public DownloadVersaoChamadoFrame(){
    
    setTitle("Repositório Versão Areatec V. 1.1");
        setSize(770, 600);
        setLocationRelativeTo(null);
        setResizable(false);
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
        
        //Carrega a imagem do button baixar
        java.net.URL imageGravarURL = getClass().getResource("/drawable/botao_de_download.png");
        ImageIcon imageIconButtonBaixar = new ImageIcon(imageGravarURL);
        Image imageButtonBaixar = imageIconButtonBaixar.getImage();
        Image newimgButtonBaixar = imageButtonBaixar.getScaledInstance(18, 16, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonBaixar = new ImageIcon(newimgButtonBaixar);
        
        //Carrega a imagem do button baixar cidade
        java.net.URL imageBaixarCidadeURL = getClass().getResource("/drawable/download_direto_cidade.png");
        ImageIcon imageIconButtonBaixarCidade = new ImageIcon(imageBaixarCidadeURL);
        Image imageButtonBaixarCidade = imageIconButtonBaixarCidade.getImage();
        Image newimgbuttonBaixarCidade = imageButtonBaixarCidade.getScaledInstance(26, 26, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonBaixarCidade = new ImageIcon(newimgbuttonBaixarCidade);
        
        //Carrega a imagem do button importar
        java.net.URL imageImportarURL = getClass().getResource("/drawable/upload_arquivo.png");
        ImageIcon imageIconButtonImportar = new ImageIcon(imageImportarURL);
        Image imageButtonImportar = imageIconButtonImportar.getImage();
        Image newimgbuttonImportar = imageButtonImportar.getScaledInstance(26, 26, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonImportar = new ImageIcon(newimgbuttonImportar);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        painelCabecalho.add(imageLabel, BorderLayout.WEST); // Adiciona a imagem à ESQUERDA do painel do cabeçalho

        // Cria o título
        JLabel tituloDownload = new JLabel("Baixar Versão Android Por Chamado ", SwingConstants.CENTER);
        tituloDownload.setFont(new Font("Arial", Font.BOLD, 24));
        tituloDownload.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 110));
        painelCabecalho.add(tituloDownload, BorderLayout.CENTER); // Adiciona o título ao CENTRO do painel do cabeçalho

        // Adiciona o painel do cabeçalho inteiro na parte NORTE da janela
        add(painelCabecalho, BorderLayout.NORTH);

        
    // =======================================================================
    //  LAYOUT PRINCIPAL COM MIGLAYOUT
    // =======================================================================
    JPanel painelVersaoDownloadChamado = new JPanel(new MigLayout(
            " insets 10 20 10 20",
            "[align left][grow, fill]",      // 2 colunas: label à esquerda, componente crescendo
                "[]"                             // Linhas com altura padrão            
           
    ));

    add(painelVersaoDownloadChamado, BorderLayout.CENTER);

    
    painelVersaoDownloadChamado.add(new JLabel("Selecione o Cliente:"));
    comboboxClienteDownload = new JComboBox<>();
    comboboxClienteDownload.setPreferredSize(new Dimension(250,20));
    painelVersaoDownloadChamado.add(comboboxClienteDownload, "width pref!, wrap");    
    
    carregarClientesDownload(); 
    
    painelVersaoDownloadChamado.add(new JLabel("Seleciona a Cidade:"));
    comboboxCidadeDownload = new JComboBox<>();
    comboboxCidadeDownload.setPreferredSize(new Dimension(250,20));
    painelVersaoDownloadChamado.add(comboboxCidadeDownload, "width pref!, wrap");

    
    painelVersaoDownloadChamado.add(new JLabel("N° Chamado:"));
    campoNumeroChamadoDownload = new JTextField();
    campoNumeroChamadoDownload.setPreferredSize(new Dimension(250,20));
        
    btnPesquisarVersao = new JButton("Pesquisar");
    btnPesquisarVersao.setPreferredSize(new Dimension(121,25));
    
    painelVersaoDownloadChamado.add(campoNumeroChamadoDownload,  "align left, split 2, width pref!");
    painelVersaoDownloadChamado.add(btnPesquisarVersao, "align left, split 2, width pref!, wrap" );

    painelVersaoDownloadChamado.add(new JLabel("Versão:"));
    campoArquivoVersaoDownload = new JTextField();
    campoArquivoVersaoDownload.setEditable(false);
    campoArquivoVersaoDownload.setBackground(UIManager.getColor("Panel.background"));
    campoArquivoVersaoDownload.setPreferredSize(new Dimension(250,20));
    painelVersaoDownloadChamado.add(campoArquivoVersaoDownload, "width pref!, wrap");
    
    btnGravarArquivoDownload = new JButton("Baixar",imageIconButtonBaixar);
    btnGravarArquivoDownload.setPreferredSize(new Dimension(100,25));
    painelVersaoDownloadChamado.add(btnGravarArquivoDownload, "gaptop 5, align left, width pref!, wrap");       
    
 // =========================================================================
    // 3. CRIAÇÃO DO RODAPÉ PARA O BOTÃO DE NAVEGAÇÃO
    // =========================================================================
    painelVersaoDownloadChamado.add(new JLabel(), "pushy, wrap");
    
    btnMudarParaUploadVersao = new JButton("Importar Versão", imageIconButtonImportar);
    btnMudarParaUploadVersao.setPreferredSize(new Dimension(160, 30));
    
    btnMudarParaDownloadVersaoCidade = new JButton("Baixar Versão Por Cidade",imageIconButtonBaixarCidade);
    btnMudarParaDownloadVersaoCidade.setPreferredSize(new Dimension(220, 30));
    
    painelVersaoDownloadChamado.add(btnMudarParaUploadVersao, "span 2, align left, gaptop 10");
    painelVersaoDownloadChamado.add(btnMudarParaDownloadVersaoCidade, "width pref!, split 2, align right, gaptop 10");

    

    // --- Adicionando os Listeners ---
    comboboxClienteDownload.addActionListener(this::verificarComboBox);    
    btnGravarArquivoDownload.addActionListener(this::baixarVersao);    
    btnPesquisarVersao.addActionListener(this::carregarNomeVersao);
    btnMudarParaUploadVersao.addActionListener(this::mudarTelaUploadVersao);
    btnMudarParaDownloadVersaoCidade.addActionListener(this::mudarTelaDownloadCidadeVersao);
    aplicarFiltroNumerico(campoNumeroChamadoDownload);
    
    
    }
    
    private void verificarComboBox(ActionEvent e) {

        String selecionadoCliente = (String) comboboxClienteDownload.getSelectedItem();

        // Limpa o comboBox de cidade antes de preencher com novas cidades
        comboboxCidadeDownload.removeAllItems();

       
        java.util.List<String> cidades = dao.listarCidadesPorCliente(selecionadoCliente);

        comboboxCidadeDownload.removeAllItems();
        for (String cidade : cidades) {
        comboboxCidadeDownload.addItem(cidade);
        }
        // ADICIONE ESTA LINHA: Define o índice selecionado como -1 (nenhum)
        comboboxCidadeDownload.setSelectedIndex(-1);
    }
    
    private void carregarNomeVersao(ActionEvent e) {
        
        String selecionadoCliente = (String) comboboxClienteDownload.getSelectedItem();
        String selecionadoCidade = (String) comboboxCidadeDownload.getSelectedItem();
        String selecionadoChamado = (String) campoNumeroChamadoDownload.getText().trim();
        
            // 2. Validar se os campos necessários foram preenchidos
        if (selecionadoCliente == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Para a execução do método
        }
        
        if (selecionadoCidade == null) {
        JOptionPane.showMessageDialog(this, "Por favor, selecione uma cidade.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return; // Para a execução do método
        }
        
        if (selecionadoChamado == null || selecionadoChamado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, informe o número do chamado", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 3. Chamar o método do DAO para buscar o nome do arquivo no banco
        //Assumindo que seu método `listarNomeArquivo` espera o número do chamado como uma String
        java.util.List<String> nomeArquivos = dao.listarNomeArquivo(selecionadoChamado,selecionadoCliente, selecionadoCidade);
        
       if (nomeArquivos != null && !nomeArquivos.isEmpty()) {
        // Se a lista não for nula e não estiver vazia, pegamos o primeiro resultado
        // (Assumindo que um chamado tem apenas um arquivo de versão)
        String nomeArquivo = nomeArquivos.get(0); 
        
        // Monta o texto final e o define no campo de versão
        String textoVersao = selecionadoCliente + " / " + selecionadoCidade + " / " + nomeArquivo;
        campoArquivoVersaoDownload.setText(textoVersao);
        
    } else {
        // Se a lista estiver vazia ou for nula, informa o usuário e limpa o campo
        JOptionPane.showMessageDialog(this, "Nenhuma versão encontrada para o chamado informado.", "Não encontrado", JOptionPane.INFORMATION_MESSAGE);
        campoArquivoVersaoDownload.setText(""); // Limpa o campo de texto
    }
    }
    
    
    private void baixarVersao(ActionEvent e){        
        String selecionadoCliente = (String) comboboxClienteDownload.getSelectedItem();
        String selecionadoCidade = (String) comboboxCidadeDownload.getSelectedItem();
        String selecionadoNumeroChamado = campoNumeroChamadoDownload.getText().trim();
        
        if (selecionadoCliente == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Para a execução do método
        }
        
        if (selecionadoCidade == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma cidade.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Para a execução do método
        }
        
        //  VERIFICA SE O CAMPO DO CHAMADO FOI PREENCHIDO
        if (selecionadoNumeroChamado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, informe o número do chamado primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Interrompe a execução
    }
        java.util.List<String> listaNomesArquivo = dao.listarNomeArquivo(selecionadoNumeroChamado, selecionadoCliente, selecionadoCidade );
        
        if (listaNomesArquivo == null || listaNomesArquivo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nenhuma versão encontrada para o chamado informado.", "Não encontrado", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
        // CORREÇÃO: Pega o primeiro nome de arquivo da lista.
        // Assumindo que cada chamado tem apenas um arquivo.
        String nomeArquivoParaBaixar = listaNomesArquivo.get(0);
        
        java.util.List<String> listaCaminhoOrigem = dao.listarCaminho(nomeArquivoParaBaixar);
        
        if (listaCaminhoOrigem == null || listaCaminhoOrigem.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Não foi possível encontrar o caminho de origem do arquivo.", "Erro", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
        
        // CORREÇÃO: Pega o primeiro caminho de origem da lista.
        String caminhoOrigemCompleto = listaCaminhoOrigem.get(0);
        
        try {
        // --- INÍCIO DA LÓGICA DE CÓPIA ---

        // 4. MONTA O CAMINHO DE ORIGEM COMPLETO (A CORREÇÃO PRINCIPAL ESTÁ AQUI)
        // Junta o caminho da pasta (ex: "P:\Repo\...") com o nome do arquivo (ex: "Frases.apk")
        Path origem = Paths.get(caminhoOrigemCompleto);
        
        // Verificação extra para garantir que o arquivo de origem realmente existe antes de copiar
        if (!Files.exists(origem)) {
             JOptionPane.showMessageDialog(this, "Erro: O arquivo de origem não foi encontrado no caminho:\n" + origem.toString(), "Arquivo Inexistente", JOptionPane.ERROR_MESSAGE);
             return;
        }

        // 5. MONTA O CAMINHO DE DESTINO NA PASTA DE DOWNLOADS DO USUÁRIO
        String homeDoUsuario = System.getProperty("user.home");
        String pastaDownloads = Paths.get(homeDoUsuario, "Downloads").toString();
        Path destino = Paths.get(pastaDownloads, nomeArquivoParaBaixar);

        // 6. EXECUTA A CÓPIA DO ARQUIVO        
        // Garante que a pasta de destino exista
        Files.createDirectories(destino.getParent());
        
        // Copia o arquivo da origem para o destino, substituindo se já existir
        Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
        
        // -- Adicione estas duas linhas para garantir a data ---
        FileTime dataAtual = FileTime.fromMillis(System.currentTimeMillis());
        Files.setLastModifiedTime(destino, dataAtual);
        
        // 7. MOSTRA MENSAGEM DE SUCESSO
        JOptionPane.showMessageDialog(this, "Arquivo baixado com sucesso em:\n" + destino.toString(), "Sucesso!", JOptionPane.INFORMATION_MESSAGE);

    } catch (IOException i) {
        JOptionPane.showMessageDialog(this, "Erro ao baixar o arquivo: " + i.getMessage(), "Erro de Cópia", JOptionPane.ERROR_MESSAGE);
        i.printStackTrace(); // Imprime o erro detalhado no console para depuração
    }
}
    
    
    private void carregarClientesDownload() {
    // Adiciona uma instrução inicial como primeiro item    

    // Busca os clientes do banco de dados
    java.util.List<String> clientes = dao.listarClientes();

    // Adiciona cada cliente da lista ao ComboBox
    for (String cliente : clientes) {
        comboboxClienteDownload.addItem(cliente);
        
        // ADICIONE ESTA LINHA: Define o índice selecionado como -1 (nenhum)
        comboboxClienteDownload.setSelectedIndex(-1);    
        }
    }
    
     private void aplicarFiltroNumerico(JTextField campo) {
        // Converte o documento do JTextField para AbstractDocument, que permite aplicar um filtro personalizado
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {

            // Este método é chamado quando o usuário tenta inserir texto (ex: digitando ou colando)
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Verifica se a string inserida contém apenas dígitos (0 a 9)
                if (string.matches("\\d+")) {
                    // Se for numérica, permite a inserção
                    super.insertString(fb, offset, string, attr);
                }
                // Caso contrário, ignora a inserção (não insere nada)
            }

            // Este método é chamado quando o usuário tenta substituir parte do texto (ex: seleciona e digita algo novo)
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                // Verifica se a string substituta é composta apenas por dígitos
                if (string.matches("\\d+")) {
                    // Se for numérica, permite a substituição
                    super.replace(fb, offset, length, string, attr);
                    // Caso contrário, ignora a substituição
                }
            }

            // Este método é chamado quando o usuário apaga texto (ex: tecla Backspace ou Delete)
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
                // Sempre permite remoção, pois não afeta a integridade numérica do campo
                super.remove(fb, offset, length);
            }
        });
    }
     
    
     
     private void mudarTelaUploadVersao(ActionEvent e) {
        // Garante que a nova janela seja criada na thread de eventos da GUI
        SwingUtilities.invokeLater(() -> {
            UploadVersaoFrame frame = new UploadVersaoFrame();
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


    
