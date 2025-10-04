package com.lucasignacio.repositorioversao;

// 1. Importar a classe do MigLayout
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;


public class DownloadVersaoCidadeFrame extends JFrame {
    
    
    private JComboBox<String> comboboxClienteDownload;
    private JComboBox<String> comboboxCidadeDownload;
    private JButton btnMudarParaUploadVersao;
    private JButton btnPesquisarVersao;    
    private JTable arquivoTabela;
    private DefaultTableModel tableModel; // O modelo para controlar os dados da tabela
    private JButton btnBaixarArquivoDownload;
    private JButton btnAlterarArquivoDownload;
    private JButton btnGravarAtualizacaoObservacaoDownload;
    private JButton btnCancelarArquivoDownload;
    private JButton btnExcluirArquivoDownload;
    private JButton btnMudarParaDownloadVersaoChamado;
    private boolean estaEditando = false;
    
    ClienteDAO dao = new ClienteDAO();
    TelaTabelaVersao tabelaVersao = new TelaTabelaVersao(); 
    
    public DownloadVersaoCidadeFrame() {
    
        setTitle("Repositório Versão Areatec V. 1.1");
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Define como tela cheia        
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
        
        //Carrega a imagem do button baixar
        java.net.URL imageBaixarURL = getClass().getResource("/drawable/botao_de_download.png");
        ImageIcon imageIconButtonBaixar = new ImageIcon(imageBaixarURL);
        Image imageButtonBaixar = imageIconButtonBaixar.getImage();
        Image newimgButtonBaixar = imageButtonBaixar.getScaledInstance(18, 16, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonBaixar = new ImageIcon(newimgButtonBaixar);
        
         //Carrega a imagem do button alterar
        java.net.URL imageAlterarURL = getClass().getResource("/drawable/alterar_image.png");
        ImageIcon imageIconButtonAlterar = new ImageIcon(imageAlterarURL);
        Image imageButtonAlterar = imageIconButtonAlterar.getImage();
        Image newimgButtonAlterar = imageButtonAlterar.getScaledInstance(18, 16, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonAlterar = new ImageIcon(newimgButtonAlterar);

        //Carrega a imagem do button gravar
        java.net.URL imageGravarURL = getClass().getResource("/drawable/confirmar_image.png");
        ImageIcon imageIconButtonGravar = new ImageIcon(imageGravarURL);
        Image imageButtonGravar = imageIconButtonGravar.getImage();
        Image newimgButtonGravar = imageButtonGravar.getScaledInstance(18, 16, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonGravar = new ImageIcon(newimgButtonGravar);        
        
         //Carrega a imagem do button cancelar
        java.net.URL imageCancelarURL = getClass().getResource("/drawable/cancelar_image.png");
        ImageIcon imageIconButtonCancelar = new ImageIcon(imageCancelarURL);
        Image imageButtonCancelar = imageIconButtonCancelar.getImage();
        Image newimgButtonCancelar = imageButtonCancelar.getScaledInstance(18, 16, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonCancelar = new ImageIcon(newimgButtonCancelar);
        
        //Carrega a imagem do button excluir
        java.net.URL imageExcluirURL = getClass().getResource("/drawable/excluir_image.png");
        ImageIcon imageIconButtonExcluir = new ImageIcon(imageExcluirURL);
        Image imageButtonExcluir = imageIconButtonExcluir.getImage();
        Image newimgbuttonExcluir = imageButtonExcluir.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonExcluir = new ImageIcon(newimgbuttonExcluir);
        
        //Carrega a imagem do button importar
        java.net.URL imageImportarURL = getClass().getResource("/drawable/upload_arquivo.png");
        ImageIcon imageIconButtonImportar = new ImageIcon(imageImportarURL);
        Image imageButtonImportar = imageIconButtonImportar.getImage();
        Image newimgbuttonImportar = imageButtonImportar.getScaledInstance(26, 26, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonImportar = new ImageIcon(newimgbuttonImportar);
        
        //Carrega a imagem do button baixar chamado
        java.net.URL imageBaixarChamadoURL = getClass().getResource("/drawable/download_direto_chamado.png");
        ImageIcon imageIconButtonBaixarChamado = new ImageIcon(imageBaixarChamadoURL);
        Image imageButtonBaixarChamado = imageIconButtonBaixarChamado.getImage();
        Image newimgbuttonBaixarChamado = imageButtonBaixarChamado.getScaledInstance(26, 26, java.awt.Image.SCALE_SMOOTH);
        imageIconButtonBaixarChamado = new ImageIcon(newimgbuttonBaixarChamado);
        
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        painelCabecalho.add(imageLabel, BorderLayout.WEST); // Adiciona a imagem à ESQUERDA do painel do cabeçalho

        // Cria o título
        JLabel tituloDownload = new JLabel("Baixar Versão Android Por Cidade", SwingConstants.CENTER);
        tituloDownload.setFont(new Font("Arial", Font.BOLD, 24));
        tituloDownload.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 110));
        painelCabecalho.add(tituloDownload, BorderLayout.CENTER); // Adiciona o título ao CENTRO do painel do cabeçalho
        
        

        // Adiciona o painel do cabeçalho inteiro na parte NORTE da janela
        add(painelCabecalho, BorderLayout.NORTH);
        
        // =======================================================================
        // LAYOUT PRINCIPAL COM MIGLAYOUT
        // =======================================================================
        JPanel painelVersaoDownloadCidade = new JPanel(new MigLayout(
           "fill, insets 10 20 10 20", // fill: Ocupa todo o espaço
                "[align left][][][][align right]",      // 4 colunas: label à esquerda, componente crescendo
                "[]"                             // Linhas com altura padrão
        ));

        add(painelVersaoDownloadCidade, BorderLayout.CENTER);


        painelVersaoDownloadCidade.add(new JLabel("Selecione o Cliente:"),"split 2");
        comboboxClienteDownload = new JComboBox<>();
        comboboxClienteDownload.setPreferredSize(new Dimension(250,20));
        painelVersaoDownloadCidade.add(comboboxClienteDownload, "width pref!, wrap");    

        carregarClientesDownload(); 

        painelVersaoDownloadCidade.add(new JLabel("Seleciona a Cidade:"),"split 2");
        comboboxCidadeDownload = new JComboBox<>();
        comboboxCidadeDownload.setPreferredSize(new Dimension(250,20));
        painelVersaoDownloadCidade.add(comboboxCidadeDownload, "width pref!, wrap");

        btnPesquisarVersao = new JButton("Pesquisar");
        btnPesquisarVersao.setPreferredSize(new Dimension(100, 25));
        painelVersaoDownloadCidade.add(btnPesquisarVersao, "align left, span 2, gaptop 5, width pref!, wrap");

        // --- Adicionando a Tabela ---
            // Define as colunas da tabela
        String[] colunas = {"ID","Cliente", "Cidade", "Chamado", "Observação", "Caminho", "Arquivo", "Versão"};
        // Cria o modelo da tabela, inicialmente sem linhas
        tableModel = new DefaultTableModel(colunas, 0);        
        
        
        arquivoTabela = new JTable(tableModel){
        @Override
            public boolean isCellEditable(int row, int column) {
                // A tabela só é editável se 'estaEditando' for true E a coluna for a de 'Observação' (índice 3)
               return estaEditando && column == 4;
            };
        };       
        
        // --- CÓDIGO PARA MOSTRAR APENAS BORDAS DAS LINHAS ---
        //arquivoTabela.setShowHorizontalLines(true); // Mostra as linhas horizontais
        //arquivoTabela.setShowVerticalLines(true);  // Mostra as linhas verticais
        //arquivoTabela.setGridColor(Color.LIGHT_GRAY); // (Opcional) Deixa a cor da linha mais suave
        // ----------------------------------------------------
        
        
        // --- TORNANDO AS LINHAS MAIS DESTACADAS ---
        arquivoTabela.setShowHorizontalLines(true);
        arquivoTabela.setShowVerticalLines(true);
        arquivoTabela.setGridColor(new Color(220, 220, 220)); // Um cinza bem claro

        // Aumenta a altura de todas as linhas da tabela para 30 pixels
        arquivoTabela.setRowHeight(30);

        // Define o espaçamento entre as células (0 na horizontal, 1 na vertical)
        // Isso cria uma linha de 1 pixel de altura entre as fileiras.
        arquivoTabela.setIntercellSpacing(new Dimension(0, 1));
        // --------------------------------------------
        

        // Adiciona a tabela dentro de um JScrollPane para ter barra de rolagem
        JScrollPane scrollPane = new JScrollPane(arquivoTabela);
        
        // --- ADICIONANDO BORDA COMPOSTA ---
        // 1. Cria a borda externa (linha cinza)
        Border bordaExterna = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        // 2. Cria a borda interna (espaçamento de 5 pixels)
        Border bordaInterna = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        // 3. Combina as duas e aplica ao scrollPane
        scrollPane.setBorder(BorderFactory.createCompoundBorder(bordaExterna, bordaInterna));
        // ----------------------------------

        // Adiciona o scrollPane ao layout
        // "span": ocupa todas as colunas da linha
        // "grow": faz o componente crescer para ocupar o espaço vertical e horizontal
        painelVersaoDownloadCidade.add(scrollPane, "span, grow, wrap, gaptop 10");

        btnBaixarArquivoDownload = new JButton("Baixar", imageIconButtonBaixar);
        btnBaixarArquivoDownload.setPreferredSize(new Dimension(100,25));
        painelVersaoDownloadCidade.add(btnBaixarArquivoDownload, "gaptop 5, align left, width pref!");
        
        btnAlterarArquivoDownload = new JButton("Alterar", imageIconButtonAlterar);
        btnAlterarArquivoDownload.setPreferredSize(new Dimension(100,25));
        painelVersaoDownloadCidade.add(btnAlterarArquivoDownload, "gaptop 5, align center, split 3, width pref!");
        
        btnGravarAtualizacaoObservacaoDownload = new JButton("Gravar", imageIconButtonGravar);
        btnGravarAtualizacaoObservacaoDownload.setPreferredSize(new Dimension(100,25));
        btnGravarAtualizacaoObservacaoDownload.setEnabled(false);
        painelVersaoDownloadCidade.add(btnGravarAtualizacaoObservacaoDownload, "gaptop 5, align center, width pref!");
        
        btnCancelarArquivoDownload = new JButton("Cancelar", imageIconButtonCancelar);
        btnCancelarArquivoDownload.setPreferredSize(new Dimension(100,25));
        btnCancelarArquivoDownload.setEnabled(false);
        painelVersaoDownloadCidade.add(btnCancelarArquivoDownload, "gaptop 5, align center, width pref!");

        btnExcluirArquivoDownload = new JButton("Excluir", imageIconButtonExcluir);
        btnExcluirArquivoDownload.setPreferredSize(new Dimension(100,25));
        painelVersaoDownloadCidade.add(btnExcluirArquivoDownload, "gaptop 5, span, align right, width pref!, wrap");

        // =========================================================================
        // 3. CRIAÇÃO DO RODAPÉ PARA O BOTÃO DE NAVEGAÇÃO
        // =========================================================================
        painelVersaoDownloadCidade.add(new JLabel(), "pushy, wrap");

        btnMudarParaUploadVersao = new JButton("Importar Versão",imageIconButtonImportar);
        btnMudarParaUploadVersao.setPreferredSize(new Dimension(160, 30));

        btnMudarParaDownloadVersaoChamado = new JButton("Baixar Versão Por Chamado",imageIconButtonBaixarChamado);
        btnMudarParaDownloadVersaoChamado.setPreferredSize(new Dimension(220, 30));

        painelVersaoDownloadCidade.add(btnMudarParaUploadVersao, " span 2,  align left, gaptop 10");  
        painelVersaoDownloadCidade.add(btnMudarParaDownloadVersaoChamado, "width pref!, span,  align right, gaptop 10");

        comboboxClienteDownload.addActionListener(this::verificarComboBox);
        btnMudarParaUploadVersao.addActionListener(this::mudarTelaUploadVersao);
        btnMudarParaDownloadVersaoChamado.addActionListener(this::mudarTelaDownloadCidadeVersao);
        btnPesquisarVersao.addActionListener(this::carregarTabelaAction);
        btnBaixarArquivoDownload.addActionListener(this::baixarVersao);
        btnExcluirArquivoDownload.addActionListener(this::excluirArquivoDownload);
        btnAlterarArquivoDownload.addActionListener(this::alterarObservacao);
        btnCancelarArquivoDownload.addActionListener(this::cancelarAlteracaoObservacao);
        btnGravarAtualizacaoObservacaoDownload.addActionListener(this::gravarAlteracaoObservacao);
    
    
    }    
    
   

    // Ação executada quando o botão "Pesquisar" é clicado.     
    private void carregarTabelaAction(ActionEvent e) {
        String cidadeSelecionada = (String) comboboxCidadeDownload.getSelectedItem();

        // Validação para garantir que uma cidade foi selecionada
        if (cidadeSelecionada == null || cidadeSelecionada.isBlank()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma cidade para pesquisar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Limpa a tabela antes de carregar novos dados
        tableModel.setRowCount(0);

        // Busca os dados no banco usando o DAO
        java.util.List<ArquivoInfo> listaDeArquivos = dao.listarArquivosPorCidade(cidadeSelecionada);

        // Verifica se a busca retornou resultados
        if (listaDeArquivos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum arquivo encontrado para a cidade selecionada.", "Sem Resultados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Percorre a lista de resultados e adiciona cada um como uma nova linha na tabela
            for (ArquivoInfo info : listaDeArquivos) {
                Object[] rowData = {
                    info.getId_chamado(),
                    info.getCliente(),
                    info.getCidade(),
                    info.getChamado(),
                    info.getObservacao(),
                    info.getCaminhoArquivo(),
                    info.getNomeArquivo()
                };
                tableModel.addRow(rowData);
            }
        }
    }
    
    private void baixarVersao(ActionEvent e) {         
        String selecionadoCliente = (String) comboboxClienteDownload.getSelectedItem();
        String selecionadoCidade = (String) comboboxCidadeDownload.getSelectedItem();
        int linhaSelecionada = arquivoTabela.getSelectedRow();
        int colunaSelecionadaCaminho = 5;
        int colunaSelecionadaNomeArquivo = 6;
        
        if (selecionadoCliente == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Para a execução do método
        }
        
        if (selecionadoCidade == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma cidade.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Para a execução do método
        }
        
        if (linhaSelecionada != -1) {
            Object valorCelulaCaminho = arquivoTabela.getValueAt(linhaSelecionada, colunaSelecionadaCaminho);
            Object valorCelulaNomeArquivo = arquivoTabela.getValueAt(linhaSelecionada, colunaSelecionadaNomeArquivo);
            System.out.println("Valor da célula: " + " Caminho: " + valorCelulaCaminho + "/ Nome da Versão: " + valorCelulaNomeArquivo);
                       
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma linha do arquivo.", "Aviso", JOptionPane.WARNING_MESSAGE);            
            System.out.println("Nenhuma linha selecionada.");
            return; // Para a execução do método
        }
        
        Object caminhoOrigemObj = arquivoTabela.getValueAt(linhaSelecionada, colunaSelecionadaCaminho);
        Object nomeArquivoObj = arquivoTabela.getValueAt(linhaSelecionada, colunaSelecionadaNomeArquivo);
        
        String caminhoOrigemCompleto = String.valueOf(caminhoOrigemObj);
        String nomeArquivoParaBaixar = String.valueOf(nomeArquivoObj);
        
        if (caminhoOrigemCompleto == null || caminhoOrigemCompleto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Não foi possível encontrar o caminho de origem do arquivo.", "Erro", JOptionPane.INFORMATION_MESSAGE);
        return;
        }
        
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
    
    private void excluirArquivoDownload(ActionEvent e){
        int linhaSelecionada = arquivoTabela.getSelectedRow();
         
        
          // 1. Validação: Garante que uma linha foi selecionada
        if (linhaSelecionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, selecione uma linha na tabela para excluir.", "Nenhuma linha selecionada", JOptionPane.WARNING_MESSAGE);
        return;
        }
                       
      
         // 2. Pega os dados da tabela (usando o tableModel para segurança)
         Object chamadoObj = tableModel.getValueAt(linhaSelecionada, 3);
         Object caminhoOrigemObj = tableModel.getValueAt(linhaSelecionada, 5);
         
         String chamadoStr = String.valueOf(chamadoObj);         
         String caminhoOrigemCompleto = String.valueOf(caminhoOrigemObj);
         
                 
        
        // 3. Pede a confirmação do usuário
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja excluir o arquivo e sua pasta?\n" + caminhoOrigemCompleto,
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                // 4. CORREÇÃO PRINCIPAL: Pega a PASTA PAI do arquivo
                Path caminhoDoArquivo = Paths.get(caminhoOrigemCompleto);
                Path diretorioParaExcluir = caminhoDoArquivo.getParent();

                // Garante que o diretório a ser excluído realmente existe
                if (diretorioParaExcluir != null && Files.exists(diretorioParaExcluir)) {

                    // 5. Chama o método para deletar a PASTA recursivamente
                    deleteDirectory(diretorioParaExcluir);

                    // 6. Se a exclusão física foi bem-sucedida, exclui do banco
                    dao.excluirArquivo(chamadoStr);
                    JOptionPane.showMessageDialog(this, "Diretório e registro excluídos com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                    // 7. Atualiza a tabela na tela para remover a linha
                    tableModel.removeRow(linhaSelecionada);
                
            } else {                    
                // Se não encontrou o diretório, avisa e exclui apenas do banco
                 dao.excluirArquivo(chamadoStr);
                 JOptionPane.showMessageDialog(this, "O diretório físico não foi encontrado, mas o registro foi removido do banco.", "Aviso", JOptionPane.WARNING_MESSAGE);
                 tableModel.removeRow(linhaSelecionada);
            }
            } catch (IOException i) {                
               JOptionPane.showMessageDialog(this, "Erro ao excluir: " + i.getMessage(), "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
            i.printStackTrace();
            }
        }        
                
    }
    
    public static void deleteDirectory(Path path) throws IOException {
        if (Files.isDirectory(path)) {
        // Usa `Files.walk` em vez de `Files.list` para percorrer recursivamente
        try (Stream<Path> files = Files.walk(path)) {
            // Ordena o stream em ordem reversa para garantir que os arquivos sejam excluídos antes dos diretórios
            files.sorted((p1, p2) -> p2.compareTo(p1))
                 .forEach(p -> {
                     try {
                         Files.delete(p);
                     } catch (IOException e) {
                         // Lide com a exceção se um arquivo não puder ser excluído
                         System.err.println("Erro ao deletar " + p + ": " + e.getMessage());
                     }
                 });
        }
        
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
    
    private void alterarObservacao(ActionEvent e) {
        int linhaSelecionada = arquivoTabela.getSelectedRow();

        // 1. Garante que o usuário selecionou uma linha para editar
        if (linhaSelecionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, selecione uma linha na tabela para alterar.", "Nenhuma linha selecionada", JOptionPane.WARNING_MESSAGE);
        return;   }    
            
            
          
         // Define a tabela como editável
        estaEditando = true;
         
         // Habilita os botões de controle e desabilita os outros  
        btnGravarAtualizacaoObservacaoDownload.setEnabled(true);
        btnCancelarArquivoDownload.setEnabled(true);
        
        btnBaixarArquivoDownload.setEnabled(false);
        btnAlterarArquivoDownload.setEnabled(false);
        btnExcluirArquivoDownload.setEnabled(false);
        btnPesquisarVersao.setEnabled(false);
        btnMudarParaUploadVersao.setEnabled(false);
        btnMudarParaDownloadVersaoChamado.setEnabled(false);
        comboboxCidadeDownload.setEnabled(false);
        comboboxClienteDownload.setEnabled(false);     
         
         // 3. Foca e inicia a edição na célula de "Observação" (coluna 4)
        arquivoTabela.editCellAt(linhaSelecionada, 4);
        
        // Pega o componente editor (o JTextField dentro da célula) e pede foco
        Component editor = arquivoTabela.getEditorComponent();
        if  (editor != null) {
        editor.requestFocusInWindow();
        }
         
         // Altera a visibilidade da coluna 'Observação' para ser editável
         arquivoTabela.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField("")));
         System.out.println("A coluna 'Observação' está agora editável.\\nClique em 'Gravar' para salvar a alteração.");

         arquivoTabela.setRowSelectionInterval(0,0);
         arquivoTabela.changeSelection(0,4, false, false);         
         
        }
        
    private void gravarAlteracaoObservacao(ActionEvent e){
        // 1. Para a edição da célula e salva o novo valor no TableModel
        if (arquivoTabela.isEditing()) {
            arquivoTabela.getCellEditor().stopCellEditing();
        }

        int linhaSelecionada = arquivoTabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            // Se por algum motivo nenhuma linha estiver selecionada, cancela a operação
            cancelarAlteracaoObservacao(null);
            return;
        }
        
        // 2. Pega os dados necessários da tabela
        // Coluna 0 = ID do arquivo, Coluna 4 = Observação
        int id = (int) tableModel.getValueAt(linhaSelecionada, 0);
        String novaObservacao = (String) tableModel.getValueAt(linhaSelecionada, 4);

        // 3. Chama o DAO para atualizar o banco de dados
        try {
            dao.atualizarObservacao(id, novaObservacao);
            JOptionPane.showMessageDialog(this, "Observação atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gravar a observação no banco de dados.", "Erro de Banco", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
        // 4. Restaura a interface para o "modo normal", como no cancelar
        estaEditando = false;
        btnGravarAtualizacaoObservacaoDownload.setEnabled(false);
        btnCancelarArquivoDownload.setEnabled(false);

        btnBaixarArquivoDownload.setEnabled(true);
        btnAlterarArquivoDownload.setEnabled(true);
        btnExcluirArquivoDownload.setEnabled(true);
        btnPesquisarVersao.setEnabled(true);
        comboboxClienteDownload.setEnabled(true);
        comboboxCidadeDownload.setEnabled(true);
        
        
        }
                
                
                
    private void cancelarAlteracaoObservacao(ActionEvent e) {
        // 1. PASSO MAIS IMPORTANTE: Verifica se uma célula está sendo editada e cancela a edição.
        // Isso descarta qualquer alteração que o usuário tenha feito na célula.
        if (arquivoTabela.isEditing()) {
        arquivoTabela.getCellEditor().cancelCellEditing();
        }
         
     
        // Define a tabela como não editável
        estaEditando = false;
        
        btnGravarAtualizacaoObservacaoDownload.setEnabled(false);
        btnCancelarArquivoDownload.setEnabled(false);
        
        btnBaixarArquivoDownload.setEnabled(true);
        btnAlterarArquivoDownload.setEnabled(true);
        btnExcluirArquivoDownload.setEnabled(true);
        btnPesquisarVersao.setEnabled(true);
        btnMudarParaUploadVersao.setEnabled(true);
        btnMudarParaDownloadVersaoChamado.setEnabled(true);
        comboboxCidadeDownload.setEnabled(true);
        comboboxClienteDownload.setEnabled(true);  
         
        
       
        // 3. Recarrega os dados da tabela a partir do banco para garantir que tudo volte ao original.
        // Esta é uma forma segura de "desfazer".
        carregarTabelaAction(null); // Passamos 'null' pois o evento não é relevante aqui
        
        
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
                DownloadVersaoChamadoFrame frame = new DownloadVersaoChamadoFrame();
                frame.setVisible(true);           
            dispose(); // Fecha a janela atual de Upload
        });
    }
}
