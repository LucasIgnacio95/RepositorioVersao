package com.lucasignacio.repositorioversao;

import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        // Aplica o visual FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Este bloco garante que a interface gráfica seja criada na thread correta (Event Dispatch Thread).   
        SwingUtilities.invokeLater(() -> {
            UploadVersaoFrame frame = new UploadVersaoFrame();
            frame.setVisible(true);            
        });
        
          Connection conn = ConexaoSQL.conectar();
        if (conn != null) {
            System.out.println("Conectado com sucesso!");
        } else {
            System.out.println("Falha na conexão.");
        }
      }
    }
    
   

